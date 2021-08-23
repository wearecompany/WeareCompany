package com.weare.wearecompany.data.model.dateil.data

import java.io.Serializable

data class review(var idx: String,
                  var profile: String,
                  var nickname: String?,
                  var grade: String?,
                  var datetime: String?,
                  var contents: String?,
                  var image: String?,
                  var reply: String?) : Serializable