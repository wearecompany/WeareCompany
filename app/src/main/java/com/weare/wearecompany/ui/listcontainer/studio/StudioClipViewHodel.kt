package com.weare.wearecompany.ui.listcontainer.studio

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.List.clip

class StudioClipViewHodel(v: View) : RecyclerView.ViewHolder(v) {

    val clip = itemView.findViewById<TextView>(R.id.clip_text)

    fun bindWithView(Item: clip, onClickListener: View.OnClickListener) {

        when (Item.main_type) {
            0 -> {
                when (Item.sub_type) {
                    0 -> clip.text = "자연광"
                    1 -> clip.text = "호리존"
                    2 -> clip.text = "웨딩"
                    3 -> clip.text = "파티"
                    4 -> clip.text = "루프탑"
                    5 -> clip.text = "키친"
                    6 -> clip.text = "엔틱"
                    7 -> clip.text = "컬러배경"
                    8 -> clip.text = "빈티지"
                    9 -> clip.text = "감성"
                    10 -> clip.text = "컨셉별"
                    11 -> clip.text = "셀프인물"
                    12 -> clip.text = "야외 촬영"
                    13 -> clip.text = "일반 스튜디오"
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