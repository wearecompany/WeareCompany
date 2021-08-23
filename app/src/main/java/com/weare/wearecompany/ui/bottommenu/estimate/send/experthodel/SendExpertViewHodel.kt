package com.weare.wearecompany.ui.bottommenu.estimate.send.experthodel

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R

class SendExpertViewHodel(v: View): RecyclerView.ViewHolder(v) {

    private val dttext = itemView.findViewById<TextView>(R.id.day_text)
    fun bindWithView(Item: String,type:Boolean) {

        dttext.text = Item

    }
}