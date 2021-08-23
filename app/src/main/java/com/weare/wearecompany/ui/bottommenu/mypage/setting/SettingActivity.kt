package com.weare.wearecompany.ui.bottommenu.mypage.setting

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.databinding.ActivitySettingBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.ui.login.login
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kakao.sdk.user.UserApiClient

class SettingActivity : BaseActivity<ActivitySettingBinding>(
    R.layout.activity_setting
), View.OnClickListener {
    override fun onCreate() {

        val toobar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toobar)
        val ActionBar = supportActionBar!!
        ActionBar.setDisplayHomeAsUpEnabled(true)
        ActionBar.setDisplayShowTitleEnabled(false)

        setUp()

    }

    private fun setUp() {
        mViewDataBinding.appLogout.setOnClickListener(this)
        mViewDataBinding.settingPush.setOnClickListener(this)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            2000 -> {
                var newIntent = Intent(this, SettingPushActivity::class.java)
                startActivity(newIntent)
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.app_logout -> {
                MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme)
                    .setTitle("로그아웃")
                    .setMessage("정말 로그아웃을 하시겠습니까?\n확인시 로그인 화면으로 돌아갑니다.")
                    .setCancelable(false)
                    .setNeutralButton("취소") { dialog, which ->
                        dialog.dismiss()
                    }
                    .setPositiveButton("확인") { dialog, which ->
                        UserApiClient.instance.logout { error ->
                            if (error != null) {
                                Log.e("실패", "로그아웃 실패. SDK에서 토큰 삭제됨", error)
                            } else {
                                dialog.dismiss()
                                MyApplication.prefs.setString("user_idx", "")
                                var newIntent = Intent(this, login::class.java)
                                newIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(newIntent)
                            }
                        }

                    }
                    .show()
            }
            R.id.setting_push -> {
                var newIntent = Intent()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
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
                startActivityForResult(newIntent,2000)
            }
        }
    }
}