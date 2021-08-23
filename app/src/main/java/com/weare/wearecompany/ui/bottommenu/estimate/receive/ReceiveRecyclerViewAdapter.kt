package com.weare.wearecompany.ui.bottommenu.estimate.receive

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.bottomnav.estimate.receive.ReceiveAllDate
import com.weare.wearecompany.data.bottomnav.estimate.receive.ReceiveResponse
import com.weare.wearecompany.ui.bottommenu.estimate.RequestHeaderViewHodel
import com.weare.wearecompany.ui.bottommenu.estimate.ReserveHeaderViewHodel
import com.weare.wearecompany.ui.bottommenu.estimate.receive.experthodel.ReceiveModelViewHodel
import com.weare.wearecompany.ui.bottommenu.estimate.receive.experthodel.ReceivePhotoViewHodel
import com.weare.wearecompany.ui.bottommenu.estimate.receive.experthodel.ReceiveStudioViewHodel
import com.weare.wearecompany.ui.bottommenu.estimate.receive.experthodel.ReceiveTripViewHodel
import com.weare.wearecompany.ui.bottommenu.estimate.send.SendShopViewHodel
import com.weare.wearecompany.ui.bottommenu.estimate.send.SendViewHodel

class ReceiveRecyclerViewAdapter(
    private val context: Context,
    private val reservecount:Int,
    private val rquestcount:Int,
    private val dataList: ArrayList<ReceiveAllDate>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // 헤더, 아이템, 푸터를 구분하기 위한 상수
    private val TYPE_RESERVE_HEADER = -1
    private val TYPE_REQUEST_HEADER = -2
    private val TYPE_STUDIO = 0
    private val TYPE_PHOTO = 1
    private val TYPE_MODEL = 2
    private val TYPE_TRIP = 3
    private val TYPE_SHOP = 4

    //ClickListener
    interface OnItemClickListener {
        fun onClick(v: View, position: Int, item: ReceiveAllDate)
    }

    private lateinit var itemClickListener: OnItemClickListener

    fun setItemClickListener(itemClickListener: ReceiveRecyclerViewAdapter.OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType) {
            TYPE_RESERVE_HEADER -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_send_header, parent, false)
                return ReserveHeaderViewHodel(headerView)
            }
            TYPE_STUDIO -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_individual_studio_send, parent, false)
                return ReceiveStudioViewHodel(headerView)
            }
            TYPE_PHOTO -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_individual_photo_send, parent, false)
                return ReceivePhotoViewHodel(headerView)
            }
            TYPE_MODEL -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_individual_model_send, parent, false)
                return ReceiveModelViewHodel(headerView)
            }
            TYPE_TRIP -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_individual_trip_send, parent, false)
                return ReceiveTripViewHodel(headerView)
            }
            TYPE_SHOP -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_individual_shop_send, parent, false)
                return ReceiveShopViewHodel(headerView)
            }
            TYPE_REQUEST_HEADER -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_send_many_header, parent, false)
                return RequestHeaderViewHodel(headerView)
            }
            else -> {
                val itemView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_many_request_send, parent, false)
                return ReceiveViewHodel(itemView)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is ReserveHeaderViewHodel) {
            holder.apply {
                bindWithView(reservecount)
            }
        } else if(holder is ReceiveStudioViewHodel){
            val item = dataList[position]
            holder.itemView.setOnClickListener {
                if (item != null) {
                    itemClickListener.onClick(it, position, item)
                }
            }
            holder.apply {
                if (item != null) {
                    bindWithView(item,context, View.OnClickListener {
                    })
                }
            }
        } else if(holder is ReceivePhotoViewHodel){
            val item = dataList[position]
            holder.itemView.setOnClickListener {
                if (item != null) {
                    itemClickListener.onClick(it, position, item)
                }
            }
            holder.apply {
                if (item != null) {
                    bindWithView(item,context, View.OnClickListener {
                    })
                }
            }
        } else if(holder is ReceiveModelViewHodel) {
            val item = dataList[position]
            holder.itemView.setOnClickListener {
                if (item != null) {
                    itemClickListener.onClick(it, position, item)
                }
            }
            holder.apply {
                if (item != null) {
                    bindWithView(item,context, View.OnClickListener {
                    })
                }
            }
        }else if(holder is ReceiveTripViewHodel) {
            val item = dataList[position]
            holder.itemView.setOnClickListener {
                if (item != null) {
                    itemClickListener.onClick(it, position, item)
                }
            }
            holder.apply {
                if (item != null) {
                    bindWithView(item,context, View.OnClickListener {
                    })
                }
            }
        }else if (holder is ReceiveShopViewHodel) {
            val item = dataList[position]
            holder.itemView.setOnClickListener {
                if (item != null) {
                    itemClickListener.onClick(it, position, item)
                }
            }
            holder.apply {
                if (item != null) {
                    bindWithView(item, View.OnClickListener {
                    })
                }
            }
        } else if (holder is RequestHeaderViewHodel) {
            holder.apply {
                bindWithView(rquestcount)
            }
        } else if (holder is ReceiveViewHodel) {
            val item = dataList[position]
            holder.itemView.setOnClickListener {
                if (item != null) {
                    itemClickListener.onClick(it, position, item)
                }
            }
            holder.apply {
                if (item != null) {
                    bindWithView(item, context, View.OnClickListener {
                    })
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getItemViewType(position: Int): Int {
        // 아이템의 처음과 마지막은 각각 헤더와 푸터를 의미함
        return dataList[position].type
    }
}