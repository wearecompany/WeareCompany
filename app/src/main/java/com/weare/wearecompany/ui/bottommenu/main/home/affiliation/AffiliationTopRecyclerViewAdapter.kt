package com.weare.wearecompany.ui.bottommenu.main.home.affiliation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.division.data.recent
import com.weare.wearecompany.data.main.affiliationTop
import com.weare.wearecompany.data.main.data.list
import com.weare.wearecompany.ui.bottommenu.main.home.HomeLIstExpertViewHoder
import com.weare.wearecompany.ui.bottommenu.main.home.HomeLIstStudioViewHoder

class AffiliationTopRecyclerViewAdapter(private val homelistList: ArrayList<recent>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var item:recent
    //ClickListener
    interface OnItemClickListener {
        fun onClick(v: View, position: Int, homelistItem: recent)
    }

    private lateinit var itemClickListener : OnItemClickListener

    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }




    // 뷰홀더와 레이아웃 연결
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType) {
            0 -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_affilition_top_one, parent, false)
                return AffiliationTopOneViewHodel(headerView)
            }

            else -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_affilition_top, parent, false)
                return AffiliationTopViewHodel(headerView)
            }
        }
    }

    // 보여줄 목록의 갯수
    override fun getItemCount(): Int {
        return homelistList.size + 1
    }

    // 뷰가 묶였을때 데이터를 뷰홀더에 넘겨준다.
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == 0) {
            item = homelistList[position]
        } else {
            item = homelistList[position-1]
        }


        when(holder) {
            is AffiliationTopOneViewHodel -> {
                holder.itemView.setOnClickListener {
                    itemClickListener.onClick(it,position,item)
                }
                holder.apply {
                    bindWithView(item,View.OnClickListener{

                    })
                }
            }
            is AffiliationTopViewHodel -> {

                holder.apply {
                    bindWithView(item)
                }
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        // 아이템의 처음과 마지막은 각각 헤더와 푸터를 의미함
        return position
    }
}