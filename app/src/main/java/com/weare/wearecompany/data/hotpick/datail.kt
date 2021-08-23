package com.weare.wearecompany.data.hotpick

import com.weare.wearecompany.data.hotpick.data.review
import com.weare.wearecompany.data.hotpick.data.room
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class datail(
    @SerializedName("idx") val idx: String,
    @SerializedName("like_status") val like_status: Boolean,
    @SerializedName("name") val name: String,
    @SerializedName("images") val images: ArrayList<String>,
    @SerializedName("sub_category") val sub_category: String,
    @SerializedName("title") val title: String,
    @SerializedName("latitude") val latitude: String,
    @SerializedName("longitude") val longitude: String,
    @SerializedName("address") val address: String,
    @SerializedName("room") val room: ArrayList<room>,
    @SerializedName("event") val event: ArrayList<String>,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("expert_user_idx") val expert_user_idx: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("info") val info: ArrayList<String>,
    @SerializedName("rule") val rule: String,
    @SerializedName("parking") val parking: String,
    @SerializedName("grade_avr") val grade_avr: String,
    @SerializedName("review") val review: ArrayList<review>,
    @SerializedName("like") val like: Int,
    @SerializedName("scrap") val scrap: Int,
    @SerializedName("review_num") val review_num: Int
): Serializable