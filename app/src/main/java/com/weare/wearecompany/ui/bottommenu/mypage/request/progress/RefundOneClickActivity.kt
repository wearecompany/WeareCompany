package com.weare.wearecompany.ui.bottommenu.mypage.request.progress

import android.content.Intent
import android.net.Uri
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.weare.wearecompany.R
import com.weare.wearecompany.data.main.Request.deta.expertarray
import com.weare.wearecompany.data.main.Request.progressOneClickPage
import com.weare.wearecompany.data.retrofit.bottomnav.mypage.onclickManager
import com.weare.wearecompany.databinding.ActivityRefundOneclickBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.ui.bottommenu.mypage.request.receive.ReceiveOneClickCaRecyclerViewAdapter
import com.weare.wearecompany.ui.bottommenu.mypage.request.send.SendOneClickImgRecyclerViewAdapter
import com.weare.wearecompany.utils.ESTIMATE
import io.socket.client.Socket
import java.text.DecimalFormat

class RefundOneClickActivity:BaseActivity<ActivityRefundOneclickBinding>(
    R.layout.activity_refund_oneclick
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

        mViewDataBinding.refundOneclickChat.setOnClickListener(this)

        when(type) {
            0 -> {
                onclickManager.instance.refundPage(reserve_idx, completion = { responseStatus, data ->
                    when(responseStatus) {
                        ESTIMATE.OKAY -> {
                            detaList = data

                            mViewDataBinding.refundOneclickTid.text = detaList[0].reserve_tid
                            mViewDataBinding.refundOneclickBillMethod.text = detaList[0].bill_method
                            mViewDataBinding.refundOneclickBillDate.text = detaList[0].bill_date

                            caAdapter = ReceiveOneClickCaRecyclerViewAdapter(detaList[0].expert_array)
                            mViewDataBinding.refundOneclickCategoryRecyclerview.layoutManager =
                                LinearLayoutManager(
                                    this,
                                    LinearLayoutManager.HORIZONTAL, false
                                )
                            mViewDataBinding.refundOneclickCategoryRecyclerview.adapter = caAdapter

                            caAdapter.setItemClickListener(object : ReceiveOneClickCaRecyclerViewAdapter.OnItemClickListener{
                                override fun onClick(v: View, position: Int, item: expertarray) {

                                }

                            })

                            if (detaList[0].thumbnail.size != 0) {
                                mViewDataBinding.refundOneclickThumbnailLayout.visibility = View.VISIBLE
                                imgAdapter = SendOneClickImgRecyclerViewAdapter(detaList[0].thumbnail)
                                mViewDataBinding.refundOneclickThumbnail.layoutManager =
                                    LinearLayoutManager(
                                        this,
                                        LinearLayoutManager.HORIZONTAL, false
                                    )
                                mViewDataBinding.refundOneclickThumbnail.adapter = imgAdapter
                            }

                            mViewDataBinding.refundOneclickProjectName.text = detaList[0].reserve_name
                            mViewDataBinding.refundOneclickExpertDt.text = detaList[0].reserve_dt
                            mViewDataBinding.refundOneclickExpertTime.text = detaList[0].reserve_time.toString()
                            mViewDataBinding.refundOneclickTimezone.text = detaList[0].reserve_time_term.toString()
                            mViewDataBinding.refundOneclickContents.text = detaList[0].reserve_contents
                            mViewDataBinding.refundOneclickPrice.text = dec.format(detaList[0].reserve_price)
                        }
                    }
                })
            }

            1 -> {
                onclickManager.instance.refundokPage(reserve_idx, completion = { responseStatus, data ->
                    when(responseStatus) {
                        ESTIMATE.OKAY -> {
                            detaList = data

                            mViewDataBinding.refundInfoTitle.text = "취소 접수가 완료되었습니다"

                            mViewDataBinding.refundInfo1.text = "*신용카드로 결제한 경우, 실제 환불 일자는 신용카드사에 따라\n차이가 있을 수 있습니다. 보다 정확한 사항은 카드사로\n문의하시기 바랍니다."
                            mViewDataBinding.refundInfo2.visibility = View.VISIBLE
                            mViewDataBinding.refundInfo2.text = "*계좌이체로 구매한 경우, 지불하신 출금계좌로 입금되며\n영업일 기준으로 1-3일 소요됩니다."
                            mViewDataBinding.refundOneclickTid.text = detaList[0].reserve_tid
                            mViewDataBinding.refundOneclickBillMethod.text = detaList[0].bill_method
                            mViewDataBinding.refundOneclickBillDate.text = detaList[0].bill_date
                            mViewDataBinding.refundOneclickRefundMoneyLayout.visibility = View.VISIBLE
                            mViewDataBinding.refundOneclickRefundMoney.text = dec.format(detaList[0].refund_money)

                            caAdapter = ReceiveOneClickCaRecyclerViewAdapter(detaList[0].expert_array)
                            mViewDataBinding.refundOneclickCategoryRecyclerview.layoutManager =
                                LinearLayoutManager(
                                    this,
                                    LinearLayoutManager.HORIZONTAL, false
                                )
                            mViewDataBinding.refundOneclickCategoryRecyclerview.adapter = caAdapter

                            caAdapter.setItemClickListener(object : ReceiveOneClickCaRecyclerViewAdapter.OnItemClickListener{
                                override fun onClick(v: View, position: Int, item: expertarray) {

                                }

                            })

                            if (detaList[0].thumbnail.size != 0) {
                                mViewDataBinding.refundOneclickThumbnailLayout.visibility = View.VISIBLE
                                imgAdapter = SendOneClickImgRecyclerViewAdapter(detaList[0].thumbnail)
                                mViewDataBinding.refundOneclickThumbnail.layoutManager =
                                    LinearLayoutManager(
                                        this,
                                        LinearLayoutManager.HORIZONTAL, false
                                    )
                                mViewDataBinding.refundOneclickThumbnail.adapter = imgAdapter
                            }

                            mViewDataBinding.refundOneclickProjectName.text = detaList[0].reserve_name
                            mViewDataBinding.refundOneclickExpertDt.text = detaList[0].reserve_dt
                            mViewDataBinding.refundOneclickExpertTime.text = detaList[0].reserve_time.toString()
                            mViewDataBinding.refundOneclickTimezone.text = detaList[0].reserve_time_term.toString()
                            mViewDataBinding.refundOneclickContents.text = detaList[0].reserve_contents
                            mViewDataBinding.refundOneclickPrice.text = dec.format(detaList[0].reserve_price)
                        }
                    }
                })
            }


        }
    }

    override fun onClick(v: View?) {
       when(v?.id) {
           R.id.refund_oneclick_chat -> {
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