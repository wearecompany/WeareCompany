package com.weare.wearecompany.data.main

import com.google.gson.annotations.SerializedName
import com.weare.wearecompany.data.main.data.hotpick
import com.weare.wearecompany.data.main.data.list

data class HotpickList (
    @SerializedName("hotpick") val hotpick: ArrayList<hotpick>
)