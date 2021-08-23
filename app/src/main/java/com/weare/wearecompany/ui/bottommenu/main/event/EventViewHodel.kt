package com.weare.wearecompany.ui.bottommenu.main.event

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
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
import com.weare.wearecompany.data.main.data.event

class EventViewHodel(v: View): RecyclerView.ViewHolder(v) {


    private val image = itemView.findViewById<ImageView>(R.id.home_event_image)
    private val title = itemView.findViewById<TextView>(R.id.home_event_title)
    private val sub = itemView.findViewById<TextView>(R.id.home_event_sub)
    private val time = itemView.findViewById<TextView>(R.id.home_event_time)
    private val goods = itemView.findViewById<TextView>(R.id.home_event_goods)
    private val person = itemView.findViewById<TextView>(R.id.home_event_person)
    private val goods_layout = itemView.findViewById<LinearLayout>(R.id.home_event_goods_layout)


    fun bindWith(Item: event, onClickListener: View.OnClickListener) {

        var multiTransformation = MultiTransformation(CenterCrop(), RoundedCorners(40))
        val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()


        Glide.with(MyApplication.instance)
            .load(Item.image)
            .fallback(R.drawable.not_load_image)
            .transition(DrawableTransitionOptions.withCrossFade(factory))
            .apply(RequestOptions.bitmapTransform(multiTransformation))
            .into(DrawableImageViewTarget(image))

        title.text = Item.title
        sub.text = Item.sub
        time.text = Item.time

        if (Item.goods == "") {
            goods_layout.visibility = View.INVISIBLE
        } else {
            goods.text = Item.goods
            person.text = Item.person
        }


    }
}