package com.weare.wearecompany.data.studio

import com.google.gson.annotations.SerializedName

data class Review (
    @SerializedName("idx") val idx: String,
    @SerializedName("profile") val name: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("grade") val grade: String,
    @SerializedName("datetime") val datetime: String,
    @SerializedName("contents") val contents: String,
    @SerializedName("image") val image: List<String>,
    @SerializedName("reply") val etc: String,
    )