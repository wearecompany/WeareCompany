package com.weare.wearecompany.data.main

import com.google.gson.annotations.SerializedName
import com.weare.wearecompany.data.main.data.list

data class HomeList (
    @SerializedName("list") val home_list: ArrayList<list>
)