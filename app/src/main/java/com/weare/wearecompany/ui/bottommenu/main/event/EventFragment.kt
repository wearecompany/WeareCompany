package com.weare.wearecompany.ui.bottommenu.main.event

import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.weare.wearecompany.R
import com.weare.wearecompany.data.main.data.banner
import com.weare.wearecompany.data.main.data.event
import com.weare.wearecompany.data.retrofit.bottomnav.main.MainManager
import com.weare.wearecompany.databinding.FragmentHome2Binding
import com.weare.wearecompany.ui.base.BaseFragment
import com.weare.wearecompany.ui.bottommenu.main.MainViewPagerAdapter
import com.weare.wearecompany.ui.bottommenu.main.weekly.WeeklyViewModel
import com.weare.wearecompany.utils.RESPONSE_STATUS
import com.weare.wearecompany.utils.ViewModelProviderFactory

class EventFragment : BaseFragment<FragmentHome2Binding>(
    R.layout.fragment_home_2
) {
    private lateinit var bannarAdapter: MainViewPagerAdapter
    private lateinit var eventAdapter: EventRecyclerViewAdapter

    lateinit var viewmodel: EventViewModel

    override fun onCreate() {
        viewmodel = ViewModelProvider(this, ViewModelProviderFactory()).get(EventViewModel::class.java)
        viewmodel.getEventList()
        setUp()
    }

    private val bannerObserveListener = Observer<ArrayList<banner>> {
        it?.let { banner ->
            bannarAdapter = MainViewPagerAdapter(banner)
            mViewDataBinding.mainEventViewPager.adapter = bannarAdapter
        }
    }
    private val eventObserveListener = Observer<ArrayList<event>> {
        it?.let { event ->

            eventAdapter = EventRecyclerViewAdapter(event)
            mViewDataBinding.mainEventRecyclerView.layoutManager =
                LinearLayoutManager(
                    context,
                    LinearLayoutManager.VERTICAL, false
                )
            mViewDataBinding.mainEventRecyclerView.adapter = eventAdapter
            mViewDataBinding.mainEventRecyclerView.setHasFixedSize(true)

            eventAdapter.setItemClickListener(object : EventRecyclerViewAdapter.OnItemClickListener {
                override fun onClick(v: View, position: Int, Item: event) {
                    var newIntent = Intent(context, EventDetailActivity::class.java)
                    newIntent.putExtra("idx", Item.idx)
                    startActivity(newIntent)
                }

            })
        }
    }

    fun setUp() {

        if (::viewmodel.isInitialized) {
            viewmodel.bannerData.observe(this, bannerObserveListener)
        }
        if (::viewmodel.isInitialized) {
            viewmodel.eventData.observe(this, eventObserveListener)
        }


        /*MainManager.instance.eventlist(complation = {responseStatus, BarrayList, EarrayList ->
            when(responseStatus) {
                RESPONSE_STATUS.OKAY -> {

                }
            }
        })*/

    }
}