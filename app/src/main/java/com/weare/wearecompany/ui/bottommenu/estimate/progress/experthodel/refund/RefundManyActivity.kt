package com.weare.wearecompany.ui.bottommenu.estimate.progress.experthodel.refund

import android.content.Intent
import android.graphics.Color
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.bottomnav.estimate.progress.data.ProgressManyPage
import com.weare.wearecompany.data.retrofit.bottomnav.estimate.progressManager
import com.weare.wearecompany.databinding.ActivityRefundManyBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.ui.bottommenu.estimate.send.SendTagRecyclerViewAdapter
import com.weare.wearecompany.ui.chat.ChatActivity
import com.weare.wearecompany.utils.ESTIMATE
import java.text.DecimalFormat

class RefundManyActivity : BaseActivity<ActivityRefundManyBinding>(
    R.layout.activity_refund_many
), View.OnClickListener {

    private var request_idx:String = ""
    private var request_log_idx:String = ""
    private var chatcheck: Int = -1
    private var reviewcheck: Int = -1

    private lateinit var dataAdapter : RefundManyThumbnailListRecyclerViewAdapter

    private lateinit var taglist: ArrayList<String>
    private lateinit var tagAdapter: SendTagRecyclerViewAdapter

    private val dec = DecimalFormat("#,###")

    override fun onCreate() {

        val toobar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toobar)
        val ActionBar = supportActionBar!!
        ActionBar.setDisplayHomeAsUpEnabled(true)
        ActionBar.setDisplayShowTitleEnabled(false)

        request_idx = intent.getStringExtra("request_idx").toString()
        request_log_idx = intent.getStringExtra("log_idx").toString()
        chatcheck = intent.getIntExtra("chatbool", 0)
        reviewcheck = intent.getIntExtra("review_check", 0)

        setup()
    }

    private fun setup() {

        mViewDataBinding.progressChat.setOnClickListener(this)

        if (reviewcheck == 1) {
            mViewDataBinding.refundTopManu.visibility = View.GONE
        }

        progressManager.instance.manyPage(
            request_idx,request_log_idx,
            completion = { responseStatus, data ->
                when (responseStatus) {
                    ESTIMATE.OKAY -> {

                        if (data[0].refund_status == 2) {
                            mViewDataBinding.refundTopManu.visibility = View.GONE
                            mViewDataBinding.refundInfoTitle.text = "환불 접수가 완료되었습니다."
                            mViewDataBinding.refundInfo1.text = "*신용카드로 결제한 경우, 실제 환불 일자는 신용카드사에 따라\n차이가 있을 수 있습니다. 보다 정확한 사항은 카드사로\n문의하시기 바랍니다."
                            mViewDataBinding.refundInfo2.visibility = View.VISIBLE
                            mViewDataBinding.refundInfo2.text = "*계좌이체로 구매한 겨우, 지불하신 출금계좌로 입금되며\n영업일 기준으로 1-3일 소요됩니다."
                            mViewDataBinding.refundInfo1.setTextColor(Color.parseColor("#8276f4"))
                            mViewDataBinding.estimateShopBillDateTitle.text = "환불일자/시간 "
                            mViewDataBinding.refundLayout.visibility = View.VISIBLE
                            mViewDataBinding.refundMoneyTitle.text = "환불금액"
                            mViewDataBinding.refundMoney.text = data[0].refund_money.toString()

                        }

                        mViewDataBinding.reserveTid.text = data[0].reserve_tid
                        mViewDataBinding.billMethod.text = data[0].bill_method
                        mViewDataBinding.billDate.text = data[0].bill_date
                        mViewDataBinding.price.text = dec.format(data[0].price)

                        mViewDataBinding.refundManyExpertName.text = data[0].expert_name
                        mViewDataBinding.refundManyExpertPlace.text = data[0].expert_place
                        mViewDataBinding.refundManyExpertPrice.text = data[0].expert_price

                        taglist = ArrayList<String>()
                        taglist.add(data[0].expert_type)
                        taglist.add(data[0].expert_category)

                        tagAdapter = SendTagRecyclerViewAdapter(taglist)
                        mViewDataBinding.refundManyCategoryRecyclerview.layoutManager = LinearLayoutManager(
                            this,
                            LinearLayoutManager.HORIZONTAL, false
                        )
                        mViewDataBinding.refundManyCategoryRecyclerview.adapter = tagAdapter

                        var multiTransformation = MultiTransformation(CenterCrop(), RoundedCorners(20))

                        if (data[0].expert_image != "") {
                            Glide.with(MyApplication.instance)
                                .load(data[0].expert_image)
                                .apply(RequestOptions.bitmapTransform(multiTransformation))
                                .into(mViewDataBinding.refundManyExpertImage)
                        } else {
                            mViewDataBinding.refundManyExpertImage.setImageResource(R.drawable.mypage_user_not_image)
                        }

                        if (data[0].thumbnail.size != 0) {
                            mViewDataBinding.userThumbnailListLayout.visibility = View.VISIBLE
                            dataAdapter = RefundManyThumbnailListRecyclerViewAdapter(data)

                            mViewDataBinding.userThumbnailList.layoutManager = LinearLayoutManager(
                                this,
                                LinearLayoutManager.HORIZONTAL, false
                            )
                            mViewDataBinding.userThumbnailList.adapter = dataAdapter
                        }

                        mViewDataBinding.datetime.text = data[0].datetime
                        mViewDataBinding.requestContents.text = data[0].request_contents
                        mViewDataBinding.responseContents.text = data[0].response_contents

                        if (chatcheck == 1) {
                            mViewDataBinding.progressChat.visibility = View.GONE
                        }
                    }
                }
            })
    }

    override fun onClick(v: View?) {
       when(v?.id) {
           R.id.refund_top_manu -> {
               val newIntent = Intent(this, ChatActivity::class.java)
               newIntent.putExtra("request_idx",request_idx)
               newIntent.putExtra("log_idx",request_log_idx)
               newIntent.putExtra("Entrytype",0)
               newIntent.putExtra("type",1)
               startActivity(newIntent)
           }
           R.id.progress_chat -> {
               val newIntent = Intent(this, ChatActivity::class.java)
               newIntent.putExtra("type",1)
               newIntent.putExtra("Entrytype",0)
               newIntent.putExtra("request_idx",request_idx)
               newIntent.putExtra("log_idx",request_log_idx)
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