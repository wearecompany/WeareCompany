package com.weare.wearecompany.ui.bottommenu.mypage.request

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

import com.weare.wearecompany.R
import com.weare.wearecompany.data.main.Request.deta.top
import kotlinx.android.synthetic.main.item_request_top.view.*
import java.text.DecimalFormat

class RequestListTopPageAdapter(private val detaList: ArrayList<top>): PagerAdapter() {

    private var image_count = 1
    private val dec = DecimalFormat("#,###")

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(container.context)
        val view = inflater.inflate(R.layout.item_request_top, container, false)

        when(detaList[position].reserve_status){
            30 -> {
                view.request_top_progress_bac.setImageResource(R.drawable.request_progress_6)
                view.request_top_expert_status.text = "결제완료"
            }
            31 -> {
                view.request_top_progress_bac.setImageResource(R.drawable.request_progress_8)
                view.request_top_expert_status.text = "진행완료"
            }
            32 -> {
                view.request_top_progress_bac.setImageResource(R.drawable.request_progress_10)
                view.request_top_expert_status.text = "후기작성"
            }
        }

        if (detaList[position].reserve_type == 0) {
            when(detaList[position].expert_type[0]) {
                0 -> {
                    view.request_top_progress_img.setImageResource(R.drawable.request_top_studio)
                    view.request_top_room_name_layout.visibility = View.VISIBLE
                    view.request_top_room_name.text = detaList[position].room_name
                    view.request_top_expert_head_count_layout.visibility = View.VISIBLE
                    view.request_top_expert_head_count.text = detaList[position].head_count.toString()
                }
                1 -> {
                    view.request_top_progress_img.setImageResource(R.drawable.request_top_photo)
                    view.request_top_expert_head_count_layout.visibility = View.VISIBLE
                    view.request_top_expert_head_count.text = detaList[position].head_count.toString()
                }
                2 -> {
                    view.request_top_progress_img.setImageResource(R.drawable.request_top_model)
                }
                3 -> {
                    view.request_top_progress_img.setImageResource(R.drawable.request_top_trip)
                }
            }
        } else {
            view.request_top_progress_img.setImageResource(R.drawable.one_click_noti)
            if (detaList[position].expert_type.isNotEmpty()) {
                view.top_oneclik_ca_layout.visibility = View.VISIBLE
                for (i in image_count..detaList[position].expert_type.size) {
                    when(i-1) {
                        0 -> {
                            view.oneclik_ca_0.visibility = View.VISIBLE
                            when(detaList[position].expert_type[i-1]) {
                                0 -> {
                                    view.oneclik_ca_0_img.setImageResource(R.drawable.request_top_studio)
                                }
                                1 -> {
                                    view.oneclik_ca_0_img.setImageResource(R.drawable.request_top_photo)
                                }
                                2 -> {
                                    view.oneclik_ca_0_img.setImageResource(R.drawable.request_top_model)
                                }
                                3 -> {
                                    view.oneclik_ca_0_img.setImageResource(R.drawable.request_top_trip)
                                }
                            }
                        }
                        1 -> {
                            view.oneclik_ca_1.visibility = View.VISIBLE
                            when(detaList[position].expert_type[i-1]) {
                                0 -> {
                                    view.oneclik_ca_1_img.setImageResource(R.drawable.request_top_studio)
                                }
                                1 -> {
                                    view.oneclik_ca_1_img.setImageResource(R.drawable.request_top_photo)
                                }
                                2 -> {
                                    view.oneclik_ca_1_img.setImageResource(R.drawable.request_top_model)
                                }
                                3 -> {
                                    view.oneclik_ca_1_img.setImageResource(R.drawable.request_top_trip)
                                }
                            }
                        }
                        2 -> {
                            view.oneclik_ca_2.visibility = View.VISIBLE
                            when(detaList[position].expert_type[i-1]) {
                                0 -> {
                                    view.oneclik_ca_2_img.setImageResource(R.drawable.request_top_studio)
                                }
                                1 -> {
                                    view.oneclik_ca_2_img.setImageResource(R.drawable.request_top_photo)
                                }
                                2 -> {
                                    view.oneclik_ca_2_img.setImageResource(R.drawable.request_top_model)
                                }
                                3 -> {
                                    view.oneclik_ca_2_img.setImageResource(R.drawable.request_top_trip)
                                }
                            }
                        }
                        3 -> {
                            view.oneclik_ca_3.visibility = View.VISIBLE
                            when(detaList[position].expert_type[i-1]) {
                                0 -> {
                                    view.oneclik_ca_3_img.setImageResource(R.drawable.request_top_studio)
                                }
                                1 -> {
                                    view.oneclik_ca_3_img.setImageResource(R.drawable.request_top_photo)
                                }
                                2 -> {
                                    view.oneclik_ca_3_img.setImageResource(R.drawable.request_top_model)
                                }
                                3 -> {
                                    view.oneclik_ca_3_img.setImageResource(R.drawable.request_top_trip)
                                }
                            }
                        }
                    }
                }
            }
        }

        view.request_top_expert_name.text = detaList[position].expert_name
        view.request_top_expert_time.text = detaList[position].time
        view.request_top_expert_date.text = detaList[position].date
        view.request_top_expert_price.text = dec.format(detaList[position].price)



        container.addView(view)

        return view
    }

    override fun getCount(): Int {
        return detaList.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View?)
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }
}