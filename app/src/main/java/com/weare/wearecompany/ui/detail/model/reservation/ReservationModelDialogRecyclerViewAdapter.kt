package com.weare.wearecompany.ui.detail.model.reservation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R

class ReservationModelDialogRecyclerViewAdapter (private val data: ArrayList<String>) : RecyclerView.Adapter<ReservationModelDialogViewHodel>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReservationModelDialogViewHodel {
        return ReservationModelDialogViewHodel(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_studio_reservation, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ReservationModelDialogViewHodel, position: Int) {
        val item = this.data[position]

        holder.apply {
            bindWith(item)
        }
    }

    override fun getItemCount(): Int {
        return this.data.size
    }
}