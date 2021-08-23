package com.weare.wearecompany.ui.bottommenu.mypage.information

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.bottomnav.mypage.data.manual

class InformationRecyclerAdapter(private val data: ArrayList<manual>) : RecyclerView.Adapter<InformationViewHodel>() {

    //ClickListener
    interface OnItemClickListener {
        fun onClick(v: View, position: Int, Item: manual)
    }

    private lateinit var itemClickListener : OnItemClickListener

    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InformationViewHodel {
        return InformationViewHodel(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_information, parent, false))
    }

    override fun onBindViewHolder(holder: InformationViewHodel, position: Int) {
        val item = data[position]

        holder.itemView.setOnClickListener{
            itemClickListener.onClick(it, position, item)
        }
        holder.apply {
            bindWithView(item, View.OnClickListener {

            })
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

}