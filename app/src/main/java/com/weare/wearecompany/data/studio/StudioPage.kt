package com.weare.wearecompany.data.studio

import com.google.gson.annotations.SerializedName

data class studiopage(
    @SerializedName("idx") val idx: String,
    @SerializedName("nema") val name: String,
    @SerializedName("images") val images: List<String>,   //배열
    @SerializedName("sub-categes") val sub_catgort: String,
    @SerializedName("title") val title: String,
    @SerializedName("latitude") val latitude: String,
    @SerializedName("longitude") val longitude: String,
    @SerializedName("address") val address: String,
    @SerializedName("room") val room: List<Rome>,
    @SerializedName("event") val event: List<Object>,
    @SerializedName("thumbnail") val thumbanil: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("info") val info: List<String>,
    @SerializedName("rule") val rule : String,
    @SerializedName("parking") val parking: String,
    @SerializedName("grade_avr") val grade_avr: String,
    @SerializedName("review") val review: List<Review>,
    @SerializedName("like") val like: Int,
    @SerializedName("scrap") val scrap: Int,
    @SerializedName("review_num") val review_num: Int
)