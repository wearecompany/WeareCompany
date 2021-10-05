package com.weare.wearecompany.ui.bottommenu.mypage.request.receive

import android.content.Intent
import android.net.Uri
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.weare.wearecompany.R
import com.weare.wearecompany.data.main.Request.deta.expertarray
import com.weare.wearecompany.data.main.Request.receiveOneClickPage
import com.weare.wearecompany.data.retrofit.bottomnav.estimate.sendManager
import com.weare.wearecompany.data.retrofit.bottomnav.mypage.onclickManager
import com.weare.wearecompany.databinding.ActivityReceiveOnclickBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.ui.bottommenu.estimate.receive.payment.paymentActivity
import com.weare.wearecompany.ui.bottommenu.estimate.send.CancellationBottomDialog
import com.weare.wearecompany.ui.bottommenu.mypage.request.send.SendOneClickCaRecyclerViewAdapter
import com.weare.wearecompany.ui.bottommenu.mypage.request.send.SendOneClickImgRecyclerViewAdapter
import com.weare.wearecompany.utils.ESTIMATE
import java.text.DecimalFormat

class ReceiveOneClickActivity:BaseActivity<ActivityReceiveOnclickBinding>(
    R.layout.activity_receive_onclick
),View.OnClickListener {

    private var reserve_idx: String = ""
    private val dec = DecimalFormat("#,###")

    private lateinit var detaList: ArrayList<receiveOneClickPage>
    private lateinit var caAdapter: ReceiveOneClickCaRecyclerViewAdapter
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

        mViewDataBinding.receiveOnclickPayment.setOnClickListener(this)
        mViewDataBinding.requestOneclickChat.setOnClickListener(this)
        mViewDataBinding.receiveOneclickToolbarRefundMenu.setOnClickListener(this)

        onclickManager.instance.receivePage(reserve_idx,completion = {responseStatus, data ->
            when(responseStatus) {
                ESTIMATE.OKAY -> {
                    detaList = data

                    caAdapter = ReceiveOneClickCaRecyclerViewAdapter(detaList[0].expert_array)
                    mViewDataBinding.receiveOneclickCategoryRecyclerview.layoutManager =
                        LinearLayoutManager(
                            this,
                            LinearLayoutManager.HORIZONTAL, false
                        )
                    mViewDataBinding.receiveOneclickCategoryRecyclerview.adapter = caAdapter

                    caAdapter.setItemClickListener(object : ReceiveOneClickCaRecyclerViewAdapter.OnItemClickListener{
                        override fun onClick(v: View, position: Int, item: expertarray) {

                        }

                    })

                    if (detaList[0].thumbnail.size != 0) {
                        mViewDataBinding.receiveOneclickThumbnailLayout.visibility = View.VISIBLE

                        imgAdapter = SendOneClickImgRecyclerViewAdapter(detaList[0].thumbnail)
                        mViewDataBinding.receiveOneclickThumbnail.layoutManager =
                            LinearLayoutManager(
                                this,
                                LinearLayoutManager.HORIZONTAL, false
                            )
                        mViewDataBinding.receiveOneclickThumbnail.adapter = imgAdapter
                    }

                        mViewDataBinding.receiveOneclickProjectName.text = detaList[0].reserve_name
                        mViewDataBinding.receiveOneclickExpertDt.text = detaList[0].date
                        mViewDataBinding.receiveOneclickExpertTime.text = detaList[0].time
                        mViewDataBinding.receiveOneclickTimezone.text = detaList[0].timezone
                        mViewDataBinding.receiveOneclickContents.text = detaList[0].contents
                        mViewDataBinding.receiveOneclickPrice.text = dec.format(detaList[0].price)

                }
            }
        })
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(resultCode) {
            5001 -> {
                val intent = Intent()
                intent.putExtra("payment", "ok")
                setResult(3001, intent)
                finish()
            }
        }

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.receive_onclick_chat -> {
                var urll = "https://pf.kakao.com/_xlQxdys/chat"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(urll)
                startActivity(intent)
            }
            R.id.receive_onclick_payment -> {
                val newIntent = Intent(this, paymentActivity::class.java)
                newIntent.putExtra("reserve_idx", reserve_idx)
                newIntent.putExtra("type", 1)
                startActivityForResult(newIntent, 5000)
            }
            R.id.receive_oneclick_toolbar_refund_menu -> {
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
                            setResult(2003, intent)
                            finish()
                        }

                    }
                }
                cancellationdialog.show(supportFragmentManager, cancellationdialog.tag)
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