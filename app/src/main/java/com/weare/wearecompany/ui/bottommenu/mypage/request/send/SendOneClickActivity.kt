package com.weare.wearecompany.ui.bottommenu.mypage.request.send

import android.content.Intent
import android.net.Uri
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.weare.wearecompany.R
import com.weare.wearecompany.data.main.Request.deta.sendoneclickpage
import com.weare.wearecompany.data.retrofit.bottomnav.estimate.sendManager
import com.weare.wearecompany.data.retrofit.bottomnav.mypage.onclickManager
import com.weare.wearecompany.databinding.ActivitySendOnclickBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.ui.bottommenu.estimate.send.CancellationBottomDialog
import com.weare.wearecompany.utils.ESTIMATE
import java.text.DecimalFormat

class SendOneClickActivity : BaseActivity<ActivitySendOnclickBinding>(
    R.layout.activity_send_onclick
), View.OnClickListener {

    private var reserve_idx: String = ""
    private val dec = DecimalFormat("#,###")

    private lateinit var detaList: ArrayList<sendoneclickpage>
    private lateinit var caAdapter: SendOneClickCaRecyclerViewAdapter
    private lateinit var imgAdapter: SendOneClickImgRecyclerViewAdapter

    override fun onCreate() {

        val toobar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toobar)
        val ActionBar = supportActionBar!!
        ActionBar.setDisplayHomeAsUpEnabled(true)
        ActionBar.setDisplayShowTitleEnabled(false)

        reserve_idx = intent.getStringExtra("reserve_idx").toString()

        setup()

    }

    private fun setup() {

        mViewDataBinding.sendOneclickChat.setOnClickListener(this)
        mViewDataBinding.requestOnclickToolbarRefundMenu.setOnClickListener(this)

        onclickManager.instance.sendPage(reserve_idx, completion = { responseStatus, data ->
            when (responseStatus) {
                ESTIMATE.OKAY -> {

                    detaList = data

                    caAdapter = SendOneClickCaRecyclerViewAdapter(detaList[0].expert_type)
                    mViewDataBinding.requestOneclickCategory.layoutManager =
                        LinearLayoutManager(
                            this,
                            LinearLayoutManager.HORIZONTAL, false
                        )
                    mViewDataBinding.requestOneclickCategory.adapter = caAdapter

                    if (detaList[0].thumbnail.isNotEmpty()) {
                        mViewDataBinding.requestOneclickThumbnailLayout.visibility = View.VISIBLE

                        imgAdapter = SendOneClickImgRecyclerViewAdapter(detaList[0].thumbnail)
                        mViewDataBinding.requestOneclickThumbnail.layoutManager =
                            LinearLayoutManager(
                                this,
                                LinearLayoutManager.HORIZONTAL, false
                            )
                        mViewDataBinding.requestOneclickThumbnail.adapter = imgAdapter
                    }


                    mViewDataBinding.requestOneclickDatetime.text = detaList[0].send_time
                    mViewDataBinding.requestOneclickContents.text = detaList[0].contents
                    mViewDataBinding.requestOneclickPrice.text =
                        dec.format(detaList[0].price.toInt())


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

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.send_oneclick_chat -> {
                var urll = "https://pf.kakao.com/_xlQxdys/chat"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(urll)
                startActivity(intent)
            }
            R.id.request_onclick_toolbar_refund_menu -> {
                val cancellationdialog: CancellationBottomDialog = CancellationBottomDialog() {
                    when (it) {
                        1 -> {
                            sendManager.instance.reservedelete(
                                reserve_idx,
                                completion = { responseStatus ->
                                    when (responseStatus) {
                                        ESTIMATE.OKAY -> {

                                        }
                                    }
                                })
                            val intent = Intent()
                            intent.putExtra("Cancellation", "ok")
                            setResult(2004, intent)
                            finish()
                        }

                    }
                }
                cancellationdialog.show(supportFragmentManager, cancellationdialog.tag)
            }

        }
    }
}