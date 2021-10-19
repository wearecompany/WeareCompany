package com.weare.wearecompany.ui.bottommenu.main.home

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
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
import com.weare.wearecompany.ui.bottommenu.estimate.receive.payment.PhotoPaymentActivity
import com.weare.wearecompany.ui.bottommenu.main.MainViewPagerAdapter
import com.weare.wearecompany.ui.bottommenu.main.alarm.AlarmActivity
import com.weare.wearecompany.ui.bottommenu.main.home.oneclick.OneClickActivity
import com.weare.wearecompany.ui.bottommenu.main.weekly.tesdialog
import com.weare.wearecompany.ui.container.ContainerActivity
import com.weare.wearecompany.ui.detail.DatailActivity
import com.weare.wearecompany.ui.detail.model.ModelActivity
import com.weare.wearecompany.ui.detail.photo.PhotoActivity
import com.weare.wearecompany.ui.detail.trip.TripActivity
import com.weare.wearecompany.ui.listcontainer.ListContainerActivity
import com.weare.wearecompany.utils.API
import com.weare.wearecompany.utils.Constants
import com.weare.wearecompany.utils.RESPONSE_STATUS
import kotlinx.android.synthetic.main.fragment_home_0.view.*
import java.util.ArrayList

class HomeFragment : Fragment(
), View.OnClickListener {

    private var bannerDataList = ArrayList<banner>()
    private var BottombannerList = ArrayList<bottom_banner>()


    private lateinit var bannarAdapter: MainViewPagerAdapter
    private lateinit var BottombannarAdapter: HomeBottomBannerRecyclerViewAdapter
    private lateinit var homelistAdapter: HomeLIstRecyeclerViewAdapter

    lateinit var viewe: View

    private lateinit var user_idx: String
    private val hotpickpostion = IntArray(2)
    private var infoCheckBox = false

    private var main_category = 0

    private var listType = 2
    private var alarm = -1
    val REQ_CAMERA_PERMISSION = 1001


    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ContainerActivity) {
            mContext = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewe = inflater!!.inflate(R.layout.fragment_home_0,container,false)
        user_idx = MyApplication.prefs.getString("user_idx", "")
        setup()
            datacall()
        return viewe
    }

    fun setup() {
        viewe.home_list_to_see.setOnClickListener(this)
        viewe.main_category_0.setOnClickListener(this)
        viewe.main_category_1.setOnClickListener(this)
        viewe.main_category_2.setOnClickListener(this)
        viewe.main_category_3.setOnClickListener(this)
        viewe.oneclick_btn.setOnClickListener(this)
        viewe.wepickstudio_btn.setOnClickListener(this)
        viewe.move_wepic_expert.setOnClickListener(this)
        viewe.one_click_bottom_layout_1_frame.setOnClickListener(this)
        viewe.one_click_bottom_layout_2_frame.setOnClickListener(this)
        viewe.one_click_bottom_layout_3_frame.setOnClickListener(this)
        viewe.one_click_bottom_layout_4_frame.setOnClickListener(this)
        viewe.home_list_title_image.setOnClickListener(this)
        viewe.main_bottom_business_info.setOnClickListener(this)
        viewe.home_list_title_layout.setOnClickListener(this)

        when (listType) {
            0 -> {
                viewe.main_category_3.setImageResource(R.drawable.main_studio_test_2)
                viewe.home_list_title.text = "스튜디오"
            } //스튜디오
            1 -> {
                viewe.main_category_2.setImageResource(R.drawable.main_photo_test_2)
                viewe.home_list_title.text = "포토그래퍼"
            }  //포토그래퍼
            2 -> {
                viewe.main_category_0.setImageResource(R.drawable.main_model_test_2)
                viewe.home_list_title.text = "모델"
            }  //모델
            3 -> {
                viewe.main_category_1.setImageResource(R.drawable.main_trip_test_2)
                viewe.home_list_title.text = "뷰티전문가"
            }  //뷰티전문가
        }

        viewe.home_list_RecyclerView.layoutManager =
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
                        viewe.main_Top_viewPager.adapter = bannarAdapter
                        viewe.worm_dots_indicator.setViewPager(viewe.main_Top_viewPager)

                        BottombannerList = arraylist[0].bottom_banner
                        BottombannarAdapter = HomeBottomBannerRecyclerViewAdapter(BottombannerList)
                        viewe.main_bottom_viewPager.adapter = BottombannarAdapter

                        viewe.main_bottom_business_info_text.text = arraylist[0].footer["company_info"]

                        MainManager.instance.mainlist(
                            listType,
                            complation = { responseStatus, arrayList ->
                                when (responseStatus) {
                                    RESPONSE_STATUS.OKAY -> {

                                        homelistAdapter =
                                            HomeLIstRecyeclerViewAdapter(arrayList, listType)
                                        viewe.home_list_RecyclerView.layoutManager =
                                            GridLayoutManager(context, 2)
                                        viewe.home_list_RecyclerView.adapter =
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

                    viewe.home_list_RecyclerView.removeAllViews()

                    homelistAdapter = HomeLIstRecyeclerViewAdapter(arrayList, listType)
                    viewe.home_list_RecyclerView.layoutManager =
                        GridLayoutManager(context, 2)
                    viewe.home_list_RecyclerView.adapter = homelistAdapter

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

            R.id.wepickstudio_btn -> {
                val newIntent = Intent(Intent.ACTION_VIEW,Uri.parse("https://weare2020.wixsite.com/my-site"))
                context?.startActivity(newIntent)
            }

            R.id.oneclick_btn -> {
                if (MyApplication.prefs.getString("user_idx", "") != "") {
                    MainManager.instance.certcheck(MyApplication.prefs.getString("user_idx", ""),complation = { responseStatus, check ->
                        when(responseStatus) {
                            RESPONSE_STATUS.OKAY -> {
                                when(check) {
                                    0 -> {
                                        val testdi: tesdialog = tesdialog {
                                            when(it) {
                                                0 -> {

                                                }
                                                1 -> {
                                                    var newIntent = Intent(context, PhotoPaymentActivity::class.java)
                                                    startActivityForResult(newIntent, 9999)
                                                }
                                            }
                                        }
                                        testdi.show(childFragmentManager, testdi.tag)
                                    }
                                    1 -> {
                                        val newIntent = Intent(context, OneClickActivity::class.java)
                                        startActivityForResult(newIntent, 3000)
                                    }
                                }
                            }
                        }
                    })

                    /*val requestFragment: UploadStepOnFragment = UploadStepOnFragment {
                        when (it) {
                            0 -> {

                            }
                            1 -> {
                                val one_requestFragment: UploadStepTwoFragment = UploadStepTwoFragment {
                                    when (it) {
                                        0 -> {
                                            clickmove(0)
                                        }
                                        1 -> {

                                            clickmove(1)
                                        }
                                        2 -> {
                                            clickmove(2)
                                        }
                                        3 -> {
                                            clickmove(3)
                                        }
                                    }
                                }
                                one_requestFragment.show(supportFragmentManager, one_requestFragment.tag)
                            }
                        }
                    }
                    requestFragment.show(supportFragmentManager, requestFragment.tag)*/
                } else {
                    Toast.makeText(context,"로그인후 이용가능합니다.",Toast.LENGTH_SHORT).show()
                }
            }
            R.id.main_category_0 -> {
                when (main_category) {
                    1 -> {
                        viewe.main_category_1.setImageResource(R.drawable.main_trip_test_0)
                    }
                    2 -> {
                        viewe.main_category_2.setImageResource(R.drawable.main_photo_test_0)
                    }
                    3 -> {
                        viewe.main_category_3.setImageResource(R.drawable.main_studio_test_0)
                    }
                }
                main_category = 0
                viewe.main_category_0.setImageResource(R.drawable.main_model_test_2)
                listType = 2
                viewe.home_list_title.text = "모델"
                listsetUp()
            }
            R.id.main_category_1 -> {
                when (main_category) {
                    0 -> {
                        viewe.main_category_0.setImageResource(R.drawable.main_model_test_0)
                    }
                    2 -> {
                        viewe.main_category_2.setImageResource(R.drawable.main_photo_test_0)
                    }
                    3 -> {
                        viewe.main_category_3.setImageResource(R.drawable.main_studio_test_0)
                    }
                }
                main_category = 1
                viewe.main_category_1.setImageResource(R.drawable.main_trip_test_2)
                listType = 3
                viewe.home_list_title.text = "뷰티전문가"
                listsetUp()
            }
            R.id.main_category_2 -> {
                when (main_category) {
                    0 -> {
                        viewe.main_category_0.setImageResource(R.drawable.main_model_test_0)
                    }
                    1 -> {
                        viewe.main_category_1.setImageResource(R.drawable.main_trip_test_0)
                    }
                    3 -> {
                        viewe.main_category_3.setImageResource(R.drawable.main_studio_test_0)
                    }
                }
                main_category = 2
                viewe.main_category_2.setImageResource(R.drawable.main_photo_test_2)
                listType = 1
                viewe.home_list_title.text = "포토그래퍼"

                listsetUp()
            }
            R.id.main_category_3 -> {
                when (main_category) {
                    0 -> {
                        viewe.main_category_0.setImageResource(R.drawable.main_model_test_0)
                    }
                    1 -> {
                        viewe.main_category_1.setImageResource(R.drawable.main_trip_test_0)
                    }
                    2 -> {
                        viewe.main_category_2.setImageResource(R.drawable.main_photo_test_0)
                    }
                }
                main_category = 3
                viewe.main_category_3.setImageResource(R.drawable.main_studio_test_2)
                listType = 0
                viewe.home_list_title.text = "스튜디오"
                listsetUp()
            }
           /* R.id.mian_menu_1 -> {
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
            }*/

            R.id.move_wepic_expert -> {
                val insta = context?.packageManager?.getLaunchIntentForPackage("com.weare.expertwearecompany")
                if (insta == null) {
                    val newintent =Intent(Intent.ACTION_VIEW)
                    newintent.setData(Uri.parse("market://details?id="+ "com.weare.expertwearecompany"))
                    context?.startActivity(newintent)
                } else {
                    context?.startActivity(insta)

                }
            }

            R.id.one_click_bottom_layout_1_frame -> {
                val newIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.xn--9q5b43x.kr/"))
                context?.startActivity(newIntent)
            }
            R.id.one_click_bottom_layout_2_frame -> {
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
            R.id.one_click_bottom_layout_3_frame -> {
                val youtube_Intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCNplNjDyYkICiptGQhGhqsg"))
                youtube_Intent.setPackage("com.google.android.youtube")
                val insta = context?.packageManager?.getLaunchIntentForPackage("com.google.android.youtube")
                if (insta == null) {
                    val newintent =Intent(Intent.ACTION_VIEW)
                    newintent.setData(Uri.parse("market://details?id="+ "com.google.android.youtube"))
                    context?.startActivity(newintent)
                } else {
                    context?.startActivity(youtube_Intent)

                }
            }
            R.id.one_click_bottom_layout_4_frame -> {
                val Instagram_Intent = Intent(Intent.ACTION_VIEW,Uri.parse("https://instagram.com/weare.company?utm_medium=copy_link"))
                Instagram_Intent.setPackage("com.instagram.android")
                context?.startActivity(Instagram_Intent)
            }

            R.id.main_bottom_business_info -> {
                if (!infoCheckBox)
                    viewe.main_bottom_business_info.animate().apply {
                        alpha(1.0f)
                        viewe.main_bottom_business_info_text.animate().alpha(1.0f)
                            .setDuration(1500).withEndAction {
                                viewe.main_bottom_business_info_text.visibility =
                                    View.VISIBLE
                                infoCheckBox = true

                            }
                    } else if (infoCheckBox) {
                    viewe.main_bottom_business_info_text.animate().alpha(0.0f)
                        .setDuration(1500).withEndAction {
                            viewe.main_bottom_business_info_text.visibility = View.GONE
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