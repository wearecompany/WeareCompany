package com.weare.wearecompany.ui.bottommenu.mypage.notice

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.bottomnav.mypage.data.notice
import com.weare.wearecompany.utils.Constants

class NoticeViewHodel(v: View): RecyclerView.ViewHolder(v) {

    private val title = itemView.findViewById<TextView>(R.id.notice_title)
    private val time = itemView.findViewById<TextView>(R.id.notice_time)

    fun bindWithView(Item: notice, onClickListener: View.OnClickListener) {

        title.setText(Item.title)
        time.setText(Item.datetime)
        Log.d(Constants.TAG, "dateilItemViewHolder - bindWithView() called")

    }
}