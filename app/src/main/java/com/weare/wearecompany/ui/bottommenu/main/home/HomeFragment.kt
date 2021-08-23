package com.weare.wearecompany.ui.bottommenu.main.home

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonObject
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.main.data.banner
import com.weare.wearecompany.data.main.data.bottom_banner
import com.weare.wearecompany.data.main.data.list
import com.weare.wearecompany.data.retrofit.IRetrofit
import com.weare.wearecompany.data.retrofit.RetrofitClient
import com.weare.wearecompany.data.retrofit.bottomnav.main.MainManager
import com.weare.wearecompany.databinding.FragmentHome0Binding
import com.weare.wearecompany.ui.base.BaseFragment
import com.weare.wearecompany.ui.bottommenu.main.MainViewPagerAdapter
import com.weare.wearecompany.ui.bottommenu.main.alarm.AlarmActivity
import com.weare.wearecompany.ui.container.ContainerActivity
import com.weare.wearecompany.ui.detail.DatailActivity
import com.weare.wearecompany.ui.detail.model.ModelActivity
import com.weare.wearecompany.ui.detail.photo.PhotoActivity
import com.weare.wearecompany.ui.detail.trip.TripActivity
import com.weare.wearecompany.ui.listcontainer.ListContainerActivity
import com.weare.wearecompany.utils.API
import com.weare.wearecompany.utils.Constants
import com.weare.wearecompany.utils.RESPONSE_STATUS
import java.util.ArrayList

class HomeFragment : BaseFragment<FragmentHome0Binding>(
    R.layout.fragment_home_0
), View.OnClickListener {

    private var bannerDataList = ArrayList<banner>()
    private var BottombannerList = ArrayList<bottom_banner>()
    private var homelistDataList = ArrayList<list>()


    private lateinit var bannarAdapter: MainViewPagerAdapter
    private lateinit var BottombannarAdapter: HomeBottomBannerRecyclerViewAdapter
    private lateinit var homelistAdapter: HomeLIstRecyeclerViewAdapter

    private lateinit var user_idx: String
    private val hotpickpostion = IntArray(2)
    private var infoCheckBox = false

    private var main_category = 0

    private var listType = 2
    private var alarm = -1
    val REQ_tall_PERMISSION = 1001


    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ContainerActivity) {
            mContext = context
        }
    }

    override fun onCreate() {

        setup()
        user_idx = MyApplication.prefs.getString("user_idx", "")
        if (user_idx != "") {
            datacall()
        }

    }

    fun setup() {
        mViewDataBinding.mianMenu1.setOnClickListener(this)
        mViewDataBinding.mianMenu2.setOnClickListener(this)
        mViewDataBinding.homeListToSee.setOnClickListener(this)
        mViewDataBinding.mainCategory0.setOnClickListener(this)
        mViewDataBinding.mainCategory1.setOnClickListener(this)
        mViewDataBinding.mainCategory2.setOnClickListener(this)
        mViewDataBinding.mainCategory3.setOnClickListener(this)
        mViewDataBinding.homeListTitleImage.setOnClickListener(this)
        mViewDataBinding.mainBottomBusinessInfo.setOnClickListener(this)
        mViewDataBinding.homeListTitleLayout.setOnClickListener(this)

        when (listType) {
            0 -> {
                mViewDataBinding.mainCategory3.setImageResource(R.drawable.main_studio_test_2)
                mViewDataBinding.homeListTitle.text = "스튜디오"
            } //스튜디오
            1 -> {
                mViewDataBinding.mainCategory2.setImageResource(R.drawable.main_photo_test_2)
                mViewDataBinding.homeListTitle.text = "포토그래퍼"
            }  //포토그래퍼
            2 -> {
                mViewDataBinding.mainCategory0.setImageResource(R.drawable.main_model_test_2)
                mViewDataBinding.homeListTitle.text = "모델"
            }  //모델
            3 -> {
                mViewDataBinding.mainCategory1.setImageResource(R.drawable.main_trip_test_2)
                mViewDataBinding.homeListTitle.text = "뷰티전문가"
            }  //뷰티전문가
        }

        mViewDataBinding.homeListRecyclerView.layoutManager =
            object : LinearLayoutManager(context) {
                override fun canScrollHorizontally(): Boolean {
                    return false
                }

                override fun canScrollVertically(): Boolean {
                    return false
                }
            }


    }

    fun datacall() {

        var os = Build.VERSION.RELEASE.toString()
        var model = Build.MODEL
        var app_version: PackageInfo = MyApplication.instance.packageManager.getPackageInfo(
            MyApplication.instance.packageName,
            0
        )

        val `object` = JsonObject()
        `object`.addProperty("user_idx", user_idx)
        `object`.addProperty("app_version", app_version.versionName)
        `object`.addProperty("device", model)
        `object`.addProperty("os", "A")
        `object`.addProperty("os_version", os)


        MainManager.instance.main(
            user_idx,
            app_version.versionName,
            model,
            "A",
            os,
            completion = { responseStatus, arraylist ->

                when (responseStatus) {
                    RESPONSE_STATUS.OKAY -> {

                        bannerDataList = arraylist[0].banner
                        bannarAdapter = MainViewPagerAdapter(bannerDataList)
                        mViewDataBinding.mainTopViewPager.adapter = bannarAdapter
                        mViewDataBinding.wormDotsIndicator.setViewPager(mViewDataBinding.mainTopViewPager)

                        BottombannerList = arraylist[0].bottom_banner
                        BottombannarAdapter = HomeBottomBannerRecyclerViewAdapter(BottombannerList)
                        mViewDataBinding.mainBottomViewPager.adapter = BottombannarAdapter

                        mViewDataBinding.mainBottomBusinessInfoText.text = arraylist[0].footer["company_info"]

                        MainManager.instance.mainlist(
                            listType,
                            complation = { responseStatus, arrayList ->
                                when (responseStatus) {
                                    RESPONSE_STATUS.OKAY -> {

                                        homelistAdapter =
                                            HomeLIstRecyeclerViewAdapter(arrayList, listType)
                                        mViewDataBinding.homeListRecyclerView.layoutManager =
                                            GridLayoutManager(context, 2)
                                        mViewDataBinding.homeListRecyclerView.adapter =
                                            homelistAdapter

                                        homelistAdapter.setItemClickListener(object :
                                            HomeLIstRecyeclerViewAdapter.OnItemClickListener {
                                            override fun onClick(
                                                v: View,
                                                position: Int,
                                                Item: list
                                            ) {
                                                var newIntent = Intent(context, ModelActivity::class.java)
                                                newIntent.putExtra("expert_idx", Item.idx)
                                                newIntent.putExtra("user_idx", MyApplication.prefs.getString("user_idx", ""))
                                                startActivity(newIntent)
                                            }

                                        })

                                    }
                                }
                            })

                        /* if (responseHotpick != null) {
                             hotpickDataList = responseHotpick
                             hotpickAdapter = MainHotpickRecyeclerViewAdapter(hotpickDataList)

                             mViewDataBinding.mainHotpickRecyclerView.layoutManager =
                                 LinearLayoutManager(
                                     context,
                                     LinearLayoutManager.VERTICAL, false
                                 )
                             mViewDataBinding.mainHotpickRecyclerView.adapter = hotpickAdapter

                             hotpickAdapter.setItemClickListener(object :
                                 MainHotpickRecyeclerViewAdapter.OnItemClickListener {
                                 override fun onClick(v: View, position: Int, hotpickItem: hotpick) {
                                     var newIntent = Intent(mContext, DatailActivity::class.java)
                                     newIntent.putExtra("idx", hotpickItem.target_idx)
                                     startActivity(newIntent)
                                 }

                             })
                         }*/
                    }
                    RESPONSE_STATUS.FAIL -> {
                        Toast.makeText(context, "api 호출 에러입니다.", Toast.LENGTH_SHORT).show()
                        Log.d(Constants.TAG, "api 호출 실패 ")
                    }

                }
            })
    }

    fun listsetUp() {
        MainManager.instance.mainlist(listType, complation = { responseStatus, arrayList ->
            when (responseStatus) {
                RESPONSE_STATUS.OKAY -> {

                    mViewDataBinding.homeListRecyclerView.removeAllViews()

                    homelistAdapter = HomeLIstRecyeclerViewAdapter(arrayList, listType)
                    mViewDataBinding.homeListRecyclerView.layoutManager =
                        GridLayoutManager(context, 2)
                    mViewDataBinding.homeListRecyclerView.adapter = homelistAdapter

                    homelistAdapter.setItemClickListener(object : HomeLIstRecyeclerViewAdapter.OnItemClickListener{
                        override fun onClick(v: View, position: Int, Item: list) {
                           when(listType) {
                               0 -> {
                                   var newIntent = Intent(context, DatailActivity::class.java)
                                   newIntent.putExtra("idx", Item.idx)
                                   startActivity(newIntent)
                               }
                               1 -> {
                                   var newIntent = Intent(context, PhotoActivity::class.java)
                                   newIntent.putExtra("expert_idx", Item.idx)
                                   newIntent.putExtra("user_idx", MyApplication.prefs.getString("user_idx", ""))
                                   startActivity(newIntent)
                               }
                               2 -> {
                                   var newIntent = Intent(context, ModelActivity::class.java)
                                   newIntent.putExtra("expert_idx", Item.idx)
                                   newIntent.putExtra("user_idx", MyApplication.prefs.getString("user_idx", ""))
                                   startActivity(newIntent)
                               }
                               3 -> {
                                   var newIntent = Intent(context, TripActivity::class.java)
                                   newIntent.putExtra("expert_idx", Item.idx)
                                   newIntent.putExtra("user_idx", MyApplication.prefs.getString("user_idx", ""))
                                   startActivity(newIntent)
                               }
                           }
                        }

                    })

                }
            }
        })
    }

    fun clickmove(num: Int) {
        var studioIntent = Intent(context, ListContainerActivity::class.java)
        studioIntent.putExtra("num", num)
        startActivity(studioIntent)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.home_list_title_layout -> {
                when (listType) {
                    0 -> clickmove(3)   //스튜디오
                    1 -> clickmove(2)   //뷰티
                    2 -> clickmove(0)   //모델
                    3 -> clickmove(1)  // 포토그래퍼
                }
            }
            R.id.home_list_to_see -> {
                when (listType) {
                    0 -> clickmove(3)
                    1 -> clickmove(2)
                    2 -> clickmove(0)
                    3 -> clickmove(1)
                }
            }
            R.id.home_list_title_image -> {
                when (listType) {
                    0 -> clickmove(3)
                    1 -> clickmove(2)
                    2 -> clickmove(0)
                    3 -> clickmove(1)
                }
            }
            R.id.main_category_0 -> {
                when (main_category) {
                    1 -> {
                        mViewDataBinding.mainCategory1.setImageResource(R.drawable.main_trip_test_0)
                    }
                    2 -> {
                        mViewDataBinding.mainCategory2.setImageResource(R.drawable.main_photo_test_0)
                    }
                    3 -> {
                        mViewDataBinding.mainCategory3.setImageResource(R.drawable.main_studio_test_0)
                    }
                }
                main_category = 0
                mViewDataBinding.mainCategory0.setImageResource(R.drawable.main_model_test_2)
                listType = 2
                mViewDataBinding.homeListTitle.text = "모델"
                listsetUp()
            }
            R.id.main_category_1 -> {
                when (main_category) {
                    0 -> {
                        mViewDataBinding.mainCategory0.setImageResource(R.drawable.main_model_test_0)
                    }
                    2 -> {
                        mViewDataBinding.mainCategory2.setImageResource(R.drawable.main_photo_test_0)
                    }
                    3 -> {
                        mViewDataBinding.mainCategory3.setImageResource(R.drawable.main_studio_test_0)
                    }
                }
                main_category = 1
                mViewDataBinding.mainCategory1.setImageResource(R.drawable.main_trip_test_2)
                listType = 3
                mViewDataBinding.homeListTitle.text = "뷰티전문가"
                listsetUp()
            }
            R.id.main_category_2 -> {
                when (main_category) {
                    0 -> {
                        mViewDataBinding.mainCategory0.setImageResource(R.drawable.main_model_test_0)
                    }
                    1 -> {
                        mViewDataBinding.mainCategory1.setImageResource(R.drawable.main_trip_test_0)
                    }
                    3 -> {
                        mViewDataBinding.mainCategory3.setImageResource(R.drawable.main_studio_test_0)
                    }
                }
                main_category = 2
                mViewDataBinding.mainCategory2.setImageResource(R.drawable.main_photo_test_2)
                listType = 1
                mViewDataBinding.homeListTitle.text = "포토그래퍼"

                listsetUp()
            }
            R.id.main_category_3 -> {
                when (main_category) {
                    0 -> {
                        mViewDataBinding.mainCategory0.setImageResource(R.drawable.main_model_test_0)
                    }
                    1 -> {
                        mViewDataBinding.mainCategory1.setImageResource(R.drawable.main_trip_test_0)
                    }
                    2 -> {
                        mViewDataBinding.mainCategory2.setImageResource(R.drawable.main_photo_test_0)
                    }
                }
                main_category = 3
                mViewDataBinding.mainCategory3.setImageResource(R.drawable.main_studio_test_2)
                listType = 0
                mViewDataBinding.homeListTitle.text = "스튜디오"
                listsetUp()
            }
            R.id.mian_menu_1 -> {
                var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wearecompany.co.kr"))
                startActivity(intent)
            }
            R.id.mian_menu_2 -> {
                var permission = ContextCompat.checkSelfPermission(
                    mContext,
                    Manifest.permission.CALL_PHONE
                )
                if (permission != PackageManager.PERMISSION_GRANTED) { // 권한 없어서 요청
                    ActivityCompat.requestPermissions(
                        context as Activity,
                        arrayOf(Manifest.permission.CALL_PHONE),
                        REQ_tall_PERMISSION
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

            R.id.main_bottom_business_info -> {
                if (!infoCheckBox)
                    mViewDataBinding.mainBottomBusinessInfo.animate().apply {
                        alpha(1.0f)
                        mViewDataBinding.mainBottomBusinessInfoText.animate().alpha(1.0f)
                            .setDuration(1500).withEndAction {
                                mViewDataBinding.mainBottomBusinessInfoText.visibility =
                                    View.VISIBLE
                                infoCheckBox = true

                            }
                    } else if (infoCheckBox) {
                    mViewDataBinding.mainBottomBusinessInfoText.animate().alpha(0.0f)
                        .setDuration(1500).withEndAction {
                            mViewDataBinding.mainBottomBusinessInfoText.visibility = View.GONE
                            infoCheckBox = false
                        }

                }
            }

            R.id.notification -> {
                var newIntent = Intent(context, AlarmActivity::class.java)
                startActivityForResult(newIntent, 12)
            }
        }
    }

}