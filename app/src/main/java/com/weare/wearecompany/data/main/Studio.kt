package com.weare.wearecompany.data.main

import com.weare.wearecompany.data.main.data.studio
import com.google.gson.annotations.SerializedName
import com.weare.wearecompany.data.main.data.newStudio

data class Studio (
    @SerializedName("new") val new: List<newStudio>,
    @SerializedName("studio") val studio: List<studio>
        )