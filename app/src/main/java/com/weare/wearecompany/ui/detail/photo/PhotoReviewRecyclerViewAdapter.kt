package com.weare.wearecompany.ui.detail.photo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.hotpick.data.review

class PhotoReviewRecyclerViewAdapter(private val data: ArrayList<review>) : RecyclerView.Adapter<PhotoReviewViewHodel>() {

    // 뷰홀더와 레이아웃 연결
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoReviewViewHodel {
        return PhotoReviewViewHodel(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_photo_review, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PhotoReviewViewHodel, position: Int) {
        val item = data[position]

        holder.apply {
            bindWithView(item)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}