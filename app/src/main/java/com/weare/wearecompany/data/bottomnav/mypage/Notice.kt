package com.weare.wearecompany.data.bottomnav.mypage

import com.weare.wearecompany.data.bottomnav.mypage.data.notice
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Notice (
    @SerializedName("notice") val notice: List<notice>,
): Serializable
