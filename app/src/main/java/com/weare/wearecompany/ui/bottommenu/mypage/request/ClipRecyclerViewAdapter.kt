package com.weare.wearecompany.ui.bottommenu.mypage.request

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.main.Request.deta.requestClip
import com.weare.wearecompany.ui.bottommenu.mypage.request.clip.DayHodel
import com.weare.wearecompany.ui.bottommenu.mypage.request.clip.PeopleHodel
import com.weare.wearecompany.ui.bottommenu.mypage.request.clip.RoomHodel
import com.weare.wearecompany.ui.bottommenu.mypage.request.clip.TimeHodel

class ClipRecyclerViewAdapter(private val list: ArrayList<requestClip>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            0 -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_request_list_room_clip, parent, false)
                return RoomHodel(headerView)
            }
            1 -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_request_list_people_clip, parent, false)
                return PeopleHodel(headerView)
            }
            2 -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_request_list_time_clip, parent, false)
                return TimeHodel(headerView)
            }
            else -> {
                val headerView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_request_list_day_clip, parent, false)
                return DayHodel(headerView)
            }

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var item = list[position]
        when (holder) {
            is RoomHodel -> {
                holder.apply {
                    bindWithView(item)
                }
            }
            is PeopleHodel -> {
                holder.apply {
                    bindWithView(item)
                }
            }
            is TimeHodel -> {
                holder.apply {
                    bindWithView(item)
                }
            }
            is DayHodel -> {
                holder.apply {
                    bindWithView(item)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        // 아이템의 처음과 마지막은 각각 헤더와 푸터를 의미함
        return list[position].type
    }
}