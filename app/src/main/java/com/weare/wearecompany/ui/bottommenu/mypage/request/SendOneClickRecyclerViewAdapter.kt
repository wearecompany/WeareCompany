package com.weare.wearecompany.ui.bottommenu.mypage.request

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.main.Request.deta.sendone
import com.weare.wearecompany.data.main.Request.deta.sendoneclick
import com.weare.wearecompany.ui.bottommenu.mypage.request.send.SendOneClickHodel

class SendOneClickRecyclerViewAdapter(private val detaList:ArrayList<sendoneclick>, private val context: Context, private val type:Int): RecyclerView.Adapter<SendOneClickHodel>() {

    //ClickListener
    interface OnItemClickListener {
        fun onClick(v: View, position: Int, item: sendoneclick)
    }

    private lateinit var itemClickListener: OnItemClickListener


    fun setItemClickListener(itemClickListener: SendOneClickRecyclerViewAdapter.OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    //ClickListener
    interface OnPurchaseClickListener {
        fun onClick(v: View, position: Int, item: sendoneclick)
    }

    private lateinit var purchaseClickListener: OnPurchaseClickListener


    fun setPurchaseClickListener(purchaseClickListener: SendOneClickRecyclerViewAdapter.OnPurchaseClickListener) {
        this.purchaseClickListener = purchaseClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SendOneClickHodel {
        return SendOneClickHodel(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_oneclick_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SendOneClickHodel, position: Int) {
        var item = detaList[position]
        holder.purchase.setOnClickListener {
            purchaseClickListener.onClick(it, position, item)
        }
        holder.itemView.setOnClickListener {
            if (item != null) {
                itemClickListener.onClick(it, position, item)
            }
        }
        holder.apply {
            bindWithView(item,type,position,context,View.OnClickListener{

            })
        }
    }

    override fun getItemCount(): Int {
        return detaList.size
    }
}