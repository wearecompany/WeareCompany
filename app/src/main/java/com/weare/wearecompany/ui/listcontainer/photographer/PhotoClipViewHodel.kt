package com.weare.wearecompany.ui.listcontainer.photographer

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.List.clip

class PhotoClipViewHodel(v: View) : RecyclerView.ViewHolder(v) {

    val clip = itemView.findViewById<TextView>(R.id.clip_text)

    fun bindWithView(Item: clip, onClickListener: View.OnClickListener) {

        when (Item.main_type) {
            0 -> {
                when (Item.sub_type) {
                    0 -> clip.text = "프로필"
                    1 -> clip.text = "뷰티/패션/쥬얼리"
                    2 -> clip.text = "패션화보"
                    3 -> clip.text = "출장/행사"
                    4 -> clip.text = "제품"
                    5 -> clip.text = "쇼핑몰"
                    6 -> clip.text = "푸드"
                    7 -> clip.text = "아기"
                    8 -> clip.text = "커플/우정"
                    9 -> clip.text = "가족"
                    10 -> clip.text = "임신기념"
                    11 -> clip.text = "아마추어"
                    12 -> clip.text = "감성"
                    13 -> clip.text = "영상(광고/유튜브)"
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