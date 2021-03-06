package com.weare.wearecompany.ui.detail.trip.reservation

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import kotlinx.android.synthetic.main.expert_reservation_check_dialog.view.*
import java.text.DecimalFormat

class ReservationTripDialog ( val reserve_dt:ArrayList<String>, val reserve_time:Int, val reserve_headcount:Int, val reserve_contents:String, val itemClick:(Int) -> Unit): DialogFragment() {

    val priceComma = DecimalFormat("#,###")
    val windowManager = MyApplication.instance.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = windowManager.defaultDisplay
    val size = Point()
    private lateinit var dateadapter : ReservationTripDialogRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.expert_reservation_check_dialog, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCancelable(false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        display.getSize(size)

            view.Re_expert_recyclerView.visibility = View.VISIBLE
            dateadapter = ReservationTripDialogRecyclerViewAdapter(reserve_dt)
            view.Re_expert_recyclerView.layoutManager = GridLayoutManager(context, 3)
            view.Re_expert_recyclerView.adapter = dateadapter

            view.Re_expert_time_date.text = reserve_time.toString()
            view.Re_expert_people_date.text = reserve_headcount.toString()


            if (reserve_contents == "") {
                view.Re_expert_price_contents_layout.visibility = View.GONE
            } else {
                view.Re_expert_price_contents.text = reserve_contents
            }


        view.Re_expert_check_ok.setOnClickListener {
            itemClick(1)
            dialog?.dismiss()
        }
        view.Re_expert_check_no.setOnClickListener {
            dialog?.dismiss()
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