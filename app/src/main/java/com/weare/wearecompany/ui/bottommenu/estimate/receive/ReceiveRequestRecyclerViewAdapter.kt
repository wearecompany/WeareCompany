package com.weare.wearecompany.ui.bottommenu.estimate.receive

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.bottomnav.estimate.receive.Receivepage

class ReceiveRequestRecyclerViewAdapter(private val dataList:ArrayList<Receivepage>): RecyclerView.Adapter<ReceiveRequestViewHodel>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceiveRequestViewHodel {
        return ReceiveRequestViewHodel(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_send_datail_thumbanil, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ReceiveRequestViewHodel, position: Int) {
        val item = dataList[0].thumbnail[position]
        holder.apply {
            bindWithView(item)
        }
    }

    override fun getItemCount(): Int {
        return dataList[0].thumbnail.size
    }
}