package com.weare.wearecompany.ui.detail.rent.reservation

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import kotlinx.android.synthetic.main.calendar_dialog.view.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class RentCalendarDialog(
    val type: Int,
    val stdata: String,
    val enddata: String,
    val itemClick: (String,Date) -> Unit
) : DialogFragment() {

    val windowManager = MyApplication.instance.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = windowManager.defaultDisplay
    val size = Point()

    var afterDate : String = ""
    var checkDate : String = ""
    val korFotmatt = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
    val checkFotmatt = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
    lateinit var stTime:Date
    lateinit var enTime:Date
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.calendar_dialog, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        display.getSize(size)

        val min = Calendar.getInstance()
        min.add(Calendar.DAY_OF_MONTH, 0)

        val max = Calendar.getInstance()
        max.add(Calendar.DAY_OF_MONTH, +30)

        view.calendarView.setMinimumDate(min)
        view.calendarView.setMaximumDate(max)

        if (type == 0) {
            if (enddata != "") {
                view.check_text.setText("* 시작일은 종료일보다 이후일수 없습니다.")
            }
            view.calendar_text.setTextColor(Color.parseColor("#6d34f3"))
        }else if (type == 1) {
            if (stdata != "") {
                view.check_text.setText("* 종료일은 시작일보다 이전일수 없습니다.")
            }
            view.calendar_text.setText("종료일")
            view.calendar_text.setTextColor(Color.parseColor("#f96565"))
        }

        if (stdata != "") {
            try {
                stTime = checkFotmatt.parse(stdata)
            }
            catch (e: ParseException) {

            }
        }
        if (enddata != "") {
            try {
                enTime = checkFotmatt.parse(enddata)
            }
            catch (e: ParseException) {

            }
        }

        view.calendarView.setOnDayClickListener(object : OnDayClickListener {
            override fun onDayClick(eventDay: EventDay) {
                if (type == 0) {
                    if (enddata != "") {
                        try {
                            checkDate = korFotmatt.format(eventDay.calendar.time)
                            stTime = checkFotmatt.parse(checkDate)
                            if (stTime.time != enTime.time) {
                                if (stTime.time <= enTime.time) {
                                    afterDate = ""
                                    afterDate = korFotmatt.format(eventDay.calendar.time)
                                    itemClick(afterDate,eventDay.calendar.time)
                                    dialog?.dismiss()
                                }
                            } else {
                                view.check_text.setText("* 시작일과 종료일은 같을수 없습니다.")
                            }

                        } catch (e: ParseException) {

                        }
                    } else {
                        afterDate = ""
                        afterDate = korFotmatt.format(eventDay.calendar.time)
                        itemClick(afterDate,eventDay.calendar.time)
                        dialog?.dismiss()
                    }
                } else if (type == 1) {
                    if (stdata != "") {
                        try {
                            checkDate = korFotmatt.format(eventDay.calendar.time)
                            enTime = checkFotmatt.parse(checkDate)
                            if (stTime.time != enTime.time) {
                                if (stTime.time <= enTime.time) {
                                    afterDate = ""
                                    afterDate = korFotmatt.format(eventDay.calendar.time)
                                    itemClick(afterDate,eventDay.calendar.time)
                                    dialog?.dismiss()
                                }
                            } else {
                                view.check_text.setText("* 시작일과 종료일은 같을수 없습니다.")
                            }
                        } catch (e: ParseException) {
                        }
                    } else {
                        afterDate = ""
                        afterDate = korFotmatt.format(eventDay.calendar.time)
                        itemClick(afterDate,eventDay.calendar.time)
                        dialog?.dismiss()
                    }
                }
            }

        })
    }

    override fun onResume() {
        super.onResume()
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size.x
        params?.width = (deviceWidth * 0.8).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }
}