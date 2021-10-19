package com.weare.wearecompany.ui.bottommenu.main.alarm

import android.content.Intent
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.notification.data.alarm
import com.weare.wearecompany.data.retrofit.bottomnav.main.MainManager
import com.weare.wearecompany.data.retrofit.bottomnav.main.alarmManager
import com.weare.wearecompany.databinding.ActivityAlarmBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.ui.bottommenu.estimate.progress.experthodel.progress.*
import com.weare.wearecompany.ui.bottommenu.estimate.progress.experthodel.refund.*
import com.weare.wearecompany.ui.bottommenu.estimate.receive.ReceiveRequestActivity
import com.weare.wearecompany.ui.bottommenu.estimate.receive.experthodel.ReceiveModelActivity
import com.weare.wearecompany.ui.bottommenu.estimate.receive.experthodel.ReceivePhotoActivity
import com.weare.wearecompany.ui.bottommenu.estimate.receive.experthodel.ReceiveStudioActivity
import com.weare.wearecompany.ui.bottommenu.estimate.receive.experthodel.ReceiveTripActivity
import com.weare.wearecompany.ui.bottommenu.estimate.send.SendRequestActivity
import com.weare.wearecompany.ui.bottommenu.estimate.send.SendStudioActivity
import com.weare.wearecompany.ui.bottommenu.estimate.send.experthodel.SendModelActivity
import com.weare.wearecompany.ui.bottommenu.estimate.send.experthodel.SendPhotoActivity
import com.weare.wearecompany.ui.bottommenu.estimate.send.experthodel.SendTripActivity
import com.weare.wearecompany.ui.bottommenu.mypage.request.progress.RefundOneClickActivity
import com.weare.wearecompany.ui.bottommenu.mypage.request.receive.ReceiveOneClickActivity
import com.weare.wearecompany.utils.ALARM
import com.weare.wearecompany.utils.RESPONSE_STATUS

class AlarmActivity: BaseActivity<ActivityAlarmBinding>(
    R.layout.activity_alarm
), View.OnClickListener {

    lateinit var user_idx:String
    private var alarm_idx = ""
    var delete_image_status = false
    lateinit var newIntent:Intent

    lateinit var dataAdapter:AlarmRecyclerViewAdapter

    override fun onCreate() {
        val toobar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toobar)
        val ActionBar = supportActionBar!!
        ActionBar.setDisplayHomeAsUpEnabled(true)
        ActionBar.setDisplayShowTitleEnabled(false)

        user_idx = MyApplication.prefs.getString("user_idx", "")
        alarm_idx = intent.getStringExtra("alarm_idx").toString()
        setup()
    }

    fun setup() {

        mViewDataBinding.alarmDelete.setOnClickListener(this)

        MainManager.instance.alarmlist(user_idx,complation = {responseStatus, arrayList ->

            when(responseStatus) {
                RESPONSE_STATUS.OKAY -> {
                        if (alarm_idx != "") {
                            for (i in arrayList) {
                                if (alarm_idx == i.idx) {
                                    MainManager.instance.alarmview(alarm_idx, complation = {responseStatus ->
                                        when(responseStatus) {
                                            RESPONSE_STATUS.OKAY -> {
                                                when(i.type) {
                                                    10 -> {
                                                        when(i.expert_type) {
                                                            0 -> {
                                                                val newIntent = Intent(this@AlarmActivity, SendStudioActivity::class.java)
                                                                newIntent.putExtra("reserve_idx", i.target_idx)
                                                                newIntent.putExtra("type", 0)
                                                                startActivityForResult(newIntent, 2000)
                                                                alarm_idx = ""
                                                            }
                                                            1 -> {
                                                                val newIntent = Intent(this@AlarmActivity, SendPhotoActivity::class.java)
                                                                newIntent.putExtra("reserve_idx", i.target_idx)
                                                                newIntent.putExtra("type", 0)
                                                                startActivityForResult(newIntent, 2000)
                                                                alarm_idx = ""
                                                            }
                                                            2 -> {
                                                                val newIntent = Intent(this@AlarmActivity, SendModelActivity::class.java)
                                                                newIntent.putExtra("reserve_idx", i.target_idx)
                                                                newIntent.putExtra("type", 0)
                                                                startActivityForResult(newIntent, 2000)
                                                                alarm_idx = ""
                                                            }
                                                            3 -> {
                                                                val newIntent = Intent(this@AlarmActivity, SendTripActivity::class.java)
                                                                newIntent.putExtra("reserve_idx", i.target_idx)
                                                                newIntent.putExtra("type", 0)
                                                                startActivityForResult(newIntent, 2000)
                                                                alarm_idx = ""
                                                            }
                                                        }
                                                    }
                                                    11 -> {
                                                        when(i.expert_type) {
                                                            0 -> {
                                                                val newIntent = Intent(this@AlarmActivity, ReceiveStudioActivity::class.java)
                                                                newIntent.putExtra("reserve_idx", i.target_idx)
                                                                newIntent.putExtra("type", 1)
                                                                startActivityForResult(newIntent, 2000)
                                                                alarm_idx = ""
                                                            }
                                                            1 -> {
                                                                val newIntent = Intent(this@AlarmActivity, ReceivePhotoActivity::class.java)
                                                                newIntent.putExtra("reserve_idx", i.target_idx)
                                                                newIntent.putExtra("type", 1)
                                                                startActivityForResult(newIntent, 2000)
                                                                alarm_idx = ""
                                                            }
                                                            2 -> {
                                                                val newIntent = Intent(this@AlarmActivity, ReceiveModelActivity::class.java)
                                                                newIntent.putExtra("reserve_idx", i.target_idx)
                                                                newIntent.putExtra("type", 1)
                                                                startActivityForResult(newIntent, 2000)
                                                                alarm_idx = ""
                                                            }
                                                            3 -> {
                                                                val newIntent = Intent(this@AlarmActivity, ReceiveTripActivity::class.java)
                                                                newIntent.putExtra("reserve_idx", i.target_idx)
                                                                newIntent.putExtra("type", 1)
                                                                startActivityForResult(newIntent, 2000)
                                                                alarm_idx = ""
                                                            }
                                                        }
                                                    }
                                                    12 -> {
                                                        when(i.expert_type) {
                                                            0 -> {
                                                                val newIntent = Intent(this@AlarmActivity, ProgressStudioActivity::class.java)
                                                                newIntent.putExtra("reserve_idx", i.target_idx)
                                                                startActivityForResult(newIntent, 2000)
                                                                alarm_idx = ""
                                                            }
                                                            1 -> {
                                                                val newIntent = Intent(this@AlarmActivity, ProgressPhotoActivity::class.java)
                                                                newIntent.putExtra("reserve_idx", i.target_idx)
                                                                startActivityForResult(newIntent, 2000)
                                                                alarm_idx = ""
                                                            }
                                                            2 -> {
                                                                val newIntent = Intent(this@AlarmActivity, ProgressModelActivity::class.java)
                                                                newIntent.putExtra("reserve_idx", i.target_idx)
                                                                startActivityForResult(newIntent, 2000)
                                                                alarm_idx = ""
                                                            }
                                                            3 -> {
                                                                val newIntent = Intent(this@AlarmActivity, ProgressTripActivity::class.java)
                                                                newIntent.putExtra("reserve_idx", i.target_idx)
                                                                startActivityForResult(newIntent, 2000)
                                                                alarm_idx = ""
                                                            }
                                                        }
                                                    }
                                                    13 -> {
                                                        when(i.expert_type) {
                                                            0 -> {
                                                                val newIntent = Intent(this@AlarmActivity, ProgressStudioActivity::class.java)
                                                                newIntent.putExtra("reserve_idx", i.target_idx)
                                                                newIntent.putExtra("chatbool", 0)
                                                                startActivityForResult(newIntent, 2000)
                                                                alarm_idx = ""
                                                            }
                                                            1 -> {
                                                                val newIntent = Intent(this@AlarmActivity, ProgressPhotoActivity::class.java)
                                                                newIntent.putExtra("reserve_idx", i.target_idx)
                                                                newIntent.putExtra("chatbool", 0)
                                                                startActivityForResult(newIntent, 2000)
                                                                alarm_idx = ""
                                                            }
                                                            2 -> {
                                                                val newIntent = Intent(this@AlarmActivity, ProgressModelActivity::class.java)
                                                                newIntent.putExtra("reserve_idx", i.target_idx)
                                                                newIntent.putExtra("chatbool", 0)
                                                                startActivityForResult(newIntent, 2000)
                                                                alarm_idx = ""
                                                            }
                                                            3 -> {
                                                                val newIntent = Intent(this@AlarmActivity, ProgressTripActivity::class.java)
                                                                newIntent.putExtra("reserve_idx", i.target_idx)
                                                                newIntent.putExtra("chatbool", 0)
                                                                startActivityForResult(newIntent, 2000)
                                                                alarm_idx = ""
                                                            }
                                                        }
                                                    }
                                                    14 -> {
                                                        when(i.expert_type) {
                                                            0 -> {
                                                                val newIntent = Intent(this@AlarmActivity, RefundStudioActivity::class.java)
                                                                newIntent.putExtra("reserve_idx", i.target_idx)
                                                                startActivityForResult(newIntent, 2000)
                                                                alarm_idx = ""
                                                            }
                                                            1 -> {
                                                                val newIntent = Intent(this@AlarmActivity, RefundPhotoActivity::class.java)
                                                                newIntent.putExtra("reserve_idx", i.target_idx)
                                                                startActivityForResult(newIntent, 2000)
                                                                alarm_idx = ""
                                                            }
                                                            2 -> {
                                                                val newIntent = Intent(this@AlarmActivity, RefundModelActivity::class.java)
                                                                newIntent.putExtra("reserve_idx", i.target_idx)
                                                                startActivityForResult(newIntent, 2000)
                                                                alarm_idx = ""
                                                            }
                                                            3 -> {
                                                                val newIntent = Intent(this@AlarmActivity, RefundTripActivity::class.java)
                                                                newIntent.putExtra("reserve_idx", i.target_idx)
                                                                startActivityForResult(newIntent, 2000)
                                                                alarm_idx = ""
                                                            }
                                                        }
                                                    }
                                                    15 -> {
                                                        when(i.expert_type) {
                                                            0 -> {
                                                                val newIntent = Intent(this@AlarmActivity, RefundStudioActivity::class.java)
                                                                newIntent.putExtra("reserve_idx", i.target_idx)
                                                                startActivityForResult(newIntent, 2000)
                                                                alarm_idx = ""
                                                            }
                                                            1 -> {
                                                                val newIntent = Intent(this@AlarmActivity, RefundPhotoActivity::class.java)
                                                                newIntent.putExtra("reserve_idx", i.target_idx)
                                                                startActivityForResult(newIntent, 2000)
                                                                alarm_idx = ""
                                                            }
                                                            2 -> {
                                                                val newIntent = Intent(this@AlarmActivity, RefundModelActivity::class.java)
                                                                newIntent.putExtra("reserve_idx", i.target_idx)
                                                                startActivityForResult(newIntent, 2000)
                                                                alarm_idx = ""
                                                            }
                                                            3 -> {
                                                                val newIntent = Intent(this@AlarmActivity, RefundTripActivity::class.java)
                                                                newIntent.putExtra("reserve_idx", i.target_idx)
                                                                startActivityForResult(newIntent, 2000)
                                                                alarm_idx = ""
                                                            }
                                                        }
                                                    }

                                                    21 -> {
                                                        val newIntent = Intent(this@AlarmActivity, ReceiveOneClickActivity::class.java)
                                                        newIntent.putExtra("reserve_idx", i.target_idx)
                                                        newIntent.putExtra("type", 0)
                                                        startActivityForResult(newIntent, 2000)
                                                        alarm_idx = ""
                                                    }
                                                    25 -> {
                                                        val newIntent = Intent(this@AlarmActivity, RefundOneClickActivity::class.java)
                                                        newIntent.putExtra("reserve_idx", i.target_idx)
                                                        newIntent.putExtra("type", 0)
                                                        startActivityForResult(newIntent, 2000)
                                                        alarm_idx = ""
                                                    }
                                                }
                                            }
                                        }
                                    })

                                }
                            }
                        }
                        dataAdapter = AlarmRecyclerViewAdapter(arrayList,delete_image_status)
                        mViewDataBinding.alarmRecyclerView.layoutManager =
                            LinearLayoutManager(
                                this,
                                LinearLayoutManager.VERTICAL, false
                            )
                        mViewDataBinding.alarmRecyclerView.adapter = dataAdapter

                        dataAdapter.setItemClickListener(object : AlarmRecyclerViewAdapter.OnItemClickListener {
                            override fun onClick(v: View, position: Int, Item: alarm) {

                                MainManager.instance.alarmview(Item.idx, complation = {responseStatus ->
                                    when(responseStatus) {
                                        RESPONSE_STATUS.OKAY -> {
                                            when(Item.type) {
                                                10 -> {
                                                    when(Item.expert_type) {
                                                        0 -> {
                                                            val newIntent = Intent(this@AlarmActivity, SendStudioActivity::class.java)
                                                            newIntent.putExtra("reserve_idx", Item.target_idx)
                                                            newIntent.putExtra("type", 0)
                                                            startActivityForResult(newIntent, 2000)
                                                        }
                                                        1 -> {
                                                            val newIntent = Intent(this@AlarmActivity, SendPhotoActivity::class.java)
                                                            newIntent.putExtra("reserve_idx", Item.target_idx)
                                                            newIntent.putExtra("type", 0)
                                                            startActivityForResult(newIntent, 2000)
                                                        }
                                                        2 -> {
                                                            val newIntent = Intent(this@AlarmActivity, SendModelActivity::class.java)
                                                            newIntent.putExtra("reserve_idx", Item.target_idx)
                                                            newIntent.putExtra("type", 0)
                                                            startActivityForResult(newIntent, 2000)
                                                        }
                                                        3 -> {
                                                            val newIntent = Intent(this@AlarmActivity, SendTripActivity::class.java)
                                                            newIntent.putExtra("reserve_idx", Item.target_idx)
                                                            newIntent.putExtra("type", 0)
                                                            startActivityForResult(newIntent, 2000)
                                                        }
                                                    }
                                                }
                                                11 -> {
                                                    when(Item.expert_type) {
                                                        0 -> {
                                                            alarmManager.instance.receivestudio(Item.target_idx, completion = { responseStatus ->
                                                                when(responseStatus) {
                                                                    ALARM.OKAY -> {
                                                                        val newIntent = Intent(this@AlarmActivity, ReceiveStudioActivity::class.java)
                                                                        newIntent.putExtra("reserve_idx", Item.target_idx)
                                                                        newIntent.putExtra("type", 1)
                                                                        startActivityForResult(newIntent, 2000)
                                                                    }
                                                                    ALARM.NOT_CONTENT -> {
                                                                        var LastDialog:AlarmLastDialog = AlarmLastDialog{
                                                                            when(it) {
                                                                                1 -> {
                                                                                    MainManager.instance.alarmdelete(Item.idx,complation = {responseStatus ->
                                                                                        when(responseStatus){
                                                                                            RESPONSE_STATUS.OKAY -> {
                                                                                                dataAdapter.removeItem(position)
                                                                                            }
                                                                                        }
                                                                                    })
                                                                                }
                                                                            }
                                                                        }
                                                                        LastDialog.isCancelable = false
                                                                        LastDialog.show(supportFragmentManager, LastDialog.tag)
                                                                    }
                                                                }
                                                            })
                                                        }
                                                        1,2,3 -> {
                                                            alarmManager.instance.receiveexpert(Item.target_idx, completion = { responseStatus ->
                                                                when(responseStatus) {
                                                                    ALARM.OKAY -> {
                                                                        when(Item.expert_type) {
                                                                            1 -> newIntent = Intent(this@AlarmActivity, ReceivePhotoActivity::class.java)
                                                                            2 -> newIntent = Intent(this@AlarmActivity, ReceiveModelActivity::class.java)
                                                                            3 -> newIntent = Intent(this@AlarmActivity, ReceiveTripActivity::class.java)
                                                                        }
                                                                        newIntent.putExtra("reserve_idx", Item.target_idx)
                                                                        newIntent.putExtra("type", 1)
                                                                        startActivityForResult(newIntent, 2000)
                                                                    }
                                                                    ALARM.NOT_CONTENT -> {
                                                                        var LastDialog:AlarmLastDialog = AlarmLastDialog{
                                                                            when(it) {
                                                                                1 -> {
                                                                                    MainManager.instance.alarmdelete(Item.idx,complation = {responseStatus ->
                                                                                        when(responseStatus){
                                                                                            RESPONSE_STATUS.OKAY -> {
                                                                                                dataAdapter.removeItem(position)
                                                                                            }
                                                                                        }
                                                                                    })
                                                                                }
                                                                            }
                                                                        }
                                                                        LastDialog.isCancelable = false
                                                                        LastDialog.show(supportFragmentManager, LastDialog.tag)
                                                                    }

                                                                }
                                                            })

                                                        }
                                                    }
                                                }
                                                12 -> {
                                                    when(Item.expert_type) {
                                                        0 -> {
                                                            val newIntent = Intent(this@AlarmActivity, ProgressStudioActivity::class.java)
                                                            newIntent.putExtra("reserve_idx", Item.target_idx)
                                                            startActivityForResult(newIntent, 2000)
                                                        }
                                                        1 -> {
                                                            val newIntent = Intent(this@AlarmActivity, ProgressPhotoActivity::class.java)
                                                            newIntent.putExtra("reserve_idx", Item.target_idx)
                                                            startActivityForResult(newIntent, 2000)
                                                        }
                                                        2 -> {
                                                            val newIntent = Intent(this@AlarmActivity, ProgressModelActivity::class.java)
                                                            newIntent.putExtra("reserve_idx", Item.target_idx)
                                                            startActivityForResult(newIntent, 2000)
                                                        }
                                                        3 -> {
                                                            val newIntent = Intent(this@AlarmActivity, ProgressTripActivity::class.java)
                                                            newIntent.putExtra("reserve_idx", Item.target_idx)
                                                            startActivityForResult(newIntent, 2000)
                                                        }
                                                    }
                                                }
                                                13 -> {
                                                    when(Item.expert_type) {
                                                        0 -> {
                                                            val newIntent = Intent(this@AlarmActivity, ProgressStudioActivity::class.java)
                                                            newIntent.putExtra("reserve_idx", Item.target_idx)
                                                            newIntent.putExtra("chatbool", 0)
                                                            startActivityForResult(newIntent, 2000)
                                                        }
                                                        1 -> {
                                                            val newIntent = Intent(this@AlarmActivity, ProgressPhotoActivity::class.java)
                                                            newIntent.putExtra("reserve_idx", Item.target_idx)
                                                            newIntent.putExtra("chatbool", 0)
                                                            startActivityForResult(newIntent, 2000)
                                                        }
                                                        2 -> {
                                                            val newIntent = Intent(this@AlarmActivity, ProgressModelActivity::class.java)
                                                            newIntent.putExtra("reserve_idx", Item.target_idx)
                                                            newIntent.putExtra("chatbool", 0)
                                                            startActivityForResult(newIntent, 2000)
                                                        }
                                                        3 -> {
                                                            val newIntent = Intent(this@AlarmActivity, ProgressTripActivity::class.java)
                                                            newIntent.putExtra("reserve_idx", Item.target_idx)
                                                            newIntent.putExtra("chatbool", 0)
                                                            startActivityForResult(newIntent, 2000)
                                                        }
                                                    }
                                                }
                                                14 -> {
                                                    when(Item.expert_type) {
                                                        0 -> {
                                                            val newIntent = Intent(this@AlarmActivity, RefundStudioActivity::class.java)
                                                            newIntent.putExtra("reserve_idx", Item.target_idx)
                                                            startActivityForResult(newIntent, 2000)
                                                        }
                                                        1 -> {
                                                            val newIntent = Intent(this@AlarmActivity, RefundPhotoActivity::class.java)
                                                            newIntent.putExtra("reserve_idx", Item.target_idx)
                                                            startActivityForResult(newIntent, 2000)
                                                        }
                                                        2 -> {
                                                            val newIntent = Intent(this@AlarmActivity, RefundModelActivity::class.java)
                                                            newIntent.putExtra("reserve_idx", Item.target_idx)
                                                            startActivityForResult(newIntent, 2000)
                                                        }
                                                        3 -> {
                                                            val newIntent = Intent(this@AlarmActivity, RefundTripActivity::class.java)
                                                            newIntent.putExtra("reserve_idx", Item.target_idx)
                                                            startActivityForResult(newIntent, 2000)
                                                        }
                                                    }
                                                }
                                                15 -> {
                                                    when(Item.expert_type) {
                                                        0 -> {

                                                            alarmManager.instance.refundstudio(Item.target_idx, completion = { responseStatus ->
                                                                when(responseStatus) {
                                                                    ALARM.OKAY -> {
                                                                        val newIntent = Intent(this@AlarmActivity, RefundStudioActivity::class.java)
                                                                        newIntent.putExtra("reserve_idx", Item.target_idx)
                                                                        startActivityForResult(newIntent, 2000)
                                                                    }
                                                                    ALARM.NOT_CONTENT -> {
                                                                        var LastDialog:AlarmLastDialog = AlarmLastDialog{
                                                                            when(it) {
                                                                                1 -> {
                                                                                    MainManager.instance.alarmdelete(Item.idx,complation = {responseStatus ->
                                                                                        when(responseStatus){
                                                                                            RESPONSE_STATUS.OKAY -> {
                                                                                                dataAdapter.removeItem(position)
                                                                                            }
                                                                                        }
                                                                                    })
                                                                                }
                                                                            }
                                                                        }
                                                                        LastDialog.isCancelable = false
                                                                        LastDialog.show(supportFragmentManager, LastDialog.tag)
                                                                    }
                                                                }
                                                            })
                                                        }
                                                        1,2,3 -> {
                                                            alarmManager.instance.refundexpert(Item.target_idx, completion = { responseStatus ->
                                                                when(responseStatus) {
                                                                    ALARM.OKAY -> {
                                                                        when(Item.expert_type) {
                                                                            1 -> newIntent = Intent(this@AlarmActivity, RefundPhotoActivity::class.java)
                                                                            2 -> newIntent = Intent(this@AlarmActivity, RefundModelActivity::class.java)
                                                                            3 -> newIntent = Intent(this@AlarmActivity, RefundTripActivity::class.java)
                                                                        }
                                                                        newIntent.putExtra("reserve_idx", Item.target_idx)
                                                                        newIntent.putExtra("type", 1)
                                                                        startActivityForResult(newIntent, 2000)
                                                                    }
                                                                    ALARM.NOT_CONTENT -> {
                                                                        var LastDialog:AlarmLastDialog = AlarmLastDialog{
                                                                            when(it) {
                                                                                1 -> {
                                                                                    MainManager.instance.alarmdelete(Item.idx,complation = {responseStatus ->
                                                                                        when(responseStatus){
                                                                                            RESPONSE_STATUS.OKAY -> {
                                                                                                dataAdapter.removeItem(position)
                                                                                            }
                                                                                        }
                                                                                    })
                                                                                }
                                                                            }
                                                                        }
                                                                        LastDialog.isCancelable = false
                                                                        LastDialog.show(supportFragmentManager, LastDialog.tag)
                                                                    }

                                                                }
                                                            })
                                                        }
                                                    }
                                                }
                                                21 -> {
                                                    val newIntent = Intent(this@AlarmActivity, ReceiveOneClickActivity::class.java)
                                                    newIntent.putExtra("reserve_idx", Item.target_idx)
                                                    newIntent.putExtra("type", 0)
                                                    startActivityForResult(newIntent, 2000)
                                                    alarm_idx = ""
                                                }

                                                25 -> {
                                                    val newIntent = Intent(this@AlarmActivity, RefundOneClickActivity::class.java)
                                                    newIntent.putExtra("reserve_idx", Item.target_idx)
                                                    newIntent.putExtra("type", 1)
                                                    startActivityForResult(newIntent, 2000)
                                                    alarm_idx = ""
                                                }
                                            }
                                        }
                                    }
                                })

                            }
                        })

                        dataAdapter.setItemClickListener(object : AlarmRecyclerViewAdapter.OnDeleteClickListener{
                            override fun onClick(v: View, position: Int, Item: alarm) {
                                MainManager.instance.alarmdelete(Item.idx,complation = {responseStatus ->
                                    when(responseStatus){
                                        RESPONSE_STATUS.OKAY -> {
                                            dataAdapter.removeItem(position)
                                        }
                                    }
                                })
                            }
                        })

                }
                RESPONSE_STATUS.NO_CONTENT -> {
                    mViewDataBinding.alarmRecyclerView.visibility = View.GONE
                    mViewDataBinding.alarmNotLayout.visibility = View.VISIBLE
                }
            }
        })
    }

    fun notalarm(item : alarm) {

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.alarm_delete -> {
                if (!delete_image_status) {
                    mViewDataBinding.alarmDeleteImage.setImageResource(R.drawable.check_black)
                    delete_image_status = true
                    setup()
                } else {
                    mViewDataBinding.alarmDeleteImage.setImageResource(R.drawable.alarm_delete)
                    delete_image_status = false
                    setup()
                }

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            2000 -> {
                setup()
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}