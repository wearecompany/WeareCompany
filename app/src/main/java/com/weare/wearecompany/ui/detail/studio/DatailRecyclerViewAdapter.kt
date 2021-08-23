package com.weare.wearecompany.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.hotpick.datail

class DatailRecyclerViewAdapter (private val data: ArrayList<datail>) : RecyclerView.Adapter<DatailViewHodel>(){

    // 뷰홀더와 레이아웃 연결
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DatailViewHodel {
        return DatailViewHodel(
                LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.item_studio_image, parent, false))
    }

    override fun onBindViewHolder(holder: DatailViewHodel, position: Int) {
        val item = this.data[0].images[position]

        holder.apply {
            bindWithView(item)
        }
    }

    override fun getItemCount(): Int {
        return this.data[0].images.size
    }
}
