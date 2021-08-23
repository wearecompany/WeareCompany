package com.weare.wearecompany.ui.bottommenu.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.main.data.list

class HomeLIstRecyeclerViewAdapter(private val homelistList: ArrayList<list>, val type:Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //ClickListener
    interface OnItemClickListener {
        fun onClick(v: View, position: Int, homelistItem: list)
    }

    private lateinit var itemClickListener : OnItemClickListener

    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }


    // 뷰홀더와 레이아웃 연결
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(type) {
            1 -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_home_model, parent, false)
                return HomeLIstExpertViewHoder(headerView)
            }
            2 -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_home_model, parent, false)
                return HomeLIstExpertViewHoder(headerView)
            }
            3 -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_home_model, parent, false)
                return HomeLIstExpertViewHoder(headerView)
            }
            else -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_home_studio, parent, false)
                return HomeLIstStudioViewHoder(headerView)
            }
        }
    }

    // 보여줄 목록의 갯수
    override fun getItemCount(): Int {
        return homelistList.size
    }

    // 뷰가 묶였을때 데이터를 뷰홀더에 넘겨준다.
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = homelistList[position]

        when(holder) {
            is HomeLIstExpertViewHoder -> {
                holder.itemView.setOnClickListener {
                        itemClickListener.onClick(it, position, item)
                }
                holder.apply {
                    bindWithView(item, type,View.OnClickListener {

                    })
                }
            }
            is HomeLIstStudioViewHoder -> {
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

    override fun getItemViewType(position: Int): Int {
        // 아이템의 처음과 마지막은 각각 헤더와 푸터를 의미함
        return type
    }
}