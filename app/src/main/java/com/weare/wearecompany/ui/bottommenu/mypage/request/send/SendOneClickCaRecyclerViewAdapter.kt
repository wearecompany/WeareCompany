package com.weare.wearecompany.ui.bottommenu.mypage.request.send

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.ui.bottommenu.estimate.send.SendRequestViewHodel

class SendOneClickCaRecyclerViewAdapter(private val dataList:ArrayList<Int>): RecyclerView.Adapter<SemdOneClickCaHodel>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SemdOneClickCaHodel {
        return SemdOneClickCaHodel(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_send_onclick_tag, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SemdOneClickCaHodel, position: Int) {
        val item = dataList[position]
        holder.apply {
            bindWithView(item)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}