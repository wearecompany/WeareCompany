package com.weare.wearecompany.ui.bottommenu.mypage.request

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R

class OnClickImageHodel(v: View) : RecyclerView.ViewHolder(v) {

    private val image = itemView.findViewById<ImageView>(R.id.onclick_image)


    fun bindWithView(deta: String) {

        var multiTransformation = MultiTransformation(CenterCrop(), RoundedCorners(20))

        Glide.with(MyApplication.instance)
            .load(deta)
            .apply(RequestOptions.bitmapTransform(multiTransformation))
            .into(image)

    }
}