package com.weare.wearecompany.ui.bottommenu.mypage

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.bottomnav.mypage.Mypage
import com.weare.wearecompany.data.retrofit.bottomnav.mypage.mypageManager
import com.weare.wearecompany.databinding.FragmentChangeMypageBinding
import com.weare.wearecompany.ui.base.BaseFragment
import com.weare.wearecompany.ui.bottommenu.mypage.bookmark.BookMarkActivity
import com.weare.wearecompany.ui.bottommenu.mypage.information.InformationActivity
import com.weare.wearecompany.ui.bottommenu.mypage.informationchange.InformationChangeActivity
import com.weare.wearecompany.ui.bottommenu.mypage.notice.NoticeActivity
import com.weare.wearecompany.ui.bottommenu.mypage.request.RequestListActivity
import com.weare.wearecompany.ui.bottommenu.mypage.review.MyReviewActivity
import com.weare.wearecompany.ui.bottommenu.mypage.setting.SettingActivity
import com.weare.wearecompany.ui.container.ContainerActivity
import com.weare.wearecompany.utils.RESPONSE_STATUS

class TestMyPageFragment : BaseFragment<FragmentChangeMypageBinding>(
    R.layout.fragment_change_mypage
), View.OnClickListener {

    lateinit var mContext: Context
    lateinit var userIdx: String
    private var post_ca_btn = 0
    val REQ_CAMERA_PERMISSION = 1001

    lateinit var detalist: ArrayList<Mypage>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ContainerActivity) {
            mContext = context
        }
    }

    override fun onCreate() {

        userIdx = MyApplication.prefs.getString("user_idx", "")

        bindsetup()
        setup()

    }

    private fun bindsetup() {
        mViewDataBinding.movePagerList.setOnClickListener(this)
        mViewDataBinding.mypageSetting.setOnClickListener(this)
        mViewDataBinding.mypageRequestCaBtn1.setOnClickListener(this)
        mViewDataBinding.mypageRequestCaBtn2.setOnClickListener(this)
        mViewDataBinding.mypageProfileEdit.setOnClickListener(this)
        mViewDataBinding.mypageBookmarker.setOnClickListener(this)
        mViewDataBinding.mypageReview.setOnClickListener(this)
        mViewDataBinding.mypageInformation.setOnClickListener(this)
        mViewDataBinding.mypageNotice.setOnClickListener(this)
        mViewDataBinding.adviceKakao.setOnClickListener(this)
        mViewDataBinding.weareTel.setOnClickListener(this)
    }

    private fun setup() {

        if (userIdx != "") {
            mypageManager.instance.data(userIdx, completion = { responseStatus, response ->
                when (responseStatus) {
                    RESPONSE_STATUS.OKAY -> {
                        detalist = response
                        var multiTransformation =
                            MultiTransformation(CenterCrop(), RoundedCorners(150))
                        Glide.with(this)
                            .load(response[0].profile_image)
                            .placeholder(R.drawable.not_load_image)
                            .fallback(R.drawable.not_load_image)
                            .apply(RequestOptions.bitmapTransform(multiTransformation))
                            .into(mViewDataBinding.mypageProfile)

                        mViewDataBinding.mypageName.setText(response[0].nickname)

                        mViewDataBinding.mypageBookmarkerCount.text =
                            response[0].like_num.toString()
                        mViewDataBinding.mypageReviewCount.text = response[0].review_num.toString()

                        mViewDataBinding.mypageRequestCa1.text = response[0].request.toString()
                        mViewDataBinding.mypageRequestCa2.text = response[0].response.toString()
                        mViewDataBinding.mypageRequestCa3.text = response[0].progress.toString()
                        mViewDataBinding.mypageRequestCa4.text = response[0].complete.toString()
                        mViewDataBinding.mypageRequestCa5.text = response[0].review.toString()
                        mViewDataBinding.mypageRequestCa6.text =
                            response[0].refund_request.toString()
                        mViewDataBinding.mypageRequestCa7.text =
                            response[0].refund_complete.toString()
                    }
                }
            })
        } else {

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
            profilecheang()
        }
    }

    fun profilecheang() {
        mypageManager.instance.data(userIdx, completion = { responseStatus, response ->

            when (responseStatus) {
                RESPONSE_STATUS.OKAY -> {
                    detalist = response

                    var multiTransformation = MultiTransformation(CenterCrop(), RoundedCorners(150))
                    Glide.with(this)
                        .load(response[0].profile_image)
                        .placeholder(R.drawable.not_load_image)
                        .fallback(R.drawable.not_load_image)
                        .apply(RequestOptions.bitmapTransform(multiTransformation))
                        .into(mViewDataBinding.mypageProfile)

                    mViewDataBinding.mypageName.setText(response[0].nickname)

                    mViewDataBinding.mypageRequestCa1.text = response[0].request.toString()
                    mViewDataBinding.mypageRequestCa1.text = response[0].response.toString()
                    mViewDataBinding.mypageRequestCa1.text = response[0].progress.toString()
                    mViewDataBinding.mypageRequestCa1.text = response[0].complete.toString()
                    mViewDataBinding.mypageRequestCa1.text = response[0].review.toString()
                }
            }
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mypage_request_ca_btn_1 -> {
                if (post_ca_btn != 0) {
                    mViewDataBinding.mypageRequestCaBtn1.setBackgroundResource(R.drawable.mypage_ca_on)
                    mViewDataBinding.mypageRequestCaBtn1.setTextColor(Color.parseColor("#6d34f3"))
                    mViewDataBinding.mypageRequestCaBtn2.setBackgroundResource(R.drawable.mypage_ca_off)
                    mViewDataBinding.mypageRequestCaBtn2.setTextColor(Color.parseColor("#838383"))
                    mViewDataBinding.mypageRequestCaLayout1.visibility = View.VISIBLE
                    mViewDataBinding.mypageRequestCaLayout2.visibility = View.GONE
                    post_ca_btn = 0
                }
            }
            R.id.mypage_request_ca_btn_2 -> {
                if (post_ca_btn != 1) {
                    mViewDataBinding.mypageRequestCaBtn2.setBackgroundResource(R.drawable.mypage_ca_on)
                    mViewDataBinding.mypageRequestCaBtn2.setTextColor(Color.parseColor("#6d34f3"))
                    mViewDataBinding.mypageRequestCaBtn1.setBackgroundResource(R.drawable.mypage_ca_off)
                    mViewDataBinding.mypageRequestCaBtn1.setTextColor(Color.parseColor("#838383"))
                    mViewDataBinding.mypageRequestCaLayout2.visibility = View.VISIBLE
                    mViewDataBinding.mypageRequestCaLayout1.visibility = View.GONE
                    post_ca_btn = 1
                }
            }
            R.id.mypage_setting -> {
                var newIntent = Intent(mContext, SettingActivity::class.java)
                startActivity(newIntent)
            }
            R.id.mypage_profile_edit -> {
                var newIntent = Intent(mContext, InformationChangeActivity::class.java)
                newIntent.putExtra("image", detalist[0].profile_image)
                newIntent.putExtra("nicname", detalist[0].nickname)
                startActivityForResult(newIntent, 999)
            }
            R.id.mypage_bookmarker -> {
                var newIntent = Intent(mContext, BookMarkActivity::class.java)
                startActivity(newIntent)
            }
            R.id.mypage_review -> {
                var newIntent = Intent(mContext, MyReviewActivity::class.java)
                startActivity(newIntent)
            }
            R.id.move_pager_list -> {
                var newIntent = Intent(context, RequestListActivity::class.java)
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

            R.id.weare_tel -> {
                var permission =
                    ContextCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE)
                if (permission != PackageManager.PERMISSION_GRANTED) { // 권한 없어서 요청
                    ActivityCompat.requestPermissions(
                        context as Activity,
                        arrayOf(Manifest.permission.CALL_PHONE),
                        REQ_CAMERA_PERMISSION
                    )
                } else {
                    mContext.startActivity(
                        Intent(
                            Intent.ACTION_CALL,
                            Uri.parse("tel:" + "0263961448")
                        )
                    )
                }
            }
            R.id.advice_kakao -> {

                var urll = "https://pf.kakao.com/_xlbDxjK/chat"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(urll)
                startActivity(intent)
            }
        }
    }
}