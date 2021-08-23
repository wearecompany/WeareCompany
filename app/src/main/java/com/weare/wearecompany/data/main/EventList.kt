package com.weare.wearecompany.data.main

import com.google.gson.annotations.SerializedName
import com.weare.wearecompany.data.main.data.banner
import com.weare.wearecompany.data.main.data.event

data class EventList(
    @SerializedName("banner") val banner : ArrayList<banner>,
    @SerializedName("event") val event : ArrayList<event>
)