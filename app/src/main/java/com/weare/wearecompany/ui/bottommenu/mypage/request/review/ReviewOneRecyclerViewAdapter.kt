package com.weare.wearecompany.ui.bottommenu.mypage.request.review

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.main.Request.deta.reviewone
import com.weare.wearecompany.ui.bottommenu.mypage.request.send.SendModelHodel
import com.weare.wearecompany.ui.bottommenu.mypage.request.send.SendPhotoHodel
import com.weare.wearecompany.ui.bottommenu.mypage.request.send.SendStudioHodel
import com.weare.wearecompany.ui.bottommenu.mypage.request.send.SendTripHodel

class ReviewOneRecyclerViewAdapter(private val detalist: ArrayList<reviewone>, private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //ClickListener
    interface OnItemClickListener {
        fun onClick(v: View, position: Int, item: reviewone)
    }

    private lateinit var itemClickListener: OnItemClickListener


    fun setItemClickListener(itemClickListener: ReviewOneRecyclerViewAdapter.OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    //ClickListener
    interface OnReviewClickListener {
        fun onClick(v: View, position: Int, item: reviewone)
    }

    private lateinit var reviewClickListener: OnReviewClickListener


    fun setReviewClickListener(reviewClickListener: ReviewOneRecyclerViewAdapter.OnReviewClickListener) {
        this.reviewClickListener = reviewClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            0 -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_change_studio_list, parent, false)
                return ReviewStudioHodel(headerView)
            }
            1 -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_change_photo_list, parent, false)
                return ReviewPhotoHodel(headerView)
            }
            2 -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_change_model_list, parent, false)
                return ReviewModelHodel(headerView)
            }
            else -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_change_trip_list, parent, false)
                return ReviewTripHodel(headerView)
            }

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var item = detalist[position]
        when (holder) {
            is ReviewModelHodel -> {
                holder.review.setOnClickListener {
                    reviewClickListener.onClick(it, position, item)
                }
                holder.itemView.setOnClickListener {
                    if (item != null) {
                        itemClickListener.onClick(it, position, item)
                    }
                }
                holder.apply {
                    bindWithView(item,context, View.OnClickListener{

                    } )
                }
            }
            is ReviewPhotoHodel -> {
                holder.review.setOnClickListener {
                    reviewClickListener.onClick(it, position, item)
                }
                holder.itemView.setOnClickListener {
                    if (item != null) {
                        itemClickListener.onClick(it, position, item)
                    }
                }
                holder.apply {
                    bindWithView(item,context, View.OnClickListener{

                    } )
                }
            }
            is ReviewTripHodel -> {
                holder.review.setOnClickListener {
                    reviewClickListener.onClick(it, position, item)
                }
                holder.itemView.setOnClickListener {
                    if (item != null) {
                        itemClickListener.onClick(it, position, item)
                    }
                }
                holder.apply {
                    bindWithView(item,context, View.OnClickListener{

                    } )
                }
            }
            is ReviewStudioHodel -> {

                holder.review.setOnClickListener {
                    reviewClickListener.onClick(it, position, item)
                }
                holder.itemView.setOnClickListener {
                    if (item != null) {
                        itemClickListener.onClick(it, position, item)
                    }
                }
                holder.apply {
                    bindWithView(item,context, View.OnClickListener{

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