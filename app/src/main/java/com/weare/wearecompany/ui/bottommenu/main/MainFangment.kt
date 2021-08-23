package com.weare.wearecompany.ui.bottommenu.main

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.tabs.TabLayout
import com.google.firebase.iid.FirebaseInstanceId
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.main.data.*
import com.weare.wearecompany.data.retrofit.IRetrofit
import com.weare.wearecompany.data.retrofit.RetrofitClient
import com.weare.wearecompany.data.retrofit.bottomnav.main.MainManager
import com.weare.wearecompany.databinding.FragmentMainBinding
import com.weare.wearecompany.ui.base.BaseFragment
import com.weare.wearecompany.ui.bottommenu.MainHomePageAdapter
import com.weare.wearecompany.ui.bottommenu.estimate.receive.payment.PhotoPaymentActivity
import com.weare.wearecompany.ui.bottommenu.main.alarm.AlarmActivity
import com.weare.wearecompany.ui.bottommenu.main.contents.ContentsFragment
import com.weare.wearecompany.ui.bottommenu.main.event.EventFragment
import com.weare.wearecompany.ui.bottommenu.main.home.HomeFragment
import com.weare.wearecompany.ui.bottommenu.main.home.HomeLIstRecyeclerViewAdapter
import com.weare.wearecompany.ui.bottommenu.main.weekly.WeeklyFragment
import com.weare.wearecompany.ui.bottommenu.main.weekly.tesdialog
import com.weare.wearecompany.ui.container.ContainerActivity
import com.weare.wearecompany.utils.API
import com.weare.wearecompany.utils.RESPONSE_STATUS
import java.util.*


class mainFangment : BaseFragment<FragmentMainBinding>(
    R.layout.fragment_main,
    //MainViewModel::class
), View.OnClickListener {

    private var bannerDataList = ArrayList<banner>()
    private var homelistDataList = ArrayList<list>()

    //private var storyDataList = ArrayList<story>()
    //private var storyDataList = ArrayList<story>()

    private lateinit var bannarAdapter: MainViewPagerAdapter
    private lateinit var homelistAdapter: HomeLIstRecyeclerViewAdapter

    private lateinit var user_idx: String
    private val hotpickpostion = IntArray(2)
    private var infoCheckBox = false

    private var listType = 2
    private var alarm = -1

    private var alarm_type_1 = ""
    private var alarm_type_2 = ""
    val REQ_tall_PERMISSION = 1001

    // 레트로핏 인터페이스 가져오기
    private val iRetrofit: IRetrofit? =
        RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    //private lateinit var bannarAdapter: MainBannerRecyeclerViewAdapter
    //private lateinit var bannarAdapter: MainBannerRecyeclerViewAdapter
    //private lateinit var bannarAdapter: MainBannerRecyeclerViewAdapter

    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ContainerActivity) {
            mContext = context
        }
    }

    override fun onCreate() {

        //throw RuntimeException("App Crashed")

        setup()
        user_idx = MyApplication.prefs.getString("user_idx", "")
        if (user_idx != "") {
            setUpFirebaseMessaging()
            alarm()
        }

        val adapter = MainHomePageAdapter(childFragmentManager)
        adapter.addFragment(HomeFragment(), "홈")
        adapter.addFragment(WeeklyFragment(), "주간의 픽")
        adapter.addFragment(EventFragment(), "이벤트")
        adapter.addFragment(ContentsFragment(), "컨텐츠")
        mViewDataBinding.mainViewpager.adapter = adapter
        mViewDataBinding.mainTab.setupWithViewPager(mViewDataBinding.mainViewpager)



        mViewDataBinding.mainTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

    }

    fun setup() {

        mViewDataBinding.notification.setOnClickListener(this)
        mViewDataBinding.textBtn.setOnClickListener(this)

        alarm_type_1 = arguments?.getString("type_1").toString()
        alarm_type_2 = arguments?.getString("type_2").toString()

    }

    fun alarm() {
        MainManager.instance.alarmcheck(0, user_idx, complation = { responseStatus, i ->
            when (responseStatus) {
                RESPONSE_STATUS.OKAY -> {
                    if (i == 0) {
                        mViewDataBinding.notificationStatus.visibility = View.GONE
                    } else {
                        mViewDataBinding.notificationStatus.visibility = View.VISIBLE
                    }
                }
            }
        })

        if (alarm_type_1 != "") {
            when(alarm_type_1) {
                "0" -> {
                    var newIntent = Intent(context, AlarmActivity::class.java)
                    newIntent.putExtra("alarm_idx",alarm_type_2)
                    startActivityForResult(newIntent, 12)
                    alarm_type_1 = ""
                    alarm_type_2 = ""
                }
                "1" -> {

                }
            }
        }
    }

    //현재 등록 토큰 가져오기
    fun setUpFirebaseMessaging() {
        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("에러", task.exception)
                return@OnCompleteListener
            }
            val token = task.result.token

            MainManager.instance.token(
                MyApplication.prefs.getString("user_idx", ""),
                token,
                completion = { responseStatus ->
                    when (responseStatus) {
                        RESPONSE_STATUS.OKAY -> {
                        }
                        RESPONSE_STATUS.NO_CONTENT -> {
                            Toast.makeText(context, "토큰을 저장하지 못했습니다.", Toast.LENGTH_SHORT).show()
                        }
                        RESPONSE_STATUS.FAIL -> {
                            Toast.makeText(context, "전송오류", Toast.LENGTH_SHORT).show()

                        }
                    }
                })
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.notification -> {
                var newIntent = Intent(context, AlarmActivity::class.java)
                startActivityForResult(newIntent, 12)
            }
            R.id.text_btn -> {
                val testdi: tesdialog = tesdialog {
                    when(it) {
                        1 -> {
                            var newIntent = Intent(context, PhotoPaymentActivity::class.java)
                            startActivityForResult(newIntent, 9999)
                        }
                    }
                }
                testdi.show(childFragmentManager, testdi.tag)

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            12 -> {
                alarm()
            }
        }

    }


}