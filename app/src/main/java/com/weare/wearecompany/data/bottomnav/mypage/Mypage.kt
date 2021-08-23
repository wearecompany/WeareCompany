package com.weare.wearecompany.data.bottomnav.mypage

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Mypage(
    @SerializedName("profile_image") val profile_image: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("like_num") val like_num: Int,
    @SerializedName("review_num") val review_num: Int
) : Serializable