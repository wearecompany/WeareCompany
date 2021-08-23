package com.weare.wearecompany.ui.bottommenu.estimate.send

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
import com.weare.wearecompany.data.bottomnav.estimate.send.SendAllDate
import com.weare.wearecompany.data.bottomnav.estimate.send.SendRequest


class SendViewHodel(v: View) : RecyclerView.ViewHolder(v) {

    private val send_time = itemView.findViewById<TextView>(R.id.send_many_call_time)
    private val user_nickname = itemView.findViewById<TextView>(R.id.send_many_user_nickname)
    private val user_thumbnail = itemView.findViewById<ImageView>(R.id.send_many_user_thumbnail)
    private val main_category = itemView.findViewById<TextView>(R.id.send_many_main_category)
    private val sub_category = itemView.findViewById<TextView>(R.id.send_many_sub_category)
    private val datetime = itemView.findViewById<TextView>(R.id.send_many_datetime)
    private val thumbnail = itemView.findViewById<RecyclerView>(R.id.send_many_thumbnail_RecyclerView)

    private lateinit var dataAdapter: SendThumbnailRecyclerViewAdapter

    fun bindWithView(item: SendAllDate, context: Context, onClickListener: View.OnClickListener) {

        var multiTransformation = MultiTransformation(CenterCrop(), RoundedCorners(20))

        Glide.with(MyApplication.instance)
            .load(item.user_thumbnail)
            .skipMemoryCache(true)
            .placeholder(R.drawable.not_load_image)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .fallback(R.drawable.mypage_user_not_image)
            .apply(RequestOptions.bitmapTransform(multiTransformation))
            .into(user_thumbnail)

        send_time.text = item.send_time
        user_nickname.setText(item.user_nickname)
        main_category.setText(item.main_category)
        sub_category.setText(item.sub_category)
        datetime.setText(item.datetime)

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

}