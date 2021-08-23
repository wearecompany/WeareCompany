package com.weare.wearecompany.ui.bottom

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.weare.wearecompany.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_dialog_top.view.*

class TopCategoryFragment(var setupdata:Int, val itemClick: (Int) -> Unit) : BottomSheetDialogFragment() {

    private var Topclicknum = -1
    private lateinit var posttext: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.bottom_dialog_top, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (setupdata != -1) {
            when(setupdata) {
                0 -> TopOn(view.top_text_0, 0)
                1 -> TopOn(view.top_text_1, 1)
                2 -> TopOn(view.top_text_2, 2)
            }
        }
        view.top_text_0.setOnClickListener {
            if (Topclicknum != -1 && Topclicknum != 0) {
                TopOff(view.top_text_0, 0)
            }
            TopOn(view.top_text_0, 0)
            itemClick(0)
        }
        view.top_text_1.setOnClickListener {
            if (Topclicknum != -1 && Topclicknum != 1) {
                TopOff(view.top_text_1, 1)
            }
            TopOn(view.top_text_1, 1)
            itemClick(1)
        }
        view.top_text_2.setOnClickListener {
            if (Topclicknum != -1 && Topclicknum != 2) {
                TopOff(view.top_text_2, 2)
            }
            TopOn(view.top_text_2, 2)
            itemClick(2)
        }
        view.top_di_request.setOnClickListener {
            dialog?.dismiss()
        }
    }


    private fun TopOn(text:TextView, num:Int) {
        if (Topclicknum == -1) {
            posttext = text
            Topclicknum = num
        }
        text.setTextColor(Color.parseColor("#6d34f3"))
        text.setBackgroundResource(R.drawable.all_ca_background_on)
    }

    private fun TopOff(text:TextView, num:Int) {
        posttext.setTextColor(Color.parseColor("#cbcbcb"))
        posttext.setBackgroundResource(R.drawable.all_ca_background_off)
        posttext = text
        Topclicknum = num
    }


}