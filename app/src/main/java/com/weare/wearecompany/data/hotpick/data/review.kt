package com.weare.wearecompany.data.hotpick.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class review (
    @SerializedName("idx") val idx: String,
    @SerializedName("profile") val profile: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("grade") val grade: Int,
    @SerializedName("datetime") val datetime: String,
    @SerializedName("contents") val contents: String,
    @SerializedName("image") val image: String,
    @SerializedName("reply") val reply: String
    ) : Serializable