package com.weare.wearecompany.ui.bottommenu.estimate.send

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
import com.weare.wearecompany.databinding.ActivityUserManySendRequestBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.ui.bottommenu.estimate.receive.ReceiveRequestRecyclerViewAdapter
import com.weare.wearecompany.ui.bottommenu.estimate.receive.payment.paymentActivity
import com.weare.wearecompany.ui.chat.ChatActivity
import com.weare.wearecompany.utils.ESTIMATE
import com.weare.wearecompany.utils.RESPONSE_STATUS

class SendRequestActivity:BaseActivity<ActivityUserManySendRequestBinding>(
    R.layout.activity_user_many_send_request
),View.OnClickListener {

    private var request_idx:String = ""
    private var request_log_idx:String = ""
    private var chatbool:Int = -1
    private var type:Int = -1

    private lateinit var dataAdapter :SendRequestRecyclerViewAdapter
    private lateinit var receiveAdapter : ReceiveRequestRecyclerViewAdapter

    private lateinit var taglist: ArrayList<String>
    private lateinit var tagAdapter: SendTagRecyclerViewAdapter

    override fun onCreate() {

        val toobar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toobar)
        val ActionBar = supportActionBar!!
        ActionBar.setDisplayHomeAsUpEnabled(true)
        ActionBar.setDisplayShowTitleEnabled(false)

        request_idx = intent.getStringExtra("request_idx").toString()
        request_log_idx = intent.getStringExtra("log_idx").toString()
        type = intent.getIntExtra("type",0)
        chatbool = intent.getIntExtra("chatbool",0)
        setup()

    }

    private fun setup() {

        when(type) {
            0 -> {
                mViewDataBinding.estimateManyToolbarRefundMenu.setOnClickListener(this)
                requestManager.instance.requset_page(request_idx,completion = {responseStatus, arraylist ->
                    when(responseStatus) {

                        RESPONSE_STATUS.OKAY -> {
                            taglist = ArrayList<String>()
                            taglist.add(arraylist[0].expert_type)
                            taglist.add(arraylist[0].expert_category)

                            tagAdapter = SendTagRecyclerViewAdapter(taglist)
                            mViewDataBinding.sendManyCategoryRecyclerview.layoutManager = LinearLayoutManager(
                                this,
                                LinearLayoutManager.HORIZONTAL, false
                            )
                            mViewDataBinding.sendManyCategoryRecyclerview.adapter = tagAdapter
                            if (arraylist[0].thumbnail.size != 0) {
                                mViewDataBinding.userThumbnailListLayout.visibility = View.VISIBLE
                                dataAdapter = SendRequestRecyclerViewAdapter(arraylist)
                                mViewDataBinding.requestUserThumbnail.layoutManager = LinearLayoutManager(
                                    this,
                                    LinearLayoutManager.HORIZONTAL, false
                                )
                                mViewDataBinding.requestUserThumbnail.adapter = dataAdapter
                            }

                            mViewDataBinding.requestUserDatetime.setText(arraylist[0].datetime)
                            mViewDataBinding.requestUserContents.setText(arraylist[0].contents)
                        }
                    }
                })
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
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
            /*R.id.request_user_send_ok -> {
                val newIntent = Intent(this, paymentActivity::class.java)
                newIntent.putExtra("request_idx",request_idx)
                newIntent.putExtra("log_idx",request_log_idx)
                newIntent.putExtra("type",1)
                startActivityForResult(newIntent,5000)
            }
            R.id.request_user_send_chat -> {
                val newIntent = Intent(this, ChatActivity::class.java)
                newIntent.putExtra("request_idx",request_idx)
                newIntent.putExtra("log_idx",request_log_idx)
                newIntent.putExtra("Entrytype",0)
                newIntent.putExtra("type",1)
                startActivity(newIntent)
            }*/
            R.id.estimate_many_toolbar_refund_menu -> {
                val cancellationdialog: CancellationBottomDialog = CancellationBottomDialog() {
                    when(it) {
                        1 -> {
                            sendManager.instance.requestdelete(request_idx,request_log_idx,completion = { responseStatus ->
                                when(responseStatus) {
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
                cancellationdialog.show(supportFragmentManager,cancellationdialog.tag)
            }
        }
    }
}