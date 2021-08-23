package com.weare.wearecompany.ui.detail.studio.reservation

import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.hotpick.data.room
import kotlinx.android.synthetic.main.item_reservation_room.view.*
import kotlinx.android.synthetic.main.item_studio_datail_room.view.*


class ReservationStrudioRecyclerViewAdapter(private val data: ArrayList<room>) : RecyclerView.Adapter<ReservationStrudioViwHodel>() {

    var postcheck = -1
    var checktype = false
    private val selectedItems = SparseBooleanArray()
    //ClickListener
    interface OnItemClickListener {
        fun onClick(v: View, position: Int, Item: room)
    }

    private lateinit var itemClickListener: OnItemClickListener

    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationStrudioViwHodel {
        return ReservationStrudioViwHodel(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_reservation_room, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ReservationStrudioViwHodel, position: Int) {
        val item = this.data[position]

        if (postcheck == position) {
            checktype = true
        } else {
            checktype = false
        }


        holder.apply {
            bindWith(item, checktype,View.OnClickListener {
            })
            holder.itemView.studio_not_list_image.setOnClickListener {
                itemClickListener.onClick(it,position,item)
                postcheck = position
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return this.data.size
    }
}