package com.weare.wearecompany.ui.bottommenu.estimate.estimateDatail

import android.content.Context
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R

class EstimateDatailDialogViewHodel(v: View): RecyclerView.ViewHolder(v) {

    private val ImageView = itemView.findViewById<ImageView>(R.id.estimate_image)
    private val removeView = itemView.findViewById<ImageView>(R.id.photo_clean)

    fun bindWithView(Item: Uri) {

        removeView.visibility = View.GONE

        var multiTransformation = MultiTransformation(CenterCrop(), RoundedCorners(20))

        Glide.with(MyApplication.instance)
            .load(Item)
            .apply(RequestOptions.bitmapTransform(multiTransformation))
            .into(ImageView)

    }
}