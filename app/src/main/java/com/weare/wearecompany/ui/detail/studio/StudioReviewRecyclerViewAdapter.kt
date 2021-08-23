package com.weare.wearecompany.ui.detail.studio

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.hotpick.data.review


class StudioReviewRecyclerViewAdapter(private val data: ArrayList<review>) : RecyclerView.Adapter<StudioReviewViewHodel>(){

    // 뷰홀더와 레이아웃 연결
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudioReviewViewHodel {
        return StudioReviewViewHodel(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_studio_review, parent, false))
    }

    override fun onBindViewHolder(holder: StudioReviewViewHodel, position: Int) {
        val item = data[position]

        holder.apply {
            bindWithView(item)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}