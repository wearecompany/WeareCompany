package com.weare.wearecompany.ui.detail.rent

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
import com.weare.wearecompany.data.hotpick.data.review

class RentReviewViewHodel (v: View): RecyclerView.ViewHolder(v) {

    private val profile = itemView.findViewById<ImageView>(R.id.rent_profile)
    private val nickname = itemView.findViewById<TextView>(R.id.rent_review_nickname)
    private val grade_1 = itemView.findViewById<ImageView>(R.id.rent_review_item_1)
    private val grade_2 = itemView.findViewById<ImageView>(R.id.rent_review_item_2)
    private val grade_3 = itemView.findViewById<ImageView>(R.id.rent_review_item_3)
    private val grade_4 = itemView.findViewById<ImageView>(R.id.rent_review_item_4)
    private val datetime = itemView.findViewById<TextView>(R.id.rent_review_datetime)
    private val contents = itemView.findViewById<TextView>(R.id.rent_review_contents)
    private val image = itemView.findViewById<ImageView>(R.id.rent_review_image)
    private val review_reply_layout = itemView.findViewById<LinearLayout>(R.id.rent_review_reply_layout)
    private val reply = itemView.findViewById<TextView>(R.id.rent_review_reply)


    fun bindWithView(Item: review) {

        var multiTransformation = MultiTransformation(CenterCrop(), RoundedCorners(20))
        Glide.with(MyApplication.instance)
            .load(Item.profile)
            .skipMemoryCache(true)
            .placeholder(R.drawable.loading_image)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .fallback(R.drawable.list_not_image)
            .apply(RequestOptions.bitmapTransform(multiTransformation))
            .into(profile)

        nickname.text = Item.nickname
        when(Item.grade) {
            2 -> {
                grade_1.setImageResource(R.drawable.review_on)
            }
            3 -> {
                grade_1.setImageResource(R.drawable.review_on)
                grade_2.setImageResource(R.drawable.review_on)
            }
            4 -> {
                grade_1.setImageResource(R.drawable.review_on)
                grade_2.setImageResource(R.drawable.review_on)
                grade_3.setImageResource(R.drawable.review_on)
            }
            5 -> {
                grade_1.setImageResource(R.drawable.review_on)
                grade_2.setImageResource(R.drawable.review_on)
                grade_3.setImageResource(R.drawable.review_on)
                grade_4.setImageResource(R.drawable.review_on)
            }
        }
        datetime.text = Item.datetime
        contents.text = Item.contents
        if (Item.image != "") {
            image.visibility = View.VISIBLE
            var multiTransformation = MultiTransformation(CenterCrop(), RoundedCorners(20))
            Glide.with(MyApplication.instance)
                .load(Item.image)
                .skipMemoryCache(true)
                .placeholder(R.drawable.loading_image)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .fallback(R.drawable.list_not_image)
                .apply(RequestOptions.bitmapTransform(multiTransformation))
                .into(image)
        }

        if (Item.reply != "") {
            review_reply_layout.visibility = View.VISIBLE
            reply.text = Item.reply
        }
    }
}