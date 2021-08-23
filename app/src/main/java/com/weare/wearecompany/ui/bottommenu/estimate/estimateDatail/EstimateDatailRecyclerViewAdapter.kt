package com.weare.wearecompany.ui.bottommenu.estimate.estimateDatail

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import kotlinx.android.synthetic.main.item_estimatephoto.view.*

class EstimateDatailRecyclerViewAdapter(private val context:Context, private val dataList: ArrayList<Uri>): RecyclerView.Adapter<EstimateDatailViewHodel>() {

    var mSize = 0

    //ClickListener
    interface OnItemClickListener {
        fun onClick(v: View, position: Int, Item: Uri)
    }

    private lateinit var itemClickListener : OnItemClickListener

    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    fun getSize(): Int {
        return mSize
    }
    fun addItem(dataUri:Uri) {
        if (dataList.size < 6) {
            dataList.add(dataUri)
            setSize(dataList.size)
            //갱신처리 반드시 해야함
            notifyDataSetChanged()
        }
    }

    fun setSize(postition: Int) {
        mSize = postition
    }

    fun removeItem(position: Int) {
        if (dataList.size != 0) {
            dataList.removeAt(position)
            setSize(dataList.size)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstimateDatailViewHodel {
        return EstimateDatailViewHodel(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_estimatephoto, parent, false)
        )
    }

    override fun onBindViewHolder(holder: EstimateDatailViewHodel, position: Int) {
        val item = dataList[position]

        /*holder.itemView.setOnClickListener {
            setPosition(position)
        }*/
        holder.itemView.photo_clean.setOnClickListener {
            itemClickListener.onClick(it, position, item)
        }
        holder.apply {
            bindWithView(item, position, context, View.OnClickListener {
            })
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}