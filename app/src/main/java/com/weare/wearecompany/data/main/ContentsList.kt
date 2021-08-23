package com.weare.wearecompany.data.main

import com.google.gson.annotations.SerializedName
import com.weare.wearecompany.data.main.data.contents
import com.weare.wearecompany.data.main.data.list

data class ContentsList (
    @SerializedName("contents") val contents: ArrayList<contents>
        )