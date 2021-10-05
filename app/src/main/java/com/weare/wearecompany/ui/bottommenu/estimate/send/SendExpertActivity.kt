package com.weare.wearecompany.ui.bottommenu.estimate.send

import android.content.Intent
import android.graphics.Color
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.retrofit.bottomnav.estimate.receiveManager
import com.weare.wearecompany.data.retrofit.bottomnav.estimate.sendManager
import com.weare.wearecompany.databinding.ActivityEstimateExpertBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.ui.bottommenu.estimate.receive.payment.paymentActivity
import com.weare.wearecompany.ui.chat.ChatActivity
import com.weare.wearecompany.ui.detail.DatailActivity
import com.weare.wearecompany.ui.detail.model.ModelActivity
import com.weare.wearecompany.ui.detail.photo.PhotoActivity
import com.weare.wearecompany.ui.detail.studio.reservation.ReservationStudioDialogRecyclerViewAdapter
import com.weare.wearecompany.ui.detail.trip.TripActivity
import com.weare.wearecompany.utils.ESTIMATE
import java.text.DecimalFormat

class SendExpertActivity:BaseActivity<ActivityEstimateExpertBinding>(
    R.layout.activity_estimate_expert
),View.OnClickListener {

    private var reserve_idx:String = ""
    private var expert_idx: String = ""
    private var expert_type: Int = -1
    private var type:Int = -1
    private var chatcheck:Int = -1

    private lateinit var dtlist: ArrayList<String>
    private lateinit var dateadapter : ReservationStudioDialogRecyclerViewAdapter

    private lateinit var taglist: ArrayList<String>
    private lateinit var tagAdapter: SendTagRecyclerViewAdapter

    private val dec = DecimalFormat("#,###")


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

        mViewDataBinding.reservationExpertOk.setOnClickListener(this)
        mViewDataBinding.estimateExpertToolbarRefundMenu.setOnClickListener(this)
        mViewDataBinding.reservationExpertChat.setOnClickListener(this)
        mViewDataBinding.sendExpertInfoLayout.setOnClickListener(this)

        when (type) {
            0 -> {
                sendManager.instance.expertPage(reserve_idx, completion = { responseStatus, data ->
                    when (responseStatus) {
                        ESTIMATE.OKAY -> {
                            expert_idx = data[0].expert_idx
                            expert_type = data[0].expert_type
                            if (data[0].dt_status == 0) {

                                mViewDataBinding.sendExpertName.text = data[0].expert_name
                                mViewDataBinding.sendExpertPlace.text = data[0].expert_place
                                mViewDataBinding.sendExpertPrice.text = data[0].expert_price

                                taglist = ArrayList<String>()
                                when(expert_type) {
                                    0 -> taglist.add("포토그래퍼")
                                    1 -> taglist.add("모델")
                                    2 -> taglist.add("뷰티전문가")
                                }
                                val tag = data[0].expert_category.split(",")
                                for (i in tag) {
                                    taglist.add(i)
                                }
                                tagAdapter = SendTagRecyclerViewAdapter(taglist)
                                mViewDataBinding.sendExpertCategoryRecyclerview.layoutManager = LinearLayoutManager(
                                    this,
                                    LinearLayoutManager.HORIZONTAL, false
                                )
                                mViewDataBinding.sendExpertCategoryRecyclerview.adapter = tagAdapter

                                var multiTransformation = MultiTransformation(
                                    CenterCrop(),
                                    RoundedCorners(20)
                                )

                                Glide.with(MyApplication.instance)
                                    .load(data[0].expert_image)
                                    .apply(RequestOptions.bitmapTransform(multiTransformation))
                                    .into(mViewDataBinding.sendExpertImage)
                                dtlist = ArrayList<String>()
                                val dt = data[0].reserve_dt.split(",")
                                for (i in dt) {
                                    dtlist.add(i)
                                }
                                if (dtlist.size != 0) {
                                    mViewDataBinding.reservationExpertRecyclerView.visibility = View.VISIBLE
                                    dateadapter = ReservationStudioDialogRecyclerViewAdapter(dtlist)
                                    mViewDataBinding.reservationExpertRecyclerView.layoutManager = GridLayoutManager(this, 3)
                                    mViewDataBinding.reservationExpertRecyclerView.adapter = dateadapter
                                } else {
                                    mViewDataBinding.reservationExpertStatus.visibility = View.VISIBLE
                                }

                                mViewDataBinding.reservationExpertTimeDate.visibility = View.VISIBLE
                                mViewDataBinding.reservationExpertTimeDate.text = data[0].reserve_time.toString()
                                when(data[0].time_zone) {
                                    0 -> mViewDataBinding.reservationExpertTimezone.text = "오전"
                                    1 -> mViewDataBinding.reservationExpertTimezone.text = "오후"
                                    2 -> mViewDataBinding.reservationExpertTimezone.text = "상관없음"
                                }
                                if (data[0].expert_type == 1) {
                                    mViewDataBinding.reservationExpertHeadcountLayout.visibility = View.GONE
                                    if (data[0].reserve_contents != "") {
                                        mViewDataBinding.reservationExpertContents.text = data[0].reserve_contents
                                    } else {
                                        mViewDataBinding.reservationExpertContents.visibility = View.GONE
                                        mViewDataBinding.reservationExpertContentsTitle.visibility = View.GONE
                                    }
                                    if (data[0].reserve_price.toString() != "0") {
                                        mViewDataBinding.reservationExpertReserveLayout.visibility = View.VISIBLE
                                        mViewDataBinding.estimateExpertReserveFinalPriceTitleBottomView.visibility = View.GONE
                                        mViewDataBinding.reservationExpertReserveFinalPriceLayout.visibility = View.VISIBLE
                                        mViewDataBinding.reservationExpertReserveFinalPrice.text = data[0].reserve_price.toString()
                                    }

                                } else {
                                    mViewDataBinding.reservationExpertHeadcount.text = data[0].reserve_headcount.toString()
                                    if (data[0].reserve_contents != "") {
                                        mViewDataBinding.reservationExpertContents.text = data[0].reserve_contents
                                    } else {
                                        mViewDataBinding.reservationExpertContents.visibility = View.GONE
                                        mViewDataBinding.reservationExpertContentsTitle.visibility = View.GONE
                                    }

                                    if (data[0].reserve_price.toString() != "0") {
                                        mViewDataBinding.reservationExpertReserveLayout.visibility = View.VISIBLE
                                        mViewDataBinding.estimateExpertReserveFinalPriceTitleBottomView.visibility = View.GONE
                                        mViewDataBinding.reservationExpertReserveFinalPriceLayout.visibility = View.VISIBLE
                                        mViewDataBinding.reservationExpertReserveFinalPrice.text = data[0].reserve_price.toString()
                                    }
                                }
                            } else if (data[0].dt_status == 1) {

                                mViewDataBinding.sendExpertName.text = data[0].expert_name
                                mViewDataBinding.sendExpertPlace.text = data[0].expert_place
                                mViewDataBinding.sendExpertPrice.text = data[0].expert_price

                                taglist = ArrayList<String>()
                                val tag = data[0].expert_category.split(",")
                                for (i in tag) {
                                    taglist.add(i)
                                }
                                tagAdapter = SendTagRecyclerViewAdapter(taglist)
                                mViewDataBinding.sendExpertCategoryRecyclerview.layoutManager = LinearLayoutManager(
                                    this,
                                    LinearLayoutManager.HORIZONTAL, false
                                )
                                mViewDataBinding.sendExpertCategoryRecyclerview.adapter = tagAdapter

                                var multiTransformation = MultiTransformation(
                                    CenterCrop(),
                                    RoundedCorners(20)
                                )

                                Glide.with(MyApplication.instance)
                                    .load(data[0].expert_image)
                                    .apply(RequestOptions.bitmapTransform(multiTransformation))
                                    .into(mViewDataBinding.sendExpertImage)

                                mViewDataBinding.reservationExpertStatus.visibility = View.VISIBLE
                                mViewDataBinding.reservationExpertRecyclerView.visibility = View.GONE
                                mViewDataBinding.reservationExpertTimeDateTitle.text = "협의가능"
                                mViewDataBinding.reservationExpertTimezone.text = "협의가능"
                                if (data[0].expert_type == 1) {
                                    mViewDataBinding.reservationExpertHeadcountLayout.visibility = View.GONE
                                } else {
                                    mViewDataBinding.reservationExpertHeadcount.text = data[0].reserve_headcount.toString()
                                }
                                if (data[0].reserve_contents != "") {
                                    mViewDataBinding.reservationExpertContents.text = data[0].reserve_contents
                                } else {
                                    mViewDataBinding.reservationExpertContents.visibility = View.GONE
                                    mViewDataBinding.reservationExpertContentsTitle.visibility = View.GONE
                                }
                                //mViewDataBinding.reservationExpertReserveLayout.visibility = View.GONE
                            }
                        }
                    }
                })
            }
            1 -> {
                receiveManager.instance.expertPage(
                    reserve_idx,
                    completion = { responseStatus, data ->
                        when (responseStatus) {
                            ESTIMATE.OKAY -> {
                                expert_idx = data[0].expert_idx
                                expert_type = data[0].expert_type
                                mViewDataBinding.reservationExpertOk.visibility = View.VISIBLE
                                mViewDataBinding.estimateExpertToolbarText.text = "받은 견적서"
                                mViewDataBinding.estimateExpertInfoTitle.text = "견적을 확인해주세요"
                                mViewDataBinding.estimateExpertInfo1.text = "전문가 님과 충분한 상의 후 견적을 받으셨나요?"
                                mViewDataBinding.estimateExpertInfo2.text =
                                    "요청하실 사항 및 문의 사항은 1:1 채팅을 통해 가능하며,\n하단의 버튼을 통해 결제를 진행해 주세요."
                                mViewDataBinding.reservationExpertStatusTitle.text = "예약날짜"
                                mViewDataBinding.reservationExpertRecyclerView.visibility = View.GONE
                                mViewDataBinding.reservationExpertStatus.visibility = View.VISIBLE
                                mViewDataBinding.reservationExpertStatus.text = data[0].reserve_dt
                                mViewDataBinding.reservationExpertTimeTitle.text = "예약시간"
                                mViewDataBinding.reservationExpertTimeDate.visibility = View.VISIBLE
                                mViewDataBinding.reservationExpertTimeDate.text = data[0].reserve_time.toString()
                                mViewDataBinding.reservationExpertTimezoneTitle.text = "예약시간대"
                                mViewDataBinding.reservationExpertTimezone.visibility = View.VISIBLE
                                mViewDataBinding.reservationExpertTimezone.text = data[0].reserve_time_term

                                mViewDataBinding.sendExpertName.text = data[0].expert_name
                                mViewDataBinding.sendExpertPlace.text = data[0].expert_place
                                mViewDataBinding.sendExpertPrice.text = data[0].expert_price

                                taglist = ArrayList<String>()
                                val tag = data[0].expert_category.split(",")
                                for (i in tag) {
                                    taglist.add(i)
                                }
                                tagAdapter = SendTagRecyclerViewAdapter(taglist)
                                mViewDataBinding.sendExpertCategoryRecyclerview.layoutManager = LinearLayoutManager(
                                    this,
                                    LinearLayoutManager.HORIZONTAL, false
                                )
                                mViewDataBinding.sendExpertCategoryRecyclerview.adapter = tagAdapter

                                var multiTransformation = MultiTransformation(
                                    CenterCrop(),
                                    RoundedCorners(20)
                                )

                                Glide.with(MyApplication.instance)
                                    .load(data[0].expert_image)
                                    .apply(RequestOptions.bitmapTransform(multiTransformation))
                                    .into(mViewDataBinding.sendExpertImage)


                                if (data[0].expert_type == 1) {
                                    mViewDataBinding.reservationExpertHeadcountLayout.visibility = View.GONE
                                } else {
                                    mViewDataBinding.reservationExpertHeadcountTitle.text = "예약인원"
                                    mViewDataBinding.reservationExpertHeadcount.text = data[0].reserve_headcount.toString()
                                }
                                if (data[0].reserve_contents != "") {
                                    mViewDataBinding.reservationExpertContents.text = data[0].reserve_contents
                                } else {
                                    mViewDataBinding.reservationExpertContents.visibility = View.GONE
                                    mViewDataBinding.reservationExpertContentsTitle.visibility = View.GONE
                                }

                                mViewDataBinding.reservationExpertReserveLayout.visibility = View.VISIBLE

                                if (data[0].reserve_add_contents != ""){
                                    mViewDataBinding.reservationExpertReserveAddContentsTitle.visibility = View.VISIBLE
                                    mViewDataBinding.estimateExpertReserveAddContents.visibility = View.VISIBLE
                                    mViewDataBinding.estimateExpertReserveAddContents.text = data[0].reserve_add_contents
                                }

                                mViewDataBinding.estimateExpertReserveFinalPriceTitleBottomView.visibility = View.VISIBLE
                                mViewDataBinding.reservationExpertReserveFinalPriceLayout.visibility = View.VISIBLE
                                mViewDataBinding.reservationExpertReserveFinalPriceTitle.text = "최종 결제 금액"
                                mViewDataBinding.reservationExpertReserveFinalPrice.text = dec.format(data[0].reserve_price)
                                mViewDataBinding.buttonLayout.visibility = View.VISIBLE
                                if (chatcheck == 1) {
                                    mViewDataBinding.reservationExpertChat.visibility = View.GONE
                                }
                            }
                        }
                    })
            }
            11 -> {
                receiveManager.instance.expertPage(
                    reserve_idx,
                    completion = { responseStatus, data ->
                        when (responseStatus) {
                            ESTIMATE.OKAY -> {
                                mViewDataBinding.estimateExpertToolbarText.text = "진행현황"

                                mViewDataBinding.estimateExpertInfoTitle.text = "결제가 완료되었습니다."
                                mViewDataBinding.estimateExpertInfo1.text ="전문가와 서비스를 진행후 7일간은 환불 요청 및 건의 기간으로 이후 자동으로 구매 확정이 진행됩니다."
                                mViewDataBinding.estimateExpertInfo1.setTextColor(
                                    Color.parseColor(
                                        "#f96565"
                                    )
                                )
                                mViewDataBinding.estimateExpertInfo2.visibility = View.GONE

                                mViewDataBinding.reservationExpertStatusTitle.text = "예약날짜"
                                mViewDataBinding.reservationExpertRecyclerView.visibility = View.GONE
                                mViewDataBinding.reservationExpertStatus.visibility = View.VISIBLE
                                mViewDataBinding.reservationExpertStatus.text = data[0].reserve_dt
                                mViewDataBinding.reservationExpertTimeTitle.text = "예약시간"
                                mViewDataBinding.reservationExpertTimeDate.visibility = View.VISIBLE
                                mViewDataBinding.reservationExpertTimeDate.text = data[0].reserve_time.toString()
                                mViewDataBinding.reservationExpertTimezoneTitle.text = "예약시간대"
                                mViewDataBinding.reservationExpertTimezone.visibility = View.VISIBLE
                                mViewDataBinding.reservationExpertTimezone.text = data[0].reserve_time_term
                                if (data[0].expert_type == 1) {
                                    mViewDataBinding.reservationExpertHeadcountTitle.visibility = View.GONE
                                    mViewDataBinding.reservationExpertHeadcountDataTitle.visibility = View.GONE
                                } else {
                                    mViewDataBinding.reservationExpertHeadcountTitle.text = "예약인원"
                                    mViewDataBinding.reservationExpertHeadcount.visibility = View.VISIBLE
                                    mViewDataBinding.reservationExpertHeadcount.text = data[0].reserve_headcount.toString()
                                }
                                if (data[0].reserve_contents != "") {
                                    mViewDataBinding.reservationExpertContents.text = data[0].reserve_contents
                                } else {
                                    mViewDataBinding.reservationExpertContents.visibility = View.GONE
                                    mViewDataBinding.reservationExpertContentsTitle.visibility = View.GONE
                                }

                                mViewDataBinding.reservationExpertReserveLayout.visibility = View.VISIBLE

                                if (data[0].reserve_add_contents != ""){
                                    mViewDataBinding.reservationExpertReserveAddContentsTitle.visibility = View.VISIBLE
                                    mViewDataBinding.estimateExpertReserveAddContents.visibility = View.VISIBLE
                                    mViewDataBinding.estimateExpertReserveAddContents.text = data[0].reserve_add_contents
                                }

                                mViewDataBinding.estimateExpertReserveFinalPriceTitleBottomView.visibility = View.VISIBLE
                                mViewDataBinding.reservationExpertReserveFinalPrice.text = dec.format(data[0].reserve_price)
                                if (chatcheck != 1) {
                                    mViewDataBinding.reservationExpertChat.visibility = View.VISIBLE
                                }
                                mViewDataBinding.reservationExpertOk.visibility = View.VISIBLE
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
            R.id.send_expert_info_layout -> {
                var newIntent = Intent()
                when(expert_type) {
                    0 -> {
                        newIntent = Intent(this, PhotoActivity::class.java)
                    }
                    1 -> {
                        newIntent = Intent(this, ModelActivity::class.java)
                    }
                    2 -> {
                        newIntent = Intent(this, TripActivity::class.java)
                    }
                }
                newIntent.putExtra("expert_idx", expert_idx)
                startActivity(newIntent)
            }
            R.id.reservation_expert_ok -> {
                val newIntent = Intent(this, paymentActivity::class.java)
                newIntent.putExtra("reserve_idx", reserve_idx)
                newIntent.putExtra("type", 0)
                startActivityForResult(newIntent,5000)
            }
            R.id.reservation_expert_chat -> {
                val newIntent = Intent(this, ChatActivity::class.java)
                newIntent.putExtra("reserve_idx", reserve_idx)
                newIntent.putExtra("type", 0)
                newIntent.putExtra("Entrytype",0)
                startActivity(newIntent)
            }
            R.id.estimate_expert_toolbar_refund_menu -> {
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