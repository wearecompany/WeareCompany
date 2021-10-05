package com.weare.wearecompany.ui.bottommenu.mypage.request

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R

class SendOnClickImageRecyclerViewAdapter(private val detaList:ArrayList<String>): RecyclerView.Adapter<OnClickImageHodel>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnClickImageHodel {
        return OnClickImageHodel(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_request_list_onclick_image, parent, false)
        )
    }

    override fun onBindViewHolder(holder: OnClickImageHodel, position: Int) {
        var item = detaList[position]
        holder.apply {
            bindWithView(item)
        }
    }

    override fun getItemCount(): Int {
        return detaList.size
    }
}