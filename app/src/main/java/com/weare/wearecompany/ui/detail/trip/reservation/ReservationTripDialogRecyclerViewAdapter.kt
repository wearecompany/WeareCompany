package com.weare.wearecompany.ui.detail.trip.reservation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.ui.detail.photo.reservation.ReservationPhotoDialogViewHodel


class ReservationTripDialogRecyclerViewAdapter(private val data: ArrayList<String>) :
    RecyclerView.Adapter<ReservationPhotoDialogViewHodel>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReservationPhotoDialogViewHodel {
        return ReservationPhotoDialogViewHodel(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_studio_reservation, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ReservationPhotoDialogViewHodel, position: Int) {
        val item = this.data[position]

        holder.apply {
            bindWith(item)
        }
    }

    override fun getItemCount(): Int {
        return this.data.size
    }
}