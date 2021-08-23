package com.weare.wearecompany.data.model.list

import com.google.gson.annotations.SerializedName
import com.weare.wearecompany.data.List.model.photographer.data.photo
import com.weare.wearecompany.data.model.list.data.Dmodel
import com.weare.wearecompany.data.model.list.data.Mstate
import com.weare.wearecompany.data.model.list.data.newlist

data class ChangeList (
    var new: List<newlist>,
    var top: Mstate,
    var list: List<Dmodel>,
        )