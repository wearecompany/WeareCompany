package com.weare.wearecompany.ui.bottommenu.main.alarm

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.notification.data.alarm

class AlarmViewHodel(v: View): RecyclerView.ViewHolder(v) {

    private val background = itemView.findViewById<ConstraintLayout>(R.id.alarm_background)
    private val imageView = itemView.findViewById<ImageView>(R.id.alarm_image)
    val deleteimage = itemView.findViewById<LinearLayout>(R.id.alarm_delete_image_layout)
    private val title = itemView.findViewById<TextView>(R.id.alarm_title)
    private val contents = itemView.findViewById<TextView>(R.id.alarm_contents)
    private val date_diff = itemView.findViewById<TextView>(R.id.alarm_date_diff)

    fun bindWithView(Item: alarm, Status:Boolean, ClickListener: View.OnClickListener) {

        when(Item.type) {
            10,11,12,13 -> {
                if (Item.view_status == 0) {
                    background.setBackgroundColor(Color.parseColor("#f5f5f5"))
                } else {
                    background.setBackgroundColor(Color.parseColor("#ffffff"))
                }
                if (!Status) {
                    deleteimage.visibility = View.GONE
                } else {
                    deleteimage.visibility = View.VISIBLE
                }
                imageView.setImageResource(R.drawable.notification_individual)
                title.text = Item.title
                contents.text = Item.contents
                date_diff.text = Item.date_diff
            }
            14,15 -> {
                if (Item.view_status == 0) {
                    background.setBackgroundColor(Color.parseColor("#f5f5f5"))
                } else {
                    background.setBackgroundColor(Color.parseColor("#ffffff"))
                }
                if (!Status) {
                    deleteimage.visibility = View.GONE
                } else {
                    deleteimage.visibility = View.VISIBLE
                }
                imageView.setImageResource(R.drawable.notification_refund)
                title.text = Item.title
                contents.text = Item.contents
                date_diff.text = Item.date_diff
            }
            20,21,22,23 -> {
                if (Item.view_status == 0) {
                    background.setBackgroundColor(Color.parseColor("#f5f5f5"))
                } else {
                    background.setBackgroundColor(Color.parseColor("#ffffff"))
                }
                if (!Status) {
                    deleteimage.visibility = View.GONE
                } else {
                    deleteimage.visibility = View.VISIBLE
                }
                imageView.setImageResource(R.drawable.notification_many)
                title.text = Item.title
                contents.text = Item.contents
                date_diff.text = Item.date_diff
            }
            24,25 -> {
                if (Item.view_status == 0) {
                    background.setBackgroundColor(Color.parseColor("#f5f5f5"))
                } else {
                    background.setBackgroundColor(Color.parseColor("#ffffff"))
                }
                if (!Status) {
                    deleteimage.visibility = View.GONE
                } else {
                    deleteimage.visibility = View.VISIBLE
                }
                imageView.setImageResource(R.drawable.notification_refund)
                title.text = Item.title
                contents.text = Item.contents
                date_diff.text = Item.date_diff
            }
            30,31,32,33,34 -> {
                if (Item.view_status == 0) {
                    background.setBackgroundColor(Color.parseColor("#f5f5f5"))
                } else {
                    background.setBackgroundColor(Color.parseColor("#ffffff"))
                }
                if (!Status) {
                    deleteimage.visibility = View.GONE
                } else {
                    deleteimage.visibility = View.VISIBLE
                }
                imageView.setImageResource(R.drawable.notification_review)
                title.text = Item.title
                contents.text = Item.contents
                date_diff.text = Item.date_diff
            }
        }
    }

    fun clickBackground() {
        background.setBackgroundColor(Color.parseColor("#ffffff"))
    }
}