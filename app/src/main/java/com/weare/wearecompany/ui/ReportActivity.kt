package com.weare.wearecompany.ui

import android.content.Intent
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.weare.wearecompany.R
import com.weare.wearecompany.data.retrofit.bottomnav.estimate.sendManager
import com.weare.wearecompany.data.retrofit.chatting.chattingManager
import com.weare.wearecompany.databinding.ActivityReportBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.utils.RESPONSE_STATUS

class ReportActivity: BaseActivity<ActivityReportBinding>(
    R.layout.activity_report
),View.OnClickListener {
    private var expert_user_nickname: String = ""
    private var reportChack = -1
    private var type = -1
    private var chat_idx = ""
    private var claim_idx = ""
    private var defandant_idx = ""


    override fun onCreate() {

        val toobar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toobar)
        val ActionBar = supportActionBar!!
        ActionBar.setDisplayHomeAsUpEnabled(true)
        ActionBar.setDisplayShowTitleEnabled(false)

        setUp()
    }

    private fun setUp() {
        mViewDataBinding.linearLayout2.setOnClickListener(this)
        mViewDataBinding.reportUpload.setOnClickListener(this)
        chat_idx = intent.getStringExtra("chat_idx").toString()
        claim_idx = intent.getStringExtra("claim_idx").toString()
        defandant_idx = intent.getStringExtra("defandant_idx").toString()
        expert_user_nickname = intent.getStringExtra("expert_user_nickname").toString()
        type = intent.getIntExtra("type",0)
        mViewDataBinding.expertUserNickname.text = expert_user_nickname
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

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.linearLayout2 -> {
                val reportDialog:ReportDialog = ReportDialog(reportChack) {
                    when(it) {
                        0 -> {
                            mViewDataBinding.reportType.text = "거래/환불 분쟁 신고"
                            mViewDataBinding.reportTextLayout.visibility = View.VISIBLE
                            reportChack = 0
                        }
                        1 -> {
                            mViewDataBinding.reportType.text = "외부 거래 요구"
                            mViewDataBinding.reportTextLayout.visibility = View.VISIBLE
                            reportChack = 1
                        }
                        2 -> {
                            mViewDataBinding.reportType.text = "비매너 사용자"
                            mViewDataBinding.reportTextLayout.visibility = View.VISIBLE
                            reportChack = 2
                        }
                        3 -> {
                            mViewDataBinding.reportType.text = "음란성/선정성"
                            mViewDataBinding.reportTextLayout.visibility = View.VISIBLE
                            reportChack = 3
                        }
                        4 -> {
                            mViewDataBinding.reportType.text = "욕설/인신공격"
                            mViewDataBinding.reportTextLayout.visibility = View.VISIBLE
                            reportChack = 4
                        }
                        5 -> {
                            mViewDataBinding.reportType.text = "불필요한 채팅 도배"
                            mViewDataBinding.reportTextLayout.visibility = View.VISIBLE
                            reportChack = 5
                        }
                        6 -> {
                            mViewDataBinding.reportType.text = "개인정보 요구"
                            mViewDataBinding.reportTextLayout.visibility = View.VISIBLE
                            reportChack = 6
                        }
                        7 -> {
                            mViewDataBinding.reportType.text = "기타 다른문제"
                            mViewDataBinding.reportTextLayout.visibility = View.VISIBLE
                            reportChack = 7
                        }
                    }
                }
                reportDialog.show(supportFragmentManager, reportDialog.tag)
            }
            R.id.report_upload -> {
                if (mViewDataBinding.reportData.text.toString() == "") {
                    Toast.makeText(this,"신고내용을 입력해주세요.",Toast.LENGTH_SHORT).show()
                } else {
                    when(type) {
                        0 -> {
                            chattingManager.instance.report_reserve(
                                chat_idx,
                                claim_idx,
                                defandant_idx,
                                reportChack,
                                mViewDataBinding.reportData.text.toString(),
                                completion = { responseStatus ->
                                    when (responseStatus) {
                                        RESPONSE_STATUS.OKAY -> {
                                            val intent = Intent()
                                            setResult(4001, intent)
                                            finish()
                                        }
                                    }
                                })
                        }
                        1 -> {
                            chattingManager.instance.report_request(
                                chat_idx,
                                claim_idx,
                                defandant_idx,
                                reportChack,
                                mViewDataBinding.reportData.text.toString(),
                                completion = { responseStatus ->
                                    when (responseStatus) {
                                        RESPONSE_STATUS.OKAY -> {
                                            val intent = Intent()
                                            setResult(4001, intent)
                                            finish()
                                        }
                                    }
                                })
                        }
                    }
                }
            }
        }
    }
}