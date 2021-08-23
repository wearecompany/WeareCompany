package com.weare.wearecompany.ui.bottommenu.estimate.progress.experthodel.progress

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
import com.weare.wearecompany.data.retrofit.bottomnav.estimate.progressManager
import com.weare.wearecompany.data.retrofit.bottomnav.estimate.refundManager
import com.weare.wearecompany.databinding.ActivityProgressManyBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.ui.bottommenu.estimate.progress.ProgressBottomRefundFragment
import com.weare.wearecompany.ui.bottommenu.estimate.refund.RefundDialog
import com.weare.wearecompany.ui.bottommenu.estimate.send.SendRequestRecyclerViewAdapter
import com.weare.wearecompany.ui.bottommenu.estimate.send.SendTagRecyclerViewAdapter
import com.weare.wearecompany.ui.chat.ChatActivity
import com.weare.wearecompany.utils.API
import com.weare.wearecompany.utils.ESTIMATE
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import java.text.DecimalFormat

class ProgressManyActivity : BaseActivity<ActivityProgressManyBinding>(
    R.layout.activity_progress_many
), View.OnClickListener {

    private var request_idx:String = ""
    private var request_log_idx:String = ""
    private var chatcheck: Int = -1
    lateinit var socket: Socket

    private lateinit var taglist: ArrayList<String>
    private lateinit var tagAdapter: SendTagRecyclerViewAdapter

    private lateinit var dataAdapter : ProgressManyRecyclerViewAdapter

    private val dec = DecimalFormat("#,###")

    override fun onCreate() {
        val toobar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toobar)
        val ActionBar = supportActionBar!!
        ActionBar.setDisplayHomeAsUpEnabled(true)
        ActionBar.setDisplayShowTitleEnabled(false)

        request_idx = intent.getStringExtra("request_idx").toString()
        request_log_idx = intent.getStringExtra("log_idx").toString()
        chatcheck = intent.getIntExtra("chatbool",0)

        setup()
    }

    private fun setup() {

        mViewDataBinding.progressChat.setOnClickListener(this)
        mViewDataBinding.progressTopManu.setOnClickListener(this)

        if (chatcheck == 1) {
            mViewDataBinding.progressChat.visibility = View.GONE
        }

        progressManager.instance.manyPage(
            request_idx,request_log_idx,
            completion = { responseStatus, data ->
                when (responseStatus) {
                    ESTIMATE.OKAY -> {

                        if (data[0].refund_status == 1) {
                            mViewDataBinding.progressTopManu.visibility = View.GONE
                            mViewDataBinding.progressInfoTitle.text = "전문가에게 환불을 요청했습니다."
                            mViewDataBinding.progressInfo1.setTextColor(Color.parseColor("#8276f4"))
                            mViewDataBinding.progressInfo1.text = "전문가가 확인 후 환불이 진행됩니다.\n환불이 계속 진행되지 않는다면, 아래 [1:1 채팅하기] 버튼을 눌러 전문가에게 요청해 주세요."
                        } else if(data[0].refund_status == 2) {

                        }


                        mViewDataBinding.reserveTid.text = data[0].reserve_tid
                        mViewDataBinding.billMethod.text = data[0].bill_method
                        mViewDataBinding.billDate.text = data[0].bill_date
                        mViewDataBinding.price.text = dec.format(data[0].price)

                        mViewDataBinding.progressManyExpertName.text = data[0].expert_name
                        mViewDataBinding.progressManyExpertPlace.text = data[0].expert_place
                        mViewDataBinding.progressManyExpertPrice.text = data[0].expert_price

                        taglist = ArrayList<String>()
                        taglist.add(data[0].expert_type)
                        taglist.add(data[0].expert_category)

                        tagAdapter = SendTagRecyclerViewAdapter(taglist)
                        mViewDataBinding.progressManyCategoryRecyclerview.layoutManager = LinearLayoutManager(
                            this,
                            LinearLayoutManager.HORIZONTAL, false
                        )
                        mViewDataBinding.progressManyCategoryRecyclerview.adapter = tagAdapter

                        var multiTransformation_1 = MultiTransformation(CenterCrop(), RoundedCorners(20))
                        if (data[0].user_thumbnail != "") {
                            Glide.with(MyApplication.instance)
                                .load(data[0].user_thumbnail)
                                .placeholder(R.drawable.not_load_image)
                                .fallback(R.drawable.mypage_user_not_image)
                                .apply(RequestOptions.bitmapTransform(multiTransformation_1))
                                .into(mViewDataBinding.progressManyExpertImage)
                        } else {
                            mViewDataBinding.progressManyExpertImage.setImageResource(R.drawable.mypage_user_not_image)
                        }


                        var multiTransformation_2 = MultiTransformation(CenterCrop(), RoundedCorners(20))

                        /*Glide.with(MyApplication.instance)
                            .load(data[0].user_thumbnail)
                            .skipMemoryCache(true)
                            .placeholder(R.drawable.loading_image)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .fallback(R.drawable.mypage_user_not_image)
                            .apply(RequestOptions.bitmapTransform(multiTransformation))
                            .into(mViewDataBinding.manyUserThumbnail)*/

                        if (data[0].thumbnail.isEmpty()){
                            mViewDataBinding.userThumbnailListLayout.visibility = View.GONE
                        } else {
                            mViewDataBinding.userThumbnailListLayout.visibility = View.VISIBLE
                            dataAdapter = ProgressManyRecyclerViewAdapter(data)
                            mViewDataBinding.userThumbnailList.layoutManager = LinearLayoutManager(
                                this,
                                LinearLayoutManager.HORIZONTAL, false
                            )
                            mViewDataBinding.userThumbnailList.adapter = dataAdapter
                        }

                        mViewDataBinding.datetime.text = data[0].datetime
                        mViewDataBinding.requestContents.text = data[0].request_contents
                        mViewDataBinding.responseContents.text = data[0].response_contents
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

    private fun notUpdate() {
        // 소켓 서버 연결
        socket = IO.socket(API.REFUND_URL)
        socket.connect()

        val noti = JSONObject()
        noti.put("type", 1)
        noti.put("reserve_idx", "")
        noti.put("request_idx", request_idx)
        noti.put("request_log_idx", request_log_idx)

        socket.emit("notiUpdate",noti)
        socket.disconnect()

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.progress_chat -> {
                val newIntent = Intent(this, ChatActivity::class.java)
                newIntent.putExtra("type",1)
                newIntent.putExtra("Entrytype",0)
                newIntent.putExtra("request_idx",request_idx)
                newIntent.putExtra("log_idx",request_log_idx)
                startActivity(newIntent)
            }
            R.id.progress_top_manu -> {
                val refundFragment: ProgressBottomRefundFragment = ProgressBottomRefundFragment {
                    when (it) {
                        0 -> {
                            val refundDialog: RefundDialog = RefundDialog() {
                                refundManager.instance.refundrequest(
                                    request_idx,
                                    request_log_idx,
                                    it,
                                    completion = { responseStatus ->
                                        when (responseStatus) {
                                            ESTIMATE.OKAY -> {
                                                notUpdate()
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
        }
    }
}