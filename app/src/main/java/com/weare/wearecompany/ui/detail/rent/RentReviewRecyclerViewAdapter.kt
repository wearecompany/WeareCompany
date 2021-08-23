package com.weare.wearecompany.ui.detail.rent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.hotpick.data.review

class RentReviewRecyclerViewAdapter (private val data: ArrayList<review>) : RecyclerView.Adapter<RentReviewViewHodel>() {

    // 뷰홀더와 레이아웃 연결
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RentReviewViewHodel {
        return RentReviewViewHodel(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_rent_review, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RentReviewViewHodel, position: Int) {
        val item = data[position]

        holder.apply {
            bindWithView(item)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}