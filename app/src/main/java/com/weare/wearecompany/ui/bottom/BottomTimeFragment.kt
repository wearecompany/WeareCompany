package com.weare.wearecompany.ui.bottom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.weare.wearecompany.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.bottom_dialog_time.view.*


class BottomTimeFragment(val startTime:(Int)-> Unit, val endTime: (Int) -> Unit, val itemClick: (Int) -> Unit) : BottomSheetDialogFragment() {

    private var startCheckTime = 0
    private var startOneCheckTime = false
    private var endOneCheckTime = false
    private var endCheckTime = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.bottom_dialog_time, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.start_tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (!startOneCheckTime) {
                    startOneCheckTime = true
                    for (i in 0..23) {
                        if (tab?.position == i) {
                            startCheckTime = i
                            startTime(i)
                        }
                    }
                } else {
                    for (i in 0..23) {
                        if (tab?.position == i) {
                            startCheckTime = i
                            startTime(i)
                        }
                    }
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
        view.end_tab.addOnTabSelectedListener(object  : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (!endOneCheckTime) {
                    endOneCheckTime = true
                    for (i in 0..23) {
                        if (tab?.position == i) {
                            endTime(i)
                        }
                    }
                }else {
                    for (i in 0..23) {
                        if (tab?.position == i) {
                            endTime(i)
                        }
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
        view.time_di_request.setOnClickListener {
            if (!startOneCheckTime) {
                startTime(0)
            }
            itemClick(30)
            dialog?.dismiss()
            /*if (!startOneCheckTime) {
                startTime(0)
            }
            if (!endOneCheckTime) {
                endTime(0)
            }

            if (startCheckTime >= endCheckTime) {
                dialog?.dismiss()
            }*/
        }

    }
}