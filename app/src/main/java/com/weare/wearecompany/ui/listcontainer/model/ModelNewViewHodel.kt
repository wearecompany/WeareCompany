package com.weare.wearecompany.ui.listcontainer.model

import android.util.Log
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
import com.weare.wearecompany.data.model.list.data.newlist
import com.weare.wearecompany.utils.Constants

class ModelNewViewHodel(v: View) : RecyclerView.ViewHolder(v) {

    private val image = itemView.findViewById<ImageView>(R.id.new_model_image)
    private val name = itemView.findViewById<TextView>(R.id.new_model_name)


    fun bindWithView(item: newlist, onClickListener: View.OnClickListener) {
        Log.d(Constants.TAG, "ModelItemViewHolder - bindWithView() called")

        var multiTransformation = MultiTransformation(CenterCrop(), RoundedCorners(150))
        val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

        Glide.with(MyApplication.instance)
            .load(item.thumbnail)
            .fallback(R.drawable.not_load_image)
            .transition(DrawableTransitionOptions.withCrossFade(factory))
            .apply(RequestOptions.bitmapTransform(multiTransformation))
            .into(DrawableImageViewTarget(image))

        name.text = item.name

    }
}