package com.weare.wearecompany.data.main

import com.weare.wearecompany.data.main.data.guidelist
import com.google.gson.annotations.SerializedName

data class Guide (
    @SerializedName("guide") val guideList: List<guidelist>
)