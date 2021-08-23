package com.weare.wearecompany.data.bottomnav.estimate.send


import com.weare.wearecompany.data.bottomnav.estimate.send.data.sendExpert
import com.weare.wearecompany.data.bottomnav.estimate.send.data.sendStudio
import com.weare.wearecompany.data.bottomnav.estimate.send.data.sendShop
import java.io.Serializable

data class SendReserve (
    var studio: sendStudio,
    var expert: sendExpert,
): Serializable