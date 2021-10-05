package com.weare.wearecompany.ui.bottommenu.mypage.request.send

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.main.Request.deta.requestClip
import com.weare.wearecompany.data.main.Request.deta.sendone
import com.weare.wearecompany.ui.bottommenu.mypage.request.ClipRecyclerViewAdapter
import java.text.DecimalFormat


class SendStudioHodel(v: View) : RecyclerView.ViewHolder(v) {

    private val clip = itemView.findViewById<RecyclerView>(R.id.request_studio_list_recyclerview)
    private val money_layout = itemView.findViewById<LinearLayout>(R.id.request_studio_list_money_layout)
    private val call_time = itemView.findViewById<TextView>(R.id.request_studio_call_time)
    private val name = itemView.findViewById<TextView>(R.id.request_studio_name)
    val purchase = itemView.findViewById<TextView>(R.id.request_studio_purchase_btn)
    val review = itemView.findViewById<TextView>(R.id.request_studio_review_btn)
    private val money = itemView.findViewById<TextView>(R.id.request_studio_list_money)
    private val money_title_1 = itemView.findViewById<TextView>(R.id.request_studio_list_money_title_1)
    private val money_title_2 = itemView.findViewById<TextView>(R.id.request_studio_list_money_title_2)

    private lateinit var Adapter: ClipRecyclerViewAdapter
    private lateinit var cliplist:ArrayList<requestClip>

    private val dec = DecimalFormat("#,###")

    fun bindWithView(item: sendone, context: Context, type:Int, onClickListener: View.OnClickListener) {

        cliplist = ArrayList()

        name.text = item.expert_name
        call_time.text = item.send_time

        if (item.room_name != "") {
            val clipitem = requestClip(
                type = 0,
                content = item.room_name
            )
            cliplist.add(clipitem)
        }

        if (item.head_count != "") {
            val clipitem = requestClip(
                type = 1,
                content = item.head_count
            )
            cliplist.add(clipitem)
        }

        if (item.time != "") {
            val clipitem = requestClip(
                type = 2,
                content = item.time
            )
            cliplist.add(clipitem)
        }

        if (item.date != "") {
            val dt = item.date.split(",")
            for (i in dt) {
                val clipitem = requestClip(
                    type = 3,
                    content = i
                )
                cliplist.add(clipitem)
            }
        }

        Adapter = ClipRecyclerViewAdapter(cliplist)
        clip.layoutManager =
            LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL, false
            )
        clip.adapter = Adapter

        if (type != 0) {
            money_layout.visibility = View.VISIBLE
            money.text = dec.format(item.price)
        }

        when(type) {
            1,2 -> {
            }
            3 -> {
                call_time.visibility = View.GONE
                purchase.visibility = View.VISIBLE
            }
            4 -> {
                call_time.visibility = View.GONE
                review.visibility = View.VISIBLE
            }
            5 -> {
                call_time.visibility = View.GONE
                money.setTextColor(Color.parseColor("#f96565"))
                money_title_1.setTextColor(Color.parseColor("#f96565"))
                money_title_2.setTextColor(Color.parseColor("#f96565"))
            }
            6 -> {
                call_time.visibility = View.GONE
                money.setTextColor(Color.parseColor("#f96565"))
                money_title_1.setTextColor(Color.parseColor("#f96565"))
                money_title_2.setTextColor(Color.parseColor("#f96565"))
            }
        }

    }
}