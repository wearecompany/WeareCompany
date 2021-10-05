package com.weare.wearecompany.ui.bottommenu.mypage.request.review

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.main.Request.deta.requestClip
import com.weare.wearecompany.data.main.Request.deta.reviewoneclick
import com.weare.wearecompany.ui.bottommenu.mypage.request.ClipRecyclerViewAdapter
import com.weare.wearecompany.ui.bottommenu.mypage.request.SendOnClickImageRecyclerViewAdapter
import java.text.DecimalFormat

class ReviewOneClickHodel(v: View) : RecyclerView.ViewHolder(v) {

    private val image = itemView.findViewById<RecyclerView>(R.id.request_list_receive_onclick_image_recyclerview)
    private val clip = itemView.findViewById<RecyclerView>(R.id.request_list_receive_onclick_clip_recyclerview)

    private val latout_0 = itemView.findViewById<FrameLayout>(R.id.oneclik_ca_0)
    private val image_0 = itemView.findViewById<ImageView>(R.id.oneclik_ca_0_img)
    private val latout_1 = itemView.findViewById<FrameLayout>(R.id.oneclik_ca_1)
    private val image_1 = itemView.findViewById<ImageView>(R.id.oneclik_ca_1_img)
    private val latout_2 = itemView.findViewById<FrameLayout>(R.id.oneclik_ca_2)
    private val image_2 = itemView.findViewById<ImageView>(R.id.oneclik_ca_2_img)
    private val latout_3 = itemView.findViewById<FrameLayout>(R.id.oneclik_ca_3)
    private val image_3 = itemView.findViewById<ImageView>(R.id.oneclik_ca_3_img)

    //private val layout = itemView.findViewById<LinearLayout>(R.id.request_list_receive_onclick_money_layout)
    private val purchase = itemView.findViewById<TextView>(R.id.request_list_onclick_purchase_btn)
    private val review = itemView.findViewById<TextView>(R.id.request_list_onclick_review_btn)
    private val call_time = itemView.findViewById<TextView>(R.id.request_list_onclick_call_time)
    private val price = itemView.findViewById<TextView>(R.id.request_list_receive_onclick_money)
    private val price_title_1 = itemView.findViewById<TextView>(R.id.request_list_receive_onclick_money_title_1)
    private val price_title_2 = itemView.findViewById<TextView>(R.id.request_list_receive_onclick_money_title_2)

    private val dec = DecimalFormat("#,###")

    private var image_count = 1
    private lateinit var cliplist:ArrayList<requestClip>

    private lateinit var Adapter: SendOnClickImageRecyclerViewAdapter
    private lateinit var ClipAdapter: ClipRecyclerViewAdapter

    fun bindWithView(item: reviewoneclick, postion:Int, context: Context, type:Int, onClickListener: View.OnClickListener) {

        if (item.expert_type.isNotEmpty()) {
            for (i in image_count..item.expert_type.size) {
                when(i-1) {
                    0 -> {
                        when(item.expert_type[i-1]) {
                            0 -> {
                                image_0.setImageResource(R.drawable.request_top_studio)
                            }
                            1 -> {
                                image_0.setImageResource(R.drawable.request_top_photo)
                            }
                            2 -> {
                                image_0.setImageResource(R.drawable.request_top_model)
                            }
                            3 -> {
                                image_0.setImageResource(R.drawable.request_top_trip)
                            }
                        }
                    }
                    1 -> {
                        latout_1.visibility = View.VISIBLE
                        when(item.expert_type[i-1]) {
                            0 -> {
                                image_1.setImageResource(R.drawable.request_top_studio)
                            }
                            1 -> {
                                image_1.setImageResource(R.drawable.request_top_photo)
                            }
                            2 -> {
                                image_1.setImageResource(R.drawable.request_top_model)
                            }
                            3 -> {
                                image_1.setImageResource(R.drawable.request_top_trip)
                            }
                        }
                    }
                    2 -> {
                        latout_2.visibility = View.VISIBLE
                        when(item.expert_type[i-1]) {
                            0 -> {
                                image_2.setImageResource(R.drawable.request_top_studio)
                            }
                            1 -> {
                                image_2.setImageResource(R.drawable.request_top_photo)
                            }
                            2 -> {
                                image_2.setImageResource(R.drawable.request_top_model)
                            }
                            3 -> {
                                image_2.setImageResource(R.drawable.request_top_trip)
                            }
                        }
                    }
                    3 -> {
                        latout_3.visibility = View.VISIBLE
                        when(item.expert_type[i-1]) {
                            0 -> {
                                image_3.setImageResource(R.drawable.request_top_studio)
                            }
                            1 -> {
                                image_3.setImageResource(R.drawable.request_top_photo)
                            }
                            2 -> {
                                image_3.setImageResource(R.drawable.request_top_model)
                            }
                            3 -> {
                                image_3.setImageResource(R.drawable.request_top_trip)
                            }
                        }
                    }
                }
            }
        }

        Adapter = SendOnClickImageRecyclerViewAdapter(item.thumbnail)
        image.layoutManager =
            LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL, false
            )

        when(type) {
            3 -> {
                call_time.visibility = View.GONE
                purchase.visibility = View.VISIBLE
            }
            4 -> {
                //call_time.visibility = View.GONE
                //review.visibility = View.VISIBLE
            }
            5 -> {
                call_time.visibility = View.GONE
                price.setTextColor(Color.parseColor("#f96565"))
                price_title_1.setTextColor(Color.parseColor("#f96565"))
                price_title_2.setTextColor(Color.parseColor("#f96565"))
            }
            6 -> {
                call_time.visibility = View.GONE
                price.setTextColor(Color.parseColor("#f96565"))
                price_title_1.setTextColor(Color.parseColor("#f96565"))
                price_title_2.setTextColor(Color.parseColor("#f96565"))
            }
        }
        image.adapter = Adapter

        cliplist = ArrayList()

        call_time.text = item.send_time
        price.text = dec.format(item.price)

        /*if (item.head_count != "") {
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

        ClipAdapter = ClipRecyclerViewAdapter(cliplist)
        clip.layoutManager =
            LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL, false
            )
        clip.adapter = ClipAdapter*/


    }
}