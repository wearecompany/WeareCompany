package com.weare.wearecompany.ui.bottommenu.main.alarm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.notification.data.alarm

class AlarmRecyclerViewAdapter(private val datalist: ArrayList<alarm>, private val deleteStatus: Boolean) :
    RecyclerView.Adapter<AlarmViewHodel>() {

    private var mSize = 0

    //ClickListener
    interface OnItemClickListener {
        fun onClick(v: View, position: Int, Item: alarm)
    }

    private lateinit var itemClickListener: OnItemClickListener

    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    //ClickListener
    interface OnDeleteClickListener {
        fun onClick(v: View, position: Int, Item: alarm)
    }

    private lateinit var DeleteClickListener: OnDeleteClickListener

    fun setItemClickListener(DeleteClickListener: OnDeleteClickListener) {
        this.DeleteClickListener = DeleteClickListener
    }

    fun setSize(postition: Int) {
        mSize = postition
    }

    fun removeItem(position: Int) {
        if (datalist.size != 0) {
            datalist.removeAt(position)
            setSize(datalist.size)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHodel {
       return AlarmViewHodel(
           LayoutInflater
               .from(parent.context)
               .inflate(R.layout.item_notification_individual, parent, false))
    }

    override fun onBindViewHolder(holder: AlarmViewHodel, position: Int) {
        val item = datalist[position]

        holder.itemView.setOnClickListener {
            holder.clickBackground()
            itemClickListener.onClick(it, position, item)
        }
        holder.deleteimage.setOnClickListener{
            DeleteClickListener.onClick(it,position,item)
        }
        holder.apply {
            holder.bindWithView(item,deleteStatus, View.OnClickListener {

            })
        }
    }

    override fun getItemCount(): Int {
        return datalist.size
    }
}