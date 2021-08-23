package com.weare.wearecompany.ui.bottommenu.estimate.estimateDatail

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.weare.wearecompany.BuildConfig
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.retrofit.bottomnav.estimate.requestManager
import com.weare.wearecompany.databinding.ActivityEstimateBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.utils.RESPONSE_STATUS
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.weare.wearecompany.ui.bottom.*
import com.weare.wearecompany.ui.container.ContainerActivity
import kotlinx.android.synthetic.main.dialog_edit_user.view.*
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class EstimateDatailActivity : BaseActivity<ActivityEstimateBinding>(
    R.layout.activity_estimate
), View.OnClickListener {

    val REQ_CAMERA_PERMISSION = 1001
    val REQ_STORAGE_PERMISSION = 1001

    private val CAMERA_IMAGE = 1
    private val GALLERY_IMAGE = 2
    lateinit var imagePath: String
    var check: Boolean = false
    lateinit var sheetBehavior: BottomSheetBehavior<LinearLayout>
    lateinit var photoUri: Uri
    lateinit var imageFileName: String

    private lateinit var storageDir: File

    private var photoList = ArrayList<Uri>()
    private var dataList = ArrayList<Uri>()
    private var datafileList = ArrayList<File>()
    private var filenameList = ArrayList<String>()
    private lateinit var dataAdapter: EstimateDatailRecyclerViewAdapter

    val korFotmatt = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)

    private var expert_type = -1



    private lateinit var postlayout: RelativeLayout
    private lateinit var posttext: TextView
    private var clicknum = -1

    private var expert_category = -1
    private var place = 0
    private var startTime = "01:00"
    private var endTime = "01:00"
    private var startCheckTime = 1
    private var endCheckTime = 1

    private var day:String = ""

    override fun onCreate() {

        val toobar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toobar)
        val ActionBar = supportActionBar!!
        ActionBar.setDisplayHomeAsUpEnabled(true)
        ActionBar.setDisplayShowTitleEnabled(false)

        setup()

    }

    fun setup() {

        val min = Calendar.getInstance()
        min.add(Calendar.DAY_OF_MONTH, -1)

        val max = Calendar.getInstance()
        max.add(Calendar.DAY_OF_MONTH, +30)

        mViewDataBinding.calendar.setMinimumDate(min)
        mViewDataBinding.calendar.setMaximumDate(max)

        mViewDataBinding.photoUpload.setOnClickListener(this)
        mViewDataBinding.estimateOk.setOnClickListener(this)
        mViewDataBinding.estimateTopCategory.setOnClickListener(this)
        mViewDataBinding.estimateBottomCategory.setOnClickListener(this)
        mViewDataBinding.estimateBottomLocation.setOnClickListener(this)
        mViewDataBinding.estimateTime.setOnClickListener(this)
        //sheetBehavior= BottomSheetBehavior.from(dialog)
        dataAdapter = EstimateDatailRecyclerViewAdapter(this, dataList)
        mViewDataBinding.photoRecyclerview.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL, false
        )
        mViewDataBinding.photoRecyclerview.adapter = dataAdapter
        dataAdapter.setItemClickListener(object :
            EstimateDatailRecyclerViewAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int, Item: Uri) {
                    val file = File(this@EstimateDatailActivity.cacheDir, filenameList[position])
                    if (file.exists()) {
                        file.delete()
                    }
                dataAdapter.removeItem(position)
                mViewDataBinding.photoCount.setText(dataAdapter.getSize().toString())
                photoList.removeAt(position)
                filenameList.removeAt(position)
            }

        })

        mViewDataBinding.calendar.setOnDayClickListener(object : OnDayClickListener {
            override fun onDayClick(eventDay: EventDay) {
                //day = korFotmatt.format(eventDay.calendar.time)
            }

        })
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.photo_upload -> {
                val photodialog:EstimateDatailPhotoUploadDialog = EstimateDatailPhotoUploadDialog() {
                    when(it) {
                        0 -> {
                            selectCamera()
                        }
                        1 -> {
                            selectgallery()
                        }
                    }
                }
                photodialog.show(supportFragmentManager,photodialog.tag)
            }

            R.id.estimate_top_category -> {
                val topCategoryFragment: TopCategoryFragment = TopCategoryFragment(expert_type) {
                    expert_type = it
                    when (it) {
                        0 -> {
                            mViewDataBinding.topText.setText("포토그래퍼")
                            mViewDataBinding.estimateLocationLayout.visibility = View.VISIBLE
                            mViewDataBinding.bottomText.setText("선택해주세요")
                            mViewDataBinding.estimateLocationText.setText("선택해주세요")
                            if(expert_category != -1) {
                                expert_category = -1
                            }
                            if(place != -1) {
                                place = -1
                            }
                        }
                        1 -> {
                            mViewDataBinding.topText.setText("모델")
                            mViewDataBinding.estimateLocationLayout.visibility = View.VISIBLE
                            mViewDataBinding.bottomText.setText("선택해주세요")
                            mViewDataBinding.estimateLocationText.setText("선택해주세요")
                            if(expert_category != -1) {
                                expert_category = -1
                            }
                            if(place != -1) {
                                place = -1
                            }
                        }
                        2 -> {
                            mViewDataBinding.topText.setText("뷰티전문가")
                            mViewDataBinding.estimateLocationLayout.visibility = View.VISIBLE
                            mViewDataBinding.bottomText.setText("선택해주세요")
                            mViewDataBinding.estimateLocationText.setText("선택해주세요")
                            if(expert_category != -1) {
                                expert_category = -1
                            }
                            if(place != -1) {
                                place = -1
                            }
                        }
                    }
                }
                topCategoryFragment.show(supportFragmentManager, topCategoryFragment.tag)
            }

            R.id.estimate_bottom_category -> {
                if (mViewDataBinding.topText.text == "포토그래퍼") {
                    val photoCategory: BottomCategoryPhotoFragment = BottomCategoryPhotoFragment(expert_category) {
                        expert_category = it
                        when (it) {
                            0 -> mViewDataBinding.bottomText.setText("프로필")
                            1 -> mViewDataBinding.bottomText.setText("뷰티/패션.쥬얼리")
                            2 -> mViewDataBinding.bottomText.setText("패션화보")
                            3 -> mViewDataBinding.bottomText.setText("출장/행사")
                            4 -> mViewDataBinding.bottomText.setText("제품")
                            5 -> mViewDataBinding.bottomText.setText("쇼핑몰")
                            6 -> mViewDataBinding.bottomText.setText("푸드")
                            7 -> mViewDataBinding.bottomText.setText("아기")
                            8 -> mViewDataBinding.bottomText.setText("커플/우정")
                            9 -> mViewDataBinding.bottomText.setText("가족")
                            10 -> mViewDataBinding.bottomText.setText("임신기념")
                            11 -> mViewDataBinding.bottomText.setText("아마추어")
                            12 -> mViewDataBinding.bottomText.setText("감성")
                            13 -> mViewDataBinding.bottomText.setText("영상(광고/유투브)")
                        }
                    }
                    photoCategory.show(supportFragmentManager, photoCategory.tag)
                } else if (mViewDataBinding.topText.text == "모델") {
                    val modelCategory: BottomCategoryModelFragment = BottomCategoryModelFragment(expert_category) {
                        expert_category = it
                        when (it) {
                            0 -> mViewDataBinding.bottomText.setText("뷰티")
                            1 -> mViewDataBinding.bottomText.setText("쇼핑몰")
                            2 -> mViewDataBinding.bottomText.setText("패션")
                            3 -> mViewDataBinding.bottomText.setText("부분")
                            4 -> mViewDataBinding.bottomText.setText("영상")
                            5 -> mViewDataBinding.bottomText.setText("주부")
                            6 -> mViewDataBinding.bottomText.setText("일반인")
                            7 -> mViewDataBinding.bottomText.setText("남자")
                            8 -> mViewDataBinding.bottomText.setText("성형")
                            9 -> mViewDataBinding.bottomText.setText("빅사이즈")
                            10 -> mViewDataBinding.bottomText.setText("시니어")
                            11 -> mViewDataBinding.bottomText.setText("주니어")
                            12 -> mViewDataBinding.bottomText.setText("수영복")
                            13 -> mViewDataBinding.bottomText.setText("인스타인기")
                            14 -> mViewDataBinding.bottomText.setText("유튜버")
                            15 -> mViewDataBinding.bottomText.setText("아나운서")
                            16 -> mViewDataBinding.bottomText.setText("레이싱")
                            17 -> mViewDataBinding.bottomText.setText("외국인")
                            18 -> mViewDataBinding.bottomText.setText("연기")
                            19 -> mViewDataBinding.bottomText.setText("mc/행사")
                        }
                    }
                    modelCategory.show(supportFragmentManager, modelCategory.tag)
                } else if (mViewDataBinding.topText.text == "뷰티전문가") {
                    val tripCategory: BottomCategoryTripFragment = BottomCategoryTripFragment(expert_category) {
                        expert_category = it
                        when (it) {
                            0 -> mViewDataBinding.bottomText.setText("헤어")
                            1 -> mViewDataBinding.bottomText.setText("메이크업")
                            2 -> mViewDataBinding.bottomText.setText("외 뷰티")
                        }
                    }
                    tripCategory.show(supportFragmentManager, tripCategory.tag)
                }  else {
                    Toast.makeText(this, "상위 카테고리를 먼저 선택해주세요.", Toast.LENGTH_SHORT).show()
                }
            }

            R.id.estimate_bottom_location -> {
                if (mViewDataBinding.topText.text != "선택해주세요") {
                    val locationCategory: BottomCategoryLocationFragment =
                        BottomCategoryLocationFragment(place) {
                            place = it
                            when (it) {
                                0 -> mViewDataBinding.estimateLocationText.setText("서울")
                                1 -> mViewDataBinding.estimateLocationText.setText("경기")
                                2 -> mViewDataBinding.estimateLocationText.setText("인천")
                                3 -> mViewDataBinding.estimateLocationText.setText("강원")
                                4 -> mViewDataBinding.estimateLocationText.setText("제주")
                                5 -> mViewDataBinding.estimateLocationText.setText("대전")
                                6 -> mViewDataBinding.estimateLocationText.setText("충북")
                                7 -> mViewDataBinding.estimateLocationText.setText("충남/세종")
                                8 -> mViewDataBinding.estimateLocationText.setText("부산")
                                9 -> mViewDataBinding.estimateLocationText.setText("울산")
                                10 -> mViewDataBinding.estimateLocationText.setText("경남")
                                11 -> mViewDataBinding.estimateLocationText.setText("대구")
                                12 -> mViewDataBinding.estimateLocationText.setText("경북")
                                13 -> mViewDataBinding.estimateLocationText.setText("광주")
                                14 -> mViewDataBinding.estimateLocationText.setText("전남")
                                15 -> mViewDataBinding.estimateLocationText.setText("전주/전북")
                            }
                        }
                    locationCategory.show(supportFragmentManager, locationCategory.tag)
                } else {
                    Toast.makeText(this, "상위 및카테고리를 먼저 선택해주세요.", Toast.LENGTH_SHORT).show()
                }
            }

            R.id.estimate_time -> {
                val time: BottomTimeFragment = BottomTimeFragment(startTime = { s ->
                        when (s) {
                            0 -> {
                                mViewDataBinding.startTime.setText("01:00")
                                startTime = "01:00"
                                startCheckTime = 1
                            }
                            1 -> {
                                mViewDataBinding.startTime.setText("02:00")
                                startTime = "02:00"
                                startCheckTime = 2
                            }
                            2 -> {
                                mViewDataBinding.startTime.setText("03:00")
                                startTime = "03:00"
                                startCheckTime = 3
                            }
                            3 -> {
                                mViewDataBinding.startTime.setText("04:00")
                                startTime = "04:00"
                                startCheckTime = 4
                            }
                            4 -> {
                                mViewDataBinding.startTime.setText("05:00")
                                startTime = "05:00"
                                startCheckTime = 5
                            }
                            5 -> {
                                mViewDataBinding.startTime.setText("06:00")
                                startTime = "06:00"
                                startCheckTime = 6
                            }
                            6 -> {
                                mViewDataBinding.startTime.setText("07:00")
                                startTime = "07:00"
                                startCheckTime = 7
                            }
                            7 -> {
                                mViewDataBinding.startTime.setText("08:00")
                                startTime = "08:00"
                                startCheckTime = 8
                            }
                            8 -> {
                                mViewDataBinding.startTime.setText("09:00")
                                startTime = "09:00"
                                startCheckTime = 9
                            }
                            9 -> {
                                mViewDataBinding.startTime.setText("10:00")
                                startTime = "10:00"
                                startCheckTime = 10
                            }
                            10 -> {
                                mViewDataBinding.startTime.setText("11:00")
                                startTime = "11:00"
                                startCheckTime = 11
                            }
                            11 -> {
                                mViewDataBinding.startTime.setText("12:00")
                                startTime = "12:00"
                                startCheckTime = 12
                            }
                            12 -> {
                                mViewDataBinding.startTime.setText("13:00")
                                startTime = "13:00"
                                startCheckTime = 13
                            }
                            13 -> {
                                mViewDataBinding.startTime.setText("14:00")
                                startTime = "14:00"
                                startCheckTime = 14
                            }
                            14 -> {
                                mViewDataBinding.startTime.setText("15:00")
                                startTime = "15:00"
                                startCheckTime = 15
                            }
                            15 -> {
                                mViewDataBinding.startTime.setText("16:00")
                                startTime = "16:00"
                                startCheckTime = 16
                            }
                            16 -> {
                                mViewDataBinding.startTime.setText("17:00")
                                startTime = "17:00"
                                startCheckTime = 17
                            }
                            17 -> {
                                mViewDataBinding.startTime.setText("18:00")
                                startTime = "18:00"
                                startCheckTime = 18
                            }
                            18 -> {
                                mViewDataBinding.startTime.setText("19:00")
                                startTime = "19:00"
                                startCheckTime = 19
                            }
                            19 -> {
                                mViewDataBinding.startTime.setText("20:00")
                                startTime = "20:00"
                                startCheckTime = 20
                            }
                            20 -> {
                                mViewDataBinding.startTime.setText("21:00")
                                startTime = "21:00"
                                startCheckTime = 21
                            }
                            21 -> {
                                mViewDataBinding.startTime.setText("22:00")
                                startTime = "22:00"
                                startCheckTime = 22
                            }
                            22 -> {
                                mViewDataBinding.startTime.setText("23:00")
                                startTime = "23:00"
                                startCheckTime = 23
                            }
                            23 -> {
                                mViewDataBinding.startTime.setText("24:00")
                                startTime = "24:00"
                                startCheckTime = 24
                            }
                        }

                }, endTime = { e ->
                        when (e) {
                            0 -> {
                                mViewDataBinding.endTime.setText("01:00")
                                endTime = "01:00"
                                endCheckTime = 1
                            }
                            1 -> {
                                mViewDataBinding.endTime.setText("02:00")
                                endTime = "02:00"
                                endCheckTime = 2
                            }
                            2 -> {
                                mViewDataBinding.endTime.setText("03:00")
                                endTime = "03:00"
                                endCheckTime = 3
                            }
                            3 -> {
                                mViewDataBinding.endTime.setText("04:00")
                                endTime = "04:00"
                                endCheckTime = 4
                            }
                            4 -> {
                                mViewDataBinding.endTime.setText("05:00")
                                endTime = "05:00"
                                endCheckTime = 5
                            }
                            5 -> {
                                mViewDataBinding.endTime.setText("06:00")
                                endTime = "06:00"
                                endCheckTime = 6
                            }
                            6 -> {
                                mViewDataBinding.endTime.setText("07:00")
                                endTime = "07:00"
                                endCheckTime = 7
                            }
                            7 -> {
                                mViewDataBinding.endTime.setText("08:00")
                                endTime = "08:00"
                                endCheckTime = 8
                            }
                            8 -> {
                                mViewDataBinding.endTime.setText("09:00")
                                endTime = "09:00"
                                endCheckTime = 9
                            }
                            9 -> {
                                mViewDataBinding.endTime.setText("10:00")
                                endTime = "10:00"
                                endCheckTime = 10
                            }
                            10 -> {
                                mViewDataBinding.endTime.setText("11:00")
                                endTime = "11:00"
                                endCheckTime = 11
                            }
                            11 -> {
                                mViewDataBinding.endTime.setText("12:00")
                                endTime = "12:00"
                                endCheckTime = 12
                            }
                            12 -> {
                                mViewDataBinding.endTime.setText("13:00")
                                endTime = "13:00"
                                endCheckTime = 13
                            }
                            13 -> {
                                mViewDataBinding.endTime.setText("14:00")
                                endTime = "14:00"
                                endCheckTime = 14
                            }
                            14 -> {
                                mViewDataBinding.endTime.setText("15:00")
                                endTime = "15:00"
                                endCheckTime = 15
                            }
                            15 -> {
                                mViewDataBinding.endTime.setText("16:00")
                                endTime = "16:00"
                                endCheckTime = 16
                            }
                            16 -> {
                                mViewDataBinding.endTime.setText("17:00")
                                endTime = "17:00"
                                endCheckTime = 17
                            }
                            17 -> {
                                mViewDataBinding.endTime.setText("18:00")
                                endTime = "18:00"
                                endCheckTime = 18
                            }
                            18 -> {
                                mViewDataBinding.endTime.setText("19:00")
                                endTime = "19:00"
                                endCheckTime = 19
                            }
                            19 -> {
                                mViewDataBinding.endTime.setText("20:00")
                                endTime = "20:00"
                                endCheckTime = 20
                            }
                            20 -> {
                                mViewDataBinding.endTime.setText("21:00")
                                endTime = "21:00"
                                endCheckTime = 21
                            }
                            21 -> {
                                mViewDataBinding.endTime.setText("22:00")
                                endTime = "22:00"
                                endCheckTime = 22
                            }
                            22 -> {
                                mViewDataBinding.endTime.setText("23:00")
                                endTime = "23:00"
                                endCheckTime = 23
                            }
                            23 -> {
                                mViewDataBinding.endTime.setText("24:00")
                                endTime = "24:00"
                                endCheckTime = 24
                            }
                        }
                }, itemClick = {
                     if (startCheckTime > endCheckTime) {
                        Toast.makeText(this, "시작 시간은 마감 시간보다 늦을수 없습니다.", Toast.LENGTH_SHORT).show()
                        mViewDataBinding.startTime.setText("재선택")
                        mViewDataBinding.startTime.setTextColor(Color.parseColor("#f96565"))
                        mViewDataBinding.endTime.setText("재선택")
                        mViewDataBinding.endTime.setTextColor(Color.parseColor("#f96565"))
                    } else if(endCheckTime < startCheckTime) {
                        Toast.makeText(this, "마감 시간은 시작 시간보다 빠를수 없습니다.", Toast.LENGTH_SHORT).show()
                         mViewDataBinding.startTime.setText("재선택")
                         mViewDataBinding.startTime.setTextColor(Color.parseColor("#f96565"))
                         mViewDataBinding.endTime.setText("재선택")
                         mViewDataBinding.endTime.setTextColor(Color.parseColor("#f96565"))
                    } else if(startCheckTime == endCheckTime) {
                        Toast.makeText(this, "시작 시간과 마감 시간은 같을수 없습니다.", Toast.LENGTH_SHORT).show()
                        mViewDataBinding.startTime.setText("재선택")
                        mViewDataBinding.startTime.setTextColor(Color.parseColor("#f96565"))
                        mViewDataBinding.endTime.setText("재선택")
                        mViewDataBinding.endTime.setTextColor(Color.parseColor("#f96565"))
                    } else {
                        mViewDataBinding.startTime.setTextColor(Color.parseColor("#000000"))
                        mViewDataBinding.endTime.setTextColor(Color.parseColor("#000000"))
                    }
                })
                time.isCancelable = false
                time.show(supportFragmentManager, time.tag)
            }

            R.id.estimate_ok -> {
                if (mViewDataBinding.topText.text == "선택해주세요") {
                    Toast.makeText(this, "상위 카테고리를 선택해주세요.", Toast.LENGTH_SHORT).show()
                } else if (mViewDataBinding.bottomText.text == "선택해주세요") {
                    Toast.makeText(this, "하위 카테고리를 선택해주세요.", Toast.LENGTH_SHORT).show()
                } else if (mViewDataBinding.estimateLocationText.text == "선택해주세요") {
                    Toast.makeText(this, "위치 선택을 해주세요.", Toast.LENGTH_SHORT).show()
                }  else if (mViewDataBinding.startTime.text == "00:00" && mViewDataBinding.endTime.text == "00:00" || mViewDataBinding.endTime.text == "재선택") {
                    Toast.makeText(this, "시간을 선택을 해주세요.", Toast.LENGTH_SHORT).show()
                } else if (mViewDataBinding.estimateContents.text.toString() == ""){
                    Toast.makeText(this, "요청 내용을 작성해 주세요.", Toast.LENGTH_SHORT).show()
                }else {
                    if (photoList.size > 0) {
                        day = korFotmatt.format(mViewDataBinding.calendar.selectedDates[0].time)
                        val estimateDialog: EstimateDatailDialog = EstimateDatailDialog(mViewDataBinding.topText.text.toString(),mViewDataBinding.bottomText.text.toString(),mViewDataBinding.estimateLocationText.text.toString(),photoList,day,mViewDataBinding.startTime.text.toString(),mViewDataBinding.endTime.text.toString(),mViewDataBinding.estimateContents.text.toString()){
                            when(it) {
                                1 -> {
                                    for (i in 1..photoList.size) {
                                        datafileList.add(imageReSize(photoList[i - 1], 500, filenameList[i - 1]))
                                    }
                                    requestManager.instance.upload(
                                        MyApplication.prefs.getString("user_idx", ""),
                                        expert_type + 1,
                                        expert_category,
                                        place,
                                        datafileList,
                                        false,
                                        day,
                                        mViewDataBinding.startTime.text.toString(),
                                        mViewDataBinding.endTime.text.toString(),
                                        mViewDataBinding.estimateContents.text.toString(),
                                        completion = { responseStatus ->

                                            when (responseStatus) {
                                                RESPONSE_STATUS.OKAY -> {
                                                    for (i in filenameList) {
                                                        val file = File(this.cacheDir, i)
                                                        if (file.exists()) {
                                                            file.delete()
                                                        }
                                                    }
                                                    var newIntent = Intent(this, ContainerActivity::class.java)
                                                    newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                                    newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                                    newIntent.putExtra("reservation", 1)
                                                    startActivity(newIntent)

                                                }
                                                RESPONSE_STATUS.NOT_USER -> {
                                                    Toast.makeText(this,"선택하신 조건의 해당하는 전문가가 없습니다.",Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                        })
                                }
                            }
                        }
                        estimateDialog.show(supportFragmentManager,estimateDialog.tag)
                        estimateDialog.isCancelable = false
                    } else {
                        day = korFotmatt.format(mViewDataBinding.calendar.selectedDates[0].time)
                        val estimateDialog: EstimateDatailDialog = EstimateDatailDialog(mViewDataBinding.topText.text.toString(),mViewDataBinding.bottomText.text.toString(),mViewDataBinding.estimateLocationText.text.toString(),photoList,day,mViewDataBinding.startTime.text.toString(),mViewDataBinding.endTime.text.toString(),mViewDataBinding.estimateContents.text.toString()){
                            when(it) {
                                1 -> {
                                    requestManager.instance.upload(
                                        MyApplication.prefs.getString("user_idx", ""),
                                        expert_type + 1,
                                        expert_category,
                                        place,
                                        datafileList,
                                        false,
                                        day,
                                        mViewDataBinding.startTime.text.toString(),
                                        mViewDataBinding.endTime.text.toString(),
                                        mViewDataBinding.estimateContents.text.toString(),
                                        completion = { responseStatus ->

                                            when (responseStatus) {
                                                RESPONSE_STATUS.OKAY -> {
                                                    var newIntent = Intent(this, ContainerActivity::class.java)
                                                    newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                                    newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                                    newIntent.putExtra("reservation", 1)
                                                    startActivity(newIntent)
                                                }
                                                RESPONSE_STATUS.NOT_USER -> {
                                                    Toast.makeText(this,"선택하신 조건의 전문가가 존재하지 않습니다.",Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                        })
                                }
                            }
                        }
                        estimateDialog.show(supportFragmentManager,estimateDialog.tag)
                        estimateDialog.isCancelable = false
                    }
                }

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
            when (requestCode) {
                CAMERA_IMAGE -> {
                    if (resultCode == Activity.RESULT_OK) {
                        if (photoList.size < 6) {
                            dataAdapter.addItem(photoUri)
                            photoList.add(photoUri)
                            filenameList.add(imageFileName)
                            mViewDataBinding.photoCount.setText(dataAdapter.getSize().toString())
                        } else {
                            Toast.makeText(this, "사진을 최대로 선택하셨습니다.",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                GALLERY_IMAGE -> {
                    if (resultCode == Activity.RESULT_OK) {

                        if (data?.clipData != null) { // 사진 여러개 선택한 경우
                            val count = data.clipData!!.itemCount
                            if (photoList.size < 6) {
                              val photo_range = 6 - photoList.size
                                if (photo_range < count ) {
                                    Toast.makeText(this, "$photo_range 개 사진까지 추가 가능합니다.",Toast.LENGTH_SHORT).show()
                                } else if (photo_range >= count) {
                                    for (i in 0 until count) {
                                        photoUri = data.clipData!!.getItemAt(i).uri
                                        imageFileName = ""
                                        imageFileName = photoUri?.lastPathSegment!!
                                        dataAdapter.addItem(photoUri)
                                        photoList.add(photoUri)
                                        filenameList.add(imageFileName)
                                        mViewDataBinding.photoCount.setText(dataAdapter.getSize().toString())
                                    }
                                }
                            } else {
                                Toast.makeText(this, "사진을 최대로 선택하셨습니다.",Toast.LENGTH_SHORT).show()
                            }
                        } else {    //단일 선택
                            if (photoList.size < 6) {
                                data?.data?.let { uri ->
                                    photoUri = data?.data!!
                                    imageFileName = ""
                                    imageFileName = photoUri?.lastPathSegment!!
                                    dataAdapter.addItem(photoUri)
                                    photoList.add(photoUri)
                                    filenameList.add(imageFileName)
                                    mViewDataBinding.photoCount.setText(dataAdapter.getSize().toString())
                                }
                            } else {
                                Toast.makeText(this, "사진을 최대로 선택하셨습니다.",Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }

    }

    private fun selectgallery() {
        var writePermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        var readPermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)

        if (writePermission == PackageManager.PERMISSION_DENIED || readPermission == PackageManager.PERMISSION_DENIED) { // 권한 없어서 요청
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                REQ_STORAGE_PERMISSION
            )
        } else {
            var intent = Intent(Intent.ACTION_PICK)
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            //intent.type = "image/*"
            startActivityForResult(intent, GALLERY_IMAGE)
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File? {


        val timeStamp = SimpleDateFormat("HHmmss").format(Date())
        // 이미지 파일 이름
        imageFileName = ""
        imageFileName = "estimate" + "_" + timeStamp

        // 이미지가 저장될 주소
        storageDir = File(
            this.getCacheDir(), "/Wearecompany/"
        )
        if (!storageDir.exists()) {
            Log.v("알림", "storageDir 존재 x$storageDir")
            storageDir.mkdirs()
        }
        Log.v("알림", "storageDir 존재함$storageDir")

        //빈 파일 생성
        storageDir = File(
            this.cacheDir, "/Wearecompany/$imageFileName"
        )

        imagePath = storageDir.absolutePath
        return storageDir
    }

    private fun selectCamera() {
        var permission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        if (permission != PackageManager.PERMISSION_GRANTED) { // 권한 없어서 요청
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                REQ_CAMERA_PERMISSION
            )
        } else { // 권한 있음
            var state = Environment.getExternalStorageState()
            if (TextUtils.equals(state, Environment.MEDIA_MOUNTED)) {
                var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE) //카메라 인텐트 생성
                intent.resolveActivity(this.packageManager)?.let {
                    var photoFile: File? = null
                    try {
                        photoFile = createImageFile()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                    photoFile?.let {
                        photoUri = FileProvider.getUriForFile(
                            this,
                            BuildConfig.APPLICATION_ID + ".provider",
                            photoFile
                        )
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                        startActivityForResult(intent, CAMERA_IMAGE)
                    }
                }
            }
        }
    }

    private fun imageReSize(uri: Uri, resize: Int, fileName: String?): File {

        var resizeBitmap: Bitmap? = null
        var resizedBitmap: Bitmap? = null
        var RotationBitmap: Bitmap? = null

        var options: BitmapFactory.Options = BitmapFactory.Options();
        try {
            BitmapFactory.decodeStream(
                this.getContentResolver().openInputStream(uri),
                null,
                options
            ); // 1번

            var width: Int = options.outWidth
            var height: Int = options.outHeight
            var dwidth: Int = options.outWidth
            var dheight: Int = options.outHeight
            var samplesize: Int = 1

            while (true) {//2번
                if (width / 2 < resize || height / 2 < resize)
                    break;
                width /= 2
                height /= 2
                samplesize *= 2
            }

            options.inSampleSize = samplesize;
            var bitmap: Bitmap? = BitmapFactory.decodeStream(
                this.getContentResolver().openInputStream(uri),
                null,
                options
            ) //3번

            width = options.outWidth
            height = options.outHeight

            if (bitmap != null) {
                resizeBitmap = bitmap
            }
            if (dwidth < dheight) {
                if (dwidth > 700) {
                    dwidth = 700
                    dheight = (dwidth.toFloat() / width.toFloat() * height).toInt()
                }
            } else if (dheight < dwidth) {
                if (dheight > 700) {
                    dheight = 700
                    dwidth = (dheight.toFloat() / height.toFloat() * width).toInt()
                }
            } else if (dheight == dwidth) {
                dwidth = 700
                dheight = 700
            }

            if (bitmap != null) {
                resizeBitmap = bitmap
            }

            var res: InputStream = contentResolver.openInputStream(uri)!!
            var exif: ExifInterface = ExifInterface(res)
            res.close()

            var orientation: Int = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )

            //사진 회전
            var rotateMatrix: Matrix = Matrix()
            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                rotateMatrix.postRotate(90f)
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                rotateMatrix.postRotate(180f)
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                rotateMatrix.postRotate(270f)
            }

            resizedBitmap = resizeBitmap?.let {
                Bitmap.createScaledBitmap(it, dwidth, dheight, true)
            }

            RotationBitmap = resizedBitmap?.let {
                Bitmap.createBitmap(
                    it,
                    0,
                    0,
                    it.width,
                    it.height,
                    rotateMatrix,
                    true
                )
            }

        } catch (e: FileNotFoundException) {
            e.printStackTrace();
        }

        val file = File(this.cacheDir, fileName)
        try {
            val outStream = ByteArrayOutputStream()

            RotationBitmap!!.compress(Bitmap.CompressFormat.PNG, 100, outStream)
            file.createNewFile()
            val fo = FileOutputStream(file)
            fo.write(outStream.toByteArray())
            // remember close de FileOutput
            fo.flush()
            fo.close()
        } catch (e: Exception) {
        }
        return file
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

}