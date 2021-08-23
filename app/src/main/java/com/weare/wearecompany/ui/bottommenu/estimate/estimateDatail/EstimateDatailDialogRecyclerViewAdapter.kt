package com.weare.wearecompany.ui.bottommenu.estimate.estimateDatail

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import kotlinx.android.synthetic.main.item_estimatephoto.view.*

class EstimateDatailDialogRecyclerViewAdapter(private val dataList: ArrayList<Uri>): RecyclerView.Adapter<EstimateDatailDialogViewHodel>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EstimateDatailDialogViewHodel {
        return EstimateDatailDialogViewHodel(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_estimatephoto, parent, false)
        )
    }

    override fun onBindViewHolder(holder: EstimateDatailDialogViewHodel, position: Int) {
        val item = dataList[position]

        holder.apply {
            bindWithView(item)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}