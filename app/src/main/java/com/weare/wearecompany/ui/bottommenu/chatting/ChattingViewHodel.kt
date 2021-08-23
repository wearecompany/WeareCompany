package com.weare.wearecompany.ui.bottommenu.chatting

import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.bottomnav.mypage.data.manual
import com.weare.wearecompany.data.chatting.data.chatlist
import com.weare.wearecompany.utils.Constants
import java.text.DecimalFormat

class ChattingViewHodel(v:View): RecyclerView.ViewHolder(v) {

    private val user_profile = itemView.findViewById<ImageView>(R.id.chat_user_profile)
    private val user_name = itemView.findViewById<TextView>(R.id.chat_user_name)
    private val chat_time = itemView.findViewById<TextView>(R.id.chat_time)
    private val sub_category = itemView.findViewById<TextView>(R.id.chat_sub_category)
    private val chat_msg = itemView.findViewById<TextView>(R.id.chat_msg)
    private val chat_status = itemView.findViewById<TextView>(R.id.chat_status)
    private val chat_price = itemView.findViewById<TextView>(R.id.chat_price)
    private val chat_price_layout = itemView.findViewById<LinearLayout>(R.id.chat_price_layout)

    private val dec = DecimalFormat("#,###")
    private val view = itemView.findViewById<View>(R.id.information_view)

    fun bindWithView(Item: chatlist, onClickListener: View.OnClickListener) {
        Log.d(Constants.TAG, "dateilItemViewHolder - bindWithView() called")

        var multiTransformation = MultiTransformation(CenterCrop(), RoundedCorners(20))

        Glide.with(MyApplication.instance)
            .load(Item.expert_user_profile)
            .skipMemoryCache(true)


            .placeholder(R.drawable.mypage_user_not_image)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .apply(RequestOptions.bitmapTransform(multiTransformation))
            .into(user_profile)

        user_name.text = Item.expert_user_nickname
        chat_time.text = Item.last_chat_dt
        sub_category.text = Item.expert_category
        chat_msg.text = Item.last_chat_msg
        if (Item.refund_status != 0) {
            if (Item.refund_status == 1) {
                chat_status.text = "환불 요청"
                chat_status.setBackgroundResource(R.drawable.refund_status_true)
                sub_category.setTextColor(Color.parseColor("#9dabe6"))
                chat_price_layout.visibility = View.VISIBLE
                chat_price.text = Item.price.toString()
            } else {
                chat_status.text = "환불 완료"
                chat_status.setBackgroundResource(R.drawable.refund_status_true)
                sub_category.setTextColor(Color.parseColor("#9dabe6"))
                chat_price_layout.visibility = View.VISIBLE
                chat_price.text = Item.price.toString()
            }
        } else {
            when(Item.status) {
                0 -> {
                    chat_status.text = "보낸요청"
                    chat_status.setBackgroundResource(R.drawable.user_request_send)
                    chat_price_layout.visibility = View.GONE
                }
                1 -> {
                    chat_status.text = "받은견적"
                    chat_status.setBackgroundResource(R.drawable.user_request_receive)
                    sub_category.setTextColor(Color.parseColor("#fec394"))
                    chat_price_layout.visibility = View.VISIBLE
                    chat_price.text = dec.format(Item.price)
                }
                2 -> {
                    chat_status.text = "진행중"
                    chat_status.setBackgroundResource(R.drawable.user_progress_ongoing)
                    sub_category.setTextColor(Color.parseColor("#ec9eff"))
                    chat_price_layout.visibility = View.VISIBLE
                    chat_price.text = dec.format(Item.price)
                }
                3 -> {
                    chat_status.text = "진행완료"
                    chat_status.setBackgroundResource(R.drawable.user_progress_ongoing)
                    sub_category.setTextColor(Color.parseColor("#ec9eff"))
                    chat_price_layout.visibility = View.VISIBLE
                    chat_price.text = dec.format(Item.price)
                }
            }
        }



    }
}