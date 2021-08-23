package com.weare.wearecompany.ui.detail.studio

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.hotpick.datail

class DatailInfoRecyclerAdapter (private val data: ArrayList<datail>) : RecyclerView.Adapter<DatailInfoViewHodel>(){

    // 뷰홀더와 레이아웃 연결
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DatailInfoViewHodel {
        return DatailInfoViewHodel(
                LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.item_studio_info, parent, false))
    }

    override fun onBindViewHolder(holder: DatailInfoViewHodel, position: Int) {
        val item = this.data[0].info[position]

        holder.apply {
            bindWithView(item)
        }
    }

    override fun getItemCount(): Int {
        return this.data[0].info.size
    }
}