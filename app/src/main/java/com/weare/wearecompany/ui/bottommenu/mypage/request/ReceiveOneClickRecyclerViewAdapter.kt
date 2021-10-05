package com.weare.wearecompany.ui.bottommenu.mypage.request

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.main.Request.deta.receiveoneclick
import com.weare.wearecompany.data.main.Request.deta.sendoneclick
import com.weare.wearecompany.ui.bottommenu.mypage.request.receive.ReceiveOneClickHodel

class ReceiveOneClickRecyclerViewAdapter(private val detaList:ArrayList<receiveoneclick>, private val context: Context, private val type:Int): RecyclerView.Adapter<ReceiveOneClickHodel>() {

    //ClickListener
    interface OnItemClickListener {
        fun onClick(v: View, position: Int, item: receiveoneclick)
    }

    private lateinit var itemClickListener: OnItemClickListener


    fun setItemClickListener(itemClickListener: ReceiveOneClickRecyclerViewAdapter.OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    //ClickListener
    interface OnPurchaseClickListener {
        fun onClick(v: View, position: Int, item: receiveoneclick)
    }

    private lateinit var purchaseClickListener: OnPurchaseClickListener


    fun setPurchaseClickListener(purchaseClickListener: ReceiveOneClickRecyclerViewAdapter.OnPurchaseClickListener) {
        this.purchaseClickListener = purchaseClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceiveOneClickHodel {
        return ReceiveOneClickHodel(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_onclick_receive_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ReceiveOneClickHodel, position: Int) {
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
            bindWithView(item,position,context,type,View.OnClickListener{

            })
        }
    }

    override fun getItemCount(): Int {
        return detaList.size
    }
}