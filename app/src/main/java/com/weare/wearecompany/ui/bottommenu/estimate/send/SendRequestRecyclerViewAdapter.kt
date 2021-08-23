package com.weare.wearecompany.ui.bottommenu.estimate.send

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.bottomnav.estimate.send.Sendpage

class SendRequestRecyclerViewAdapter(private val dataList:ArrayList<Sendpage>): RecyclerView.Adapter<SendRequestViewHodel>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SendRequestViewHodel {
        return SendRequestViewHodel(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_send_datail_thumbanil, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SendRequestViewHodel, position: Int) {
        val item = dataList[0].thumbnail[position]
        holder.apply {
            bindWithView(item)
        }
    }

    override fun getItemCount(): Int {
        return dataList[0].thumbnail.size
    }
}