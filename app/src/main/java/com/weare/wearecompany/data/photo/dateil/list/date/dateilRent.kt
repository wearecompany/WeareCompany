package com.weare.wearecompany.data.photo.dateil.list.date

import com.google.gson.annotations.SerializedName
import com.weare.wearecompany.data.hotpick.data.review
import java.io.Serializable

data class dateilRent (
    @SerializedName("idx") val idx: String,
    @SerializedName("like_status") val like_status: Boolean,
    @SerializedName("user_nickname") val user_nickname: String,
    @SerializedName("images") val images: List<String>,   //배열
    @SerializedName("sub_category") val sub_category: String,
    @SerializedName("shop_nickname") val shop_nickname: String,
    @SerializedName("title") val title: String,
    @SerializedName("latitude") val latitude: String,
    @SerializedName("longitude") val longitude: String,
    @SerializedName("address") val address: String,
    @SerializedName("expert_user_idx") val expert_user_idx: String,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("info") val info: String,
    @SerializedName("rule") val rule: String,
    @SerializedName("grade_avr") val grade_avr: String,
    @SerializedName("review") val review: ArrayList<review>,
    @SerializedName("like") val like: Int,
    @SerializedName("scrap") val scrap: Int,
    @SerializedName("review_num") val review_num: Int
): Serializable