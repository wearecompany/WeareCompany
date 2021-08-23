package com.weare.wearecompany.data.bottomnav.mypage

import com.google.gson.annotations.SerializedName
import com.weare.wearecompany.data.bottomnav.mypage.data.*

data class Bookmark (
    @SerializedName("studio") val Studio: List<studio>,
    @SerializedName("photo") val Photo: List<photo>,
    @SerializedName("model") val Model: List<model>,
    @SerializedName("trip") val Trip: List<trip>,
    @SerializedName("rent") val Rent: List<rent>
)