package com.weare.wearecompany.ui.bottommenu.mypage.request

import android.content.Intent
import android.graphics.Color
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.main.Request.deta.*
import com.weare.wearecompany.data.retrofit.bottomnav.estimate.progressManager
import com.weare.wearecompany.data.retrofit.bottomnav.mypage.requestManager
import com.weare.wearecompany.databinding.ActivityMypageRequestBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.ui.bottommenu.estimate.progress.experthodel.progress.*
import com.weare.wearecompany.ui.bottommenu.estimate.progress.experthodel.refund.RefundModelActivity
import com.weare.wearecompany.ui.bottommenu.estimate.progress.experthodel.refund.RefundPhotoActivity
import com.weare.wearecompany.ui.bottommenu.estimate.progress.experthodel.refund.RefundStudioActivity
import com.weare.wearecompany.ui.bottommenu.estimate.progress.experthodel.refund.RefundTripActivity
import com.weare.wearecompany.ui.bottommenu.estimate.progress.experthodel.review.ReviewActivity
import com.weare.wearecompany.ui.bottommenu.estimate.receive.experthodel.ReceiveModelActivity
import com.weare.wearecompany.ui.bottommenu.estimate.receive.experthodel.ReceivePhotoActivity
import com.weare.wearecompany.ui.bottommenu.estimate.receive.experthodel.ReceiveStudioActivity
import com.weare.wearecompany.ui.bottommenu.estimate.receive.experthodel.ReceiveTripActivity
import com.weare.wearecompany.ui.bottommenu.estimate.send.SendStudioActivity
import com.weare.wearecompany.ui.bottommenu.estimate.send.experthodel.SendModelActivity
import com.weare.wearecompany.ui.bottommenu.estimate.send.experthodel.SendPhotoActivity
import com.weare.wearecompany.ui.bottommenu.estimate.send.experthodel.SendTripActivity
import com.weare.wearecompany.ui.bottommenu.mypage.request.progress.ProgressOneClickActivity
import com.weare.wearecompany.ui.bottommenu.mypage.request.progress.RefundOneClickActivity
import com.weare.wearecompany.ui.bottommenu.mypage.request.receive.ReceiveOneClickActivity
import com.weare.wearecompany.ui.bottommenu.mypage.request.review.ReviewOneClickRecyclerViewAdapter
import com.weare.wearecompany.ui.bottommenu.mypage.request.review.ReviewOneRecyclerViewAdapter
import com.weare.wearecompany.ui.bottommenu.mypage.request.send.SendOneClickActivity
import com.weare.wearecompany.ui.listcontainer.ListContainerActivity
import com.weare.wearecompany.utils.ESTIMATE
import com.weare.wearecompany.utils.RESPONSE_STATUS

class RequestListActivity : BaseActivity<ActivityMypageRequestBinding>(
    R.layout.activity_mypage_request
), View.OnClickListener {

    private var post_ca = 0
    private var post_ca_btn = 0
    lateinit var userIdx: String
    private var list_type = 0
    private var not_list_check = false

    private lateinit var sendOneList: ArrayList<sendone>
    private lateinit var reviewOneList: ArrayList<reviewone>
    private lateinit var topList: ArrayList<top>
    private lateinit var sendOneClickList: ArrayList<sendoneclick>
    private lateinit var receiveOneClickList: ArrayList<receiveoneclick>
    private lateinit var reviewOneClickList: ArrayList<reviewoneclick>

    //어탭터
    private lateinit var topAdapter: RequestListTopPageAdapter
    private lateinit var send_one_Adapter: SendOneRecyclerViewAdapter
    private lateinit var send_oneclick_Adapter: SendOneClickRecyclerViewAdapter
    private lateinit var receive_onCLick_Adapter: ReceiveOneClickRecyclerViewAdapter
    private lateinit var review_one_Adapter: ReviewOneRecyclerViewAdapter
    private lateinit var review_oneclick_Adapter: ReviewOneClickRecyclerViewAdapter

    override fun onCreate() {

        bindingsetup()
        setup()

    }

    private fun bindingsetup() {
        userIdx = MyApplication.prefs.getString("user_idx", "")
        mViewDataBinding.requestListCaBtn1.setOnClickListener(this)
        mViewDataBinding.requestListCaBtn2.setOnClickListener(this)
        mViewDataBinding.requestListCa0.setOnClickListener(this)
        mViewDataBinding.requestListCa1.setOnClickListener(this)
        mViewDataBinding.requestListCa2.setOnClickListener(this)
        mViewDataBinding.requestListCa3.setOnClickListener(this)
        mViewDataBinding.requestListCa4.setOnClickListener(this)
        mViewDataBinding.requestListCa5.setOnClickListener(this)
        mViewDataBinding.requestListCa6.setOnClickListener(this)
        mViewDataBinding.moveExpertList.setOnClickListener(this)

    }

    private fun setup() {

        requestManager.instance.topList(userIdx, completion = { responseStatus, arrayList ->
            when (responseStatus) {
                RESPONSE_STATUS.OKAY -> {
                    if (arrayList.size != 0) {
                        mViewDataBinding.requestTop.visibility = View.VISIBLE
                        topList = arrayList

                        topAdapter = RequestListTopPageAdapter(topList)
                        mViewDataBinding.requestTopPager.adapter = topAdapter

                        mViewDataBinding.requestTopPager.clipToPadding = false

                        val dpValue = 16
                        val d = resources.displayMetrics.density
                        val margin = (dpValue * d).toInt()
                        mViewDataBinding.requestTopPager.setPadding(margin, 0, margin, 0)
                        mViewDataBinding.requestTopPager.setPageMargin(margin / 2)
                    } else {
                        mViewDataBinding.requestTop.visibility = View.GONE
                    }

                }
            }
        })


        requestManager.instance.sendList(userIdx, completion = { responseStatus, arrayList ->
            when (responseStatus) {
                RESPONSE_STATUS.OKAY -> {

                    sendOneList = arrayList[0].reserve
                    sendOneClickList = arrayList[0].oneClick

                    mViewDataBinding.requestListCa0.text = arrayList[0].request.toString()
                    mViewDataBinding.requestListCa1.text = arrayList[0].response.toString()
                    mViewDataBinding.requestListCa2.text = arrayList[0].progress.toString()
                    mViewDataBinding.requestListCa3.text = arrayList[0].complete.toString()
                    mViewDataBinding.requestListCa4.text = arrayList[0].review.toString()
                    mViewDataBinding.requestListCa5.text = arrayList[0].refund_request.toString()
                    mViewDataBinding.requestListCa6.text = arrayList[0].refund_complete.toString()

                    if (sendOneList.size != 0) {
                        mViewDataBinding.requestListTitle1.visibility = View.VISIBLE
                        mViewDataBinding.requestOneRecyclerview.visibility = View.VISIBLE

                        send_one_Adapter = SendOneRecyclerViewAdapter(sendOneList, this, 0)
                        mViewDataBinding.requestOneRecyclerview.layoutManager =
                            LinearLayoutManager(
                                this,
                                LinearLayoutManager.VERTICAL, false
                            )
                        mViewDataBinding.requestOneRecyclerview.adapter = send_one_Adapter

                        send_one_Adapter.setItemClickListener(object :
                            SendOneRecyclerViewAdapter.OnItemClickListener {
                            override fun onClick(v: View, position: Int, item: sendone) {
                                when (item.expert_type) {
                                    0 -> {
                                        val newIntent =
                                            Intent(
                                                this@RequestListActivity,
                                                SendStudioActivity::class.java
                                            )
                                        newIntent.putExtra("reserve_idx", item.reserve_idx)
                                        newIntent.putExtra("type", 0)
                                        startActivityForResult(newIntent, 2000)
                                    }
                                    1 -> {
                                        val newIntent =
                                            Intent(
                                                this@RequestListActivity,
                                                SendPhotoActivity::class.java
                                            )
                                        newIntent.putExtra("reserve_idx", item.reserve_idx)
                                        newIntent.putExtra("type", 0)
                                        startActivityForResult(newIntent, 2000)
                                    }
                                    2 -> {
                                        val newIntent =
                                            Intent(
                                                this@RequestListActivity,
                                                SendModelActivity::class.java
                                            )
                                        newIntent.putExtra("reserve_idx", item.reserve_idx)
                                        newIntent.putExtra("type", 0)
                                        startActivityForResult(newIntent, 2000)
                                    }
                                    3 -> {
                                        val newIntent =
                                            Intent(
                                                this@RequestListActivity,
                                                SendTripActivity::class.java
                                            )
                                        newIntent.putExtra("reserve_idx", item.reserve_idx)
                                        newIntent.putExtra("type", 0)
                                        startActivityForResult(newIntent, 2000)
                                    }
                                }
                            }

                        })
                    } else {
                        mViewDataBinding.requestListTitle1.visibility = View.GONE
                        mViewDataBinding.requestOneRecyclerview.visibility = View.GONE
                    }

                    if (sendOneClickList.size != 0) {
                        mViewDataBinding.requestListTitle2.visibility = View.VISIBLE
                        mViewDataBinding.requestOneClickRecyclerview.visibility = View.VISIBLE

                        send_oneclick_Adapter =
                            SendOneClickRecyclerViewAdapter(sendOneClickList, this, 0)
                        mViewDataBinding.requestOneClickRecyclerview.layoutManager =
                            LinearLayoutManager(
                                this,
                                LinearLayoutManager.VERTICAL, false
                            )
                        mViewDataBinding.requestOneClickRecyclerview.adapter = send_oneclick_Adapter

                        send_oneclick_Adapter.setItemClickListener(object :
                            SendOneClickRecyclerViewAdapter.OnItemClickListener {
                            override fun onClick(v: View, position: Int, item: sendoneclick) {
                                val newIntent =
                                    Intent(
                                        this@RequestListActivity,
                                        SendOneClickActivity::class.java
                                    )
                                newIntent.putExtra("reserve_idx", item.reserve_idx)
                                startActivityForResult(newIntent, 2000)
                            }

                        })
                    } else {
                        mViewDataBinding.requestListTitle2.visibility = View.GONE
                        mViewDataBinding.requestOneClickRecyclerview.visibility = View.GONE
                    }

                    if (sendOneList.size == 0 && sendOneClickList.size == 0) {
                        mViewDataBinding.requestListLayout.visibility = View.GONE
                        if (!not_list_check) {
                            mViewDataBinding.requestNotListLayout.visibility =
                                View.VISIBLE
                            not_list_check = true
                        }
                        notlisttitle()
                    }

                }
            }
        })
    }

    private fun poastchange(postion: Int) {
        when (postion) {
            0 -> {
                mViewDataBinding.requestListCa0.setBackgroundResource(R.drawable.request_ca_round_off)
            }
            1 -> {
                mViewDataBinding.requestListCa1.setBackgroundResource(R.drawable.request_ca_round_off)
            }
            2 -> {
                mViewDataBinding.requestListCa2.setBackgroundResource(R.drawable.request_ca_round_off)
            }
            3 -> {
                mViewDataBinding.requestListCa3.setBackgroundResource(R.drawable.request_ca_round_off)
            }
            4 -> {
                mViewDataBinding.requestListCa4.setBackgroundResource(R.drawable.request_ca_round_off)
            }
            5 -> {
                mViewDataBinding.requestListCa5.setBackgroundResource(R.drawable.request_ca_round_off)
            }
            6 -> {
                mViewDataBinding.requestListCa6.setBackgroundResource(R.drawable.request_ca_round_off)
            }
        }

    }

    private fun notlisttitle() {
        if (list_type == 0) {
            mViewDataBinding.moveExpertList.visibility = View.VISIBLE
        } else {
            mViewDataBinding.moveExpertList.visibility = View.GONE
        }
        when (list_type) {
            0 -> {
                mViewDataBinding.requestNotListImage.setImageResource(R.drawable.request_not_image_type_1)
                mViewDataBinding.requestNotListTitle1.text = "보낸 요청이 없습니다"
                mViewDataBinding.requestNotListTitle2.text =
                    "아직 견적요청을 보내지 않으셨나요?\n나에게 딱 맞는 전문가를 찾아서\n요청서를 보내보세요"
            }
            1 -> {
                mViewDataBinding.requestNotListImage.setImageResource(R.drawable.request_not_image_type_1)
                mViewDataBinding.requestNotListTitle1.text = "받은 견적이 없습니다"
                mViewDataBinding.requestNotListTitle2.text = "원하는 전문가에게 요청서를 보내고\n딱 맞는 견적서를 받아보세요"
            }
            2 -> {
                mViewDataBinding.requestNotListImage.setImageResource(R.drawable.request_not_image_type_1)
                mViewDataBinding.requestNotListTitle1.text = "결제 완료된 건 이 없습니다"
                mViewDataBinding.requestNotListTitle2.text = "나에게 맞는 전문가를 찾아서\n딱 맞는 서비스를 이용해보세요"
            }
            3 -> {
                mViewDataBinding.requestNotListImage.setImageResource(R.drawable.request_not_image_type_1)
                mViewDataBinding.requestNotListTitle1.text = "진행 완료된 건 이 없습니다"
                mViewDataBinding.requestNotListTitle2.text = "나에게 맞는 전문가를 찾아서\n딱 맞는 서비스를 이용해보세요"
            }
            4 -> {
                mViewDataBinding.requestNotListImage.setImageResource(R.drawable.request_not_image_type_1)
                mViewDataBinding.requestNotListTitle1.text = "후기작성 내역이 없습니다"
                mViewDataBinding.requestNotListTitle2.text = "원하는 전문가에게 서비스를 받고\n후기를 남겨주세요"
            }
            5 -> {
                mViewDataBinding.requestNotListImage.setImageResource(R.drawable.request_not_image_type_2)
                mViewDataBinding.requestNotListTitle1.text = "취소 현황이 없습니다"
                mViewDataBinding.requestNotListTitle2.text =
                    "환불을 원하시는 경우\n진행했던 전문가와  1:1 채팅을 통하여\n환불요청을 보내세요"
            }
            6 -> {
                mViewDataBinding.requestNotListImage.setImageResource(R.drawable.request_not_image_type_2)
                mViewDataBinding.requestNotListTitle1.text = "취소 완료된 건 이 없습니다"
                mViewDataBinding.requestNotListTitle2.text =
                    "환불을 원하시는 경우\n진행했던 전문가와  1:1 채팅을 통하여\n환불요청을 보내세요"
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            3001 -> {
                Toast.makeText(this, "결제 완료", Toast.LENGTH_SHORT).show()
                mViewDataBinding.paymentLottie.bringToFront()
                mViewDataBinding.paymentLottie.playAnimation()

                requestManager.instance.receiveList(
                    userIdx,
                    completion = { responseStatus, arrayList ->
                        when (responseStatus) {
                            RESPONSE_STATUS.OKAY -> {
                                sendOneList = arrayList[0].reserve
                                receiveOneClickList = arrayList[0].oneClick

                                mViewDataBinding.requestListCa0.text =
                                    arrayList[0].request.toString()
                                mViewDataBinding.requestListCa1.text =
                                    arrayList[0].response.toString()
                                mViewDataBinding.requestListCa2.text =
                                    arrayList[0].progress.toString()
                                mViewDataBinding.requestListCa3.text =
                                    arrayList[0].complete.toString()
                                mViewDataBinding.requestListCa4.text =
                                    arrayList[0].review.toString()
                                mViewDataBinding.requestListCa5.text =
                                    arrayList[0].refund_request.toString()
                                mViewDataBinding.requestListCa6.text =
                                    arrayList[0].refund_complete.toString()

                                if (sendOneList.size != 0) {
                                    if (not_list_check) {
                                        mViewDataBinding.requestNotListLayout.visibility =
                                            View.GONE
                                        mViewDataBinding.requestListLayout.visibility =
                                            View.VISIBLE
                                        not_list_check = false
                                    }
                                    mViewDataBinding.requestOneRecyclerview.visibility =
                                        View.VISIBLE
                                    mViewDataBinding.requestListTitle1.visibility = View.VISIBLE

                                    send_one_Adapter =
                                        SendOneRecyclerViewAdapter(sendOneList, this, 1)
                                    mViewDataBinding.requestOneRecyclerview.layoutManager =
                                        LinearLayoutManager(
                                            this,
                                            LinearLayoutManager.VERTICAL, false
                                        )
                                    mViewDataBinding.requestOneRecyclerview.adapter =
                                        send_one_Adapter

                                    send_one_Adapter.setItemClickListener(object :
                                        SendOneRecyclerViewAdapter.OnItemClickListener {
                                        override fun onClick(
                                            v: View,
                                            position: Int,
                                            item: sendone
                                        ) {
                                            when (item.expert_type) {
                                                0 -> {
                                                    val newIntent =
                                                        Intent(
                                                            this@RequestListActivity,
                                                            ReceiveStudioActivity::class.java
                                                        )
                                                    newIntent.putExtra(
                                                        "reserve_idx",
                                                        item.reserve_idx
                                                    )
                                                    newIntent.putExtra("type", 1)
                                                    startActivityForResult(newIntent, 3000)
                                                }
                                                1 -> {
                                                    val newIntent =
                                                        Intent(
                                                            this@RequestListActivity,
                                                            ReceivePhotoActivity::class.java
                                                        )
                                                    newIntent.putExtra(
                                                        "reserve_idx",
                                                        item.reserve_idx
                                                    )
                                                    newIntent.putExtra("type", 1)
                                                    startActivityForResult(newIntent, 3000)
                                                }
                                                2 -> {
                                                    val newIntent =
                                                        Intent(
                                                            this@RequestListActivity,
                                                            ReceiveModelActivity::class.java
                                                        )
                                                    newIntent.putExtra(
                                                        "reserve_idx",
                                                        item.reserve_idx
                                                    )
                                                    newIntent.putExtra("type", 1)
                                                    startActivityForResult(newIntent, 3000)
                                                }
                                                3 -> {
                                                    val newIntent =
                                                        Intent(
                                                            this@RequestListActivity,
                                                            ReceiveTripActivity::class.java
                                                        )
                                                    newIntent.putExtra(
                                                        "reserve_idx",
                                                        item.reserve_idx
                                                    )
                                                    newIntent.putExtra("type", 1)
                                                    startActivityForResult(newIntent, 3000)
                                                }
                                            }
                                        }

                                    })
                                } else {
                                    mViewDataBinding.requestOneRecyclerview.visibility = View.GONE
                                    mViewDataBinding.requestListTitle1.visibility = View.GONE
                                }

                                if (receiveOneClickList.size != 0) {
                                    if (not_list_check) {
                                        mViewDataBinding.requestNotListLayout.visibility =
                                            View.GONE
                                        mViewDataBinding.requestListLayout.visibility =
                                            View.VISIBLE
                                        not_list_check = false
                                    }
                                    mViewDataBinding.requestListTitle2.visibility = View.VISIBLE
                                    mViewDataBinding.requestOneClickRecyclerview.visibility =
                                        View.VISIBLE

                                    receive_onCLick_Adapter =
                                        ReceiveOneClickRecyclerViewAdapter(
                                            receiveOneClickList,
                                            this,
                                            1
                                        )
                                    mViewDataBinding.requestOneClickRecyclerview.layoutManager =
                                        LinearLayoutManager(
                                            this,
                                            LinearLayoutManager.VERTICAL, false
                                        )
                                    mViewDataBinding.requestOneClickRecyclerview.adapter =
                                        receive_onCLick_Adapter

                                    receive_onCLick_Adapter.setItemClickListener(object :
                                        ReceiveOneClickRecyclerViewAdapter.OnItemClickListener {
                                        override fun onClick(
                                            v: View,
                                            position: Int,
                                            item: receiveoneclick
                                        ) {
                                            val newIntent =
                                                Intent(
                                                    this@RequestListActivity,
                                                    ReceiveOneClickActivity::class.java
                                                )
                                            newIntent.putExtra("reserve_idx", item.reserve_idx)
                                            startActivityForResult(newIntent, 3000)
                                        }

                                    })

                                } else {
                                    mViewDataBinding.requestListTitle2.visibility = View.GONE
                                    mViewDataBinding.requestOneClickRecyclerview.visibility =
                                        View.GONE

                                }

                                if (sendOneList.size == 0 && receiveOneClickList.size == 0) {
                                    mViewDataBinding.requestListLayout.visibility = View.GONE
                                    if (!not_list_check) {
                                        mViewDataBinding.requestNotListLayout.visibility =
                                            View.VISIBLE
                                        not_list_check = true
                                    }
                                    notlisttitle()
                                }


                            }
                        }
                    })
            }
            2001 -> {
                Toast.makeText(this, "취소요청 완료", Toast.LENGTH_SHORT).show()

                requestManager.instance.progressList(
                    userIdx,
                    completion = { responseStatus, arrayList ->
                        when (responseStatus) {
                            RESPONSE_STATUS.OKAY -> {
                                sendOneList = arrayList[0].reserve
                                receiveOneClickList = arrayList[0].oneClick

                                mViewDataBinding.requestListCa0.text =
                                    arrayList[0].request.toString()
                                mViewDataBinding.requestListCa1.text =
                                    arrayList[0].response.toString()
                                mViewDataBinding.requestListCa2.text =
                                    arrayList[0].progress.toString()
                                mViewDataBinding.requestListCa3.text =
                                    arrayList[0].complete.toString()
                                mViewDataBinding.requestListCa4.text =
                                    arrayList[0].review.toString()
                                mViewDataBinding.requestListCa5.text =
                                    arrayList[0].refund_request.toString()
                                mViewDataBinding.requestListCa6.text =
                                    arrayList[0].refund_complete.toString()

                                if (sendOneList.size != 0) {
                                    if (not_list_check) {
                                        mViewDataBinding.requestNotListLayout.visibility =
                                            View.GONE
                                        mViewDataBinding.requestListLayout.visibility =
                                            View.VISIBLE
                                        not_list_check = false
                                    }
                                    mViewDataBinding.requestListTitle1.visibility = View.VISIBLE
                                    mViewDataBinding.requestOneRecyclerview.visibility =
                                        View.VISIBLE

                                    send_one_Adapter =
                                        SendOneRecyclerViewAdapter(sendOneList, this, 2)
                                    mViewDataBinding.requestOneRecyclerview.layoutManager =
                                        LinearLayoutManager(
                                            this,
                                            LinearLayoutManager.VERTICAL, false
                                        )
                                    mViewDataBinding.requestOneRecyclerview.adapter =
                                        send_one_Adapter

                                    send_one_Adapter.setItemClickListener(object :
                                        SendOneRecyclerViewAdapter.OnItemClickListener {
                                        override fun onClick(
                                            v: View,
                                            position: Int,
                                            item: sendone
                                        ) {
                                            when (item.expert_type) {
                                                0 -> {
                                                    val newIntent = Intent(
                                                        this@RequestListActivity,
                                                        ProgressStudioActivity::class.java
                                                    )
                                                    newIntent.putExtra(
                                                        "reserve_idx",
                                                        item.reserve_idx
                                                    )
                                                    newIntent.putExtra("type",2)
                                                    newIntent.putExtra("chatbool", 0)
                                                    startActivityForResult(newIntent, 2000)
                                                }
                                                1 -> {
                                                    val newIntent = Intent(
                                                        this@RequestListActivity,
                                                        ProgressPhotoActivity::class.java
                                                    )
                                                    newIntent.putExtra(
                                                        "reserve_idx",
                                                        item.reserve_idx
                                                    )
                                                    newIntent.putExtra("type",2)
                                                    newIntent.putExtra("chatbool", 0)
                                                    startActivityForResult(newIntent, 2000)
                                                }
                                                2 -> {
                                                    val newIntent = Intent(
                                                        this@RequestListActivity,
                                                        ProgressModelActivity::class.java
                                                    )
                                                    newIntent.putExtra(
                                                        "reserve_idx",
                                                        item.reserve_idx
                                                    )
                                                    newIntent.putExtra("type",2)
                                                    newIntent.putExtra("chatbool", 0)
                                                    startActivityForResult(newIntent, 2000)
                                                }
                                                3 -> {
                                                    val newIntent = Intent(
                                                        this@RequestListActivity,
                                                        ProgressTripActivity::class.java
                                                    )
                                                    newIntent.putExtra(
                                                        "reserve_idx",
                                                        item.reserve_idx
                                                    )
                                                    newIntent.putExtra("type",2)
                                                    newIntent.putExtra("chatbool", 0)
                                                    startActivityForResult(newIntent, 2000)
                                                }
                                            }
                                        }

                                    })

                                } else {
                                    mViewDataBinding.requestOneRecyclerview.visibility =
                                        View.GONE
                                    mViewDataBinding.requestListTitle1.visibility = View.GONE
                                }

                                if (receiveOneClickList.size != 0) {
                                    if (not_list_check) {
                                        mViewDataBinding.requestNotListLayout.visibility =
                                            View.GONE
                                        mViewDataBinding.requestListLayout.visibility =
                                            View.VISIBLE
                                        not_list_check = false
                                    }
                                    mViewDataBinding.requestListTitle2.visibility = View.VISIBLE
                                    mViewDataBinding.requestOneClickRecyclerview.visibility =
                                        View.VISIBLE

                                    receive_onCLick_Adapter =
                                        ReceiveOneClickRecyclerViewAdapter(
                                            receiveOneClickList,
                                            this,
                                            2
                                        )
                                    mViewDataBinding.requestOneClickRecyclerview.layoutManager =
                                        LinearLayoutManager(
                                            this,
                                            LinearLayoutManager.VERTICAL, false
                                        )
                                    mViewDataBinding.requestOneClickRecyclerview.adapter =
                                        receive_onCLick_Adapter

                                    receive_onCLick_Adapter.setItemClickListener(object :
                                        ReceiveOneClickRecyclerViewAdapter.OnItemClickListener {
                                        override fun onClick(
                                            v: View,
                                            position: Int,
                                            item: receiveoneclick
                                        ) {
                                            val newIntent =
                                                Intent(
                                                    this@RequestListActivity,
                                                    ProgressOneClickActivity::class.java
                                                )
                                            newIntent.putExtra("reserve_idx", item.reserve_idx)
                                            startActivityForResult(newIntent, 3000)
                                        }

                                    })
                                } else {
                                    mViewDataBinding.requestOneClickRecyclerview.visibility =
                                        View.GONE
                                    mViewDataBinding.requestListTitle2.visibility = View.GONE
                                }

                                if (sendOneList.size == 0 && receiveOneClickList.size == 0) {
                                    mViewDataBinding.requestListLayout.visibility = View.GONE
                                    if (!not_list_check) {
                                        mViewDataBinding.requestNotListLayout.visibility =
                                            View.VISIBLE
                                        not_list_check = true
                                    }
                                    notlisttitle()
                                }


                            }
                        }
                    })
            }   //예약취소
            2003 -> {
                Toast.makeText(this, "취소요청 완료", Toast.LENGTH_SHORT).show()
                requestManager.instance.receiveList(
                    userIdx,
                    completion = { responseStatus, arrayList ->
                        when (responseStatus) {
                            RESPONSE_STATUS.OKAY -> {
                                sendOneList = arrayList[0].reserve
                                receiveOneClickList = arrayList[0].oneClick

                                mViewDataBinding.requestListCa0.text =
                                    arrayList[0].request.toString()
                                mViewDataBinding.requestListCa1.text =
                                    arrayList[0].response.toString()
                                mViewDataBinding.requestListCa2.text =
                                    arrayList[0].progress.toString()
                                mViewDataBinding.requestListCa3.text =
                                    arrayList[0].complete.toString()
                                mViewDataBinding.requestListCa4.text =
                                    arrayList[0].review.toString()
                                mViewDataBinding.requestListCa5.text =
                                    arrayList[0].refund_request.toString()
                                mViewDataBinding.requestListCa6.text =
                                    arrayList[0].refund_complete.toString()

                                if (sendOneList.size != 0) {
                                    if (not_list_check) {
                                        mViewDataBinding.requestNotListLayout.visibility =
                                            View.GONE
                                        mViewDataBinding.requestListLayout.visibility =
                                            View.VISIBLE
                                        not_list_check = false
                                    }
                                    mViewDataBinding.requestOneRecyclerview.visibility =
                                        View.VISIBLE
                                    mViewDataBinding.requestListTitle1.visibility = View.VISIBLE

                                    send_one_Adapter =
                                        SendOneRecyclerViewAdapter(sendOneList, this, 1)
                                    mViewDataBinding.requestOneRecyclerview.layoutManager =
                                        LinearLayoutManager(
                                            this,
                                            LinearLayoutManager.VERTICAL, false
                                        )
                                    mViewDataBinding.requestOneRecyclerview.adapter =
                                        send_one_Adapter

                                    send_one_Adapter.setItemClickListener(object :
                                        SendOneRecyclerViewAdapter.OnItemClickListener {
                                        override fun onClick(
                                            v: View,
                                            position: Int,
                                            item: sendone
                                        ) {
                                            when (item.expert_type) {
                                                0 -> {
                                                    val newIntent =
                                                        Intent(
                                                            this@RequestListActivity,
                                                            ReceiveStudioActivity::class.java
                                                        )
                                                    newIntent.putExtra(
                                                        "reserve_idx",
                                                        item.reserve_idx
                                                    )
                                                    newIntent.putExtra("type", 0)
                                                    startActivityForResult(newIntent, 3000)
                                                }
                                                1 -> {
                                                    val newIntent =
                                                        Intent(
                                                            this@RequestListActivity,
                                                            ReceivePhotoActivity::class.java
                                                        )
                                                    newIntent.putExtra(
                                                        "reserve_idx",
                                                        item.reserve_idx
                                                    )
                                                    newIntent.putExtra("type", 0)
                                                    startActivityForResult(newIntent, 3000)
                                                }
                                                2 -> {
                                                    val newIntent =
                                                        Intent(
                                                            this@RequestListActivity,
                                                            ReceiveModelActivity::class.java
                                                        )
                                                    newIntent.putExtra(
                                                        "reserve_idx",
                                                        item.reserve_idx
                                                    )
                                                    newIntent.putExtra("type", 0)
                                                    startActivityForResult(newIntent, 3000)
                                                }
                                                3 -> {
                                                    val newIntent =
                                                        Intent(
                                                            this@RequestListActivity,
                                                            ReceiveTripActivity::class.java
                                                        )
                                                    newIntent.putExtra(
                                                        "reserve_idx",
                                                        item.reserve_idx
                                                    )
                                                    newIntent.putExtra("type", 0)
                                                    startActivityForResult(newIntent, 3000)
                                                }
                                            }
                                        }

                                    })
                                } else {
                                    mViewDataBinding.requestOneRecyclerview.visibility =
                                        View.GONE
                                    mViewDataBinding.requestListTitle1.visibility = View.GONE
                                }

                                if (receiveOneClickList.size != 0) {
                                    if (not_list_check) {
                                        mViewDataBinding.requestNotListLayout.visibility =
                                            View.GONE
                                        mViewDataBinding.requestListLayout.visibility =
                                            View.VISIBLE
                                        not_list_check = false
                                    }
                                    mViewDataBinding.requestListTitle2.visibility = View.VISIBLE
                                    mViewDataBinding.requestOneClickRecyclerview.visibility =
                                        View.VISIBLE

                                    receive_onCLick_Adapter =
                                        ReceiveOneClickRecyclerViewAdapter(
                                            receiveOneClickList,
                                            this,
                                            0
                                        )
                                    mViewDataBinding.requestOneClickRecyclerview.layoutManager =
                                        LinearLayoutManager(
                                            this,
                                            LinearLayoutManager.VERTICAL, false
                                        )
                                    mViewDataBinding.requestOneClickRecyclerview.adapter =
                                        receive_onCLick_Adapter

                                    receive_onCLick_Adapter.setItemClickListener(object :
                                        ReceiveOneClickRecyclerViewAdapter.OnItemClickListener {
                                        override fun onClick(
                                            v: View,
                                            position: Int,
                                            item: receiveoneclick
                                        ) {
                                            val newIntent =
                                                Intent(
                                                    this@RequestListActivity,
                                                    ReceiveOneClickActivity::class.java
                                                )
                                            newIntent.putExtra("reserve_idx", item.reserve_idx)
                                            startActivityForResult(newIntent, 3000)
                                        }
                                    })

                                } else {
                                    mViewDataBinding.requestListTitle2.visibility = View.GONE
                                    mViewDataBinding.requestOneClickRecyclerview.visibility =
                                        View.GONE
                                }

                                if (sendOneList.size == 0 && receiveOneClickList.size == 0) {
                                    mViewDataBinding.requestListLayout.visibility = View.GONE
                                    if (!not_list_check) {
                                        mViewDataBinding.requestNotListLayout.visibility =
                                            View.VISIBLE
                                        not_list_check = true
                                    }
                                    notlisttitle()
                                }


                            }
                        }
                    })
            }   //예약취소(받은견적)
            2004 -> {
                Toast.makeText(this, "취소요청 완료", Toast.LENGTH_SHORT).show()
                requestManager.instance.sendList(
                    userIdx,
                    completion = { responseStatus, arrayList ->
                        when (responseStatus) {
                            RESPONSE_STATUS.OKAY -> {
                                sendOneList = arrayList[0].reserve
                                sendOneClickList = arrayList[0].oneClick

                                mViewDataBinding.requestListCa0.text =
                                    arrayList[0].request.toString()
                                mViewDataBinding.requestListCa1.text =
                                    arrayList[0].response.toString()
                                mViewDataBinding.requestListCa2.text =
                                    arrayList[0].progress.toString()
                                mViewDataBinding.requestListCa3.text =
                                    arrayList[0].complete.toString()
                                mViewDataBinding.requestListCa4.text =
                                    arrayList[0].review.toString()
                                mViewDataBinding.requestListCa5.text =
                                    arrayList[0].refund_request.toString()
                                mViewDataBinding.requestListCa6.text =
                                    arrayList[0].refund_complete.toString()


                                if (sendOneList.size != 0) {
                                    if (not_list_check) {
                                        mViewDataBinding.requestNotListLayout.visibility =
                                            View.GONE
                                        mViewDataBinding.requestListLayout.visibility =
                                            View.VISIBLE
                                        not_list_check = false
                                    }
                                    mViewDataBinding.requestOneRecyclerview.visibility =
                                        View.VISIBLE
                                    mViewDataBinding.requestListTitle1.visibility = View.VISIBLE

                                    send_one_Adapter =
                                        SendOneRecyclerViewAdapter(sendOneList, this, 0)
                                    mViewDataBinding.requestOneRecyclerview.layoutManager =
                                        LinearLayoutManager(
                                            this,
                                            LinearLayoutManager.VERTICAL, false
                                        )
                                    mViewDataBinding.requestOneRecyclerview.adapter =
                                        send_one_Adapter

                                    send_one_Adapter.setItemClickListener(object :
                                        SendOneRecyclerViewAdapter.OnItemClickListener {
                                        override fun onClick(
                                            v: View,
                                            position: Int,
                                            item: sendone
                                        ) {
                                            when (item.expert_type) {
                                                0 -> {
                                                    val newIntent =
                                                        Intent(
                                                            this@RequestListActivity,
                                                            SendStudioActivity::class.java
                                                        )
                                                    newIntent.putExtra(
                                                        "reserve_idx",
                                                        item.reserve_idx
                                                    )
                                                    newIntent.putExtra("type", 0)
                                                    startActivityForResult(newIntent, 2000)
                                                }
                                                1 -> {
                                                    val newIntent =
                                                        Intent(
                                                            this@RequestListActivity,
                                                            SendPhotoActivity::class.java
                                                        )
                                                    newIntent.putExtra(
                                                        "reserve_idx",
                                                        item.reserve_idx
                                                    )
                                                    newIntent.putExtra("type", 0)
                                                    startActivityForResult(newIntent, 2000)
                                                }
                                                2 -> {
                                                    val newIntent =
                                                        Intent(
                                                            this@RequestListActivity,
                                                            SendModelActivity::class.java
                                                        )
                                                    newIntent.putExtra(
                                                        "reserve_idx",
                                                        item.reserve_idx
                                                    )
                                                    newIntent.putExtra("type", 0)
                                                    startActivityForResult(newIntent, 2000)
                                                }
                                                3 -> {
                                                    val newIntent =
                                                        Intent(
                                                            this@RequestListActivity,
                                                            SendTripActivity::class.java
                                                        )
                                                    newIntent.putExtra(
                                                        "reserve_idx",
                                                        item.reserve_idx
                                                    )
                                                    newIntent.putExtra("type", 0)
                                                    startActivityForResult(newIntent, 2000)
                                                }
                                            }
                                        }

                                    })
                                } else {
                                    mViewDataBinding.requestOneRecyclerview.visibility =
                                        View.GONE
                                    mViewDataBinding.requestListTitle1.visibility = View.GONE
                                }

                                if (sendOneClickList.size != 0) {
                                    if (not_list_check) {
                                        mViewDataBinding.requestNotListLayout.visibility =
                                            View.GONE
                                        mViewDataBinding.requestListLayout.visibility =
                                            View.VISIBLE
                                        not_list_check = false
                                    }
                                    mViewDataBinding.requestOneClickRecyclerview.visibility =
                                        View.VISIBLE
                                    mViewDataBinding.requestListTitle2.visibility = View.VISIBLE

                                    send_oneclick_Adapter =
                                        SendOneClickRecyclerViewAdapter(
                                            sendOneClickList,
                                            this,
                                            0
                                        )
                                    mViewDataBinding.requestOneClickRecyclerview.layoutManager =
                                        LinearLayoutManager(
                                            this,
                                            LinearLayoutManager.VERTICAL, false
                                        )
                                    mViewDataBinding.requestOneClickRecyclerview.adapter =
                                        send_oneclick_Adapter

                                    send_oneclick_Adapter.setItemClickListener(object :
                                        SendOneClickRecyclerViewAdapter.OnItemClickListener {
                                        override fun onClick(
                                            v: View,
                                            position: Int,
                                            item: sendoneclick
                                        ) {
                                            val newIntent =
                                                Intent(
                                                    this@RequestListActivity,
                                                    SendOneClickActivity::class.java
                                                )
                                            newIntent.putExtra("reserve_idx", item.reserve_idx)
                                            startActivityForResult(newIntent, 2000)
                                        }

                                    })

                                } else {
                                    mViewDataBinding.requestOneClickRecyclerview.visibility =
                                        View.GONE
                                    mViewDataBinding.requestListTitle2.visibility = View.GONE
                                }

                                if (sendOneList.size == 0 && sendOneClickList.size == 0) {
                                    mViewDataBinding.requestListLayout.visibility = View.GONE
                                    if (!not_list_check) {
                                        mViewDataBinding.requestNotListLayout.visibility =
                                            View.VISIBLE
                                        not_list_check = true
                                    }
                                    notlisttitle()
                                }

                            }
                        }
                    })
            }   //여약취소(보낸요청)
            2002 -> {
                Toast.makeText(this, "리뷰작성 완료", Toast.LENGTH_SHORT).show()
                requestManager.instance.reviewList(
                    userIdx,
                    completion = { responseStatus, arrayList ->
                        when (responseStatus) {
                            RESPONSE_STATUS.OKAY -> {
                                reviewOneList = arrayList[0].reserve
                                reviewOneClickList = arrayList[0].oneClick

                                mViewDataBinding.requestListCa0.text =
                                    arrayList[0].request.toString()
                                mViewDataBinding.requestListCa1.text =
                                    arrayList[0].response.toString()
                                mViewDataBinding.requestListCa2.text =
                                    arrayList[0].progress.toString()
                                mViewDataBinding.requestListCa3.text =
                                    arrayList[0].complete.toString()
                                mViewDataBinding.requestListCa4.text =
                                    arrayList[0].review.toString()
                                mViewDataBinding.requestListCa5.text =
                                    arrayList[0].refund_request.toString()
                                mViewDataBinding.requestListCa6.text =
                                    arrayList[0].refund_complete.toString()

                                if (reviewOneList.size != 0) {
                                    if (not_list_check) {
                                        mViewDataBinding.requestNotListLayout.visibility =
                                            View.GONE
                                        mViewDataBinding.requestListLayout.visibility =
                                            View.VISIBLE
                                        not_list_check = false
                                    }
                                    mViewDataBinding.requestListTitle1.visibility = View.VISIBLE
                                    mViewDataBinding.requestOneRecyclerview.visibility =
                                        View.VISIBLE

                                    review_one_Adapter =
                                        ReviewOneRecyclerViewAdapter(reviewOneList, this)
                                    mViewDataBinding.requestOneRecyclerview.layoutManager =
                                        LinearLayoutManager(
                                            this,
                                            LinearLayoutManager.VERTICAL, false
                                        )
                                    mViewDataBinding.requestOneRecyclerview.adapter =
                                        review_one_Adapter

                                    review_one_Adapter.setReviewClickListener(object : ReviewOneRecyclerViewAdapter.OnReviewClickListener {
                                        override fun onClick(
                                            v: View,
                                            position: Int,
                                            item: reviewone
                                        ) {
                                            val newIntent = Intent(this@RequestListActivity, ReviewActivity::class.java)
                                            newIntent.putExtra("reserve_idx", item.reserve_idx)
                                            newIntent.putExtra("type", 0)
                                            startActivityForResult(newIntent, 3003)
                                        }

                                    })

                                    review_one_Adapter.setItemClickListener(object :
                                        ReviewOneRecyclerViewAdapter.OnItemClickListener {
                                        override fun onClick(
                                            v: View,
                                            position: Int,
                                            item: reviewone
                                        ) {
                                            when (item.expert_type) {
                                                0 -> {
                                                    val newIntent = Intent(
                                                        this@RequestListActivity,
                                                        ProgressStudioActivity::class.java
                                                    )
                                                    newIntent.putExtra(
                                                        "reserve_idx",
                                                        item.reserve_idx
                                                    )
                                                    newIntent.putExtra("type",4)
                                                    newIntent.putExtra("chatbool", 0)
                                                    startActivityForResult(newIntent, 2000)
                                                }
                                                1 -> {
                                                    val newIntent = Intent(
                                                        this@RequestListActivity,
                                                        ProgressPhotoActivity::class.java
                                                    )
                                                    newIntent.putExtra(
                                                        "reserve_idx",
                                                        item.reserve_idx
                                                    )
                                                    newIntent.putExtra("type",4)
                                                    newIntent.putExtra("chatbool", 0)
                                                    startActivityForResult(newIntent, 2000)
                                                }
                                                2 -> {
                                                    val newIntent = Intent(
                                                        this@RequestListActivity,
                                                        ProgressModelActivity::class.java
                                                    )
                                                    newIntent.putExtra(
                                                        "reserve_idx",
                                                        item.reserve_idx
                                                    )
                                                    newIntent.putExtra("type",4)
                                                    newIntent.putExtra("chatbool", 0)
                                                    startActivityForResult(newIntent, 2000)
                                                }
                                                3 -> {
                                                    val newIntent = Intent(
                                                        this@RequestListActivity,
                                                        ProgressTripActivity::class.java
                                                    )
                                                    newIntent.putExtra(
                                                        "reserve_idx",
                                                        item.reserve_idx
                                                    )
                                                    newIntent.putExtra("type",4)
                                                    newIntent.putExtra("chatbool", 0)
                                                    startActivityForResult(newIntent, 2000)
                                                }
                                            }
                                        }

                                    })

                                } else {
                                    mViewDataBinding.requestOneRecyclerview.visibility =
                                        View.GONE
                                    mViewDataBinding.requestListTitle1.visibility = View.GONE
                                }

                                if (reviewOneClickList.size != 0) {
                                    if (not_list_check) {
                                        mViewDataBinding.requestNotListLayout.visibility =
                                            View.GONE
                                        mViewDataBinding.requestListLayout.visibility =
                                            View.VISIBLE
                                        not_list_check = false
                                    }
                                    mViewDataBinding.requestOneClickRecyclerview.visibility =
                                        View.VISIBLE
                                    mViewDataBinding.requestListTitle2.visibility = View.VISIBLE

                                    review_oneclick_Adapter =
                                        ReviewOneClickRecyclerViewAdapter(
                                            reviewOneClickList,
                                            this,
                                            4
                                        )
                                    mViewDataBinding.requestOneClickRecyclerview.layoutManager =
                                        LinearLayoutManager(
                                            this,
                                            LinearLayoutManager.VERTICAL, false
                                        )
                                    mViewDataBinding.requestOneClickRecyclerview.adapter =
                                        review_oneclick_Adapter

                                } else {
                                    mViewDataBinding.requestOneClickRecyclerview.visibility =
                                        View.GONE
                                    mViewDataBinding.requestListTitle2.visibility = View.GONE
                                }

                                if (reviewOneList.size == 0 && reviewOneClickList.size == 0) {
                                    mViewDataBinding.requestListLayout.visibility = View.GONE
                                    if (!not_list_check) {
                                        mViewDataBinding.requestNotListLayout.visibility =
                                            View.VISIBLE
                                        not_list_check = true
                                    }
                                    notlisttitle()
                                }


                            }
                        }
                    })
            }
        }
    }

    private fun progressOkRefresh() {
        requestManager.instance.progressOkList(
            userIdx,
            completion = { responseStatus, arrayList ->
                when (responseStatus) {
                    RESPONSE_STATUS.OKAY -> {
                        sendOneList = arrayList[0].reserve
                        receiveOneClickList = arrayList[0].oneClick

                        mViewDataBinding.requestListCa0.text =
                            arrayList[0].request.toString()
                        mViewDataBinding.requestListCa1.text =
                            arrayList[0].response.toString()
                        mViewDataBinding.requestListCa2.text =
                            arrayList[0].progress.toString()
                        mViewDataBinding.requestListCa3.text =
                            arrayList[0].complete.toString()
                        mViewDataBinding.requestListCa4.text =
                            arrayList[0].review.toString()
                        mViewDataBinding.requestListCa5.text =
                            arrayList[0].refund_request.toString()
                        mViewDataBinding.requestListCa6.text =
                            arrayList[0].refund_complete.toString()

                        if (sendOneList.size != 0) {
                            if (not_list_check) {
                                mViewDataBinding.requestNotListLayout.visibility =
                                    View.GONE
                                mViewDataBinding.requestListLayout.visibility =
                                    View.VISIBLE
                                not_list_check = false
                            }
                            send_one_Adapter =
                                SendOneRecyclerViewAdapter(sendOneList, this, 3)
                            mViewDataBinding.requestOneRecyclerview.layoutManager =
                                LinearLayoutManager(
                                    this,
                                    LinearLayoutManager.VERTICAL, false
                                )
                            mViewDataBinding.requestOneRecyclerview.adapter =
                                send_one_Adapter

                            send_one_Adapter.setpurchaseClickListener(object :
                                SendOneRecyclerViewAdapter.OnPurchaseClickListener {
                                override fun onClick(
                                    v: View,
                                    position: Int,
                                    item: sendone
                                ) {
                                    MaterialAlertDialogBuilder(
                                        this@RequestListActivity,
                                        R.style.AlertDialogTheme
                                    )
                                        .setTitle("구매확정")
                                        .setMessage("구매 확정을 하시겠습니까?")
                                        .setCancelable(false)
                                        .setNeutralButton("취소") { dialog, which ->
                                            dialog.dismiss()
                                        }
                                        .setPositiveButton("확정") { dialog, which ->
                                            progressManager.instance.reserve_complete(
                                                item.reserve_idx,
                                                completion = { responseStatus ->
                                                    when (responseStatus) {
                                                        ESTIMATE.OKAY -> {
                                                            //dialog.dismiss()
                                                            Toast.makeText(
                                                                this@RequestListActivity,
                                                                "후기 작성 탭에서 후기를 작성해 주세요!",
                                                                Toast.LENGTH_SHORT
                                                            ).show()
                                                            progressOkRefresh()
                                                        }
                                                    }
                                                })
                                        }.show()
                                }

                            })
                        }

                        if (receiveOneClickList.size != 0) {
                            if (not_list_check) {
                                mViewDataBinding.requestNotListLayout.visibility =
                                    View.GONE
                                mViewDataBinding.requestListLayout.visibility =
                                    View.VISIBLE
                                not_list_check = false
                            }
                            send_oneclick_Adapter =
                                SendOneClickRecyclerViewAdapter(
                                    sendOneClickList,
                                    this,
                                    3
                                )
                            mViewDataBinding.requestOneClickRecyclerview.layoutManager =
                                LinearLayoutManager(
                                    this,
                                    LinearLayoutManager.VERTICAL, false
                                )
                            mViewDataBinding.requestOneClickRecyclerview.adapter =
                                send_oneclick_Adapter
                            send_oneclick_Adapter.setPurchaseClickListener(object :
                                SendOneClickRecyclerViewAdapter.OnPurchaseClickListener {
                                override fun onClick(
                                    v: View,
                                    position: Int,
                                    item: sendoneclick
                                ) {
                                    MaterialAlertDialogBuilder(
                                        this@RequestListActivity,
                                        R.style.AlertDialogTheme
                                    )
                                        .setTitle("구매확정")
                                        .setMessage("구매 확정을 하시겠습니까?")
                                        .setCancelable(false)
                                        .setNeutralButton("취소") { dialog, which ->
                                            dialog.dismiss()
                                        }
                                        .setPositiveButton("확정") { dialog, which ->
                                            progressManager.instance.reserve_complete(
                                                item.reserve_idx,
                                                completion = { responseStatus ->
                                                    when (responseStatus) {
                                                        ESTIMATE.OKAY -> {
                                                            dialog.dismiss()
                                                            Toast.makeText(
                                                                this@RequestListActivity,
                                                                "후기 작성 탭에서 후기를 작성해 주세요!",
                                                                Toast.LENGTH_SHORT
                                                            ).show()
                                                            progressOkRefresh()
                                                        }
                                                    }
                                                })
                                        }.show()
                                }

                            })

                        }

                        if (sendOneList.size == 0 && receiveOneClickList.size == 0) {
                            mViewDataBinding.requestListLayout.visibility = View.GONE
                            if (!not_list_check) {
                                mViewDataBinding.requestNotListLayout.visibility =
                                    View.VISIBLE
                                not_list_check = true
                            }
                            notlisttitle()
                        }


                    }
                }
            })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.move_expert_list -> {
                var newIntent = Intent(this, ListContainerActivity::class.java)
                newIntent.putExtra("num", 0)
                startActivity(newIntent)
            }
            R.id.request_list_ca_btn_1 -> {
                if (post_ca_btn != 0) {
                    mViewDataBinding.requestListCaBtn1.setBackgroundResource(R.drawable.mypage_ca_on)
                    mViewDataBinding.requestListCaBtn1.setTextColor(Color.parseColor("#6d34f3"))
                    mViewDataBinding.requestListCaBtn2.setBackgroundResource(R.drawable.mypage_ca_off)
                    mViewDataBinding.requestListCaBtn2.setTextColor(Color.parseColor("#838383"))
                    mViewDataBinding.requestListCaLayout1.visibility = View.VISIBLE
                    mViewDataBinding.requestListCaLayout2.visibility = View.GONE
                    post_ca_btn = 0
                }
            }
            R.id.request_list_ca_btn_2 -> {
                if (post_ca_btn != 1) {
                    mViewDataBinding.requestListCaBtn2.setBackgroundResource(R.drawable.mypage_ca_on)
                    mViewDataBinding.requestListCaBtn2.setTextColor(Color.parseColor("#6d34f3"))
                    mViewDataBinding.requestListCaBtn1.setBackgroundResource(R.drawable.mypage_ca_off)
                    mViewDataBinding.requestListCaBtn1.setTextColor(Color.parseColor("#838383"))
                    mViewDataBinding.requestListCaLayout2.visibility = View.VISIBLE
                    mViewDataBinding.requestListCaLayout1.visibility = View.GONE
                    post_ca_btn = 1
                }
            }

            R.id.request_list_ca_0 -> {
                if (post_ca != 0) {
                    poastchange(post_ca)
                    mViewDataBinding.requestListCa0.setBackgroundResource(R.drawable.request_ca_round_on)
                    mViewDataBinding.requestListBac.setBackgroundResource(R.drawable.mypage_request_list_bac)
                    mViewDataBinding.requestListTitle1.text = "보낸요청 (1:1)"
                    mViewDataBinding.requestListTitle2.text = "보낸요청 (원클릭)"
                    post_ca = 0
                    list_type = 0

                    requestManager.instance.sendList(
                        userIdx,
                        completion = { responseStatus, arrayList ->
                            when (responseStatus) {
                                RESPONSE_STATUS.OKAY -> {
                                    sendOneList = arrayList[0].reserve
                                    sendOneClickList = arrayList[0].oneClick

                                    mViewDataBinding.requestListCa0.text =
                                        arrayList[0].request.toString()
                                    mViewDataBinding.requestListCa1.text =
                                        arrayList[0].response.toString()
                                    mViewDataBinding.requestListCa2.text =
                                        arrayList[0].progress.toString()
                                    mViewDataBinding.requestListCa3.text =
                                        arrayList[0].complete.toString()
                                    mViewDataBinding.requestListCa4.text =
                                        arrayList[0].review.toString()
                                    mViewDataBinding.requestListCa5.text =
                                        arrayList[0].refund_request.toString()
                                    mViewDataBinding.requestListCa6.text =
                                        arrayList[0].refund_complete.toString()


                                    if (sendOneList.size != 0) {
                                        if (not_list_check) {
                                            mViewDataBinding.requestNotListLayout.visibility =
                                                View.GONE
                                            mViewDataBinding.requestListLayout.visibility =
                                                View.VISIBLE
                                            not_list_check = false
                                        }
                                        mViewDataBinding.requestOneRecyclerview.visibility =
                                            View.VISIBLE
                                        mViewDataBinding.requestListTitle1.visibility = View.VISIBLE

                                        send_one_Adapter =
                                            SendOneRecyclerViewAdapter(sendOneList, this, 0)
                                        mViewDataBinding.requestOneRecyclerview.layoutManager =
                                            LinearLayoutManager(
                                                this,
                                                LinearLayoutManager.VERTICAL, false
                                            )
                                        mViewDataBinding.requestOneRecyclerview.adapter =
                                            send_one_Adapter

                                        send_one_Adapter.setItemClickListener(object :
                                            SendOneRecyclerViewAdapter.OnItemClickListener {
                                            override fun onClick(
                                                v: View,
                                                position: Int,
                                                item: sendone
                                            ) {
                                                when (item.expert_type) {
                                                    0 -> {
                                                        val newIntent =
                                                            Intent(
                                                                this@RequestListActivity,
                                                                SendStudioActivity::class.java
                                                            )
                                                        newIntent.putExtra(
                                                            "reserve_idx",
                                                            item.reserve_idx
                                                        )
                                                        newIntent.putExtra("type", 0)
                                                        startActivityForResult(newIntent, 2000)
                                                    }
                                                    1 -> {
                                                        val newIntent =
                                                            Intent(
                                                                this@RequestListActivity,
                                                                SendPhotoActivity::class.java
                                                            )
                                                        newIntent.putExtra(
                                                            "reserve_idx",
                                                            item.reserve_idx
                                                        )
                                                        newIntent.putExtra("type", 0)
                                                        startActivityForResult(newIntent, 2000)
                                                    }
                                                    2 -> {
                                                        val newIntent =
                                                            Intent(
                                                                this@RequestListActivity,
                                                                SendModelActivity::class.java
                                                            )
                                                        newIntent.putExtra(
                                                            "reserve_idx",
                                                            item.reserve_idx
                                                        )
                                                        newIntent.putExtra("type", 0)
                                                        startActivityForResult(newIntent, 2000)
                                                    }
                                                    3 -> {
                                                        val newIntent =
                                                            Intent(
                                                                this@RequestListActivity,
                                                                SendTripActivity::class.java
                                                            )
                                                        newIntent.putExtra(
                                                            "reserve_idx",
                                                            item.reserve_idx
                                                        )
                                                        newIntent.putExtra("type", 0)
                                                        startActivityForResult(newIntent, 2000)
                                                    }
                                                }
                                            }

                                        })
                                    } else {
                                        mViewDataBinding.requestOneRecyclerview.visibility =
                                            View.GONE
                                        mViewDataBinding.requestListTitle1.visibility = View.GONE
                                    }

                                    if (sendOneClickList.size != 0) {
                                        if (not_list_check) {
                                            mViewDataBinding.requestNotListLayout.visibility =
                                                View.GONE
                                            mViewDataBinding.requestListLayout.visibility =
                                                View.VISIBLE
                                            not_list_check = false
                                        }
                                        mViewDataBinding.requestOneClickRecyclerview.visibility =
                                            View.VISIBLE
                                        mViewDataBinding.requestListTitle2.visibility = View.VISIBLE

                                        send_oneclick_Adapter =
                                            SendOneClickRecyclerViewAdapter(
                                                sendOneClickList,
                                                this,
                                                0
                                            )
                                        mViewDataBinding.requestOneClickRecyclerview.layoutManager =
                                            LinearLayoutManager(
                                                this,
                                                LinearLayoutManager.VERTICAL, false
                                            )
                                        mViewDataBinding.requestOneClickRecyclerview.adapter =
                                            send_oneclick_Adapter

                                        send_oneclick_Adapter.setItemClickListener(object :
                                            SendOneClickRecyclerViewAdapter.OnItemClickListener {
                                            override fun onClick(
                                                v: View,
                                                position: Int,
                                                item: sendoneclick
                                            ) {
                                                val newIntent =
                                                    Intent(
                                                        this@RequestListActivity,
                                                        SendOneClickActivity::class.java
                                                    )
                                                newIntent.putExtra("reserve_idx", item.reserve_idx)
                                                startActivityForResult(newIntent, 2000)
                                            }

                                        })

                                    } else {
                                        mViewDataBinding.requestOneClickRecyclerview.visibility =
                                            View.GONE
                                        mViewDataBinding.requestListTitle2.visibility = View.GONE
                                    }

                                    if (sendOneList.size == 0 && sendOneClickList.size == 0) {
                                        mViewDataBinding.requestListLayout.visibility = View.GONE
                                        if (!not_list_check) {
                                            mViewDataBinding.requestNotListLayout.visibility =
                                                View.VISIBLE
                                            not_list_check = true
                                        }
                                        notlisttitle()
                                    }

                                }
                            }
                        })
                }
            }

            R.id.request_list_ca_1 -> {
                if (post_ca != 1) {
                    poastchange(post_ca)
                    mViewDataBinding.requestListCa1.setBackgroundResource(R.drawable.request_ca_round_on)
                    mViewDataBinding.requestListBac.setBackgroundResource(R.drawable.mypage_request_list_bac)
                    mViewDataBinding.requestListTitle1.text = "받은견적 (1:1)"
                    mViewDataBinding.requestListTitle2.text = "받은견적 (원클릭)"
                    post_ca = 1
                    list_type = 1

                    requestManager.instance.receiveList(
                        userIdx,
                        completion = { responseStatus, arrayList ->
                            when (responseStatus) {
                                RESPONSE_STATUS.OKAY -> {
                                    sendOneList = arrayList[0].reserve
                                    receiveOneClickList = arrayList[0].oneClick

                                    mViewDataBinding.requestListCa0.text =
                                        arrayList[0].request.toString()
                                    mViewDataBinding.requestListCa1.text =
                                        arrayList[0].response.toString()
                                    mViewDataBinding.requestListCa2.text =
                                        arrayList[0].progress.toString()
                                    mViewDataBinding.requestListCa3.text =
                                        arrayList[0].complete.toString()
                                    mViewDataBinding.requestListCa4.text =
                                        arrayList[0].review.toString()
                                    mViewDataBinding.requestListCa5.text =
                                        arrayList[0].refund_request.toString()
                                    mViewDataBinding.requestListCa6.text =
                                        arrayList[0].refund_complete.toString()

                                    if (sendOneList.size != 0) {
                                        if (not_list_check) {
                                            mViewDataBinding.requestNotListLayout.visibility =
                                                View.GONE
                                            mViewDataBinding.requestListLayout.visibility =
                                                View.VISIBLE
                                            not_list_check = false
                                        }
                                        mViewDataBinding.requestOneRecyclerview.visibility =
                                            View.VISIBLE
                                        mViewDataBinding.requestListTitle1.visibility = View.VISIBLE

                                        send_one_Adapter =
                                            SendOneRecyclerViewAdapter(sendOneList, this, 1)
                                        mViewDataBinding.requestOneRecyclerview.layoutManager =
                                            LinearLayoutManager(
                                                this,
                                                LinearLayoutManager.VERTICAL, false
                                            )
                                        mViewDataBinding.requestOneRecyclerview.adapter =
                                            send_one_Adapter

                                        send_one_Adapter.setItemClickListener(object :
                                            SendOneRecyclerViewAdapter.OnItemClickListener {
                                            override fun onClick(
                                                v: View,
                                                position: Int,
                                                item: sendone
                                            ) {
                                                when (item.expert_type) {
                                                    0 -> {
                                                        val newIntent =
                                                            Intent(
                                                                this@RequestListActivity,
                                                                ReceiveStudioActivity::class.java
                                                            )
                                                        newIntent.putExtra(
                                                            "reserve_idx",
                                                            item.reserve_idx
                                                        )
                                                        newIntent.putExtra("type", 0)
                                                        startActivityForResult(newIntent, 3000)
                                                    }
                                                    1 -> {
                                                        val newIntent =
                                                            Intent(
                                                                this@RequestListActivity,
                                                                ReceivePhotoActivity::class.java
                                                            )
                                                        newIntent.putExtra(
                                                            "reserve_idx",
                                                            item.reserve_idx
                                                        )
                                                        newIntent.putExtra("type", 0)
                                                        startActivityForResult(newIntent, 3000)
                                                    }
                                                    2 -> {
                                                        val newIntent =
                                                            Intent(
                                                                this@RequestListActivity,
                                                                ReceiveModelActivity::class.java
                                                            )
                                                        newIntent.putExtra(
                                                            "reserve_idx",
                                                            item.reserve_idx
                                                        )
                                                        newIntent.putExtra("type", 0)
                                                        startActivityForResult(newIntent, 3000)
                                                    }
                                                    3 -> {
                                                        val newIntent =
                                                            Intent(
                                                                this@RequestListActivity,
                                                                ReceiveTripActivity::class.java
                                                            )
                                                        newIntent.putExtra(
                                                            "reserve_idx",
                                                            item.reserve_idx
                                                        )
                                                        newIntent.putExtra("type", 0)
                                                        startActivityForResult(newIntent, 3000)
                                                    }
                                                }
                                            }
                                        })
                                    } else {
                                        mViewDataBinding.requestOneRecyclerview.visibility =
                                            View.GONE
                                        mViewDataBinding.requestListTitle1.visibility = View.GONE
                                    }
                                    if (receiveOneClickList.size != 0) {
                                        if (not_list_check) {
                                            mViewDataBinding.requestNotListLayout.visibility =
                                                View.GONE
                                            mViewDataBinding.requestListLayout.visibility =
                                                View.VISIBLE
                                            not_list_check = false
                                        }
                                        mViewDataBinding.requestListTitle2.visibility = View.VISIBLE
                                        mViewDataBinding.requestOneClickRecyclerview.visibility =
                                            View.VISIBLE

                                        receive_onCLick_Adapter =
                                            ReceiveOneClickRecyclerViewAdapter(
                                                receiveOneClickList,
                                                this,
                                                0
                                            )
                                        mViewDataBinding.requestOneClickRecyclerview.layoutManager =
                                            LinearLayoutManager(
                                                this,
                                                LinearLayoutManager.VERTICAL, false
                                            )
                                        mViewDataBinding.requestOneClickRecyclerview.adapter =
                                            receive_onCLick_Adapter

                                        receive_onCLick_Adapter.setItemClickListener(object :
                                            ReceiveOneClickRecyclerViewAdapter.OnItemClickListener {
                                            override fun onClick(
                                                v: View,
                                                position: Int,
                                                item: receiveoneclick
                                            ) {
                                                val newIntent =
                                                    Intent(
                                                        this@RequestListActivity,
                                                        ReceiveOneClickActivity::class.java
                                                    )
                                                newIntent.putExtra("reserve_idx", item.reserve_idx)
                                                startActivityForResult(newIntent, 3000)
                                            }
                                        })

                                    } else {
                                        mViewDataBinding.requestListTitle2.visibility = View.GONE
                                        mViewDataBinding.requestOneClickRecyclerview.visibility =
                                            View.GONE
                                    }

                                    if (sendOneList.size == 0 && receiveOneClickList.size == 0) {
                                        mViewDataBinding.requestListLayout.visibility = View.GONE
                                        if (!not_list_check) {
                                            mViewDataBinding.requestNotListLayout.visibility =
                                                View.VISIBLE
                                            not_list_check = true
                                        }
                                        notlisttitle()
                                    }


                                }
                            }
                        })


                }
            }

            R.id.request_list_ca_2 -> {
                if (post_ca != 2) {
                    poastchange(post_ca)
                    mViewDataBinding.requestListCa2.setBackgroundResource(R.drawable.request_ca_round_on)
                    mViewDataBinding.requestListBac.setBackgroundResource(R.drawable.mypage_request_list_bac)
                    mViewDataBinding.requestListTitle1.text = "결제완료 (1:1)"
                    mViewDataBinding.requestListTitle2.text = "결제완료 (원클릭)"
                    post_ca = 2
                    list_type = 2

                    requestManager.instance.progressList(
                        userIdx,
                        completion = { responseStatus, arrayList ->
                            when (responseStatus) {
                                RESPONSE_STATUS.OKAY -> {
                                    sendOneList = arrayList[0].reserve
                                    receiveOneClickList = arrayList[0].oneClick

                                    mViewDataBinding.requestListCa0.text =
                                        arrayList[0].request.toString()
                                    mViewDataBinding.requestListCa1.text =
                                        arrayList[0].response.toString()
                                    mViewDataBinding.requestListCa2.text =
                                        arrayList[0].progress.toString()
                                    mViewDataBinding.requestListCa3.text =
                                        arrayList[0].complete.toString()
                                    mViewDataBinding.requestListCa4.text =
                                        arrayList[0].review.toString()
                                    mViewDataBinding.requestListCa5.text =
                                        arrayList[0].refund_request.toString()
                                    mViewDataBinding.requestListCa6.text =
                                        arrayList[0].refund_complete.toString()

                                    if (sendOneList.size != 0) {
                                        if (not_list_check) {
                                            mViewDataBinding.requestNotListLayout.visibility =
                                                View.GONE
                                            mViewDataBinding.requestListLayout.visibility =
                                                View.VISIBLE
                                            not_list_check = false
                                        }
                                        mViewDataBinding.requestListTitle1.visibility = View.VISIBLE
                                        mViewDataBinding.requestOneRecyclerview.visibility =
                                            View.VISIBLE

                                        send_one_Adapter =
                                            SendOneRecyclerViewAdapter(sendOneList, this, 2)
                                        mViewDataBinding.requestOneRecyclerview.layoutManager =
                                            LinearLayoutManager(
                                                this,
                                                LinearLayoutManager.VERTICAL, false
                                            )
                                        mViewDataBinding.requestOneRecyclerview.adapter =
                                            send_one_Adapter

                                        send_one_Adapter.setItemClickListener(object :
                                            SendOneRecyclerViewAdapter.OnItemClickListener {
                                            override fun onClick(
                                                v: View,
                                                position: Int,
                                                item: sendone
                                            ) {
                                                when (item.expert_type) {
                                                    0 -> {
                                                        val newIntent = Intent(
                                                            this@RequestListActivity,
                                                            ProgressStudioActivity::class.java
                                                        )
                                                        newIntent.putExtra(
                                                            "reserve_idx",
                                                            item.reserve_idx
                                                        )
                                                        newIntent.putExtra("type",2)
                                                        newIntent.putExtra("chatbool", 0)
                                                        startActivityForResult(newIntent, 2000)
                                                    }
                                                    1 -> {
                                                        val newIntent = Intent(
                                                            this@RequestListActivity,
                                                            ProgressPhotoActivity::class.java
                                                        )
                                                        newIntent.putExtra(
                                                            "reserve_idx",
                                                            item.reserve_idx
                                                        )
                                                        newIntent.putExtra("type",2)
                                                        newIntent.putExtra("chatbool", 0)
                                                        startActivityForResult(newIntent, 2000)
                                                    }
                                                    2 -> {
                                                        val newIntent = Intent(
                                                            this@RequestListActivity,
                                                            ProgressModelActivity::class.java
                                                        )
                                                        newIntent.putExtra(
                                                            "reserve_idx",
                                                            item.reserve_idx
                                                        )
                                                        newIntent.putExtra("type",2)
                                                        newIntent.putExtra("chatbool", 0)
                                                        startActivityForResult(newIntent, 2000)
                                                    }
                                                    3 -> {
                                                        val newIntent = Intent(
                                                            this@RequestListActivity,
                                                            ProgressTripActivity::class.java
                                                        )
                                                        newIntent.putExtra(
                                                            "reserve_idx",
                                                            item.reserve_idx
                                                        )
                                                        newIntent.putExtra("type",2)
                                                        newIntent.putExtra("chatbool", 0)
                                                        startActivityForResult(newIntent, 2000)
                                                    }
                                                }
                                            }

                                        })

                                    } else {
                                        mViewDataBinding.requestOneRecyclerview.visibility =
                                            View.GONE
                                        mViewDataBinding.requestListTitle1.visibility = View.GONE
                                    }

                                    if (receiveOneClickList.size != 0) {
                                        if (not_list_check) {
                                            mViewDataBinding.requestNotListLayout.visibility =
                                                View.GONE
                                            mViewDataBinding.requestListLayout.visibility =
                                                View.VISIBLE
                                            not_list_check = false
                                        }
                                        mViewDataBinding.requestListTitle2.visibility = View.VISIBLE
                                        mViewDataBinding.requestOneClickRecyclerview.visibility =
                                            View.VISIBLE

                                        receive_onCLick_Adapter =
                                            ReceiveOneClickRecyclerViewAdapter(
                                                receiveOneClickList,
                                                this,
                                                2
                                            )
                                        mViewDataBinding.requestOneClickRecyclerview.layoutManager =
                                            LinearLayoutManager(
                                                this,
                                                LinearLayoutManager.VERTICAL, false
                                            )
                                        mViewDataBinding.requestOneClickRecyclerview.adapter =
                                            receive_onCLick_Adapter

                                        receive_onCLick_Adapter.setItemClickListener(object :
                                            ReceiveOneClickRecyclerViewAdapter.OnItemClickListener {
                                            override fun onClick(
                                                v: View,
                                                position: Int,
                                                item: receiveoneclick
                                            ) {
                                                val newIntent =
                                                    Intent(
                                                        this@RequestListActivity,
                                                        ProgressOneClickActivity::class.java
                                                    )
                                                newIntent.putExtra("type",2)
                                                newIntent.putExtra("reserve_idx", item.reserve_idx)
                                                startActivityForResult(newIntent, 3000)
                                            }

                                        })
                                    } else {
                                        mViewDataBinding.requestOneClickRecyclerview.visibility =
                                            View.GONE
                                        mViewDataBinding.requestListTitle2.visibility = View.GONE
                                    }

                                    if (sendOneList.size == 0 && receiveOneClickList.size == 0) {
                                        mViewDataBinding.requestListLayout.visibility = View.GONE
                                        if (!not_list_check) {
                                            mViewDataBinding.requestNotListLayout.visibility =
                                                View.VISIBLE
                                            not_list_check = true
                                        }
                                        notlisttitle()
                                    }


                                }
                            }
                        })

                }
            }

            R.id.request_list_ca_3 -> {
                if (post_ca != 3) {
                    poastchange(post_ca)
                    mViewDataBinding.requestListCa3.setBackgroundResource(R.drawable.request_ca_round_on)
                    mViewDataBinding.requestListBac.setBackgroundResource(R.drawable.mypage_request_list_bac)
                    mViewDataBinding.requestListTitle1.text = "진행완료 (1:1)"
                    mViewDataBinding.requestListTitle2.text = "진행완료 (원클릭)"
                    post_ca = 3
                    list_type = 3

                    requestManager.instance.progressOkList(
                        userIdx,
                        completion = { responseStatus, arrayList ->
                            when (responseStatus) {
                                RESPONSE_STATUS.OKAY -> {
                                    sendOneList = arrayList[0].reserve
                                    receiveOneClickList = arrayList[0].oneClick

                                    mViewDataBinding.requestListCa0.text =
                                        arrayList[0].request.toString()
                                    mViewDataBinding.requestListCa1.text =
                                        arrayList[0].response.toString()
                                    mViewDataBinding.requestListCa2.text =
                                        arrayList[0].progress.toString()
                                    mViewDataBinding.requestListCa3.text =
                                        arrayList[0].complete.toString()
                                    mViewDataBinding.requestListCa4.text =
                                        arrayList[0].review.toString()
                                    mViewDataBinding.requestListCa5.text =
                                        arrayList[0].refund_request.toString()
                                    mViewDataBinding.requestListCa6.text =
                                        arrayList[0].refund_complete.toString()

                                    if (sendOneList.size != 0) {
                                        if (not_list_check) {
                                            mViewDataBinding.requestNotListLayout.visibility =
                                                View.GONE
                                            mViewDataBinding.requestListLayout.visibility =
                                                View.VISIBLE
                                            not_list_check = false
                                        }
                                        mViewDataBinding.requestListTitle1.visibility = View.VISIBLE
                                        mViewDataBinding.requestOneRecyclerview.visibility =
                                            View.VISIBLE

                                        send_one_Adapter =
                                            SendOneRecyclerViewAdapter(sendOneList, this, 3)
                                        mViewDataBinding.requestOneRecyclerview.layoutManager =
                                            LinearLayoutManager(
                                                this,
                                                LinearLayoutManager.VERTICAL, false
                                            )
                                        mViewDataBinding.requestOneRecyclerview.adapter =
                                            send_one_Adapter

                                        send_one_Adapter.setItemClickListener(object :
                                            SendOneRecyclerViewAdapter.OnItemClickListener {
                                            override fun onClick(
                                                v: View,
                                                position: Int,
                                                item: sendone
                                            ) {
                                                when (item.expert_type) {
                                                    0 -> {
                                                        val newIntent = Intent(
                                                            this@RequestListActivity,
                                                            ProgressStudioActivity::class.java
                                                        )
                                                        newIntent.putExtra(
                                                            "reserve_idx",
                                                            item.reserve_idx
                                                        )
                                                        newIntent.putExtra("type",3)
                                                        newIntent.putExtra("chatbool", 0)
                                                        startActivityForResult(newIntent, 2000)
                                                    }
                                                    1 -> {
                                                        val newIntent = Intent(
                                                            this@RequestListActivity,
                                                            ProgressPhotoActivity::class.java
                                                        )
                                                        newIntent.putExtra(
                                                            "reserve_idx",
                                                            item.reserve_idx
                                                        )
                                                        newIntent.putExtra("type",3)
                                                        newIntent.putExtra("chatbool", 0)
                                                        startActivityForResult(newIntent, 2000)
                                                    }
                                                    2 -> {
                                                        val newIntent = Intent(
                                                            this@RequestListActivity,
                                                            ProgressModelActivity::class.java
                                                        )
                                                        newIntent.putExtra(
                                                            "reserve_idx",
                                                            item.reserve_idx
                                                        )
                                                        newIntent.putExtra("type",3)
                                                        newIntent.putExtra("chatbool", 0)
                                                        startActivityForResult(newIntent, 2000)
                                                    }
                                                    3 -> {
                                                        val newIntent = Intent(
                                                            this@RequestListActivity,
                                                            ProgressTripActivity::class.java
                                                        )
                                                        newIntent.putExtra(
                                                            "reserve_idx",
                                                            item.reserve_idx
                                                        )
                                                        newIntent.putExtra("type",3)
                                                        newIntent.putExtra("chatbool", 0)
                                                        startActivityForResult(newIntent, 2000)
                                                    }
                                                }
                                            }

                                        })

                                        send_one_Adapter.setpurchaseClickListener(object :
                                            SendOneRecyclerViewAdapter.OnPurchaseClickListener {
                                            override fun onClick(
                                                v: View,
                                                position: Int,
                                                item: sendone
                                            ) {
                                                MaterialAlertDialogBuilder(
                                                    this@RequestListActivity,
                                                    R.style.AlertDialogTheme
                                                )
                                                    .setTitle("구매확정")
                                                    .setMessage("구매 확정을 하시겠습니까?")
                                                    .setCancelable(false)
                                                    .setNeutralButton("취소") { dialog, which ->
                                                        dialog.dismiss()
                                                    }
                                                    .setPositiveButton("확정") { dialog, which ->
                                                        progressManager.instance.reserve_complete(
                                                            item.reserve_idx,
                                                            completion = { responseStatus ->
                                                                when (responseStatus) {
                                                                    ESTIMATE.OKAY -> {
                                                                        //dialog.dismiss()
                                                                        Toast.makeText(
                                                                            this@RequestListActivity,
                                                                            "후기 작성 탭에서 후기를 작성해 주세요!",
                                                                            Toast.LENGTH_SHORT
                                                                        ).show()
                                                                        progressOkRefresh()
                                                                    }
                                                                }
                                                            })
                                                    }.show()
                                            }

                                        })

                                    } else {
                                        mViewDataBinding.requestOneRecyclerview.visibility =
                                            View.GONE
                                        mViewDataBinding.requestListTitle1.visibility = View.GONE
                                    }

                                    if (receiveOneClickList.size != 0) {
                                        if (not_list_check) {
                                            mViewDataBinding.requestNotListLayout.visibility =
                                                View.GONE
                                            mViewDataBinding.requestListLayout.visibility =
                                                View.VISIBLE
                                            not_list_check = false
                                        }
                                        mViewDataBinding.requestOneClickRecyclerview.visibility =
                                            View.VISIBLE
                                        mViewDataBinding.requestListTitle2.visibility = View.VISIBLE

                                        receive_onCLick_Adapter =
                                            ReceiveOneClickRecyclerViewAdapter(
                                                receiveOneClickList,
                                                this,
                                                3
                                            )
                                        mViewDataBinding.requestOneClickRecyclerview.layoutManager =
                                            LinearLayoutManager(
                                                this,
                                                LinearLayoutManager.VERTICAL, false
                                            )
                                        mViewDataBinding.requestOneClickRecyclerview.adapter =
                                            receive_onCLick_Adapter

                                        receive_onCLick_Adapter.setItemClickListener(object :
                                            ReceiveOneClickRecyclerViewAdapter.OnItemClickListener {
                                            override fun onClick(
                                                v: View,
                                                position: Int,
                                                item: receiveoneclick
                                            ) {
                                                val newIntent =
                                                    Intent(
                                                        this@RequestListActivity,
                                                        ProgressOneClickActivity::class.java
                                                    )
                                                newIntent.putExtra("type",3)
                                                newIntent.putExtra("reserve_idx", item.reserve_idx)
                                                startActivityForResult(newIntent, 3000)
                                            }

                                        })

                                        receive_onCLick_Adapter.setPurchaseClickListener(object :
                                            ReceiveOneClickRecyclerViewAdapter.OnPurchaseClickListener {
                                            override fun onClick(
                                                v: View,
                                                position: Int,
                                                item: receiveoneclick
                                            ) {
                                                MaterialAlertDialogBuilder(
                                                    this@RequestListActivity,
                                                    R.style.AlertDialogTheme
                                                )
                                                    .setTitle("구매확정")
                                                    .setMessage("구매 확정을 하시겠습니까?")
                                                    .setCancelable(false)
                                                    .setNeutralButton("취소") { dialog, which ->
                                                        dialog.dismiss()
                                                    }
                                                    .setPositiveButton("확정") { dialog, which ->
                                                        progressManager.instance.reserve_complete(
                                                            item.reserve_idx,
                                                            completion = { responseStatus ->
                                                                when (responseStatus) {
                                                                    ESTIMATE.OKAY -> {
                                                                        dialog.dismiss()
                                                                        Toast.makeText(
                                                                            this@RequestListActivity,
                                                                            "후기 작성 탭에서 후기를 작성해 주세요!",
                                                                            Toast.LENGTH_SHORT
                                                                        ).show()
                                                                        progressOkRefresh()
                                                                    }
                                                                }
                                                            })
                                                    }.show()
                                            }

                                        })

                                    } else {
                                        mViewDataBinding.requestOneClickRecyclerview.visibility =
                                            View.GONE
                                        mViewDataBinding.requestListTitle2.visibility = View.GONE
                                    }

                                    if (sendOneList.size == 0 && receiveOneClickList.size == 0) {
                                        mViewDataBinding.requestListLayout.visibility = View.GONE
                                        if (!not_list_check) {
                                            mViewDataBinding.requestNotListLayout.visibility =
                                                View.VISIBLE
                                            not_list_check = true
                                        }
                                        notlisttitle()
                                    }


                                }
                            }
                        })
                }
            }

            R.id.request_list_ca_4 -> {
                if (post_ca != 4) {
                    poastchange(post_ca)
                    mViewDataBinding.requestListCa4.setBackgroundResource(R.drawable.request_ca_round_on)
                    mViewDataBinding.requestListBac.setBackgroundResource(R.drawable.mypage_request_list_bac)
                    mViewDataBinding.requestListTitle1.text = "후기작성 (1:1)"
                    mViewDataBinding.requestListTitle2.text = "후기작성 (원클릭)"
                    post_ca = 4
                    list_type = 4

                    requestManager.instance.reviewList(
                        userIdx,
                        completion = { responseStatus, arrayList ->
                            when (responseStatus) {
                                RESPONSE_STATUS.OKAY -> {
                                    reviewOneList = arrayList[0].reserve
                                    reviewOneClickList = arrayList[0].oneClick

                                    mViewDataBinding.requestListCa0.text =
                                        arrayList[0].request.toString()
                                    mViewDataBinding.requestListCa1.text =
                                        arrayList[0].response.toString()
                                    mViewDataBinding.requestListCa2.text =
                                        arrayList[0].progress.toString()
                                    mViewDataBinding.requestListCa3.text =
                                        arrayList[0].complete.toString()
                                    mViewDataBinding.requestListCa4.text =
                                        arrayList[0].review.toString()
                                    mViewDataBinding.requestListCa5.text =
                                        arrayList[0].refund_request.toString()
                                    mViewDataBinding.requestListCa6.text =
                                        arrayList[0].refund_complete.toString()

                                    if (reviewOneList.size != 0) {
                                        if (not_list_check) {
                                            mViewDataBinding.requestNotListLayout.visibility =
                                                View.GONE
                                            mViewDataBinding.requestListLayout.visibility =
                                                View.VISIBLE
                                            not_list_check = false
                                        }
                                        mViewDataBinding.requestListTitle1.visibility = View.VISIBLE
                                        mViewDataBinding.requestOneRecyclerview.visibility =
                                            View.VISIBLE

                                        review_one_Adapter =
                                            ReviewOneRecyclerViewAdapter(reviewOneList, this)
                                        mViewDataBinding.requestOneRecyclerview.layoutManager =
                                            LinearLayoutManager(
                                                this,
                                                LinearLayoutManager.VERTICAL, false
                                            )
                                        mViewDataBinding.requestOneRecyclerview.adapter =
                                            review_one_Adapter

                                        review_one_Adapter.setReviewClickListener(object : ReviewOneRecyclerViewAdapter.OnReviewClickListener {
                                            override fun onClick(
                                                v: View,
                                                position: Int,
                                                item: reviewone
                                            ) {
                                                val newIntent = Intent(this@RequestListActivity, ReviewActivity::class.java)
                                                newIntent.putExtra("reserve_idx", item.reserve_idx)
                                                newIntent.putExtra("type", 0)
                                                startActivityForResult(newIntent, 3003)
                                            }

                                        })

                                        review_one_Adapter.setItemClickListener(object :
                                            ReviewOneRecyclerViewAdapter.OnItemClickListener {
                                            override fun onClick(
                                                v: View,
                                                position: Int,
                                                item: reviewone
                                            ) {
                                                when (item.expert_type) {
                                                    0 -> {
                                                        val newIntent = Intent(
                                                            this@RequestListActivity,
                                                            ProgressStudioActivity::class.java
                                                        )
                                                        newIntent.putExtra(
                                                            "reserve_idx",
                                                            item.reserve_idx
                                                        )
                                                        newIntent.putExtra("type",4)
                                                        newIntent.putExtra("chatbool", 0)
                                                        startActivityForResult(newIntent, 2000)
                                                    }
                                                    1 -> {
                                                        val newIntent = Intent(
                                                            this@RequestListActivity,
                                                            ProgressPhotoActivity::class.java
                                                        )
                                                        newIntent.putExtra(
                                                            "reserve_idx",
                                                            item.reserve_idx
                                                        )
                                                        newIntent.putExtra("type",4)
                                                        newIntent.putExtra("chatbool", 0)
                                                        startActivityForResult(newIntent, 2000)
                                                    }
                                                    2 -> {
                                                        val newIntent = Intent(
                                                            this@RequestListActivity,
                                                            ProgressModelActivity::class.java
                                                        )
                                                        newIntent.putExtra(
                                                            "reserve_idx",
                                                            item.reserve_idx
                                                        )
                                                        newIntent.putExtra("type",4)
                                                        newIntent.putExtra("chatbool", 0)
                                                        startActivityForResult(newIntent, 2000)
                                                    }
                                                    3 -> {
                                                        val newIntent = Intent(
                                                            this@RequestListActivity,
                                                            ProgressTripActivity::class.java
                                                        )
                                                        newIntent.putExtra(
                                                            "reserve_idx",
                                                            item.reserve_idx
                                                        )
                                                        newIntent.putExtra("type",4)
                                                        newIntent.putExtra("chatbool", 0)
                                                        startActivityForResult(newIntent, 2000)
                                                    }
                                                }
                                            }

                                        })

                                    } else {
                                        mViewDataBinding.requestOneRecyclerview.visibility =
                                            View.GONE
                                        mViewDataBinding.requestListTitle1.visibility = View.GONE
                                    }

                                    if (reviewOneClickList.size != 0) {
                                        if (not_list_check) {
                                            mViewDataBinding.requestNotListLayout.visibility =
                                                View.GONE
                                            mViewDataBinding.requestListLayout.visibility =
                                                View.VISIBLE
                                            not_list_check = false
                                        }
                                        mViewDataBinding.requestOneClickRecyclerview.visibility =
                                            View.VISIBLE
                                        mViewDataBinding.requestListTitle2.visibility = View.VISIBLE

                                        review_oneclick_Adapter =
                                            ReviewOneClickRecyclerViewAdapter(
                                                reviewOneClickList,
                                                this,
                                                4
                                            )
                                        mViewDataBinding.requestOneClickRecyclerview.layoutManager =
                                            LinearLayoutManager(
                                                this,
                                                LinearLayoutManager.VERTICAL, false
                                            )
                                        mViewDataBinding.requestOneClickRecyclerview.adapter =
                                            review_oneclick_Adapter

                                        review_oneclick_Adapter.setItemClickListener(object : ReviewOneClickRecyclerViewAdapter.OnItemClickListener{
                                            override fun onClick(
                                                v: View,
                                                position: Int,
                                                item: reviewoneclick
                                            ) {
                                                val newIntent =
                                                    Intent(
                                                        this@RequestListActivity,
                                                        ProgressOneClickActivity::class.java
                                                    )
                                                newIntent.putExtra("type",4)
                                                newIntent.putExtra("reserve_idx", item.reserve_idx)
                                                startActivityForResult(newIntent, 3000)
                                            }

                                        })

                                    } else {
                                        mViewDataBinding.requestOneClickRecyclerview.visibility =
                                            View.GONE
                                        mViewDataBinding.requestListTitle2.visibility = View.GONE
                                    }

                                    if (reviewOneList.size == 0 && reviewOneClickList.size == 0) {
                                        mViewDataBinding.requestListLayout.visibility = View.GONE
                                        if (!not_list_check) {
                                            mViewDataBinding.requestNotListLayout.visibility =
                                                View.VISIBLE
                                            not_list_check = true
                                        }
                                        notlisttitle()
                                    }


                                }
                            }
                        })
                }
            }

            R.id.request_list_ca_5 -> {
                if (post_ca != 5) {
                    poastchange(post_ca)
                    mViewDataBinding.requestListCa5.setBackgroundResource(R.drawable.request_ca_round_on)
                    mViewDataBinding.requestListBac.setBackgroundResource(R.drawable.mypage_request_list_bac_2)
                    mViewDataBinding.requestListTitle1.text = "취소요청 (1:1)"
                    mViewDataBinding.requestListTitle2.text = "취소요청 (원클릭)"
                    post_ca = 5
                    list_type = 5

                    requestManager.instance.refundList(
                        userIdx,
                        completion = { responseStatus, arrayList ->
                            when (responseStatus) {
                                RESPONSE_STATUS.OKAY -> {
                                    sendOneList = arrayList[0].reserve
                                    receiveOneClickList = arrayList[0].oneClick

                                    mViewDataBinding.requestListCa0.text =
                                        arrayList[0].request.toString()
                                    mViewDataBinding.requestListCa1.text =
                                        arrayList[0].response.toString()
                                    mViewDataBinding.requestListCa2.text =
                                        arrayList[0].progress.toString()
                                    mViewDataBinding.requestListCa3.text =
                                        arrayList[0].complete.toString()
                                    mViewDataBinding.requestListCa4.text =
                                        arrayList[0].review.toString()
                                    mViewDataBinding.requestListCa5.text =
                                        arrayList[0].refund_request.toString()
                                    mViewDataBinding.requestListCa6.text =
                                        arrayList[0].refund_complete.toString()

                                    if (sendOneList.size != 0) {
                                        if (not_list_check) {
                                            mViewDataBinding.requestNotListLayout.visibility =
                                                View.GONE
                                            mViewDataBinding.requestListLayout.visibility =
                                                View.VISIBLE
                                            not_list_check = false
                                        }
                                        mViewDataBinding.requestListTitle1.visibility = View.VISIBLE
                                        mViewDataBinding.requestOneRecyclerview.visibility =
                                            View.VISIBLE
                                        send_one_Adapter =
                                            SendOneRecyclerViewAdapter(sendOneList, this, 5)
                                        mViewDataBinding.requestOneRecyclerview.layoutManager =
                                            LinearLayoutManager(
                                                this,
                                                LinearLayoutManager.VERTICAL, false
                                            )
                                        mViewDataBinding.requestOneRecyclerview.adapter =
                                            send_one_Adapter

                                        send_one_Adapter.setItemClickListener(object :
                                            SendOneRecyclerViewAdapter.OnItemClickListener {
                                            override fun onClick(
                                                v: View,
                                                position: Int,
                                                item: sendone
                                            ) {
                                                when (item.expert_type) {
                                                    0 -> {
                                                        val newIntent = Intent(
                                                            this@RequestListActivity,
                                                            RefundStudioActivity::class.java
                                                        )
                                                        newIntent.putExtra(
                                                            "reserve_idx",
                                                            item.reserve_idx
                                                        )
                                                        newIntent.putExtra("type",0)
                                                        newIntent.putExtra("chatbool", 0)
                                                        startActivityForResult(newIntent, 2000)
                                                    }
                                                    1 -> {
                                                        val newIntent = Intent(
                                                            this@RequestListActivity,
                                                            RefundPhotoActivity::class.java
                                                        )
                                                        newIntent.putExtra(
                                                            "reserve_idx",
                                                            item.reserve_idx
                                                        )
                                                        newIntent.putExtra("type",0)
                                                        newIntent.putExtra("chatbool", 0)
                                                        startActivityForResult(newIntent, 2000)
                                                    }
                                                    2 -> {
                                                        val newIntent = Intent(
                                                            this@RequestListActivity,
                                                            RefundModelActivity::class.java
                                                        )
                                                        newIntent.putExtra(
                                                            "reserve_idx",
                                                            item.reserve_idx
                                                        )
                                                        newIntent.putExtra("type",0)
                                                        newIntent.putExtra("chatbool", 0)
                                                        startActivityForResult(newIntent, 2000)
                                                    }
                                                    3 -> {
                                                        val newIntent = Intent(
                                                            this@RequestListActivity,
                                                            RefundTripActivity::class.java
                                                        )
                                                        newIntent.putExtra(
                                                            "reserve_idx",
                                                            item.reserve_idx
                                                        )
                                                        newIntent.putExtra("type",0)
                                                        newIntent.putExtra("chatbool", 0)
                                                        startActivityForResult(newIntent, 2000)
                                                    }
                                                }
                                            }

                                        })
                                    }

                                    if (receiveOneClickList.size != 0) {
                                        if (not_list_check) {
                                            mViewDataBinding.requestNotListLayout.visibility =
                                                View.GONE
                                            mViewDataBinding.requestListLayout.visibility =
                                                View.VISIBLE
                                            not_list_check = false
                                        }
                                        mViewDataBinding.requestOneClickRecyclerview.visibility =
                                            View.VISIBLE
                                        mViewDataBinding.requestListTitle2.visibility = View.VISIBLE
                                        receive_onCLick_Adapter =
                                            ReceiveOneClickRecyclerViewAdapter(
                                                receiveOneClickList,
                                                this,
                                                5
                                            )
                                        mViewDataBinding.requestOneClickRecyclerview.layoutManager =
                                            LinearLayoutManager(
                                                this,
                                                LinearLayoutManager.VERTICAL, false
                                            )
                                        mViewDataBinding.requestOneClickRecyclerview.adapter =
                                            receive_onCLick_Adapter

                                        receive_onCLick_Adapter.setItemClickListener(object : ReceiveOneClickRecyclerViewAdapter.OnItemClickListener{
                                            override fun onClick(
                                                v: View,
                                                position: Int,
                                                item: receiveoneclick
                                            ) {
                                                val newIntent =
                                                    Intent(
                                                        this@RequestListActivity,
                                                        RefundOneClickActivity::class.java
                                                    )
                                                newIntent.putExtra("type",0)
                                                newIntent.putExtra("reserve_idx", item.reserve_idx)
                                                startActivityForResult(newIntent, 3000)
                                            }

                                        })

                                    }

                                    if (sendOneList.size == 0 && receiveOneClickList.size == 0) {
                                        mViewDataBinding.requestListLayout.visibility = View.GONE
                                        if (!not_list_check) {
                                            mViewDataBinding.requestNotListLayout.visibility =
                                                View.VISIBLE
                                            not_list_check = true
                                        }
                                        notlisttitle()
                                    }

                                }
                            }
                        })

                }
            }

            R.id.request_list_ca_6 -> {
                if (post_ca != 6) {
                    poastchange(post_ca)
                    mViewDataBinding.requestListCa6.setBackgroundResource(R.drawable.request_ca_round_on)
                    mViewDataBinding.requestListBac.setBackgroundResource(R.drawable.mypage_request_list_bac_2)
                    mViewDataBinding.requestListTitle1.text = "취소완료 (1:1)"
                    mViewDataBinding.requestListTitle2.text = "취소완료 (원클릭)"
                    post_ca = 6
                    list_type = 6

                    requestManager.instance.refundOkList(
                        userIdx,
                        completion = { responseStatus, arrayList ->
                            when (responseStatus) {
                                RESPONSE_STATUS.OKAY -> {
                                    sendOneList = arrayList[0].reserve
                                    receiveOneClickList = arrayList[0].oneClick

                                    mViewDataBinding.requestListCa0.text =
                                        arrayList[0].request.toString()
                                    mViewDataBinding.requestListCa1.text =
                                        arrayList[0].response.toString()
                                    mViewDataBinding.requestListCa2.text =
                                        arrayList[0].progress.toString()
                                    mViewDataBinding.requestListCa3.text =
                                        arrayList[0].complete.toString()
                                    mViewDataBinding.requestListCa4.text =
                                        arrayList[0].review.toString()
                                    mViewDataBinding.requestListCa5.text =
                                        arrayList[0].refund_request.toString()
                                    mViewDataBinding.requestListCa6.text =
                                        arrayList[0].refund_complete.toString()

                                    if (sendOneList.size != 0) {
                                        if (not_list_check) {
                                            mViewDataBinding.requestNotListLayout.visibility =
                                                View.GONE
                                            mViewDataBinding.requestListLayout.visibility =
                                                View.VISIBLE
                                            not_list_check = false
                                        }
                                        mViewDataBinding.requestListTitle1.visibility = View.VISIBLE
                                        mViewDataBinding.requestOneRecyclerview.visibility =
                                            View.VISIBLE
                                        send_one_Adapter =
                                            SendOneRecyclerViewAdapter(sendOneList, this, 6)
                                        mViewDataBinding.requestOneRecyclerview.layoutManager =
                                            LinearLayoutManager(
                                                this,
                                                LinearLayoutManager.VERTICAL, false
                                            )
                                        mViewDataBinding.requestOneRecyclerview.adapter =
                                            send_one_Adapter

                                        send_one_Adapter.setItemClickListener(object :
                                            SendOneRecyclerViewAdapter.OnItemClickListener {
                                            override fun onClick(
                                                v: View,
                                                position: Int,
                                                item: sendone
                                            ) {
                                                when (item.expert_type) {
                                                    0 -> {
                                                        val newIntent = Intent(
                                                            this@RequestListActivity,
                                                            RefundStudioActivity::class.java
                                                        )
                                                        newIntent.putExtra(
                                                            "reserve_idx",
                                                            item.reserve_idx
                                                        )
                                                        newIntent.putExtra("type",1)
                                                        newIntent.putExtra("chatbool", 0)
                                                        startActivityForResult(newIntent, 2000)
                                                    }
                                                    1 -> {
                                                        val newIntent = Intent(
                                                            this@RequestListActivity,
                                                            RefundPhotoActivity::class.java
                                                        )
                                                        newIntent.putExtra(
                                                            "reserve_idx",
                                                            item.reserve_idx
                                                        )
                                                        newIntent.putExtra("type",1)
                                                        newIntent.putExtra("chatbool", 0)
                                                        startActivityForResult(newIntent, 2000)
                                                    }
                                                    2 -> {
                                                        val newIntent = Intent(
                                                            this@RequestListActivity,
                                                            RefundModelActivity::class.java
                                                        )
                                                        newIntent.putExtra(
                                                            "reserve_idx",
                                                            item.reserve_idx
                                                        )
                                                        newIntent.putExtra("type",1)
                                                        newIntent.putExtra("chatbool", 0)
                                                        startActivityForResult(newIntent, 2000)
                                                    }
                                                    3 -> {
                                                        val newIntent = Intent(
                                                            this@RequestListActivity,
                                                            RefundTripActivity::class.java
                                                        )
                                                        newIntent.putExtra(
                                                            "reserve_idx",
                                                            item.reserve_idx
                                                        )
                                                        newIntent.putExtra("type",1)
                                                        newIntent.putExtra("chatbool", 0)
                                                        startActivityForResult(newIntent, 2000)
                                                    }
                                                }
                                            }

                                        })
                                    }

                                    if (receiveOneClickList.size != 0) {
                                        if (not_list_check) {
                                            mViewDataBinding.requestNotListLayout.visibility =
                                                View.GONE
                                            mViewDataBinding.requestListLayout.visibility =
                                                View.VISIBLE
                                            not_list_check = false
                                        }
                                        mViewDataBinding.requestOneClickRecyclerview.visibility =
                                            View.VISIBLE
                                        mViewDataBinding.requestListTitle2.visibility = View.VISIBLE
                                        receive_onCLick_Adapter =
                                            ReceiveOneClickRecyclerViewAdapter(
                                                receiveOneClickList,
                                                this,

                                                6
                                            )
                                        mViewDataBinding.requestOneClickRecyclerview.layoutManager =
                                            LinearLayoutManager(
                                                this,
                                                LinearLayoutManager.VERTICAL, false
                                            )
                                        mViewDataBinding.requestOneClickRecyclerview.adapter =
                                            receive_onCLick_Adapter

                                        receive_onCLick_Adapter.setItemClickListener(object : ReceiveOneClickRecyclerViewAdapter.OnItemClickListener{
                                            override fun onClick(
                                                v: View,
                                                position: Int,
                                                item: receiveoneclick
                                            ) {
                                                val newIntent =
                                                    Intent(
                                                        this@RequestListActivity,
                                                        RefundOneClickActivity::class.java
                                                    )
                                                newIntent.putExtra("type",1)
                                                newIntent.putExtra("reserve_idx", item.reserve_idx)
                                                startActivityForResult(newIntent, 3000)
                                            }

                                        })

                                    }

                                    if (sendOneList.size == 0 && receiveOneClickList.size == 0) {
                                        mViewDataBinding.requestListLayout.visibility = View.GONE
                                        if (!not_list_check) {
                                            mViewDataBinding.requestNotListLayout.visibility =
                                                View.VISIBLE
                                            not_list_check = true
                                        }
                                        notlisttitle()
                                    }


                                }
                            }
                        })

                }
            }
        }
    }
}