package com.weare.wearecompany.ui.bottommenu.estimate.send

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.ui.bottommenu.estimate.send.experthodel.SendExpertViewHodel

class SendTagRecyclerViewAdapter(private val datalist:List<String>): RecyclerView.Adapter<SendTagViewHodel>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SendTagViewHodel {
        return SendTagViewHodel(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_tag, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SendTagViewHodel, position: Int) {
        val item = datalist[position]
        holder.apply {
            bindWithView(item)
        }
    }

    override fun getItemCount(): Int {
        return datalist.size
    }

}