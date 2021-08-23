package com.weare.wearecompany.ui.detail.photo.reservation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.ui.detail.trip.reservation.ReservationTripDialogViewHodel

class ReservationPhotoDialogRecyclerViewAdapter(private val data: ArrayList<String>) : RecyclerView.Adapter<ReservationTripDialogViewHodel>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReservationTripDialogViewHodel {
        return ReservationTripDialogViewHodel(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_studio_reservation, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ReservationTripDialogViewHodel, position: Int) {
        val item = this.data[position]

        holder.apply {
            bindWith(item)
        }
    }

    override fun getItemCount(): Int {
        return this.data.size
    }
}