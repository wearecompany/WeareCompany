package com.weare.wearecompany.ui.detail.rent.reservation

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.weare.wearecompany.R
import com.weare.wearecompany.data.retrofit.dateil.rentDateilManager
import com.weare.wearecompany.databinding.ActivityReservationRentBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.ui.container.ContainerActivity
import com.weare.wearecompany.utils.RESPONSE_STATUS
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ReservationRentActivity:BaseActivity<ActivityReservationRentBinding>(
    R.layout.activity_reservation_rent
),View.OnClickListener {

    private var startday:String = ""
    private var startDate : Date = Date()
    private var endday:String = ""
    private var endDate : Date = Date()
    private var startTimeCheck = false
    private var EndTimeCheck = false


    private  var allDay : Long = -1

    private lateinit var user_idx: String
    private lateinit var expert_user_idx: String
    private lateinit var expert_idx: String
    private var reserve_day: Int = -1
    private var daylist:ArrayList<String> = ArrayList<String>()
    val checkFotmatt = SimpleDateFormat("yyyyMMdd", Locale.KOREA)
    private var timeCheck = false

    override fun onCreate() {

        val toobar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toobar)
        val ActionBar = supportActionBar!!
        ActionBar.setDisplayHomeAsUpEnabled(true)
        ActionBar.setDisplayShowTitleEnabled(false)

        setup()
    }

    private fun setup() {

        user_idx = intent.getStringExtra("user_idx").toString()
        expert_user_idx = intent.getStringExtra("expert_user_idx").toString()
        expert_idx = intent.getStringExtra("expert_idx").toString()

        mViewDataBinding.startDay.setOnClickListener(this)
        mViewDataBinding.endDay.setOnClickListener(this)
        mViewDataBinding.rentReservationOk.setOnClickListener(this)
        mViewDataBinding.dayOrtimeOk.setOnClickListener(this)

        mViewDataBinding.dayOrtimeOk.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                buttonView.buttonTintList = ColorStateList.valueOf(Color.parseColor("#6d34f3"))
                mViewDataBinding.notDay.visibility = View.VISIBLE
            } else {
                buttonView.buttonTintList = ColorStateList.valueOf(Color.parseColor("#cbcbcb"))
                mViewDataBinding.notDay.visibility = View.GONE
            }

        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.start_day -> {
                startday = mViewDataBinding.startDay.text.toString()
                endday = mViewDataBinding.endDay.text.toString()
                val calendarDialog: RentCalendarDialog = RentCalendarDialog(0,startday,endday, itemClick = {st,tw ->
                    mViewDataBinding.startDay.setText(st)
                    startTimeCheck = true
                    startDate = Date()
                    startDate = tw
                    if (startTimeCheck && EndTimeCheck) {
                        allDay = (endDate.time - startDate.time) / (24*60*60*1000)
                        mViewDataBinding.rentDay.text = allDay.toString()
                    }
                })
                calendarDialog.isCancelable = false
                calendarDialog.show(supportFragmentManager,calendarDialog.tag)
            }
            R.id.end_day -> {
                startday = mViewDataBinding.startDay.text.toString()
                endday = mViewDataBinding.endDay.text.toString()
                if (startday == "선택") {
                    Toast.makeText(this, "시작일 먼저 선택해주세요", Toast.LENGTH_SHORT).show()
                } else {
                    val calendarDialog: RentCalendarDialog = RentCalendarDialog(1,startday,endday,itemClick = {st,tw ->
                        mViewDataBinding.endDay.text = st
                        EndTimeCheck = true
                        endDate = Date()
                        endDate = tw
                        if (startTimeCheck && EndTimeCheck) {
                            allDay = (endDate.time - startDate.time) / (24*60*60*1000)
                            mViewDataBinding.rentDay.text = allDay.toString()
                        }
                    })
                    calendarDialog.isCancelable = false
                    calendarDialog.show(supportFragmentManager,calendarDialog.tag)
                }
            }
            R.id.dayOrtime_ok -> {
                if (timeCheck == false) {
                    timeCheck = true
                } else if (timeCheck == true) {
                    timeCheck = false
                }
            }
            R.id.rent_reservation_ok -> {
                daylist = ArrayList<String>()

                if (!mViewDataBinding.dayOrtimeOk.isChecked) {
                    if (mViewDataBinding.startDay.text.toString() == "" || mViewDataBinding.endDay.text.toString() == "") {
                        Toast.makeText(this, "날짜를 선택해주세요", Toast.LENGTH_SHORT).show()
                    }  else {
                        daylist.add( mViewDataBinding.startDay.text.toString())
                        daylist.add( mViewDataBinding.endDay.text.toString())

                        val checkDialog:RentReservationDialog = RentReservationDialog(mViewDataBinding.dayOrtimeOk.isChecked,daylist,mViewDataBinding.rentDay.text.toString().toInt(),mViewDataBinding.rentReserveContents.text.toString()) {
                            when(it) {
                                1 -> {
                                    rentDateilManager.instance.reserve(user_idx,expert_user_idx,expert_idx,mViewDataBinding.dayOrtimeOk.isChecked,daylist,mViewDataBinding.rentDay.text.toString().toInt(),mViewDataBinding.rentReserveContents.text.toString(),completion = {responseStatus ->
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
                        checkDialog.isCancelable = false
                        checkDialog.show(supportFragmentManager,checkDialog.tag)
                    }
                } else {
                    reserve_day = 0

                    val checkDialog:RentReservationDialog = RentReservationDialog(mViewDataBinding.dayOrtimeOk.isChecked,daylist,reserve_day,mViewDataBinding.rentReserveContents.text.toString()) {
                        when(it) {
                            1 -> {
                                rentDateilManager.instance.reserve(user_idx,expert_user_idx,expert_idx,mViewDataBinding.dayOrtimeOk.isChecked,daylist,reserve_day,mViewDataBinding.rentReserveContents.text.toString(),completion = {responseStatus ->
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
                    checkDialog.isCancelable = false
                    checkDialog.show(supportFragmentManager,checkDialog.tag)
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