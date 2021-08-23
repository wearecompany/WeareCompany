package com.weare.wearecompany.ui.bottommenu.chatting


import android.content.Context
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.chatting.data.chatlist
import com.weare.wearecompany.data.retrofit.chatting.chattingManager
import com.weare.wearecompany.databinding.FragmentChatBinding
import com.weare.wearecompany.ui.base.BaseFragment
import com.weare.wearecompany.ui.chat.ChatActivity
import com.weare.wearecompany.ui.container.ContainerActivity
import com.weare.wearecompany.utils.RESPONSE_STATUS

class ChattingFragment : BaseFragment<FragmentChatBinding>(
    R.layout.fragment_chat
), SwipeRefreshLayout.OnRefreshListener {

    lateinit var mContext: Context
    lateinit var userIdx: String
    private var alarm_chat_idx = ""
    private var remove_position = -1

    private lateinit var dataAdapter: ChattingRecyclerAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ContainerActivity) {
            mContext = context
        }
    }

    override fun onCreate() {

        userIdx = MyApplication.prefs.getString("user_idx", "")
        alarm_chat_idx = arguments?.getString("type_2").toString()
        setup()

    }

    fun setup() {
        mViewDataBinding.swipe.setOnRefreshListener(this)
        if (userIdx != "") {
            chattingManager.instance.list(userIdx, completion = { responseStatus, arrayList ->
                when (responseStatus) {
                    RESPONSE_STATUS.OKAY -> {
                        if (arrayList[0].chat.size != 0) {
                            if (alarm_chat_idx != "") {
                                val i1 = 1
                                for (i in i1..arrayList[0].chat.size) {
                                    if (alarm_chat_idx == arrayList[0].chat[i-1].chat_idx) {
                                        val newIntent = Intent(mContext, ChatActivity::class.java)
                                        newIntent.putExtra("chat_idx", arrayList[0].chat[i-1].chat_idx)
                                        newIntent.putExtra("Entrytype", 1)
                                        newIntent.putExtra("type", arrayList[0].chat[i-1].type)
                                        when (arrayList[0].chat[i-1].type) {
                                            0 -> newIntent.putExtra("reserve_idx", arrayList[0].chat[i-1].reserve_idx)
                                            1 -> {
                                                newIntent.putExtra("request_idx", arrayList[0].chat[i-1].request_idx)
                                                newIntent.putExtra("log_idx", arrayList[0].chat[i-1].request_log_idx)
                                            }
                                        }
                                        newIntent.putExtra(
                                            "expert_user_nickname",
                                            arrayList[0].chat[i-1].expert_user_nickname
                                        )
                                        newIntent.putExtra("chat_end", arrayList[0].chat[i-1].chat_end)
                                        newIntent.putExtra("expert_type", arrayList[0].chat[i-1].expert_type)
                                        newIntent.putExtra("status", arrayList[0].chat[i-1].status)
                                        newIntent.putExtra("refund_status", arrayList[0].chat[i-1].refund_status)
                                        newIntent.putExtra("expert_user_idx", arrayList[0].chat[i-1].expert_user_idx)
                                        newIntent.putExtra("report_status",arrayList[0].chat[i-1].report_status)
                                        newIntent.putExtra("position",i-1)
                                        startActivityForResult(newIntent,3001)
                                        alarm_chat_idx = ""
                                    }
                                }
                            }

                            mViewDataBinding.notChatLayout.visibility = View.GONE
                            dataAdapter = ChattingRecyclerAdapter(arrayList)
                            mViewDataBinding.chatRecyclerView.layoutManager = LinearLayoutManager(
                                context,
                                LinearLayoutManager.VERTICAL, false

                            )
                            mViewDataBinding.chatRecyclerView.adapter = dataAdapter
                            dataAdapter.setItemClickListener(object :

                                ChattingRecyclerAdapter.OnItemClickListener {
                                override fun onClick(v: View, position: Int, Item: chatlist) {

                                    val newIntent = Intent(mContext, ChatActivity::class.java)
                                    newIntent.putExtra("chat_idx", Item.chat_idx)
                                    newIntent.putExtra("Entrytype", 1)
                                    newIntent.putExtra("type", Item.type)
                                    when (Item.type) {
                                        0 -> newIntent.putExtra("reserve_idx", Item.reserve_idx)
                                        1 -> {
                                            newIntent.putExtra("request_idx", Item.request_idx)
                                            newIntent.putExtra("log_idx", Item.request_log_idx)
                                        }
                                    }
                                    newIntent.putExtra(
                                        "expert_user_nickname",
                                        Item.expert_user_nickname
                                    )
                                    newIntent.putExtra("chat_end", Item.chat_end)
                                    newIntent.putExtra("expert_type", Item.expert_type)
                                    newIntent.putExtra("status", Item.status)
                                    newIntent.putExtra("refund_status", Item.refund_status)
                                    newIntent.putExtra("expert_user_idx", Item.expert_user_idx)
                                    newIntent.putExtra("report_status",Item.report_status)
                                    newIntent.putExtra("position",position)
                                    startActivityForResult(newIntent,3001)
                                }
                            })
                        } else {
                            mViewDataBinding.chatRecyclerView.visibility = View.GONE
                            mViewDataBinding.notChatLayout.visibility = View.VISIBLE
                        }


                    }
                }
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != -1) {
            when(resultCode) {
                3002 -> {
                    setup()
                }
            }
        }
    }

    override fun onRefresh() {
        setup()
        mViewDataBinding.swipe.isRefreshing = false
    }
}