package com.weare.wearecompany.ui.bottommenu.estimate.progress

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.bottomnav.estimate.progress.ProgressAllDate
import com.weare.wearecompany.data.bottomnav.estimate.progress.ProgressList
import com.weare.wearecompany.data.bottomnav.estimate.receive.ReceiveResponse
import com.weare.wearecompany.data.retrofit.bottomnav.estimate.progressManager
import com.weare.wearecompany.databinding.FragmentProgressBinding
import com.weare.wearecompany.ui.base.BaseFragment
import com.weare.wearecompany.ui.bottommenu.estimate.progress.experthodel.progress.*
import com.weare.wearecompany.ui.bottommenu.estimate.progress.experthodel.refund.*
import com.weare.wearecompany.ui.bottommenu.estimate.progress.experthodel.review.ReviewActivity
import com.weare.wearecompany.ui.container.ContainerActivity
import com.weare.wearecompany.utils.ESTIMATE

class ProgressFragment : BaseFragment<FragmentProgressBinding>(
    R.layout.fragment_progress
), View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    lateinit var user_idx: String

    private lateinit var dataAdapter: ProgressRecyclerViewAdapter
    private lateinit var refundAdapter: ProgressRefundRecyclerViewAdapter
    private lateinit var reviewAdapter: ProgressReviewRecyclerViewAdapter
    lateinit var dataList: List<ReceiveResponse>
    private var on_progress_true: Int = 0
    private var on_progress_false: Int = 0
    private var refund_true: Int = 0
    private var refund_false: Int = 0
    private var review_true: Int = 0
    private var review_false: Int = 0
    private var listType: Int = -1
    lateinit var dateList: ArrayList<ProgressList>

    private var refreshCheck: Int = -1

    lateinit var mContext: Context

    lateinit var ongoingList: ArrayList<ProgressAllDate>
    lateinit var reviewList: ArrayList<ProgressAllDate>
    lateinit var refundList: ArrayList<ProgressAllDate>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ContainerActivity) {
            mContext = context
        }
    }

    override fun onCreate() {

        user_idx = MyApplication.prefs.getString("user_idx", "")

        setup()

        mViewDataBinding.progressList.setOnClickListener(this)
        mViewDataBinding.refundList.setOnClickListener(this)
        mViewDataBinding.reviewList.setOnClickListener(this)
        mViewDataBinding.swipe.setOnRefreshListener(this)
    }

    private fun setup() {

        progress_on(mViewDataBinding.progressList, mViewDataBinding.progressListText, 0)

        if (user_idx != "") {
            progressManager.instance.progress_list(0, 1, user_idx,
                completion = { responseStatus, arraylist ->
                    when (responseStatus) {
                        ESTIMATE.OKAY -> {
                            dateList = arraylist
                            ongoing(arraylist)
                            refreshCheck = 0
                            if (ongoingList.size != 0) {
                                mViewDataBinding.progressNotDataLayout.visibility = View.GONE
                                mViewDataBinding.progressRecyclerview.visibility = View.VISIBLE
                                dataAdapter =
                                    context?.let {
                                        ProgressRecyclerViewAdapter(
                                            it,
                                            on_progress_true,
                                            on_progress_false,
                                            ongoingList
                                        )
                                    }!!
                                mViewDataBinding.progressRecyclerview.layoutManager =
                                    LinearLayoutManager(
                                        context,
                                        LinearLayoutManager.VERTICAL, false
                                    )
                                mViewDataBinding.progressRecyclerview.adapter = dataAdapter

                                dataAdapter.setItemClickListener(object :
                                    ProgressRecyclerViewAdapter.OnItemClickListener {
                                    override fun onClick(
                                        v: View,
                                        position: Int,
                                        item: ProgressAllDate
                                    ) {
                                        when (item.type) {
                                            0 -> {
                                                val newIntent = Intent(
                                                    context,
                                                    ProgressStudioActivity::class.java
                                                )
                                                newIntent.putExtra("reserve_idx", item.reserve_idx)
                                                newIntent.putExtra("chatbool", 0)
                                                startActivityForResult(newIntent, 2000)
                                            }
                                            1 -> {
                                                val newIntent = Intent(
                                                    context,
                                                    ProgressPhotoActivity::class.java
                                                )
                                                newIntent.putExtra("reserve_idx", item.reserve_idx)
                                                newIntent.putExtra("chatbool", 0)
                                                startActivityForResult(newIntent, 2000)
                                            }
                                            2 -> {
                                                val newIntent = Intent(
                                                    context,
                                                    ProgressModelActivity::class.java
                                                )
                                                newIntent.putExtra("reserve_idx", item.reserve_idx)
                                                newIntent.putExtra("chatbool", 0)
                                                startActivityForResult(newIntent, 2000)
                                            }
                                            3 -> {
                                                val newIntent = Intent(
                                                    context,
                                                    ProgressTripActivity::class.java
                                                )
                                                newIntent.putExtra("reserve_idx", item.reserve_idx)
                                                newIntent.putExtra("chatbool", 0)
                                                startActivityForResult(newIntent, 2000)
                                            }
                                        }
                                    }
                                })

                                dataAdapter.setItemClickListener(object :
                                    ProgressRecyclerViewAdapter.OncompleteClickListener {
                                    override fun onClick(
                                        v: View,
                                        position: Int,
                                        item: ProgressAllDate
                                    ) {

                                    }

                                })
                            } else {
                                mViewDataBinding.progressRecyclerview.visibility = View.GONE
                                mViewDataBinding.progressNotDataLayout.visibility = View.VISIBLE
                            }


                        }
                    }
                })
        }
    }

    private fun progress_on(layout: LinearLayout, text: TextView, clicknum: Int) {
        listType = 0
        layout.setBackgroundResource(R.drawable.progress_tab_on)
        text.setTextColor(Color.parseColor("#ffffff"))
    }

    private fun progress_off(layout: LinearLayout?, text: TextView?) {
        layout?.setBackgroundResource(R.drawable.progress_tab_off)
        text?.setTextColor(Color.parseColor("#dedede"))
    }

    private fun refund_on(layout: LinearLayout, text: TextView, clicknum: Int) {
        listType = 1
        layout.setBackgroundResource(R.drawable.progress_tab_on)
        text.setTextColor(Color.parseColor("#ffffff"))
    }

    private fun refund_off(layout: LinearLayout?, text: TextView?) {
        layout?.setBackgroundResource(R.drawable.progress_tab_off)
        text?.setTextColor(Color.parseColor("#dedede"))
    }

    private fun review_on(layout: LinearLayout, text: TextView, clicknum: Int) {
        listType = 2
        layout.setBackgroundResource(R.drawable.progress_tab_on)
        text.setTextColor(Color.parseColor("#ffffff"))
    }

    private fun review_off(layout: LinearLayout?, text: TextView?) {
        layout?.setBackgroundResource(R.drawable.progress_tab_off)
        text?.setTextColor(Color.parseColor("#dedede"))
    }

    private fun ongoing(list: ArrayList<ProgressList>) {
        ongoingList = ArrayList<ProgressAllDate>()
        on_progress_false = 0
        on_progress_true = 0
        if (list[0].progress?.size != 0) {
            for (i in 1..list[0].progress!!.size) {
                if (list[0].progress?.get(i - 1)!!.studio != null) {
                    if (list[0].progress?.get(i - 1)!!.studio.on_progress == false) {
                        if (on_progress_true == 0) {
                            val ongoingItem = ProgressAllDate(
                                type = -1,
                                reserve_idx = "",
                                expert_idx = "",
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
                                sub_category = "",
                                user_nickname = "",
                                user_thumbnail = "",
                                datetime = "",
                                thumbnail = null,
                                view_status = false,
                                review_status = false,
                                on_progress = false,
                                refund_status = false
                            )
                            ongoingList.add(ongoingItem)
                        }
                        val alldataItem = ProgressAllDate(
                            type = 0,
                            reserve_idx = list[0].progress?.get(i - 1)!!.studio.reserve_idx,
                            expert_idx = list[0].progress?.get(i - 1)!!.studio.expert_idx,
                            request_idx = "",
                            request_log_idx = "",
                            room_name = list[0].progress?.get(i - 1)!!.studio.room_name,
                            head_count = list[0].progress?.get(i - 1)!!.studio.head_count,
                            expert_type = list[0].progress?.get(i - 1)!!.studio.expert_type,
                            price = list[0].progress?.get(i - 1)!!.studio.price,
                            time = list[0].progress?.get(i - 1)!!.studio.time,
                            date = list[0].progress?.get(i - 1)!!.studio.date,
                            date_term = "",
                            start_date = "",
                            end_date = "",
                            sub_category = "",
                            user_nickname = "",
                            user_thumbnail = "",
                            datetime = "",
                            thumbnail = null,
                            view_status = list[0].progress?.get(i - 1)!!.studio.view_status,
                            review_status = list[0].progress?.get(i - 1)!!.studio.review_status,
                            on_progress = list[0].progress?.get(i - 1)!!.studio.on_progress,
                            refund_status = list[0].progress?.get(i - 1)!!.studio.refund_status
                        )
                        on_progress_true += 1
                        ongoingList.add(alldataItem)
                    }
                } else if (list[0].progress?.get(i - 1)!!.request != null) {
                    if (list[0].progress?.get(i - 1)!!.request.on_progress == false) {
                        if (on_progress_true == 0) {
                            val ongoingItem = ProgressAllDate(
                                type = -1,
                                reserve_idx = "",
                                expert_idx = "",
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
                                sub_category = "",
                                user_nickname = "",
                                user_thumbnail = "",
                                datetime = "",
                                thumbnail = null,
                                view_status = false,
                                review_status = false,
                                on_progress = false,
                                refund_status = false
                            )
                            ongoingList.add(ongoingItem)
                        }
                        val alldataItem = ProgressAllDate(
                            type = 5,
                            reserve_idx = "",
                            expert_idx = list[0].progress?.get(i - 1)!!.request.expert_idx,
                            request_idx = list[0].progress?.get(i - 1)!!.request.request_idx,
                            request_log_idx = list[0].progress?.get(i - 1)!!.request.request_log_idx,
                            room_name = "",
                            head_count = "",
                            expert_type = list[0].progress?.get(i - 1)!!.request.expert_type,
                            price = list[0].progress?.get(i - 1)!!.request.price,
                            time = "",
                            date = "",
                            date_term = "",
                            start_date = "",
                            end_date = "",
                            sub_category = list[0].progress?.get(i - 1)!!.request.sub_category,
                            user_nickname = list[0].progress?.get(i - 1)!!.request.user_nickname,
                            user_thumbnail = list[0].progress?.get(i - 1)!!.request.user_thumbnail,
                            datetime = list[0].progress?.get(i - 1)!!.request.datetime,
                            thumbnail = list[0].progress?.get(i - 1)!!.request.thumbnail,
                            view_status = list[0].progress?.get(i - 1)!!.request.view_status,
                            review_status = list[0].progress?.get(i - 1)!!.request.review_status,
                            on_progress = list[0].progress?.get(i - 1)!!.request.on_progress,
                            refund_status = list[0].progress?.get(i - 1)!!.request.refund_status
                        )
                        on_progress_true += 1
                        ongoingList.add(alldataItem)
                    }

                }
            }
        }
        if (list[0].progress?.size != 0) {
            for (i in 1..list[0].progress!!.size) {
                if (list[0].progress?.get(i - 1)!!.studio != null) {
                    if (list[0].progress?.get(i - 1)!!.studio.on_progress == true) {
                        if (on_progress_false == 0) {
                            val ongoingItem = ProgressAllDate(
                                type = -2,
                                reserve_idx = "",
                                expert_idx = "",
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
                                sub_category = "",
                                user_nickname = "",
                                user_thumbnail = "",
                                datetime = "",
                                thumbnail = null,
                                view_status = false,
                                review_status = false,
                                on_progress = false,
                                refund_status = false
                            )
                            ongoingList.add(ongoingItem)
                        }
                        val alldataItem = ProgressAllDate(
                            type = 0,
                            reserve_idx = list[0].progress?.get(i - 1)!!.studio.reserve_idx,
                            expert_idx = list[0].progress?.get(i - 1)!!.studio.expert_idx,
                            request_idx = "",
                            request_log_idx = "",
                            room_name = list[0].progress?.get(i - 1)!!.studio.room_name,
                            head_count = list[0].progress?.get(i - 1)!!.studio.head_count,
                            expert_type = list[0].progress?.get(i - 1)!!.studio.expert_type,
                            price = list[0].progress?.get(i - 1)!!.studio.price,
                            time = list[0].progress?.get(i - 1)!!.studio.time,
                            date = list[0].progress?.get(i - 1)!!.studio.date,
                            date_term = "",
                            start_date = "",
                            end_date = "",
                            sub_category = "",
                            user_nickname = "",
                            user_thumbnail = "",
                            datetime = "",
                            thumbnail = null,
                            view_status = list[0].progress?.get(i - 1)!!.studio.view_status,
                            review_status = list[0].progress?.get(i - 1)!!.studio.review_status,
                            on_progress = list[0].progress?.get(i - 1)!!.studio.on_progress,
                            refund_status = list[0].progress?.get(i - 1)!!.studio.refund_status
                        )
                        on_progress_false += 1
                        ongoingList.add(alldataItem)
                    }
                } else if (list[0].progress?.get(i - 1)!!.expert != null) {
                    if (list[0].progress?.get(i - 1)!!.expert.on_progress == true) {
                        if (on_progress_false == 0) {
                            val ongoingItem = ProgressAllDate(
                                type = -2,
                                reserve_idx = "",
                                expert_idx = "",
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
                                sub_category = "",
                                user_nickname = "",
                                user_thumbnail = "",
                                datetime = "",
                                thumbnail = null,
                                view_status = false,
                                review_status = false,
                                on_progress = false,
                                refund_status = false
                            )
                            ongoingList.add(ongoingItem)
                        }
                        val alldataItem = ProgressAllDate(
                            type = list[0].progress?.get(i - 1)!!.expert.expert_type,
                            reserve_idx = list[0].progress?.get(i - 1)!!.expert.reserve_idx,
                            expert_idx = list[0].progress?.get(i - 1)!!.expert.reserve_idx,
                            request_idx = "",
                            request_log_idx = "",
                            room_name = "",
                            head_count = list[0].progress?.get(i - 1)!!.expert.head_count,
                            expert_type = list[0].progress?.get(i - 1)!!.expert.expert_type,
                            price = list[0].progress?.get(i - 1)!!.expert.price,
                            time = list[0].progress?.get(i - 1)!!.expert.time,
                            date = list[0].progress?.get(i - 1)!!.expert.date,
                            date_term = "",
                            start_date = "",
                            end_date = "",
                            sub_category = "",
                            user_nickname = "",
                            user_thumbnail = "",
                            datetime = "",
                            thumbnail = null,
                            view_status = list[0].progress?.get(i - 1)!!.expert.view_status,
                            review_status = list[0].progress?.get(i - 1)!!.expert.review_status,
                            on_progress = list[0].progress?.get(i - 1)!!.expert.on_progress,
                            refund_status = list[0].progress?.get(i - 1)!!.expert.refund_status
                        )
                        on_progress_false += 1
                        ongoingList.add(alldataItem)
                    }
                } else if (list[0].progress?.get(i - 1)!!.request != null) {
                    if (list[0].progress?.get(i - 1)!!.request.on_progress == true) {
                        if (on_progress_false == 0) {
                            val ongoingItem = ProgressAllDate(
                                type = -2,
                                reserve_idx = "",
                                expert_idx = "",
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
                                sub_category = "",
                                user_nickname = "",
                                user_thumbnail = "",
                                datetime = "",
                                thumbnail = null,
                                view_status = false,
                                review_status = false,
                                on_progress = false,
                                refund_status = false
                            )
                            ongoingList.add(ongoingItem)
                        }
                        val alldataItem = ProgressAllDate(
                            type = 5,
                            reserve_idx = "",
                            expert_idx = list[0].progress?.get(i - 1)!!.request.expert_idx,
                            request_idx = list[0].progress?.get(i - 1)!!.request.request_idx,
                            request_log_idx = list[0].progress?.get(i - 1)!!.request.request_log_idx,
                            room_name = "",
                            head_count = "",
                            expert_type = list[0].progress?.get(i - 1)!!.request.expert_type,
                            price = list[0].progress?.get(i - 1)!!.request.price,
                            time = "",
                            date = "",
                            date_term = "",
                            start_date = "",
                            end_date = "",
                            sub_category = list[0].progress?.get(i - 1)!!.request.sub_category,
                            user_nickname = list[0].progress?.get(i - 1)!!.request.user_nickname,
                            user_thumbnail = list[0].progress?.get(i - 1)!!.request.user_thumbnail,
                            datetime = list[0].progress?.get(i - 1)!!.request.datetime,
                            thumbnail = list[0].progress?.get(i - 1)!!.request.thumbnail,
                            view_status = list[0].progress?.get(i - 1)!!.request.view_status,
                            review_status = list[0].progress?.get(i - 1)!!.request.review_status,
                            on_progress = list[0].progress?.get(i - 1)!!.request.on_progress,
                            refund_status = list[0].progress?.get(i - 1)!!.request.refund_status
                        )
                        on_progress_false += 1
                        ongoingList.add(alldataItem)
                    }

                }
            }
        }
    }

    private fun refunding(list: ArrayList<ProgressList>) {
        refundList = ArrayList<ProgressAllDate>()
        refund_false = 0
        refund_true = 0

        if (list[0].progress?.size != 0) {
            for (i in 1..list[0].progress!!.size) {
                if (list[0].progress?.get(i - 1)!!.studio != null) {
                    if (list[0].progress?.get(i - 1)!!.studio.refund_status == false) {
                        if (refund_false == 0) {
                            val ongoingItem = ProgressAllDate(
                                type = -1,
                                reserve_idx = "",
                                expert_idx = "",
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
                                sub_category = "",
                                user_nickname = "",
                                user_thumbnail = "",
                                datetime = "",
                                thumbnail = null,
                                view_status = false,
                                review_status = false,
                                on_progress = false,
                                refund_status = false
                            )
                            refundList.add(ongoingItem)
                        }
                        val alldataItem = ProgressAllDate(
                            type = 0,
                            reserve_idx = list[0].progress?.get(i - 1)!!.studio.reserve_idx,
                            expert_idx = list[0].progress?.get(i - 1)!!.studio.expert_idx,
                            request_idx = "",
                            request_log_idx = "",
                            room_name = list[0].progress?.get(i - 1)!!.studio.room_name,
                            head_count = list[0].progress?.get(i - 1)!!.studio.head_count,
                            expert_type = list[0].progress?.get(i - 1)!!.studio.expert_type,
                            price = list[0].progress?.get(i - 1)!!.studio.price,
                            time = list[0].progress?.get(i - 1)!!.studio.time,
                            date = list[0].progress?.get(i - 1)!!.studio.date,
                            date_term = "",
                            start_date = "",
                            end_date = "",
                            sub_category = "",
                            user_nickname = "",
                            user_thumbnail = "",
                            datetime = "",
                            thumbnail = null,
                            view_status = list[0].progress?.get(i - 1)!!.studio.view_status,
                            review_status = list[0].progress?.get(i - 1)!!.studio.review_status,
                            on_progress = list[0].progress?.get(i - 1)!!.studio.on_progress,
                            refund_status = list[0].progress?.get(i - 1)!!.studio.refund_status
                        )
                        refund_false += 1
                        refundList.add(alldataItem)
                    }
                } else if (list[0].progress?.get(i - 1)!!.expert != null) {
                    if (list[0].progress?.get(i - 1)!!.expert.refund_status == false) {
                        if (refund_false == 0) {
                            val ongoingItem = ProgressAllDate(
                                type = -1,
                                reserve_idx = "",
                                expert_idx = "",
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
                                sub_category = "",
                                user_nickname = "",
                                user_thumbnail = "",
                                datetime = "",
                                thumbnail = null,
                                view_status = false,
                                review_status = false,
                                on_progress = false,
                                refund_status = false
                            )
                            refundList.add(ongoingItem)
                        }
                        val alldataItem = ProgressAllDate(
                            type = list[0].progress?.get(i - 1)!!.expert.expert_type,
                            reserve_idx = list[0].progress?.get(i - 1)!!.expert.reserve_idx,
                            expert_idx = list[0].progress?.get(i - 1)!!.expert.reserve_idx,
                            request_idx = "",
                            request_log_idx = "",
                            room_name = "",
                            head_count = list[0].progress?.get(i - 1)!!.expert.head_count,
                            expert_type = list[0].progress?.get(i - 1)!!.expert.expert_type,
                            price = list[0].progress?.get(i - 1)!!.expert.price,
                            time = list[0].progress?.get(i - 1)!!.expert.time,
                            date = list[0].progress?.get(i - 1)!!.expert.date,
                            date_term = "",
                            start_date = "",
                            end_date = "",
                            sub_category = "",
                            user_nickname = "",
                            user_thumbnail = "",
                            datetime = "",
                            thumbnail = null,
                            view_status = list[0].progress?.get(i - 1)!!.expert.view_status,
                            review_status = list[0].progress?.get(i - 1)!!.expert.review_status,
                            on_progress = list[0].progress?.get(i - 1)!!.expert.on_progress,
                            refund_status = list[0].progress?.get(i - 1)!!.expert.refund_status
                        )
                        refund_false += 1
                        refundList.add(alldataItem)
                    }
                } else if (list[0].progress?.get(i - 1)!!.request != null) {
                    if (list[0].progress?.get(i - 1)!!.request.refund_status == false) {
                        if (refund_false == 0) {
                            val ongoingItem = ProgressAllDate(
                                type = -1,
                                reserve_idx = "",
                                expert_idx = "",
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
                                sub_category = "",
                                user_nickname = "",
                                user_thumbnail = "",
                                datetime = "",
                                thumbnail = null,
                                view_status = false,
                                review_status = false,
                                on_progress = false,
                                refund_status = false
                            )
                            refundList.add(ongoingItem)
                        }
                        val alldataItem = ProgressAllDate(
                            type = 5,
                            reserve_idx = "",
                            expert_idx = list[0].progress?.get(i - 1)!!.request.expert_idx,
                            request_idx = list[0].progress?.get(i - 1)!!.request.request_idx,
                            request_log_idx = list[0].progress?.get(i - 1)!!.request.request_log_idx,
                            room_name = "",
                            head_count = "",
                            expert_type = list[0].progress?.get(i - 1)!!.request.expert_type,
                            price = list[0].progress?.get(i - 1)!!.request.price,
                            time = "",
                            date = "",
                            date_term = "",
                            start_date = "",
                            end_date = "",
                            sub_category = list[0].progress?.get(i - 1)!!.request.sub_category,
                            user_nickname = list[0].progress?.get(i - 1)!!.request.user_nickname,
                            user_thumbnail = list[0].progress?.get(i - 1)!!.request.user_thumbnail,
                            datetime = list[0].progress?.get(i - 1)!!.request.datetime,
                            thumbnail = list[0].progress?.get(i - 1)!!.request.thumbnail,
                            view_status = list[0].progress?.get(i - 1)!!.request.view_status,
                            review_status = list[0].progress?.get(i - 1)!!.request.review_status,
                            on_progress = list[0].progress?.get(i - 1)!!.request.on_progress,
                            refund_status = list[0].progress?.get(i - 1)!!.request.refund_status
                        )
                        refund_false += 1
                        refundList.add(alldataItem)
                    }

                }
            }
        }
        if (list[0].progress?.size != 0) {
            for (i in 1..list[0].progress!!.size) {
                if (list[0].progress?.get(i - 1)!!.studio != null) {
                    if (list[0].progress?.get(i - 1)!!.studio.refund_status == true) {
                        if (refund_true == 0) {
                            val ongoingItem = ProgressAllDate(
                                type = -2,
                                reserve_idx = "",
                                expert_idx = "",
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
                                sub_category = "",
                                user_nickname = "",
                                user_thumbnail = "",
                                datetime = "",
                                thumbnail = null,
                                view_status = false,
                                review_status = false,
                                on_progress = false,
                                refund_status = false
                            )
                            refundList.add(ongoingItem)
                        }
                        val alldataItem = ProgressAllDate(
                            type = 0,
                            reserve_idx = list[0].progress?.get(i - 1)!!.studio.reserve_idx,
                            expert_idx = list[0].progress?.get(i - 1)!!.studio.expert_idx,
                            request_idx = "",
                            request_log_idx = "",
                            room_name = list[0].progress?.get(i - 1)!!.studio.room_name,
                            head_count = list[0].progress?.get(i - 1)!!.studio.head_count,
                            expert_type = list[0].progress?.get(i - 1)!!.studio.expert_type,
                            price = list[0].progress?.get(i - 1)!!.studio.price,
                            time = list[0].progress?.get(i - 1)!!.studio.time,
                            date = list[0].progress?.get(i - 1)!!.studio.date,
                            date_term = "",
                            start_date = "",
                            end_date = "",
                            sub_category = "",
                            user_nickname = "",
                            user_thumbnail = "",
                            datetime = "",
                            thumbnail = null,
                            view_status = list[0].progress?.get(i - 1)!!.studio.view_status,
                            review_status = list[0].progress?.get(i - 1)!!.studio.review_status,
                            on_progress = list[0].progress?.get(i - 1)!!.studio.on_progress,
                            refund_status = list[0].progress?.get(i - 1)!!.studio.refund_status
                        )
                        refund_true += 1
                        refundList.add(alldataItem)
                    }
                } else if (list[0].progress?.get(i - 1)!!.expert != null) {
                    if (list[0].progress?.get(i - 1)!!.expert.refund_status == true) {
                        if (refund_true == 0) {
                            val ongoingItem = ProgressAllDate(
                                type = -2,
                                reserve_idx = "",
                                expert_idx = "",
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
                                sub_category = "",
                                user_nickname = "",
                                user_thumbnail = "",
                                datetime = "",
                                thumbnail = null,
                                view_status = false,
                                review_status = false,
                                on_progress = false,
                                refund_status = false
                            )
                            refundList.add(ongoingItem)
                        }
                        val alldataItem = ProgressAllDate(
                            type = list[0].progress?.get(i - 1)!!.expert.expert_type,
                            reserve_idx = list[0].progress?.get(i - 1)!!.expert.reserve_idx,
                            expert_idx = list[0].progress?.get(i - 1)!!.expert.reserve_idx,
                            request_idx = "",
                            request_log_idx = "",
                            room_name = "",
                            head_count = list[0].progress?.get(i - 1)!!.expert.head_count,
                            expert_type = list[0].progress?.get(i - 1)!!.expert.expert_type,
                            price = list[0].progress?.get(i - 1)!!.expert.price,
                            time = list[0].progress?.get(i - 1)!!.expert.time,
                            date = list[0].progress?.get(i - 1)!!.expert.date,
                            date_term = "",
                            start_date = "",
                            end_date = "",
                            sub_category = "",
                            user_nickname = "",
                            user_thumbnail = "",
                            datetime = "",
                            thumbnail = null,
                            view_status = list[0].progress?.get(i - 1)!!.expert.view_status,
                            review_status = list[0].progress?.get(i - 1)!!.expert.review_status,
                            on_progress = list[0].progress?.get(i - 1)!!.expert.on_progress,
                            refund_status = list[0].progress?.get(i - 1)!!.expert.refund_status
                        )
                        refund_true += 1
                        refundList.add(alldataItem)
                    }
                } else if (list[0].progress?.get(i - 1)!!.request != null) {
                    if (list[0].progress?.get(i - 1)!!.request.refund_status == true) {
                        if (refund_true == 0) {
                            val ongoingItem = ProgressAllDate(
                                type = -2,
                                reserve_idx = "",
                                expert_idx = "",
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
                                sub_category = "",
                                user_nickname = "",
                                user_thumbnail = "",
                                datetime = "",
                                thumbnail = null,
                                view_status = false,
                                review_status = false,
                                on_progress = false,
                                refund_status = false
                            )
                            refundList.add(ongoingItem)
                        }
                        val alldataItem = ProgressAllDate(
                            type = 5,
                            reserve_idx = "",
                            expert_idx = list[0].progress?.get(i - 1)!!.request.expert_idx,
                            request_idx = list[0].progress?.get(i - 1)!!.request.request_idx,
                            request_log_idx = list[0].progress?.get(i - 1)!!.request.request_log_idx,
                            room_name = "",
                            head_count = "",
                            expert_type = list[0].progress?.get(i - 1)!!.request.expert_type,
                            price = list[0].progress?.get(i - 1)!!.request.price,
                            time = "",
                            date = "",
                            date_term = "",
                            start_date = "",
                            end_date = "",
                            sub_category = list[0].progress?.get(i - 1)!!.request.sub_category,
                            user_nickname = list[0].progress?.get(i - 1)!!.request.user_nickname,
                            user_thumbnail = list[0].progress?.get(i - 1)!!.request.user_thumbnail,
                            datetime = list[0].progress?.get(i - 1)!!.request.datetime,
                            thumbnail = list[0].progress?.get(i - 1)!!.request.thumbnail,
                            view_status = list[0].progress?.get(i - 1)!!.request.view_status,
                            review_status = list[0].progress?.get(i - 1)!!.request.review_status,
                            on_progress = list[0].progress?.get(i - 1)!!.request.on_progress,
                            refund_status = list[0].progress?.get(i - 1)!!.request.refund_status
                        )
                        refund_true += 1
                        refundList.add(alldataItem)
                    }

                }
            }
        }
    }

    private fun reviewding(list: ArrayList<ProgressList>) {
        reviewList = ArrayList<ProgressAllDate>()
        review_false = 0
        review_true = 0

        if (list[0].progress?.size != 0) {
            for (i in 1..list[0].progress!!.size) {
                if (list[0].progress?.get(i - 1)!!.studio != null) {
                    if (list[0].progress?.get(i - 1)!!.studio.review_status == false) {
                        if (review_false == 0) {
                            val ongoingItem = ProgressAllDate(
                                type = -1,
                                reserve_idx = "",
                                expert_idx = "",
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
                                sub_category = "",
                                user_nickname = "",
                                user_thumbnail = "",
                                datetime = "",
                                thumbnail = null,
                                view_status = false,
                                review_status = false,
                                on_progress = false,
                                refund_status = false
                            )
                            reviewList.add(ongoingItem)
                        }
                        val alldataItem = ProgressAllDate(
                            type = 0,
                            reserve_idx = list[0].progress?.get(i - 1)!!.studio.reserve_idx,
                            expert_idx = list[0].progress?.get(i - 1)!!.studio.expert_idx,
                            request_idx = "",
                            request_log_idx = "",
                            room_name = list[0].progress?.get(i - 1)!!.studio.room_name,
                            head_count = list[0].progress?.get(i - 1)!!.studio.head_count,
                            expert_type = list[0].progress?.get(i - 1)!!.studio.expert_type,
                            price = list[0].progress?.get(i - 1)!!.studio.price,
                            time = list[0].progress?.get(i - 1)!!.studio.time,
                            date = list[0].progress?.get(i - 1)!!.studio.date,
                            date_term = "",
                            start_date = "",
                            end_date = "",
                            sub_category = "",
                            user_nickname = "",
                            user_thumbnail = "",
                            datetime = "",
                            thumbnail = null,
                            view_status = list[0].progress?.get(i - 1)!!.studio.view_status,
                            review_status = list[0].progress?.get(i - 1)!!.studio.review_status,
                            on_progress = list[0].progress?.get(i - 1)!!.studio.on_progress,
                            refund_status = list[0].progress?.get(i - 1)!!.studio.refund_status
                        )
                        review_false += 1
                        reviewList.add(alldataItem)
                    }
                } else if (list[0].progress?.get(i - 1)!!.expert != null) {
                    if (list[0].progress?.get(i - 1)!!.expert.review_status == false) {
                        if (review_false == 0) {
                            val ongoingItem = ProgressAllDate(
                                type = -1,
                                reserve_idx = "",
                                expert_idx = "",
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
                                sub_category = "",
                                user_nickname = "",
                                user_thumbnail = "",
                                datetime = "",
                                thumbnail = null,
                                view_status = false,
                                review_status = false,
                                on_progress = false,
                                refund_status = false
                            )
                            reviewList.add(ongoingItem)
                        }
                        val alldataItem = ProgressAllDate(
                            type = list[0].progress?.get(i - 1)!!.expert.expert_type,
                            reserve_idx = list[0].progress?.get(i - 1)!!.expert.reserve_idx,
                            expert_idx = list[0].progress?.get(i - 1)!!.expert.reserve_idx,
                            request_idx = "",
                            request_log_idx = "",
                            room_name = "",
                            head_count = list[0].progress?.get(i - 1)!!.expert.head_count,
                            expert_type = list[0].progress?.get(i - 1)!!.expert.expert_type,
                            price = list[0].progress?.get(i - 1)!!.expert.price,
                            time = list[0].progress?.get(i - 1)!!.expert.time,
                            date = list[0].progress?.get(i - 1)!!.expert.date,
                            date_term = "",
                            start_date = "",
                            end_date = "",
                            sub_category = "",
                            user_nickname = "",
                            user_thumbnail = "",
                            datetime = "",
                            thumbnail = null,
                            view_status = list[0].progress?.get(i - 1)!!.expert.view_status,
                            review_status = list[0].progress?.get(i - 1)!!.expert.review_status,
                            on_progress = list[0].progress?.get(i - 1)!!.expert.on_progress,
                            refund_status = list[0].progress?.get(i - 1)!!.expert.refund_status
                        )
                        review_false += 1
                        reviewList.add(alldataItem)
                    }
                } else if (list[0].progress?.get(i - 1)!!.request != null) {
                    if (list[0].progress?.get(i - 1)!!.request.review_status == false) {
                        if (review_false == 0) {
                            val ongoingItem = ProgressAllDate(
                                type = -1,
                                reserve_idx = "",
                                expert_idx = "",
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
                                sub_category = "",
                                user_nickname = "",
                                user_thumbnail = "",
                                datetime = "",
                                thumbnail = null,
                                view_status = false,
                                review_status = false,
                                on_progress = false,
                                refund_status = false
                            )
                            reviewList.add(ongoingItem)
                        }
                        val alldataItem = ProgressAllDate(
                            type = 5,
                            reserve_idx = "",
                            expert_idx = list[0].progress?.get(i - 1)!!.request.expert_idx,
                            request_idx = list[0].progress?.get(i - 1)!!.request.request_idx,
                            request_log_idx = list[0].progress?.get(i - 1)!!.request.request_log_idx,
                            room_name = "",
                            head_count = "",
                            expert_type = list[0].progress?.get(i - 1)!!.request.expert_type,
                            price = list[0].progress?.get(i - 1)!!.request.price,
                            time = "",
                            date = "",
                            date_term = "",
                            start_date = "",
                            end_date = "",
                            sub_category = list[0].progress?.get(i - 1)!!.request.sub_category,
                            user_nickname = list[0].progress?.get(i - 1)!!.request.user_nickname,
                            user_thumbnail = list[0].progress?.get(i - 1)!!.request.user_thumbnail,
                            datetime = list[0].progress?.get(i - 1)!!.request.datetime,
                            thumbnail = list[0].progress?.get(i - 1)!!.request.thumbnail,
                            view_status = list[0].progress?.get(i - 1)!!.request.view_status,
                            review_status = list[0].progress?.get(i - 1)!!.request.review_status,
                            on_progress = list[0].progress?.get(i - 1)!!.request.on_progress,
                            refund_status = list[0].progress?.get(i - 1)!!.request.refund_status
                        )
                        review_false += 1
                        reviewList.add(alldataItem)
                    }

                }
            }
        }
        if (list[0].progress?.size != 0) {
            for (i in 1..list[0].progress!!.size) {
                if (list[0].progress?.get(i - 1)!!.studio != null) {
                    if (list[0].progress?.get(i - 1)!!.studio.review_status == true) {
                        if (review_true == 0) {
                            val ongoingItem = ProgressAllDate(
                                type = -2,
                                reserve_idx = "",
                                expert_idx = "",
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
                                sub_category = "",
                                user_nickname = "",
                                user_thumbnail = "",
                                datetime = "",
                                thumbnail = null,
                                view_status = false,
                                review_status = false,
                                on_progress = false,
                                refund_status = false
                            )
                            reviewList.add(ongoingItem)
                        }
                        val alldataItem = ProgressAllDate(
                            type = 0,
                            reserve_idx = list[0].progress?.get(i - 1)!!.studio.reserve_idx,
                            expert_idx = list[0].progress?.get(i - 1)!!.studio.expert_idx,
                            request_idx = "",
                            request_log_idx = "",
                            room_name = list[0].progress?.get(i - 1)!!.studio.room_name,
                            head_count = list[0].progress?.get(i - 1)!!.studio.head_count,
                            expert_type = list[0].progress?.get(i - 1)!!.studio.expert_type,
                            price = list[0].progress?.get(i - 1)!!.studio.price,
                            time = list[0].progress?.get(i - 1)!!.studio.time,
                            date = list[0].progress?.get(i - 1)!!.studio.date,
                            date_term = "",
                            start_date = "",
                            end_date = "",
                            sub_category = "",
                            user_nickname = "",
                            user_thumbnail = "",
                            datetime = "",
                            thumbnail = null,
                            view_status = list[0].progress?.get(i - 1)!!.studio.view_status,
                            review_status = list[0].progress?.get(i - 1)!!.studio.review_status,
                            on_progress = list[0].progress?.get(i - 1)!!.studio.on_progress,
                            refund_status = list[0].progress?.get(i - 1)!!.studio.refund_status
                        )
                        review_true += 1
                        reviewList.add(alldataItem)
                    }
                } else if (list[0].progress?.get(i - 1)!!.expert != null) {
                    if (list[0].progress?.get(i - 1)!!.expert.review_status == true) {
                        if (review_true == 0) {
                            val ongoingItem = ProgressAllDate(
                                type = -2,
                                reserve_idx = "",
                                expert_idx = "",
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
                                sub_category = "",
                                user_nickname = "",
                                user_thumbnail = "",
                                datetime = "",
                                thumbnail = null,
                                view_status = false,
                                review_status = false,
                                on_progress = false,
                                refund_status = false
                            )
                            reviewList.add(ongoingItem)
                        }
                        val alldataItem = ProgressAllDate(
                            type = list[0].progress?.get(i - 1)!!.expert.expert_type,
                            reserve_idx = list[0].progress?.get(i - 1)!!.expert.reserve_idx,
                            expert_idx = list[0].progress?.get(i - 1)!!.expert.reserve_idx,
                            request_idx = "",
                            request_log_idx = "",
                            room_name = "",
                            head_count = list[0].progress?.get(i - 1)!!.expert.head_count,
                            expert_type = list[0].progress?.get(i - 1)!!.expert.expert_type,
                            price = list[0].progress?.get(i - 1)!!.expert.price,
                            time = list[0].progress?.get(i - 1)!!.expert.time,
                            date = list[0].progress?.get(i - 1)!!.expert.date,
                            date_term = "",
                            start_date = "",
                            end_date = "",
                            sub_category = "",
                            user_nickname = "",
                            user_thumbnail = "",
                            datetime = "",
                            thumbnail = null,
                            view_status = list[0].progress?.get(i - 1)!!.expert.view_status,
                            review_status = list[0].progress?.get(i - 1)!!.expert.review_status,
                            on_progress = list[0].progress?.get(i - 1)!!.expert.on_progress,
                            refund_status = list[0].progress?.get(i - 1)!!.expert.refund_status
                        )
                        review_true += 1
                        reviewList.add(alldataItem)
                    }
                } else if (list[0].progress?.get(i - 1)!!.request != null) {
                    if (list[0].progress?.get(i - 1)!!.request.review_status == true) {
                        if (review_true == 0) {
                            val ongoingItem = ProgressAllDate(
                                type = -2,
                                reserve_idx = "",
                                expert_idx = "",
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
                                sub_category = "",
                                user_nickname = "",
                                user_thumbnail = "",
                                datetime = "",
                                thumbnail = null,
                                view_status = false,
                                review_status = false,
                                on_progress = false,
                                refund_status = false
                            )
                            reviewList.add(ongoingItem)
                        }
                        val alldataItem = ProgressAllDate(
                            type = 5,
                            reserve_idx = "",
                            expert_idx = list[0].progress?.get(i - 1)!!.request.expert_idx,
                            request_idx = list[0].progress?.get(i - 1)!!.request.request_idx,
                            request_log_idx = list[0].progress?.get(i - 1)!!.request.request_log_idx,
                            room_name = "",
                            head_count = "",
                            expert_type = list[0].progress?.get(i - 1)!!.request.expert_type,
                            price = list[0].progress?.get(i - 1)!!.request.price,
                            time = "",
                            date = "",
                            date_term = "",
                            start_date = "",
                            end_date = "",
                            sub_category = list[0].progress?.get(i - 1)!!.request.sub_category,
                            user_nickname = list[0].progress?.get(i - 1)!!.request.user_nickname,
                            user_thumbnail = list[0].progress?.get(i - 1)!!.request.user_thumbnail,
                            datetime = list[0].progress?.get(i - 1)!!.request.datetime,
                            thumbnail = list[0].progress?.get(i - 1)!!.request.thumbnail,
                            view_status = list[0].progress?.get(i - 1)!!.request.view_status,
                            review_status = list[0].progress?.get(i - 1)!!.request.review_status,
                            on_progress = list[0].progress?.get(i - 1)!!.request.on_progress,
                            refund_status = list[0].progress?.get(i - 1)!!.request.refund_status
                        )
                        review_true += 1
                        reviewList.add(alldataItem)
                    }
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.progress_list -> {
                if (listType == 1) {
                    refund_off(mViewDataBinding.refundList, mViewDataBinding.refundListText)
                } else if (listType == 2) {
                    review_off(mViewDataBinding.reviewList, mViewDataBinding.reviewListText)
                }
                progress_on(mViewDataBinding.progressList, mViewDataBinding.progressListText, 0)

                if (user_idx != "") {
                    refreshCheck = 0
                    progressManager.instance.progress_list(0, 1, user_idx,
                        completion = { responseStatus, arraylist ->
                            when (responseStatus) {
                                ESTIMATE.OKAY -> {
                                    mViewDataBinding.progressRecyclerview.removeAllViews()
                                    if (arraylist[0].progress.size != 0) {
                                        mViewDataBinding.progressNotDataLayout.visibility =
                                            View.GONE
                                        mViewDataBinding.progressRecyclerview.visibility =
                                            View.VISIBLE
                                        dateList = arraylist
                                        ongoing(arraylist)

                                        dataAdapter =
                                            context?.let {
                                                ProgressRecyclerViewAdapter(
                                                    it,
                                                    on_progress_true,
                                                    on_progress_false,
                                                    ongoingList
                                                )
                                            }!!
                                        mViewDataBinding.progressRecyclerview.layoutManager =
                                            LinearLayoutManager(
                                                context,
                                                LinearLayoutManager.VERTICAL, false
                                            )
                                        mViewDataBinding.progressRecyclerview.adapter = dataAdapter

                                        dataAdapter.setItemClickListener(object :
                                            ProgressRecyclerViewAdapter.OnItemClickListener {
                                            override fun onClick(
                                                v: View,
                                                position: Int,
                                                item: ProgressAllDate
                                            ) {
                                                when (item.type) {
                                                    0 -> {
                                                        val newIntent = Intent(
                                                            context,
                                                            ProgressStudioActivity::class.java
                                                        )
                                                        newIntent.putExtra(
                                                            "reserve_idx",
                                                            item.reserve_idx
                                                        )
                                                        newIntent.putExtra("chatbool", 0)
                                                        startActivityForResult(newIntent, 3001)
                                                    }
                                                    1 -> {
                                                        val newIntent = Intent(
                                                            context,
                                                            ProgressPhotoActivity::class.java
                                                        )
                                                        newIntent.putExtra(
                                                            "reserve_idx",
                                                            item.reserve_idx
                                                        )
                                                        newIntent.putExtra("chatbool", 0)
                                                        startActivityForResult(newIntent, 3001)
                                                    }
                                                    2 -> {
                                                        val newIntent = Intent(
                                                            context,
                                                            ProgressModelActivity::class.java
                                                        )
                                                        newIntent.putExtra(
                                                            "reserve_idx",
                                                            item.reserve_idx
                                                        )
                                                        newIntent.putExtra("chatbool", 0)
                                                        startActivityForResult(newIntent, 3001)
                                                    }
                                                    3 -> {
                                                        val newIntent = Intent(
                                                            context,
                                                            ProgressTripActivity::class.java
                                                        )
                                                        newIntent.putExtra(
                                                            "reserve_idx",
                                                            item.reserve_idx
                                                        )
                                                        newIntent.putExtra("chatbool", 0)
                                                        startActivityForResult(newIntent, 3001)
                                                    }
                                                    5 -> {
                                                        val newIntent = Intent(
                                                            context,
                                                            ProgressManyActivity::class.java
                                                        )
                                                        newIntent.putExtra(
                                                            "request_idx",
                                                            item.request_idx
                                                        )
                                                        newIntent.putExtra("chatbool", 0)
                                                        newIntent.putExtra(
                                                            "log_idx",
                                                            item.request_log_idx
                                                        )
                                                        startActivityForResult(newIntent, 3001)
                                                    }
                                                }

                                            }

                                        })

                                        dataAdapter.setItemClickListener(object :
                                            ProgressRecyclerViewAdapter.OncompleteClickListener {
                                            override fun onClick(
                                                v: View,
                                                position: Int,
                                                item: ProgressAllDate
                                            ) {

                                            }

                                        })
                                    } else {
                                        mViewDataBinding.progressNotDataLayout.visibility =
                                            View.VISIBLE
                                        mViewDataBinding.listNotImage.setImageResource(R.drawable.send_not_list)
                                        mViewDataBinding.listNotText1.text = "진행현황이 없습니다"
                                        mViewDataBinding.listNotText2.text = "나에게 맞는 전문가를 찾아서\n딱 맞는 서비스를 이용해보세요"
                                        mViewDataBinding.progressRecyclerview.visibility = View.GONE
                                    }

                                }
                            }
                        })
                }
            }
            R.id.refund_list -> {
                if (listType == 0) {
                    progress_off(mViewDataBinding.progressList, mViewDataBinding.progressListText)
                } else if (listType == 2) {
                    review_off(mViewDataBinding.reviewList, mViewDataBinding.reviewListText)
                }
                refund_on(mViewDataBinding.refundList, mViewDataBinding.refundListText, 1)

                if (user_idx != "") {
                    refreshCheck = 1
                    progressManager.instance.progress_list(1, 1, user_idx,
                        completion = { responseStatus, arraylist ->
                            when (responseStatus) {
                                ESTIMATE.OKAY -> {
                                    mViewDataBinding.progressRecyclerview.removeAllViews()
                                    if (arraylist[0].progress.size != 0) {
                                        mViewDataBinding.progressNotDataLayout.visibility =
                                            View.GONE
                                        mViewDataBinding.progressRecyclerview.visibility =
                                            View.VISIBLE
                                        dateList = arraylist
                                        refunding(arraylist)

                                        refundAdapter =
                                            context?.let {
                                                ProgressRefundRecyclerViewAdapter(
                                                    it,
                                                    refund_false,
                                                    refund_true,
                                                    refundList
                                                )
                                            }!!
                                        mViewDataBinding.progressRecyclerview.layoutManager =
                                            LinearLayoutManager(
                                                context,
                                                LinearLayoutManager.VERTICAL, false
                                            )
                                        mViewDataBinding.progressRecyclerview.adapter =
                                            refundAdapter

                                        refundAdapter.setItemClickListener(object :
                                            ProgressRefundRecyclerViewAdapter.OnItemClickListener {
                                            override fun onClick(
                                                v: View,
                                                position: Int,
                                                item: ProgressAllDate
                                            ) {
                                                when (item.type) {
                                                    0 -> {
                                                        val newIntent = Intent(
                                                            context,
                                                            RefundStudioActivity::class.java
                                                        )
                                                        newIntent.putExtra(
                                                            "reserve_idx",
                                                            item.reserve_idx
                                                        )
                                                        newIntent.putExtra("chatbool", 0)
                                                        startActivityForResult(newIntent, 3002)
                                                    }
                                                    1 -> {
                                                        val newIntent = Intent(
                                                            context,
                                                            RefundPhotoActivity::class.java
                                                        )
                                                        newIntent.putExtra(
                                                            "reserve_idx",
                                                            item.reserve_idx
                                                        )
                                                        newIntent.putExtra("chatbool", 0)
                                                        startActivityForResult(newIntent, 3002)
                                                    }
                                                    2 -> {
                                                        val newIntent = Intent(
                                                            context,
                                                            RefundModelActivity::class.java
                                                        )
                                                        newIntent.putExtra(
                                                            "reserve_idx",
                                                            item.reserve_idx
                                                        )
                                                        newIntent.putExtra("chatbool", 0)
                                                        startActivityForResult(newIntent, 3002)
                                                    }
                                                    3 -> {
                                                        val newIntent = Intent(
                                                            context,
                                                            RefundTripActivity::class.java
                                                        )
                                                        newIntent.putExtra(
                                                            "reserve_idx",
                                                            item.reserve_idx
                                                        )
                                                        newIntent.putExtra("chatbool", 0)
                                                        startActivityForResult(newIntent, 3002)
                                                    }
                                                    5 -> {
                                                        val newIntent = Intent(
                                                            context,
                                                            RefundManyActivity::class.java
                                                        )
                                                        newIntent.putExtra(
                                                            "request_idx",
                                                            item.request_idx
                                                        )
                                                        newIntent.putExtra("chatbool", 0)
                                                        newIntent.putExtra(
                                                            "log_idx",
                                                            item.request_log_idx
                                                        )
                                                        startActivityForResult(newIntent, 3002)
                                                    }
                                                }

                                            }

                                        })

                                        refundAdapter.setItemClickListener(object :
                                            ProgressRefundRecyclerViewAdapter.OncompleteClickListener {
                                            override fun onClick(
                                                v: View,
                                                position: Int,
                                                item: ProgressAllDate
                                            ) {

                                            }

                                        })
                                    } else {
                                        mViewDataBinding.progressNotDataLayout.visibility =
                                            View.VISIBLE
                                        mViewDataBinding.listNotImage.setImageResource(R.drawable.refund_not_list)
                                        mViewDataBinding.listNotText1.text = "취소현황이 없습니다"
                                        mViewDataBinding.listNotText2.text = "환불을 원하시는 경우\n진행했던 전문가와 1:1 채팅을 통하여\n환불요청을 보내세요"
                                        mViewDataBinding.progressRecyclerview.visibility = View.GONE
                                    }

                                }
                            }
                        })
                }
            }
            R.id.review_list -> {
                if (listType == 0) {
                    progress_off(mViewDataBinding.progressList, mViewDataBinding.progressListText)
                } else if (listType == 1) {
                    refund_off(mViewDataBinding.refundList, mViewDataBinding.refundListText)
                }
                review_on(mViewDataBinding.reviewList, mViewDataBinding.reviewListText, 2)

                if (user_idx != "") {
                    refreshCheck = 2
                    progressManager.instance.progress_list(2, 1, user_idx,
                        completion = { responseStatus, arraylist ->
                            when (responseStatus) {
                                ESTIMATE.OKAY -> {
                                    mViewDataBinding.progressRecyclerview.removeAllViews()
                                    if (arraylist[0].progress.size != 0) {
                                        mViewDataBinding.progressNotDataLayout.visibility =
                                            View.GONE
                                        mViewDataBinding.progressRecyclerview.visibility =
                                            View.VISIBLE
                                        dateList = arraylist
                                        reviewding(arraylist)

                                        reviewAdapter =
                                            context?.let {
                                                ProgressReviewRecyclerViewAdapter(
                                                    it,
                                                    review_false,
                                                    review_true,
                                                    reviewList
                                                )
                                            }!!
                                        mViewDataBinding.progressRecyclerview.layoutManager =
                                            LinearLayoutManager(
                                                context,
                                                LinearLayoutManager.VERTICAL, false
                                            )
                                        mViewDataBinding.progressRecyclerview.adapter =
                                            reviewAdapter

                                        reviewAdapter.setItemClickListener(object :
                                            ProgressReviewRecyclerViewAdapter.OnItemClickListener {
                                            override fun onClick(
                                                v: View,
                                                position: Int,
                                                item: ProgressAllDate
                                            ) {
                                                when (item.type) {
                                                    0 -> {
                                                        val newIntent = Intent(
                                                            context,
                                                            ProgressStudioActivity::class.java
                                                        )
                                                        newIntent.putExtra(
                                                            "reserve_idx",
                                                            item.reserve_idx
                                                        )
                                                        newIntent.putExtra("chatbool", 0)
                                                        newIntent.putExtra("review_check", 1)
                                                        startActivityForResult(newIntent, 3003)
                                                    }
                                                    1 -> {
                                                        val newIntent = Intent(
                                                            context,
                                                            ProgressPhotoActivity::class.java
                                                        )
                                                        newIntent.putExtra(
                                                            "reserve_idx",
                                                            item.reserve_idx
                                                        )
                                                        newIntent.putExtra("chatbool", 0)
                                                        newIntent.putExtra("review_check", 1)
                                                        startActivityForResult(newIntent, 3003)
                                                    }
                                                    2 -> {
                                                        val newIntent = Intent(
                                                            context,
                                                            ProgressModelActivity::class.java
                                                        )
                                                        newIntent.putExtra(
                                                            "reserve_idx",
                                                            item.reserve_idx
                                                        )
                                                        newIntent.putExtra("chatbool", 0)
                                                        newIntent.putExtra("review_check", 1)
                                                        startActivityForResult(newIntent, 3003)
                                                    }
                                                    3 -> {
                                                        val newIntent = Intent(
                                                            context,
                                                            ProgressTripActivity::class.java
                                                        )
                                                        newIntent.putExtra(
                                                            "reserve_idx",
                                                            item.reserve_idx
                                                        )
                                                        newIntent.putExtra("chatbool", 0)
                                                        newIntent.putExtra("review_check", 1)
                                                        startActivityForResult(newIntent, 3003)
                                                    }
                                                    5 -> {
                                                        val newIntent = Intent(
                                                            context,
                                                            ProgressManyActivity::class.java
                                                        )
                                                        newIntent.putExtra(
                                                            "request_idx",
                                                            item.request_idx
                                                        )
                                                        newIntent.putExtra("chatbool", 0)
                                                        newIntent.putExtra(
                                                            "log_idx",
                                                            item.request_log_idx
                                                        )
                                                        newIntent.putExtra("review_check", 1)
                                                        startActivityForResult(newIntent, 3003)
                                                    }
                                                }

                                            }

                                        })

                                        reviewAdapter.setItemClickListener(object :
                                            ProgressReviewRecyclerViewAdapter.OnreviewClickListener {
                                            override fun onClick(
                                                v: View,
                                                position: Int,
                                                item: ProgressAllDate
                                            ) {
                                                if (item.reserve_idx != "") {
                                                    val newIntent =
                                                        Intent(context, ReviewActivity::class.java)
                                                    newIntent.putExtra(
                                                        "reserve_idx",
                                                        item.reserve_idx
                                                    )
                                                    newIntent.putExtra("type", 0)
                                                    startActivityForResult(newIntent, 3003)
                                                } else {
                                                    val newIntent =
                                                        Intent(context, ReviewActivity::class.java)
                                                    newIntent.putExtra(
                                                        "request_idx",
                                                        item.request_idx
                                                    )
                                                    newIntent.putExtra(
                                                        "log_idx",
                                                        item.request_log_idx
                                                    )
                                                    newIntent.putExtra("type", 1)
                                                    startActivityForResult(newIntent, 3003)
                                                }

                                            }
                                        })
                                    } else {
                                        mViewDataBinding.progressNotDataLayout.visibility =
                                            View.VISIBLE
                                        mViewDataBinding.listNotImage.setImageResource(R.drawable.my_service_not_list)
                                        mViewDataBinding.listNotText1.text = "후기작성 내역이 없습니다"
                                        mViewDataBinding.listNotText2.text = "원하는 전문가에게 서비스를 받고\n후기를 남겨주세요"
                                        mViewDataBinding.progressRecyclerview.visibility = View.GONE
                                    }

                                }
                            }
                        })
                }

            }
        }
    }

    private fun progressRefresh() {
        progressManager.instance.progress_list(0, 1, user_idx,
            completion = { responseStatus, arraylist ->
                when (responseStatus) {
                    ESTIMATE.OKAY -> {
                        mViewDataBinding.progressRecyclerview.removeAllViews()
                        if (arraylist[0].progress.size != 0) {
                            mViewDataBinding.progressNotDataLayout.visibility = View.GONE
                            mViewDataBinding.progressRecyclerview.visibility = View.VISIBLE
                            dateList = arraylist
                            ongoing(arraylist)

                            dataAdapter =
                                context?.let {
                                    ProgressRecyclerViewAdapter(
                                        it,
                                        on_progress_true,
                                        on_progress_false,
                                        ongoingList
                                    )
                                }!!
                            mViewDataBinding.progressRecyclerview.layoutManager =
                                LinearLayoutManager(
                                    context,
                                    LinearLayoutManager.VERTICAL, false
                                )
                            mViewDataBinding.progressRecyclerview.adapter = dataAdapter

                            dataAdapter.setItemClickListener(object :
                                ProgressRecyclerViewAdapter.OnItemClickListener {
                                override fun onClick(
                                    v: View,
                                    position: Int,
                                    item: ProgressAllDate
                                ) {
                                    when (item.type) {
                                        0 -> {
                                            val newIntent =
                                                Intent(context, ProgressStudioActivity::class.java)
                                            newIntent.putExtra("reserve_idx", item.reserve_idx)
                                            newIntent.putExtra("chatbool", 0)
                                            startActivityForResult(newIntent, 3001)
                                        }
                                        1 -> {
                                            val newIntent =
                                                Intent(context, ProgressExpertActivity::class.java)
                                            newIntent.putExtra("reserve_idx", item.reserve_idx)
                                            newIntent.putExtra("chatbool", 0)
                                            startActivityForResult(newIntent, 3001)
                                        }
                                        2 -> {
                                            val newIntent =
                                                Intent(context, ProgressExpertActivity::class.java)
                                            newIntent.putExtra("reserve_idx", item.reserve_idx)
                                            newIntent.putExtra("chatbool", 0)
                                            startActivityForResult(newIntent, 3001)
                                        }
                                        3 -> {
                                            val newIntent =
                                                Intent(context, ProgressExpertActivity::class.java)
                                            newIntent.putExtra("reserve_idx", item.reserve_idx)
                                            newIntent.putExtra("chatbool", 0)
                                            startActivityForResult(newIntent, 3001)
                                        }
                                        5 -> {
                                            val newIntent =
                                                Intent(context, ProgressManyActivity::class.java)
                                            newIntent.putExtra("request_idx", item.request_idx)
                                            newIntent.putExtra("chatbool", 0)
                                            newIntent.putExtra("log_idx", item.request_log_idx)
                                            startActivityForResult(newIntent, 3001)
                                        }
                                    }

                                }

                            })

                            dataAdapter.setItemClickListener(object :
                                ProgressRecyclerViewAdapter.OncompleteClickListener {
                                override fun onClick(
                                    v: View,
                                    position: Int,
                                    item: ProgressAllDate
                                ) {
                                        progressManager.instance.reserve_complete(
                                            item.reserve_idx,
                                            completion = { responseStatus ->
                                                when (responseStatus) {
                                                    ESTIMATE.OKAY -> {
                                                        setup()
                                                        Toast.makeText(
                                                            mContext,
                                                            "후기 작성 탭에서 후기를 작성해 주세요!",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                    }
                                                }
                                            })
                                }

                            })
                        } else {
                            mViewDataBinding.progressNotDataLayout.visibility = View.VISIBLE
                            mViewDataBinding.progressRecyclerview.visibility = View.GONE
                        }

                    }
                }
            })
    }

    private fun reviewRefresh() {
        progressManager.instance.progress_list(2, 1, user_idx,
            completion = { responseStatus, arraylist ->
                when (responseStatus) {
                    ESTIMATE.OKAY -> {
                        mViewDataBinding.progressRecyclerview.removeAllViews()
                        if (arraylist[0].progress.size != 0) {
                            refreshCheck = 2
                            mViewDataBinding.progressNotDataLayout.visibility = View.GONE
                            mViewDataBinding.progressRecyclerview.visibility = View.VISIBLE
                            dateList = arraylist
                            reviewding(arraylist)

                            reviewAdapter =
                                context?.let {
                                    ProgressReviewRecyclerViewAdapter(
                                        it,
                                        review_false,
                                        review_true,
                                        reviewList
                                    )
                                }!!
                            mViewDataBinding.progressRecyclerview.layoutManager =
                                LinearLayoutManager(
                                    context,
                                    LinearLayoutManager.VERTICAL, false
                                )
                            mViewDataBinding.progressRecyclerview.adapter = reviewAdapter

                            reviewAdapter.setItemClickListener(object :
                                ProgressReviewRecyclerViewAdapter.OnItemClickListener {
                                override fun onClick(
                                    v: View,
                                    position: Int,
                                    item: ProgressAllDate
                                ) {
                                    when (item.type) {
                                        0 -> {
                                            val newIntent =
                                                Intent(context, ProgressStudioActivity::class.java)
                                            newIntent.putExtra("reserve_idx", item.reserve_idx)
                                            newIntent.putExtra("chatbool", 0)
                                            newIntent.putExtra("review_check", 1)
                                            startActivityForResult(newIntent, 3003)
                                        }
                                        1 -> {
                                            val newIntent =
                                                Intent(context, ProgressExpertActivity::class.java)
                                            newIntent.putExtra("reserve_idx", item.reserve_idx)
                                            newIntent.putExtra("chatbool", 0)
                                            newIntent.putExtra("review_check", 1)
                                            startActivityForResult(newIntent, 3003)
                                        }
                                        2 -> {
                                            val newIntent =
                                                Intent(context, ProgressExpertActivity::class.java)
                                            newIntent.putExtra("reserve_idx", item.reserve_idx)
                                            newIntent.putExtra("chatbool", 0)
                                            newIntent.putExtra("review_check", 1)
                                            startActivityForResult(newIntent, 3003)
                                        }
                                        3 -> {
                                            val newIntent =
                                                Intent(context, ProgressExpertActivity::class.java)
                                            newIntent.putExtra("reserve_idx", item.reserve_idx)
                                            newIntent.putExtra("chatbool", 0)
                                            newIntent.putExtra("review_check", 1)
                                            startActivityForResult(newIntent, 3003)
                                        }
                                        5 -> {
                                            val newIntent =
                                                Intent(context, ProgressManyActivity::class.java)
                                            newIntent.putExtra("request_idx", item.request_idx)
                                            newIntent.putExtra("chatbool", 0)
                                            newIntent.putExtra("log_idx", item.request_log_idx)
                                            newIntent.putExtra("review_check", 1)
                                            startActivityForResult(newIntent, 3003)
                                        }
                                    }

                                }

                            })

                            reviewAdapter.setItemClickListener(object :
                                ProgressReviewRecyclerViewAdapter.OnreviewClickListener {
                                override fun onClick(
                                    v: View,
                                    position: Int,
                                    item: ProgressAllDate
                                ) {
                                    if (item.reserve_idx != "") {
                                        val newIntent = Intent(context, ReviewActivity::class.java)
                                        newIntent.putExtra("reserve_idx", item.reserve_idx)
                                        newIntent.putExtra("type", 0)
                                        startActivityForResult(newIntent, 3003)
                                    } else {
                                        val newIntent = Intent(context, ReviewActivity::class.java)
                                        newIntent.putExtra("request_idx", item.request_idx)
                                        newIntent.putExtra("log_idx", item.request_log_idx)
                                        newIntent.putExtra("type", 1)
                                        startActivityForResult(newIntent, 3003)
                                    }

                                }
                            })
                        } else {
                            mViewDataBinding.progressNotDataLayout.visibility = View.VISIBLE
                            mViewDataBinding.progressRecyclerview.visibility = View.GONE
                        }

                    }
                }
            })
    }

    private fun refundRefresh() {
        progressManager.instance.progress_list(1, 1, user_idx,
            completion = { responseStatus, arraylist ->
                when (responseStatus) {
                    ESTIMATE.OKAY -> {
                        mViewDataBinding.progressRecyclerview.removeAllViews()
                        if (arraylist[0].progress.size != 0) {
                            refreshCheck = 1
                            mViewDataBinding.progressNotDataLayout.visibility = View.GONE
                            mViewDataBinding.progressRecyclerview.visibility = View.VISIBLE
                            dateList = arraylist
                            refunding(arraylist)

                            refundAdapter =
                                context?.let {
                                    ProgressRefundRecyclerViewAdapter(
                                        it,
                                        refund_false,
                                        refund_true,
                                        refundList
                                    )
                                }!!
                            mViewDataBinding.progressRecyclerview.layoutManager =
                                LinearLayoutManager(
                                    context,
                                    LinearLayoutManager.VERTICAL, false
                                )
                            mViewDataBinding.progressRecyclerview.adapter = refundAdapter

                            refundAdapter.setItemClickListener(object :
                                ProgressRefundRecyclerViewAdapter.OnItemClickListener {
                                override fun onClick(
                                    v: View,
                                    position: Int,
                                    item: ProgressAllDate
                                ) {
                                    when (item.type) {
                                        0 -> {
                                            val newIntent =
                                                Intent(context, RefundStudioActivity::class.java)
                                            newIntent.putExtra("reserve_idx", item.reserve_idx)
                                            newIntent.putExtra("chatbool", 0)
                                            startActivityForResult(newIntent, 3002)
                                        }
                                        1 -> {
                                            val newIntent =
                                                Intent(context, RefundExpertActivity::class.java)
                                            newIntent.putExtra("reserve_idx", item.reserve_idx)
                                            newIntent.putExtra("chatbool", 0)
                                            startActivityForResult(newIntent, 3002)
                                        }
                                        2 -> {
                                            val newIntent =
                                                Intent(context, RefundExpertActivity::class.java)
                                            newIntent.putExtra("reserve_idx", item.reserve_idx)
                                            newIntent.putExtra("chatbool", 0)
                                            startActivityForResult(newIntent, 3002)
                                        }
                                        3 -> {
                                            val newIntent =
                                                Intent(context, RefundExpertActivity::class.java)
                                            newIntent.putExtra("reserve_idx", item.reserve_idx)
                                            newIntent.putExtra("chatbool", 0)
                                            startActivityForResult(newIntent, 3002)
                                        }
                                        5 -> {
                                            val newIntent =
                                                Intent(context, RefundManyActivity::class.java)
                                            newIntent.putExtra("request_idx", item.request_idx)
                                            newIntent.putExtra("chatbool", 0)
                                            newIntent.putExtra("log_idx", item.request_log_idx)
                                            startActivityForResult(newIntent, 3002)
                                        }
                                    }

                                }

                            })

                            refundAdapter.setItemClickListener(object :
                                ProgressRefundRecyclerViewAdapter.OncompleteClickListener {
                                override fun onClick(
                                    v: View,
                                    position: Int,
                                    item: ProgressAllDate
                                ) {

                                }

                            })
                        } else {
                            mViewDataBinding.progressNotDataLayout.visibility = View.VISIBLE
                            mViewDataBinding.progressRecyclerview.visibility = View.GONE
                        }

                    }
                }
            })
    }

    override fun onRefresh() {
        when (refreshCheck) {
            0 -> {
                progressRefresh()
                mViewDataBinding.swipe.isRefreshing = false
            }
            1 -> {
                refundRefresh()
                mViewDataBinding.swipe.isRefreshing = false
            }
            2 -> {
                reviewRefresh()
                mViewDataBinding.swipe.isRefreshing = false
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            3004 -> {
                reviewRefresh()
            }
            2001 -> {
                setup()
                Toast.makeText(context,"환불을 요청했습니다. 취소현황에서 확인해보세요",Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun refreshFragment(fragment: Fragment, fragmentManager: FragmentManager) {
        var ft: FragmentTransaction = fragmentManager.beginTransaction()
        ft.detach(fragment).attach(fragment).commit()
    }

}