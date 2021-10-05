package com.weare.wearecompany.ui.bottommenu.mypage.review

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.bottomnav.mypage.data.myreview

class MyReviewViewHodel(v: View) : RecyclerView.ViewHolder(v) {

    //private val user_image = itemView.findViewById<ImageView>(R.id.my_review_user_thumbnail)
    val review_menu = itemView.findViewById<ImageView>(R.id.my_review_menu)
    private val review_status_ = itemView.findViewById<ImageView>(R.id.my_review_status)
    private val grade_1 = itemView.findViewById<ImageView>(R.id.my_review_user_nickname_grade_1)
    private val grade_2 = itemView.findViewById<ImageView>(R.id.my_review_user_nickname_grade_2)
    private val grade_3 = itemView.findViewById<ImageView>(R.id.my_review_user_nickname_grade_3)
    private val grade_4 = itemView.findViewById<ImageView>(R.id.my_review_user_nickname_grade_4)
    private val review_dt = itemView.findViewById<TextView>(R.id.my_review_dt)
    private val expert_nickname = itemView.findViewById<TextView>(R.id.my_review_expert_nickname)
    private val expert_image = itemView.findViewById<ImageView>(R.id.my_review_user_image)
    private val expert_type = itemView.findViewById<TextView>(R.id.my_review_expert_type)
    private val expert_category = itemView.findViewById<TextView>(R.id.my_review_expert_category)
    private val review_contents = itemView.findViewById<TextView>(R.id.my_review_contents)
    private val review_image = itemView.findViewById<ImageView>(R.id.my_review_image)
    private val review_reply_layout =
        itemView.findViewById<LinearLayout>(R.id.my_review_review_reply_layout)
    private val review_reply = itemView.findViewById<TextView>(R.id.my_review_review_reply)

    fun bindWithView(Item: myreview, onClickListener: View.OnClickListener) {

        var multiTransformation = MultiTransformation(CenterCrop(), RoundedCorners(30))
        /*Glide.with(MyApplication.instance)
            .load(Item.user_thumbnail)
            .apply(RequestOptions.bitmapTransform(multiTransformation))
            .into(user_image)*/



        when (Item.grade) {
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

        review_dt.text = Item.review_dt

        Glide.with(MyApplication.instance)
            .load(Item.expert_thumbnail)
            .apply(RequestOptions.bitmapTransform(multiTransformation))
            .into(expert_image)

        expert_nickname.text = Item.expert_nickname

        when (Item.expert_type) {
            0 -> expert_type.text = "스튜디오"
            1 -> expert_type.text = "포토그래퍼"
            2 -> expert_type.text = "모델"
            3 -> expert_type.text = "뷰티전문가"
        }

        expert_category.text = Item.expert_category
        review_contents.text = Item.review_contents

        if (Item.review_image != "") {
            review_image.visibility = View.VISIBLE
            Glide.with(MyApplication.instance)
                .load(Item.review_image)
                .apply(RequestOptions.bitmapTransform(multiTransformation))
                .into(review_image)
        }

        if (Item.review_reply != "") {
            review_status_.visibility = View.GONE
            review_reply_layout.visibility = View.VISIBLE
            review_reply.text = Item.review_reply
        }

    }
}