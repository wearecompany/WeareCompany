package com.weare.wearecompany.data.bottomnav.estimate

import com.google.gson.annotations.SerializedName

data class ReceiveProgress (
    @SerializedName("request_idx") val request_idx: String,
    @SerializedName("request_log_idx") val request_log_idx: String,
    @SerializedName("category") val category: String,
    @SerializedName("price") val price: String,
    @SerializedName("user_nickname") val user_nickname: String,
    @SerializedName("user_thumbnail") val user_thumbnail: String,
    @SerializedName("datetime") val datetime: String,
    @SerializedName("request_contents") val request_contents: String,
    @SerializedName("response_contents") val response_contents: String,
    @SerializedName("thumbnail") val thumbnail: String,
    )