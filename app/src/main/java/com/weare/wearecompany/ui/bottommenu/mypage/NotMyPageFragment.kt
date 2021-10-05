package com.weare.wearecompany.ui.bottommenu.mypage

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.weare.wearecompany.R
import com.weare.wearecompany.databinding.FragmentChangeNotUserMypageBinding
import com.weare.wearecompany.ui.base.BaseFragment
import com.weare.wearecompany.ui.bottommenu.mypage.information.InformationActivity
import com.weare.wearecompany.ui.bottommenu.mypage.notice.NoticeActivity
import com.weare.wearecompany.ui.container.ContainerActivity
import com.weare.wearecompany.ui.login.login

class NotMyPageFragment: BaseFragment<FragmentChangeNotUserMypageBinding>(
    R.layout.fragment_change_not_user_mypage
),View.OnClickListener {

    lateinit var mContext: Context
    val REQ_CAMERA_PERMISSION = 1001

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ContainerActivity) {
            mContext = context
        }
    }

    override fun onCreate() {
        bindSetUp()
    }

    private fun bindSetUp() {
        mViewDataBinding.mypageLogin.setOnClickListener(this)
        mViewDataBinding.mypageInformation.setOnClickListener(this)
        mViewDataBinding.mypageNotice.setOnClickListener(this)
        mViewDataBinding.notWeareTel.setOnClickListener(this)
        mViewDataBinding.notAdviceKakao.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.mypage_login -> {
                var newIntent = Intent(mContext, login::class.java)
                newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(newIntent)
            }
            R.id.mypage_information -> {
                var newIntent = Intent(mContext, InformationActivity::class.java)
                startActivity(newIntent)
            }
            R.id.mypage_notice -> {
                var newIntent = Intent(mContext, NoticeActivity::class.java)
                startActivity(newIntent)
            }
            R.id.not_weare_tel -> {
                var permission = ContextCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE)
                if (permission != PackageManager.PERMISSION_GRANTED) { // 권한 없어서 요청
                    ActivityCompat.requestPermissions(
                        context as Activity,
                        arrayOf(Manifest.permission.CALL_PHONE),
                        REQ_CAMERA_PERMISSION
                    )
                } else {
                    mContext.startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "0263961448")))
                }
            }
            R.id.not_advice_kakao -> {
                var urll = "https://pf.kakao.com/_xlbDxjK/chat"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(urll)
                startActivity(intent)
            }
        }
    }
}