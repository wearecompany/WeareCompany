package com.weare.wearecompany.data.studio.main

import com.weare.wearecompany.data.main.data.*
import com.google.gson.annotations.SerializedName
import com.google.gson.internal.LinkedTreeMap

data class Main (
        @SerializedName("banner") val banner: ArrayList<banner>,
        @SerializedName("list") val list: ArrayList<list>,
        @SerializedName("bottom_banner") val bottom_banner: ArrayList<bottom_banner>,
        @SerializedName("footer") val footer: LinkedTreeMap<String, String>,
        @SerializedName("company_info") val company_info: String?,
        @SerializedName("tel") val tel: String?,
        @SerializedName("sub_category") val sub_category: List<List<String>>?,
        @SerializedName("sub_max_count") val sub_max_count: List<Int>?,
        @SerializedName("location") val location: List<String>?,
        @SerializedName("location_max_count") val location_max_count: List<Int>?
        )