package com.weare.wearecompany.ui.chat

import android.view.MenuItem
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.databinding.ActivityChatImagePinchBinding
import com.weare.wearecompany.ui.base.BaseActivity
import java.lang.Math.max
import java.lang.Math.min


class ChatPinchActivity:BaseActivity<ActivityChatImagePinchBinding>(
    R.layout.activity_chat_image_pinch
) {

    private var scaleGestureDetector: ScaleGestureDetector? = null
    private var scaleFactor = 1.0f
    private var image_uri: String = ""

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        scaleGestureDetector?.onTouchEvent(event)
        return true
    }

    override fun onCreate() {

        val toobar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toobar)
        val ActionBar = supportActionBar!!
        ActionBar.setDisplayHomeAsUpEnabled(true)
        ActionBar.setDisplayShowTitleEnabled(false)

        image_uri = intent.getStringExtra("image").toString()

        Glide.with(MyApplication.instance)
            .load(image_uri)
            .into(mViewDataBinding.chatImage)

        scaleGestureDetector = ScaleGestureDetector(this, ScaleListener())
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

    inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(scaleGestureDetector: ScaleGestureDetector): Boolean {

            scaleFactor *= scaleGestureDetector.scaleFactor

            // 최소 0.5, 최대 2배
            scaleFactor = max(0.5f, min(scaleFactor, 2.0f))

            // 리사이클러뷰에 적용
            mViewDataBinding.chatImage.scaleX = scaleFactor
            mViewDataBinding.chatImage.scaleY = scaleFactor
            return true
        }
    }
}

