package com.weare.wearecompany.ui.bottommenu.main.weekly

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.main.data.hotpick
import com.weare.wearecompany.data.retrofit.bottomnav.main.MainManager
import com.weare.wearecompany.databinding.FragmentHome1Binding
import com.weare.wearecompany.ui.base.BaseFragment
import com.weare.wearecompany.ui.detail.DatailActivity
import com.weare.wearecompany.ui.detail.model.ModelActivity
import com.weare.wearecompany.ui.detail.photo.PhotoActivity
import com.weare.wearecompany.ui.detail.trip.TripActivity
import com.weare.wearecompany.utils.RESPONSE_STATUS

class WeeklyFragment:BaseFragment<FragmentHome1Binding>(
    R.layout.fragment_home_1
) {
    lateinit var dataAdatper: WeeklyRecyclerViewAdapter

    override fun onCreate() {
        setup()
    }

    fun setup() {
        MainManager.instance.hotpicklist(complation = {responseStatus, arrayList ->
            when(responseStatus) {
                RESPONSE_STATUS.OKAY -> {
                    dataAdatper = WeeklyRecyclerViewAdapter(arrayList)
                    mViewDataBinding.mainWeeklyRecyclerView.layoutManager = LinearLayoutManager(
                        MyApplication.instance,
                        LinearLayoutManager.VERTICAL, false
                    )
                    mViewDataBinding.mainWeeklyRecyclerView.adapter = dataAdatper
                    dataAdatper.setItemClickListener(object : WeeklyRecyclerViewAdapter.OnItemClickListener{
                        override fun onClick(v: View, position: Int, Item: hotpick) {
                            when(Item.target_type) {
                                0 -> {
                                    var newIntent = Intent(context, DatailActivity::class.java)
                                    newIntent.putExtra("idx", Item.target_idx)
                                    startActivity(newIntent)
                                }
                                1 -> {
                                    var newIntent = Intent(context, PhotoActivity::class.java)
                                    newIntent.putExtra("expert_idx", Item.target_idx)
                                    newIntent.putExtra("user_idx", MyApplication.prefs.getString("user_idx", ""))
                                    startActivity(newIntent)
                                }
                                2 -> {
                                    var newIntent = Intent(context, ModelActivity::class.java)
                                    newIntent.putExtra("expert_idx", Item.target_idx)
                                    newIntent.putExtra("user_idx", MyApplication.prefs.getString("user_idx", ""))
                                    startActivity(newIntent)
                                }
                                3 -> {
                                    var newIntent = Intent(context, TripActivity::class.java)
                                    newIntent.putExtra("expert_idx", Item.target_idx)
                                    newIntent.putExtra("user_idx", MyApplication.prefs.getString("user_idx", ""))
                                    startActivity(newIntent)
                                }
                            }
                        }
                    })
                }
            }
        })
    }
}