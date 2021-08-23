package com.weare.wearecompany.ui.bottommenu.main.weekly

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.main.data.hotpick
import com.weare.wearecompany.ui.bottommenu.main.home.HomeLIstStudioViewHoder

class WeeklyRecyclerViewAdapter(private val homelistList: ArrayList<hotpick>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //ClickListener
    interface OnItemClickListener {
        fun onClick(v: View, position: Int, hotpickItem: hotpick)
    }

    private lateinit var itemClickListener : OnItemClickListener

    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }


    // 뷰홀더와 레이아웃 연결
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType) {
            1 -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_weekly_model, parent, false)
                return WeeklyExpertViewHodel(headerView)
            }
            2 -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_weekly_model, parent, false)
                return WeeklyExpertViewHodel(headerView)
            }
            3 -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_weekly_model, parent, false)
                return WeeklyExpertViewHodel(headerView)
            }
            else -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_weekly_studio, parent, false)
                return WeeklyStudioViewHodel(headerView)
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
            is WeeklyExpertViewHodel -> {
                holder.itemView.setOnClickListener {
                    itemClickListener.onClick(it, position, item)
                }
                holder.apply {
                    bindWithView(item, View.OnClickListener {

                    })
                }
            }
            is WeeklyStudioViewHodel -> {
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
        return homelistList[position].target_type!!
    }
}