package com.weare.wearecompany.ui.join

import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.weare.wearecompany.R
import com.weare.wearecompany.databinding.ActivityPersonalBinding
import com.weare.wearecompany.databinding.ActivityPhotoBinding
import com.weare.wearecompany.ui.base.BaseActivity

class PersonaActivity: BaseActivity<ActivityPersonalBinding>(
    R.layout.activity_personal
) {
    override fun onCreate() {

        val toobar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toobar)
        val ActionBar = supportActionBar!!
        ActionBar.setDisplayHomeAsUpEnabled(true)
        ActionBar.setDisplayShowTitleEnabled(false)
        if (intent.getIntExtra("num",0) == 0) {

            mViewDataBinding.toolbartext.setText("이용약관")
        } else if (intent.getIntExtra("num",0) == 1) {
            mViewDataBinding.toolbartext.setText("개인정보처리방침")
        }


        mViewDataBinding.personalWeb.loadUrl("https://wearecompany.co.kr/A00/terms/"+intent.getStringExtra("type"))
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