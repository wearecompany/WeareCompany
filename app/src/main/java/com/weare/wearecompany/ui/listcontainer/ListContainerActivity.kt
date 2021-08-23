package com.weare.wearecompany.ui.listcontainer

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.Toolbar
import com.weare.wearecompany.R
import com.weare.wearecompany.databinding.ActivityListcontainerBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.ui.listcontainer.trip.TripFragment
import com.weare.wearecompany.ui.listcontainer.model.ModelFragment
import com.weare.wearecompany.ui.listcontainer.photographer.PhotographerFragment
import com.weare.wearecompany.ui.listcontainer.rent.RentFragment
import com.weare.wearecompany.ui.listcontainer.studio.StudioFragment
import com.google.android.material.tabs.TabLayout


class
ListContainerActivity : BaseActivity<ActivityListcontainerBinding>(
        R.layout.activity_listcontainer
){

    companion object {

        fun newInstance(): ListContainerActivity {
            return ListContainerActivity()
        }
    }

    private val Studiofragment: StudioFragment = StudioFragment()
    private val photoFragment: PhotographerFragment = PhotographerFragment()
    private val modelFragment: ModelFragment = ModelFragment()
    private val buautyFragment: TripFragment = TripFragment()

    var imm : InputMethodManager? = null

    override fun onCreate() {

        val toobar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toobar)
        val ActionBar = supportActionBar!!
        ActionBar.setDisplayHomeAsUpEnabled(true)
        ActionBar.setDisplayShowTitleEnabled(false)

        imm = getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager?

        supportFragmentManager.beginTransaction().apply {
            add(R.id.listfragment, Studiofragment)
            add(R.id.listfragment, photoFragment)
            add(R.id.listfragment, modelFragment)
            add(R.id.listfragment, buautyFragment)

            commit()
        }

        if (intent.getIntExtra("num", 0) != null) {
            when(intent.getIntExtra("num", 0)) {
                0 -> {
                    supportFragmentManager.beginTransaction().apply {
                        mViewDataBinding.toolbarName.setText("모델")
                        hide(Studiofragment)
                        hide(photoFragment)
                        show(modelFragment)
                        hide(buautyFragment)

                        commit()
                    }
                }
                1 -> {

                    supportFragmentManager.beginTransaction().apply {
                        mViewDataBinding.listTab.selectTab(mViewDataBinding.listTab.getTabAt(1))
                        mViewDataBinding.toolbarName.setText("뷰티전문가")
                        hide(Studiofragment)
                        hide(photoFragment)
                        hide(modelFragment)
                        show(buautyFragment)

                        commit()

                    }

                }
                2 -> {
                    supportFragmentManager.beginTransaction().apply {
                        mViewDataBinding.listTab.selectTab(mViewDataBinding.listTab.getTabAt(2))
                        mViewDataBinding.toolbarName.setText("포토그래퍼")
                        hide(Studiofragment)
                        show(photoFragment)
                        hide(modelFragment)
                        hide(buautyFragment)

                        commit()
                    }
                }
                3 -> {
                    supportFragmentManager.beginTransaction().apply {
                        mViewDataBinding.listTab.selectTab(mViewDataBinding.listTab.getTabAt(3))
                        mViewDataBinding.toolbarName.setText("스튜디오")
                        show(Studiofragment)
                        hide(photoFragment)
                        hide(modelFragment)
                        hide(buautyFragment)

                        commit()

                    }
                }
            }

        }

        mViewDataBinding.listTab.addOnTabSelectedListener(object  : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 0) {

                    supportFragmentManager.beginTransaction().apply {
                        mViewDataBinding.toolbarName.setText("모델")
                        hide(Studiofragment)
                        hide(photoFragment)
                        show(modelFragment)
                        hide(buautyFragment)

                        commit()

                    }

                } else if (tab?.position == 1) {
                    supportFragmentManager.beginTransaction().apply {
                        mViewDataBinding.toolbarName.setText("뷰티전문가")
                        hide(Studiofragment)
                        hide(photoFragment)
                        hide(modelFragment)
                        show(buautyFragment)

                        commit()

                    }

                } else if (tab?.position == 2) {

                    supportFragmentManager.beginTransaction().apply {
                        mViewDataBinding.toolbarName.setText("포토그래퍼")
                        hide(Studiofragment)
                        show(photoFragment)
                        hide(modelFragment)
                        hide(buautyFragment)

                        commit()

                    }

                } else if (tab?.position == 3) {

                    supportFragmentManager.beginTransaction().apply {
                        mViewDataBinding.toolbarName.setText("스튜디오")
                        show(Studiofragment)
                        hide(photoFragment)
                        hide(modelFragment)
                        hide(buautyFragment)

                        commit()

                    }

                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
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