package com.weare.wearecompany.data.bottomnav.estimate

import com.google.gson.annotations.SerializedName
import java.io.File

data class request(
    @SerializedName("user_idx") val user_idx: String,
    @SerializedName("expert_type") val expert_type: Int,
    @SerializedName("expert_category") val expert_category: Int,
    @SerializedName("place") val place: Int,
    @SerializedName("request_thumbnail") val request_thumbnail: List<File>,
    @SerializedName("request_dt_status") val request_dt_status: Boolean,
    @SerializedName("request_dt") val request_dt: String,
    @SerializedName("request_start_time") val coupon_sub: String,
    @SerializedName("request_end_time") val recomment_code: String,
    @SerializedName("request_contents") val tel: String,
)