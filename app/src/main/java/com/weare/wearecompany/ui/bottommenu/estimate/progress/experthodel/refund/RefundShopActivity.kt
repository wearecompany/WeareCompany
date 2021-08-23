package com.weare.wearecompany.ui.bottommenu.estimate.progress.experthodel.refund

import android.content.Intent
import android.graphics.Color
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.weare.wearecompany.R
import com.weare.wearecompany.data.retrofit.bottomnav.estimate.progressManager
import com.weare.wearecompany.databinding.ActivityRefundShopBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.ui.chat.ChatActivity
import com.weare.wearecompany.utils.ESTIMATE

class RefundShopActivity : BaseActivity<ActivityRefundShopBinding>(
    R.layout.activity_refund_shop
), View.OnClickListener {

    private var reserve_idx: String = ""
    private var chatcheck: Int = -1

    override fun onCreate() {

        val toobar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toobar)
        val ActionBar = supportActionBar!!
        ActionBar.setDisplayHomeAsUpEnabled(true)
        ActionBar.setDisplayShowTitleEnabled(false)

        reserve_idx = intent.getStringExtra("reserve_idx").toString()
        chatcheck = intent.getIntExtra("chatbool", 0)

        setup()

    }

    private fun setup() {
        mViewDataBinding.progressChat.setOnClickListener(this)
        mViewDataBinding.progressTopManu.visibility = View.GONE

        if (chatcheck == 1) {
            mViewDataBinding.progressChat.visibility = View.GONE
        }

        progressManager.instance.shopPage(
            reserve_idx,
            completion = { responseStatus, data ->
                when (responseStatus) {
                    ESTIMATE.OKAY -> {

                        if (data[0].refund_status == 2) {
                            mViewDataBinding.progressTopManu.visibility = View.GONE
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
                        mViewDataBinding.startDt.text = data[0].start_dt
                        mViewDataBinding.endDt.text = data[0].end_dt
                        mViewDataBinding.reserveTimeTerm.text = data[0].reserve_time_term.toString()
                        if (data[0].reserve_contents != "") {
                            mViewDataBinding.reserveAddContentsLayout.visibility = View.VISIBLE
                            mViewDataBinding.reserveContents.text = data[0].reserve_contents
                        }
                        if (data[0].reserve_add_contents != "") {
                            mViewDataBinding.reserveAddContents.visibility = View.VISIBLE
                            mViewDataBinding.reserveAddContents.text = data[0].reserve_add_contents
                        }
                        mViewDataBinding.reserveFinalPrice.text = data[0].reserve_final_price.toString()
                    }
                }
            })
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.progress_chat -> {
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