package com.weare.wearecompany.ui.bottom

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.weare.wearecompany.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_dialog_many_trip.view.*


class BottomCategoryTripFragment(var setupdata:Int, val itemClick: (Int) -> Unit) : BottomSheetDialogFragment()  {

    private lateinit var postimage: ImageView
    private lateinit var posttext: TextView
    private var clicknum = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.bottom_dialog_many_trip, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (setupdata != -1) {
            when(setupdata) {
                0 -> bottomOn(view.bottom_trip_text_0, 0)
                1 -> bottomOn(view.bottom_trip_text_1, 1)
                2 -> bottomOn(view.bottom_trip_text_2, 2)
            }
        }

        view.bottom_trip_text_0.setOnClickListener {
            if (clicknum != -1 && clicknum != 0) {
                bottomOff(view.bottom_trip_text_0, 0)
            }
            bottomOn(view.bottom_trip_text_0, 0)
            itemClick(0)
        }
        view.bottom_trip_text_1.setOnClickListener {
            if (clicknum != -1 && clicknum != 1) {
                bottomOff(view.bottom_trip_text_1, 1)
            }
            bottomOn(view.bottom_trip_text_1, 1)
            itemClick(1)
        }
        view.bottom_trip_text_2.setOnClickListener {
            if (clicknum != -1 && clicknum != 2) {
                bottomOff(view.bottom_trip_text_2, 2)
            }
            bottomOn(view.bottom_trip_text_2, 2)
            itemClick(2)
        }
        view.top_di_request.setOnClickListener {
            dialog?.dismiss()
        }
    }

    private fun bottomOn(text:TextView, num:Int) {
        if (clicknum == -1) {
            posttext = text
            clicknum = num
        }
        text.setTextColor(Color.parseColor("#6d34f3"))
        text.setBackgroundResource(R.drawable.all_ca_background_on)
    }

    private fun bottomOff(text:TextView, num:Int) {
        posttext.setTextColor(Color.parseColor("#cbcbcb"))
        posttext.setBackgroundResource(R.drawable.all_ca_background_off)
        posttext = text
        clicknum = num
    }
}