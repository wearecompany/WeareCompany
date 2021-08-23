package com.weare.wearecompany.ui.detail

import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.databinding.ActivityDetailPinchBinding
import com.weare.wearecompany.ui.base.BaseActivity


class PinchActivity : BaseActivity<ActivityDetailPinchBinding>(
    R.layout.activity_detail_pinch
) {

    var imageuri = ""
    override fun onCreate() {
        val toobar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toobar)
        val ActionBar = supportActionBar!!
        ActionBar.setDisplayHomeAsUpEnabled(true)
        ActionBar.setDisplayShowTitleEnabled(false)

        imageuri = intent.getStringExtra("image").toString()

        val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

        Glide.with(MyApplication.instance)
            .load(imageuri)
            .fallback(R.drawable.not_load_image)
            .transition(withCrossFade(factory))
            .into(DrawableImageViewTarget(mViewDataBinding.photoView))

            /*Picasso.get().load(imageuri).into(object : Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
               mViewDataBinding.photoView.setImageBitmap(bitmap)
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {

            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

            }

        })*/


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