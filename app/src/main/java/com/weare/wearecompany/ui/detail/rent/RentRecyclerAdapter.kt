package com.weare.wearecompany.ui.detail.rent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.photo.dateil.list.date.dateilRent

class RentRecyclerAdapter(private val date: ArrayList<dateilRent>) :
    RecyclerView.Adapter<RentViewHodel>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RentViewHodel {
        return RentViewHodel(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_studio_image, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RentViewHodel, position: Int) {
        val item = this.date[0].images[position]

        holder.apply {
            bindWithView(item)
        }
    }

    override fun getItemCount(): Int {
        return date[0].images.size
    }
}