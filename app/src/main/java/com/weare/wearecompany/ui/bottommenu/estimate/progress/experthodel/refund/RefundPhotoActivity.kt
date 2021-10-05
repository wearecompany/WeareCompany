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
import com.weare.wearecompany.databinding.ActivityRefundPhotoBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.ui.bottommenu.estimate.send.SendTagRecyclerViewAdapter
import com.weare.wearecompany.ui.chat.ChatActivity
import com.weare.wearecompany.ui.detail.model.ModelActivity
import com.weare.wearecompany.ui.detail.photo.PhotoActivity
import com.weare.wearecompany.ui.detail.trip.TripActivity
import com.weare.wearecompany.utils.ESTIMATE
import java.text.DecimalFormat

class RefundPhotoActivity:BaseActivity<ActivityRefundPhotoBinding>(
    R.layout.activity_refund_photo
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

        mViewDataBinding.refundPhotoChat.setOnClickListener(this)
        mViewDataBinding.refundPhotoExpertImage.setOnClickListener(this)
        //mViewDataBinding.progressTopManu.setOnClickListener(this)
        mViewDataBinding.refundPhotoTopManu.visibility = View.GONE
        if (chatcheck == 1) {
            mViewDataBinding.refundPhotoChat.visibility = View.GONE
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
                                    mViewDataBinding.refundPhotoBillDateTitle.text = "환불일자/시간 "
                                }
                                mViewDataBinding.refundPhotoExpertName.text = data[0].expert_name
                                mViewDataBinding.refundPhotoExpertPlace.text = data[0].expert_place
                                mViewDataBinding.refundPhotoExpertPrice.text = data[0].expert_price

                                var multiTransformation = MultiTransformation(CenterCrop(),RoundedCorners(20))

                                Glide.with(MyApplication.instance)
                                    .load(data[0].expert_image)
                                    .apply(RequestOptions.bitmapTransform(multiTransformation))
                                    .into(mViewDataBinding.refundPhotoExpertImage)

                                taglist = ArrayList<String>()
                                val tag = data[0].expert_category.split(",")
                                for (i in tag) {
                                    taglist.add(i)
                                }
                                tagAdapter = SendTagRecyclerViewAdapter(taglist)
                                mViewDataBinding.refundPhotoExpertCategoryRecyclerview.layoutManager = LinearLayoutManager(
                                    this,
                                    LinearLayoutManager.HORIZONTAL, false
                                )
                                mViewDataBinding.refundPhotoExpertCategoryRecyclerview.adapter = tagAdapter

                                mViewDataBinding.refundPhotoTid.text = data[0].reserve_tid
                                mViewDataBinding.refundPhotoBillMethod.text = data[0].bill_method
                                mViewDataBinding.refundPhotoBillDate.text = data[0].bill_date
                                mViewDataBinding.refundPhotoDt.text = data[0].reserve_dt
                                mViewDataBinding.refundPhotoTime.text = data[0].reserve_time.toString()
                                mViewDataBinding.refundPhotoTimeTerm.text = data[0].reserve_time_term
                                mViewDataBinding.refundPhotoHeadcount.text =
                                    data[0].reserve_headcount.toString()

                                mViewDataBinding.refundPhotoContents.text = data[0].reserve_contents

                                if (data[0].reserve_add_contents != "") {
                                    mViewDataBinding.refundPhotoAddContentsLayout.visibility = View.VISIBLE
                                    mViewDataBinding.refundPhotoAddContents.text = data[0].reserve_add_contents
                                }

                                mViewDataBinding.refundPhotoFinalPrice.text = dec.format(data[0].reserve_price)
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
                                    mViewDataBinding.refundPhotoTopManu.visibility = View.GONE
                                    mViewDataBinding.refundInfoTitle.text = "환불 접수가 완료되었습니다."
                                    mViewDataBinding.refundInfo1.text = "*신용카드로 결제한 경우, 실제 환불 일자는 신용카드사에 따라\n차이가 있을 수 있습니다. 보다 정확한 사항은 카드사로\n문의하시기 바랍니다."
                                    mViewDataBinding.refundInfo2.visibility = View.VISIBLE
                                    mViewDataBinding.refundInfo2.text = "*계좌이체로 구매한 경우, 지불하신 출금계좌로 입금되며\n영업일 기준으로 1-3일 소요됩니다."
                                    mViewDataBinding.refundInfo1.setTextColor(Color.parseColor("#8276f4"))
                                    mViewDataBinding.refundPhotoBillDateTitle.text = "환불일자/시간 "
                                    mViewDataBinding.refundPhotoMoneyLayout.visibility = View.VISIBLE
                                    mViewDataBinding.refundPhotoMoneyTitle.text = "환불금액"
                                    mViewDataBinding.refundPhotoMoney.text = data[0].refund_money.toString()
                                }

                                mViewDataBinding.refundPhotoExpertName.text = data[0].expert_name
                                mViewDataBinding.refundPhotoExpertPlace.text = data[0].expert_place
                                mViewDataBinding.refundPhotoExpertPrice.text = data[0].expert_price

                                var multiTransformation = MultiTransformation(CenterCrop(),RoundedCorners(20))

                                Glide.with(MyApplication.instance)
                                    .load(data[0].expert_image)
                                    .apply(RequestOptions.bitmapTransform(multiTransformation))
                                    .into(mViewDataBinding.refundPhotoExpertImage)

                                taglist = ArrayList<String>()
                                val tag = data[0].expert_category.split(",")
                                for (i in tag) {
                                    taglist.add(i)
                                }
                                tagAdapter = SendTagRecyclerViewAdapter(taglist)
                                mViewDataBinding.refundPhotoExpertCategoryRecyclerview.layoutManager = LinearLayoutManager(
                                    this,
                                    LinearLayoutManager.HORIZONTAL, false
                                )
                                mViewDataBinding.refundPhotoExpertCategoryRecyclerview.adapter = tagAdapter

                                mViewDataBinding.refundPhotoTid.text = data[0].reserve_tid
                                mViewDataBinding.refundPhotoBillMethod.text = data[0].bill_method
                                mViewDataBinding.refundPhotoBillDate.text = data[0].bill_date
                                mViewDataBinding.refundPhotoDt.text = data[0].reserve_dt
                                mViewDataBinding.refundPhotoTime.text = data[0].reserve_time.toString()
                                mViewDataBinding.refundPhotoTimeTerm.text = data[0].reserve_time_term
                                mViewDataBinding.refundPhotoHeadcount.text =
                                    data[0].reserve_headcount.toString()

                                mViewDataBinding.refundPhotoContents.text = data[0].reserve_contents

                                if (data[0].reserve_add_contents != "") {
                                    mViewDataBinding.refundPhotoAddContentsLayout.visibility = View.VISIBLE
                                    mViewDataBinding.refundPhotoAddContents.text = data[0].reserve_add_contents
                                }

                                mViewDataBinding.refundPhotoFinalPrice.text = dec.format(data[0].reserve_price)
                            }
                        }
                    })
            }
        }

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.refund_photo_expert_image -> {
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
            R.id.refund_photo_chat -> {
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