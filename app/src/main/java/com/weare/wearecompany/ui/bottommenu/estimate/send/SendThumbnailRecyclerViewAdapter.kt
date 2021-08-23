package com.weare.wearecompany.ui.bottommenu.estimate.send

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R

class SendThumbnailRecyclerViewAdapter(private val datalist:List<String>): RecyclerView.Adapter<SendThumbanilViewHodel>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SendThumbanilViewHodel {
        return SendThumbanilViewHodel (
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_send_thumbanil, parent, false)
                )
    }

    override fun onBindViewHolder(holder: SendThumbanilViewHodel, position: Int) {
        val item = datalist[position]
        holder.apply {
            bindWithView(item)
        }
    }

    override fun getItemCount(): Int {
        return datalist.size
    }
}