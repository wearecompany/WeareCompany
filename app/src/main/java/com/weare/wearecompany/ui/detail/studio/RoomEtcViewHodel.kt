package com.weare.wearecompany.ui.detail.studio

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.utils.Constants

class RoomEtcViewHodel(v: View): RecyclerView.ViewHolder(v) {

    private val TextView = itemView.findViewById<TextView>(R.id.room_etc)

    fun bindWithView(Item: String) {
        Log.d(Constants.TAG, "datailInfoViewHolder - bindWithView() called")

        TextView.setText(Item)
    }
}