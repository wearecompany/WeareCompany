package com.weare.wearecompany.ui.bottommenu.mypage.request.clip

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.main.Request.deta.requestClip

class PeopleHodel(v: View) : RecyclerView.ViewHolder(v) {

    private val people = itemView.findViewById<TextView>(R.id.people_clip)

    fun bindWithView(item: requestClip) {
        people.text = item.content
    }
}