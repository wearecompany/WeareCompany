package com.weare.wearecompany.data.bottomnav.mypage

import com.weare.wearecompany.data.bottomnav.mypage.data.manual
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Information (
    @SerializedName("manual") val manual: List<manual>,
): Serializable