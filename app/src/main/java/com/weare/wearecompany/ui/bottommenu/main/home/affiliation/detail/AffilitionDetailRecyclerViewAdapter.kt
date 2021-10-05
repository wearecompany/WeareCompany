package com.weare.wearecompany.ui.bottommenu.main.home.affiliation.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.division.DivisionPage
import com.weare.wearecompany.data.division.data.division
import com.weare.wearecompany.data.main.affiliationTop
import com.weare.wearecompany.ui.bottommenu.main.home.affiliation.AffilitionBottomViewHodel

class AffilitionDetailRecyclerViewAdapter(private val detaList: ArrayList<DivisionPage>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var item : DivisionPage
    //ClickListener
    interface OnItemClickListener {
        fun onClick(v: View, position: Int, homelistItem: DivisionPage)
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
                    .inflate(R.layout.item_affiliaction_detail_top, parent, false)
                return AffiliationDetailTopViewHodel(headerView)
            }

            else -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_affiliaction_detail, parent, false)
                return AffiliationDetailViewHodel(headerView)
            }
        }
    }

    // 보여줄 목록의 갯수
    override fun getItemCount(): Int {
        return detaList[0].qa.size + 1
    }

    // 뷰가 묶였을때 데이터를 뷰홀더에 넘겨준다.
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (position == 0) {
            item = detaList[0]
        } else {
            item = detaList[0]
        }


        when(holder) {
            is AffiliationDetailTopViewHodel -> {
                holder.image.setOnClickListener {
                    itemClickListener.onClick(it,position,item)
                }
                holder.apply {
                    bindWithView(item,View.OnClickListener {

                    })
                }
            }
            is AffiliationDetailViewHodel -> {

                holder.apply {
                    bindWithView(item,position-1)
                }
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        // 아이템의 처음과 마지막은 각각 헤더와 푸터를 의미함
        return position
    }
}