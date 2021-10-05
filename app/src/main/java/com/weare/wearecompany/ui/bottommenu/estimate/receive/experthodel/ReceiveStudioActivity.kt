package com.weare.wearecompany.ui.bottommenu.estimate.receive.experthodel

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
import com.weare.wearecompany.data.retrofit.bottomnav.estimate.receiveManager
import com.weare.wearecompany.data.retrofit.bottomnav.estimate.sendManager
import com.weare.wearecompany.databinding.ActivityReceiveStudioBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.ui.bottommenu.estimate.receive.payment.paymentActivity
import com.weare.wearecompany.ui.bottommenu.estimate.send.CancellationBottomDialog
import com.weare.wearecompany.ui.bottommenu.estimate.send.SendTagRecyclerViewAdapter
import com.weare.wearecompany.ui.chat.ChatActivity
import com.weare.wearecompany.ui.detail.DatailActivity
import com.weare.wearecompany.ui.detail.studio.reservation.ReservationStudioDialogRecyclerViewAdapter
import com.weare.wearecompany.utils.ESTIMATE
import java.text.DecimalFormat

class ReceiveStudioActivity:BaseActivity<ActivityReceiveStudioBinding>(
    R.layout.activity_receive_studio
),View.OnClickListener {

    private var reserve_idx: String = ""
    private var expert_idx: String = ""
    private var type: Int = -1
    private lateinit var dtlist: ArrayList<String>
    private lateinit var taglist: ArrayList<String>
    private var chatcheck = -1
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
        mViewDataBinding.receiveStudioPayment.setOnClickListener(this)
        mViewDataBinding.receiveStudioToolbarRefundMenu.setOnClickListener(this)
        mViewDataBinding.receiveStudioChat.setOnClickListener(this)
        mViewDataBinding.receiveStudioExpertInfoLayout.setOnClickListener(this)

                receiveManager.instance.studioPage(
                    reserve_idx,
                    completion = { responseStatus, data ->
                        when (responseStatus) {
                            ESTIMATE.OKAY -> {
                                expert_idx = data[0].expert_idx
                                mViewDataBinding.receiveStudioRoom.text = data[0].room_name
                                mViewDataBinding.receiveStudioDt.text = data[0].reserve_dt
                                mViewDataBinding.receiveStudioTime.text = data[0].reserve_time.toString()
                                mViewDataBinding.receiveStudioTimezone.text = data[0].reserve_time_term
                                mViewDataBinding.receiveStudioContents.text = data[0].reserve_contents

                                taglist = ArrayList<String>()
                                val tag = data[0].expert_category.split(",")
                                for (i in tag) {
                                    taglist.add(i)
                                }
                                tagAdapter = SendTagRecyclerViewAdapter(taglist)
                                mViewDataBinding.receiveStudioCategoryRecyclerview.layoutManager = LinearLayoutManager(
                                    this,
                                    LinearLayoutManager.HORIZONTAL, false
                                )
                                mViewDataBinding.receiveStudioCategoryRecyclerview.adapter = tagAdapter

                                var multiTransformation = MultiTransformation(
                                    CenterCrop(),
                                    RoundedCorners(20)
                                )

                                Glide.with(MyApplication.instance)
                                    .load(data[0].room_image)
                                    .apply(RequestOptions.bitmapTransform(multiTransformation))
                                    .into(mViewDataBinding.sendStudioRoomImage)

                                if (data[0].reserve_add_contents != ""){
                                    mViewDataBinding.receiveStudioAddContentsLayout.visibility = View.VISIBLE
                                    mViewDataBinding.receiveStudioAddContents.text = data[0].reserve_add_contents
                                }

                                mViewDataBinding.receiveStudioFinalPrice.text = dec.format(data[0].reserve_price)
                                if(chatcheck == 1) {
                                    mViewDataBinding.receiveStudioChat.visibility = View.GONE
                                }
                            }
                            ESTIMATE.NOT_USER -> {
                                Toast.makeText(this,"이미 지난 요청서 입니다.", Toast.LENGTH_SHORT).show()
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
                setResult(3001, intent)
                finish()
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

    override fun onClick(v: View?) {

        when(v?.id) {
            R.id.receive_studio_expert_info_layout -> {
                var newIntent = Intent(this, DatailActivity::class.java)
                newIntent.putExtra("idx", expert_idx)
                startActivity(newIntent)
            }
            R.id.receive_studio_payment -> {
                val newIntent = Intent(this, paymentActivity::class.java)
                newIntent.putExtra("reserve_idx", reserve_idx)
                newIntent.putExtra("type", 0)
                startActivityForResult(newIntent,5000)
            }
            R.id.receive_studio_chat -> {
                var urll = "https://pf.kakao.com/_xlQxdys/chat"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(urll)
                startActivity(intent)
            }
            R.id.receive_studio_toolbar_refund_menu -> {
                val cancellationdialog: CancellationBottomDialog = CancellationBottomDialog() {
                    when(it) {
                        1 -> {
                            sendManager.instance.reservedelete(reserve_idx,completion = {responseStatus ->
                                when(responseStatus) {
                                    ESTIMATE.OKAY -> {

                                        val intent = Intent()
                                        setResult(2003, intent)
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
}