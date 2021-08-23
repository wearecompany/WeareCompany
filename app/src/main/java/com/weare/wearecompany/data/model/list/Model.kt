package com.weare.wearecompany.data.model.list.data

import com.google.gson.annotations.SerializedName

data class Model (
        @SerializedName("new") val new: List<newlist>,
        @SerializedName("model") val model: List<Dmodel>)