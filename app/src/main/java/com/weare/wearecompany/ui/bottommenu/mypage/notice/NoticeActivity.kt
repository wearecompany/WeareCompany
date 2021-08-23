package com.weare.wearecompany.ui.bottommenu.mypage.notice

import android.content.Intent
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.weare.wearecompany.R
import com.weare.wearecompany.data.bottomnav.mypage.data.notice
import com.weare.wearecompany.data.retrofit.bottomnav.mypage.mypageManager
import com.weare.wearecompany.databinding.ActivityNoticeBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.utils.RESPONSE_STATUS

class NoticeActivity : BaseActivity<ActivityNoticeBinding>(
    R.layout.activity_notice
) {

    private var dataList = ArrayList<notice>()
    private lateinit var dataAdapter: NoticeRecyclerAdapter
    override fun onCreate() {

        val toobar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toobar)
        val ActionBar = supportActionBar!!
        ActionBar.setDisplayHomeAsUpEnabled(true)
        ActionBar.setDisplayShowTitleEnabled(false)

        mypageManager.instance.notice(completion = { responseStatus, response ->

            when (responseStatus) {
                RESPONSE_STATUS.OKAY -> {

                    if (responseStatus != null) {
                        dataList = response
                        dataAdapter = NoticeRecyclerAdapter(dataList)

                        mViewDataBinding.noticeRecyclerView.layoutManager =
                            LinearLayoutManager(
                                this,
                                LinearLayoutManager.VERTICAL, false
                            )
                        mViewDataBinding.noticeRecyclerView.adapter = dataAdapter

                        dataAdapter.setItemClickListener(object :
                            NoticeRecyclerAdapter.OnItemClickListener {
                            override fun onClick(v: View, position: Int, Item: notice) {
                                var newIntent = Intent(
                                    this@NoticeActivity,
                                    NoticeDetailActivity::class.java
                                )
                                newIntent.putExtra("title", Item.title)
                                newIntent.putExtra("detatime", Item.datetime)
                                newIntent.putExtra("contents", Item.text_contents)
                                startActivity(newIntent)
                            }

                        })
                    }

                }
                RESPONSE_STATUS.FAIL -> {

                }
            }
        })
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