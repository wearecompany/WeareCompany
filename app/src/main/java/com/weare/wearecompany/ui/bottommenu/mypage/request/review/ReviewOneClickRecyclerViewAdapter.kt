package com.weare.wearecompany.ui.bottommenu.mypage.request.review

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.main.Request.deta.receiveoneclick
import com.weare.wearecompany.data.main.Request.deta.reviewoneclick
import com.weare.wearecompany.ui.bottommenu.mypage.request.ReceiveOneClickRecyclerViewAdapter
import com.weare.wearecompany.ui.bottommenu.mypage.request.receive.ReceiveOneClickHodel

class ReviewOneClickRecyclerViewAdapter(private val detaList:ArrayList<reviewoneclick>, private val context: Context, private val type:Int): RecyclerView.Adapter<ReviewOneClickHodel>() {

    //ClickListener
    interface OnItemClickListener {
        fun onClick(v: View, position: Int, item: reviewoneclick)
    }

    private lateinit var itemClickListener: OnItemClickListener


    fun setItemClickListener(itemClickListener: ReviewOneClickRecyclerViewAdapter.OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewOneClickHodel {
        return ReviewOneClickHodel(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_onclick_receive_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ReviewOneClickHodel, position: Int) {
        var item = detaList[position]
        holder.itemView.setOnClickListener {
            if (item != null) {
                itemClickListener.onClick(it, position, item)
            }
        }
        holder.apply {
            bindWithView(item,position,context,type,View.OnClickListener{

            })
        }
    }

    override fun getItemCount(): Int {
        return detaList.size
    }
}