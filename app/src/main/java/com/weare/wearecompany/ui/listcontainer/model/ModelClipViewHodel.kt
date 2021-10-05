package com.weare.wearecompany.ui.listcontainer.model

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.List.clip
import com.weare.wearecompany.data.chatting.data.send

class ModelClipViewHodel(v: View) : RecyclerView.ViewHolder(v) {

    val clip = itemView.findViewById<TextView>(R.id.clip_text)
    val cliplayout = itemView.findViewById<LinearLayout>(R.id.tag_layout)
    val clipimge = itemView.findViewById<ImageView>(R.id.clip_image)

    fun bindWithView(Item: clip, onClickListener: View.OnClickListener) {

        when (Item.main_type) {
            0 -> {
                when (Item.sub_type) {
                    0 -> clip.text = "뷰티"
                    1 -> clip.text = "쇼핑몰"
                    2 -> clip.text = "패션"
                    3 -> clip.text = "부분"
                    4 -> clip.text = "영상"
                    5 -> clip.text = "주부"
                    6 -> clip.text = "일반인"
                    7 -> clip.text = "남자"
                    8 -> clip.text = "성형"
                    9 -> clip.text = "빅사이즈"
                    10 -> clip.text = "시니어"
                    11 -> clip.text = "주니어"
                    12 -> clip.text = "수영복"
                    13 -> clip.text = "인스터인기"
                    14 -> clip.text = "유튜버"
                    15 -> clip.text = "아나운서"
                    16 -> clip.text = "레이싱"
                    17 -> clip.text = "외국인"
                    18 -> clip.text = "연기"
                    19 -> clip.text = "mc/행사"
                }
            }
            1 -> {
                when (Item.sub_type) {
                    0 -> clip.text = "서울"
                    1 -> clip.text = "경기"
                    2 -> clip.text = "인천"
                    3 -> clip.text = "강원"
                    4 -> clip.text = "제주"
                    5 -> clip.text = "대전"
                    6 -> clip.text = "충북"
                    7 -> clip.text = "충남/세종"
                    8 -> clip.text = "부산"
                    9 -> clip.text = "울산"
                    10 -> clip.text = "경남"
                    11 -> clip.text = "대구"
                    12 -> clip.text = "경북"
                    13 -> clip.text = "광주"
                    14 -> clip.text = "전남"
                    15 -> clip.text = "전주/전북"
                }
            }
            2 -> {
                clip.text = Item.name
            }
            3 -> {
                when (Item.sub_type) {
                    1 -> clip.text = "인기순"
                    2 -> clip.text = "평점순"
                    3 -> clip.text = "등록순"
                    4 -> clip.text = "응답순"
                }
            }
        }
    }
}