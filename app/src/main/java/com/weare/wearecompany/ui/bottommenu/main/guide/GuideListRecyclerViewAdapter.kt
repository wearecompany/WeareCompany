package com.weare.wearecompany.ui.bottommenu.main.guide

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.main.data.guidelist

class GuideListRecyclerViewAdapter(private val guideList: ArrayList<guidelist>): RecyclerView.Adapter<GuideListViewHodel>() {

    //ClickListener
    interface OnItemClickListener {
        fun onClick(v: View, position: Int, Item: guidelist)
    }

    private lateinit var itemClickListener : OnItemClickListener

    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuideListViewHodel {
        return GuideListViewHodel(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_guide_list, parent, false))
    }

    override fun onBindViewHolder(holder: GuideListViewHodel, position: Int) {
        if (position < 15) {
            val item = guideList[position]

            holder.itemView.setOnClickListener {
                itemClickListener.onClick(it, position, item)
            }

            holder.apply {
                bindWithView(item, position, View.OnClickListener {
                })
            }
        }
    }

    override fun getItemCount(): Int {
        return guideList.size
    }

}