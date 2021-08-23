package com.weare.wearecompany.ui.bottommenu.estimate.progress.experthodel.refund

import android.content.Intent
import android.graphics.Color
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
import com.weare.wearecompany.databinding.ActivityRefundModelBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.ui.bottommenu.estimate.send.SendTagRecyclerViewAdapter
import com.weare.wearecompany.ui.chat.ChatActivity
import com.weare.wearecompany.ui.detail.model.ModelActivity
import com.weare.wearecompany.ui.detail.photo.PhotoActivity
import com.weare.wearecompany.ui.detail.trip.TripActivity
import com.weare.wearecompany.utils.ESTIMATE
import java.text.DecimalFormat

class RefundModelActivity:BaseActivity<ActivityRefundModelBinding>(
    R.layout.activity_refund_model
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
        //type = intent.getIntExtra("type", 0)
        chatcheck = intent.getIntExtra("chatbool", 0)

        setup()
    }

    private fun setup() {

        mViewDataBinding.refundModelChat.setOnClickListener(this)
        mViewDataBinding.refundModelExpertImage.setOnClickListener(this)
        //mViewDataBinding.progressTopManu.setOnClickListener(this)
        mViewDataBinding.refundModelTopManu.visibility = View.GONE
        if (chatcheck == 1) {
            mViewDataBinding.refundModelChat.visibility = View.GONE
        }

        progressManager.instance.expertPage(
            reserve_idx,
            completion = { responseStatus, data ->
                when (responseStatus) {
                    ESTIMATE.OKAY -> {
                        type = data[0].expert_type
                        expert_idx = data[0].expert_idx
                        if (data[0].refund_status == 2) {
                            mViewDataBinding.refundModelTopManu.visibility = View.GONE
                            mViewDataBinding.refundInfoTitle.text = "환불 접수가 완료되었습니다."
                            mViewDataBinding.refundInfo1.text = "*신용카드로 결제한 경우, 실제 환불 일자는 신용카드사에 따라\n차이가 있을 수 있습니다. 보다 정확한 사항은 카드사로\n문의하시기 바랍니다."
                            mViewDataBinding.refundInfo2.visibility = View.VISIBLE
                            mViewDataBinding.refundModelBillDateTitle.text = "환불일자/시간 "
                            mViewDataBinding.refundModelMoneyLayout.visibility = View.VISIBLE
                            mViewDataBinding.refundModelMoneyTitle.text = "환불금액"
                            mViewDataBinding.refundModelMoney.text = data[0].refund_money.toString()
                        }

                        mViewDataBinding.refundModelExpertName.text = data[0].expert_name
                        mViewDataBinding.refundModelExpertPlace.text = data[0].expert_place
                        mViewDataBinding.refundModelExpertPrice.text = data[0].expert_price

                        var multiTransformation = MultiTransformation(CenterCrop(),RoundedCorners(20))

                        Glide.with(MyApplication.instance)
                            .load(data[0].expert_image)
                            .apply(RequestOptions.bitmapTransform(multiTransformation))
                            .into(mViewDataBinding.refundModelExpertImage)

                        taglist = ArrayList<String>()
                        val tag = data[0].expert_category.split(",")
                        for (i in tag) {
                            taglist.add(i)
                        }
                        tagAdapter = SendTagRecyclerViewAdapter(taglist)
                        mViewDataBinding.refundModelExpertCategoryRecyclerview.layoutManager = LinearLayoutManager(
                            this,
                            LinearLayoutManager.HORIZONTAL, false
                        )
                        mViewDataBinding.refundModelExpertCategoryRecyclerview.adapter = tagAdapter

                        mViewDataBinding.refundModelTid.text = data[0].reserve_tid
                        mViewDataBinding.refundModelBillMethod.text = data[0].bill_method
                        mViewDataBinding.refundModelBillDate.text = data[0].bill_date
                        mViewDataBinding.refundModelDt.text = data[0].reserve_dt
                        mViewDataBinding.refundModelTime.text = data[0].reserve_time.toString()
                        mViewDataBinding.refundModelTimeTerm.text = data[0].reserve_time_term
                            mViewDataBinding.refundModelContents.text = data[0].reserve_contents
                        if (data[0].reserve_add_contents != "") {
                            mViewDataBinding.refundModelAddContentsLayout.visibility = View.VISIBLE
                            mViewDataBinding.refundModelAddContents.text = data[0].reserve_add_contents
                        }
                        mViewDataBinding.refundModelFinalPrice.text = dec.format(data[0].reserve_price)
                    }
                }
            })
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.refund_model_expert_image -> {
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
            R.id.refund_model_chat -> {
                val newIntent = Intent(this, ChatActivity::class.java)
                newIntent.putExtra("type",0)
                newIntent.putExtra("Entrytype",0)
                newIntent.putExtra("reserve_idx",reserve_idx)
                startActivity(newIntent)
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