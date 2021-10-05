package com.weare.wearecompany.ui.detail.model

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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.dynamiclinks.ktx.*
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.link.LinkClient
import com.kakao.sdk.template.model.*
import com.weare.wearecompany.BuildConfig
import com.weare.wearecompany.R
import com.weare.wearecompany.data.model.dateil.dateilModel
import com.weare.wearecompany.data.retrofit.dateil.modelDateilManager
import com.weare.wearecompany.databinding.ActivityModelBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.utils.RESPONSE_STATUS
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.data.hotpick.data.review
import com.weare.wearecompany.data.retrofit.bottomnav.main.MainManager
import com.weare.wearecompany.data.retrofit.bottomnav.mypage.mypageManager
import com.weare.wearecompany.ui.bottommenu.estimate.receive.payment.PhotoPaymentActivity
import com.weare.wearecompany.ui.bottommenu.main.weekly.tesdialog
import com.weare.wearecompany.ui.chat.ChatActivity
import com.weare.wearecompany.ui.chat.detail.DetailChatActivity
import com.weare.wearecompany.ui.detail.model.reservation.ReservationModelActivity
import com.weare.wearecompany.ui.detail.model.reservation.ReservationModelDialog
import com.weare.wearecompany.ui.detail.photo.PhotoReviewRecyclerViewAdapter
import com.weare.wearecompany.ui.detail.studio.DatailSharingDialog
import com.weare.wearecompany.ui.detail.studio.reservation.ReservationStudioActivity
import com.weare.wearecompany.utils.BitmapUtils
import com.weare.wearecompany.utils.Constants
import com.weare.wearecompany.utils.LIKE
import okhttp3.RequestBody
import java.io.File
import java.text.DecimalFormat

class ModelActivity: BaseActivity<ActivityModelBinding>(
        R.layout.activity_model
), View.OnClickListener {

    private var dateList = ArrayList<dateilModel>()
    private var like: Boolean = false

    private var user_idx = ""
    private var model_Idx = ""
    lateinit var fileuri: Uri
    lateinit var serveruri: String
    var fileName: String? = null
    private lateinit var sharebitmap: Bitmap
    private lateinit var sharefile: File
    private lateinit var dynamicLinkUri: Uri

    private val dec = DecimalFormat("#,###")

    private lateinit var dateAdapter : ModelPagerAdapter
    private lateinit var reviewAdapter : ModelReviewRecyclerViewAdapter

    private lateinit var reviewlist:ArrayList<review>
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
        model_Idx = intent.getStringExtra("expert_idx").toString()
        // 비동기 통신(요청과 결과가 별개로 작용)
        modelDateilManager.instance.data(
            user_idx,
            model_Idx,
                completion = {responseStatus, date ->

                    when (responseStatus) {
                        RESPONSE_STATUS.OKAY -> {

                            if (responseStatus != null) {
                                dateList = date
                                dateAdapter = ModelPagerAdapter(this,dateList)
                                mViewDataBinding.modelViewPager.adapter = dateAdapter
                                mViewDataBinding.modelWormDotsIndicator.setViewPager(mViewDataBinding.modelViewPager)

                                setup(dateList)
                            }
                        }

                        RESPONSE_STATUS.FAIL -> {

                        }
                    }
                }
        )
    }

    fun setup(date: ArrayList<dateilModel>) {

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

        mViewDataBinding.modelDatailCategory.setText(date[0].sub_category)
        mViewDataBinding.modelDatailTitle.setText(date[0].title)
        mViewDataBinding.modelDatailAddress.setText(date[0].address)
        mViewDataBinding.sharing.setOnClickListener(this)
        mViewDataBinding.like.setOnClickListener(this)
        mViewDataBinding.modelReservation.setOnClickListener(this)
        mViewDataBinding.detailModelChat.setOnClickListener(this)

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
                .into(mViewDataBinding.modelDatailThumbnail)
        }
        mViewDataBinding.modelDatailName.setText(date[0].name)
        mViewDataBinding.modelDatailPrice.text = dec.format(date[0].price.toInt())
        mViewDataBinding.modelDatailTall.setText(date[0].tall)
        mViewDataBinding.modelDatailFeet.setText(date[0].feet)
        mViewDataBinding.modelDatailTopSize.setText(date[0].top_size)
        mViewDataBinding.modelDatailBottomSize.setText(date[0].bottom_size)
        mViewDataBinding.modelDatailInfo.setText(date[0].info)
        mViewDataBinding.modelDatailCareer.setText(date[0].career)
        mViewDataBinding.modelDatailRule.setText(date[0].rule)


        if (date[0].review.size != 0) {
            mViewDataBinding.reviewLayout.visibility = View.VISIBLE
            reviewlist = date[0].review
            mViewDataBinding.gradeAvr.text = date[0].review[0].grade.toString()

            reviewAdapter = ModelReviewRecyclerViewAdapter(reviewlist)

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
        when(v?.id) {
            R.id.like -> {
                if (MyApplication.prefs.getString("user_idx", "") != "") {
                    if (like) {
                        mViewDataBinding.likeImage.setImageResource(R.drawable.like_off)
                        like = false
                        mypageManager.instance.like_off(
                            2,
                            model_Idx,
                            user_idx,
                            completion = { responseStatus ->
                                when (responseStatus) {
                                    LIKE.OKAY -> {
                                    }
                                }
                            })

                    } else {
                        mypageManager.instance.like_on(
                            2,
                            model_Idx,
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
                            val templateId = "52477"
                            val longId = templateId.toLong()
                            val map: HashMap<String, String> = HashMap()
                            map.put("image_one",dateList[0].images[0])
                            map.put("user_image",dateList[0].thumbnail)
                            map.put("user_nickname",dateList[0].name)
                            map.put("user_title",dateList[0].sub_category)
                            map.put("user_content",dateList[0].title)
                            map.put("user_like",dateList[0].like.toString())
                            map.put("item_idx",model_Idx)
                            map.put("item_type",2.toString())

                            if (LinkClient.instance.isKakaoLinkAvailable(this)) {
                                LinkClient.instance.customTemplate(this,longId,map) { linkResult, error ->
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
                            /*val params = FeedTemplate(
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
                                        "앱으로 보기",
                                        Link(
                                            androidExecParams = mapOf(
                                                "user_idx" to MyApplication.prefs.getString(
                                                    "user_idx",
                                                    ""
                                                ), "item_idx" to model_Idx, "item_type" to "2"
                                            ),
                                            iosExecParams = mapOf( "user_idx" to MyApplication.prefs.getString(
                                                "user_idx",
                                                ""
                                            ), "item_idx" to model_Idx, "item_type" to "2")
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
                            }*/
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
            R.id.model_reservation -> {

                if (MyApplication.prefs.getString("user_idx", "") != "") {
                    MainManager.instance.certcheck(user_idx,complation = {responseStatus,check ->
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
                                        var newIntent = Intent(this@ModelActivity, ReservationModelActivity::class.java)
                                        newIntent.putExtra("user_idx",user_idx)
                                        newIntent.putExtra("expert_user_idx",dateList[0].expert_user_idx)
                                        newIntent.putExtra("expert_idx",dateList[0].idx)
                                        newIntent.putExtra("expert_type",1)
                                        newIntent.putExtra("price",dateList[0].price)
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
            R.id.detail_model_chat -> {
                val newIntent = Intent(this, DetailChatActivity::class.java)
                newIntent.putExtra("expert_type", "2")
                newIntent.putExtra("expert_idx", dateList[0].idx)
                newIntent.putExtra("expert_user_idx", dateList[0].expert_user_idx)
                startActivityForResult(newIntent,3001)
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
                iosParameters("kr.co.wearecompany.wearecompany") {
                    minimumVersion = "14.2"
                }
                socialMetaTagParameters {
                    title = dateList[0].name + " · " + "모델님"
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