package com.weare.wearecompany.ui.detail.photo.reservation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R

class ReservationPhotoDialogViewHodel(v: View): RecyclerView.ViewHolder(v) {

    private val day = itemView.findViewById<TextView>(R.id.day_text)
    fun bindWith(Item: String) {

        day.text = Item

    }
}