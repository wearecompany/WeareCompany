package com.weare.wearecompany.ui.bottommenu.estimate.progress.experthodel.progress

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
import com.weare.wearecompany.BuildConfig.SERVER_URL
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.retrofit.bottomnav.estimate.progressManager
import com.weare.wearecompany.data.retrofit.bottomnav.estimate.refundManager
import com.weare.wearecompany.databinding.ActivityProgressStudioBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.ui.bottommenu.estimate.progress.ProgressBottomRefundFragment
import com.weare.wearecompany.ui.bottommenu.estimate.refund.RefundDialog
import com.weare.wearecompany.ui.bottommenu.estimate.send.SendTagRecyclerViewAdapter
import com.weare.wearecompany.ui.chat.ChatActivity
import com.weare.wearecompany.ui.detail.DatailActivity
import com.weare.wearecompany.utils.ESTIMATE
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import java.text.DecimalFormat

class ProgressStudioActivity : BaseActivity<ActivityProgressStudioBinding>(
    R.layout.activity_progress_studio
), View.OnClickListener {

    private var reserve_idx: String = ""
    private var expert_idx: String = ""
    private var chatcheck: Int = -1
    private var reviewcheck: Int = -1

    private lateinit var taglist: ArrayList<String>

    private val dec = DecimalFormat("#,###")
    private lateinit var tagAdapter: SendTagRecyclerViewAdapter

    private var type = -1
    lateinit var socket: Socket

    override fun onCreate() {
        val toobar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toobar)
        val ActionBar = supportActionBar!!
        ActionBar.setDisplayHomeAsUpEnabled(true)
        ActionBar.setDisplayShowTitleEnabled(false)


        reserve_idx = intent.getStringExtra("reserve_idx").toString()
        chatcheck = intent.getIntExtra("chatbool", 0)
        reviewcheck = intent.getIntExtra("review_check", 0)

        setup()
    }


    private fun setup() {
        mViewDataBinding.progressChat.setOnClickListener(this)
        mViewDataBinding.progressTopManu.setOnClickListener(this)
        mViewDataBinding.progressStudioExpertInfoLayout.setOnClickListener(this)

        if (chatcheck == 1) {
            mViewDataBinding.progressChat.visibility = View.GONE
        }

        progressManager.instance.studioPage(
            reserve_idx,
            completion = { responseStatus, data ->
                when (responseStatus) {
                    ESTIMATE.OKAY -> {
                        expert_idx = data[0].expert_idx
                        /*if (data[0].refund_status == 1) {
                            mViewDataBinding.progressTopManu.visibility = View.GONE
                            mViewDataBinding.progressInfoTitle.text = "전문가에게 환불을 요청했습니다."
                            mViewDataBinding.progressInfo1.setTextColor(Color.parseColor("#8276f4"))
                            mViewDataBinding.progressInfo1.text = "전문가가 확인 후 환불이 진행됩니다.\n환불이 계속 진행되지 않는다면, 아래 [1:1 채팅하기] 버튼을 눌러 전문가에게 요청해 주세요."
                        } else if (data[0].refund_status == 2) {
                            mViewDataBinding.progressTopManu.visibility = View.GONE
                            mViewDataBinding.progressInfoTitle.text = "환불 접수가 완료되었습니다."
                            mViewDataBinding.progressInfo1.text = "*신용카드로 결제한 경우, 실제 환불 일자는 신용카드사에 따라\n차이가 있을 수 있습니다. 보다 정확한 사항은 카드사로\n문의하시기 바랍니다."
                            mViewDataBinding.progressInfo2.visibility = View.VISIBLE
                            mViewDataBinding.progressInfo2.text = "*계좌이체로 구매한 겨우, 지불하신 출금계좌로 입금되며\n영업일 기준으로 1-3일 소요됩니다."
                            mViewDataBinding.progressInfo1.setTextColor(Color.parseColor("#8276f4"))
                        } else {
                            if (reviewcheck == 1) {
                                mViewDataBinding.progressTopManu.visibility = View.GONE
                            } else {
                                mViewDataBinding.progressTopManu.visibility = View.VISIBLE
                                mViewDataBinding.progressInfo1.setTextColor(Color.parseColor("#f96565"))
                                mViewDataBinding.progressInfo2.visibility = View.GONE
                            }
                        }*/

                        mViewDataBinding.progressStudioName.text = data[0].expert_name
                        mViewDataBinding.progressStudioExpertUserName.text = data[0].expert_user_name
                        mViewDataBinding.progressStudioRoom.text = data[0].room_name

                        var multiTransformation = MultiTransformation(CenterCrop(),RoundedCorners(20))

                        Glide.with(MyApplication.instance)
                            .load(data[0].room_image)
                            .apply(RequestOptions.bitmapTransform(multiTransformation))
                            .into(mViewDataBinding.progressStudioRoomImage)

                        taglist = ArrayList<String>()
                        val tag = data[0].expert_category.split(",")
                        for (i in tag) {
                            taglist.add(i)
                        }
                        tagAdapter = SendTagRecyclerViewAdapter(taglist)
                        mViewDataBinding.progressStudioCategoryRecyclerview.layoutManager = LinearLayoutManager(
                            this,
                            LinearLayoutManager.HORIZONTAL, false
                        )
                        mViewDataBinding.progressStudioCategoryRecyclerview.adapter = tagAdapter

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

    private fun dateemit() {
        // 소켓 서버 연결
        socket = IO.socket(SERVER_URL)
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
        when (v?.id) {
            R.id.progress_studio_expert_info_layout -> {
                var newIntent = Intent(this, DatailActivity::class.java)
                newIntent.putExtra("idx", expert_idx)
                startActivity(newIntent)
            }
            R.id.progress_top_manu -> {
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
                                                try {
                                                    //TODO 액티비티 화면 재갱신 시키는 코드
                                                    val intent = intent
                                                    finish() //현재 액티비티 종료 실시
                                                    overridePendingTransition(
                                                        0,
                                                        0
                                                    ) //인텐트 애니메이션 없애기
                                                    startActivity(intent) //현재 액티비티 재실행 실시
                                                    overridePendingTransition(
                                                        0,
                                                        0
                                                    ) //인텐트 애니메이션 없애기
                                                } catch (e: Exception) {
                                                    e.printStackTrace()
                                                }
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
                R.id.progress_chat -> {
                    val newIntent = Intent(this, ChatActivity::class.java)
                    newIntent.putExtra("type",0)
                    newIntent.putExtra("Entrytype",0)
                    newIntent.putExtra("reserve_idx",reserve_idx)
                    startActivity(newIntent)
                }

        }
    }
}