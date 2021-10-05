package com.weare.wearecompany.ui.bottommenu.mypage.request

import android.content.ClipData
import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.bottomnav.estimate.send.SendAllDate
import com.weare.wearecompany.data.main.Request.deta.sendone
import com.weare.wearecompany.ui.bottommenu.estimate.send.SendRecyclerViewAdapter
import com.weare.wearecompany.ui.bottommenu.mypage.request.send.SendModelHodel
import com.weare.wearecompany.ui.bottommenu.mypage.request.send.SendPhotoHodel
import com.weare.wearecompany.ui.bottommenu.mypage.request.send.SendStudioHodel
import com.weare.wearecompany.ui.bottommenu.mypage.request.send.SendTripHodel


class SendOneRecyclerViewAdapter(private val detalist: ArrayList<sendone>,private val context:Context, private val type:Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //ClickListener
    interface OnItemClickListener {
        fun onClick(v: View, position: Int, item: sendone)
    }

    private lateinit var itemClickListener: OnItemClickListener


    fun setItemClickListener(itemClickListener: SendOneRecyclerViewAdapter.OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    //ClickListener
    interface OnPurchaseClickListener {
        fun onClick(v: View, position: Int, item: sendone)
    }

    private lateinit var purchaseClickListener: OnPurchaseClickListener


    fun setpurchaseClickListener(purchaseClickListener: SendOneRecyclerViewAdapter.OnPurchaseClickListener) {
        this.purchaseClickListener = purchaseClickListener
    }

    //ClickListener
    interface OnReviewClickListener {
        fun onClick(v: View, position: Int, item: sendone)
    }

    private lateinit var reviewClickListener: OnReviewClickListener


    fun setReviewClickListener(reviewClickListener: SendOneRecyclerViewAdapter.OnReviewClickListener) {
        this.reviewClickListener = reviewClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            0 -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_change_studio_list, parent, false)
                return SendStudioHodel(headerView)
            }
            1 -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_change_photo_list, parent, false)
                return SendPhotoHodel(headerView)
            }
            2 -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_change_model_list, parent, false)
                return SendModelHodel(headerView)
            }
            else -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_change_trip_list, parent, false)
                return SendTripHodel(headerView)
            }

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var item = detalist[position]
        when (holder) {
            is SendModelHodel -> {
                holder.purchase.setOnClickListener {
                    purchaseClickListener.onClick(it, position, item)
                }
                holder.review.setOnClickListener {
                    reviewClickListener.onClick(it, position, item)
                }
                holder.itemView.setOnClickListener {
                    if (item != null) {
                        itemClickListener.onClick(it, position, item)
                    }
                }
                holder.apply {
                    bindWithView(item,context,type,View.OnClickListener{

                    } )
                }
            }
            is SendPhotoHodel -> {
                holder.purchase.setOnClickListener {
                    purchaseClickListener.onClick(it, position, item)
                }
                holder.review.setOnClickListener {
                    reviewClickListener.onClick(it, position, item)
                }
                holder.itemView.setOnClickListener {
                    if (item != null) {
                        itemClickListener.onClick(it, position, item)
                    }
                }
                holder.apply {
                    bindWithView(item,context,type,View.OnClickListener{

                    } )
                }
            }
            is SendTripHodel -> {
                holder.purchase.setOnClickListener {
                    purchaseClickListener.onClick(it, position, item)
                }
                holder.review.setOnClickListener {
                    reviewClickListener.onClick(it, position, item)
                }
                holder.itemView.setOnClickListener {
                    if (item != null) {
                        itemClickListener.onClick(it, position, item)
                    }
                }
                holder.apply {
                    bindWithView(item,context,type,View.OnClickListener{

                    } )
                }
            }
            is SendStudioHodel -> {
                holder.purchase.setOnClickListener {
                    purchaseClickListener.onClick(it, position, item)
                }
                holder.review.setOnClickListener {
                    reviewClickListener.onClick(it, position, item)
                }
                holder.itemView.setOnClickListener {
                    if (item != null) {
                        itemClickListener.onClick(it, position, item)
                    }
                }
                holder.apply {
                    bindWithView(item,context,type,View.OnClickListener{

                    } )
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return detalist.size
    }

    override fun getItemViewType(position: Int): Int {
        // 아이템의 처음과 마지막은 각각 헤더와 푸터를 의미함
        return detalist[position].expert_type
    }
}