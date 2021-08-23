package com.weare.wearecompany.ui.bottommenu.estimate.progress.experthodel.refund

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.bottomnav.estimate.progress.data.ProgressManyPage


class RefundManyThumbnailListRecyclerViewAdapter(private val dataList:ArrayList<ProgressManyPage>): RecyclerView.Adapter<RefundManyThumbnailListViewHodel>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RefundManyThumbnailListViewHodel {
        return RefundManyThumbnailListViewHodel(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_send_datail_thumbanil, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RefundManyThumbnailListViewHodel, position: Int) {
        val item = dataList[0].thumbnail[position]
        holder.apply {
            bindWithView(item)
        }
    }

    override fun getItemCount(): Int {
        return dataList[0].thumbnail.size
    }
}