package com.weare.wearecompany.ui.bottommenu.mypage.notice

import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.weare.wearecompany.R
import com.weare.wearecompany.databinding.ActivityNoticeDetailBinding
import com.weare.wearecompany.ui.base.BaseActivity

class NoticeDetailActivity:BaseActivity<ActivityNoticeDetailBinding>(
    R.layout.activity_notice_detail
) {
    override fun onCreate() {

        val toobar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toobar)
        val ActionBar = supportActionBar!!
        ActionBar.setDisplayHomeAsUpEnabled(true)
        ActionBar.setDisplayShowTitleEnabled(false)

        setup()
    }

    fun setup() {
        mViewDataBinding.noticeDetailTitle.setText(intent.getStringExtra("title"))
        mViewDataBinding.noticeDetailDatetime.setText(intent.getStringExtra("detatime"))
        mViewDataBinding.noticeDetailContents.setText(intent.getStringExtra("contents"))
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