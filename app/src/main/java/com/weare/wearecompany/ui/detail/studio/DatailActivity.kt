package com.weare.wearecompany.ui.detail

import android.Manifest
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.dynamiclinks.ktx.*
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.link.LinkClient
import com.kakao.sdk.template.model.*
import com.weare.wearecompany.BuildConfig
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.hotpick.data.review
import com.weare.wearecompany.data.hotpick.data.room
import com.weare.wearecompany.data.hotpick.datail
import com.weare.wearecompany.data.retrofit.bottomnav.main.MainManager
import com.weare.wearecompany.data.retrofit.bottomnav.mypage.mypageManager
import com.weare.wearecompany.data.retrofit.hotpick.HotPickDataManager
import com.weare.wearecompany.databinding.ActivityStudioBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.ui.bottommenu.estimate.receive.payment.PhotoPaymentActivity
import com.weare.wearecompany.ui.bottommenu.main.weekly.tesdialog
import com.weare.wearecompany.ui.detail.model.reservation.ReservationModelActivity
import com.weare.wearecompany.ui.detail.studio.*
import com.weare.wearecompany.ui.detail.studio.reservation.ReservationStudioActivity
import com.weare.wearecompany.utils.BitmapUtils
import com.weare.wearecompany.utils.Constants
import com.weare.wearecompany.utils.LIKE
import com.weare.wearecompany.utils.RESPONSE_STATUS
import kotlinx.android.synthetic.main.activity_studio.*
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import java.io.File
import java.text.DecimalFormat

class DatailActivity : BaseActivity<ActivityStudioBinding>(
    R.layout.activity_studio
), View.OnClickListener, MapView.MapViewEventListener,NestedScrollView.OnScrollChangeListener {

    private var dataList = ArrayList<datail>()
    private var roomList = ArrayList<room>()

    private lateinit var dataAdaptrt: StudioPagerAdapter
    private lateinit var address: String

    private val PERMISSIONS_REQUEST_CODE = 100
    private var REQUIRED_PERMISSIONS = arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION)
    lateinit var uNowPosition: MapPoint
    lateinit var mapView: MapView
    private var like: Boolean = false
    private var studio_Idx = ""
    private var user_idx = ""

    //공유
    var fileName: String? = null
    lateinit var fileuri: Uri
    lateinit var serveruri: String
    private lateinit var sharebitmap: Bitmap
    private lateinit var sharefile: File

    private lateinit var datalist:ArrayList<room>
    private lateinit var reviewlist: ArrayList<review>
    private lateinit var dynamicLinkUri: Uri


    private lateinit var roomAdapter: DatailRoomRecyclerAdapter
    private lateinit var infoAdapter: DatailInfoRecyclerAdapter
    private lateinit var reviewAdapter: StudioReviewRecyclerViewAdapter


    override fun onCreate() {

        val toobar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toobar)
        val ActionBar = supportActionBar!!
        ActionBar.setDisplayHomeAsUpEnabled(true)
        ActionBar.setDisplayShowTitleEnabled(false)

        image()
    }

    fun image() {

        user_idx = MyApplication.prefs.getString("user_idx", "")
        // 비동기 통신(요청과 결과가 별개로 작용)
        HotPickDataManager.instance.data(
            user_idx,
            intent.getStringExtra("idx"),
            completion = { responseStatus, data ->

                when (responseStatus) {
                    RESPONSE_STATUS.OKAY -> {

                        if (responseStatus != null) {
                            address = data[0].address
                            dataList = data
                            datalist = data[0].room
                            dataAdaptrt = StudioPagerAdapter(this, dataList)
                            mViewDataBinding.studioViewPager.adapter = dataAdaptrt
                            mViewDataBinding.wormDotsIndicator.setViewPager(mViewDataBinding.studioViewPager)
                            /*dataAdapter = DatailRecyclerViewAdapter(dataList)

                            mViewDataBinding.hotpickListRecyclerView.layoutManager =
                                    LinearLayoutManager(
                                            this,
                                            LinearLayoutManager.HORIZONTAL, false
                                    )
                            mViewDataBinding.hotpickListRecyclerView.adapter = dataAdapter*/

                            roomList = data[0].room
                            roomAdapter = DatailRoomRecyclerAdapter(roomList)
                            roomAdapter.setItemClickListener(object :
                                DatailRoomRecyclerAdapter.OnItemClickListener {
                                override fun onClick(v: View, position: Int, Item: room) {
                                    val roomDialogFragment: RoomDialogFragment =
                                        RoomDialogFragment(Item) {

                                        }
                                    roomDialogFragment.show(
                                        supportFragmentManager,
                                        roomDialogFragment.tag
                                    )
                                }

                            })

                            mViewDataBinding.datailRoomList.layoutManager = LinearLayoutManager(
                                this,
                                LinearLayoutManager.HORIZONTAL, false
                            )
                            mViewDataBinding.datailRoomList.adapter = roomAdapter

                            infoAdapter = DatailInfoRecyclerAdapter(dataList)

                            mViewDataBinding.datailRoomInfo.layoutManager = LinearLayoutManager(
                                this,
                                LinearLayoutManager.VERTICAL, false
                            )
                            mViewDataBinding.datailRoomInfo.adapter = infoAdapter

                            setup(dataList)
                        }

                    }
                    RESPONSE_STATUS.FAIL -> {

                    }
                }
            })
    }

    fun setup(data: ArrayList<datail>) {

        val thread = Thread(Runnable {
            fileuri = Uri.parse(data[0].images[0])
            serveruri = data[0].images[0]
            fileName = fileuri.lastPathSegment


            sharebitmap = Glide.with(this).asBitmap().load(fileuri).submit().get()
            Handler(Looper.getMainLooper()).post {

                // show Progress on UI Thread
            }
            Handler(Looper.getMainLooper()).post {
                sharefile = BitmapUtils.glideToBitmap(this, sharebitmap, fileName)
                fileuri = FileProvider.getUriForFile(
                    this,
                    BuildConfig.APPLICATION_ID + ".provider",
                    sharefile
                )

            }
        })

        thread.start()

        studio_Idx = data[0].idx
        mViewDataBinding.datailName.setText(data[0].name)
        mViewDataBinding.datailTitle.setText(data[0].title)
        mViewDataBinding.datailAddress.setText(data[0].address)

        mViewDataBinding.sharing.setOnClickListener(this)
        mViewDataBinding.like.setOnClickListener(this)
        mViewDataBinding.studioReservation.setOnClickListener(this)
        mViewDataBinding.datailStudioChat.setOnClickListener(this)

        like = data[0].like_status
        if (like) {
            mViewDataBinding.likeImage.setImageResource(R.drawable.like_on)
            like = true
        } else {
            mViewDataBinding.likeImage.setImageResource(R.drawable.like_off)
        }
        mViewDataBinding.addrCopy.setOnClickListener(this)

        if (data[0].thumbnail != "") {
            var multiTransformation = MultiTransformation(CenterCrop(), RoundedCorners(20))
            Glide.with(MyApplication.instance)
                .load(data[0].thumbnail)
                .apply(RequestOptions.bitmapTransform(multiTransformation))
                .into(mViewDataBinding.studioThumbnail)
        }
        mViewDataBinding.studioNickname.setText(data[0].nickname)
        mViewDataBinding.studioRule.setText(data[0].rule)
        mViewDataBinding.studioParking.setText(data[0].parking)
        mViewDataBinding.studioMapAddress.setText(data[0].address)

        mapView = MapView(this)
        val mapViewContainer = map_view

        mapView.setMapViewEventListener(this)
        mapViewContainer.addView(mapView)

        val permissionCheck = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            try {
                uNowPosition = MapPoint.mapPointWithGeoCoord(
                    data[0].latitude.toDouble(),
                    data[0].longitude.toDouble()
                )
                mapView.setMapCenterPoint(uNowPosition, true)
                val marker = MapPOIItem()
                marker.itemName = data[0].address
                marker.tag = 0
                marker.mapPoint = uNowPosition
                // 기본으로 제공하는 BluePin 마커 모양.
                marker.markerType = MapPOIItem.MarkerType.CustomImage
                // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
                marker.customImageResourceId = R.drawable.location_min_pin
                marker.setCustomImageAutoscale(false)
                //marker.selectedMarkerType = MapPOIItem.MarkerType.RedPin
                mapView.addPOIItem(marker)
            } catch (e: NullPointerException) {
                Log.e("LOCATION_ERROR", e.toString())
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ActivityCompat.finishAffinity(this as Activity)
                } else {
                    ActivityCompat.finishAffinity(this as Activity)
                }

            }
        } else {
            ActivityCompat.requestPermissions(
                this as Activity,
                REQUIRED_PERMISSIONS,
                PERMISSIONS_REQUEST_CODE
            )
        }

        if (data[0].review.size != 0) {
            mViewDataBinding.reviewLayout.visibility = View.VISIBLE
            reviewlist = data[0].review

            reviewAdapter = StudioReviewRecyclerViewAdapter(reviewlist)

            mViewDataBinding.reviewRecyclerview.layoutManager = LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL, false
            )
            mViewDataBinding.reviewRecyclerview.adapter = reviewAdapter
        } else {

        }

    }


    override fun onResume() {
        super.onResume()
        addLocationListener()
    }

    private fun addLocationListener() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.addr_copy -> {
                Toast.makeText(this, "주소복사 완료", Toast.LENGTH_SHORT).show()
            }
            R.id.like -> {
                if (MyApplication.prefs.getString("user_idx", "") != "") {
                    if (like) {
                        mViewDataBinding.likeImage.setImageResource(R.drawable.like_off)
                        like = false
                        mypageManager.instance.like_off(
                            0,
                            studio_Idx,
                            user_idx,
                            completion = { responseStatus ->
                                when (responseStatus) {
                                    LIKE.OKAY -> {
                                    }
                                }
                            })

                    } else {
                        mypageManager.instance.like_on(
                            0,
                            studio_Idx,
                            user_idx,
                            completion = { responseStatus ->
                                when (responseStatus) {
                                    LIKE.OKAY -> {
                                        mViewDataBinding.likeImage.setImageResource(R.drawable.like_on)
                                        like = true
                                    }
                                }
                            })
                    }
                } else {
                    Toast.makeText(this, "로그인후 이용 가능합니다.", Toast.LENGTH_SHORT).show()
                }

            }
            R.id.sharing -> {
                val dialog: DatailSharingDialog = DatailSharingDialog() {
                    when (it) {
                        0 -> {
                            val params = FeedTemplate(
                                content = Content(
                                    title = dataList[0].name,
                                    description = dataList[0].title,
                                    imageUrl = dataList[0].images[0],
                                    link = Link(
                                        webUrl = "https://developers.kakao.com",
                                        mobileWebUrl = "https://developers.kakao.com"
                                    )
                                ),
                                social = Social(
                                    likeCount = dataList[0].like
                                ),
                                buttons = listOf(
                                    Button(
                                        "앱으로 보기",
                                        Link(
                                            androidExecParams = mapOf(
                                                "user_idx" to MyApplication.prefs.getString(
                                                    "user_idx",
                                                    ""
                                                ), "item_idx" to studio_Idx, "item_type" to "0"
                                            ),
                                            iosExecParams = mapOf(
                                                "user_idx" to MyApplication.prefs.getString(
                                                    "user_idx",
                                                    ""
                                                ), "item_idx" to studio_Idx, "item_type" to "0"
                                            ),

                                            )
                                    )
                                )
                            )

                            // 피드 메시지 보내기
                            LinkClient.instance.defaultTemplate(this, params) { linkResult, error ->
                                if (error != null) {
                                    Log.e(Constants.TAG, "카카오링크 보내기 실패", error)
                                } else if (linkResult != null) {
                                    Log.d(Constants.TAG, "카카오링크 보내기 성공 ${linkResult.intent}")
                                    startActivity(linkResult.intent)

                                    // 카카오링크 보내기에 성공했지만 아래 경고 메시지가 존재할 경우 일부 컨텐츠가 정상 동작하지 않을 수 있습니다.
                                    Log.w(Constants.TAG, "Warning Msg: ${linkResult.warningMsg}")
                                    Log.w(Constants.TAG, "Argument Msg: ${linkResult.argumentMsg}")
                                }
                            }
                        }
                        1 -> {
                            val newIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                type = "image/*"
                                putExtra(Intent.EXTRA_STREAM, fileuri)
                                setPackage("com.instagram.android")
                            }
                            val share = Intent.createChooser(newIntent, "인스타 공유하기")
                            startActivity(share)
                        }
                        2 -> {

                        }
                        3 -> {
                            dynamicLicnk()
                        }
                    }
                }
                dialog.show(supportFragmentManager, dialog.tag)
            }
            R.id.studio_reservation -> {
                if (MyApplication.prefs.getString("user_idx", "") != "") {
                    MainManager.instance.certcheck(user_idx,complation = { responseStatus, check ->
                        when(responseStatus) {
                            RESPONSE_STATUS.OKAY -> {
                                when(check) {
                                    0 -> {
                                        val testdi: tesdialog = tesdialog {
                                            when(it) {
                                                0 -> {

                                                }
                                                1 -> {
                                                    var newIntent = Intent(this, PhotoPaymentActivity::class.java)
                                                    startActivityForResult(newIntent, 9999)
                                                }
                                            }
                                        }
                                        testdi.show(supportFragmentManager, testdi.tag)
                                    }
                                    1 -> {
                                        var newIntent = Intent(this@DatailActivity, ReservationStudioActivity::class.java)
                                        newIntent.putExtra("user_idx",user_idx)
                                        newIntent.putExtra("expert_user_idx",dataList[0].expert_user_idx)
                                        newIntent.putExtra("expert_idx",dataList[0].idx)
                                        newIntent.putParcelableArrayListExtra("roomdata",datalist)
                                        startActivity(newIntent)
                                    }
                                }
                            }
                        }
                    })
                } else {
                    Toast.makeText(this, "로그인후 이용 가능합니다.", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.datail_studio_chat -> {

            }
        }
    }

    private fun dynamicLicnk() {
        val dynamicLink =
            Firebase.dynamicLinks.dynamicLink { // or Firebase.dynamicLinks.shortLinkAsync
                link = Uri.parse("https://wraer.com/" )
                domainUriPrefix = "https://wearecompany.page.link"
                androidParameters("com.weare.wearecompany") {
                    minimumVersion = 3
                }
                socialMetaTagParameters {
                    title = dataList[0].name + " · " + dataList[0].nickname
                    description = dataList[0].title
                    imageUrl = Uri.parse(dataList[0].images[0])
                }
            }
        dynamicLinkUri = dynamicLink.uri
        shortenLongLink()

    }

    fun shortenLongLink() {
        // [START shorten_long_link]
        Firebase.dynamicLinks.shortLinkAsync {
            longLink = Uri.parse(dynamicLinkUri.toString())

        }.addOnSuccessListener { (shortLink, flowChartLink) ->
            // You'll need to import com.google.firebase.dynamiclinks.ktx.component1 and
            // com.google.firebase.dynamiclinks.ktx.component2

            // Short link created
            processShortLink(shortLink, flowChartLink)
        }.addOnFailureListener {
            // Error
            // ...
        }
        // [END shorten_long_link]
    }

    private fun processShortLink(shortLink: Uri?, previewLink: Uri?) {

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shortLink.toString())
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, dynamicLinkUri.toString())
        startActivity(shareIntent)
    }


    override fun onMapViewInitialized(p0: MapView?) {
    }

    override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {
        mViewDataBinding.detailNestedScrollview.requestDisallowInterceptTouchEvent(true)
    }

    override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {
        mViewDataBinding.detailNestedScrollview.requestDisallowInterceptTouchEvent(true)
    }

    override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint?) {
        mViewDataBinding.detailNestedScrollview.requestDisallowInterceptTouchEvent(true)
    }

    override fun onMapViewDoubleTapped(p0: MapView?, p1: MapPoint?) {
        mViewDataBinding.detailNestedScrollview.requestDisallowInterceptTouchEvent(true)
    }

    override fun onMapViewLongPressed(p0: MapView?, p1: MapPoint?) {
        mViewDataBinding.detailNestedScrollview.requestDisallowInterceptTouchEvent(true)
    }

    override fun onMapViewDragStarted(p0: MapView?, p1: MapPoint?) {
        mViewDataBinding.detailNestedScrollview.requestDisallowInterceptTouchEvent(true)
    }

    override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {
        mViewDataBinding.detailNestedScrollview.requestDisallowInterceptTouchEvent(true)
    }

    override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {
        mViewDataBinding.detailNestedScrollview.requestDisallowInterceptTouchEvent(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home -> {
                val file = File(this.cacheDir, fileName)
                if (file.exists()) {
                    file.delete()
                }
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onScrollChange(
        v: NestedScrollView?,
        scrollX: Int,
        scrollY: Int,
        oldScrollX: Int,
        oldScrollY: Int
    ) {
    }


}