package com.weare.wearecompany.ui.listcontainer

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.weare.wearecompany.R
import kotlinx.android.synthetic.main.bottom_dialog_money.view.*


class MoneyDialog(var con:Context, var min_money:String, var max_money:String, val itemClick: (String,String) -> Unit) : BottomSheetDialogFragment()  {

    private lateinit var postimage: ImageView
    private lateinit var posttext: TextView
    private var clicknum = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.bottom_dialog_money, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (min_money != "") {
            view.money_min.setText(min_money)
        }
        if (max_money != "") {
            view.money_max.setText(max_money)
        }

        view.list_money_ok.setOnClickListener {
            if (view.money_min.text.toString() != "") {
                if (view.money_max.text.toString() != "") {
                    if (view.money_min.text.toString().toInt() >= view.money_max.text.toString().toInt()) {
                        Toast.makeText(con,"최소금액은 최대금액보다 크거나 같을수 없습니다.",Toast.LENGTH_SHORT).show()
                    } else {
                        dialog?.dismiss()
                        itemClick(view.money_min.text.toString(),view.money_max.text.toString())
                    }
                } else {
                    dialog?.dismiss()
                    itemClick(view.money_min.text.toString(),view.money_max.text.toString())
                }
            } else {
                dialog?.dismiss()
                itemClick(view.money_min.text.toString(),view.money_max.text.toString())
            }
        }
    }

    private fun bottomOn(image: ImageView, text: TextView, num:Int) {
        if (clicknum == -1) {
            postimage = image
            posttext = text
            clicknum = num
        }
        image.visibility = View.VISIBLE
        text.setTextColor(Color.parseColor("#6d34f3"))
    }

    private fun bottomOff(image: ImageView, text: TextView, num:Int) {
        postimage.visibility = View.GONE
        posttext.setTextColor(Color.parseColor("#000000"))
        postimage = image
        posttext = text
        clicknum = num
    }

    private fun bottomReset(image: ImageView, text: TextView, num:Int) {
        image.visibility = View.GONE
        text.setTextColor(Color.parseColor("#000000"))
        clicknum = num
    }
}