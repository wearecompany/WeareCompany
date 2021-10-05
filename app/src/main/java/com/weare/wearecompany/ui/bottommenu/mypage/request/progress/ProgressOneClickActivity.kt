package com.weare.wearecompany.ui.bottommenu.mypage.request.progress

import android.content.Intent
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
import com.weare.wearecompany.BuildConfig
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.main.Request.deta.expertarray
import com.weare.wearecompany.data.main.Request.progressOneClickPage
import com.weare.wearecompany.data.retrofit.bottomnav.estimate.progressManager
import com.weare.wearecompany.data.retrofit.bottomnav.estimate.refundManager
import com.weare.wearecompany.data.retrofit.bottomnav.mypage.onclickManager
import com.weare.wearecompany.databinding.ActivityProgressOneclickBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.ui.bottommenu.estimate.progress.ProgressBottomRefundFragment
import com.weare.wearecompany.ui.bottommenu.estimate.refund.RefundDialog
import com.weare.wearecompany.ui.bottommenu.estimate.send.SendTagRecyclerViewAdapter
import com.weare.wearecompany.ui.bottommenu.mypage.request.receive.ReceiveOneClickCaRecyclerViewAdapter
import com.weare.wearecompany.ui.bottommenu.mypage.request.send.SendOneClickImgRecyclerViewAdapter
import com.weare.wearecompany.utils.ESTIMATE
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import java.text.DecimalFormat

class ProgressOneClickActivity:BaseActivity<ActivityProgressOneclickBinding>(
    R.layout.activity_progress_oneclick
),View.OnClickListener {

    private var reserve_idx: String = ""
    private val dec = DecimalFormat("#,###")

    lateinit var socket: Socket
    private var type = -1
    private lateinit var detaList: ArrayList<progressOneClickPage>
    private lateinit var caAdapter: ReceiveOneClickCaRecyclerViewAdapter
    private lateinit var imgAdapter: SendOneClickImgRecyclerViewAdapter

    override fun onCreate() {

        val toobar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toobar)
        val ActionBar = supportActionBar!!
        ActionBar.setDisplayHomeAsUpEnabled(true)
        ActionBar.setDisplayShowTitleEnabled(false)

        type = intent.getIntExtra("type", 0)
        reserve_idx = intent.getStringExtra("reserve_idx").toString()

        setup()

    }

    private fun setup() {

        mViewDataBinding.progressOneclickTopMenu.setOnClickListener(this)
        mViewDataBinding.progressOneclickChat.setOnClickListener(this)

        when(type) {
            2 -> {
                onclickManager.instance.progressPage(reserve_idx, completion = {responseStatus, data ->
                    when(responseStatus) {
                        ESTIMATE.OKAY -> {
                            detaList = data

                            mViewDataBinding.progressOneclickTid.text = detaList[0].reserve_tid
                            mViewDataBinding.progressOneclickBillMethod.text = detaList[0].bill_method
                            mViewDataBinding.progressOneclickBillDate.text = detaList[0].bill_date

                            caAdapter = ReceiveOneClickCaRecyclerViewAdapter(detaList[0].expert_array)
                            mViewDataBinding.progressOneclickCategoryRecyclerview.layoutManager =
                                LinearLayoutManager(
                                    this,
                                    LinearLayoutManager.HORIZONTAL, false
                                )
                            mViewDataBinding.progressOneclickCategoryRecyclerview.adapter = caAdapter

                            caAdapter.setItemClickListener(object : ReceiveOneClickCaRecyclerViewAdapter.OnItemClickListener{
                                override fun onClick(v: View, position: Int, item: expertarray) {

                                }

                            })

                            if (detaList[0].thumbnail.size != 0) {
                                mViewDataBinding.progressOneclickThumbnailLayout.visibility = View.VISIBLE
                                imgAdapter = SendOneClickImgRecyclerViewAdapter(detaList[0].thumbnail)
                                mViewDataBinding.progressOneclickThumbnail.layoutManager =
                                    LinearLayoutManager(
                                        this,
                                        LinearLayoutManager.HORIZONTAL, false
                                    )
                                mViewDataBinding.progressOneclickThumbnail.adapter = imgAdapter
                            }

                            mViewDataBinding.progressOneclickProjectName.text = detaList[0].reserve_name
                            mViewDataBinding.progressOneclickExpertDt.text = detaList[0].reserve_dt
                            mViewDataBinding.progressOneclickExpertTime.text = detaList[0].reserve_time.toString()
                            mViewDataBinding.progressOneclickTimezone.text = detaList[0].reserve_time_term.toString()
                            mViewDataBinding.progressOneclickContents.text = detaList[0].reserve_contents
                            mViewDataBinding.progressOneclickPrice.text = dec.format(detaList[0].reserve_price)
                        }
                    }
                })
            }
            3 ->{
                onclickManager.instance.progressOkPage(reserve_idx, completion = {responseStatus, data ->
                    when(responseStatus) {
                        ESTIMATE.OKAY -> {
                            detaList = data

                            mViewDataBinding.progressOneclickTid.text = detaList[0].reserve_tid
                            mViewDataBinding.progressOneclickBillMethod.text = detaList[0].bill_method
                            mViewDataBinding.progressOneclickBillDate.text = detaList[0].bill_date

                            caAdapter = ReceiveOneClickCaRecyclerViewAdapter(detaList[0].expert_array)
                            mViewDataBinding.progressOneclickCategoryRecyclerview.layoutManager =
                                LinearLayoutManager(
                                    this,
                                    LinearLayoutManager.HORIZONTAL, false
                                )
                            mViewDataBinding.progressOneclickCategoryRecyclerview.adapter = caAdapter

                            caAdapter.setItemClickListener(object : ReceiveOneClickCaRecyclerViewAdapter.OnItemClickListener{
                                override fun onClick(v: View, position: Int, item: expertarray) {

                                }

                            })

                            if (detaList[0].thumbnail.size != 0) {
                                mViewDataBinding.progressOneclickThumbnailLayout.visibility = View.VISIBLE
                                imgAdapter = SendOneClickImgRecyclerViewAdapter(detaList[0].thumbnail)
                                mViewDataBinding.progressOneclickThumbnail.layoutManager =
                                    LinearLayoutManager(
                                        this,
                                        LinearLayoutManager.HORIZONTAL, false
                                    )
                                mViewDataBinding.progressOneclickThumbnail.adapter = imgAdapter
                            }

                            mViewDataBinding.progressOneclickProjectName.text = detaList[0].reserve_name
                            mViewDataBinding.progressOneclickExpertDt.text = detaList[0].reserve_dt
                            mViewDataBinding.progressOneclickExpertTime.text = detaList[0].reserve_time.toString()
                            mViewDataBinding.progressOneclickTimezone.text = detaList[0].reserve_time_term.toString()
                            mViewDataBinding.progressOneclickContents.text = detaList[0].reserve_contents
                            mViewDataBinding.progressOneclickPrice.text = dec.format(detaList[0].reserve_price)
                        }
                    }
                })
            }
            4 ->{
                onclickManager.instance.reviewPage(reserve_idx, completion = {responseStatus, data ->
                    when(responseStatus) {
                        ESTIMATE.OKAY -> {
                            detaList = data

                            mViewDataBinding.progressOneclickTid.text = detaList[0].reserve_tid
                            mViewDataBinding.progressOneclickBillMethod.text = detaList[0].bill_method
                            mViewDataBinding.progressOneclickBillDate.text = detaList[0].bill_date

                            caAdapter = ReceiveOneClickCaRecyclerViewAdapter(detaList[0].expert_array)
                            mViewDataBinding.progressOneclickCategoryRecyclerview.layoutManager =
                                LinearLayoutManager(
                                    this,
                                    LinearLayoutManager.HORIZONTAL, false
                                )
                            mViewDataBinding.progressOneclickCategoryRecyclerview.adapter = caAdapter

                            caAdapter.setItemClickListener(object : ReceiveOneClickCaRecyclerViewAdapter.OnItemClickListener{
                                override fun onClick(v: View, position: Int, item: expertarray) {

                                }

                            })

                            if (detaList[0].thumbnail.size != 0) {
                                mViewDataBinding.progressOneclickThumbnailLayout.visibility = View.VISIBLE
                                imgAdapter = SendOneClickImgRecyclerViewAdapter(detaList[0].thumbnail)
                                mViewDataBinding.progressOneclickThumbnail.layoutManager =
                                    LinearLayoutManager(
                                        this,
                                        LinearLayoutManager.HORIZONTAL, false
                                    )
                                mViewDataBinding.progressOneclickThumbnail.adapter = imgAdapter
                            }

                            mViewDataBinding.progressOneclickProjectName.text = detaList[0].reserve_name
                            mViewDataBinding.progressOneclickExpertDt.text = detaList[0].reserve_dt
                            mViewDataBinding.progressOneclickExpertTime.text = detaList[0].reserve_time.toString()
                            mViewDataBinding.progressOneclickTimezone.text = detaList[0].reserve_time_term.toString()
                            mViewDataBinding.progressOneclickContents.text = detaList[0].reserve_contents
                            mViewDataBinding.progressOneclickPrice.text = dec.format(detaList[0].reserve_price)
                        }
                    }
                })
            }

        }
    }

    private fun dateemit() {
        // 소켓 서버 연결
        socket = IO.socket(BuildConfig.CHAT_URL)
        socket.connect()

        val `object2` = JSONObject()
        `object2`.put("type", 0)
        `object2`.put("reserve_idx", reserve_idx)
        `object2`.put("request_idx", "")
        `object2`.put("request_log_idx", "")

        socket?.emit("notiUpdate", object2)

        socket.disconnect()
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.progress_oneclick_top_menu -> {
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

                                                val intent = Intent()
                                                setResult(2001, intent)
                                                finish()
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
            R.id.progress_oneclick_chat -> {
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