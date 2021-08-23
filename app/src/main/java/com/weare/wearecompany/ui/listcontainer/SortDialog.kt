package com.weare.wearecompany.ui.listcontainer

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.weare.wearecompany.R
import kotlinx.android.synthetic.main.bottom_dialog_sort.view.*


class SortDialog(var setupdata:Int, val itemClick: (Int) -> Unit) : BottomSheetDialogFragment()  {

    private lateinit var postimage: ImageView
    private lateinit var posttext: TextView
    private var clicknum = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.bottom_dialog_sort, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clicknum = setupdata

            when(setupdata) {
                0 -> bottomOn(view.bottom_sort_image_0, view.bottom_sort_text_0, 0)
                1 -> bottomOn(view.bottom_sort_image_1, view.bottom_sort_text_1, 1)
                2 -> bottomOn(view.bottom_sort_image_2, view.bottom_sort_text_2, 2)
                3 -> bottomOn(view.bottom_sort_image_3, view.bottom_sort_text_3, 3)
                4 -> bottomOn(view.bottom_sort_image_4, view.bottom_sort_text_4, 4)
            }

        view.bottom_sort_0.setOnClickListener {
            if (clicknum != 0) {
                bottomOff(view.bottom_sort_image_0, view.bottom_sort_text_0, 0)
                bottomOn(view.bottom_sort_image_0, view.bottom_sort_text_0, 0)
            }
        }
        view.bottom_sort_1.setOnClickListener {
            if (clicknum != 1) {
                bottomOff(view.bottom_sort_image_1, view.bottom_sort_text_1, 1)
                bottomOn(view.bottom_sort_image_1, view.bottom_sort_text_1, 1)
            }
        }
        view.bottom_sort_2.setOnClickListener {
            if ( clicknum != 2) {
                bottomOff(view.bottom_sort_image_2, view.bottom_sort_text_2, 2)
                bottomOn(view.bottom_sort_image_2, view.bottom_sort_text_2, 2)
            }
        }
        view.bottom_sort_3.setOnClickListener {
            if (clicknum != 3) {
                bottomOff(view.bottom_sort_image_3, view.bottom_sort_text_3, 3)
                bottomOn(view.bottom_sort_image_3, view.bottom_sort_text_3, 3)
            }
        }
        view.bottom_sort_4.setOnClickListener {
            if (clicknum != 4) {
                bottomOff(view.bottom_sort_image_4, view.bottom_sort_text_4, 4)
                bottomOn(view.bottom_sort_image_4, view.bottom_sort_text_4, 4)
            }
        }
        view.top_di_request.setOnClickListener {
            dialog?.dismiss()
            itemClick(clicknum)
        }
    }

    private fun bottomOn(image: ImageView, text: TextView, num:Int) {
        image.visibility = View.VISIBLE
        text.setTextColor(Color.parseColor("#6d34f3"))
        postimage = image
        posttext = text
        clicknum = num
    }

    private fun bottomOff(image: ImageView, text: TextView, num:Int) {
        postimage.visibility = View.GONE
        posttext.setTextColor(Color.parseColor("#000000"))
    }

    private fun bottomReset(image: ImageView, text: TextView, num:Int) {
        image.visibility = View.GONE
        text.setTextColor(Color.parseColor("#000000"))
        clicknum = num
    }
}