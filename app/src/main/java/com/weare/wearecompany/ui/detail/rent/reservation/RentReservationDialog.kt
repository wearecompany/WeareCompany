package com.weare.wearecompany.ui.detail.rent.reservation

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import kotlinx.android.synthetic.main.rent_reservation_check_dialog.view.*

class RentReservationDialog(val dt_status:Boolean, val dayList:ArrayList<String>, val reserve_day:Int, val rentReserveContents:String, val itemClick:(Int) -> Unit):DialogFragment() {

    val windowManager = MyApplication.instance.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = windowManager.defaultDisplay
    val size = Point()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.rent_reservation_check_dialog, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCancelable(false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        display.getSize(size)

        if (dt_status) {
            view.Re_rent_end_time.text = "협의가능"
            view.Re_rent_all_day_layout.visibility = View.GONE
            if (rentReserveContents == "") {
                view.Re_rent_Reserve_Contents_title.visibility = View.GONE
                view.Re_rent_Reserve_Contents.visibility = View.GONE
            } else {
                view.Re_rent_Reserve_Contents.text = rentReserveContents
            }

        } else if (!dt_status) {
            view.Re_rent_start_time.visibility = View.VISIBLE
            view.Re_rent_time_end.visibility = View.VISIBLE
            view.Re_rent_start_time.text = dayList[0]
            view.Re_rent_end_time.text = dayList[1]
            view.Re_rent_all_day.visibility = View.VISIBLE
            view.Re_rent_all_day.text = reserve_day.toString()
            if (rentReserveContents == "") {
                view.Re_rent_Reserve_Contents_title.visibility = View.GONE
                view.Re_rent_Reserve_Contents.visibility = View.GONE
            } else {
                view.Re_rent_Reserve_Contents.text = rentReserveContents
            }
        }
        view.rent_reservation_check_ok.setOnClickListener {
            itemClick(1)
            dialog!!.dismiss()
        }
        view.rent_reservation_check_no.setOnClickListener {
            dialog!!.dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size.x
        params?.width = (deviceWidth * 0.8).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }
}