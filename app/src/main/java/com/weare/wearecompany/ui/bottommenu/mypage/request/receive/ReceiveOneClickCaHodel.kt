package com.weare.wearecompany.ui.bottommenu.mypage.request.receive

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.main.Request.deta.expertarray

class ReceiveOneClickCaHodel(v: View): RecyclerView.ViewHolder(v) {

    private val user_image = itemView.findViewById<ImageView>(R.id.receive_onclick_tag_user_image)
    private val user_name = itemView.findViewById<TextView>(R.id.receive_onclick_tag_user_name)
    private val tag_image = itemView.findViewById<ImageView>(R.id.receive_onclick_tag_image)
    private val tag_name = itemView.findViewById<TextView>(R.id.receive_onclick_tag_name)

    fun bindWithView(Item: expertarray,onClickListener: View.OnClickListener) {

        var multiTransformation = MultiTransformation(CenterCrop(), RoundedCorners(20))
        val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

        Glide.with(MyApplication.instance)
            .load(Item.expert_image)
            .fallback(R.drawable.not_load_image)
            .transition(DrawableTransitionOptions.withCrossFade(factory))
            .apply(RequestOptions.bitmapTransform(multiTransformation))
            .into(DrawableImageViewTarget(user_image))

        when(Item.expert_type) {
            0 -> {
                user_name.text = Item.expert_name
                tag_image.setImageResource(R.drawable.send_room)
                tag_name.text = Item.room_name
            }
            1 -> {
                user_name.text = Item.expert_name
                tag_image.setImageResource(R.drawable.send_tag)
                tag_name.text = "포토그래퍼"
            }
            2 -> {
                user_name.text = Item.expert_name
                tag_image.setImageResource(R.drawable.send_tag)
                tag_name.text = "모델"
            }
            3 -> {
                user_name.text = Item.expert_name
                tag_image.setImageResource(R.drawable.send_tag)
                tag_name.text = "뷰티전문가"
            }
        }

    }
}