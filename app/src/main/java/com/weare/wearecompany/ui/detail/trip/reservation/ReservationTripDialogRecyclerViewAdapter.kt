package com.weare.wearecompany.ui.detail.trip.reservation

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.ui.detail.photo.reservation.ReservationPhotoDialogRecyclerViewAdapter
import com.weare.wearecompany.ui.detail.photo.reservation.ReservationPhotoDialogViewHodel
import kotlinx.android.synthetic.main.expert_reservation_check_dialog.view.*
import kotlinx.android.synthetic.main.studio_reservation_check_dialog.view.*
import java.text.DecimalFormat

class ReservationTripDialogRecyclerViewAdapter (private val data: ArrayList<String>) : RecyclerView.Adapter<ReservationPhotoDialogViewHodel>() {
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