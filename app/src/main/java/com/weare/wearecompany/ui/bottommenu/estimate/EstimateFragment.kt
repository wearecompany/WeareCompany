package com.weare.wearecompany.ui.bottommenu.estimate

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayout
import com.weare.wearecompany.R
import com.weare.wearecompany.databinding.FragmentExpterBinding
import com.weare.wearecompany.ui.base.BaseFragment
import com.weare.wearecompany.ui.bottommenu.estimate.estimateDatail.EstimateDatailActivity
import com.weare.wearecompany.ui.bottommenu.estimate.progress.ProgressFragment
import com.weare.wearecompany.ui.bottommenu.estimate.receive.ReceiveFragment
import com.weare.wearecompany.ui.bottommenu.estimate.send.SendFragment
import com.weare.wearecompany.ui.container.ContainerActivity

class expetrFragment : BaseFragment<FragmentExpterBinding>(
    R.layout.fragment_expter
), View.OnClickListener {

    lateinit var mContext: Context

    private var SendFragment = SendFragment()
    private var ReceiveFragment = ReceiveFragment()
    private var ProgressFragment = ProgressFragment()
    private var payment:String = ""

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ContainerActivity) {
            mContext = context
        }
    }



    override fun onCreate() {
        payment = arguments?.getInt("payment").toString()
        setup()
    }

    fun setup() {
        childFragmentManager.beginTransaction()
            .replace(R.id.estimate_fragment, SendFragment)
            .commit()

        mViewDataBinding.estimateTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 0) {
                    childFragmentManager.beginTransaction()
                        .replace(R.id.estimate_fragment, SendFragment)
                        .commit()
                } else if (tab?.position == 1) {
                    childFragmentManager.beginTransaction()
                        .replace(R.id.estimate_fragment, ReceiveFragment)
                        .commit()
                } else if (tab?.position == 2) {
                    childFragmentManager.beginTransaction()
                        .replace(R.id.estimate_fragment, ProgressFragment)
                        .commit()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
        if (payment == "ok") {
            childFragmentManager.beginTransaction()
                .replace(R.id.estimate_fragment, ProgressFragment)
                .commit()
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
            when (resultCode) {
                5001 -> {
                    childFragmentManager.beginTransaction()
                        .replace(R.id.estimate_fragment, ProgressFragment)
                        .commit()
                }
            }

    }


}
