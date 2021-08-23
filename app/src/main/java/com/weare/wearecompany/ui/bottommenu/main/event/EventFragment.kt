package com.weare.wearecompany.ui.bottommenu.main.event

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.weare.wearecompany.R
import com.weare.wearecompany.data.main.data.event
import com.weare.wearecompany.data.retrofit.bottomnav.main.MainManager
import com.weare.wearecompany.databinding.FragmentHome2Binding
import com.weare.wearecompany.ui.base.BaseFragment
import com.weare.wearecompany.ui.bottommenu.main.MainViewPagerAdapter
import com.weare.wearecompany.utils.RESPONSE_STATUS

class EventFragment:BaseFragment<FragmentHome2Binding>(
    R.layout.fragment_home_2
) {
    private lateinit var bannarAdapter: MainViewPagerAdapter
    private lateinit var eventAdapter: EventRecyclerViewAdapter


    override fun onCreate() {
        setUp()
    }

    fun setUp() {
        MainManager.instance.eventlist(complation = {responseStatus, BarrayList, EarrayList ->
            when(responseStatus) {
                RESPONSE_STATUS.OKAY -> {
                    bannarAdapter = MainViewPagerAdapter(BarrayList)
                    mViewDataBinding.mainEventViewPager.adapter = bannarAdapter

                    eventAdapter = EventRecyclerViewAdapter(EarrayList)
                    mViewDataBinding.mainEventRecyclerView.layoutManager =
                        LinearLayoutManager(
                            context,
                            LinearLayoutManager.VERTICAL, false
                        )
                    mViewDataBinding.mainEventRecyclerView.adapter = eventAdapter

                    eventAdapter.setItemClickListener(object : EventRecyclerViewAdapter.OnItemClickListener{
                        override fun onClick(v: View, position: Int, Item: event) {
                            var newIntent = Intent(context, EventDetailActivity::class.java)
                            newIntent.putExtra("idx", Item.idx)
                            startActivity(newIntent)
                        }

                    })
                }
            }
        })
    }
}