package com.weare.wearecompany.ui.listcontainer.model

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.model.list.ChangeList
import com.weare.wearecompany.data.model.list.data.Dmodel
import com.weare.wearecompany.data.model.list.data.Mstate
import com.weare.wearecompany.data.model.list.data.newlist
import kotlinx.android.synthetic.main.item_list_model_top.view.*
import kotlinx.android.synthetic.main.item_model_list.view.*
import kotlinx.android.synthetic.main.item_model_list.view.model_list_image

class ModelRecyclerViewAdapter(private val modelList: ArrayList<Dmodel>) :
    RecyclerView.Adapter<ModelViewHodel>() {
    var mSize = 0
    fun getSize(): Int {
        return mSize
    }

    fun setSize(postition: Int) {
        mSize = postition
    }

    fun addItem(data: Dmodel) {
        modelList.add(data)
        setSize(modelList.size)
        //갱신처리 반드시 해야함
        notifyDataSetChanged()
    }


    //ClickListener
    interface OnItemClickListener {
        fun onClick(v: View, position: Int, hotpickItem: Dmodel)
    }

    private lateinit var itemClickListener: OnItemClickListener

    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ModelViewHodel {

        return ModelViewHodel(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_model_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ModelViewHodel, position: Int) {
        val item = modelList[position]
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