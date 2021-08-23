package com.weare.wearecompany.ui.bottommenu.estimate.progress

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R

class ProgressThumbanailRecyclerViewAdapter(private val datalist:List<String>):
    RecyclerView.Adapter<ProgressThumbanailViewHodel>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgressThumbanailViewHodel {
        return ProgressThumbanailViewHodel (
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_send_thumbanil, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProgressThumbanailViewHodel, position: Int) {
        val item = datalist[position]
        holder.apply {
            bindWithView(item)
        }
    }

    override fun getItemCount(): Int {
        return datalist.size
    }
}