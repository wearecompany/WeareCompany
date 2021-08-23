package com.weare.wearecompany.data.model.dateil
import com.google.gson.annotations.SerializedName
import com.weare.wearecompany.data.hotpick.data.review
import java.io.Serializable

data class dateilModel(
        @SerializedName("idx") val idx: String,
        @SerializedName("like_status") val like_status: Boolean,
        @SerializedName("name") val name: String,
        @SerializedName("images") val images: List<String>,   //배열
        @SerializedName("sub_category") val sub_category: String,
        @SerializedName("title") val title: String,
        @SerializedName("address") val address: String,
        @SerializedName("expert_user_idx") val expert_user_idx: String,
        @SerializedName("thumbnail") val thumbnail: String,
        @SerializedName("price") val price: String,
        @SerializedName("tall") val tall: String,
        @SerializedName("feet") val feet: String,
        @SerializedName("top_size") val top_size: String,
        @SerializedName("bottom_size") val bottom_size: String,
        @SerializedName("career") val career: String,
        @SerializedName("info") val info: String,
        @SerializedName("rule") val rule: String,
        @SerializedName("grade_avr") val grade_avr: String,
        @SerializedName("review") val review: ArrayList<review>,
        @SerializedName("like") val like: Int,
        @SerializedName("scrap") val scrap: Int,
        @SerializedName("review_num") val review_num: Int
): Serializable