package com.weare.wearecompany.ui.listcontainer.studio

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.JsonArray
import com.kakao.sdk.navi.NaviClient
import com.kakao.sdk.navi.model.CoordType
import com.kakao.sdk.navi.model.Location
import com.kakao.sdk.navi.model.NaviOption
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.List.clip
import com.weare.wearecompany.data.main.data.newStudio
import com.weare.wearecompany.data.main.data.studio
import com.weare.wearecompany.data.retrofit.List.studio.studioListManager
import com.weare.wearecompany.databinding.ActivityStudioMapSearchBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.ui.detail.DatailActivity
import com.weare.wearecompany.ui.detail.studio.DatailNaviDialog
import com.weare.wearecompany.utils.Constants
import com.weare.wearecompany.utils.RESPONSE_STATUS
import kotlinx.android.synthetic.main.activity_listcontainer.view.*
import kotlinx.android.synthetic.main.fragment_list_stdio.view.*
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import java.net.URLEncoder
import java.util.*
import kotlin.collections.ArrayList

class StudioMapSearchActivity : BaseActivity<ActivityStudioMapSearchBinding>(
    R.layout.activity_studio_map_search
), OnMapReadyCallback, Overlay.OnClickListener, View.OnClickListener {

    private var locationList = ArrayList<Int>()
    private var categoryList = ArrayList<Int>()
    private var sort_check = 0
    lateinit var behavior: BottomSheetBehavior<ViewPager>

    private lateinit var locationSource: FusedLocationSource

    var studio_array = JsonArray()
    var location_array = JsonArray()
    val datetime_array = JsonArray()
    var start_array = Vector<Marker>()
    var poast_marker: Marker = Marker()

    var check_postion = 0

    var map_check = false
    var clipmoney = ""
    private var fm = supportFragmentManager
    private lateinit var mapFragment: MapFragment
    private var clipList = ArrayList<clip>()

    var postArrlist = Array<DoubleArray>(1) { DoubleArray(2) }
    private var min_money = ""
    private var max_money = ""
    private var nick_name = ""
    private lateinit var map: NaverMap

    private lateinit var dataAdapter: StudioMapViewPager
    private lateinit var datalist: ArrayList<studio>
    private lateinit var searchlist: ArrayList<studio>
    private lateinit var clipAdapter: StudioMapClipRecyerViewAdapter

    private lateinit var loctionlist: ArrayList<LatLng>

    private val PERMISSIONS_REQUEST_CODE = 100
    private var REQUIRED_PERMISSIONS = arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION)

    var pageLocation: LatLng? = null

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }


    override fun onCreate() {

        setUp()

    }


    private fun setUp() {

        mViewDataBinding.mapBackBtn.setOnClickListener(this)
        mViewDataBinding.mapFilterBtn.setOnClickListener(this)
        mViewDataBinding.mapNavi.setOnClickListener(this)


        locationSource =
            FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

        behavior = BottomSheetBehavior.from(mViewDataBinding.bottomSheet)
        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        behavior.isHideable = true
        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

        })

        mViewDataBinding.bottomSheet.addOnPageChangeListener(object :
            ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {

                check_postion = position
                poast_marker.icon = OverlayImage.fromResource(R.drawable.map_mark_off)
                start_array[position].icon = OverlayImage.fromResource(R.drawable.map_mark_on)
                poast_marker = start_array[position]

                if (postArrlist == null) {
                    postArrlist[0][0] = datalist[position].lat!!.toDouble()
                    postArrlist[0][1] = datalist[position].long!!.toDouble()
                } else {
                    postArrlist[0][0] = datalist[position].lat!!.toDouble()
                    postArrlist[0][1] = datalist[position].long!!.toDouble()

                }
                if (map_check) {
                    val cameraUpdate = CameraUpdate.scrollTo(
                        LatLng(
                            searchlist[position].lat!!.toDouble(),
                            searchlist[position].long!!.toDouble()
                        )
                    )
                        .animate(CameraAnimation.Fly, 2000)
                    map.moveCamera(cameraUpdate)
                } else {
                    val cameraUpdate = CameraUpdate.scrollTo(
                        LatLng(
                            datalist[position].lat!!.toDouble(),
                            datalist[position].long!!.toDouble()
                        )
                    )
                        .animate(CameraAnimation.Fly, 2000)
                    map.moveCamera(cameraUpdate)
                }



            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })



        studioListManager.instant.listdata(
            studio_array,
            location_array,
            datetime_array,
            min_money,
            max_money,
            nick_name,
            1,
            1,
            completion = { responseStatus, responsenewlist, responsestudiokList ->

                when (responseStatus) {
                    RESPONSE_STATUS.OKAY -> {

                        datalist = responsestudiokList


                        mapFragment = fm.findFragmentById(R.id.map_view) as MapFragment?
                            ?: MapFragment.newInstance().also {
                                fm.beginTransaction().add(R.id.map_view, it).commit()
                            }

                        mapFragment.getMapAsync(this)

                    }
                }
            })


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions,
                grantResults
            )
        ) {
            if (!locationSource.isActivated) { // 권한 거부됨
                map.locationTrackingMode = LocationTrackingMode.None
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onMapReady(p0: NaverMap) {

        map = p0
        val i1 = 1
        if (map_check) {
            for (i in start_array) {
                i.map = null
            }
            start_array = Vector<Marker>()
            for (i in searchlist) {
                val marker = Marker()
                marker.position = LatLng(i.lat!!.toDouble(), i.long!!.toDouble())
                marker.icon = OverlayImage.fromResource(R.drawable.map_mark_off)
                marker.isFlat = true
                marker.onClickListener = this
                marker.map = map
                start_array.add(marker)
            }
            postArrlist[0][0] = searchlist[0].lat!!.toDouble()
            postArrlist[0][1] = searchlist[0].long!!.toDouble()
            poast_marker = start_array[0]
            val cameraUpdate = CameraUpdate.scrollTo(
                LatLng(
                    searchlist[0].lat!!.toDouble(),
                    searchlist[0].long!!.toDouble()
                )
            )
                .animate(CameraAnimation.Fly, 2000)
            map.moveCamera(cameraUpdate)
        } else {
            for (i in start_array) {
                i.map = null
            }
            start_array = Vector<Marker>()
            for (i in datalist) {
                val marker = Marker()
                marker.position = LatLng(i.lat!!.toDouble(), i.long!!.toDouble())
                marker.icon = OverlayImage.fromResource(R.drawable.map_mark_off)
                marker.isFlat = true
                marker.onClickListener = this
                marker.map = map
                start_array.add(marker)
            }
            poast_marker = start_array[0]
            postArrlist[0][0] = datalist[0].lat!!.toDouble()
            postArrlist[0][1] = datalist[0].long!!.toDouble()
            val cameraUpdate = CameraUpdate.scrollTo(
                LatLng(
                    datalist[0].lat!!.toDouble(),
                    datalist[0].long!!.toDouble()
                )
            )
                .animate(CameraAnimation.Fly, 2000)
            map.moveCamera(cameraUpdate)
        }

        map.locationSource = locationSource
        val uiSettings = p0.uiSettings
        uiSettings.logoGravity = 0
        uiSettings.setLogoMargin(60, 20, 0, 0)
        uiSettings.isCompassEnabled = false
        //uiSettings.isLocationButtonEnabled = false
        mViewDataBinding.locationBtn.map = map
        mViewDataBinding.compassViewBtn.map = map
        //mViewDataBinding.naverLogoBtn.setMap(map)
        //map.isIndoorEnabled = true
        if (map_check) {
            dataAdapter = StudioMapViewPager(searchlist)
        } else {
            dataAdapter = StudioMapViewPager(datalist)
        }

        mViewDataBinding.bottomSheet.clipToPadding = false

        val dpValue = 30
        val d = resources.displayMetrics.density
        val margin = (dpValue * d).toInt()
        mViewDataBinding.bottomSheet.setPadding(margin, 0, margin, 0)
        mViewDataBinding.bottomSheet.pageMargin = margin / 2
        mViewDataBinding.bottomSheet.adapter = dataAdapter
    }

    fun datacall(
        studioArray: JsonArray,
        locationArray: JsonArray,
        timeArray: JsonArray,
        min: String,
        max: String,
        nickname: String,
        sort: Int,
        page: Int
    ) {
        clipList = ArrayList()

        if (studioArray.size() != 0) {
            for (i in studioArray) {
                val clipitem = clip(
                    main_type = 0,
                    sub_type = i.asInt,
                    name = ""
                )
                clipList.add(clipitem)
            }
        }

        if (locationArray.size() != 0) {
            for (i in locationArray) {
                val clipitem = clip(
                    main_type = 1,
                    sub_type = i.asInt,
                    name = ""
                )
                clipList.add(clipitem)
            }
        }

        if (min != "" && max != "") {
            clipmoney = min + " ~ " + max
            val moneyitem = clip(
                main_type = 2,
                sub_type = -1,
                name = clipmoney
            )
            clipList.add(moneyitem)
        } else if (min == "" && max != "") {
            clipmoney = "0" + " ~ " + max
            val moneyitem = clip(
                main_type = 2,
                sub_type = -1,
                name = clipmoney
            )
            clipList.add(moneyitem)
        } else if (min != "" && max == "") {
            clipmoney = min + " ~ " + "500,000 이상"
            val moneyitem = clip(
                main_type = 2,
                sub_type = -1,
                name = clipmoney
            )
            clipList.add(moneyitem)
        }

        if (sort != 0) {
            val sortitem = clip(
                main_type = 3,
                sub_type = sort,
                name = ""
            )
            clipList.add(sortitem)
        }
        studioListManager.instant.listdata(
            studioArray,
            locationArray,
            timeArray,
            min,
            max,
            nickname,
            sort,
            page,
            completion = { responseStatus, responsenewlist, responsestudiokList ->

                when (responseStatus) {
                    RESPONSE_STATUS.OKAY -> {

                        searchlist = responsestudiokList
                        if (clipList.size != 0) {
                            mViewDataBinding.mapSearchResult.visibility = View.VISIBLE
                            mViewDataBinding.mapSearchNum.text = searchlist.size.toString()
                            mViewDataBinding.mapFilterImage.setImageResource(R.drawable.map_filter_on)

                        } else {
                            mViewDataBinding.mapSearchResult.visibility = View.INVISIBLE
                            mViewDataBinding.mapFilterImage.setImageResource(R.drawable.map_filter)
                        }


                        if (responsestudiokList.size != 0) {

                            locationSource =
                                FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)


                            behavior = BottomSheetBehavior.from(mViewDataBinding.bottomSheet)
                            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                            behavior.addBottomSheetCallback(object :
                                BottomSheetBehavior.BottomSheetCallback() {
                                override fun onStateChanged(bottomSheet: View, newState: Int) {
                                    if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                                        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                                    }
                                }

                                override fun onSlide(bottomSheet: View, slideOffset: Float) {

                                }

                            })

                            mViewDataBinding.bottomSheet.addOnPageChangeListener(object :
                                ViewPager.OnPageChangeListener {
                                override fun onPageScrolled(
                                    position: Int,
                                    positionOffset: Float,
                                    positionOffsetPixels: Int
                                ) {

                                }

                                override fun onPageSelected(position: Int) {

                                    check_postion = position
                                    val marker = Marker()
                                    marker.position = LatLng(
                                        searchlist[position].lat!!.toDouble(),
                                        searchlist[position].long!!.toDouble()
                                    )
                                    marker.icon = OverlayImage.fromResource(R.drawable.map_mark_on)
                                    marker.isFlat = true
                                    marker.map = map
                                    if (postArrlist == null) {
                                        postArrlist[0][0] = searchlist[position].lat!!.toDouble()
                                        postArrlist[0][1] = searchlist[position].long!!.toDouble()
                                    } else {
                                        val marker = Marker()
                                        marker.position =
                                            LatLng(postArrlist[0][0], postArrlist[0][1])
                                        marker.icon =
                                            OverlayImage.fromResource(R.drawable.map_mark_off)
                                        marker.isFlat = true
                                        marker.map = map
                                        start_array[position].map = map
                                        postArrlist[0][0] = searchlist[position].lat!!.toDouble()
                                        postArrlist[0][1] = searchlist[position].long!!.toDouble()

                                    }
                                    val cameraUpdate = CameraUpdate.scrollTo(
                                        LatLng(
                                            searchlist[position].lat!!.toDouble(),
                                            searchlist[position].long!!.toDouble()
                                        )
                                    )
                                        .animate(CameraAnimation.Fly, 2000)
                                    map.moveCamera(cameraUpdate)


                                }

                                override fun onPageScrollStateChanged(state: Int) {

                                }

                            })

                            if (clipList.size != 0) {
                                mViewDataBinding.mapClipRecyclerView.visibility = View.VISIBLE
                                clipAdapter = StudioMapClipRecyerViewAdapter(clipList)
                                mViewDataBinding.mapClipRecyclerView.layoutManager =
                                    LinearLayoutManager(
                                        MyApplication.instance,
                                        LinearLayoutManager.HORIZONTAL, false
                                    )
                                mViewDataBinding.mapClipRecyclerView.adapter = clipAdapter

                            } else {
                                mViewDataBinding.mapClipRecyclerView.visibility = View.GONE
                            }
                        } else {
                            Toast.makeText(this, "조건에 맞는 스튜디오가 없습니다.", Toast.LENGTH_SHORT).show()
                        }

                        map_check = true
                        mapFragment?.getMapAsync(this)


                    }
                }
            })
    }

    override fun onClick(p0: Overlay): Boolean {
        if (p0 is Marker) {

            poast_marker.icon = OverlayImage.fromResource(R.drawable.map_mark_off)
            p0.icon = OverlayImage.fromResource(R.drawable.map_mark_on)
            poast_marker = p0
            val i1 = 1
            if (map_check) {
                for (i in i1..searchlist.size) {
                    if (p0.position.latitude == searchlist[i - 1].lat!!.toDouble()) {
                        mViewDataBinding.bottomSheet.setCurrentItem(i - 1, true)
                    }
                }
                val cameraUpdate = CameraUpdate.scrollTo(
                    LatLng(
                        p0.position.latitude!!.toDouble(),
                        p0.position.longitude!!.toDouble()
                    )
                )
                    .animate(CameraAnimation.Fly, 2000)
                map.moveCamera(cameraUpdate)
            } else {
                for (i in i1..datalist.size) {
                    if (p0.position.latitude == datalist[i - 1].lat!!.toDouble()) {
                        mViewDataBinding.bottomSheet.setCurrentItem(i - 1, true)
                    }
                }
                val cameraUpdate = CameraUpdate.scrollTo(
                    LatLng(
                        p0.position.latitude!!.toDouble(),
                        p0.position.longitude!!.toDouble()
                )
                )
                    .animate(CameraAnimation.Fly, 2000)
                map.moveCamera(cameraUpdate)

            }

            /*val marker = Marker()
            marker.position = LatLng((postArrlist[0][0]), postArrlist[0][1])
            marker.icon = OverlayImage.fromResource(R.drawable.map_mark_off)
            marker.isFlat = true
            marker.map = map
            postArrlist[0][0] = p0.position.latitude
            postArrlist[0][1] = p0.position.longitude
            val i1 = 1
            if (map_check) {
                for (i in i1..searchlist.size) {
                    if (p0.position.latitude == searchlist[i - 1].lat!!.toDouble()) {
                        check_postion = i - 1
                        mViewDataBinding.bottomSheet.setCurrentItem(i - 1, true)
                        val marker = Marker()
                        marker.position = LatLng(p0.position.latitude, p0.position.longitude)
                        marker.icon = OverlayImage.fromResource(R.drawable.map_mark_on)
                        marker.isFlat = true
                        marker.map = map
                    }
                }
            } else {
                for (i in i1..datalist.size) {
                    if (p0.position.latitude == datalist[i - 1].lat!!.toDouble()) {
                        check_postion = i - 1
                        mViewDataBinding.bottomSheet.setCurrentItem(i - 1, true)
                        val marker = Marker()
                        marker.position = LatLng(p0.position.latitude, p0.position.longitude)
                        marker.icon = OverlayImage.fromResource(R.drawable.map_mark_on)
                        marker.isFlat = true
                        marker.map = map
                    }
                }
            }*/

            return true
        }
        return false
    }

    private fun ClipSetUp() {

        clipList = ArrayList()

        if (studio_array.size() != 0) {
            for (i in studio_array) {
                val clipitem = clip(
                    main_type = 0,
                    sub_type = i.asInt,
                    name = ""
                )
                clipList.add(clipitem)
            }
        }

        if (location_array.size() != 0) {
            for (i in location_array) {
                val clipitem = clip(
                    main_type = 1,
                    sub_type = i.asInt,
                    name = ""
                )
                clipList.add(clipitem)
            }
        }

        if (min_money != "" && max_money != "") {
            clipmoney = min_money + " ~ " + max_money
            val moneyitem = clip(
                main_type = 2,
                sub_type = -1,
                name = clipmoney
            )
            clipList.add(moneyitem)
        } else if (min_money == "" && max_money != "") {
            clipmoney = "0" + " ~ " + max_money
            val moneyitem = clip(
                main_type = 2,
                sub_type = -1,
                name = clipmoney
            )
            clipList.add(moneyitem)
        } else if (min_money != "" && max_money == "") {
            clipmoney = min_money + " ~ " + "500,000 이상"
            val moneyitem = clip(
                main_type = 2,
                sub_type = -1,
                name = clipmoney
            )
            clipList.add(moneyitem)
        }

        if (sort_check != 0) {
            val sortitem = clip(
                main_type = 3,
                sub_type = sort_check,
                name = ""
            )
            clipList.add(sortitem)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.map_back_btn -> {
                finish()
            }

            R.id.map_filter_btn -> {
                val searchDialog: StudioMapSearchAllDialog = StudioMapSearchAllDialog(
                    0,
                    categoryList,
                    locationList,
                    min_money,
                    max_money,
                    sort_check
                ) { ca: ArrayList<Int>, location: ArrayList<Int>, min: String, max: String, sort: Int, search: ArrayList<studio> ->

                    studio_array = JsonArray()
                    categoryList = ArrayList()
                    location_array = JsonArray()
                    locationList = ArrayList()

                    for (i in ca) {
                        studio_array.add(i)
                        categoryList.add(i)
                    }

                    for (i in location) {
                        location_array.add(i)
                        locationList.add(i)
                    }
                    min_money = min
                    max_money = max
                    sort_check = sort


                    ClipSetUp()

                    searchlist = search

                    if (clipList.size != 0) {
                        mViewDataBinding.mapSearchResult.visibility = View.VISIBLE
                        mViewDataBinding.mapSearchNum.text = searchlist.size.toString()
                        mViewDataBinding.mapFilterImage.setImageResource(R.drawable.map_filter_on)
                    } else {
                        map_check = false
                        mViewDataBinding.mapSearchResult.visibility = View.INVISIBLE
                        mViewDataBinding.mapFilterImage.setImageResource(R.drawable.map_filter)
                    }

                    if (search.size != 0) {

                        locationSource =
                            FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

                        if (clipList.size != 0) {
                            mViewDataBinding.mapClipRecyclerView.visibility = View.VISIBLE
                            clipAdapter = StudioMapClipRecyerViewAdapter(clipList)
                            mViewDataBinding.mapClipRecyclerView.layoutManager =
                                LinearLayoutManager(
                                    MyApplication.instance,
                                    LinearLayoutManager.HORIZONTAL, false
                                )
                            mViewDataBinding.mapClipRecyclerView.adapter = clipAdapter

                        } else {
                            mViewDataBinding.mapClipRecyclerView.visibility = View.GONE
                        }
                    } else {
                        Toast.makeText(this, "조건에 맞는 스튜디오가 없습니다.", Toast.LENGTH_SHORT).show()
                    }

                    map_check = true
                    mapFragment?.getMapAsync(this)

                }
                searchDialog.show(supportFragmentManager, searchDialog.tag)
            }

            R.id.map_navi -> {
                val dialog: DatailNaviDialog = DatailNaviDialog {
                    when (it) {
                        0 -> {
                            if (map_check) {
                                if (NaviClient.instance.isKakaoNaviInstalled(this)) {
                                    startActivity(
                                        NaviClient.instance.shareDestinationIntent(
                                            Location(
                                                "위치 테스트",
                                                searchlist[check_postion].long!!,
                                                searchlist[check_postion].lat!!,
                                            ),
                                            NaviOption(coordType = CoordType.WGS84)
                                        )
                                    )
                                } else {
                                    Toast.makeText(this, "카카오내비 앱이 필요합니다.", Toast.LENGTH_SHORT)
                                        .show()
                                    val newintent = Intent(Intent.ACTION_VIEW)
                                    newintent.setData(Uri.parse("market://details?id=com.locnall.KimGiSa"))
                                    startActivity(newintent)
                                }
                            } else {
                                if (NaviClient.instance.isKakaoNaviInstalled(this)) {
                                    startActivity(
                                        NaviClient.instance.shareDestinationIntent(
                                            Location(
                                                "위치 테스트",
                                                datalist[check_postion].long!!,
                                                datalist[check_postion].lat!!,
                                            ),
                                            NaviOption(coordType = CoordType.WGS84)
                                        )
                                    )

                                } else {
                                    Toast.makeText(this, "카카오내비 앱이 필요합니다.", Toast.LENGTH_SHORT)
                                        .show()
                                    val newintent = Intent(Intent.ACTION_VIEW)
                                    newintent.setData(Uri.parse("market://details?id=com.locnall.KimGiSa"))
                                    startActivity(newintent)
                                }
                            }


                        }
                        1 -> {
                            if (map_check) {
                                var dname_encode =
                                    URLEncoder.encode(searchlist[check_postion].address)
                                val url =
                                    "nmap://navigation?dlat=" + searchlist[check_postion].lat + "&dlng=" + searchlist[check_postion].long + "&dname=" + dname_encode + "&appname=com.weare.wearecompany.ui.detail"
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                intent.addCategory(Intent.CATEGORY_BROWSABLE)
                                val list = packageManager.queryIntentActivities(
                                    intent,
                                    PackageManager.MATCH_DEFAULT_ONLY
                                )

                                if (list == null || list.isEmpty()) {
                                    startActivity(
                                        Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse("market://details?id=com.nhn.android.nmap")
                                        )
                                    )
                                } else {
                                    startActivity(intent)
                                }


                            } else {
                                var dname_encode =
                                    URLEncoder.encode(datalist[check_postion].address)
                                val url =
                                    "nmap://navigation?dlat=" + datalist[check_postion].lat + "&dlng=" + datalist[check_postion].long + "&dname=" + dname_encode + "&appname=com.weare.wearecompany.ui.detail"
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                intent.addCategory(Intent.CATEGORY_BROWSABLE)
                                val list = packageManager.queryIntentActivities(
                                    intent,
                                    PackageManager.MATCH_DEFAULT_ONLY
                                )

                                if (list == null || list.isEmpty()) {
                                    startActivity(
                                        Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse("market://details?id=com.nhn.android.nmap")
                                        )
                                    )
                                } else {
                                    startActivity(intent)
                                }
                            }


                        }
                    }
                }
                dialog.show(supportFragmentManager, dialog.tag)
            }
        }
    }


}