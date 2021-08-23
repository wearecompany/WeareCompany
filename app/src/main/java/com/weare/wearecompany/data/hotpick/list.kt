package com.weare.wearecompany.data.hotpick

import java.io.Serializable

data class list (var idx: String?,
                 var target_type: Int?,
                 var target_idx: String?,
                 var name: String?,
                 var title: String?,
                 var sub: String?,
                 var Image: String?,) : Serializable {
}