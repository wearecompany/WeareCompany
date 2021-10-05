package com.weare.wearecompany.ui.bottommenu.mypage.bookmark

import android.content.Intent
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.bottomnav.mypage.Bookmark
import com.weare.wearecompany.data.bottomnav.mypage.data.allList
import com.weare.wearecompany.data.retrofit.bottomnav.mypage.mypageManager
import com.weare.wearecompany.databinding.ActivityBookmarkListBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.ui.detail.DatailActivity
import com.weare.wearecompany.ui.detail.model.ModelActivity
import com.weare.wearecompany.ui.detail.photo.PhotoActivity
import com.weare.wearecompany.ui.detail.trip.TripActivity
import com.weare.wearecompany.utils.LIKE

class BookMarkActivity: BaseActivity<ActivityBookmarkListBinding>(
    R.layout.activity_bookmark_list
) {

    private lateinit var dataAdapter: BookMarkRecyclerViewAdapter
    private var dataList = ArrayList<allList>()

    override fun onCreate() {
        val toobar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toobar)
        val ActionBar = supportActionBar!!
        ActionBar.setDisplayHomeAsUpEnabled(true)
        ActionBar.setDisplayShowTitleEnabled(false)

        setUp()
    }

    private fun setUp() {
        mypageManager.instance.like_list(
            MyApplication.prefs.getString("user_idx", ""),
            completion = { responseStatus, arrayList ->
                when(responseStatus) {
                    LIKE.OKAY -> {
                        if (arrayList[0].Studio.isNotEmpty()) {
                            dataSum(arrayList, 0)
                        }
                        if (arrayList[0].Photo.isNotEmpty()) {
                            dataSum(arrayList, 1)
                        }
                        if (arrayList[0].Model.isNotEmpty()) {
                            dataSum(arrayList, 2)
                        }
                        if (arrayList[0].Trip.isNotEmpty()) {
                            dataSum(arrayList, 3)
                        }
                        if (arrayList[0].Rent.isNotEmpty()) {
                            dataSum(arrayList, 4)
                        }

                        if (dataList.size == 0) {
                            mViewDataBinding.bookmarkNotDataLayout.visibility = View.VISIBLE
                            mViewDataBinding.bookmarkRecyclerview.visibility = View.GONE
                        } else if (dataList.isNotEmpty()) {
                            dataAdapter = BookMarkRecyclerViewAdapter(dataList)
                            mViewDataBinding.bookmarkRecyclerview.layoutManager = LinearLayoutManager(
                                this,
                                LinearLayoutManager.VERTICAL, false
                            )
                            mViewDataBinding.bookmarkRecyclerview.adapter = dataAdapter
                            dataAdapter.setItemClickListener(object : BookMarkRecyclerViewAdapter.OnItemClickListener {
                                override fun onClick(v: View, position: Int, Item: allList) {
                                    when(Item.type) {
                                        0 -> {
                                            var newIntent = Intent(this@BookMarkActivity, DatailActivity::class.java)
                                            newIntent.putExtra("idx", Item.idx)
                                            newIntent.putExtra("user_idx", MyApplication.prefs.getString("user_idx", ""))
                                            startActivityForResult(newIntent,9999)
                                        }
                                        1 -> {
                                            var newIntent = Intent(this@BookMarkActivity, PhotoActivity::class.java)
                                            newIntent.putExtra("expert_idx", Item.idx)
                                            newIntent.putExtra("user_idx", MyApplication.prefs.getString("user_idx", ""))
                                            startActivityForResult(newIntent,9999)
                                        }
                                        2 -> {
                                            var newIntent = Intent(this@BookMarkActivity, ModelActivity::class.java)
                                            newIntent.putExtra("expert_idx", Item.idx)
                                            newIntent.putExtra("user_idx", MyApplication.prefs.getString("user_idx", ""))
                                            startActivityForResult(newIntent,9999)
                                        }
                                        3 -> {
                                            var newIntent = Intent(this@BookMarkActivity, TripActivity::class.java)
                                            newIntent.putExtra("expert_idx", Item.idx)
                                            newIntent.putExtra("user_idx", MyApplication.prefs.getString("user_idx", ""))
                                            startActivityForResult(newIntent,9999)
                                        }
                                    }
                                }

                            })
                        }
                    }
                }
            }
        )
    }

    fun dataSum(list: ArrayList<Bookmark>, type:Int) {
        when(type) {
            0 -> {
                list[0].Studio.let {
                    for (i in list[0].Studio) {
                        val bookmarkItem = allList(
                            type = 0,
                            idx = i.idx,
                            image = i.image,
                            name = i.name,
                            category = i.category,
                            description = i.description,
                        )
                        dataList.add(bookmarkItem)
                    }
                }
            }
            1 -> {
                list[0].Photo.let {
                    for (i in list[0].Photo) {
                        val bookmarkItem = allList(
                            type = 1,
                            idx = i.idx,
                            image = i.image,
                            name = i.name,
                            category = i.category,
                            description = i.description,
                        )
                        dataList.add(bookmarkItem)
                    }
                }
            }
            2 -> {
                list[0].Model.let {
                    for (i in list[0].Model) {
                        val bookmarkItem = allList(
                            type = 2,
                            idx = i.idx,
                            image = i.image,
                            name = i.name,
                            category = i.category,
                            description = i.description,
                        )
                        dataList.add(bookmarkItem)
                    }
                }
            }
            3 -> {
                list[0].Trip.let {
                    for (i in list[0].Trip) {
                        val bookmarkItem = allList(
                            type = 3,
                            idx = i.idx,
                            image = i.image,
                            name = i.name,
                            category = i.category,
                            description = i.description,
                        )
                        dataList.add(bookmarkItem)
                    }
                }
            }
            4 -> {
                list[0].Rent.let {
                    for (i in list[0].Rent) {
                        val bookmarkItem = allList(
                            type = 4,
                            idx = i.idx,
                            image = i.image,
                            name = i.name,
                            category = i.category,
                            description = i.description,
                        )
                        dataList.add(bookmarkItem)
                    }
                }
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 9999) {
            dataList.clear()
            setUp()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home -> {

                onBackPressed()

            }
        }
        return super.onOptionsItemSelected(item)
    }
}