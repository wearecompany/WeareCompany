package com.weare.wearecompany.ui.bottommenu.main.contents

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.main.data.contents
import com.weare.wearecompany.data.main.data.list
import com.weare.wearecompany.ui.bottommenu.main.home.HomeLIstExpertViewHoder
import com.weare.wearecompany.ui.bottommenu.main.home.HomeLIstStudioViewHoder

class ContentsRecyclerViewAdapter(private val contentsList: ArrayList<contents>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //ClickListener
    interface OnItemClickListener {
        fun onClick(v: View, position: Int, homelistItem: contents)
    }

    private lateinit var itemClickListener : OnItemClickListener

    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType) {
            0 -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_contents_top, parent, false)
                return ContentsTopViewHodel(headerView)
            }
            else -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_contents_bottom, parent, false)
                return ContentsBottomViewHodel(headerView)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = contentsList[position]

        when(holder) {
            is ContentsTopViewHodel -> {
                holder.itemView.setOnClickListener {
                    itemClickListener.onClick(it, position, item)
                }
                holder.apply {
                    bindWithView(item, View.OnClickListener {

                    })
                }
            }
            is ContentsBottomViewHodel -> {
                holder.itemView.setOnClickListener {
                    itemClickListener.onClick(it, position, item)
                }
                holder.apply {
                    bindWithView(item, View.OnClickListener {

                    })
                }
            }
        }
    }

    override fun getItemCount(): Int = contentsList.size

    override fun getItemViewType(position: Int): Int = position
}