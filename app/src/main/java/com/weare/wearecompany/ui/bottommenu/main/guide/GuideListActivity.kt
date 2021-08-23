package com.weare.wearecompany.ui.bottommenu.main.guide

import android.content.Intent
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.weare.wearecompany.R
import com.weare.wearecompany.data.main.data.guidelist
import com.weare.wearecompany.data.retrofit.bottomnav.main.GuideManager
import com.weare.wearecompany.databinding.ActivityGuideListBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.utils.RESPONSE_STATUS

class GuideListActivity: BaseActivity<ActivityGuideListBinding>(
    R.layout.activity_guide_list
)

{

    var detaList = ArrayList<guidelist>()
    private lateinit var detaAdapter : GuideListRecyclerViewAdapter

    override fun onCreate() {
        val toobar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toobar)
        val ActionBar = supportActionBar!!
        ActionBar.setDisplayHomeAsUpEnabled(true)
        ActionBar.setDisplayShowTitleEnabled(false)

        load()
    }

    fun load() {

        GuideManager.instance.deta(completion = { responseStatus, date ->
            when (responseStatus) {
                RESPONSE_STATUS.OKAY -> {
                    if (responseStatus != null) {
                        detaList = date
                        detaAdapter = GuideListRecyclerViewAdapter(detaList)

                        mViewDataBinding.guideListRecyclerView.layoutManager = LinearLayoutManager(this,
                            LinearLayoutManager.VERTICAL, false)
                        mViewDataBinding.guideListRecyclerView.adapter = detaAdapter

                        detaAdapter.setItemClickListener(object : GuideListRecyclerViewAdapter.OnItemClickListener {
                            override fun onClick(v: View, position: Int, Item: guidelist) {
                                var newIntent = Intent(this@GuideListActivity, GuideActivity::class.java)
                                newIntent.putExtra("idx",Item.idx)
                                startActivity(newIntent)
                            }

                        })
                    }
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