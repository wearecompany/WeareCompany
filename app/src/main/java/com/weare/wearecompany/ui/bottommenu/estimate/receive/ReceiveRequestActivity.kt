package com.weare.wearecompany.ui.bottommenu.estimate.receive

import android.content.Intent
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
import com.weare.wearecompany.data.retrofit.bottomnav.estimate.requestManager
import com.weare.wearecompany.data.retrofit.bottomnav.estimate.sendManager
import com.weare.wearecompany.databinding.ActivityReceiveManyBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.ui.bottommenu.estimate.receive.payment.paymentActivity
import com.weare.wearecompany.ui.bottommenu.estimate.send.CancellationBottomDialog
import com.weare.wearecompany.ui.bottommenu.estimate.send.SendTagRecyclerViewAdapter
import com.weare.wearecompany.ui.chat.ChatActivity
import com.weare.wearecompany.utils.ESTIMATE
import java.text.DecimalFormat

class ReceiveRequestActivity : BaseActivity<ActivityReceiveManyBinding>(
    R.layout.activity_receive_many
), View.OnClickListener {

    private var request_idx: String = ""
    private var request_log_idx: String = ""
    private var chatbool: Int = -1
    private var type: Int = -1

    private lateinit var receiveAdapter: ReceiveRequestRecyclerViewAdapter

    private var taglist: ArrayList<String> = ArrayList<String>()
    private lateinit var tagAdapter: SendTagRecyclerViewAdapter

    private val dec = DecimalFormat("#,###")

    override fun onCreate() {

        val toobar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toobar)
        val ActionBar = supportActionBar!!
        ActionBar.setDisplayHomeAsUpEnabled(true)
        ActionBar.setDisplayShowTitleEnabled(false)

        request_idx = intent.getStringExtra("request_idx").toString()
        request_log_idx = intent.getStringExtra("log_idx").toString()
        type = intent.getIntExtra("type", 0)
        chatbool = intent.getIntExtra("chatbool", 0)

        setup()

    }

    private fun setup() {

        mViewDataBinding.receiveManyOk.setOnClickListener(this)
        mViewDataBinding.receiveManyChat.setOnClickListener(this)
        mViewDataBinding.receiveManyToolbarRefundMenu.setOnClickListener(this)
        mViewDataBinding.receiveManyToolbarRefundMenu.visibility = View.VISIBLE

        request_log_idx?.let {
            requestManager.instance.receive_page(request_idx,
                it, completion = { responseStatus, arraylist ->
                    when (responseStatus) {
                        ESTIMATE.OKAY -> {

                            mViewDataBinding.receiveManyExpertName.text = arraylist[0].expert_name
                            mViewDataBinding.receiveManyExpertPlace.text = arraylist[0].expert_place
                            mViewDataBinding.receiveManyExpertPrice.text = arraylist[0].expert_price
                            taglist.add(arraylist[0].expert_type)
                            taglist.add(arraylist[0].expert_category)

                            tagAdapter = SendTagRecyclerViewAdapter(taglist)
                            mViewDataBinding.receiveManyCategoryRecyclerview.layoutManager =
                                LinearLayoutManager(
                                    this,
                                    LinearLayoutManager.HORIZONTAL, false
                                )
                            mViewDataBinding.receiveManyCategoryRecyclerview.adapter = tagAdapter

                            if (arraylist[0].thumbnail.size != 0) {
                                mViewDataBinding.receiveManyThumbnailLayout.visibility =
                                    View.VISIBLE
                                mViewDataBinding.buttonLayout.visibility = View.VISIBLE

                                receiveAdapter = ReceiveRequestRecyclerViewAdapter(arraylist)

                                mViewDataBinding.receiveManyThumbnail.layoutManager =
                                    LinearLayoutManager(
                                        this,
                                        LinearLayoutManager.HORIZONTAL, false
                                    )
                                mViewDataBinding.receiveManyThumbnail.adapter = receiveAdapter
                            }

                            var multiTransformation =
                                MultiTransformation(CenterCrop(), RoundedCorners(20))

                            Glide.with(MyApplication.instance)
                                .load(arraylist[0].expert_image)
                                .skipMemoryCache(true)
                                .placeholder(R.drawable.not_load_image)
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .fallback(R.drawable.not_load_image)
                                .apply(RequestOptions.bitmapTransform(multiTransformation))
                                .into(mViewDataBinding.receiveManyExpertImage)

                            mViewDataBinding.receiveManyDatetime.setText(arraylist[0].datetime)
                            mViewDataBinding.receiveManyContents.setText(arraylist[0].request_contents)
                            mViewDataBinding.receiveManyData.visibility = View.VISIBLE
                            if (chatbool == 1) {
                                mViewDataBinding.receiveManyChat.visibility = View.GONE
                            }
                            mViewDataBinding.receiveManyPrice.text = dec.format(arraylist[0].price)
                            mViewDataBinding.requestUserSendContents.setText(arraylist[0].response_contents)

                            mViewDataBinding.buttonLayout.visibility = View.VISIBLE
                        }
                    }
                })
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.receive_many_chat -> {
                val newIntent = Intent(this, ChatActivity::class.java)
                newIntent.putExtra("request_idx", request_idx)
                newIntent.putExtra("log_idx", request_log_idx)
                newIntent.putExtra("Entrytype", 0)
                newIntent.putExtra("type", 1)
                startActivity(newIntent)
            }
            R.id.receive_many_ok -> {
                val newIntent = Intent(this, paymentActivity::class.java)
                newIntent.putExtra("request_idx", request_idx)
                newIntent.putExtra("log_idx", request_log_idx)
                newIntent.putExtra("type", 1)
                startActivityForResult(newIntent, 5000)
            }
            R.id.receive_many_toolbar_refund_menu -> {
                val cancellationdialog: CancellationBottomDialog = CancellationBottomDialog() {
                    when (it) {
                        1 -> {
                            sendManager.instance.requestdelete(
                                request_idx,
                                request_log_idx,
                                completion = { responseStatus ->
                                    when (responseStatus) {
                                        ESTIMATE.OKAY -> {
                                            val intent = Intent()
                                            intent.putExtra("Cancellation", "ok")
                                            setResult(6000, intent)
                                            finish()
                                        }
                                    }
                                })
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