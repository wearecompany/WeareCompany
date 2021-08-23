package com.weare.wearecompany.ui.detail.model.reservation

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.weare.wearecompany.R
import com.weare.wearecompany.data.retrofit.dateil.expertReserveManager
import com.weare.wearecompany.databinding.ActivityReservationExpertBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.ui.container.ContainerActivity
import com.weare.wearecompany.ui.detail.photo.reservation.ReservationPhotoDialog
import com.weare.wearecompany.utils.RESPONSE_STATUS
import java.text.SimpleDateFormat
import java.util.*

class ReservationModelActivity: BaseActivity<ActivityReservationExpertBinding>(
    R.layout.activity_reservation_expert
), View.OnClickListener {

    private lateinit var postTimeZoneLayout: LinearLayout
    private lateinit var postTimeZoneText: TextView
    private var timezonetype = 0

    private var peoplecount = 1

    private lateinit var user_idx: String
    private lateinit var expert_user_idx: String
    private var expert_type:Int = -1
    private lateinit var expert_idx: String
    private lateinit var dateprice: String
    private var final_price = 0

    private var daylist: ArrayList<String> = ArrayList<String>()
    val korFotmatt = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)

    override fun onCreate() {
        val toobar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toobar)
        val ActionBar = supportActionBar!!
        ActionBar.setDisplayHomeAsUpEnabled(true)
        ActionBar.setDisplayShowTitleEnabled(false)

        user_idx = intent.getStringExtra("user_idx")!!
        expert_user_idx = intent.getStringExtra("expert_user_idx")!!
        expert_type = intent.getIntExtra("expert_type",0)!!
        expert_idx = intent.getStringExtra("expert_idx")!!
        dateprice = intent.getStringExtra("price")!!

        setup()

        mViewDataBinding.calendarView.setOnDayClickListener(object : OnDayClickListener {
            override fun onDayClick(eventDay: EventDay) {
                if (mViewDataBinding.calendarView.selectedDates.size < 6) {

                } else {
                    for (i in mViewDataBinding.calendarView.selectedDates) {
                        if (eventDay.calendar.time == i.time) {
                            return
                        }
                    }
                    eventDay.calendar.clear()
                }

            }
        })
    }

    private fun setup() {

        val min = Calendar.getInstance()
        min.add(Calendar.DAY_OF_MONTH, 0)

        val max = Calendar.getInstance()
        max.add(Calendar.DAY_OF_MONTH, +30)


        mViewDataBinding.calendarView.setMinimumDate(min)
        mViewDataBinding.calendarView.setMaximumDate(max)

        mViewDataBinding.expertPeopleLayout.visibility = View.GONE

        mViewDataBinding.expertPeopleMinus.setOnClickListener(this)
        mViewDataBinding.expertPeoplePlus.setOnClickListener(this)
        mViewDataBinding.expertOk.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.expert_people_plus -> {
                peoplecount += 1
                mViewDataBinding.expertPeople.text = peoplecount.toString()
            }
            R.id.expert_people_minus -> {
                if (peoplecount >= 1) {
                    peoplecount -= 1
                    mViewDataBinding.expertPeople.text = peoplecount.toString()
                }
            }

            R.id.expert_ok -> {
                daylist = ArrayList<String>()

                    if (mViewDataBinding.calendarView.selectedDates.size == 0) {
                        Toast.makeText(this,"날짜를 선택해주세요", Toast.LENGTH_SHORT).show()
                    } else if (mViewDataBinding.expertTime.text.toString() == "") {
                        Toast.makeText(this,"이용 시간을 입력해주세요", Toast.LENGTH_SHORT).show()
                    } else if (mViewDataBinding.expertTime.text.toString() == "0") {
                        Toast.makeText(this,"최소 이용 시간은 1시간입니다.", Toast.LENGTH_SHORT).show()
                    } else if (mViewDataBinding.expertTime.text.toString().toInt() > 12) {
                        Toast.makeText(this,"최대 이용 시간은 12시간입니다.", Toast.LENGTH_SHORT).show()
                    } else {

                        for (i in mViewDataBinding.calendarView.selectedDates) {
                            daylist.add(korFotmatt.format(i.time))
                        }
                        daylist.sort()

                        val modelDialog: ReservationModelDialog = ReservationModelDialog(daylist,mViewDataBinding.expertTime.text.toString().toInt(),mViewDataBinding.expertContents.text.toString()) {
                            when(it) {
                                1 -> {
                                    expertReserveManager.instance.reserve(user_idx,expert_user_idx,expert_type,expert_idx,daylist,mViewDataBinding.expertTime.text.toString().toInt(),mViewDataBinding.expertPeople.text.toString().toInt(),mViewDataBinding.expertContents.text.toString(),completion = { responseStatus ->
                                        when(responseStatus) {
                                            RESPONSE_STATUS.OKAY -> {
                                                var newIntent = Intent(this, ContainerActivity::class.java)
                                                newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                                newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                                newIntent.putExtra("reservation", 1)
                                                startActivity(newIntent)
                                            }
                                        }
                                    })
                                }
                            }
                        }
                        modelDialog.isCancelable = false
                        modelDialog.show(supportFragmentManager,modelDialog.tag)
                    }


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