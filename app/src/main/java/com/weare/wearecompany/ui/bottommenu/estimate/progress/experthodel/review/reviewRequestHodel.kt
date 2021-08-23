package com.weare.wearecompany.ui.bottommenu.estimate.progress.experthodel.review

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.bottomnav.estimate.progress.ProgressAllDate
import com.weare.wearecompany.ui.bottommenu.estimate.send.SendThumbnailRecyclerViewAdapter
import java.text.DecimalFormat

class reviewRequestHodel(v: View) : RecyclerView.ViewHolder(v) {

    private val user_nickname = itemView.findViewById<TextView>(R.id.many_user_nickname)
    private val user_thumbnail = itemView.findViewById<ImageView>(R.id.many_user_thumbnail)
    private val category = itemView.findViewById<TextView>(R.id.many_main_category)
    private val view_status = itemView.findViewById<ImageView>(R.id.request_view_status)
    private val sub_category = itemView.findViewById<TextView>(R.id.many_sub_category)
    private val many_ok = itemView.findViewById<TextView>(R.id.many_ok)
    private val price = itemView.findViewById<TextView>(R.id.many_price)
    private val datetime = itemView.findViewById<TextView>(R.id.many_datetime)
    private val thumbnail = itemView.findViewById<RecyclerView>(R.id.many_thumbnail_RecyclerView)

    private val dec = DecimalFormat("#,###")
    private lateinit var dataAdapter: SendThumbnailRecyclerViewAdapter

    fun bindWithView(
        item: ProgressAllDate,
        context: Context,
        onClickListener: View.OnClickListener
    ) {

        if (item.view_status) {
            view_status.setBackgroundResource(R.drawable.progress_status_no)
        } else {
            view_status.setBackgroundResource(R.drawable.review_status)
        }

        if (item.review_status) {
            category.visibility = View.GONE
        } else {
            many_ok.setBackgroundResource(R.drawable.user_request_send)
            many_ok.setTextColor(Color.parseColor("#ffffff"))
            many_ok.setText("후기작성")
        }

        price.text = dec.format(item.price)

        var multiTransformation = MultiTransformation(CenterCrop(), RoundedCorners(20))

        Glide.with(MyApplication.instance)
            .load(item.user_thumbnail)
            .skipMemoryCache(true)
            .placeholder(R.drawable.loading_image)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .fallback(R.drawable.mypage_user_not_image)
            .apply(RequestOptions.bitmapTransform(multiTransformation))
            .into(user_thumbnail)
        user_nickname.setText(item.user_nickname)
        sub_category.setText(item.sub_category)
        datetime.setText(item.datetime)
        datetime.setTextColor(Color.parseColor("#6d34f3"))


        if (item.thumbnail?.size != 0) {
            thumbnail.visibility = View.VISIBLE
            dataAdapter = SendThumbnailRecyclerViewAdapter(item.thumbnail!!)
            thumbnail.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL, false
            )
            thumbnail.adapter = dataAdapter
        }

    }

    fun viewStatus() {
        view_status.setBackgroundResource(R.drawable.progress_status_no)
    }
}