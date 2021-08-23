package com.weare.wearecompany.ui.detail.studio.reservation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R

class ReservationStudioDialogRecyclerViewAdapter(private val data: ArrayList<String>) : RecyclerView.Adapter<ReservationStudioDialogViewHodel>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationStudioDialogViewHodel {
        return ReservationStudioDialogViewHodel(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_studio_reservation, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ReservationStudioDialogViewHodel, position: Int) {
        val item = this.data[position]

        holder.apply {
            bindWith(item)
        }
    }

    override fun getItemCount(): Int {
        return this.data.size
    }
}