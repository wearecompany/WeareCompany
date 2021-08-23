package com.weare.wearecompany.data.login

import com.google.gson.annotations.SerializedName

data class Login (
        @SerializedName("user_idx") val user_idx: String,
        @SerializedName("user_nickname") val user_nickname: String,
        @SerializedName("user_image") val user_image: String,
        @SerializedName("user_type") val user_type: String,
        @SerializedName("user_push") val user_push: Boolean,
        @SerializedName("user_marketing") val user_marketing: Boolean,
        @SerializedName("user_token") val user_token: String
)
