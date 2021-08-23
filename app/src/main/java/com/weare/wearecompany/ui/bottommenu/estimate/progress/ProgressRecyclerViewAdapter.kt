package com.weare.wearecompany.ui.bottommenu.estimate.progress

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.bottomnav.estimate.progress.ProgressAllDate
import com.weare.wearecompany.ui.bottommenu.estimate.progress.experthodel.progress.ProgressHeaderFalseViewHodel
import com.weare.wearecompany.ui.bottommenu.estimate.progress.experthodel.progress.ProgressHeaderTrueViewHodel
import com.weare.wearecompany.ui.bottommenu.estimate.progress.experthodel.progress.progressModelHodel
import com.weare.wearecompany.ui.bottommenu.estimate.progress.experthodel.progress.progressPhotoHodel
import com.weare.wearecompany.ui.bottommenu.estimate.progress.experthodel.progress.progressStudioHodel
import com.weare.wearecompany.ui.bottommenu.estimate.progress.experthodel.progress.progressTripHodel


class ProgressRecyclerViewAdapter(
    private val context: Context, private val reservecount: Int,
    private val rquestcount: Int, private val dataList: ArrayList<ProgressAllDate>
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
        fun onClick(v: View, position: Int, item: ProgressAllDate)
    }

    private lateinit var itemClickListener: OnItemClickListener

    fun setItemClickListener(itemClickListener: ProgressRecyclerViewAdapter.OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    //ClickListener
    interface OncompleteClickListener {
        fun onClick(v: View, position: Int, item: ProgressAllDate)
    }

    private lateinit var completeClickListener: OncompleteClickListener

    fun setItemClickListener(completeClickListener: ProgressRecyclerViewAdapter.OncompleteClickListener) {
        this.completeClickListener = completeClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_RESERVE_HEADER -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_progress_header, parent, false)
                return ProgressHeaderTrueViewHodel(headerView)
            }
            TYPE_STUDIO -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_individual_studio_send, parent, false)
                return progressStudioHodel(headerView)
            }
            TYPE_PHOTO -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_individual_photo_send, parent, false)
                return progressPhotoHodel(headerView)
            }
            TYPE_MODEL -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_individual_model_send, parent, false)
                return progressModelHodel(headerView)
            }
            TYPE_TRIP -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_individual_trip_send, parent, false)
                return progressTripHodel(headerView)
            }
            TYPE_SHOP -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_individual_shop_send, parent, false)
                return ProgressShopViewHodel(headerView)
            }
            TYPE_REQUEST_HEADER -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_progress_header, parent, false)
                return ProgressHeaderFalseViewHodel(headerView)
            }
            else -> {
                val itemView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_progress_many, parent, false)
                return ProgressViewHodel(itemView)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is ProgressHeaderTrueViewHodel) {
            holder.apply {
                bindWithView(reservecount)
            }
        } else if (holder is progressStudioHodel) {
            val item = dataList[position]
            holder.itemView.setOnClickListener {
                if (item != null) {
                    holder.viewStatus()
                    itemClickListener.onClick(it, position, item)
                }
            }
            holder.category.setOnClickListener {
                completeClickListener.onClick(it, position, item)
            }
            holder.apply {
                if (item != null) {
                    bindWithView(item, context,View.OnClickListener {
                    })
                }
            }
        } else if (holder is progressPhotoHodel) {
            val item = dataList[position]
            holder.itemView.setOnClickListener {
                if (item != null) {
                    holder.viewStatus()
                    itemClickListener.onClick(it, position, item)
                }
            }
            holder.category.setOnClickListener {
                completeClickListener.onClick(it, position, item)
            }
            holder.apply {
                if (item != null) {
                    bindWithView(item, context,View.OnClickListener {
                    })
                }
            }
        } else if (holder is progressModelHodel) {
            val item = dataList[position]
            holder.itemView.setOnClickListener {
                if (item != null) {
                    holder.viewStatus()
                    itemClickListener.onClick(it, position, item)
                }
            }
            holder.category.setOnClickListener {
                completeClickListener.onClick(it, position, item)
            }
            holder.apply {
                if (item != null) {
                    bindWithView(item, context,View.OnClickListener {
                    })
                }
            }
        } else if (holder is progressTripHodel) {
            val item = dataList[position]
            holder.itemView.setOnClickListener {
                if (item != null) {
                    holder.viewStatus()
                    itemClickListener.onClick(it, position, item)
                }
            }
            holder.category.setOnClickListener {
                completeClickListener.onClick(it, position, item)
            }
            holder.apply {
                if (item != null) {
                    bindWithView(item, context,View.OnClickListener {
                    })
                }
            }
        }else if (holder is ProgressShopViewHodel) {
            val item = dataList[position]
            holder.itemView.setOnClickListener {
                if (item != null) {
                    holder.viewStatus()
                    itemClickListener.onClick(it, position, item)
                }
            }
            holder.category.setOnClickListener {
                completeClickListener.onClick(it, position, item)
            }
            holder.apply {
                if (item != null) {
                    bindWithView(item, View.OnClickListener {
                    })
                }
            }
        } else if (holder is ProgressHeaderFalseViewHodel) {
            holder.apply {
                bindWithView(rquestcount)
            }
        } else if (holder is ProgressViewHodel) {
            val item = dataList[position]
            holder.itemView.setOnClickListener {
                if (item != null) {
                    itemClickListener.onClick(it, position, item)
                    holder.viewStatus()
                }
            }
            holder.category.setOnClickListener {
                completeClickListener.onClick(it, position, item)
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