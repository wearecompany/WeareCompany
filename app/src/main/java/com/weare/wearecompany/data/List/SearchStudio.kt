package com.weare.wearecompany.data.List

import java.io.Serializable

data class SearchStudio (
    var title: String,
    var addr: String,
    var image: String,
    var lat: Double,
    var lon: Double
) : Serializable