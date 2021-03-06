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
                                    0 -> taglist.add("???????????????")
                                    1 -> taglist.add("??????")
                                    2 -> taglist.add("???????????????")
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
                                    0 -> mViewDataBinding.reservationExpertTimezone.text = "??????"
                                    1 -> mViewDataBinding.reservationExpertTimezone.text = "??????"
                                    2 -> mViewDataBinding.reservationExpertTimezone.text = "????????????"
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
                                mViewDataBinding.reservationExpertTimeDateTitle.text = "????????????"
                                mViewDataBinding.reservationExpertTimezone.text = "????????????"
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
                                mViewDataBinding.estimateExpertToolbarText.text = "?????? ?????????"
                                mViewDataBinding.estimateExpertInfoTitle.text = "????????? ??????????????????"
                                mViewDataBinding.estimateExpertInfo1.text = "????????? ?????? ????????? ?????? ??? ????????? ????????????????"
                                mViewDataBinding.estimateExpertInfo2.text =
                                    "???????????? ?????? ??? ?????? ????????? 1:1 ????????? ?????? ????????????,\n????????? ????????? ?????? ????????? ????????? ?????????."
                                mViewDataBinding.reservationExpertStatusTitle.text = "????????????"
                                mViewDataBinding.reservationExpertRecyclerView.visibility = View.GONE
                                mViewDataBinding.reservationExpertStatus.visibility = View.VISIBLE
                                mViewDataBinding.reservationExpertStatus.text = data[0].reserve_dt
                                mViewDataBinding.reservationExpertTimeTitle.text = "????????????"
                                mViewDataBinding.reservationExpertTimeDate.visibility = View.VISIBLE
                                mViewDataBinding.reservationExpertTimeDate.text = data[0].reserve_time.toString()
                                mViewDataBinding.reservationExpertTimezoneTitle.text = "???????????????"
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
                                    mViewDataBinding.reservationExpertHeadcountTitle.text = "????????????"
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
                                mViewDataBinding.reservationExpertReserveFinalPriceTitle.text = "?????? ?????? ??????"
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
                                mViewDataBinding.estimateExpertToolbarText.text = "????????????"

                                mViewDataBinding.estimateExpertInfoTitle.text = "????????? ?????????????????????."
                                mViewDataBinding.estimateExpertInfo1.text ="???????????? ???????????? ????????? 7????????? ?????? ?????? ??? ?????? ???????????? ?????? ???????????? ?????? ????????? ???????????????."
                                mViewDataBinding.estimateExpertInfo1.setTextColor(
                                    Color.parseColor(
                                        "#f96565"
                                    )
                                )
                                mViewDataBinding.estimateExpertInfo2.visibility = View.GONE

                                mViewDataBinding.reservationExpertStatusTitle.text = "????????????"
                                mViewDataBinding.reservationExpertRecyclerView.visibility = View.GONE
                                mViewDataBinding.reservationExpertStatus.visibility = View.VISIBLE
                                mViewDataBinding.reservationExpertStatus.text = data[0].reserve_dt
                                mViewDataBinding.reservationExpertTimeTitle.text = "????????????"
                                mViewDataBinding.reservationExpertTimeDate.visibility = View.VISIBLE
                                mViewDataBinding.reservationExpertTimeDate.text = data[0].reserve_time.toString()
                                mViewDataBinding.reservationExpertTimezoneTitle.text = "???????????????"
                                mViewDataBinding.reservationExpertTimezone.visibility = View.VISIBLE
                                mViewDataBinding.reservationExpertTimezone.text = data[0].reserve_time_term
                                if (data[0].expert_type == 1) {
                                    mViewDataBinding.reservationExpertHeadcountTitle.visibility = View.GONE
                                    mViewDataBinding.reservationExpertHeadcountDataTitle.visibility = View.GONE
                                } else {
                                    mViewDataBinding.reservationExpertHeadcountTitle.text = "????????????"
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