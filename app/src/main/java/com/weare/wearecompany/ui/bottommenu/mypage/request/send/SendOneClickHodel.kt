package com.weare.wearecompany.ui.bottommenu.mypage.request.send

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.main.Request.deta.sendoneclick
import com.weare.wearecompany.ui.bottommenu.mypage.request.ClipRecyclerViewAdapter
import com.weare.wearecompany.ui.bottommenu.mypage.request.SendOnClickImageRecyclerViewAdapter
import java.text.DecimalFormat

class SendOneClickHodel(v: View) : RecyclerView.ViewHolder(v) {

    private val image = itemView.findViewById<RecyclerView>(R.id.request_list_send_onclick_recyclerview)

    private val latout_0 = itemView.findViewById<FrameLayout>(R.id.oneclik_ca_0)
    private val image_0 = itemView.findViewById<ImageView>(R.id.oneclik_ca_0_img)
    private val latout_1 = itemView.findViewById<FrameLayout>(R.id.oneclik_ca_1)
    private val image_1 = itemView.findViewById<ImageView>(R.id.oneclik_ca_1_img)
    private val latout_2 = itemView.findViewById<FrameLayout>(R.id.oneclik_ca_2)
    private val image_2 = itemView.findViewById<ImageView>(R.id.oneclik_ca_2_img)
    private val latout_3 = itemView.findViewById<FrameLayout>(R.id.oneclik_ca_3)
    private val image_3 = itemView.findViewById<ImageView>(R.id.oneclik_ca_3_img)

    val purchase = itemView.findViewById<TextView>(R.id.request_list_send_onclick_purchase_btn)
    private val time = itemView.findViewById<TextView>(R.id.request_list_send_onclick_call_time)
    private val contents = itemView.findViewById<TextView>(R.id.request_list_send_onclick_contents)
    private val price = itemView.findViewById<TextView>(R.id.request_list_send_onclick_price)

    private val dec = DecimalFormat("#,###")

    private lateinit var Adapter: SendOnClickImageRecyclerViewAdapter
    private var image_count = 1


    fun bindWithView(item:sendoneclick, type:Int, postion:Int, context:Context, onClickListener: View.OnClickListener) {

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
        image.adapter = Adapter

        time.text = item.send_time
        contents.text = item.contents
        price.text = dec.format(item.price)

        when(type) {
            3 -> {
                time.visibility = View.GONE
                purchase.visibility = View.VISIBLE
            }
        }

    }
}