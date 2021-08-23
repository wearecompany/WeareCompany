package com.weare.wearecompany.data.List.rent

import com.weare.wearecompany.data.List.rent.data.rent
import com.google.gson.annotations.SerializedName

data class Rent (
        @SerializedName("rent") val rent: List<rent>
        )