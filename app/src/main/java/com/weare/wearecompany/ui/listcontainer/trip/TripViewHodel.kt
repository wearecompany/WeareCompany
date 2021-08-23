package com.weare.wearecompany.ui.listcontainer.trip

import android.util.Log
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
import com.weare.wearecompany.R
import com.weare.wearecompany.data.List.trip.data.trip
import com.weare.wearecompany.utils.Constants
import com.weare.wearecompany.MyApplication

class TripViewHodel(v: View) : RecyclerView.ViewHolder(v) {

    private val ImageView = itemView.findViewById<ImageView>(R.id.trip_list_image)
    private val Text_title = itemView.findViewById<TextView>(R.id.trip_list_title)
    private val Text_sub_category = itemView.findViewById<TextView>(R.id.trip_list_sub_category)
    private val Text_sub = itemView.findViewById<TextView>(R.id.trip_list_sub)
    private val Text_price = itemView.findViewById<TextView>(R.id.trip_list_price)
    private val grade_layout = itemView.findViewById<LinearLayout>(R.id.trip_grade_layout)
    private val grade = itemView.findViewById<TextView>(R.id.trip_list_grade)

    fun bindWithView(Item: trip, onClickListener: View.OnClickListener) {
        Log.d(Constants.TAG, "BeautyItemViewHolder - bindWithView() called")

        val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
        var multiTransformation = MultiTransformation(CenterCrop(), RoundedCorners(90))
        Glide.with(MyApplication.instance)
            .load(Item.image)
            .fallback(R.drawable.not_load_image)
            .transition(DrawableTransitionOptions.withCrossFade(factory))
            .apply(RequestOptions.bitmapTransform(multiTransformation))
            .into(DrawableImageViewTarget(ImageView))

        Text_title.setText(Item.title)
        Text_sub_category.setText(Item.sub_category)
        Text_sub.setText(Item.sub)
        Text_price.setText(Item.price)

        if (Item.grade != "") {
            grade_layout.visibility = View.VISIBLE
            grade.text = Item.grade.toString()
        }
    }
}
