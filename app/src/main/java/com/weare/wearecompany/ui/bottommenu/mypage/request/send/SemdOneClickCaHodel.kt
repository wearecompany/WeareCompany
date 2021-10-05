package com.weare.wearecompany.ui.bottommenu.mypage.request.send

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R

class SemdOneClickCaHodel(v: View): RecyclerView.ViewHolder(v) {

    private val tag = itemView.findViewById<TextView>(R.id.onclick_tag_text)

    fun bindWithView(Item: Int) {

        when(Item) {
            0 -> {
                tag.text = "스튜디오"
            }
            1 -> {
                tag.text = "포토그래퍼"
            }
            2 -> {
                tag.text = "모델"
            }
            3 -> {
                tag.text = "뷰티전문가"
            }
        }

    }
}