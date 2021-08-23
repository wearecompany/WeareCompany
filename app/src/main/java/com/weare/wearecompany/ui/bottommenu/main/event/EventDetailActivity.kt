package com.weare.wearecompany.ui.bottommenu.main.event

import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.retrofit.bottomnav.main.MainManager
import com.weare.wearecompany.databinding.ActivityWeeklyBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.utils.RESPONSE_STATUS

class EventDetailActivity:BaseActivity<ActivityWeeklyBinding>(
    R.layout.activity_weekly
),View.OnClickListener {

    private var event_idx = ""
    override fun onCreate() {

        val toobar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toobar)
        val ActionBar = supportActionBar!!
        ActionBar.setDisplayHomeAsUpEnabled(true)
        ActionBar.setDisplayShowTitleEnabled(false)

        event_idx = intent.getStringExtra("idx")!!

        MainManager.instance.event_page(event_idx,complation = {responseStatus, arrayList ->
            when(responseStatus) {
                RESPONSE_STATUS.OKAY -> {
                    mViewDataBinding.eventTitle.text = arrayList[0].event_title
                    mViewDataBinding.eventSubTitle.text = arrayList[0].event_sub_title
                    mViewDataBinding.eventDatetime.text = arrayList[0].event_datetime
                    mViewDataBinding.eventContents.text = arrayList[0].event_contents

                    var multiTransformation = MultiTransformation(CenterCrop(), RoundedCorners(20))
                    val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()


                    Glide.with(MyApplication.instance)
                        .load(arrayList[0].event_image)
                        .fallback(R.drawable.not_load_image)
                        .transition(DrawableTransitionOptions.withCrossFade(factory))
                        .apply(RequestOptions.bitmapTransform(multiTransformation))
                        .into(DrawableImageViewTarget(mViewDataBinding.eventImage))

                }
            }
        })

    }

    override fun onClick(v: View?) {

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}