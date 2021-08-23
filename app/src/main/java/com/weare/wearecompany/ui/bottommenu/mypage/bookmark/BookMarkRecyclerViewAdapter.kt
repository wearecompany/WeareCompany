package com.weare.wearecompany.ui.bottommenu.mypage.bookmark

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.bottomnav.mypage.data.allList

class BookMarkRecyclerViewAdapter(private val bookmarkList: ArrayList<allList>):RecyclerView.Adapter<BookMarkViewHodel>() {

    private var mSize = 0

    //ClickListener
    interface OnItemClickListener {
        fun onClick(v: View, position: Int, Item: allList)
    }

    private lateinit var itemClickListener : OnItemClickListener

    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookMarkViewHodel {
        return BookMarkViewHodel(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_bookmark_list, parent, false))
    }

    override fun onBindViewHolder(holder: BookMarkViewHodel, position: Int) {
        val item = bookmarkList[position]

        holder.itemView.setOnClickListener{
            itemClickListener.onClick(it, position, item)
        }

        holder.apply {
            BindWithView(item, position, View.OnClickListener {

            })
        }
    }

    override fun getItemCount(): Int {
        return bookmarkList.size
    }
}