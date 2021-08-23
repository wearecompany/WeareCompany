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
import kotlinx.android.synthetic.main.bottom_dialog_many_model.view.*


class BottomCategoryModelFragment(var setupdata:Int, val itemClick: (Int) -> Unit) : BottomSheetDialogFragment() {

    private lateinit var postimage: ImageView
    private lateinit var posttext: TextView
    private var clicknum = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.bottom_dialog_many_model, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (setupdata != -1) {
            when(setupdata) {
                0 -> bottomOn(view.bottom_model_text_0, 0)
                1 -> bottomOn(view.bottom_model_text_1, 1)
                2 -> bottomOn(view.bottom_model_text_2, 2)
                3 -> bottomOn(view.bottom_model_text_3, 3)
                4 -> bottomOn(view.bottom_model_text_4, 4)
                5 -> bottomOn(view.bottom_model_text_5, 5)
                6 -> bottomOn(view.bottom_model_text_6, 6)
                7 -> bottomOn(view.bottom_model_text_7, 7)
                8 -> bottomOn(view.bottom_model_text_8, 8)
                9 -> bottomOn(view.bottom_model_text_9, 9)
                10 -> bottomOn(view.bottom_model_text_10, 10)
                11 -> bottomOn(view.bottom_model_text_11, 11)
                12 -> bottomOn(view.bottom_model_text_12, 12)
                13 -> bottomOn(view.bottom_model_text_13, 13)
                14 -> bottomOn(view.bottom_model_text_14, 14)
                15 -> bottomOn(view.bottom_model_text_15, 15)
                16 -> bottomOn(view.bottom_model_text_16, 16)
                17 -> bottomOn(view.bottom_model_text_17, 17)
                18 -> bottomOn(view.bottom_model_text_18, 18)
                19 -> bottomOn(view.bottom_model_text_19, 19)
            }
        }

        view.bottom_model_text_0.setOnClickListener {
            if (clicknum != -1 && clicknum != 0) {
                bottomOff(view.bottom_model_text_0, 0)
            }
            bottomOn(view.bottom_model_text_0, 0)
            itemClick(0)
        }
        view.bottom_model_text_1.setOnClickListener {
            if (clicknum != -1 && clicknum != 1) {
                bottomOff(view.bottom_model_text_1, 1)
            }
            bottomOn(view.bottom_model_text_1, 1)
            itemClick(1)
        }
        view.bottom_model_text_2.setOnClickListener {
            if (clicknum != -1 && clicknum != 2) {
                bottomOff(view.bottom_model_text_2, 2)
            }
            bottomOn(view.bottom_model_text_2, 2)
            itemClick(2)
        }
        view.bottom_model_text_3.setOnClickListener {
            if (clicknum != -1 && clicknum != 3) {
                bottomOff(view.bottom_model_text_3, 3)
            }
            bottomOn(view.bottom_model_text_3, 3)
            itemClick(3)
        }
        view.bottom_model_text_4.setOnClickListener {
            if (clicknum != -1 && clicknum != 4) {
                bottomOff(view.bottom_model_text_4, 4)
            }
            bottomOn(view.bottom_model_text_4, 4)
            itemClick(4)
        }
        view.bottom_model_text_5.setOnClickListener {
            if (clicknum != -1 && clicknum != 5) {
                bottomOff(view.bottom_model_text_5, 5)
            }
            bottomOn(view.bottom_model_text_5, 5)
            itemClick(5)
        }
        view.bottom_model_text_6.setOnClickListener {
            if (clicknum != -1 && clicknum != 6) {
                bottomOff(view.bottom_model_text_6, 6)
            }
            bottomOn(view.bottom_model_text_6, 6)
            itemClick(6)
        }
        view.bottom_model_text_7.setOnClickListener {
            if (clicknum != -1 && clicknum != 7) {
                bottomOff(view.bottom_model_text_7, 7)
            }
            bottomOn(view.bottom_model_text_7, 7)
            itemClick(7)
        }
        view.bottom_model_text_8.setOnClickListener {
            if (clicknum != -1 && clicknum != 8) {
                bottomOff(view.bottom_model_text_8, 8)
            }
            bottomOn(view.bottom_model_text_8, 8)
            itemClick(8)
        }
        view.bottom_model_text_9.setOnClickListener {
            if (clicknum != -1 && clicknum != 9) {
                bottomOff(view.bottom_model_text_9, 9)
            }
            bottomOn(view.bottom_model_text_9, 9)
            itemClick(9)
        }
        view.bottom_model_text_10.setOnClickListener {
            if (clicknum != -1 && clicknum != 10) {
                bottomOff(view.bottom_model_text_10, 10)
            }
            bottomOn(view.bottom_model_text_10, 10)
            itemClick(10)
        }
        view.bottom_model_text_11.setOnClickListener {
            if (clicknum != -1 && clicknum != 11) {
                bottomOff(view.bottom_model_text_11, 11)
            }
            bottomOn(view.bottom_model_text_11, 11)
            itemClick(11)
        }
        view.bottom_model_text_12.setOnClickListener {
            if (clicknum != -1 && clicknum != 12) {
                bottomOff(view.bottom_model_text_12, 12)
            }
            bottomOn(view.bottom_model_text_12, 12)
            itemClick(12)
        }
        view.bottom_model_text_13.setOnClickListener {
            if (clicknum != -1 && clicknum != 13) {
                bottomOff(view.bottom_model_text_13, 13)
            }
            bottomOn(view.bottom_model_text_13, 13)
            itemClick(13)
        }
        view.bottom_model_text_14.setOnClickListener {
            if (clicknum != -1 && clicknum != 14) {
                bottomOff(view.bottom_model_text_14, 14)
            }
            bottomOn( view.bottom_model_text_14, 14)
            itemClick(14)
        }
        view.bottom_model_text_15.setOnClickListener {
            if (clicknum != -1 && clicknum != 15) {
                bottomOff(view.bottom_model_text_15, 15)
            }
            bottomOn(view.bottom_model_text_15, 15)
            itemClick(15)
        }
        view.bottom_model_text_16.setOnClickListener {
            if (clicknum != -1 && clicknum != 16) {
                bottomOff(view.bottom_model_text_16, 16)
            }
            bottomOn(view.bottom_model_text_16, 16)
            itemClick(16)
        }
        view.bottom_model_text_17.setOnClickListener {
            if (clicknum != -1 && clicknum != 17) {
                bottomOff(view.bottom_model_text_17, 17)
            }
            bottomOn(view.bottom_model_text_17, 17)
            itemClick(17)
        }
        view.bottom_model_text_18.setOnClickListener {
            if (clicknum != -1 && clicknum != 18) {
                bottomOff(view.bottom_model_text_18, 18)
            }
            bottomOn(view.bottom_model_text_18, 18)
            itemClick(18)
        }
        view.bottom_model_text_19.setOnClickListener {
            if (clicknum != -1 && clicknum != 19) {
                bottomOff(view.bottom_model_text_19, 19)
            }
            bottomOn(view.bottom_model_text_19, 19)
            itemClick(19)
        }
        view.bottom_model_request.setOnClickListener {
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