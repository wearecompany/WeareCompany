package com.weare.wearecompany.ui.detail.trip

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.hotpick.data.review
import com.weare.wearecompany.ui.detail.photo.PhotoReviewViewHodel

class TripReviewRecyclerViewAdapter (private val data: ArrayList<review>) : RecyclerView.Adapter<TripReviewViewHodel>() {

    // 뷰홀더와 레이아웃 연결
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripReviewViewHodel {
        return TripReviewViewHodel(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_trip_review, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TripReviewViewHodel, position: Int) {
        val item = data[position]

        holder.apply {
            bindWithView(item)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}