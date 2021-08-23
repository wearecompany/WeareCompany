package com.weare.wearecompany.ui.bottom

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.weare.wearecompany.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_dialog_many_location.view.*


class BottomCategoryLocationFragment(var setupdata:Int, val itemClick: (Int) -> Unit) : BottomSheetDialogFragment() {

    private lateinit var posttext: TextView
    private var clicknum = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.bottom_dialog_many_location, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (setupdata != -1) {
            when(setupdata) {
                0 -> locationOn(view.location_text_0, 0)
                1 -> locationOn(view.location_text_1, 1)
                2 -> locationOn(view.location_text_2, 2)
                3 -> locationOn(view.location_text_3, 3)
                4 -> locationOn(view.location_text_4, 4)
                5 -> locationOn(view.location_text_5, 5)
                6 -> locationOn(view.location_text_6, 6)
                7 -> locationOn(view.location_text_7, 7)
                8 -> locationOn(view.location_text_8, 8)
                9 -> locationOn(view.location_text_9, 9)
                10 -> locationOn(view.location_text_10, 10)
                11 -> locationOn(view.location_text_11, 11)
                12 -> locationOn(view.location_text_12, 12)
                13 -> locationOn(view.location_text_13, 13)
                14 -> locationOn(view.location_text_14, 14)
                15 -> locationOn(view.location_text_15, 15)
            }
        }

        view.location_text_0.setOnClickListener {
            if (clicknum != -1 && clicknum != 0) {
                locationOff(view.location_text_0, 0)
            }
            locationOn(view.location_text_0, 0)
            itemClick(0)
        }
        view.location_text_1.setOnClickListener {
            if (clicknum != -1 && clicknum != 1) {
                locationOff(view.location_text_1, 1)
            }
            locationOn(view.location_text_1, 1)
            itemClick(1)
        }
        view.location_text_2.setOnClickListener {
            if (clicknum != -1 && clicknum != 2) {
                locationOff(view.location_text_2, 2)
            }
            locationOn(view.location_text_2, 2)
            itemClick(2)
        }
        view.location_text_3.setOnClickListener {
            if (clicknum != -1 && clicknum != 3) {
                locationOff(view.location_text_3, 3)
            }
            locationOn(view.location_text_3, 3)
            itemClick(3)
        }
        view.location_text_4.setOnClickListener {
            if (clicknum != -1 && clicknum != 4) {
                locationOff(view.location_text_4, 4)
            }
            locationOn(view.location_text_4, 4)
            itemClick(4)
        }
        view.location_text_5.setOnClickListener {
            if (clicknum != -1 && clicknum != 5) {
                locationOff(view.location_text_5, 5)
            }
            locationOn(view.location_text_5, 5)
            itemClick(5)
        }
        view.location_text_6.setOnClickListener {
            if (clicknum != -1 && clicknum != 6) {
                locationOff(view.location_text_6, 6)
            }
            locationOn(view.location_text_6, 6)
            itemClick(6)
        }
        view.location_text_7.setOnClickListener {
            if (clicknum != -1 && clicknum != 7) {
                locationOff(view.location_text_7, 7)
            }
            locationOn(view.location_text_7, 7)
            itemClick(7)
        }
        view.location_text_8.setOnClickListener {
            if (clicknum != -1 && clicknum != 8) {
                locationOff(view.location_text_8, 8)
            }
            locationOn(view.location_text_8, 8)
            itemClick(8)
        }
        view.location_text_9.setOnClickListener {
            if (clicknum != -1 && clicknum != 9) {
                locationOff(view.location_text_9, 9)
            }
            locationOn(view.location_text_9, 9)
            itemClick(9)
        }
        view.location_text_10.setOnClickListener {
            if (clicknum != -1 && clicknum != 10) {
                locationOff(view.location_text_10, 10)
            }
            locationOn(view.location_text_10, 10)
            itemClick(10)
        }
        view.location_text_11.setOnClickListener {
            if (clicknum != -1 && clicknum != 11) {
                locationOff(view.location_text_11, 11)
            }
            locationOn(view.location_text_11, 11)
            itemClick(11)
        }
        view.location_text_12.setOnClickListener {
            if (clicknum != -1 && clicknum != 12) {
                locationOff(view.location_text_12, 12)
            }
            locationOn(view.location_text_12, 12)
            itemClick(12)
        }
        view.location_text_13.setOnClickListener {
            if (clicknum != -1 && clicknum != 13) {
                locationOff(view.location_text_13, 13)
            }
            locationOn(view.location_text_13, 13)
            itemClick(13)
        }
        view.location_text_14.setOnClickListener {
            if (clicknum != -1 && clicknum != 14) {
                locationOff(view.location_text_14, 14)
            }
            locationOn(view.location_text_14, 14)
            itemClick(14)
        }
        view.location_text_15.setOnClickListener {
            if (clicknum != -1 && clicknum != 15) {
                locationOff(view.location_text_14, 15)
            }
            locationOn(view.location_text_14, 15)
            itemClick(15)
        }
        view.top_di_request.setOnClickListener{
            dialog?.dismiss()
        }

    }

    private fun locationOn(text:TextView, num:Int) {
        if (clicknum == -1) {
            posttext = text
            clicknum = num
        }
        text.setTextColor(Color.parseColor("#6d34f3"))
        text.setBackgroundResource(R.drawable.all_ca_background_on)
    }

    private fun locationOff(text:TextView, num:Int) {
        posttext.setTextColor(Color.parseColor("#cbcbcb"))
        posttext.setBackgroundResource(R.drawable.all_ca_background_off)
        posttext = text
        clicknum = num
    }
}