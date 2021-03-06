package com.weare.wearecompany.ui.detail.trip.reservation

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

class ReservationTripActivity: BaseActivity<ActivityReservationExpertBinding>(
    R.layout.activity_reservation_expert
), View.OnClickListener {

    private lateinit var postTimeZoneLayout: LinearLayout
    private lateinit var postTimeZoneText: TextView
    private var timezonetype = 0

    private var click_room = -1
    private var peoplecount = 1

    private lateinit var user_idx: String
    private lateinit var expert_user_idx: String
    private var expert_type:Int = -1
    private lateinit var dateprice: String
    private lateinit var expert_idx: String
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

        mViewDataBinding.expertPeopleMinus.setOnClickListener(this)
        mViewDataBinding.expertPeoplePlus.setOnClickListener(this)
        mViewDataBinding.expertOk.setOnClickListener(this)

    }

    private fun timezone_on(layout: LinearLayout, text: TextView, clicknum: Int) {
        timezonetype = clicknum
        layout.setBackgroundResource(R.drawable.reservation_studio_timezone_on)
        text.setTextColor(Color.parseColor("#ffffff"))
    }

    private fun timezone_off(layout: LinearLayout?, text: TextView?) {
        layout?.setBackgroundResource(R.drawable.reservation_studio_timezone_off)
        text?.setTextColor(Color.parseColor("#545454"))
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
                        Toast.makeText(this,"????????? ??????????????????", Toast.LENGTH_SHORT).show()
                    } else if (mViewDataBinding.expertTime.text.toString() == "") {
                        Toast.makeText(this,"?????? ????????? ??????????????????", Toast.LENGTH_SHORT).show()
                    } else if (mViewDataBinding.expertTime.text.toString() == "0") {
                        Toast.makeText(this,"?????? ?????? ????????? 1???????????????.", Toast.LENGTH_SHORT).show()
                    } else if (mViewDataBinding.expertTime.text.toString().toInt() > 12) {
                        Toast.makeText(this,"?????? ?????? ????????? 12???????????????.", Toast.LENGTH_SHORT).show()
                    } else {

                        for (i in mViewDataBinding.calendarView.selectedDates) {
                            daylist.add(korFotmatt.format(i.time))
                        }
                        daylist.sort()

                        val tripDialog: ReservationTripDialog = ReservationTripDialog(daylist,mViewDataBinding.expertTime.text.toString().toInt(), mViewDataBinding.expertPeople.text.toString().toInt(),mViewDataBinding.expertContents.text.toString()) {
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
                        tripDialog.isCancelable = false
                        tripDialog.show(supportFragmentManager,tripDialog.tag)
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