package com.weare.wearecompany.data.bottomnav.mypage

import com.google.gson.annotations.SerializedName
import com.weare.wearecompany.data.bottomnav.mypage.data.myreview
import java.io.Serializable

data class MyReviewList (
    @SerializedName("review") val review: List<myreview>,
): Serializable