package com.weare.wearecompany.ui.bottommenu.mypage.request.receive

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.main.Request.deta.expertarray
import com.weare.wearecompany.data.main.Request.deta.receiveoneclick
import com.weare.wearecompany.ui.bottommenu.mypage.request.ReceiveOneClickRecyclerViewAdapter

class ReceiveOneClickCaRecyclerViewAdapter(private val dataList:ArrayList<expertarray>): RecyclerView.Adapter<ReceiveOneClickCaHodel>() {

    //ClickListener
    interface OnItemClickListener {
        fun onClick(v: View, position: Int, item: expertarray)
    }

    private lateinit var itemClickListener: OnItemClickListener


    fun setItemClickListener(itemClickListener: ReceiveOneClickCaRecyclerViewAdapter.OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceiveOneClickCaHodel {
        return ReceiveOneClickCaHodel(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_receive_onclick_tag, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ReceiveOneClickCaHodel, position: Int) {
        val item = dataList[position]
        holder.itemView.setOnClickListener {
            if (item != null) {
                itemClickListener.onClick(it, position, item)
            }
        }
        holder.apply {
            bindWithView(item,View.OnClickListener{

            })
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}