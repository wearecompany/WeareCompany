package com.weare.wearecompany.ui.bottommenu.estimate.send

import android.app.Activity
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
import com.weare.wearecompany.data.retrofit.bottomnav.estimate.receiveManager
import com.weare.wearecompany.data.retrofit.bottomnav.estimate.refundManager
import com.weare.wearecompany.data.retrofit.bottomnav.estimate.sendManager
import com.weare.wearecompany.databinding.ActivityEstimateShopBinding
import com.weare.wearecompany.ui.ReportActivity
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.ui.bottommenu.estimate.progress.ProgressBottomRefundFragment
import com.weare.wearecompany.ui.bottommenu.estimate.receive.payment.paymentActivity
import com.weare.wearecompany.ui.bottommenu.estimate.refund.RefundDialog
import com.weare.wearecompany.ui.chat.ChatActivity
import com.weare.wearecompany.ui.detail.model.ModelActivity
import com.weare.wearecompany.ui.detail.photo.PhotoActivity
import com.weare.wearecompany.ui.detail.rent.RentActivity
import com.weare.wearecompany.ui.detail.trip.TripActivity
import com.weare.wearecompany.utils.ESTIMATE

class SendShopActivity:BaseActivity<ActivityEstimateShopBinding>(
    R.layout.activity_estimate_shop
),View.OnClickListener {
    private var reserve_idx:String = ""
    private var type:Int = -1
    private var chatcheck:Int = -1

    private var expert_idx: String = ""

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
        chatcheck = intent.getIntExtra("chatcheck", 0)
        setup()
    }

    private fun setup() {

        mViewDataBinding.estimateShopOk.setOnClickListener(this)
        mViewDataBinding.estimateShopToolbarRefundMenu.setOnClickListener(this)
        mViewDataBinding.estimateShopChat.setOnClickListener(this)
        mViewDataBinding.sendShopInfoLayout.setOnClickListener(this)

        when(type) {
            0 -> {
                sendManager.instance.shopPage(reserve_idx, completion = { responseStatus, data ->
                    when (responseStatus) {
                        ESTIMATE.OKAY -> {
                            expert_idx = data[0].expert_idx
                            if (data[0].dt_status == 0) {
                                mViewDataBinding.sendShopExpertUserName.text = data[0].expert_user_name
                                mViewDataBinding.sendShopName.text = data[0].expert_name

                                taglist = ArrayList<String>()
                                val tag = data[0].expert_category.split(",")
                                for (i in tag) {
                                    taglist.add(i)
                                }
                                tagAdapter = SendTagRecyclerViewAdapter(taglist)
                                mViewDataBinding.sendShopCategoryRecyclerview.layoutManager = LinearLayoutManager(
                                    this,
                                    LinearLayoutManager.HORIZONTAL, false
                                )
                                mViewDataBinding.sendShopCategoryRecyclerview.adapter = tagAdapter

                                var multiTransformation = MultiTransformation(CenterCrop(),RoundedCorners(20))

                                Glide.with(MyApplication.instance)
                                    .load(data[0].expert_image)
                                    .apply(RequestOptions.bitmapTransform(multiTransformation))
                                    .into(mViewDataBinding.sendShopImage)

                                mViewDataBinding.estimateShopStaetTime.visibility = View.VISIBLE
                                mViewDataBinding.estimateShopStaetTime.text = data[0].start_dt
                                mViewDataBinding.estimateShopStaetTimeAnd.visibility = View.VISIBLE
                                mViewDataBinding.estimateShopTime.text = data[0].end_dt
                                mViewDataBinding.estimateShopReserveDay.text = data[0].reserve_day.toString()
                                mViewDataBinding.estimateShopReserveDay.visibility = View.VISIBLE

                                mViewDataBinding.estimateShopReserveContents.text =
                                    data[0].reserve_contents
                            } else if (data[0].dt_status == 1) {
                                mViewDataBinding.sendShopExpertUserName.text = data[0].expert_user_name
                                mViewDataBinding.sendShopName.text = data[0].expert_name

                                taglist = ArrayList<String>()
                                val tag = data[0].expert_category.split(",")
                                for (i in tag) {
                                    taglist.add(i)
                                }
                                tagAdapter = SendTagRecyclerViewAdapter(taglist)
                                mViewDataBinding.sendShopCategoryRecyclerview.layoutManager = LinearLayoutManager(
                                    this,
                                    LinearLayoutManager.HORIZONTAL, false
                                )
                                mViewDataBinding.sendShopCategoryRecyclerview.adapter = tagAdapter

                                var multiTransformation = MultiTransformation(CenterCrop(), RoundedCorners(20))

                                Glide.with(MyApplication.instance)
                                    .load(data[0].expert_image)
                                    .apply(RequestOptions.bitmapTransform(multiTransformation))
                                    .into(mViewDataBinding.sendShopImage)

                                mViewDataBinding.estimateShopTime.text = "협의가능"
                                mViewDataBinding.estimateShopReserveDayLayout.visibility = View.GONE
                                //mViewDataBinding.estimateShopReserveDay2.text = "협의가능"
                                if (data[0].reserve_contents != "") {
                                    mViewDataBinding.estimateShopReserveContents.text = data[0].reserve_contents
                                } else {
                                    mViewDataBinding.estimateShopReserveContentsTitle.visibility = View.GONE
                                    mViewDataBinding.estimateShopReserveContents.visibility = View.GONE
                                }
                                mViewDataBinding.estimateShopReserveContents.text =
                                    data[0].reserve_contents
                            }
                        }
                    }
                })
            }
            1 -> {
                receiveManager.instance.shopPage(reserve_idx, completion = { responseStatus, data ->
                    when (responseStatus) {
                        ESTIMATE.OKAY -> {
                            expert_idx = data[0].expert_idx
                            mViewDataBinding.estimateShopToolbarText.text = "받은 견적서"
                            mViewDataBinding.estimateShopInfoTitle.text = "견적을 확인해주세요"
                            mViewDataBinding.estimateShopInfo1.text = "전문가 님과 충분한 상의 후 견적을 받으셨나요?"
                            mViewDataBinding.estimateShopInfo2.text =
                                "요청하실 사항 및 문의 사항은 1:1 채팅을 통해 가능하며,\n하단의 버튼을 통해 결제를 진행해 주세요."
                            mViewDataBinding.estimateShopStaetTime.visibility = View.VISIBLE
                            mViewDataBinding.estimateShopStaetTime.text = data[0].start_dt
                            mViewDataBinding.estimateShopStaetTimeAnd.visibility = View.VISIBLE
                            mViewDataBinding.estimateShopTime.text = data[0].end_dt
                            mViewDataBinding.estimateShopReserveDay.visibility = View.VISIBLE
                            mViewDataBinding.estimateShopReserveDay.text =
                                data[0].reserve_time_term.toString()

                            taglist = ArrayList<String>()
                            val tag = data[0].expert_category.split(",")
                            for (i in tag) {
                                taglist.add(i)
                            }
                            tagAdapter = SendTagRecyclerViewAdapter(taglist)
                            mViewDataBinding.sendShopCategoryRecyclerview.layoutManager = LinearLayoutManager(
                                this,
                                LinearLayoutManager.HORIZONTAL, false
                            )
                            mViewDataBinding.sendShopCategoryRecyclerview.adapter = tagAdapter

                            var multiTransformation = MultiTransformation(CenterCrop(),RoundedCorners(20))

                            Glide.with(MyApplication.instance)
                                .load(data[0].expert_image)
                                .apply(RequestOptions.bitmapTransform(multiTransformation))
                                .into(mViewDataBinding.sendShopImage)

                            if (data[0].reserve_contents != "") {
                                mViewDataBinding.estimateShopReserveContents.text =
                                    data[0].reserve_contents
                            } else {
                                mViewDataBinding.estimateShopReserveContentsTitle.visibility = View.GONE
                                mViewDataBinding.estimateShopReserveContents.visibility = View.GONE
                            }
                            mViewDataBinding.estimateShopReserveLayout.visibility = View.VISIBLE
                            if(data[0].reserve_add_contents != "") {
                                mViewDataBinding.estimateShopReserveAddContentsTitle.visibility = View.VISIBLE
                                mViewDataBinding.estimateShopReserveAddContents.visibility = View.VISIBLE
                                mViewDataBinding.estimateShopReserveAddContents.text = data[0].reserve_add_contents
                            }
                            mViewDataBinding.estimateShopReserveFinalPriceLayout.visibility = View.VISIBLE
                            mViewDataBinding.estimateShopReserveFinalPrice.text =
                                data[0].reserve_final_price.toString()
                            mViewDataBinding.buttonLayout.visibility = View.VISIBLE
                            if (chatcheck == 1) {
                                mViewDataBinding.estimateShopChat.visibility = View.GONE
                            }
                        }
                    }
                })
            }
            2 -> {
                progressManager.instance.shopPage(
                    reserve_idx,
                    completion = { responseStatus, data ->
                        when (responseStatus) {
                            ESTIMATE.OKAY -> {
                                expert_idx = data[0].expert_idx
                                mViewDataBinding.estimateShopToolbarRefundMenu.visibility =
                                    View.VISIBLE
                                mViewDataBinding.estimateShopToolbarText.text = "진행현황"

                                if (data[0].refund_status == 0) {
                                    mViewDataBinding.estimateShopInfoTitle.text = "결제가 완료되었습니다."
                                    mViewDataBinding.estimateShopInfo1.text =
                                        "전문가와 서비스를 진행후 7일간은 환불 요청 및 건의 기간으로 이후 자동으로 구매 확정이 진행됩니다."
                                    mViewDataBinding.estimateShopInfo1.setTextColor(
                                        Color.parseColor(
                                            "#f96565"
                                        )
                                    )
                                    mViewDataBinding.estimateShopInfo2.visibility = View.GONE
                                } else if (data[0].refund_status == 1) {
                                    mViewDataBinding.estimateShopInfoTitle.text =
                                        "전문가에게 환불을 요청했습니다."
                                    mViewDataBinding.estimateShopInfo1.setTextColor(
                                        Color.parseColor(
                                            "#f96565"
                                        )
                                    )
                                    mViewDataBinding.estimateShopInfo1.text = "전문가 확인 후 환불이 진행됩니다."
                                    mViewDataBinding.estimateShopInfo2.setTextColor(
                                        Color.parseColor(
                                            "#f96565"
                                        )
                                    )
                                    mViewDataBinding.estimateShopInfo2.text =
                                        "환불이 계속 진행되지 않는다면, 아래 [1:1 채팅하기] 버튼을 눌러 전문가에게 환불을 요청해 주세요."
                                } else if (data[0].refund_status == 2) {
                                    mViewDataBinding.estimateShopInfoTitle.text = "환불 접수가 완료되었습니다"
                                    mViewDataBinding.estimateShopInfo1.setTextColor(
                                        Color.parseColor(
                                            "#8276f4"
                                        )
                                    )
                                    mViewDataBinding.estimateShopInfo1.text =
                                        "* 신용카드로 결제한 경우, 실제 환불 일자는 신용카드사에 따라 차이가 있을 수 있습니다. 보다 정확한 사항은 카드사로 문의하시기 바랍니다."
                                    mViewDataBinding.estimateShopInfo2.setTextColor(
                                        Color.parseColor(
                                            "#8276f4"
                                        )
                                    )
                                    mViewDataBinding.estimateShopInfo2.text =
                                        "계좌이체로 구매한 경우, 지불하신 출금 계좌로 입금되며 영업일 기준으로 1-3일 소요됩니다."
                                    mViewDataBinding.estimateShopBillDateTitle.text = "취소 일자"
                                    mViewDataBinding.estimateShopBillDate.text = data[0].bill_date
                                    mViewDataBinding.estimateShopRefundMoneyLayout.visibility =
                                        View.VISIBLE
                                    mViewDataBinding.estimateShopRefundMoney.text =
                                        data[0].refund_money.toString()
                                }

                                mViewDataBinding.estimateShopProggressLayout.visibility =
                                    View.VISIBLE
                                mViewDataBinding.estimateShopReserveTid.text = data[0].reserve_tid
                                mViewDataBinding.estimateShopBillMethod.text = data[0].bill_method
                                mViewDataBinding.estimateShopBillDate.text = data[0].bill_date

                                mViewDataBinding.estimateShopStaetTime.visibility = View.VISIBLE
                                mViewDataBinding.estimateShopStaetTime.text = data[0].start_dt
                                mViewDataBinding.estimateShopStaetTimeAnd.visibility = View.VISIBLE

                                taglist = ArrayList<String>()
                                val tag = data[0].expert_category.split(",")
                                for (i in tag) {
                                    taglist.add(i)
                                }
                                tagAdapter = SendTagRecyclerViewAdapter(taglist)
                                mViewDataBinding.sendShopCategoryRecyclerview.layoutManager = LinearLayoutManager(
                                    this,
                                    LinearLayoutManager.HORIZONTAL, false
                                )
                                mViewDataBinding.sendShopCategoryRecyclerview.adapter = tagAdapter

                                var multiTransformation = MultiTransformation(CenterCrop(),RoundedCorners(20))

                                Glide.with(MyApplication.instance)
                                    .load(data[0].expert_image)
                                    .apply(RequestOptions.bitmapTransform(multiTransformation))
                                    .into(mViewDataBinding.sendShopImage)

                                //mViewDataBinding.estimateShopEndTime.text = data[0].end_dt
                                mViewDataBinding.estimateShopReserveDay2.visibility = View.VISIBLE
                                mViewDataBinding.estimateShopReserveDay.text =
                                    data[0].reserve_time_term.toString()

                                mViewDataBinding.estimateShopReserveLayout.visibility = View.VISIBLE

                                mViewDataBinding.estimateShopReserveContents.text =
                                    data[0].reserve_contents
                                mViewDataBinding.estimateShopReserveAddContents.text =
                                    data[0].reserve_add_contents
                                mViewDataBinding.estimateShopReserveFinalPrice.text =
                                    data[0].reserve_final_price.toString()

                                mViewDataBinding.estimateShopOk.visibility = View.GONE

                            }
                        }
                    })
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(resultCode) {
            5001 -> {
                val intent = Intent()
                intent.putExtra("payment", "ok")
                setResult(5001, intent)
                finish()
            }
        }

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.estimate_shop_ok -> {
                val newIntent = Intent(this, paymentActivity::class.java)
                newIntent.putExtra("reserve_idx", reserve_idx)
                newIntent.putExtra("type", 0)
                startActivityForResult(newIntent,5000)
            }
            R.id.estimate_shop_toolbar_refund_menu -> {
                    val cancellationdialog: CancellationBottomDialog = CancellationBottomDialog() {
                        when(it) {
                            1 -> {
                                sendManager.instance.reservedelete(reserve_idx,completion = {responseStatus ->
                                    when(responseStatus) {
                                        ESTIMATE.OKAY -> {

                                        }
                                    }
                                })
                                val intent = Intent()
                                intent.putExtra("Cancellation", "ok")
                                setResult(6000, intent)
                                finish()
                            }

                        }
                    }
                    cancellationdialog.show(supportFragmentManager,cancellationdialog.tag)
            }
            R.id.estimate_shop_chat -> {
                val newIntent = Intent(this, ChatActivity::class.java)
                newIntent.putExtra("reserve_idx", reserve_idx)
                newIntent.putExtra("type", 0)
                newIntent.putExtra("Entrytype",0)
                startActivity(newIntent)
            }
            R.id.estimate_shop_toolbar_refund_menu -> {
                val cancellationdialog: CancellationBottomDialog = CancellationBottomDialog() {
                    when(it) {
                        1 -> {
                            sendManager.instance.reservedelete(reserve_idx,completion = {responseStatus ->
                                when(responseStatus) {
                                    ESTIMATE.OKAY -> {

                                    }
                                }
                            })
                            val intent = Intent()
                            intent.putExtra("Cancellation", "ok")
                            setResult(6000, intent)
                            finish()
                        }
                    }
                }
                cancellationdialog.show(supportFragmentManager,cancellationdialog.tag)
            }
            R.id.send_shop_info_layout -> {
                var newIntent = Intent(this, RentActivity::class.java)
                newIntent.putExtra("expert_idx", expert_idx)
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