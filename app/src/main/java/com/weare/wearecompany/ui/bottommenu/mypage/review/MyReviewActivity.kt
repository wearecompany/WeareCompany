package com.weare.wearecompany.ui.bottommenu.mypage.review

import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.bottomnav.mypage.data.myreview
import com.weare.wearecompany.data.retrofit.bottomnav.mypage.mypageManager
import com.weare.wearecompany.databinding.ActivityMyReviewListBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.utils.RESPONSE_STATUS

class MyReviewActivity:BaseActivity<ActivityMyReviewListBinding>(
    R.layout.activity_my_review_list
) {
    lateinit var user_idx:String

    lateinit var reviewAdapter : MyReviewRecyclerViewAdapter
    override fun onCreate() {

        val toobar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toobar)
        val ActionBar = supportActionBar!!
        ActionBar.setDisplayHomeAsUpEnabled(true)
        ActionBar.setDisplayShowTitleEnabled(false)

        user_idx = MyApplication.prefs.getString("user_idx","")

        setUp()
    }

    fun setUp() {
        mypageManager.instance.myreviewlist(user_idx,completion = { responseStatus, arrayList ->
            when(responseStatus) {
                RESPONSE_STATUS.OKAY -> {
                        mViewDataBinding.notMyReviewListLayout.visibility = View.GONE
                        mViewDataBinding.myReviewRecyclerView.visibility = View.VISIBLE

                        reviewAdapter = MyReviewRecyclerViewAdapter(arrayList)
                        mViewDataBinding.myReviewRecyclerView.layoutManager = LinearLayoutManager(
                            this,
                            LinearLayoutManager.VERTICAL, false
                        )
                        mViewDataBinding.myReviewRecyclerView.adapter = reviewAdapter

                    reviewAdapter.setItemClickListener(object : MyReviewRecyclerViewAdapter.OnItemClickListener{
                        override fun onClick(v: View, position: Int, reviewItem: myreview) {
                        }

                    })
                }
                RESPONSE_STATUS.NO_CONTENT -> {
                    mViewDataBinding.notMyReviewListLayout.visibility = View.VISIBLE
                    mViewDataBinding.myReviewRecyclerView.visibility = View.GONE
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