package com.weare.wearecompany.data.division

import com.google.gson.annotations.SerializedName
import com.weare.wearecompany.data.division.data.division
import com.weare.wearecompany.data.division.data.recent

data class Division (
    @SerializedName("recent") val recent : ArrayList<recent>,
    @SerializedName("division") val division : ArrayList<division>,
        )