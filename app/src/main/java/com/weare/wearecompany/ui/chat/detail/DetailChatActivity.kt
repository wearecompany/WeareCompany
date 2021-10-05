package com.weare.wearecompany.ui.chat.detail

import android.Manifest
import android.app.Activity
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.VibrationEffect
import android.os.Vibrator
import android.provider.MediaStore
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import com.weare.wearecompany.BuildConfig
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.chatting.ChatDatail
import com.weare.wearecompany.data.chatting.ChatLog
import com.weare.wearecompany.data.chatting.data.send
import com.weare.wearecompany.data.retrofit.IRetrofit
import com.weare.wearecompany.data.retrofit.RetrofitClient
import com.weare.wearecompany.data.retrofit.chatting.chattingManager
import com.weare.wearecompany.databinding.ActivityChatBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.ui.chat.ChatActivity
import com.weare.wearecompany.ui.chat.ChatDialogFragment
import com.weare.wearecompany.ui.chat.ChatImageUploadFragment
import com.weare.wearecompany.ui.chat.ChatRecyclerAdapterView
import com.weare.wearecompany.ui.detail.PinchActivity
import com.weare.wearecompany.utils.API
import com.weare.wearecompany.utils.RESPONSE_STATUS
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.engineio.client.EngineIOException
import org.json.JSONObject
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DetailChatActivity : BaseActivity<ActivityChatBinding>(
    R.layout.activity_chat
), View.OnClickListener {

    lateinit var socket: Socket

    lateinit var mContext: Context

    private var reserve_idx: String = ""
    private var request_idx: String = ""
    private var request_log_idx: String = ""
    private var expert_user_nickname: String = ""
    private var expert_user_idx: String = ""
    private var expert_idx: String = ""
    private var chat_end: Int = -1
    private var expert_type: String = ""
    private var status: Int = -1
    private var refund_status: Int = -1
    private var report_status: Int = -1
    private var user_idx: Int = -1

    private val REQ_CAMERA_PERMISSION = 1001
    val REQ_GALLERY_PERMISSION = 1002
    private val CAMERA_IMAGE = 1
    private val GALLERY_IMAGE = 2
    private var photoUri: Uri? = null
    private var imageFileName: String = ""
    private var fileNameList: ArrayList<String> = ArrayList()
    private var imagePath: String = ""

    private var image_width = -1
    private var image_height = -1

    private var photoList = ArrayList<Uri>()
    private var filenameList = ArrayList<String>()
    private var startUploadImage = 0
    private var endUploadImage = 0

    private var second = 10
    private var second_check = 1

    var sendlist = ArrayList<send>()
    lateinit var dataAdapter: ChatRecyclerAdapterView
    private var type: Int = -1
    private var Entrytype: Int = -1
    var detailArry = ArrayList<ChatDatail>()
    var chatlist = ArrayList<ChatLog>()
    var dtlist = ArrayList<String>()
    var forbiddenlist = ArrayList<String>()
    private var msgidx: String = ""
    lateinit var newIntent: Intent

    var compositeDisposable = CompositeDisposable()

    protected val disposables by lazy {
        CompositeDisposable()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }

    fun newIntent(context: Context?): Intent? {
        return Intent(context, ChatActivity::class.java)
    }

    companion object {
        lateinit var chat_idx: String
    }

    private val iRetrofit: IRetrofit? =
        RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState?.putString("msgidx", msgidx)
        /* 맨 처음에는 저장되어있는 Bundle 데이터가 없으므로, outState가 null일 수 있다.
           따라서 outState 뒤에 ? 를 붙여 safeCall 한다. */
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null) {
            msgidx = savedInstanceState.getString("stringKey").toString()
        }
    }

    override fun onCreate() {

        val toobar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toobar)
        val ActionBar = supportActionBar!!
        ActionBar.setDisplayHomeAsUpEnabled(true)
        ActionBar.setDisplayShowTitleEnabled(false)

        mContext = this

        type = intent.getIntExtra("type", 0)
        Entrytype = intent.getIntExtra("Entrytype", 0)

        bindingSetUp()
        dataSetUp()

        forbiddenlistsetUp()

        var cm : ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        dataAdapter = ChatRecyclerAdapterView(cm,this, sendlist)

        mViewDataBinding.chatRecyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL, false
        )

        mViewDataBinding.chatRecyclerView.adapter = dataAdapter
        mViewDataBinding.chatRecyclerView.setHasFixedSize(true) //아이템이 추가,삭제 될대 크기 측면에서 오류 방지

        mViewDataBinding.chatRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (mViewDataBinding.chatRecyclerView.canScrollVertically(1)) {
                    mViewDataBinding.chatFab.visibility = View.VISIBLE
                } else {
                    mViewDataBinding.chatFab.visibility = View.GONE
                }
            }
        })

        dataAdapter.setItemClickListener(object : ChatRecyclerAdapterView.OnItemClickListener {
            override fun onClick(v: View, position: Int, item: send) {
                if (item.msg_type == 1) {
                    val newIntent = Intent(MyApplication.instance, PinchActivity::class.java)
                    newIntent.putExtra("image", item.image_origin_url)
                    startActivityForResult(newIntent, 3000)
                } else if (item.msg_type == 3) {
                    val newIntent = Intent(MyApplication.instance, PinchActivity::class.java)
                    newIntent.putExtra("image", item.image_origin_url)
                    startActivityForResult(newIntent, 3000)
                }
            }
        })

        mViewDataBinding.chatEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (mViewDataBinding.chatEditText.isFocusable && !s.toString().equals("")) {
                    mViewDataBinding.chatOkImage.visibility = View.VISIBLE
                } else if (mViewDataBinding.chatEditText.isFocusable && s.toString().equals("")) {
                    mViewDataBinding.chatOkImage.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun forbiddenlistsetUp() {
        forbiddenlist.add("카카오톡")
        forbiddenlist.add("카톡")
        forbiddenlist.add("카톡으로")
        forbiddenlist.add("톡")
        forbiddenlist.add("톡으로")
        forbiddenlist.add("디엠")
        forbiddenlist.add("인스타")
        forbiddenlist.add("인스타그램")
        forbiddenlist.add("010")
        forbiddenlist.add("까톡")
        forbiddenlist.add("라인")
        forbiddenlist.add("아이디")
        forbiddenlist.add("카카오")
        forbiddenlist.add("ID")
        forbiddenlist.add("sns")
        forbiddenlist.add("SNS")
        forbiddenlist.add("kakao")
        forbiddenlist.add("KAKAO")
        forbiddenlist.add("TALK")
        forbiddenlist.add("talk")
        forbiddenlist.add("Instagram")
        forbiddenlist.add("instagram")
        forbiddenlist.add("따로 연락")
        forbiddenlist.add("연락처")
        forbiddenlist.add("전화")
        forbiddenlist.add("전화번호")
        forbiddenlist.add("핸드폰번호")
        forbiddenlist.add("핸드폰")
        forbiddenlist.add("휴대폰")
        forbiddenlist.add("번호")
    }

    private fun bindingSetUp() {

        mViewDataBinding.chatOkImage.setOnClickListener(this)
        mViewDataBinding.relativeLayout3.setOnClickListener(this)
        mViewDataBinding.chatImageUpload.setOnClickListener(this)
        mViewDataBinding.chatMenu.setOnClickListener(this)
        mViewDataBinding.chatFab.setOnClickListener(this)

        user_idx = MyApplication.prefs.getString("user_idx", "").toInt()
        expert_type = intent.getStringExtra("expert_type").toString()
        expert_idx = intent.getStringExtra("expert_idx").toString()
        expert_user_idx = intent.getStringExtra("expert_user_idx").toString()
    }

    private fun dataSetUp() {

        chattingManager.instance.servicedetail(user_idx,expert_type,expert_idx,expert_user_idx, completion = {responseStatus, arrayList ->
            when (responseStatus) {
                RESPONSE_STATUS.OKAY -> {
                    detailArry = arrayList
                    ChatActivity.chat_idx = detailArry[0].chat_idx
                    mViewDataBinding.expertUserNickname.text = detailArry[0].oppenent_nickname

                    socketsetup()
                    log()
                }
            }
        })
    }

    private fun log() {

        val `object` = JsonObject()
        `object`.addProperty("chat_idx", ChatActivity.chat_idx)
        `object`.addProperty("msg_idx", "0")

        iRetrofit?.chatlog(`object`)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                for (i in it.chat_log) {
                    if (i.msg_type == "noti") {
                        val dt = i.msg.split("||")
                        mViewDataBinding.chatNotiTitle.text = dt[0]
                        mViewDataBinding.chatNotiSubTitle.text = dt[1]
                        val msgitem = send(
                            msg_idx = "",
                            msg_type = -1,
                            send_type = -1,
                            send_day = "",
                            send_time = "",
                            msg = "",
                            image_origin_url = "",
                            image_resize_url = "",
                            origin_width = -1,
                            origin_height = -1
                        )
                        dataAdapter.addItem(msgitem)
                    } else if (i.send_type == 0) {
                        when (i.msg_type) {
                            "msg" -> {
                                val msgitem = send(
                                    msg_idx = i.msg_idx,
                                    msg_type = 0,
                                    send_type = i.send_type,
                                    send_day = i.send_day,
                                    send_time = i.send_time,
                                    msg = i.msg,
                                    image_origin_url = "",
                                    image_resize_url = "",
                                    origin_width = -1,
                                    origin_height = -1
                                )
                                msgidx = i.msg_idx
                                dataAdapter.addItem(msgitem)
                                mViewDataBinding.chatRecyclerView.smoothScrollToPosition(dataAdapter.itemCount - 1)
                            }
                            "img" -> {
                                val dt = i.msg.split("||")
                                val msgitem = send(
                                    msg_idx = i.msg_idx,
                                    msg_type = 1,
                                    send_type = i.send_type,
                                    send_day = i.send_day,
                                    send_time = i.send_time,
                                    msg = "",
                                    image_origin_url = dt[1],
                                    image_resize_url = dt[0],
                                    origin_width = dt[2].toInt(),
                                    origin_height = dt[3].toInt()
                                )
                                msgidx = i.msg_idx
                                dataAdapter.addItem(msgitem)
                                mViewDataBinding.chatRecyclerView.smoothScrollToPosition(dataAdapter.itemCount - 1)
                            }
                        }
                    } else if (i.send_type == 1) {
                        when (i.msg_type) {
                            "msg" -> {
                                val msgitem = send(
                                    msg_idx = i.msg_idx,
                                    msg_type = 2,
                                    send_type = i.send_type,
                                    send_day = i.send_day,
                                    send_time = i.send_time,
                                    msg = i.msg,
                                    image_origin_url = "",
                                    image_resize_url = "",
                                    origin_width = -1,
                                    origin_height = -1
                                )
                                msgidx = i.msg_idx
                                dataAdapter.addItem(msgitem)
                                mViewDataBinding.chatRecyclerView.smoothScrollToPosition(dataAdapter.itemCount - 1)
                            }
                            "img" -> {
                                val dt = i.msg.split("||")
                                val msgitem = send(
                                    msg_idx = i.msg_idx,
                                    msg_type = 3,
                                    send_type = i.send_type,
                                    send_day = i.send_day,
                                    send_time = i.send_time,
                                    msg = "",
                                    image_origin_url = dt[1],
                                    image_resize_url = dt[0],
                                    origin_width = dt[2].toInt(),
                                    origin_height = dt[3].toInt()
                                )
                                msgidx = i.msg_idx
                                dataAdapter.addItem(msgitem)
                                mViewDataBinding.chatRecyclerView.smoothScrollToPosition(dataAdapter.itemCount - 1)
                            }
                        }
                    }
                }
                if (dataAdapter.itemCount > 1) {
                    mViewDataBinding.chatRecyclerView.postDelayed(Runnable { // Select the last row so it will scroll into view...
                        mViewDataBinding.chatRecyclerView.smoothScrollToPosition(dataAdapter.itemCount - 1)
                    }, 200)
                }
                compositeDisposable.dispose()
            }, {
                Toast.makeText(this, "통신실패", Toast.LENGTH_SHORT).show()
                compositeDisposable.dispose()
            })

    }

    private fun socketsetup() {
        // 소켓 서버 연결
        socket = IO.socket(API.REFUND_URL)
        socket.connect()

        socket.on(Socket.EVENT_CONNECT) {
            // 소켓 서버에 연결이 성공하면 호출됩니다.
            Log.i("Socket", "Connect")

        }.on(Socket.EVENT_DISCONNECT) { args ->
            // 소켓 서버 연결이 끊어질 경우에 호출됩니다.
            Log.i("Socket", "Disconnet: ${args[0]}")
        }.on(Socket.EVENT_CONNECT_ERROR) { args ->
            // 소켓 서버 연결 시 오류가 발생할 경우에 호출됩니다.
            val errorMessage = ""
            if (args[0] is EngineIOException) {
                //errorMessage = args[0].toString()
            }
            Log.i("Socket", "Connect Error: $errorMessage")
        }

        socket.on("msg") { args ->
            Log.d("msg", "msg deta: $args")
            val data = args[0] as JSONObject
            addmsg(data)
        }
        socket.on("chatImage") { args ->
            Log.d("chatImage", "chatImage deta: $args")
            val data = args[0] as JSONObject
            addimage(data)
        }
        socket.on("notiUpdate") { args ->
            Log.d("notiUpdate", "notUpdate deta: $args")
            val data = args[0] as JSONObject
            notiupdate(data)
        }

        val `object2` = JSONObject()
        `object2`.put("chat_idx", ChatActivity.chat_idx)
        `object2`.put("user_type", 0)

        socket?.emit("joinRoom", object2)
    }

    fun addmsg(data: JSONObject) {
        if (data.getInt("send_type") == 0) {
            this.runOnUiThread {
                val msgitem = send(
                    msg_idx = data.getString("msg_idx"),
                    msg_type = 0,
                    send_type = data.getInt("send_type"),
                    send_day = data.getString("send_day"),
                    send_time = data.getString("send_time"),
                    msg = data.getString("msg"),
                    image_origin_url = "",
                    image_resize_url = "",
                    origin_width = -1,
                    origin_height = -1
                )
                msgidx = data.getString("msg_idx")
                dataAdapter.addItem(msgitem)
                mViewDataBinding.chatRecyclerView.smoothScrollToPosition(dataAdapter.itemCount - 1)
            }
        } else if (data.getInt("send_type") == 1) {
            this.runOnUiThread {
                val msgitem = send(
                    msg_idx = data.getString("msg_idx"),
                    msg_type = 2,
                    send_type = data.getInt("send_type"),
                    send_day = data.getString("send_day"),
                    send_time = data.getString("send_time"),
                    msg = data.getString("msg"),
                    image_origin_url = "",
                    image_resize_url = "",
                    origin_width = -1,
                    origin_height = -1
                )
                msgidx = data.getString("msg_idx")
                dataAdapter.addItem(msgitem)
                mViewDataBinding.chatRecyclerView.smoothScrollToPosition(dataAdapter.itemCount - 1)
            }
        }
    }

    fun addimage(data: JSONObject) {
        if (data.getInt("send_type") == 0) {

            this.runOnUiThread {
                val msgitem = send(
                    msg_idx = data.getString("msg_idx"),
                    msg_type = 1,
                    send_type = data.getInt("send_type"),
                    send_day = data.getString("send_day"),
                    send_time = data.getString("send_time"),
                    msg = "",
                    image_origin_url = data.getString("image_origin_url"),
                    image_resize_url = data.getString("image_resize_url"),
                    origin_width = data.getInt("origin_width"),
                    origin_height = data.getInt("origin_height")
                )
                msgidx = data.getString("msg_idx")
                dataAdapter.addItem(msgitem)
                mViewDataBinding.chatRecyclerView.smoothScrollToPosition(dataAdapter.itemCount - 1)
                mViewDataBinding.chatImageLoding.visibility = View.GONE
            }
        } else if (data.getInt("send_type") == 1) {
            this.runOnUiThread {
                val msgitem = send(
                    msg_idx = data.getString("msg_idx"),
                    msg_type = 3,
                    send_type = data.getInt("send_type"),
                    send_day = data.getString("send_day"),
                    send_time = data.getString("send_time"),
                    msg = "",
                    image_origin_url = data.getString("image_origin_url"),
                    image_resize_url = data.getString("image_resize_url"),
                    origin_width = data.getInt("origin_width"),
                    origin_height = data.getInt("origin_height")
                )
                msgidx = data.getString("msg_idx")
                dataAdapter.addItem(msgitem)
                mViewDataBinding.chatRecyclerView.smoothScrollToPosition(dataAdapter.itemCount - 1)
                mViewDataBinding.chatImageLoding.visibility = View.GONE
            }
        }
    }

    fun notiupdate(data: JSONObject) {
        this.runOnUiThread {
            mViewDataBinding.chatNotiTitle.text = data.getString("title")
            mViewDataBinding.chatNotiSubTitle.text = data.getString("sub_title")
            status = data.getInt("status")
            refund_status = data.getInt("refund_status")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        when (requestCode) {
            1 -> {
                if (resultCode == Activity.RESULT_OK) {
                    mViewDataBinding.chatImageLoding.visibility = View.VISIBLE
                    Cameraimageupload()
                }

            }
            2 -> {
                if (resultCode == Activity.RESULT_OK) {

                    mViewDataBinding.chatImageLoding.visibility = View.VISIBLE

                    if (data?.clipData != null) { // 사진 여러개 선택한 경우
                        val count = data.clipData!!.itemCount
                        if (count <= 3) {
                            for (i in 0 until count) {
                                photoUri = data.clipData!!.getItemAt(i).uri
                                val timeStamp = SimpleDateFormat("HHmmss").format(Date())
                                imageFileName = ""
                                imageFileName = "chat" + "_" + timeStamp
                                photoList.add(photoUri!!)
                                filenameList.add(imageFileName)
                            }
                            endUploadImage = photoList.size
                            imageupload()
                        } else {
                            Toast.makeText(this, "사진을 최대 3장까지 선택 가능합니다.", Toast.LENGTH_SHORT).show()
                            mViewDataBinding.chatImageLoding.visibility = View.GONE
                        }
                    } else {    //단일 선택
                        data?.data?.let { uri ->
                            photoUri = data?.data!!
                            val timeStamp = SimpleDateFormat("HHmmss").format(Date())
                            imageFileName = ""
                            imageFileName = "chat" + "_" + timeStamp
                            //photoList.add(photoUri!!)
                            filenameList.add(imageFileName)
                            Cameraimageupload()
                        }
                    }
                }

            }
            3000 -> {

                /*chattingManager.instance.log(
                    chat_idx,
                    msgidx,
                    completion = { responseStatus, arraylist ->
                        when (responseStatus) {
                            RESPONSE_STATUS.OKAY -> {
                                if (arraylist.size != 0) {
                                    for (i in arraylist[0].chat_log) {
                                        if (i.msg_type == "noti") {
                                            val dt = i.msg.split("||")
                                            mViewDataBinding.chatNotiTitle.text = dt[0]
                                            mViewDataBinding.chatNotiSubTitle.text = dt[1]
                                        } else if (i.send_type == 0) {
                                            when (i.msg_type) {
                                                "msg" -> {
                                                    val msgitem = send(
                                                        msg_idx = i.msg_idx,
                                                        msg_type = 0,
                                                        send_type = i.send_type,
                                                        send_day = i.send_day,
                                                        send_time = i.send_time,
                                                        msg = i.msg,
                                                        image_origin_url = "",
                                                        image_resize_url = "",
                                                        origin_width = -1,
                                                        origin_height = -1
                                                    )
                                                    msgidx = i.msg_idx
                                                    dataAdapter.addItem(msgitem)
                                                    mViewDataBinding.chatRecyclerView.scrollToPosition(
                                                        dataAdapter.getsize() - 1
                                                    )
                                                }
                                                "img" -> {
                                                    val dt = i.msg.split("||")
                                                    val msgitem = send(
                                                        msg_idx = i.msg_idx,
                                                        msg_type = 1,
                                                        send_type = i.send_type,
                                                        send_day = i.send_day,
                                                        send_time = i.send_time,
                                                        msg = "",
                                                        image_origin_url = dt[1],
                                                        image_resize_url = dt[0],
                                                        origin_width = dt[2].toInt(),
                                                        origin_height = dt[3].toInt()
                                                    )
                                                    msgidx = i.msg_idx
                                                    dataAdapter.addItem(msgitem)
                                                    mViewDataBinding.chatRecyclerView.scrollToPosition(
                                                        dataAdapter.getsize() - 1
                                                    )

                                                }
                                            }
                                        } else if (i.send_type == 1) {
                                            when (i.msg_type) {
                                                "msg" -> {
                                                    val msgitem = send(
                                                        msg_idx = i.msg_idx,
                                                        msg_type = 2,
                                                        send_type = i.send_type,
                                                        send_day = i.send_day,
                                                        send_time = i.send_time,
                                                        msg = i.msg,
                                                        image_origin_url = "",
                                                        image_resize_url = "",
                                                        origin_width = -1,
                                                        origin_height = -1
                                                    )
                                                    msgidx = i.msg_idx
                                                    dataAdapter.addItem(msgitem)
                                                    mViewDataBinding.chatRecyclerView.scrollToPosition(
                                                        dataAdapter.getsize() - 1
                                                    )
                                                }
                                                "img" -> {
                                                    val dt = i.msg.split("||")
                                                    val msgitem = send(
                                                        msg_idx = i.msg_idx,
                                                        msg_type = 3,
                                                        send_type = i.send_type,
                                                        send_day = i.send_day,
                                                        send_time = i.send_time,
                                                        msg = "",
                                                        image_origin_url = dt[1],
                                                        image_resize_url = dt[0],
                                                        origin_width = dt[2].toInt(),
                                                        origin_height = dt[3].toInt()
                                                    )
                                                    msgidx = i.msg_idx
                                                    dataAdapter.addItem(msgitem)
                                                    mViewDataBinding.chatRecyclerView.scrollToPosition(
                                                        dataAdapter.getsize() - 1
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }

                            }
                        }
                    })*/
            }
        }
        if (resultCode != -1) {
            when (resultCode) {
                4001 -> {
                    report_status = 1
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun Cameraimageupload() {
        chattingManager.instance.img(
            ChatActivity.chat_idx,
            imageReSize(photoUri!!, 700, imageFileName),
            completion = { responseStatus, origin, reize ->
                when (responseStatus) {
                    RESPONSE_STATUS.OKAY -> {
                        val `image` = JSONObject()
                        `image`.put("chat_idx", ChatActivity.chat_idx)
                        `image`.put("origin_url", origin)
                        `image`.put("resize_url", reize)
                        `image`.put("image_width", image_width)
                        `image`.put("image_height", image_height)
                        `image`.put("user_type", 0)

                        socket?.emit("chatImage", `image`)

                        val file = File(this.cacheDir, imageFileName)
                        if (file.exists()) {
                            file.delete()
                        }

                    }
                }
            })
    }

    fun imageupload() {
        chattingManager.instance.img(
            ChatActivity.chat_idx,
            imageReSize(photoList[startUploadImage]!!, 700, filenameList[startUploadImage]),
            completion = { responseStatus, origin, reize ->
                when (responseStatus) {
                    RESPONSE_STATUS.OKAY -> {
                        startUploadImage += 1
                        val `image` = JSONObject()
                        `image`.put("chat_idx", ChatActivity.chat_idx)
                        `image`.put("origin_url", origin)
                        `image`.put("resize_url", reize)
                        `image`.put("image_width", image_width)
                        `image`.put("image_height", image_height)
                        `image`.put("user_type", 0)

                        socket?.emit("chatImage", `image`)

                        val file = File(this.cacheDir, imageFileName)
                        if (file.exists()) {
                            file.delete()
                        }
                        if (startUploadImage < endUploadImage) {
                            imageupload()
                        }
                    }
                }
            })
    }

    private fun imageReSize(uri: Uri, resize: Int, fileName: String?): File {

        var resizeBitmap: Bitmap? = null

        var resizedBitmap: Bitmap? = null

        var RotationBitmap: Bitmap? = null

        var options: BitmapFactory.Options = BitmapFactory.Options();
        try {
            BitmapFactory.decodeStream(
                this.getContentResolver().openInputStream(uri),
                null,
                options
            ); // 1번

            var width: Int = options.outWidth;
            var height: Int = options.outHeight;
            var dwidth: Int = options.outWidth
            var dheight: Int = options.outHeight
            var samplesize: Int = 1;

            while (true) {//2번
                if (width / 2 < resize || height / 2 < resize)
                    break
                width /= 2
                height /= 2
                samplesize += 1
            }

            options.inSampleSize = samplesize
            var bitmap: Bitmap? = BitmapFactory.decodeStream(
                this.getContentResolver().openInputStream(uri),
                null,
                options
            ) //3번


            width = options.outWidth
            height = options.outHeight
            if (dwidth < dheight) {
                if (dwidth > 700) {
                    dwidth = 700
                    dheight = (dwidth.toFloat() / width.toFloat() * height).toInt()
                }
            } else if (dheight < dwidth) {
                if (dheight > 700) {
                    dheight = 700
                    dwidth = (dheight.toFloat() / height.toFloat() * width).toInt()
                }
            } else if (dheight == dwidth) {
                dwidth = 700
                dheight = 700
            }

            if (bitmap != null) {
                resizeBitmap = bitmap
            }
            var res: InputStream = contentResolver.openInputStream(uri)!!
            var exif: ExifInterface = ExifInterface(res)
            res.close()

            var orientation: Int = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )

            //사진 회전
            var rotateMatrix: Matrix = Matrix()
            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                rotateMatrix.postRotate(90f)
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                rotateMatrix.postRotate(180f)
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                rotateMatrix.postRotate(270f)
            }

            resizedBitmap = resizeBitmap?.let {
                Bitmap.createScaledBitmap(it, dwidth, dheight, true)
            }

            RotationBitmap = resizedBitmap?.let {
                Bitmap.createBitmap(
                    it,
                    0,
                    0,
                    it.width,
                    it.height,
                    rotateMatrix,
                    true
                )
            }

            if (RotationBitmap != null) {
                image_width = RotationBitmap.width
                image_height = RotationBitmap.height
            }


        } catch (e: FileNotFoundException) {
            //  e.printStackTrace();
        }

        val file = File(this.cacheDir, "/chat/$fileName")
        //file.createNewFile()
        if (file.exists()) {
            file.mkdirs()
        }
        try {

            val outStream = ByteArrayOutputStream()

            RotationBitmap!!.compress(Bitmap.CompressFormat.PNG, 100, outStream)
            val fo = FileOutputStream(file)
            fo.write(outStream.toByteArray())
            // remember close de FileOutput
            fo.flush()
            fo.close()
        } catch (e: Exception) {

        }
        return file
    }

    private fun selectCamera() {
        var permission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        if (permission != PackageManager.PERMISSION_GRANTED) { // 권한 없어서 요청
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                REQ_CAMERA_PERMISSION
            )
        } else { // 권한 있음
            var state = Environment.getExternalStorageState()
            if (TextUtils.equals(state, Environment.MEDIA_MOUNTED)) {
                var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE) //카메라 인텐트 생성
                intent.resolveActivity(this.packageManager)?.let {
                    var userphotoFile: File? = null
                    try {
                        userphotoFile = createImageFile()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                    userphotoFile?.let {
                        photoUri = FileProvider.getUriForFile(
                            this,
                            BuildConfig.APPLICATION_ID + ".provider",
                            userphotoFile
                        )
                        //urilist.add(photoUri!!)
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                        startActivityForResult(intent, CAMERA_IMAGE)
                    }
                }
            }
        }
    }

    private fun selectgallery() {
        var writePermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        var readPermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)

        if (writePermission == PackageManager.PERMISSION_DENIED || readPermission == PackageManager.PERMISSION_DENIED) { // 권한 없어서 요청
            ActivityCompat.requestPermissions(
                this as Activity,
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                REQ_GALLERY_PERMISSION
            )
        } else {

            // 이미지가 저장될 주소
            var storageDir = File(
                this.cacheDir, "/chat/"
            )
            if (!storageDir.exists()) {
                Log.v("알림", "storageDir 존재 x$storageDir")
                storageDir.mkdirs()
            }
            Log.v("알림", "storageDir 존재함$storageDir")

            var intent = Intent(Intent.ACTION_PICK)
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT

            startActivityForResult(intent, GALLERY_IMAGE)
        }

    }

    @Throws(IOException::class)
    private fun createImageFile(): File? {

        //fileNameList = ArrayList()
        // 이미지 파일 이름
        val timeStamp = SimpleDateFormat("HHmmss").format(Date())
        imageFileName = ""
        imageFileName = "chat" + "_" + timeStamp
        // 이미지가 저장될 주소
        var storageDir = File(
            this.cacheDir, "/chat/"
        )
        if (!storageDir.exists()) {
            Log.v("알림", "storageDir 존재 x$storageDir")
            storageDir.mkdirs()
        }
        Log.v("알림", "storageDir 존재함$storageDir")

        //빈 파일 생성
        storageDir = File(
            this.cacheDir, "/chat/$imageFileName"
        )
        if (!storageDir.exists()) {
            storageDir.createNewFile()
        }
        imagePath = storageDir.absolutePath
        //fileNameList.add(imageFileName)
        return storageDir
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.chat_ok_image -> {
                for (i in forbiddenlist) {
                    if (mViewDataBinding.chatEditText.text.toString().indexOf(i) != -1) {
                        val msgitem = send(
                            msg_idx = "",
                            msg_type = -2,
                            send_type = -1,
                            send_day = "",
                            send_time = "",
                            msg = "",
                            image_origin_url = "",
                            image_resize_url = "",
                            origin_width = -1,
                            origin_height = -1
                        )
                        dataAdapter.addItem(msgitem)
                        mViewDataBinding.chatRecyclerView.smoothScrollToPosition(dataAdapter.itemCount - 1)

                        mViewDataBinding.chatEditText.setText("")
                        return
                    }
                }
                val `object2` = JSONObject()
                `object2`.put("chat_idx", ChatActivity.chat_idx)
                `object2`.put("user_type", 0)
                `object2`.put("msg", mViewDataBinding.chatEditText.text.toString())

                socket?.emit("msg", object2)
                val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                val vibratorEffect = VibrationEffect.createOneShot(100, 10)
                vibrator.vibrate(vibratorEffect)
                mViewDataBinding.chatEditText.setText("")
            }
            R.id.relativeLayout3 -> {
                when (Entrytype) {
                    0 -> {
                    }
                    1 -> {
                    }
                }
            }

            R.id.chat_image_upload -> {
                val uploadFragment: ChatImageUploadFragment = ChatImageUploadFragment {
                    when (it) {
                        0 -> {
                            selectCamera()
                        }
                        1 -> {
                            selectgallery()
                        }
                    }
                }
                uploadFragment.show(supportFragmentManager, uploadFragment.tag)
            }
            R.id.chat_menu -> {
                val chatmenudialog: ChatDialogFragment = ChatDialogFragment() {
                    when (it) {
                        0 -> {
                            chattingManager.instance.out(ChatActivity.chat_idx, completion = { responseStatus ->
                                when (responseStatus) {
                                    RESPONSE_STATUS.OKAY -> {
                                        val intent = Intent()
                                        setResult(3002, intent)
                                        finish()
                                    }
                                }
                            })
                        }
                        1 -> {
                        }
                    }
                }
                chatmenudialog.show(supportFragmentManager, chatmenudialog.tag)
            }
            R.id.chat_fab -> {
                mViewDataBinding.chatRecyclerView.smoothScrollToPosition(dataAdapter.itemCount - 1)
            }
        }
    }
}