package com.weare.wearecompany.ui.detail

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.weare.wearecompany.R
import com.weare.wearecompany.utils.Constants
import com.weare.wearecompany.MyApplication

class DatailViewHodel(v: View): RecyclerView.ViewHolder(v) {

    private val ImageView = itemView.findViewById<ImageView>(R.id.datail_image)

    fun bindWithView(Item: String) {
        Log.d(Constants.TAG, "datailItemViewHolder - bindWithView() called")

        Glide.with(MyApplication.instance)
            .load(Item)
            .into(ImageView)

    }
}