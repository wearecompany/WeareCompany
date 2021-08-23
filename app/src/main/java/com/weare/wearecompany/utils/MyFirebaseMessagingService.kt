package com.weare.wearecompany.utils


import android.app.*
import android.content.ClipData.newIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.ui.chat.ChatActivity
import com.weare.wearecompany.ui.container.ContainerActivity
import com.weare.wearecompany.utils.Constants.TAG
import timber.log.Timber


class MyFirebaseMessagingService : FirebaseMessagingService() {

    private lateinit var typelist: ArrayList<String>
    private var activity : String = ""
    private lateinit var componentName: ComponentName
    // 받은 메시지에서 title와body를 추가
    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        /*Log.d("알림1",remoteMessage.from!!)
        if (remoteMessage.data.size > 0) {
            Log.d("알림1",remoteMessage.from!!)
            handleNow()
        }*/

        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val info : MutableList<ActivityManager.AppTask>? = manager.appTasks
        if (info?.size != 0) {
            componentName = info?.get(0)?.taskInfo?.topActivity!!
            activity = componentName?.shortClassName?.substring(1).toString()
        }


        typelist = ArrayList()


        var data : Map<String,String> = remoteMessage.data
        var title = data["title"].toString()
        var body = data["body"].toString()
        var type = data["type"].toString()

        val dt = type.split("_")
        for (i in dt) {
            typelist.add(i)
        }


        when(typelist[0]) {
            "0" -> {
                sendNotification(title,body,typelist[0],typelist[1])
            }
            "1" -> {
                if (activity == "ui.chat.ChatActivity") {
                    if (typelist[1] == ChatActivity.chat_idx) {
                    } else {
                        sendNotification(title,body,typelist[0],typelist[1])
                    }
                } else {
                    sendNotification(title,body,typelist[0],typelist[1])
                }
            }
        }
    }

    private fun handleNow() {
        Log.d(TAG, "Short lived task is done.")
    }

    // 받은 title과 body로 디바이스 알림 전
    private fun sendNotification(messageTitle: String, messageBody: String, type_1: String, type_2: String) {
        val intent = Intent(this, ContainerActivity::class.java) //푸시 클릭시 나오는 화면
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("type_1", type_1)
        intent.putExtra("type_2", type_2)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.icon1_launcher) //알림 작음 아이콘
            .setContentTitle(messageTitle) //알림 제목
            .setContentText(messageBody) //알림 부제목
            .setDefaults(Notification.DEFAULT_ALL) // 알림, 사운드 진동 설정
            .setAutoCancel(true) //알림 터치시 반응 후 삭제
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        //오레오 버전 이후
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = getString(R.string.default_notification_channel_name)
            val channel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            channel.description = "channel description"
            channel.enableLights(true) //알림을 표시 할 수 있도록 설정
            channel.lightColor = Color.RED //알림 표시 색상
            channel.enableVibration(true) //알림에 대한 진동 활성화
            channel.vibrationPattern = longArrayOf(100, 200, 100, 200)
            channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            if (defaultSoundUri != null) {
                val audioAttributes = AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build()
                channel.setSound(defaultSoundUri, audioAttributes)
            }
            channel.setShowBadge(false)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(1, notificationBuilder.build())
    }

}
