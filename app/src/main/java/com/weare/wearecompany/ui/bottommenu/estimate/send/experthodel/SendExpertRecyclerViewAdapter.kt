package com.weare.wearecompany.ui.bottommenu.estimate.send.experthodel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R

class SendExpertRecyclerViewAdapter(private val datalist:List<String>, private val dt_status:Boolean): RecyclerView.Adapter<SendExpertViewHodel>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SendExpertViewHodel {
        return SendExpertViewHodel(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_expter_reservation_dt, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SendExpertViewHodel, position: Int) {
        val item = datalist[position]
        holder.apply {
            bindWithView(item,dt_status)
        }
    }

    override fun getItemCount(): Int {
        return datalist.size
    }

}