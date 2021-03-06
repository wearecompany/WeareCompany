package com.weare.wearecompany.ui.bottommenu.estimate.progress.experthodel.refund

import android.content.Intent
import android.graphics.Color
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
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.retrofit.bottomnav.estimate.progressManager
import com.weare.wearecompany.databinding.ActivityRefundTripBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.ui.bottommenu.estimate.send.SendTagRecyclerViewAdapter
import com.weare.wearecompany.ui.chat.ChatActivity
import com.weare.wearecompany.ui.detail.model.ModelActivity
import com.weare.wearecompany.ui.detail.photo.PhotoActivity
import com.weare.wearecompany.ui.detail.trip.TripActivity
import com.weare.wearecompany.utils.ESTIMATE
import java.text.DecimalFormat

class RefundTripActivity:BaseActivity<ActivityRefundTripBinding>(
    R.layout.activity_refund_trip
),View.OnClickListener {

    private var reserve_idx: String = ""
    private var expert_idx: String = ""
    private var type: Int = -1
    private var chatcheck: Int = -1

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

        setup()

    }

    private fun setup() {

        mViewDataBinding.refundTripChat.setOnClickListener(this)
        mViewDataBinding.refundTripExpertImage.setOnClickListener(this)
        //mViewDataBinding.progressTopManu.setOnClickListener(this)
        mViewDataBinding.refundTripTopManu.visibility = View.GONE
        if (chatcheck == 1) {
            mViewDataBinding.refundTripChat.visibility = View.GONE
        }

        when(type) {
            0 -> {
                progressManager.instance.expertRefundPage(
                    reserve_idx,
                    completion = { responseStatus, data ->
                        when (responseStatus) {
                            ESTIMATE.OKAY -> {
                                type = data[0].expert_type
                                expert_idx = data[0].expert_idx
                                if (data[0].refund_status == 1) {
                                    mViewDataBinding.refundTripBillDateTitle.text = "????????????/?????? "
                                }

                                mViewDataBinding.refundTripExpertName.text = data[0].expert_name
                                mViewDataBinding.refundTripExpertPlace.text = data[0].expert_place
                                mViewDataBinding.refundTripExpertPrice.text = data[0].expert_price

                                var multiTransformation = MultiTransformation(CenterCrop(),RoundedCorners(20))

                                Glide.with(MyApplication.instance)
                                    .load(data[0].expert_image)
                                    .apply(RequestOptions.bitmapTransform(multiTransformation))
                                    .into(mViewDataBinding.refundTripExpertImage)

                                taglist = ArrayList<String>()
                                val tag = data[0].expert_category.split(",")
                                for (i in tag) {
                                    taglist.add(i)
                                }
                                tagAdapter = SendTagRecyclerViewAdapter(taglist)
                                mViewDataBinding.refundTripExpertCategoryRecyclerview.layoutManager = LinearLayoutManager(
                                    this,
                                    LinearLayoutManager.HORIZONTAL, false
                                )
                                mViewDataBinding.refundTripExpertCategoryRecyclerview.adapter = tagAdapter

                                mViewDataBinding.refundTripTid.text = data[0].reserve_tid
                                mViewDataBinding.refundTripBillMethod.text = data[0].bill_method
                                mViewDataBinding.refundTripBillDate.text = data[0].bill_date
                                mViewDataBinding.refundTripDt.text = data[0].reserve_dt
                                mViewDataBinding.refundTripTime.text = data[0].reserve_time.toString()
                                mViewDataBinding.refundTripTimeTerm.text = data[0].reserve_time_term
                                mViewDataBinding.refundTripHeadcount.text =
                                    data[0].reserve_headcount.toString()

                                mViewDataBinding.refundTripContents.text = data[0].reserve_contents

                                if (data[0].reserve_add_contents != "") {
                                    mViewDataBinding.refundTripAddContentsLayout.visibility = View.VISIBLE
                                    mViewDataBinding.refundTripAddContents.text = data[0].reserve_add_contents
                                }

                                mViewDataBinding.refundTripFinalPrice.text = dec.format(data[0].reserve_price)
                            }
                        }
                    })
            }
            1 -> {
                progressManager.instance.expertRefundOkPage(
                    reserve_idx,
                    completion = { responseStatus, data ->
                        when (responseStatus) {
                            ESTIMATE.OKAY -> {
                                type = data[0].expert_type
                                expert_idx = data[0].expert_idx
                                if (data[0].refund_status == 2) {
                                    mViewDataBinding.refundTripTopManu.visibility = View.GONE
                                    mViewDataBinding.refundInfoTitle.text = "?????? ????????? ?????????????????????."
                                    mViewDataBinding.refundInfo1.text = "*??????????????? ????????? ??????, ?????? ?????? ????????? ?????????????????? ??????\n????????? ?????? ??? ????????????. ?????? ????????? ????????? ????????????\n??????????????? ????????????."
                                    mViewDataBinding.refundInfo2.visibility = View.VISIBLE
                                    mViewDataBinding.refundInfo2.text = "*??????????????? ????????? ??????, ???????????? ??????????????? ????????????\n????????? ???????????? 1-3??? ???????????????."
                                    mViewDataBinding.refundInfo1.setTextColor(Color.parseColor("#8276f4"))
                                    mViewDataBinding.refundTripBillDateTitle.text = "????????????/?????? "
                                    mViewDataBinding.refundTripMoneyLayout.visibility = View.VISIBLE
                                    mViewDataBinding.refundTripMoneyTitle.text = "????????????"
                                    mViewDataBinding.refundTripMoney.text = data[0].refund_money.toString()
                                }

                                mViewDataBinding.refundTripExpertName.text = data[0].expert_name
                                mViewDataBinding.refundTripExpertPlace.text = data[0].expert_place
                                mViewDataBinding.refundTripExpertPrice.text = data[0].expert_price

                                var multiTransformation = MultiTransformation(CenterCrop(),RoundedCorners(20))

                                Glide.with(MyApplication.instance)
                                    .load(data[0].expert_image)
                                    .apply(RequestOptions.bitmapTransform(multiTransformation))
                                    .into(mViewDataBinding.refundTripExpertImage)

                                taglist = ArrayList<String>()
                                val tag = data[0].expert_category.split(",")
                                for (i in tag) {
                                    taglist.add(i)
                                }
                                tagAdapter = SendTagRecyclerViewAdapter(taglist)
                                mViewDataBinding.refundTripExpertCategoryRecyclerview.layoutManager = LinearLayoutManager(
                                    this,
                                    LinearLayoutManager.HORIZONTAL, false
                                )
                                mViewDataBinding.refundTripExpertCategoryRecyclerview.adapter = tagAdapter

                                mViewDataBinding.refundTripTid.text = data[0].reserve_tid
                                mViewDataBinding.refundTripBillMethod.text = data[0].bill_method
                                mViewDataBinding.refundTripBillDate.text = data[0].bill_date
                                mViewDataBinding.refundTripDt.text = data[0].reserve_dt
                                mViewDataBinding.refundTripTime.text = data[0].reserve_time.toString()
                                mViewDataBinding.refundTripTimeTerm.text = data[0].reserve_time_term
                                mViewDataBinding.refundTripHeadcount.text =
                                    data[0].reserve_headcount.toString()

                                mViewDataBinding.refundTripContents.text = data[0].reserve_contents

                                if (data[0].reserve_add_contents != "") {
                                    mViewDataBinding.refundTripAddContentsLayout.visibility = View.VISIBLE
                                    mViewDataBinding.refundTripAddContents.text = data[0].reserve_add_contents
                                }

                                mViewDataBinding.refundTripFinalPrice.text = dec.format(data[0].reserve_price)
                            }
                        }
                    })
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.refund_trip_expert_image -> {
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
            R.id.refund_trip_chat -> {
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