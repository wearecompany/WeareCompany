package com.weare.wearecompany.ui.bottommenu.estimate.progress

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.bottomnav.estimate.progress.ProgressAllDate
import com.weare.wearecompany.ui.bottommenu.estimate.progress.experthodel.refund.*
import com.weare.wearecompany.ui.bottommenu.estimate.progress.experthodel.review.*

class ProgressReviewRecyclerViewAdapter (
    private val context: Context, private val reservecount: Int,
    private val rquestcount: Int, private val dataList: ArrayList<ProgressAllDate>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

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

    fun setItemClickListener(itemClickListener: ProgressReviewRecyclerViewAdapter.OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    //ClickListener
    interface OnreviewClickListener {
        fun onClick(v: View, position: Int, item: ProgressAllDate)
    }

    private lateinit var reviewClickListener: OnreviewClickListener

    fun setItemClickListener(reviewClickListener: ProgressReviewRecyclerViewAdapter.OnreviewClickListener) {
        this.reviewClickListener = reviewClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_RESERVE_HEADER -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_progress_header, parent, false)
                return reviewHeaderFalseHodel(headerView)
            }
            TYPE_STUDIO -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_individual_studio_send, parent, false)
                return reviewStudioHodel(headerView)
            }
            TYPE_PHOTO -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_individual_photo_send, parent, false)
                return reviewPhotoHodel(headerView)
            }
            TYPE_MODEL -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_individual_model_send, parent, false)
                return reviewModelHodel(headerView)
            }
            TYPE_TRIP -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_individual_trip_send, parent, false)
                return reviewTripHodel(headerView)
            }
            TYPE_SHOP -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_individual_shop_send, parent, false)
                return reviewShopHodel(headerView)
            }
            TYPE_REQUEST_HEADER -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_progress_header, parent, false)
                return reviewHeaderTrueHodel(headerView)
            }
            else -> {
                val itemView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_many_request_send, parent, false)
                return reviewRequestHodel(itemView)
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is reviewHeaderFalseHodel) {
            holder.apply {
                bindWithView(reservecount)
            }
        } else if (holder is reviewStudioHodel) {
            val item = dataList[position]
            holder.itemView.setOnClickListener {
                if (item != null) {
                    holder.viewStatus()
                    itemClickListener.onClick(it, position, item)
                }
            }
            holder.category.setOnClickListener {
                reviewClickListener.onClick(it, position, item)
            }
            holder.apply {
                if (item != null) {
                    bindWithView(item, context, View.OnClickListener {
                    })
                }
            }
        } else if (holder is reviewPhotoHodel) {
            val item = dataList[position]
            holder.itemView.setOnClickListener {
                if (item != null) {
                    holder.viewStatus()
                    itemClickListener.onClick(it, position, item)
                }
            }
            holder.category.setOnClickListener {
                reviewClickListener.onClick(it, position, item)
            }
            holder.apply {
                if (item != null) {
                    bindWithView(item, context, View.OnClickListener {
                    })
                }
            }
        } else if (holder is reviewModelHodel) {
            val item = dataList[position]
            holder.itemView.setOnClickListener {
                if (item != null) {
                    holder.viewStatus()
                    itemClickListener.onClick(it, position, item)
                }
            }
            holder.category.setOnClickListener {
                reviewClickListener.onClick(it, position, item)
            }
            holder.apply {
                if (item != null) {
                    bindWithView(item, context, View.OnClickListener {
                    })
                }
            }
        } else if (holder is reviewTripHodel) {
            val item = dataList[position]
            holder.itemView.setOnClickListener {
                if (item != null) {
                    holder.viewStatus()
                    itemClickListener.onClick(it, position, item)
                }
            }
            holder.category.setOnClickListener {
                reviewClickListener.onClick(it, position, item)
            }
            holder.apply {
                if (item != null) {
                    bindWithView(item, context, View.OnClickListener {
                    })
                }
            }
        } else if (holder is reviewShopHodel) {
            val item = dataList[position]
            holder.itemView.setOnClickListener {
                if (item != null) {
                    holder.viewStatus()
                    itemClickListener.onClick(it, position, item)
                }
            }
            holder.category.setOnClickListener {
                reviewClickListener.onClick(it, position, item)
            }
            holder.apply {
                if (item != null) {
                    bindWithView(item, View.OnClickListener {
                    })
                }
            }
        } else if (holder is reviewHeaderTrueHodel) {
            holder.apply {
                bindWithView(rquestcount)
            }
        } else if (holder is reviewRequestHodel) {
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