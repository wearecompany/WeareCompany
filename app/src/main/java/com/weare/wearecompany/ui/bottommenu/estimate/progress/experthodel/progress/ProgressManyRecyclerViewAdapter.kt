package com.weare.wearecompany.ui.bottommenu.estimate.progress.experthodel.progress

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.bottomnav.estimate.progress.data.ProgressManyPage
import com.weare.wearecompany.data.bottomnav.estimate.send.Sendpage
import com.weare.wearecompany.ui.bottommenu.estimate.send.SendRequestViewHodel

class ProgressManyRecyclerViewAdapter(private val dataList:ArrayList<ProgressManyPage>): RecyclerView.Adapter<ProgressManyViewHodel>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgressManyViewHodel {
        return ProgressManyViewHodel(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_send_datail_thumbanil, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProgressManyViewHodel, position: Int) {
        val item = dataList[0].thumbnail[position]
        holder.apply {
            bindWithView(item)
        }
    }

    override fun getItemCount(): Int {
        return dataList[0].thumbnail.size
    }
}