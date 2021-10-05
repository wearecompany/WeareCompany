package com.weare.wearecompany.ui.bottommenu.main.home.affiliation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.division.data.division
import com.weare.wearecompany.data.main.affiliationTop
import com.weare.wearecompany.data.main.data.studio
import com.weare.wearecompany.ui.bottommenu.main.home.HomeListCaViewHodel
import com.weare.wearecompany.ui.listcontainer.studio.StudioRecyclerViewAdapter

class AffiliationBottomRecyclerViewAdapter(private val data: ArrayList<division>) : RecyclerView.Adapter<AffilitionBottomViewHodel>() {

    interface OnItemClickListener {
        fun onClick(v: View, position: Int, hotpickItem: division)
    }

    private lateinit var itemClickListener : AffiliationBottomRecyclerViewAdapter.OnItemClickListener

    fun setItemClickListener(itemClickListener: AffiliationBottomRecyclerViewAdapter.OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AffilitionBottomViewHodel {
        return AffilitionBottomViewHodel(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_affilition_bottom, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AffilitionBottomViewHodel, position: Int) {
        val item = data[position]

        holder.aff_layout.setOnClickListener {
            itemClickListener.onClick(it, position, item)
        }

        holder.apply {
            bindWithView(item,View.OnClickListener{

            })
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}