package com.weare.wearecompany.ui.detail.studio

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.hotpick.data.room
import com.weare.wearecompany.data.hotpick.datail

class RoomEtcRecyclerAdapter (private val data: room ) : RecyclerView.Adapter<RoomEtcViewHodel>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomEtcViewHodel {
        return RoomEtcViewHodel(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_room_etc, parent, false))
    }

    override fun onBindViewHolder(holder: RoomEtcViewHodel, position: Int) {
        val item = data.etc[position]

        holder.apply {
            bindWithView(item)
        }

    }

    override fun getItemCount(): Int {
        return this.data.etc.size
    }
}