package com.weare.wearecompany.ui.bottommenu.mypage.request.review

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.main.Request.deta.requestClip
import com.weare.wearecompany.data.main.Request.deta.reviewone
import com.weare.wearecompany.data.main.Request.deta.sendone
import com.weare.wearecompany.ui.bottommenu.mypage.request.ClipRecyclerViewAdapter
import java.text.DecimalFormat

class ReviewPhotoHodel(v: View) : RecyclerView.ViewHolder(v) {

    private val clip = itemView.findViewById<RecyclerView>(R.id.request_photo_list_recyclerview)
    private val money_layout = itemView.findViewById<LinearLayout>(R.id.request_photo_list_money_layout)
    private val call_time = itemView.findViewById<TextView>(R.id.request_photo_call_time)
    private val name = itemView.findViewById<TextView>(R.id.request_photo_name)
    val purchase = itemView.findViewById<TextView>(R.id.request_photo_purchase_btn)
    val review = itemView.findViewById<TextView>(R.id.request_photo_review_btn)
    private val review_ok = itemView.findViewById<TextView>(R.id.request_photo_review_ok_btn)
    private val money = itemView.findViewById<TextView>(R.id.request_photo_list_money)

    private val dec = DecimalFormat("#,###")

    private lateinit var Adapter: ClipRecyclerViewAdapter
    private lateinit var cliplist:ArrayList<requestClip>

    fun bindWithView(item: reviewone, context: Context, onClickListener: View.OnClickListener) {

        cliplist = ArrayList()

        name.text = item.expert_name
        call_time.text = item.send_time
        money_layout.visibility = View.VISIBLE
        money.text = dec.format(item.price)

        when(item.review_status) {
            0 -> {
                call_time.visibility = View.GONE
                review.visibility = View.VISIBLE
            }
            1 -> {
                call_time.visibility = View.GONE
                review_ok.visibility = View.VISIBLE
            }
        }

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


    }
}