package com.weare.wearecompany.ui.listcontainer.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.model.list.data.newlist

class ModelNewRecyclerViewAdapter(private val modelList: ArrayList<newlist>) : RecyclerView.Adapter<ModelNewViewHodel>() {


    //ClickListener
    interface OnItemClickListener {
        fun onClick(v: View, position: Int, Item: newlist)
    }

    private lateinit var itemClickListener : OnItemClickListener

    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelNewViewHodel {
        return ModelNewViewHodel(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_list_new_model, parent, false))
    }

    override fun onBindViewHolder(holder: ModelNewViewHodel, position: Int) {
        val item = this.modelList[position]

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position, item)
        }

        holder.apply {
            bindWithView(item, View.OnClickListener {
            })
        }


    }

    override fun getItemCount(): Int {
        return modelList.size
    }
}