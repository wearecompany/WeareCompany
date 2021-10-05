package com.weare.wearecompany.ui.bottommenu.estimate.progress.experthodel.progress

import android.content.Intent
import android.net.Uri
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.weare.wearecompany.BuildConfig.CHAT_URL
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.retrofit.bottomnav.estimate.progressManager
import com.weare.wearecompany.data.retrofit.bottomnav.estimate.refundManager
import com.weare.wearecompany.databinding.ActivityProgressTripBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.ui.bottommenu.estimate.progress.ProgressBottomRefundFragment
import com.weare.wearecompany.ui.bottommenu.estimate.refund.RefundDialog
import com.weare.wearecompany.ui.bottommenu.estimate.send.SendTagRecyclerViewAdapter
import com.weare.wearecompany.ui.chat.ChatActivity
import com.weare.wearecompany.ui.detail.model.ModelActivity
import com.weare.wearecompany.ui.detail.photo.PhotoActivity
import com.weare.wearecompany.ui.detail.trip.TripActivity
import com.weare.wearecompany.utils.ESTIMATE
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import java.text.DecimalFormat

class ProgressTripActivity:BaseActivity<ActivityProgressTripBinding>(
    R.layout.activity_progress_trip
),View.OnClickListener {

    private var reserve_idx: String = ""
    private var expert_idx: String = ""
    private var reviewcheck: Int = -1
    private var chatcheck: Int = -1
    private var type = -1
    lateinit var socket: Socket

    private val dec = DecimalFormat("#,###")

    private lateinit var taglist: ArrayList<String>

    private lateinit var tagAdapter: SendTagRecyclerViewAdapter

    override fun onCreate() {

        val toobar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toobar)
        val ActionBar = supportActionBar!!
        ActionBar.setDisplayHomeAsUpEnabled(true)
        ActionBar.setDisplayShowTitleEnabled(false)

        reserve_idx = intent.getStringExtra("reserve_idx").toString()
        type = intent.getIntExtra("type", 0)
        chatcheck = intent.getIntExtra("chatbool", 0)
        reviewcheck = intent.getIntExtra("review_check", 0)

        setup()
    }

    private fun setup() {

        mViewDataBinding.progressTripChat.setOnClickListener(this)
        mViewDataBinding.progressTripTopMenu.setOnClickListener(this)
        mViewDataBinding.progressTripExpertImage.setOnClickListener(this)

        if (chatcheck == 1) {
            mViewDataBinding.progressTripChat.visibility = View.GONE
        }
        if (reviewcheck == 1) {
            mViewDataBinding.progressTripTopMenu.visibility = View.GONE
        }

        when(type) {
            2 -> {
                progressManager.instance.expertPage(
                    reserve_idx,
                    completion = { responseStatus, data ->
                        when (responseStatus) {
                            ESTIMATE.OKAY -> {
                                type = data[0].expert_type
                                expert_idx = data[0].expert_idx

                                mViewDataBinding.progressTripExpertName.text = data[0].expert_name
                                mViewDataBinding.progressTripExpertPlace.text = data[0].expert_place
                                mViewDataBinding.progressTripExpertPrice.text = data[0].expert_price

                                var multiTransformation = MultiTransformation(CenterCrop(),RoundedCorners(20))

                                Glide.with(MyApplication.instance)
                                    .load(data[0].expert_image)
                                    .apply(RequestOptions.bitmapTransform(multiTransformation))
                                    .into(mViewDataBinding.progressTripExpertImage)

                                taglist = ArrayList<String>()
                                val tag = data[0].expert_category.split(",")
                                for (i in tag) {
                                    taglist.add(i)
                                }
                                tagAdapter = SendTagRecyclerViewAdapter(taglist)
                                mViewDataBinding.progressTripExpertCategoryRecyclerview.layoutManager = LinearLayoutManager(
                                    this,
                                    LinearLayoutManager.HORIZONTAL, false
                                )
                                mViewDataBinding.progressTripExpertCategoryRecyclerview.adapter = tagAdapter

                                mViewDataBinding.progressTripTid.text = data[0].reserve_tid
                                mViewDataBinding.progressTripBillMethod.text = data[0].bill_method
                                mViewDataBinding.progressTripBillDate.text = data[0].bill_date
                                mViewDataBinding.progressTripDt.text = data[0].reserve_dt
                                mViewDataBinding.progressTripTime.text = data[0].reserve_time.toString()
                                mViewDataBinding.progressTripTimeTerm.text = data[0].reserve_time_term
                                mViewDataBinding.progressTripHeadCount.text = data[0].reserve_headcount.toString()
                                mViewDataBinding.progressTripContents.text = data[0].reserve_contents

                                if (data[0].reserve_add_contents != "") {
                                    mViewDataBinding.progressTripAddContentsLayout.visibility = View.VISIBLE
                                    mViewDataBinding.progressTripAddContents.text = data[0].reserve_add_contents
                                }

                                mViewDataBinding.progressTripFinalPrice.text = dec.format(data[0].reserve_price)
                            }
                        }
                    })
            }
            3 -> {
                progressManager.instance.expertProgressOkPage(
                    reserve_idx,
                    completion = { responseStatus, data ->
                        when (responseStatus) {
                            ESTIMATE.OKAY -> {
                                type = data[0].expert_type
                                expert_idx = data[0].expert_idx

                                mViewDataBinding.progressTripExpertName.text = data[0].expert_name
                                mViewDataBinding.progressTripExpertPlace.text = data[0].expert_place
                                mViewDataBinding.progressTripExpertPrice.text = data[0].expert_price

                                var multiTransformation = MultiTransformation(CenterCrop(),RoundedCorners(20))

                                Glide.with(MyApplication.instance)
                                    .load(data[0].expert_image)
                                    .apply(RequestOptions.bitmapTransform(multiTransformation))
                                    .into(mViewDataBinding.progressTripExpertImage)

                                taglist = ArrayList<String>()
                                val tag = data[0].expert_category.split(",")
                                for (i in tag) {
                                    taglist.add(i)
                                }
                                tagAdapter = SendTagRecyclerViewAdapter(taglist)
                                mViewDataBinding.progressTripExpertCategoryRecyclerview.layoutManager = LinearLayoutManager(
                                    this,
                                    LinearLayoutManager.HORIZONTAL, false
                                )
                                mViewDataBinding.progressTripExpertCategoryRecyclerview.adapter = tagAdapter

                                mViewDataBinding.progressTripTid.text = data[0].reserve_tid
                                mViewDataBinding.progressTripBillMethod.text = data[0].bill_method
                                mViewDataBinding.progressTripBillDate.text = data[0].bill_date
                                mViewDataBinding.progressTripDt.text = data[0].reserve_dt
                                mViewDataBinding.progressTripTime.text = data[0].reserve_time.toString()
                                mViewDataBinding.progressTripTimeTerm.text = data[0].reserve_time_term
                                mViewDataBinding.progressTripHeadCount.text = data[0].reserve_headcount.toString()
                                mViewDataBinding.progressTripContents.text = data[0].reserve_contents

                                if (data[0].reserve_add_contents != "") {
                                    mViewDataBinding.progressTripAddContentsLayout.visibility = View.VISIBLE
                                    mViewDataBinding.progressTripAddContents.text = data[0].reserve_add_contents
                                }

                                mViewDataBinding.progressTripFinalPrice.text = dec.format(data[0].reserve_price)
                            }
                        }
                    })
            }
            4 -> {
                progressManager.instance.expertProgressReviewPage(
                    reserve_idx,
                    completion = { responseStatus, data ->
                        when (responseStatus) {
                            ESTIMATE.OKAY -> {
                                type = data[0].expert_type
                                expert_idx = data[0].expert_idx

                                mViewDataBinding.progressTripExpertName.text = data[0].expert_name
                                mViewDataBinding.progressTripExpertPlace.text = data[0].expert_place
                                mViewDataBinding.progressTripExpertPrice.text = data[0].expert_price

                                var multiTransformation = MultiTransformation(CenterCrop(),RoundedCorners(20))

                                Glide.with(MyApplication.instance)
                                    .load(data[0].expert_image)
                                    .apply(RequestOptions.bitmapTransform(multiTransformation))
                                    .into(mViewDataBinding.progressTripExpertImage)

                                taglist = ArrayList<String>()
                                val tag = data[0].expert_category.split(",")
                                for (i in tag) {
                                    taglist.add(i)
                                }
                                tagAdapter = SendTagRecyclerViewAdapter(taglist)
                                mViewDataBinding.progressTripExpertCategoryRecyclerview.layoutManager = LinearLayoutManager(
                                    this,
                                    LinearLayoutManager.HORIZONTAL, false
                                )
                                mViewDataBinding.progressTripExpertCategoryRecyclerview.adapter = tagAdapter

                                mViewDataBinding.progressTripTid.text = data[0].reserve_tid
                                mViewDataBinding.progressTripBillMethod.text = data[0].bill_method
                                mViewDataBinding.progressTripBillDate.text = data[0].bill_date
                                mViewDataBinding.progressTripDt.text = data[0].reserve_dt
                                mViewDataBinding.progressTripTime.text = data[0].reserve_time.toString()
                                mViewDataBinding.progressTripTimeTerm.text = data[0].reserve_time_term
                                mViewDataBinding.progressTripHeadCount.text = data[0].reserve_headcount.toString()
                                mViewDataBinding.progressTripContents.text = data[0].reserve_contents

                                if (data[0].reserve_add_contents != "") {
                                    mViewDataBinding.progressTripAddContentsLayout.visibility = View.VISIBLE
                                    mViewDataBinding.progressTripAddContents.text = data[0].reserve_add_contents
                                }

                                mViewDataBinding.progressTripFinalPrice.text = dec.format(data[0].reserve_price)
                            }
                        }
                    })
            }
        }
    }

    private fun dateemit() {
        // 소켓 서버 연결
        socket = IO.socket(CHAT_URL)
        socket.connect()

        val `object2` = JSONObject()
        `object2`.put("type", type)
        `object2`.put("reserve_idx", reserve_idx)
        `object2`.put("request_idx", "")
        `object2`.put("request_log_idx", "")

        socket?.emit("notiUpdate", object2)

        socket.disconnect()
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.progress_trip_expert_image -> {
                when(type) {
                    0 -> {
                        var newIntent = Intent(this, PhotoActivity::class.java)
                        newIntent.putExtra("expert_idx", expert_idx)
                        startActivity(newIntent)
                    }
                    1 -> {
                        var newIntent = Intent(this, ModelActivity::class.java)
                        newIntent.putExtra("expert_idx", expert_idx)
                        startActivity(newIntent)
                    }
                    2 -> {
                        var newIntent = Intent(this, TripActivity::class.java)
                        newIntent.putExtra("expert_idx", expert_idx)
                        startActivity(newIntent)
                    }
                }

            }
            R.id.progress_trip_top_menu -> {
                val refundFragment: ProgressBottomRefundFragment = ProgressBottomRefundFragment {
                    when (it) {
                        0 -> {
                            val refundDialog: RefundDialog = RefundDialog() {
                                refundManager.instance.refundreserve(
                                    reserve_idx,
                                    it,
                                    completion = { responseStatus ->
                                        when (responseStatus) {
                                            ESTIMATE.OKAY -> {

                                                dateemit()

                                                val intent = Intent()
                                                setResult(2001, intent)
                                                finish()
                                            }
                                        }
                                    })
                            }
                            refundDialog.show(supportFragmentManager, refundDialog.tag)
                        }
                    }
                }
                refundFragment.show(supportFragmentManager, refundFragment.tag)
            }

            R.id.progress_trip_chat -> {
                var urll = "https://pf.kakao.com/_xlQxdys/chat"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(urll)
                startActivity(intent)
            }
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
}