package com.weare.wearecompany.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.weare.wearecompany.R
import kotlinx.android.synthetic.main.bottom_dialog_report.view.*

class ReportDialog(var setupdata:Int, val itemClick: (Int) -> Unit) : BottomSheetDialogFragment() {

    private lateinit var postimage: ImageView
    private lateinit var posttext: TextView
    private var clicknum = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.bottom_dialog_report, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (setupdata != -1) {
            when(setupdata) {
                0 -> reportOn(view.bottom_report_image_0, view.bottom_report_text_0, 0)
                1 -> reportOn(view.bottom_report_image_1, view.bottom_report_text_1, 1)
                2 -> reportOn(view.bottom_report_image_2, view.bottom_report_text_2, 2)
                3 -> reportOn(view.bottom_report_image_3, view.bottom_report_text_3, 3)
                4 -> reportOn(view.bottom_report_image_4, view.bottom_report_text_4, 4)
                5 -> reportOn(view.bottom_report_image_5, view.bottom_report_text_5, 5)
                6 -> reportOn(view.bottom_report_image_6, view.bottom_report_text_6, 6)
                7 -> reportOn(view.bottom_report_image_7, view.bottom_report_text_7, 7)
            }
        }

        view.bottom_report_0.setOnClickListener {
            if (clicknum != -1 && clicknum != 0) {
                reportOff(view.bottom_report_image_0, view.bottom_report_text_0, 0)
            }
            reportOn(view.bottom_report_image_0, view.bottom_report_text_0, 0)
            itemClick(0)
        }

        view.bottom_report_1.setOnClickListener {
            if (clicknum != -1 && clicknum != 1) {
                reportOff(view.bottom_report_image_1, view.bottom_report_text_1, 1)
            }
            reportOn(view.bottom_report_image_1, view.bottom_report_text_1, 1)
            itemClick(1)
        }

        view.bottom_report_2.setOnClickListener {
            if (clicknum != -1 && clicknum != 2) {
                reportOff(view.bottom_report_image_2, view.bottom_report_text_2, 2)
            }
            reportOn(view.bottom_report_image_2, view.bottom_report_text_2, 2)
            itemClick(2)
        }

        view.bottom_report_3.setOnClickListener {
            if (clicknum != -1 && clicknum != 3) {
                reportOff(view.bottom_report_image_3, view.bottom_report_text_3, 3)
            }
            reportOn(view.bottom_report_image_3, view.bottom_report_text_3, 3)
            itemClick(3)
        }

        view.bottom_report_4.setOnClickListener {
            if (clicknum != -1 && clicknum != 4) {
                reportOff(view.bottom_report_image_4, view.bottom_report_text_4, 4)
            }
            reportOn(view.bottom_report_image_4, view.bottom_report_text_4, 4)
            itemClick(4)
        }

        view.bottom_report_5.setOnClickListener {
            if (clicknum != -1 && clicknum != 5) {
                reportOff(view.bottom_report_image_5, view.bottom_report_text_5, 5)
            }
            reportOn(view.bottom_report_image_5, view.bottom_report_text_5, 5)
            itemClick(5)
        }

        view.bottom_report_6.setOnClickListener {
            if (clicknum != -1 && clicknum != 6) {
                reportOff(view.bottom_report_image_6, view.bottom_report_text_6, 6)
            }
            reportOn(view.bottom_report_image_6, view.bottom_report_text_6, 6)
            itemClick(6)
        }

        view.bottom_report_7.setOnClickListener {
            if (clicknum != -1 && clicknum != 7) {
                reportOff(view.bottom_report_image_7, view.bottom_report_text_7, 7)
            }
            reportOn(view.bottom_report_image_7, view.bottom_report_text_7, 7)
            itemClick(7)
        }

        view.report_ok.setOnClickListener {
            dialog?.dismiss()
        }

    }

    private fun reportOn(image:ImageView, text:TextView, num:Int) {
        if (clicknum == -1) {
            postimage = image
            posttext = text
            clicknum = num
        }
        image.visibility = View.VISIBLE
        text.setTextColor(Color.parseColor("#6d34f3"))
    }

    private fun reportOff(image:ImageView, text:TextView, num:Int) {
        postimage.visibility = View.GONE
        posttext.setTextColor(Color.parseColor("#000000"))
        postimage = image
        posttext = text
        clicknum = num
    }

}