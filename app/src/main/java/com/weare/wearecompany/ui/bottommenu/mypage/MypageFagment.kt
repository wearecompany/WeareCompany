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
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.retrofit.bottomnav.mypage.mypageManager
import com.weare.wearecompany.databinding.FragmentMypageBinding
import com.weare.wearecompany.ui.base.BaseFragment
import com.weare.wearecompany.ui.bottommenu.mypage.bookmark.BookMarkActivity
import com.weare.wearecompany.ui.bottommenu.mypage.information.InformationActivity
import com.weare.wearecompany.ui.bottommenu.mypage.informationchange.InformationChangeActivity
import com.weare.wearecompany.ui.bottommenu.mypage.notice.NoticeActivity
import com.weare.wearecompany.ui.bottommenu.mypage.review.MyReviewActivity
import com.weare.wearecompany.ui.bottommenu.mypage.setting.SettingActivity
import com.weare.wearecompany.ui.container.ContainerActivity
import com.weare.wearecompany.ui.login.login
import com.weare.wearecompany.utils.RESPONSE_STATUS

class mypageFagment: BaseFragment<FragmentMypageBinding> (
    R.layout.fragment_mypage
), View.OnClickListener {

    lateinit var userImage:String
    lateinit var mContext: Context
    lateinit var userIdx:String
    val REQ_CAMERA_PERMISSION = 1001;

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ContainerActivity) {
            mContext = context
        }
    }

    override fun onCreate() {

        userIdx = MyApplication.prefs.getString("user_idx","")
        setup()

        if (userIdx != "") {
            mypageManager.instance.data(MyApplication.prefs.getString("user_idx",""),completion ={responseStatus,response ->

                when(responseStatus) {
                    RESPONSE_STATUS.OKAY -> {
                        mViewDataBinding.notUserLayout.visibility = View.GONE
                        userImage = response[0].profile_image
                        var multiTransformation = MultiTransformation(CenterCrop(), RoundedCorners(20))
                        Glide.with(this)
                            .load(response[0].profile_image)
                            .placeholder(R.drawable.not_load_image)
                            .fallback(R.drawable.not_load_image)
                            .apply(RequestOptions.bitmapTransform(multiTransformation))
                            .into(mViewDataBinding.userImage)

                        mViewDataBinding.userName.setText(response[0].nickname)
                    }
                }
            })
        } else {
            mViewDataBinding.informationChange.visibility = View.GONE
            mViewDataBinding.bookmark.visibility = View.GONE
            mViewDataBinding.myReviewList.visibility = View.GONE
            mViewDataBinding.setting.visibility = View.GONE
            mViewDataBinding.userTextLayout.visibility = View.GONE
            mViewDataBinding.notUserLayout.visibility = View.VISIBLE
        }


    }

    fun setup() {
        mViewDataBinding.bookmark.setOnClickListener(this)
        mViewDataBinding.myReviewList.setOnClickListener(this)
        mViewDataBinding.information.setOnClickListener(this)
        mViewDataBinding.notice.setOnClickListener(this)
        mViewDataBinding.informationChange.setOnClickListener(this)
        mViewDataBinding.setting.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.weare_tel -> {
                /*if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    //안드로이드 M 이상일 경우
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        mContext.startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "0260144915")))
                        //전화 권한이 있을경우
                    } else {
                        //권한이 없을경우
                    }
                } else { //안드로이드 M 이하일 경우
                    mContext.startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "0260144915")))
                }*/

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

            R.id.user_loging_btn -> {
                var newIntent = Intent(mContext, login::class.java)
                newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(newIntent)
            }


        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        /*if (requestCode == 999) {
            getFragmentManager()?.let {
                refreshFragment(this, it)
            }
        }*/
        if (resultCode == 998) {
            infocheang()
        }
    }

    fun infocheang() {
        mypageManager.instance.data(MyApplication.prefs.getString("user_idx",""),completion ={responseStatus,response ->

            when(responseStatus) {
                RESPONSE_STATUS.OKAY -> {
                    mViewDataBinding.notUserLayout.visibility = View.GONE
                    userImage = response[0].profile_image
                    var multiTransformation = MultiTransformation(CenterCrop(), RoundedCorners(20))
                    Glide.with(this)
                        .load(response[0].profile_image)
                        .placeholder(R.drawable.not_load_image)
                        .fallback(R.drawable.not_load_image)
                        .apply(RequestOptions.bitmapTransform(multiTransformation))
                        .into(mViewDataBinding.userImage)

                    mViewDataBinding.userName.setText(response[0].nickname)
                }
            }
        })
    }

    // Fragment 새로고침
    fun refreshFragment(fragment: Fragment, fragmentManager: FragmentManager) {
        var ft: FragmentTransaction = fragmentManager.beginTransaction()
        ft.detach(fragment).attach(fragment).commit()
    }

}