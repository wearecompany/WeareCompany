package com.weare.wearecompany.ui.detail.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.hotpick.data.review
import com.weare.wearecompany.ui.detail.rent.RentReviewViewHodel

class ModelReviewRecyclerViewAdapter (private val data: ArrayList<review>) : RecyclerView.Adapter<ModelReviewViewHodel>() {

    // 뷰홀더와 레이아웃 연결
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelReviewViewHodel {
        return ModelReviewViewHodel(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_model_review, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ModelReviewViewHodel, position: Int) {
        val item = data[position]

        holder.apply {
            bindWithView(item)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}