package com.weare.wearecompany.data.List.trip

import com.weare.wearecompany.data.List.trip.data.trip
import com.google.gson.annotations.SerializedName
import com.weare.wearecompany.data.model.list.data.newlist

data class Trip (
    @SerializedName("new") val new: List<newlist>,
    @SerializedName("trip") val rent: List<trip>
)