package com.weare.wearecompany.ui.bottommenu.main.contents

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.weare.wearecompany.R
import com.weare.wearecompany.data.main.data.contents
import com.weare.wearecompany.data.retrofit.bottomnav.main.MainManager
import com.weare.wearecompany.databinding.FragmentHome3Binding
import com.weare.wearecompany.ui.base.BaseFragment
import com.weare.wearecompany.ui.bottommenu.main.MainViewPagerAdapter
import com.weare.wearecompany.ui.bottommenu.main.event.EventRecyclerViewAdapter
import com.weare.wearecompany.ui.bottommenu.main.guide.GuideActivity
import com.weare.wearecompany.utils.RESPONSE_STATUS

class ContentsFragment: BaseFragment<FragmentHome3Binding>(
    R.layout.fragment_home_3
),View.OnClickListener {

    private lateinit var Adapter: ContentsRecyclerViewAdapter

    override fun onCreate() {
        setUp()
    }

    fun setUp() {
        mViewDataBinding.youtube.setOnClickListener(this)

        MainManager.instance.contentslist(complation = { responseStatus, List ->
            when(responseStatus) {
                RESPONSE_STATUS.OKAY -> {
                    Adapter = ContentsRecyclerViewAdapter(List)
                    mViewDataBinding.mainContentsRecyclerView.layoutManager =
                        LinearLayoutManager(
                            context,
                            LinearLayoutManager.VERTICAL, false
                        )
                    mViewDataBinding.mainContentsRecyclerView.adapter = Adapter

                    Adapter.setItemClickListener(object : ContentsRecyclerViewAdapter.OnItemClickListener{
                        override fun onClick(v: View, position: Int, Item: contents) {
                            var newIntent = Intent(context, GuideActivity::class.java)
                            newIntent.putExtra("idx", Item.idx)
                            startActivity(newIntent)
                        }

                    })
                }
            }
        })
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.youtube -> {
                val uri: Uri = Uri.parse("https://www.youtube.com/watch?v=n9S4pIEDu4c")
                val Instagram_Intent = Intent(Intent.ACTION_VIEW, uri)
                    Instagram_Intent.setPackage("com.google.android.youtube")
                startActivity(Instagram_Intent)
            }
        }
    }
}