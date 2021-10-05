package com.weare.wearecompany.ui.bottommenu.estimate.send


import android.content.Intent
import android.view.View
import android.widget.Toast

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.bottomnav.estimate.send.SendAllDate
import com.weare.wearecompany.data.bottomnav.estimate.send.SendList
import com.weare.wearecompany.data.bottomnav.estimate.send.SendRequest
import com.weare.wearecompany.data.retrofit.bottomnav.estimate.requestManager
import com.weare.wearecompany.databinding.FragmentSendBinding
import com.weare.wearecompany.ui.base.BaseFragment
import com.weare.wearecompany.ui.bottommenu.estimate.send.experthodel.SendModelActivity
import com.weare.wearecompany.ui.bottommenu.estimate.send.experthodel.SendPhotoActivity
import com.weare.wearecompany.ui.bottommenu.estimate.send.experthodel.SendTripActivity

import com.weare.wearecompany.utils.RESPONSE_STATUS

class SendFragment : BaseFragment<FragmentSendBinding>(
    R.layout.fragment_send
), SwipeRefreshLayout.OnRefreshListener {

    lateinit var user_idx: String
    private lateinit var dataAdapter: SendRecyclerViewAdapter
    lateinit var dataList: SendList
    lateinit var alldataList: ArrayList<SendAllDate>
    private var reservecount: Int = 0
    private var requestcount: Int = 0
    private var headerCount: Int = 0
    private var experttype = -1

    override fun onCreate() {

        user_idx = MyApplication.prefs.getString("user_idx", "")

        setup()

    }

    private fun setup() {
        mViewDataBinding.swipe.setOnRefreshListener(this)
        if (user_idx != "") {
            requestManager.instance.send_list(user_idx,
                completion = { responseStatus, arraylist ->

                    when (responseStatus) {
                        RESPONSE_STATUS.OKAY -> {
                            datawork(arraylist)

                            if (alldataList.size != 0) {
                                mViewDataBinding.sendNotDataLayout.visibility = View.GONE
                                dataAdapter = context?.let {
                                    SendRecyclerViewAdapter(
                                        it,
                                        reservecount,
                                        requestcount,
                                        alldataList
                                    )
                                }!!
                                mViewDataBinding.sendRecyclerview.layoutManager =
                                    LinearLayoutManager(
                                        context,
                                        LinearLayoutManager.VERTICAL, false
                                    )
                                mViewDataBinding.sendRecyclerview.adapter = dataAdapter

                                dataAdapter.setItemClickListener(object :
                                    SendRecyclerViewAdapter.OnItemClickListener {
                                    override fun onClick(
                                        v: View,
                                        position: Int,
                                        item: SendAllDate
                                    ) {
                                        when (item.type) {
                                            0 -> {
                                                val newIntent =
                                                    Intent(context, SendStudioActivity::class.java)
                                                newIntent.putExtra("reserve_idx", item.reserve_idx)
                                                newIntent.putExtra("type", 0)
                                                startActivityForResult(newIntent, 2000)
                                            }
                                            1 -> {
                                                val newIntent =
                                                    Intent(context, SendPhotoActivity::class.java)
                                                newIntent.putExtra("reserve_idx", item.reserve_idx)
                                                newIntent.putExtra("type", 0)
                                                startActivityForResult(newIntent, 2000)
                                            }
                                            2 -> {
                                                val newIntent =
                                                    Intent(context, SendModelActivity::class.java)
                                                newIntent.putExtra("reserve_idx", item.reserve_idx)
                                                newIntent.putExtra("type", 0)
                                                startActivityForResult(newIntent, 2000)
                                            }
                                            3 -> {
                                                val newIntent =
                                                    Intent(context, SendTripActivity::class.java)
                                                newIntent.putExtra("reserve_idx", item.reserve_idx)
                                                newIntent.putExtra("type", 0)
                                                startActivityForResult(newIntent, 2000)
                                            }
                                        }
                                    }
                                })
                            } else {
                                mViewDataBinding.sendRecyclerview.visibility = View.GONE
                                mViewDataBinding.sendNotDataLayout.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            )
        }

    }

    private fun datawork(list: ArrayList<SendList>) {
        alldataList = ArrayList<SendAllDate>()
        reservecount = 0
        if (list[0].reserve?.size != 0) {
            headerCount += 1
            val alldataItem = SendAllDate(
                type = -1,
                reserve_idx = "",
                request_idx = "",
                send_time = "",
                room_name = "",
                head_count = "",
                expert_type = null,
                date_status = null,
                price = null,
                time = "",
                date = "",
                date_term = "",
                start_date = "",
                end_date = "",
                main_category = "",
                sub_category = "",
                user_nickname = "",
                user_thumbnail = "",
                datetime = "",
                thumbnail = null,
            )
            alldataList.add(alldataItem)
            for (i in 1..list[0].reserve!!.size) {
                if (list[0].reserve?.get(i - 1)!!.studio != null) {
                    val alldataItem = SendAllDate(
                        type = 0,
                        reserve_idx = list[0].reserve?.get(i - 1)!!.studio.reserve_idx,
                        request_idx = "",
                        send_time = list[0].reserve?.get(i - 1)!!.studio.send_time,
                        room_name = list[0].reserve?.get(i - 1)!!.studio.room_name,
                        head_count = list[0].reserve?.get(i - 1)!!.studio.head_count,
                        expert_type = null,
                        date_status = list[0].reserve?.get(i - 1)!!.studio.date_status,
                        price = list[0].reserve?.get(i - 1)!!.studio.price,
                        time = list[0].reserve?.get(i - 1)!!.studio.time,
                        date = list[0].reserve?.get(i - 1)!!.studio.date,
                        date_term = "",
                        start_date = "",
                        end_date = "",
                        main_category = "",
                        sub_category = "",
                        user_nickname = "",
                        user_thumbnail = "",
                        datetime = "",
                        thumbnail = null,
                    )
                    reservecount += 1
                    alldataList.add(alldataItem)
                } else if (list[0].reserve?.get(i - 1)!!.expert != null) {
                    if (list[0].reserve?.get(i - 1)!!.expert.expert_type == 0) {
                        experttype = 1
                    } else if (list[0].reserve?.get(i - 1)!!.expert.expert_type == 1) {
                        experttype = 2
                    } else if (list[0].reserve?.get(i - 1)!!.expert.expert_type == 2) {
                        experttype = 3
                    }
                    val alldataItem = SendAllDate(
                        type = experttype,
                        reserve_idx = list[0].reserve?.get(i - 1)!!.expert.reserve_idx,
                        request_idx = "",
                        send_time = list[0].reserve?.get(i - 1)!!.expert.send_time,
                        room_name = "",
                        head_count = list[0].reserve?.get(i - 1)!!.expert.head_count,
                        expert_type = list[0].reserve?.get(i - 1)!!.expert.expert_type,
                        date_status = list[0].reserve?.get(i - 1)!!.expert.date_status,
                        price = list[0].reserve?.get(i - 1)!!.expert.price,
                        time = list[0].reserve?.get(i - 1)!!.expert.time,
                        date = list[0].reserve?.get(i - 1)!!.expert.date,
                        date_term = "",
                        start_date = "",
                        end_date = "",
                        main_category = "",
                        sub_category = "",
                        user_nickname = "",
                        user_thumbnail = "",
                        datetime = "",
                        thumbnail = null,
                    )
                    reservecount += 1
                    alldataList.add(alldataItem)

                }
            }
        }

        if (list[0].request?.size != 0) {
            requestcount = 0
            headerCount += 1
            val alldataItem = SendAllDate(
                type = -2,
                reserve_idx = "",
                request_idx = "",
                send_time = "",
                room_name = "",
                head_count = "",
                expert_type = null,
                date_status = null,
                price = null,
                time = "",
                date = "",
                date_term = "",
                start_date = "",
                end_date = "",
                main_category = "",
                sub_category = "",
                user_nickname = "",
                user_thumbnail = "",
                datetime = "",
                thumbnail = null,
            )
            alldataList.add(alldataItem)
            for (i in 1..list[0].request!!.size) {
                val alldataItem = SendAllDate(
                    type = 5,
                    reserve_idx = "",
                    request_idx = list[0].request?.get(i - 1)!!.request_idx,
                    send_time = list[0].request?.get(i - 1)!!.send_time,
                    room_name = "",
                    head_count = "",
                    expert_type = null,
                    date_status = null,
                    price = null,
                    time = "",
                    date = "",
                    date_term = "",
                    start_date = "",
                    end_date = "",
                    main_category = list[0].request?.get(i - 1)!!.main_category,
                    sub_category = list[0].request?.get(i - 1)!!.sub_category,
                    user_nickname = list[0].request?.get(i - 1)!!.user_nickname,
                    user_thumbnail = list[0].request?.get(i - 1)!!.user_thumbnail,
                    datetime = list[0].request?.get(i - 1)!!.datetime,
                    thumbnail = list[0].request?.get(i - 1)!!.thumbnail,
                )
                requestcount += 1
                alldataList.add(alldataItem)
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            6000 -> {
                setup()
                Toast.makeText(context, "예약이 취소되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onRefresh() {
        setup()
        mViewDataBinding.swipe.isRefreshing = false
    }
}