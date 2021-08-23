package com.weare.wearecompany.ui.listcontainer.studio

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.List.model.photographer.data.photo
import com.weare.wearecompany.data.main.data.newStudio
import com.weare.wearecompany.ui.listcontainer.photographer.PhotographerViewHodel

class StudioNewRecyclerViewAdapter(private val photoList: ArrayList<newStudio>) : RecyclerView.Adapter<StudioNewViewHodel>() {

    //ClickListener
    interface OnItemClickListener {
        fun onClick(v: View, position: Int, hotpickItem: newStudio)
    }

    private lateinit var itemClickListener : OnItemClickListener

    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudioNewViewHodel {
        return StudioNewViewHodel(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_list_new_studio, parent, false))
    }

    override fun onBindViewHolder(holder: StudioNewViewHodel, position: Int) {
        val item = this.photoList[position]

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position, item)
        }

        holder.apply {
            bindWithView(item, View.OnClickListener {

            })
        }
    }

    override fun getItemCount(): Int {
        return photoList.size
    }
}