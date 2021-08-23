package com.weare.wearecompany.data.studio

import com.google.gson.annotations.SerializedName

data class Rome (
    @SerializedName("image") val image: List<String>,
    @SerializedName("nema") val name: String,
    @SerializedName("price") val price: String,   //배열
    @SerializedName("time") val time: String,
    @SerializedName("contents") val contents: String,
    @SerializedName("etc") val etc: List<String>,
)