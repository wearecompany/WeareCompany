package com.weare.wearecompany.ui.bottommenu.estimate.send.experthodel

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
import com.weare.wearecompany.databinding.ActivitySendModelBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.ui.bottommenu.estimate.receive.payment.paymentActivity
import com.weare.wearecompany.ui.bottommenu.estimate.send.CancellationBottomDialog
import com.weare.wearecompany.ui.bottommenu.estimate.send.SendTagRecyclerViewAdapter
import com.weare.wearecompany.ui.chat.ChatActivity
import com.weare.wearecompany.ui.detail.model.ModelActivity
import com.weare.wearecompany.ui.detail.photo.PhotoActivity
import com.weare.wearecompany.ui.detail.studio.reservation.ReservationStudioDialogRecyclerViewAdapter
import com.weare.wearecompany.utils.ESTIMATE
import java.text.DecimalFormat

class SendModelActivity:BaseActivity<ActivitySendModelBinding>(
    R.layout.activity_send_model
),View.OnClickListener {
    private var reserve_idx: String = ""
    private var expert_idx: String = ""
    private var expert_type: Int = -1
    private var type: Int = -1
    private var chatcheck: Int = -1

    private lateinit var dtlist: ArrayList<String>
    private lateinit var dateadapter: ReservationStudioDialogRecyclerViewAdapter

    private lateinit var taglist: ArrayList<String>
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

        mViewDataBinding.sendModelToolbarRefundMenu.setOnClickListener(this)
        mViewDataBinding.reservationExpertChat.setOnClickListener(this)
        mViewDataBinding.sendModelExpertInfoLayout.setOnClickListener(this)

        sendManager.instance.expertPage(reserve_idx, completion = { responseStatus, data ->
            when (responseStatus) {
                ESTIMATE.OKAY -> {
                    expert_idx = data[0].expert_idx
                    expert_type = data[0].expert_type


                    mViewDataBinding.sendModelExpertName.text = data[0].expert_name
                    mViewDataBinding.sendModelExpertPlace.text = data[0].expert_place
                    mViewDataBinding.sendModelExpertPrice.text = data[0].expert_price

                    taglist = ArrayList<String>()
                    when (expert_type) {
                        0 -> taglist.add("???????????????")
                        1 -> taglist.add("??????")
                        2 -> taglist.add("???????????????")
                    }
                    val tag = data[0].expert_category.split(",")
                    for (i in tag) {
                        taglist.add(i)
                    }
                    tagAdapter = SendTagRecyclerViewAdapter(taglist)
                    mViewDataBinding.sendModelExpertCategoryRecyclerview.layoutManager =
                        LinearLayoutManager(
                            this,
                            LinearLayoutManager.HORIZONTAL, false
                        )
                    mViewDataBinding.sendModelExpertCategoryRecyclerview.adapter = tagAdapter

                    var multiTransformation = MultiTransformation(
                        CenterCrop(),
                        RoundedCorners(20)
                    )

                    Glide.with(MyApplication.instance)
                        .load(data[0].expert_image)
                        .apply(RequestOptions.bitmapTransform(multiTransformation))
                        .into(mViewDataBinding.sendModelExpertImage)
                    dtlist = ArrayList<String>()
                    val dt = data[0].reserve_dt.split(",")
                    for (i in dt) {
                        dtlist.add(i)
                    }
                    if (dtlist.size != 0) {
                        mViewDataBinding.sendModelExpertRecyclerView.visibility = View.VISIBLE
                        dateadapter = ReservationStudioDialogRecyclerViewAdapter(dtlist)
                        mViewDataBinding.sendModelExpertRecyclerView.layoutManager =
                            GridLayoutManager(this, 3)
                        mViewDataBinding.sendModelExpertRecyclerView.adapter = dateadapter
                    }

                    mViewDataBinding.sendModelExpertTime.text = data[0].reserve_time.toString()
                    mViewDataBinding.sendModelExpertContents.text = data[0].reserve_contents


                }
                ESTIMATE.NOT_USER -> {
                    Toast.makeText(this,"?????? ?????? ????????? ?????????.", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        })


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            5001 -> {
                val intent = Intent()
                intent.putExtra("payment", "ok")
                setResult(5001, intent)
                finish()
            }
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.send_model_expert_info_layout -> {
                var newIntent = Intent()
                newIntent = Intent(this, ModelActivity::class.java)
                newIntent.putExtra("expert_idx", expert_idx)
                startActivity(newIntent)
            }
            R.id.reservation_expert_ok -> {
                val newIntent = Intent(this, paymentActivity::class.java)
                newIntent.putExtra("reserve_idx", reserve_idx)
                newIntent.putExtra("type", 0)
                startActivityForResult(newIntent, 5000)
            }
            R.id.reservation_expert_chat -> {
                var urll = "https://pf.kakao.com/_xlQxdys/chat"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(urll)
                startActivity(intent)
            }
            R.id.send_model_toolbar_refund_menu -> {
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