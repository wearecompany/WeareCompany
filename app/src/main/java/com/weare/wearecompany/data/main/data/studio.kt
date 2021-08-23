package com.weare.wearecompany.data.main.data

import java.io.Serializable

data class studio (var idx: String?,
                   var profile: String?,
                   var image: String?,
                   var sub: String?,
                   var title: String?,
                   var address: String?,
                   var price: String?,
                   var grade: String?,
                   var event: ArrayList<String>?) : Serializable {
}