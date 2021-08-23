package com.weare.wearecompany.ui.bottommenu.estimate.send

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R

class SendThumbanilViewHodel(v: View):RecyclerView.ViewHolder(v) {

    private val ImageView = itemView.findViewById<ImageView>(R.id.send_thumbnail)
    fun bindWithView(Item: String,) {

        var multiTransformation = MultiTransformation(CenterCrop(), RoundedCorners(10))

        Glide.with(MyApplication.instance)
            .load(Item)
            .skipMemoryCache(true)
            .placeholder(R.drawable.loading_image)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .fallback(R.drawable.not_load_image)
            .apply(RequestOptions.bitmapTransform(multiTransformation))
            .into(ImageView)

    }
}