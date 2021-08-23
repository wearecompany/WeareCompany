package com.weare.wearecompany.ui.detail.studio

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.hotpick.data.room
import kotlinx.android.synthetic.main.item_studio_datail_room.view.*

class  DatailRoomRecyclerAdapter (private val data: ArrayList<room>) : RecyclerView.Adapter<DatailRoomViewHodel>(){

    //ClickListener
    interface OnItemClickListener {
        fun onClick(v: View, position: Int, Item: room)
    }

    private lateinit var itemClickListener: OnItemClickListener

    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DatailRoomViewHodel {
       return DatailRoomViewHodel(
               LayoutInflater
                       .from(parent.context)
                       .inflate(R.layout.item_studio_datail_room, parent, false))
    }

    override fun onBindViewHolder(holder: DatailRoomViewHodel, position: Int) {
       val item = this.data[position]

        holder.apply {
            bindWith(item, View.OnClickListener {
            })
            holder.itemView.room_image.setOnClickListener {
                itemClickListener.onClick(it,position,item)
            }
        }
    }

    override fun getItemCount(): Int {
        return this.data.size
    }
}