package com.weare.wearecompany.ui.bottommenu.mypage.review

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.bottomnav.mypage.data.myreview

class MyReviewRecyclerViewAdapter(private val dataList: ArrayList<myreview>) : RecyclerView.Adapter<MyReviewViewHodel>() {

    //ClickListener
    interface OnItemClickListener {
        fun onClick(v: View, position: Int, reviewItem: myreview)
    }

    private lateinit var itemClickListener : OnItemClickListener

    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyReviewViewHodel {
        return MyReviewViewHodel(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_my_review_list, parent, false))
    }

    override fun onBindViewHolder(holder: MyReviewViewHodel, position: Int) {
        val item = dataList[position]

        holder.review_menu.setOnClickListener {
            itemClickListener.onClick(it, position, item)
        }

        holder.apply {
            bindWithView(item, View.OnClickListener {

            })
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}