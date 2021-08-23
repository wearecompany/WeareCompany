package com.weare.wearecompany.data.notification

import com.google.gson.annotations.SerializedName
import com.weare.wearecompany.data.chatting.data.chat
import com.weare.wearecompany.data.notification.data.alarm

data class AlarmList (
    @SerializedName("alarm") val alarm_list: ArrayList<alarm>
)