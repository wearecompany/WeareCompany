package com.weare.wearecompany.ui.listcontainer.studio

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
import com.weare.wearecompany.data.main.data.studio
import com.weare.wearecompany.utils.Constants
import com.weare.wearecompany.MyApplication

class StudioViewHodel(v : View): RecyclerView.ViewHolder(v) {

    private val ImageView = itemView.findViewById<ImageView>(R.id.studio_list_image)
    private val Text_title = itemView.findViewById<TextView>(R.id.studio_list_title)
    private val Text_address = itemView.findViewById<TextView>(R.id.studio_list_address)
    private val Text_sub = itemView.findViewById<TextView>(R.id.studio_list_sub)
    private val Text_price = itemView.findViewById<TextView>(R.id.studio_list_price)
    private val grade_layout = itemView.findViewById<LinearLayout>(R.id.studio_grade_layout)
    private val grade = itemView.findViewById<TextView>(R.id.studio_list_grade)

    fun bindWithView(studioItem: studio, onClickListener: View.OnClickListener) {
        Log.d(Constants.TAG, "StudioItemViewHolder - bindWithView() called")

        var multiTransformation = MultiTransformation(CenterCrop(), RoundedCorners(90))
        val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

        Glide.with(MyApplication.instance)
            .load(studioItem.image)
            .fallback(R.drawable.not_load_image)
            .transition(DrawableTransitionOptions.withCrossFade(factory))
            .apply(RequestOptions.bitmapTransform(multiTransformation))
            .into(DrawableImageViewTarget(ImageView))

        Text_title.setText(studioItem.title)
        Text_address.setText(studioItem.address)
        Text_sub.setText(studioItem.sub)
        Text_price.setText(studioItem.price)

        if (studioItem.grade != "") {
            grade_layout.visibility = View.VISIBLE
            grade.text = studioItem.grade.toString()
        }
    }
}