package com.weare.wearecompany.ui.bottommenu.main.guide

import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.weare.wearecompany.R
import com.weare.wearecompany.databinding.ActivityGuideDetailBinding
import com.weare.wearecompany.ui.base.BaseActivity

class GuideActivity: BaseActivity<ActivityGuideDetailBinding> (
    R.layout.activity_guide_detail
){
    override fun onCreate() {

        val toobar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toobar)
        val ActionBar = supportActionBar!!
        ActionBar.setDisplayHomeAsUpEnabled(true)
        ActionBar.setDisplayShowTitleEnabled(false)
        mViewDataBinding.guideWeb.getSettings().textZoom = 100  //시스템 텍스트 크기를 무시한 텍스트 사이즈 고정

        mViewDataBinding.guideWeb.loadUrl("https://dev.wearecompany.co.kr/guide/app/1/"+intent.getStringExtra("idx"))


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