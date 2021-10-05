package com.weare.wearecompany.ui.bottommenu.estimate.send

import android.content.Intent
import android.net.Uri
import android.view.MenuItem
import android.view.View
import android.widget.Toast
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
import com.weare.wearecompany.data.retrofit.bottomnav.estimate.sendManager
import com.weare.wearecompany.databinding.ActivitySendStudioBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.ui.bottommenu.estimate.receive.payment.paymentActivity
import com.weare.wearecompany.ui.detail.DatailActivity
import com.weare.wearecompany.ui.detail.studio.reservation.ReservationStudioDialogRecyclerViewAdapter
import com.weare.wearecompany.utils.ESTIMATE
import java.text.DecimalFormat

class SendStudioActivity : BaseActivity<ActivitySendStudioBinding>(
    R.layout.activity_send_studio
), View.OnClickListener {

    private var reserve_idx: String = ""
    private var expert_idx: String = ""
    private var type: Int = -1
    private lateinit var dtlist: ArrayList<String>
    private lateinit var taglist: ArrayList<String>
    private var chatcheck = -1
    private lateinit var dateadapter: ReservationStudioDialogRecyclerViewAdapter
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

        mViewDataBinding.sendStudioToolbarRefundMenu.setOnClickListener(this)
        mViewDataBinding.estimateStudioChat.setOnClickListener(this)
        mViewDataBinding.sendStudioExpertInfoLayout.setOnClickListener(this)

                sendManager.instance.studioPage(reserve_idx, completion = { responseStatus, data ->
                    when (responseStatus) {
                        ESTIMATE.OKAY -> {
                            expert_idx = data[0].expert_idx
                            if (data[0].dt_status == 0) {
                                mViewDataBinding.sendStudioName.text = data[0].expert_name
                                mViewDataBinding.sendStudioExpertUserName.text = data[0].expert_user_name
                                mViewDataBinding.sendStudioRoom.text = data[0].room_name

                                taglist = ArrayList<String>()
                                val tag = data[0].expert_category.split(",")
                                for (i in tag) {
                                    taglist.add(i)
                                }
                                tagAdapter = SendTagRecyclerViewAdapter(taglist)
                                mViewDataBinding.sendStudioCategoryRecyclerview.layoutManager = LinearLayoutManager(
                                    this,
                                    LinearLayoutManager.HORIZONTAL, false
                                )
                                mViewDataBinding.sendStudioCategoryRecyclerview.adapter = tagAdapter

                                var multiTransformation = MultiTransformation(CenterCrop(),RoundedCorners(20))

                                Glide.with(MyApplication.instance)
                                    .load(data[0].room_image)
                                    .apply(RequestOptions.bitmapTransform(multiTransformation))
                                    .into(mViewDataBinding.sendStudioRoomImage)

                                dtlist = ArrayList<String>()
                                val dt = data[0].reserve_dt.split(",")
                                for (i in dt) {
                                    dtlist.add(i)
                                }
                                if (dtlist.size != 0) {
                                    mViewDataBinding.sendStudioRecyclerView.visibility = View.VISIBLE
                                    dateadapter = ReservationStudioDialogRecyclerViewAdapter(dtlist)
                                    mViewDataBinding.sendStudioRecyclerView.layoutManager =
                                        GridLayoutManager(this, 3)
                                    mViewDataBinding.sendStudioRecyclerView.adapter = dateadapter
                                }


                                mViewDataBinding.sendStudioContents.text =
                                    data[0].reserve_contents


                            }
                        }
                        ESTIMATE.NOT_USER -> {
                            Toast.makeText(this,"이미 지난 요청서 입니다.",Toast.LENGTH_SHORT).show()
                            finish()
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
                setResult(5001, intent)
                finish()
            }
        }

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.send_studio_expert_info_layout -> {
                var newIntent = Intent(this, DatailActivity::class.java)
                newIntent.putExtra("idx", expert_idx)
                startActivity(newIntent)
            }
            R.id.estimate_studio_ok -> {
                val newIntent = Intent(this, paymentActivity::class.java)
                newIntent.putExtra("reserve_idx", reserve_idx)
                newIntent.putExtra("type", 0)
                startActivityForResult(newIntent,5000)
            }
            R.id.estimate_studio_chat -> {
                var urll = "https://pf.kakao.com/_xlQxdys/chat"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(urll)
                startActivity(intent)
            }
            R.id.send_studio_toolbar_refund_menu -> {
                val cancellationdialog: CancellationBottomDialog = CancellationBottomDialog() {
                    when(it) {
                        1 -> {
                            sendManager.instance.reservedelete(reserve_idx,completion = {responseStatus ->
                                when(responseStatus) {
                                    ESTIMATE.OKAY -> {

                                        val intent = Intent()
                                        intent.putExtra("Cancellation", "ok")
                                        setResult(2004, intent)
                                        finish()

                                    }
                                }
                            })


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