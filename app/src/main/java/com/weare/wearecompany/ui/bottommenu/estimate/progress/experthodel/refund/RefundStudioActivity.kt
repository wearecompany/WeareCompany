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
import com.weare.wearecompany.databinding.ActivityRefundStudioBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.ui.bottommenu.estimate.send.SendTagRecyclerViewAdapter
import com.weare.wearecompany.ui.chat.ChatActivity
import com.weare.wearecompany.ui.detail.DatailActivity
import com.weare.wearecompany.utils.ESTIMATE
import java.text.DecimalFormat

class RefundStudioActivity : BaseActivity<ActivityRefundStudioBinding>(
    R.layout.activity_refund_studio
), View.OnClickListener {

    private var reserve_idx: String = ""
    private var expert_idx: String = ""
    private var chatcheck: Int = -1
    private var type = -1

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

        mViewDataBinding.progressChat.setOnClickListener(this)
        mViewDataBinding.refundStudioExpertInfoLayout.setOnClickListener(this)
        //mViewDataBinding.progressTopManu.setOnClickListener(this)
        mViewDataBinding.progressTopManu.visibility = View.GONE

        if (chatcheck == 1) {
            mViewDataBinding.progressChat.visibility = View.GONE
        }

        when(type) {
            0 -> {
                progressManager.instance.studioRefundPage(
                    reserve_idx,
                    completion = { responseStatus, data ->
                        when (responseStatus) {
                            ESTIMATE.OKAY -> {
                                expert_idx = data[0].expert_idx
                                if (data[0].refund_status == 1) {
                                    mViewDataBinding.estimateShopBillDateTitle.text = "????????????/?????? "
                                }

                                mViewDataBinding.refundStudioName.text = data[0].expert_name
                                mViewDataBinding.refundStudioExpertUserName.text = data[0].expert_user_name
                                mViewDataBinding.refundStudioRoom.text = data[0].room_name

                                var multiTransformation = MultiTransformation(CenterCrop(),RoundedCorners(20))

                                Glide.with(MyApplication.instance)
                                    .load(data[0].room_image)
                                    .apply(RequestOptions.bitmapTransform(multiTransformation))
                                    .into(mViewDataBinding.refundStudioRoomImage)

                                taglist = ArrayList<String>()
                                val tag = data[0].expert_category.split(",")
                                for (i in tag) {
                                    taglist.add(i)
                                }
                                tagAdapter = SendTagRecyclerViewAdapter(taglist)
                                mViewDataBinding.refundStudioCategoryRecyclerview.layoutManager = LinearLayoutManager(
                                    this,
                                    LinearLayoutManager.HORIZONTAL, false
                                )
                                mViewDataBinding.refundStudioCategoryRecyclerview.adapter = tagAdapter

                                mViewDataBinding.reserveTid.text = data[0].reserve_tid
                                mViewDataBinding.billMethod.text = data[0].bill_method
                                mViewDataBinding.billDate.text = data[0].bill_date
                                mViewDataBinding.reserveDt.text = data[0].reserve_dt
                                mViewDataBinding.reserveTime.text = data[0].reserve_time.toString()
                                mViewDataBinding.reserveTimeTerm.text = data[0].reserve_time_term
                                if (data[0].reserve_contents != "") {
                                    mViewDataBinding.reserveContents.text = data[0].reserve_contents
                                }
                                if (data[0].reserve_add_contents != "") {
                                    mViewDataBinding.reserveAddContentsLayout.visibility = View.VISIBLE
                                    mViewDataBinding.reserveAddContents.text = data[0].reserve_add_contents
                                }


                                mViewDataBinding.reserveFinalPrice.text =
                                    dec.format(data[0].reserve_price)
                            }
                        }
                    })
            }
            1 -> {
                progressManager.instance.studioRefundOkPage(
                    reserve_idx,
                    completion = { responseStatus, data ->
                        when (responseStatus) {
                            ESTIMATE.OKAY -> {
                                expert_idx = data[0].expert_idx
                                if (data[0].refund_status == 2) {
                                    mViewDataBinding.progressTopManu.visibility = View.GONE
                                    mViewDataBinding.refundInfoTitle.text = "?????? ????????? ?????????????????????."
                                    mViewDataBinding.refundInfo1.text = "*??????????????? ????????? ??????, ?????? ?????? ????????? ?????????????????? ??????\n????????? ?????? ??? ????????????. ?????? ????????? ????????? ????????????\n??????????????? ????????????."
                                    mViewDataBinding.refundInfo2.visibility = View.VISIBLE
                                    mViewDataBinding.refundInfo2.text = "*??????????????? ????????? ??????, ???????????? ??????????????? ????????????\n????????? ???????????? 1-3??? ???????????????."
                                    mViewDataBinding.refundInfo1.setTextColor(Color.parseColor("#8276f4"))
                                    mViewDataBinding.estimateShopBillDateTitle.text = "????????????/?????? "
                                    mViewDataBinding.refundLayout.visibility = View.VISIBLE
                                    mViewDataBinding.refundMoneyTitle.text = "????????????"
                                    mViewDataBinding.refundMoney.text = dec.format(data[0].refund_money)
                                }

                                mViewDataBinding.refundStudioName.text = data[0].expert_name
                                mViewDataBinding.refundStudioExpertUserName.text = data[0].expert_user_name
                                mViewDataBinding.refundStudioRoom.text = data[0].room_name

                                var multiTransformation = MultiTransformation(CenterCrop(),RoundedCorners(20))

                                Glide.with(MyApplication.instance)
                                    .load(data[0].room_image)
                                    .apply(RequestOptions.bitmapTransform(multiTransformation))
                                    .into(mViewDataBinding.refundStudioRoomImage)

                                taglist = ArrayList<String>()
                                val tag = data[0].expert_category.split(",")
                                for (i in tag) {
                                    taglist.add(i)
                                }
                                tagAdapter = SendTagRecyclerViewAdapter(taglist)
                                mViewDataBinding.refundStudioCategoryRecyclerview.layoutManager = LinearLayoutManager(
                                    this,
                                    LinearLayoutManager.HORIZONTAL, false
                                )
                                mViewDataBinding.refundStudioCategoryRecyclerview.adapter = tagAdapter

                                mViewDataBinding.reserveTid.text = data[0].reserve_tid
                                mViewDataBinding.billMethod.text = data[0].bill_method
                                mViewDataBinding.billDate.text = data[0].bill_date
                                mViewDataBinding.reserveDt.text = data[0].reserve_dt
                                mViewDataBinding.reserveTime.text = data[0].reserve_time.toString()
                                mViewDataBinding.reserveTimeTerm.text = data[0].reserve_time_term
                                if (data[0].reserve_contents != "") {
                                    mViewDataBinding.reserveContents.text = data[0].reserve_contents
                                }
                                if (data[0].reserve_add_contents != "") {
                                    mViewDataBinding.reserveAddContentsLayout.visibility = View.VISIBLE
                                    mViewDataBinding.reserveAddContents.text = data[0].reserve_add_contents
                                }


                                mViewDataBinding.reserveFinalPrice.text =
                                    dec.format(data[0].reserve_price)
                            }
                        }
                    })
            }
        }

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.refund_studio_expert_info_layout -> {
                var newIntent = Intent(this, DatailActivity::class.java)
                newIntent.putExtra("idx", expert_idx)
                startActivity(newIntent)
            }
            R.id.progress_chat -> {
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