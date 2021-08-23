package com.weare.wearecompany.ui.bottommenu.mypage.information

import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.weare.wearecompany.R
import com.weare.wearecompany.databinding.ActivityInformationDetailBinding
import com.weare.wearecompany.ui.base.BaseActivity

class InformationDetailActivity: BaseActivity<ActivityInformationDetailBinding> (
    R.layout.activity_information_detail
) {
    override fun onCreate() {

        val toobar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toobar)
        val ActionBar = supportActionBar!!
        ActionBar.setDisplayHomeAsUpEnabled(true)
        ActionBar.setDisplayShowTitleEnabled(false)
        mViewDataBinding.infoDetailTitle.setText(intent.getStringExtra("title"))

        setup()
    }

    fun setup() {
        mViewDataBinding.infoDetailQuestion.setText(intent.getStringExtra("qurstion"))
        mViewDataBinding.infoDetailContents.setText(intent.getStringExtra("contents"))
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