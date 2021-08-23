package com.weare.wearecompany.ui.listcontainer.trip

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.weare.wearecompany.R
import com.weare.wearecompany.databinding.BottomDialogTripBinding
import com.weare.wearecompany.ui.base.BaseFragment



class TripCategoryFragment: BaseFragment<BottomDialogTripBinding>(
    R.layout.bottom_dialog_trip
),View.OnClickListener{

    private lateinit var posttext: TextView
    private val viewmodel: TripViewModel by activityViewModels()
    private var clicknum = -1

    private var setupCategory = -1
    var oknum:Int = 0


    override fun onCreate() {
        setupCategory = viewmodel.getCategory()
        bindingSetup()
        dataSetup()
    }

    fun bindingSetup() {
        mViewDataBinding.btnAllTripCaText0.setOnClickListener(this)
        mViewDataBinding.btnAllTripCaText1.setOnClickListener(this)
        mViewDataBinding.btnAllTripCaText2.setOnClickListener(this)
    }

    fun dataSetup() {
        if (setupCategory != -1) {
            when(setupCategory) {
                0 -> listOn(mViewDataBinding.btnAllTripCaText0, 0)
                1 -> listOn(mViewDataBinding.btnAllTripCaText1, 1)
                2 -> listOn(mViewDataBinding.btnAllTripCaText2, 2)
            }
        }
    }

    private fun listOn(text: TextView, num:Int) {
        if (clicknum == -1) {
            posttext = text
            clicknum = num
            oknum = num
        }
        viewmodel.clickCategory(num)
        oknum = num
        text.setTextColor(Color.parseColor("#6d34f3"))
        text.setBackgroundResource(R.drawable.all_ca_background_on)
    }

    private fun listOff(text: TextView, num:Int) {
        posttext.setTextColor(Color.parseColor("#cbcbcb"))
        posttext.setBackgroundResource(R.drawable.all_ca_background_off)
        posttext = text
        clicknum = num
    }

    private fun listReset(text: TextView, num:Int) {
        text.setTextColor(Color.parseColor("#cbcbcb"))
        text.setBackgroundResource(R.drawable.all_ca_background_off)
        clicknum = num
        viewmodel.clickCategory(-1)
        oknum = num
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_all_trip_ca_text_0 -> {
                if (clicknum != -1 && clicknum != 0) {
                    listOff(mViewDataBinding.btnAllTripCaText0, 0)
                    listOn(mViewDataBinding.btnAllTripCaText0, 0)
                } else if (clicknum != -1 && clicknum == 0){
                    listReset(mViewDataBinding.btnAllTripCaText0, -1)
                } else if (clicknum != 0){
                    listOn(mViewDataBinding.btnAllTripCaText0, 0)
                }
            }
            R.id.btn_all_trip_ca_text_1 -> {
                if (clicknum != -1 && clicknum != 1) {
                    listOff(mViewDataBinding.btnAllTripCaText1, 1)
                    listOn(mViewDataBinding.btnAllTripCaText1, 1)
                } else if (clicknum != -1 && clicknum == 1){
                    listReset(mViewDataBinding.btnAllTripCaText1, -1)
                } else if (clicknum != 1){
                    listOn(mViewDataBinding.btnAllTripCaText1, 1)
                }
            }
            R.id.btn_all_trip_ca_text_2 -> {
                if (clicknum != -1 && clicknum != 2) {
                    listOff(mViewDataBinding.btnAllTripCaText2, 2)
                    listOn(mViewDataBinding.btnAllTripCaText2, 2)
                } else if (clicknum != -1 && clicknum == 2){
                    listReset(mViewDataBinding.btnAllTripCaText2, -1)
                } else if (clicknum != 2){
                    listOn(mViewDataBinding.btnAllTripCaText2, 2)
                }
            }

        }
    }
}