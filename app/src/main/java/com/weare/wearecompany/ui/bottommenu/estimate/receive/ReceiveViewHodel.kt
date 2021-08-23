package com.weare.wearecompany.ui.bottommenu.estimate.receive

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
import com.weare.wearecompany.data.bottomnav.estimate.receive.ReceiveAllDate
import com.weare.wearecompany.data.bottomnav.estimate.receive.ReceiveResponse
import java.text.DecimalFormat


class ReceiveViewHodel(v : View):RecyclerView.ViewHolder(v) {

    private val user_nickname = itemView.findViewById<TextView>(R.id.many_user_nickname)
    private val user_thumbnail = itemView.findViewById<ImageView>(R.id.many_user_thumbnail)
    private val main_category = itemView.findViewById<TextView>(R.id.many_main_category)
    private val sub_category = itemView.findViewById<TextView>(R.id.many_sub_category)
    private val datetime = itemView.findViewById<TextView>(R.id.many_datetime)
    private val thumbnail = itemView.findViewById<RecyclerView>(R.id.many_thumbnail_RecyclerView)
    private val price = itemView.findViewById<TextView>(R.id.many_price)

    private lateinit var dataAdapter: ReceiveThumbanailRecyclerViewAdapter

    private val dec = DecimalFormat("#,###")

    fun bindWithView(item: ReceiveAllDate, context: Context, onClickListener: View.OnClickListener) {

        main_category.setText(item.main_category)
        price.text = dec.format(item.price)

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
            thumbnail.visibility = View.VISIBLE
            dataAdapter = ReceiveThumbanailRecyclerViewAdapter(item.thumbnail!!)
            thumbnail.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL, false
            )
            thumbnail.adapter = dataAdapter
        }
    }

}