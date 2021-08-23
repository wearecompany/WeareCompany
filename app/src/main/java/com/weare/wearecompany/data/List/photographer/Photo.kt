package com.weare.wearecompany.data.List.model.photographer

import com.weare.wearecompany.data.List.model.photographer.data.photo
import com.google.gson.annotations.SerializedName
import com.weare.wearecompany.data.model.list.data.newlist

data class Photo (
    @SerializedName("new") val new: List<newlist>,
    @SerializedName("photo") val photo: List<photo>
)