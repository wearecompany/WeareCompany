package com.weare.wearecompany.ui.bottommenu.mypage.information

import android.content.Intent
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.weare.wearecompany.R
import com.weare.wearecompany.data.bottomnav.mypage.data.manual
import com.weare.wearecompany.data.retrofit.bottomnav.mypage.mypageManager
import com.weare.wearecompany.databinding.ActivityInformationBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.utils.RESPONSE_STATUS

class InformationActivity : BaseActivity<ActivityInformationBinding>(
    R.layout.activity_information
) {

    private var dataList = ArrayList<manual>()
    private lateinit var dataAdapter: InformationRecyclerAdapter

    override fun onCreate() {

        val toobar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toobar)
        val ActionBar = supportActionBar!!
        ActionBar.setDisplayHomeAsUpEnabled(true)
        ActionBar.setDisplayShowTitleEnabled(false)

        setup()

        mypageManager.instance.information(completion = { responseStatus, response ->

            when (responseStatus) {
                RESPONSE_STATUS.OKAY -> {

                    if (responseStatus != null) {
                        dataList = response
                        dataAdapter = InformationRecyclerAdapter(dataList)

                        mViewDataBinding.informationRecyclerView.layoutManager =
                            LinearLayoutManager(
                                this,
                                LinearLayoutManager.VERTICAL, false
                            )

                        mViewDataBinding.informationRecyclerView.adapter = dataAdapter

                        dataAdapter.setItemClickListener(object :
                            InformationRecyclerAdapter.OnItemClickListener {
                            override fun onClick(v: View, position: Int, Item: manual) {
                                if (Item.type == "1") {
                                    var newIntent = Intent(
                                        this@InformationActivity,
                                        InformationDetailActivity::class.java
                                    )
                                    newIntent.putExtra("title", Item.title)
                                    newIntent.putExtra("qurstion", Item.question)
                                    newIntent.putExtra("contents", Item.contents)
                                    startActivity(newIntent)
                                }
                            }
                        })
                    }

                }
                RESPONSE_STATUS.FAIL -> {

                }
            }
        })
    }

    fun setup() {

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