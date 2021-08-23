package com.weare.wearecompany.ui.bottommenu.main.guide

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.weare.wearecompany.R
import com.weare.wearecompany.data.main.data.guidelist
import com.weare.wearecompany.utils.Constants
import com.weare.wearecompany.MyApplication

class GuideListViewHodel(v: View): RecyclerView.ViewHolder(v) {

    private val list_title = itemView.findViewById<TextView>(R.id.guide_list_title)
    private val list_sub = itemView.findViewById<TextView>(R.id.guide_list_sub)
    private val list_image = itemView.findViewById<ImageView>(R.id.guide_list_image)

    fun bindWithView(Item: guidelist, postion: Int, onClickListener: View.OnClickListener) {
        Log.d(Constants.TAG, "GuideListItemViewHolder - bindWithView() called")

        var multiTransformation = MultiTransformation(CenterCrop(), RoundedCorners(20)) //글라이더 크기및 테두리 변환
        Glide.with(MyApplication.instance)
            .load(Item.image)
            .apply(RequestOptions.bitmapTransform(multiTransformation))
            .into(list_image)

        list_title.setText(Item.title)
        list_sub.setText(Item.sub)
    }
}