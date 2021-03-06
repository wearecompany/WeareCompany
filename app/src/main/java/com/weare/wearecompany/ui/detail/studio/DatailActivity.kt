package com.weare.wearecompany.ui.detail

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.dynamiclinks.ktx.*
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.link.LinkClient
import com.kakao.sdk.navi.NaviClient
import com.kakao.sdk.navi.model.CoordType
import com.kakao.sdk.navi.model.Location
import com.kakao.sdk.navi.model.NaviOption
import com.kakao.sdk.template.model.*
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
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
import com.weare.wearecompany.ui.chat.detail.DetailChatActivity
import com.weare.wearecompany.ui.detail.studio.*
import com.weare.wearecompany.ui.detail.studio.reservation.ReservationStudioActivity
import com.weare.wearecompany.utils.BitmapUtils
import com.weare.wearecompany.utils.Constants
import com.weare.wearecompany.utils.LIKE
import com.weare.wearecompany.utils.RESPONSE_STATUS
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import java.io.File
import java.net.URLEncoder

class DatailActivity : BaseActivity<ActivityStudioBinding>(
    R.layout.activity_studio
), View.OnClickListener, OnMapReadyCallback,NestedScrollView.OnScrollChangeListener {

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

    //??????
    var fileName: String? = null
    lateinit var fileuri: Uri
    lateinit var serveruri: String
    private lateinit var sharebitmap: Bitmap
    private lateinit var sharefile: File

    private lateinit var map: NaverMap

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
        // ????????? ??????(????????? ????????? ????????? ??????)
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
        mViewDataBinding.navi.setOnClickListener(this)
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

        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }
        mapFragment.getMapAsync(this)

        /*mapView = MapView(this)
      //  val mapViewContainer = map_view

        mapView.setMapViewEventListener(this)
       // mapViewContainer.addView(mapView)

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
                // ???????????? ???????????? BluePin ?????? ??????.
                marker.markerType = MapPOIItem.MarkerType.CustomImage
                // ????????? ???????????????, ???????????? ???????????? RedPin ?????? ??????.
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
        }*/

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
                Toast.makeText(this, "???????????? ??????", Toast.LENGTH_SHORT).show()
            }
            R.id.navi -> {
                val dialog: DatailNaviDialog = DatailNaviDialog {
                    when (it) {
                        0 -> {
                            if (NaviClient.instance.isKakaoNaviInstalled(this)) {
                                Log.i(Constants.TAG, "??????????????? ????????? ????????? ??????")
                                startActivity(
                                    NaviClient.instance.shareDestinationIntent(
                                        Location(
                                            dataList[0].name,
                                            dataList[0].longitude,
                                            dataList[0].latitude,
                                        ),
                                        NaviOption(coordType = CoordType.WGS84)
                                    )
                                )
                            } else {
                                Toast.makeText(this, "??????????????? ?????? ???????????????.", Toast.LENGTH_SHORT)
                                    .show()
                                val newintent = Intent(Intent.ACTION_VIEW)
                                newintent.setData(Uri.parse("market://details?id=com.locnall.KimGiSa"))
                                startActivity(newintent)
                            }
                        }
                        1 -> {
                            var dname_encode = URLEncoder.encode(dataList[0].address)
                            val url =
                                "nmap://navigation?dlat=" + dataList[0].latitude + "&dlng=" + dataList[0].longitude + "&dname=" + dname_encode +"&appname=com.weare.wearecompany.ui.detail"
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            intent.addCategory(Intent.CATEGORY_BROWSABLE)
                            val list = packageManager.queryIntentActivities(
                                intent,
                                PackageManager.MATCH_DEFAULT_ONLY
                            )

                            if (list == null || list.isEmpty()) {
                                Toast.makeText(this, "????????? ?????? ?????? ???????????????.", Toast.LENGTH_SHORT)
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
                dialog.show(supportFragmentManager, dialog.tag)

                /*val destination: Kakao =
                    com.kakao.kakaonavi.Location.newBuilder(
                        "??????",
                        viewModel.getPicZone().getCoordinate().get(0).doubleValue(),
                        viewModel.getPicZone().getCoordinate().get(1).doubleValue()
                    ).build()

                val builder: KakaoNaviParams= KakaoNaviParams.
                    .setNaviOptions(NaviOptions.newBuilder().setCoordType(CoordType.WGS84).build())

                KakaoNaviService.getInstance()
                    .shareDestination(this@DetailMapActivity, builder.build())*/
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
                                        mViewDataBinding.bookLottie.playAnimation()
                                        mViewDataBinding.likeImage.setImageResource(R.drawable.like_on)
                                        like = true
                                    }
                                }
                            })
                    }
                } else {
                    Toast.makeText(this, "???????????? ?????? ???????????????.", Toast.LENGTH_SHORT).show()
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
                                        "????????? ??????",
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

                            // ?????? ????????? ?????????
                            LinkClient.instance.defaultTemplate(this, params) { linkResult, error ->
                                if (error != null) {
                                    //Log.e(Constants.TAG, "??????????????? ????????? ??????", error)
                                } else if (linkResult != null) {
                                    //Log.d(Constants.TAG, "??????????????? ????????? ?????? ${linkResult.intent}")
                                    startActivity(linkResult.intent)

                                    // ??????????????? ???????????? ??????????????? ?????? ?????? ???????????? ????????? ?????? ?????? ???????????? ?????? ???????????? ?????? ??? ????????????.
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
                            val share = Intent.createChooser(newIntent, "????????? ????????????")
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
                    MainManager.instance.certcheck(user_idx, complation = { responseStatus, check ->
                        when (responseStatus) {
                            RESPONSE_STATUS.OKAY -> {
                                when (check) {
                                    0 -> {
                                        val testdi: tesdialog = tesdialog {
                                            when (it) {
                                                0 -> {

                                                }
                                                1 -> {
                                                    var newIntent = Intent(
                                                        this,
                                                        PhotoPaymentActivity::class.java
                                                    )
                                                    startActivityForResult(newIntent, 9999)
                                                }
                                            }
                                        }
                                        testdi.show(supportFragmentManager, testdi.tag)
                                    }
                                    1 -> {
                                        var newIntent = Intent(
                                            this@DatailActivity,
                                            ReservationStudioActivity::class.java
                                        )
                                        newIntent.putExtra("user_idx", user_idx)
                                        newIntent.putExtra(
                                            "expert_user_idx",
                                            dataList[0].expert_user_idx
                                        )
                                        newIntent.putExtra("expert_idx", dataList[0].idx)
                                        newIntent.putParcelableArrayListExtra("roomdata", datalist)
                                        startActivity(newIntent)
                                    }
                                }
                            }
                        }
                    })
                } else {
                    Toast.makeText(this, "???????????? ?????? ???????????????.", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.datail_studio_chat -> {
                val newIntent = Intent(this, DetailChatActivity::class.java)
                newIntent.putExtra("expert_type", "0")
                newIntent.putExtra("expert_idx", dataList[0].idx)
                newIntent.putExtra("expert_user_idx", dataList[0].expert_user_idx)
                startActivityForResult(newIntent,3001)
            }
        }
    }

    private fun dynamicLicnk() {
        val dynamicLink =
            Firebase.dynamicLinks.dynamicLink { // or Firebase.dynamicLinks.shortLinkAsync
                link = Uri.parse("https://wraer.com/")
                domainUriPrefix = "https://wearecompany.page.link"
                androidParameters("com.weare.wearecompany") {
                    minimumVersion = 3
                }
                socialMetaTagParameters {
                    title = dataList[0].name + " ?? " + dataList[0].nickname
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


   /* override fun onMapViewInitialized(p0: MapView?) {
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
    }*/

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

    override fun onMapReady(p0: NaverMap) {
        map = p0

        val marker = Marker()
        marker.position = LatLng(dataList[0].latitude.toDouble(), dataList[0].longitude.toDouble())
        marker.icon = OverlayImage.fromResource(R.drawable.map_mark_on)
        marker.isFlat = true
        marker.map = map

        val cameraUpdate = CameraUpdate.scrollTo(LatLng(
            dataList[0].latitude.toDouble(),
            dataList[0].longitude.toDouble()
        )).animate(CameraAnimation.Fly, 2000)
        map.moveCamera(cameraUpdate)

    }


}