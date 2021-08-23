package com.weare.wearecompany.ui.bottommenu.estimate.receive

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.ui.bottommenu.estimate.send.SendThumbanilViewHodel

class ReceiveThumbanailRecyclerViewAdapter(private val datalist:List<String>):RecyclerView.Adapter<ReceiveThumbanailViewHodel>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceiveThumbanailViewHodel {
        return ReceiveThumbanailViewHodel (
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_send_thumbanil, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ReceiveThumbanailViewHodel, position: Int) {
        val item = datalist[position]
        holder.apply {
            bindWithView(item)
        }
    }

    override fun getItemCount(): Int {
        return datalist.size
    }
}