package com.weare.wearecompany.ui.detail.studio.reservation


import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.weare.wearecompany.R
import com.weare.wearecompany.data.hotpick.data.room
import com.weare.wearecompany.data.retrofit.dateil.expertReserveManager
import com.weare.wearecompany.data.retrofit.dateil.studioReserveManager
import com.weare.wearecompany.databinding.ActivityReservationStudioBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.ui.container.ContainerActivity
import com.weare.wearecompany.utils.RESPONSE_STATUS
import java.text.SimpleDateFormat
import java.util.*


class ReservationStudioActivity : BaseActivity<ActivityReservationStudioBinding>(
    R.layout.activity_reservation_studio
), View.OnClickListener {

    private var clickdaylist: MutableList<EventDay> = ArrayList()
    private var peoplecount = 1
    private var timezonetype = 0
    private lateinit var postTimeZoneLayout: LinearLayout
    private lateinit var postTimeZoneText: TextView

    private lateinit var thisday: String
    private lateinit var today: String

    private lateinit var user_idx: String
    private lateinit var expert_user_idx: String
    private lateinit var expert_idx: String
    private lateinit var room_idx: String
    private lateinit var room_price: String
    private lateinit var room_name: String
    private var click_room = -1
    private var final_price = 0

    private lateinit var roomAdapter: ReservationStrudioRecyclerViewAdapter
    private lateinit var datalist: ArrayList<room>
    private var daylist: ArrayList<String> = ArrayList<String>()
    val korFotmatt = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
    override fun onCreate() {


        val toobar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toobar)
        val ActionBar = supportActionBar!!
        ActionBar.setDisplayHomeAsUpEnabled(true)
        ActionBar.setDisplayShowTitleEnabled(false)

        datalist = intent.getParcelableArrayListExtra("roomdata")!!
        user_idx = intent.getStringExtra("user_idx")!!
        expert_user_idx = intent.getStringExtra("expert_user_idx")!!
        expert_idx = intent.getStringExtra("expert_idx")!!

        setup()

        roomAdapter = ReservationStrudioRecyclerViewAdapter(datalist)
        mViewDataBinding.studioReservationRoomRecyclerview.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL, false
        )
        mViewDataBinding.studioReservationRoomRecyclerview.adapter = roomAdapter

        roomAdapter.setItemClickListener(object :
            ReservationStrudioRecyclerViewAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int, Item: room) {
                room_idx = ""
                room_price = ""
                room_name = ""

                room_idx = Item.room_idx
                room_price = Item.price
                room_name = Item.name
                click_room = position
            }

        })

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

    fun setup() {

        //val day = Calendar.getInstance()
        //thisday = korFotmatt.format(day.time)

        val min = Calendar.getInstance()
        min.add(Calendar.DAY_OF_MONTH, 0)

        val max = Calendar.getInstance()
        max.add(Calendar.DAY_OF_MONTH, +30)

        mViewDataBinding.calendarView.setMinimumDate(min)
        mViewDataBinding.calendarView.setMaximumDate(max)

        //day.add(Calendar.MONTH, 1)
        //today = korFotmatt.format(day.time)

        mViewDataBinding.reservationOk.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.reservation_ok -> {
                daylist = ArrayList<String>()

                    if (click_room == -1) {
                        Toast.makeText(this,"룸을 먼저 선택해주세요",Toast.LENGTH_SHORT).show()
                    } else if(mViewDataBinding.calendarView.selectedDates.size == 0){
                        Toast.makeText(this,"날짜를 선택해주세요",Toast.LENGTH_SHORT).show()
                    } else if(mViewDataBinding.reserveContents.text.toString() == ""){
                        Toast.makeText(this,"문의사항을 입력해주세요.",Toast.LENGTH_SHORT).show()
                    } else {

                        for (i in mViewDataBinding.calendarView.selectedDates) {
                            daylist.add(korFotmatt.format(i.time))
                        }
                        daylist.sort()

                        val studioDialog: ReservationStudioDialog = ReservationStudioDialog(room_name,daylist,mViewDataBinding.reserveContents.text.toString()){
                            when(it) {
                                1 -> {
                                    studioReserveManager.instance.reserve(user_idx,expert_user_idx,expert_idx,room_idx,daylist,mViewDataBinding.reserveContents.text.toString(),completion = { responseStatus ->
                                        when(responseStatus) {
                                            RESPONSE_STATUS.OKAY -> {
                                                var newIntent = Intent(this, ContainerActivity::class.java)
                                                newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                                newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                                newIntent.putExtra("reservation", 1)
                                                startActivityForResult(newIntent, 4000)
                                            }
                                        }
                                    })
                                }
                            }
                        }
                        studioDialog.isCancelable = false
                        studioDialog.show(supportFragmentManager,studioDialog.tag)
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