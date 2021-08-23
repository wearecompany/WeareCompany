package com.weare.wearecompany.ui.bottommenu.estimate.progress

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.bottomnav.estimate.progress.ProgressAllDate
import com.weare.wearecompany.data.bottomnav.estimate.receive.ReceiveAllDate

class ProgressViewHodel(v: View) : RecyclerView.ViewHolder(v) {

    private val expert_type = itemView.findViewById<TextView>(R.id.expert_type)
    private val datetime = itemView.findViewById<TextView>(R.id.datetime)
    private val view_status = itemView.findViewById<ImageView>(R.id.progress_request_view_status)
    private val sub_category = itemView.findViewById<TextView>(R.id.sub_category)
    private val many_category = itemView.findViewById<TextView>(R.id.many_category)
    private val user_nickname = itemView.findViewById<TextView>(R.id.user_nickname)
    private val user_thumbnail = itemView.findViewById<ImageView>(R.id.many_user_thumbnail)
    private val thumbnail = itemView.findViewById<RecyclerView>(R.id.many_thumbnail)
    private val price = itemView.findViewById<TextView>(R.id.price)
    val category = itemView.findViewById<TextView>(R.id.many_category)


    private lateinit var dataAdapter: ProgressThumbanailRecyclerViewAdapter
    fun bindWithView(
        item: ProgressAllDate,
        context: Context,
        onClickListener: View.OnClickListener
    ) {

        if (item.view_status) {
            view_status.setBackgroundResource(R.drawable.progress_status_no)
        } else {
            view_status.setBackgroundResource(R.drawable.progress_status)
        }

        if (item.on_progress) {
            category.setBackgroundResource(R.drawable.user_request_send)
            category.setTextColor(Color.parseColor("#ffffff"))
            category.setText("구매확정")
        } else {
            category.visibility = View.GONE
        }

        when(item.expert_type) {
            1 -> expert_type.text = "포토그래퍼"
            2 -> expert_type.text = "모델"
            3 -> expert_type.text = "뷰티전문가"
        }
        expert_type.setBackgroundResource(R.drawable.progress_status_true)

        /*if (item.on_progress) {
            many_category.visibility = View.VISIBLE
        }*/

        price.text = item.price.toString()

        val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
        var multiTransformation = MultiTransformation(CenterCrop(), RoundedCorners(20))

        if (item.user_thumbnail != "") {
            Glide.with(MyApplication.instance)
                .load(item.user_thumbnail)
                .fallback(R.drawable.not_load_image)
                .transition(DrawableTransitionOptions.withCrossFade(factory))
                .apply(RequestOptions.bitmapTransform(multiTransformation))
                .into(user_thumbnail)
        } else {
            user_thumbnail.setImageResource(R.drawable.mypage_user_not_image)
        }

        user_nickname.setText(item.user_nickname)
        sub_category.setText(item.sub_category)
        datetime.setText(item.datetime)

        if (item.thumbnail?.size != 0) {
            dataAdapter = ProgressThumbanailRecyclerViewAdapter(item.thumbnail!!)
            thumbnail.visibility = View.VISIBLE
            thumbnail.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL, false
            )
            thumbnail.adapter = dataAdapter
        } else {
            thumbnail.visibility = View.GONE
        }
    }

    fun viewStatus() {
        view_status.setBackgroundResource(R.drawable.progress_status_no)
    }

}
