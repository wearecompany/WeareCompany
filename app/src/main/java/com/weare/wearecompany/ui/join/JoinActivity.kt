package com.weare.wearecompany.ui.join


import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.weare.wearecompany.R
import com.weare.wearecompany.databinding.ActivityJoinBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.ui.container.ContainerActivity
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.data.retrofit.MemberManager
import com.weare.wearecompany.utils.RESPONSE_STATUS

class JoinActivity : BaseActivity<ActivityJoinBinding>(
    R.layout.activity_join,
    //JoinViewModel::class
),View.OnClickListener {

    var check = true

    private var joincount = false

    override fun onCreate() {
        val toobar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toobar)
        val ActionBar = supportActionBar!!
        ActionBar.setDisplayHomeAsUpEnabled(true)
        ActionBar.setDisplayShowTitleEnabled(false)

        setup()

    }

    fun setup() {
        if (intent.hasExtra("user_nickname")) {
            mViewDataBinding.joinNicName.setText(intent.getStringExtra("user_nickname"))
        }
        mViewDataBinding.personal0.setOnClickListener(this)
        mViewDataBinding.personal1.setOnClickListener(this)
        mViewDataBinding.joinAllButton.setOnClickListener(this)
        mViewDataBinding.joinOk.setOnClickListener(this)

        mViewDataBinding.joinBtn1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                buttonView.buttonTintList = ColorStateList.valueOf(Color.parseColor("#6d34f3"))
            } else {
                buttonView.buttonTintList = ColorStateList.valueOf(Color.parseColor("#eaeaea"))
            }
        }
        mViewDataBinding.joinBtn2.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                buttonView.buttonTintList = ColorStateList.valueOf(Color.parseColor("#6d34f3"))
            } else {
                buttonView.buttonTintList = ColorStateList.valueOf(Color.parseColor("#eaeaea"))
            }
        }
        mViewDataBinding.joinBtn3.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                buttonView.buttonTintList = ColorStateList.valueOf(Color.parseColor("#6d34f3"))
            } else {
                buttonView.buttonTintList = ColorStateList.valueOf(Color.parseColor("#eaeaea"))
            }
        }
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
            R.id.personal_0 -> {
                var newIntent = Intent(this, PersonaActivity::class.java)
                newIntent.putExtra("type", "?type=0")
                newIntent.putExtra("num", 0)
                startActivity(newIntent)
            }
            R.id.personal_1 -> {
                var newIntent = Intent(this, PersonaActivity::class.java)
                newIntent.putExtra("type", "?type=1")
                newIntent.putExtra("num", 1)
                startActivity(newIntent)
            }
            R.id.join_allButton -> {
                if (!joincount) {
                    joincount = true
                    mViewDataBinding.joinBtn1.isChecked= true
                    mViewDataBinding.joinBtn2.isChecked = true
                    mViewDataBinding.joinBtn3.isChecked = true
                } else if (joincount) {
                    joincount = false
                    mViewDataBinding.joinBtn1.isChecked = false
                    mViewDataBinding.joinBtn2.isChecked = false
                    mViewDataBinding.joinBtn3.isChecked = false
                }
            }
            R.id.join_ok -> {
                if (mViewDataBinding.joinNicName.text.toString() == "") {
                    Toast.makeText(this, "활동명을 입력해주세요.", Toast.LENGTH_SHORT).show()
                } else if (!mViewDataBinding.joinBtn1.isChecked) {
                    Toast.makeText(this, "이용약관을 동의해주세요..", Toast.LENGTH_SHORT).show()
                } else if (!mViewDataBinding.joinBtn2.isChecked) {
                    Toast.makeText(this, "개인정보 처리 방침을 동의해주세요.", Toast.LENGTH_SHORT).show()
                } else {
                    MemberManager.instance.join(
                        intent.getStringExtra("user_uid"),
                        0,
                        intent.getStringExtra("user_email"),
                        intent.getStringExtra("user_profile_image"),
                        mViewDataBinding.joinNicName.text.toString(),
                        mViewDataBinding.joinBtn3.isChecked,
                        "",
                        intent.getStringExtra("token"),
                        completion = { responseStatus, arrayList ->
                            when (responseStatus) {
                                RESPONSE_STATUS.OKAY -> {
                                    var newIntent =
                                        Intent(this@JoinActivity, ContainerActivity::class.java)
                                    MyApplication.prefs.setString("user_idx", arrayList[0].user_idx)
                                    newIntent.putExtra("user_idx", arrayList[0].user_idx)
                                    newIntent.putExtra("user_nickname", arrayList[0].user_nickname)
                                    newIntent.putExtra("user_image", arrayList[0].user_image)
                                    startActivity(newIntent)
                                }
                                RESPONSE_STATUS.NO_JOIN -> {
                                    Toast.makeText(this, "회원가입에 실패하였습니다.", Toast.LENGTH_SHORT)
                                        .show()
                                    finish()
                                }

                            }
                        }
                    )
                }
            }
        }
    }

}