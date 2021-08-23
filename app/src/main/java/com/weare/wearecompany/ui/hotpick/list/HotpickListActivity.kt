package com.weare.wearecompany.ui.hotpick.list

import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.weare.wearecompany.R
import com.weare.wearecompany.data.main.data.hotpick
import com.weare.wearecompany.databinding.ActivityHotpickListBinding
import com.weare.wearecompany.ui.base.BaseActivity


class HotpickListActivity: BaseActivity<ActivityHotpickListBinding>(
    R.layout.activity_hotpick_list
) {

    private var hotpickDataList = ArrayList<hotpick>()

    private lateinit var hotpickAdapter: HotpickListRecyeclerViewAdapter


    override fun onCreate() {

        val toobar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toobar)
        val ActionBar = supportActionBar!!
        ActionBar.setDisplayHomeAsUpEnabled(true)
        ActionBar.setDisplayShowTitleEnabled(false)

        /*HotPickManager.instant.list(completion ={responseStatus, responseHotpickList ->

            when(responseStatus) {
                RESPONSE_STATUS.OKAY -> {

                    if (responseHotpickList != null) {
                        hotpickDataList = responseHotpickList
                        hotpickAdapter = HotpickListRecyeclerViewAdapter(hotpickDataList)


                        mViewDataBinding.hotpickListRecyclerView.layoutManager = LinearLayoutManager(this,
                                LinearLayoutManager.VERTICAL, false)
                        mViewDataBinding.hotpickListRecyclerView.adapter = hotpickAdapter

                        hotpickAdapter.setItemClickListener(object : HotpickListRecyeclerViewAdapter.OnItemClickListener{
                            override fun onClick(v: View, position: Int, hotpickItem: hotpick) {

                                var newIntent = Intent(this@HotpickListActivity, DatailActivity::class.java)
                                newIntent.putExtra("idx",hotpickItem.target_idx)
                                startActivity(newIntent)
                            }
                        })
                    }
                }
            }
        })*/

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