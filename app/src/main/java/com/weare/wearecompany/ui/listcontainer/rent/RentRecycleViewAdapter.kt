package com.weare.wearecompany.ui.listcontainer.rent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.List.rent.data.rent

class RentRecycleViewAdapter(private val rentList: ArrayList<rent>) : RecyclerView.Adapter<RentViewHodel>() {

    //ClickListener
    interface OnItemClickListener {
        fun onClick(v: View, position: Int, hotpickItem: rent)
    }

    private lateinit var itemClickListener : OnItemClickListener

    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RentViewHodel {
        return RentViewHodel(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_rent_list, parent, false))
    }

    override fun onBindViewHolder(holder: RentViewHodel, position: Int) {
        val item = this.rentList[position]

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position, item)
        }

        holder.apply {
            bindWithView(item, View.OnClickListener {
            })
        }
    }

    override fun getItemCount(): Int {
      return  rentList.size
    }
}