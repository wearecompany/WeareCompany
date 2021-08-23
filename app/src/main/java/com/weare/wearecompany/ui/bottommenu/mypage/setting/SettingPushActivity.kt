package com.weare.wearecompany.ui.bottommenu.mypage.setting

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationManagerCompat
import com.weare.wearecompany.R
import com.weare.wearecompany.databinding.ActivitySettingPushBinding
import com.weare.wearecompany.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_setting_push.*

class SettingPushActivity: BaseActivity<ActivitySettingPushBinding> (
    R.layout.activity_setting_push
),View.OnClickListener{
    override fun onCreate() {

        val toobar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toobar)
        val ActionBar = supportActionBar!!
        ActionBar.setDisplayHomeAsUpEnabled(true)
        ActionBar.setDisplayShowTitleEnabled(false)
        mViewDataBinding.phonePush.isChecked = NotificationManagerCompat.from(this).areNotificationsEnabled()

        setUp()
    }

    private fun setUp() {
        mViewDataBinding.phonePush.setOnClickListener(this)
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

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.phone_push -> {
                var newIntent = Intent()
               if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                   newIntent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                   newIntent.putExtra(Settings.EXTRA_APP_PACKAGE, this.packageName)
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                   newIntent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
                   newIntent.putExtra("app_package", this.packageName)
                   newIntent.putExtra("app_uid", this.applicationInfo?.uid)
               } else {
                   newIntent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                   newIntent.addCategory(Intent.CATEGORY_DEFAULT)
                   newIntent.data = Uri.parse("package:" + this?.packageName)

            }
                startActivity(newIntent)
            }
        }
    }
}