package com.weare.wearecompany.ui.detail.photo

import android.animation.Animator
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.dynamiclinks.ktx.*
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.link.LinkClient
import com.kakao.sdk.template.model.*
import com.weare.wearecompany.BuildConfig
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.hotpick.data.review
import com.weare.wearecompany.data.photo.dateil.list.date.dateilPhoto
import com.weare.wearecompany.data.retrofit.bottomnav.main.MainManager
import com.weare.wearecompany.data.retrofit.bottomnav.mypage.mypageManager
import com.weare.wearecompany.data.retrofit.dateil.photoDateilManager
import com.weare.wearecompany.databinding.ActivityPhotoBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.ui.bottommenu.estimate.receive.payment.PhotoPaymentActivity
import com.weare.wearecompany.ui.bottommenu.main.weekly.tesdialog
import com.weare.wearecompany.ui.chat.detail.DetailChatActivity
import com.weare.wearecompany.ui.detail.photo.reservation.ReservationPhotoActivity
import com.weare.wearecompany.ui.detail.studio.DatailSharingDialog
import com.weare.wearecompany.utils.BitmapUtils
import com.weare.wearecompany.utils.Constants
import com.weare.wearecompany.utils.LIKE
import com.weare.wearecompany.utils.RESPONSE_STATUS
import java.io.File
import java.text.DecimalFormat

class PhotoActivity : BaseActivity<ActivityPhotoBinding>(
    R.layout.activity_photo
), View.OnClickListener {

    private var dateList = ArrayList<dateilPhoto>()
    private var user_idx = ""
    private var like: Boolean = false


    private var photo_Idx = ""
    lateinit var fileuri: Uri
    lateinit var serveruri: String
    var fileName: String? = null
    private lateinit var sharebitmap: Bitmap
    private lateinit var sharefile: File
    private lateinit var dynamicLinkUri: Uri
    private lateinit var reviewlist: ArrayList<review>

    private val dec = DecimalFormat("#,###")

    private lateinit var dateAdapter: PhotoPagerAdapter
    private lateinit var reviewAdapter: PhotoReviewRecyclerViewAdapter

    //private var dataList =
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
        photo_Idx = intent.getStringExtra("expert_idx").toString()
        // 비동기 통신(요청과 결과가 별개로 작용)
        photoDateilManager.instance.date(
            user_idx,
            photo_Idx,
            completion = { responseStatus, date ->

                when (responseStatus) {
                    RESPONSE_STATUS.OKAY -> {

                        if (responseStatus != null) {
                            dateList = date
                            dateAdapter = PhotoPagerAdapter(this, dateList)
                            mViewDataBinding.photoViewPager.adapter = dateAdapter
                            mViewDataBinding.photoWormDotsIndicator.setViewPager(mViewDataBinding.photoViewPager)

                            setup(date)
                        }
                    }

                    RESPONSE_STATUS.FAIL -> {

                    }
                }
            }
        )
    }

    fun setup(date: ArrayList<dateilPhoto>) {

        val thread = Thread(Runnable {
            fileuri = Uri.parse(date[0].images[0])
            serveruri = date[0].images[0]
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
        mViewDataBinding.photoDatailCategory.setText(date[0].sub_category)
        mViewDataBinding.photoDatailTitle.setText(date[0].title)
        mViewDataBinding.photoDatailAddress.setText(date[0].address)
        mViewDataBinding.sharing.setOnClickListener(this)
        mViewDataBinding.like.setOnClickListener(this)
        mViewDataBinding.photoReservation.setOnClickListener(this)
        mViewDataBinding.datailPhotoChat.setOnClickListener(this)

        like = date[0].like_status
        if (like) {
            mViewDataBinding.likeImage.setImageResource(R.drawable.like_on)
            like = true
        } else {
            mViewDataBinding.likeImage.setImageResource(R.drawable.like_off)
        }
        if (date[0].thumbnail != "") {
            var multiTransformation = MultiTransformation(CenterCrop(), RoundedCorners(20))
            Glide.with(MyApplication.instance)
                .load(date[0].thumbnail)
                .apply(RequestOptions.bitmapTransform(multiTransformation))
                .into(mViewDataBinding.photoDatailThumbnail)
        }
        mViewDataBinding.photoDatailName.setText(date[0].name)
        mViewDataBinding.photoDatailPrice.text = dec.format(date[0].price.toInt())
        mViewDataBinding.photoDatailInfo.setText(date[0].info)
        mViewDataBinding.photoDatailCareer.setText(date[0].career)
        mViewDataBinding.photoDatailRule.setText(date[0].rule)

        if (date[0].review.size != 0) {
            mViewDataBinding.reviewLayout.visibility = View.VISIBLE
            reviewlist = date[0].review

            reviewAdapter = PhotoReviewRecyclerViewAdapter(reviewlist)

            mViewDataBinding.reviewRecyclerview.layoutManager = LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL, false
            )
            mViewDataBinding.reviewRecyclerview.adapter = reviewAdapter
        } else {

        }
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

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.like -> {
                if (MyApplication.prefs.getString("user_idx", "") != "") {
                    if (like) {
                        mViewDataBinding.likeImage.setImageResource(R.drawable.like_off)
                        like = false
                        mypageManager.instance.like_off(
                            1,
                            photo_Idx,
                            user_idx,
                            completion = { responseStatus ->
                                when (responseStatus) {
                                    LIKE.OKAY -> {
                                    }
                                }
                            })

                    } else {
                        mypageManager.instance.like_on(
                            1,
                            photo_Idx,
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
                    Toast.makeText(this, "로그인후 이용 가능합니다.", Toast.LENGTH_SHORT).show()
                }

            }
            R.id.sharing -> {
                val dialog: DatailSharingDialog = DatailSharingDialog() {
                    when (it) {
                        0 -> {
                            val params = FeedTemplate(
                                content = Content(
                                    title = dateList[0].name,
                                    description = dateList[0].title,
                                    imageUrl = dateList[0].images[0],
                                    link = Link(
                                        webUrl = "https://developers.kakao.com",
                                        mobileWebUrl = "https://developers.kakao.com"
                                    )
                                ),
                                social = Social(
                                    likeCount = dateList[0].like
                                ),
                                buttons = listOf(
                                    Button(
                                        "웹으로 보기",
                                        Link(
                                            webUrl = "https://developers.kakao.com",
                                            mobileWebUrl = "https://play.google.com/store/apps/details?id=com.weare.wearecompany"
                                        )
                                    ),
                                    Button(
                                        "앱으로 보기",
                                        Link(
                                            androidExecParams = mapOf(
                                                "user_idx" to MyApplication.prefs.getString(
                                                    "user_idx",
                                                    ""
                                                ), "item_idx" to photo_Idx, "item_type" to "1"
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
            R.id.photo_reservation -> {

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
                                        var newIntent = Intent(this@PhotoActivity, ReservationPhotoActivity::class.java)
                                        newIntent.putExtra("user_idx", user_idx)
                                        newIntent.putExtra("expert_user_idx", dateList[0].expert_user_idx)
                                        newIntent.putExtra("expert_idx", dateList[0].idx)
                                        newIntent.putExtra("expert_type", 0)
                                        newIntent.putExtra("price", dateList[0].price)
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
            R.id.datail_photo_chat -> {
                val newIntent = Intent(this, DetailChatActivity::class.java)
                newIntent.putExtra("expert_type", "1")
                newIntent.putExtra("expert_idx", dateList[0].idx)
                newIntent.putExtra("expert_user_idx", dateList[0].expert_user_idx)
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
                    title = dateList[0].name + " · " + "포토그래퍼님"
                    description = dateList[0].title
                    imageUrl = Uri.parse(dateList[0].images[0])
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


}