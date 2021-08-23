package com.weare.wearecompany.ui.bottommenu.estimate.receive

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.bottomnav.estimate.receive.ReceiveAllDate
import com.weare.wearecompany.data.bottomnav.estimate.receive.ReceiveList
import com.weare.wearecompany.data.bottomnav.estimate.receive.ReceiveResponse
import com.weare.wearecompany.data.bottomnav.estimate.send.SendAllDate
import com.weare.wearecompany.data.bottomnav.estimate.send.SendList
import com.weare.wearecompany.data.retrofit.bottomnav.estimate.requestManager
import com.weare.wearecompany.databinding.FragmentReceiveBinding
import com.weare.wearecompany.ui.base.BaseFragment
import com.weare.wearecompany.ui.bottommenu.estimate.expetrFragment
import com.weare.wearecompany.ui.bottommenu.estimate.progress.ProgressFragment
import com.weare.wearecompany.ui.bottommenu.estimate.receive.experthodel.ReceiveModelActivity
import com.weare.wearecompany.ui.bottommenu.estimate.receive.experthodel.ReceivePhotoActivity
import com.weare.wearecompany.ui.bottommenu.estimate.receive.experthodel.ReceiveStudioActivity
import com.weare.wearecompany.ui.bottommenu.estimate.receive.experthodel.ReceiveTripActivity
import com.weare.wearecompany.ui.bottommenu.estimate.send.SendExpertActivity
import com.weare.wearecompany.ui.bottommenu.estimate.send.SendRequestActivity
import com.weare.wearecompany.ui.bottommenu.estimate.send.SendShopActivity
import com.weare.wearecompany.ui.bottommenu.estimate.send.SendStudioActivity
import com.weare.wearecompany.utils.ESTIMATE

class ReceiveFragment : BaseFragment<FragmentReceiveBinding>(
    R.layout.fragment_receive
), SwipeRefreshLayout.OnRefreshListener {

    lateinit var user_idx: String
    private lateinit var dataAdapter: ReceiveRecyclerViewAdapter
    lateinit var dataList: List<ReceiveResponse>
    lateinit var alldataList: ArrayList<ReceiveAllDate>
    private var reservecount: Int = 0
    private var requestcount: Int = 0
    private var experttype = -1

    private var ProgressFragment = ProgressFragment()

    override fun onCreate() {

        user_idx = MyApplication.prefs.getString("user_idx", "")

        mViewDataBinding.swipe.setOnRefreshListener(this)
        setup()

    }

    private fun setup() {

        if (user_idx != "") {
            requestManager.instance.receive_list(user_idx,
                completion = { responseStatus, arraylist ->
                    when (responseStatus) {
                        ESTIMATE.OKAY -> {

                            datawork(arraylist)

                            if (alldataList.size != 0) {
                                mViewDataBinding.receiveNotDataLayout.visibility = View.GONE

                                dataAdapter =
                                    context?.let { ReceiveRecyclerViewAdapter(it,reservecount,requestcount, alldataList) }!!
                                mViewDataBinding.receiveRecyclerview.layoutManager =
                                    LinearLayoutManager(
                                        context,
                                        LinearLayoutManager.VERTICAL, false
                                    )
                                mViewDataBinding.receiveRecyclerview.adapter = dataAdapter

                                dataAdapter.setItemClickListener(object : ReceiveRecyclerViewAdapter.OnItemClickListener {
                                    override fun onClick(
                                        v: View,
                                        position: Int,
                                        item: ReceiveAllDate
                                    ) {
                                        when(item.type) {
                                            0 -> {
                                                val newIntent = Intent(context, ReceiveStudioActivity::class.java)
                                                newIntent.putExtra("reserve_idx", item.reserve_idx)
                                                newIntent.putExtra("type", 1)
                                                startActivityForResult(newIntent, 2000)
                                            }
                                            1 -> {
                                                val newIntent = Intent(context, ReceivePhotoActivity::class.java)
                                                newIntent.putExtra("reserve_idx", item.reserve_idx)
                                                newIntent.putExtra("type", 1)
                                                startActivityForResult(newIntent, 2000)
                                            }
                                            2 -> {
                                                val newIntent = Intent(context, ReceiveModelActivity::class.java)
                                                newIntent.putExtra("reserve_idx", item.reserve_idx)
                                                newIntent.putExtra("type", 1)
                                                startActivityForResult(newIntent, 2000)
                                            }
                                            3 -> {
                                                val newIntent = Intent(context, ReceiveTripActivity::class.java)
                                                newIntent.putExtra("reserve_idx", item.reserve_idx)
                                                newIntent.putExtra("type", 1)
                                                startActivityForResult(newIntent, 2000)
                                            }
                                            5 -> {
                                                val newIntent = Intent(context, ReceiveRequestActivity::class.java)
                                                newIntent.putExtra("request_idx",item.request_idx)
                                                newIntent.putExtra("log_idx",item.request_log_idx)
                                                startActivityForResult(newIntent, 2000)
                                            }
                                        }
                                    }
                                })
                            } else {
                                mViewDataBinding.receiveRecyclerview.visibility = View.GONE
                                mViewDataBinding.receiveNotDataLayout.visibility = View.VISIBLE
                            }
                        }
                    }
                })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(resultCode) {
            5001 -> {
                setup()
                Toast.makeText(context,"결제가 완료되었습니다. 진행현황에서 확인해보세요",Toast.LENGTH_SHORT).show()
            }
            6000 -> {
                setup()
                Toast.makeText(context,"예약이 취소되었습니다.",Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun datawork(list: ArrayList<ReceiveList>) {
        alldataList = ArrayList<ReceiveAllDate>()
        reservecount = 0
        if (list[0].reserve?.size != 0) {

            val alldataItem = ReceiveAllDate(
                type = -1,
                reserve_idx = "",
                request_idx = "",
                request_log_idx = "",
                room_name = "",
                head_count = "",
                expert_type = null,
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
                if (list[0].reserve?.get(i-1)!!.studio != null) {
                    val alldataItem = ReceiveAllDate(
                        type = 0,
                        reserve_idx = list[0].reserve?.get(i-1)!!.studio.reserve_idx,
                        request_idx = "",
                        request_log_idx = "",
                        room_name = list[0].reserve?.get(i-1)!!.studio.room_name,
                        head_count = list[0].reserve?.get(i-1)!!.studio.head_count,
                        expert_type = null,
                        price = list[0].reserve?.get(i-1)!!.studio.price,
                        time = list[0].reserve?.get(i-1)!!.studio.time,
                        date = list[0].reserve?.get(i-1)!!.studio.date,
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
                } else if (list[0].reserve?.get(i-1)!!.expert != null) {
                    if (list[0].reserve?.get(i-1)!!.expert.expert_type == 0) {
                        experttype = 1  //포토
                    } else if (list[0].reserve?.get(i-1)!!.expert.expert_type == 1) {
                        experttype = 2  //모델
                    } else if (list[0].reserve?.get(i-1)!!.expert.expert_type == 2) {
                        experttype = 3  //뷰티
                    }
                    val alldataItem = ReceiveAllDate(
                        type = experttype,
                        reserve_idx = list[0].reserve?.get(i-1)!!.expert.reserve_idx,
                        request_idx = "",
                        request_log_idx = "",
                        room_name = "",
                        head_count = list[0].reserve?.get(i-1)!!.expert.head_count,
                        expert_type = list[0].reserve?.get(i-1)!!.expert.expert_type,
                        price = list[0].reserve?.get(i-1)!!.expert.price,
                        time = list[0].reserve?.get(i-1)!!.expert.time,
                        date = list[0].reserve?.get(i-1)!!.expert.date,
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

                } else if (list[0].reserve?.get(i-1)!!.shop != null) {
                    val alldataItem = ReceiveAllDate(
                        type = 4,
                        reserve_idx = list[0].reserve?.get(i-1)!!.shop.reserve_idx,
                        request_idx = "",
                        request_log_idx = "",
                        room_name = "",
                        head_count = "",
                        expert_type = null,
                        price = list[0].reserve?.get(i-1)!!.shop.price,
                        time = "",
                        date = "",
                        date_term = list[0].reserve?.get(i-1)!!.shop.date_term,
                        start_date = list[0].reserve?.get(i-1)!!.shop.start_date,
                        end_date = list[0].reserve?.get(i-1)!!.shop.end_date,
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
            val alldataItem = ReceiveAllDate(
                type = -2,
                reserve_idx = "",
                request_idx = "",
                request_log_idx = "",
                room_name = "",
                head_count = "",
                expert_type = null,
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
                val alldataItem = ReceiveAllDate(
                    type = 5,
                    reserve_idx = "",
                    request_idx = list[0].request?.get(i-1)!!.request_idx,
                    request_log_idx = list[0].request?.get(i-1)!!.request_log_idx,
                    room_name = "",
                    head_count = "",
                    expert_type = null,
                    price = list[0].request?.get(i-1)!!.price,
                    time = "",
                    date = "",
                    date_term = "",
                    start_date = "",
                    end_date = "",
                    main_category = list[0].request?.get(i-1)!!.main_category,
                    sub_category = list[0].request?.get(i-1)!!.sub_category,
                    user_nickname = list[0].request?.get(i-1)!!.user_nickname,
                    user_thumbnail = list[0].request?.get(i-1)!!.user_thumbnail,
                    datetime = list[0].request?.get(i-1)!!.datetime,
                    thumbnail = list[0].request?.get(i-1)!!.thumbnail,
                )
                requestcount += 1
                alldataList.add(alldataItem)
            }
        }
    }

    override fun onRefresh() {
        setup()
        mViewDataBinding.swipe.isRefreshing = false
    }
}