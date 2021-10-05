package com.weare.wearecompany.ui.bottommenu.estimate.send

import android.content.Intent
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.retrofit.bottomnav.estimate.requestManager
import com.weare.wearecompany.data.retrofit.bottomnav.estimate.sendManager
import com.weare.wearecompany.databinding.ActivityUserManySendRequestBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.ui.bottommenu.estimate.receive.ReceiveRequestRecyclerViewAdapter
import com.weare.wearecompany.ui.bottommenu.estimate.receive.payment.paymentActivity
import com.weare.wearecompany.ui.chat.ChatActivity
import com.weare.wearecompany.utils.ESTIMATE
import com.weare.wearecompany.utils.RESPONSE_STATUS

class SendRequestActivity:BaseActivity<ActivityUserManySendRequestBinding>(
    R.layout.activity_user_many_send_request
){

    override fun onCreate() {

    }

}