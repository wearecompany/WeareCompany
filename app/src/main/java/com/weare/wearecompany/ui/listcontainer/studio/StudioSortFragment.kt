package com.weare.wearecompany.ui.listcontainer.studio

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.weare.wearecompany.R
import com.weare.wearecompany.databinding.BottomDialogAllSortBinding
import com.weare.wearecompany.ui.base.BaseFragment

class StudioSortFragment : BaseFragment<BottomDialogAllSortBinding>(
    R.layout.bottom_dialog_all_sort
), View.OnClickListener {

    private var setupSort = 0
    private lateinit var posttext: TextView
    private val viewmodel: StudioViewModel by activityViewModels()
    private var clicknum = -1
    override fun onCreate() {
        setupSort = viewmodel.getSort()
        bindingSetup()
        dataSetup()
    }

    fun bindingSetup() {
        mViewDataBinding.btnAllModelSoText0.setOnClickListener(this)
        mViewDataBinding.btnAllModelSoText1.setOnClickListener(this)
        mViewDataBinding.btnAllModelSoText2.setOnClickListener(this)
        mViewDataBinding.btnAllModelSoText3.setOnClickListener(this)
        mViewDataBinding.btnAllModelSoText4.setOnClickListener(this)
    }

    fun dataSetup() {
        when(setupSort) {
            0 -> listOn(mViewDataBinding.btnAllModelSoText0, 0)
            1 -> listOn(mViewDataBinding.btnAllModelSoText1, 1)
            2 -> listOn(mViewDataBinding.btnAllModelSoText2, 2)
            3 -> listOn(mViewDataBinding.btnAllModelSoText3, 3)
            4 -> listOn(mViewDataBinding.btnAllModelSoText4, 4)
        }
    }

    private fun listOn(text: TextView, num: Int) {
        posttext = text
        clicknum = num
        viewmodel.clickSort(num)
        text.setTextColor(Color.parseColor("#6d34f3"))
        text.setBackgroundResource(R.drawable.all_ca_background_on)
    }

    private fun listOff(text: TextView, num: Int) {
        posttext.setTextColor(Color.parseColor("#cbcbcb"))
        posttext.setBackgroundResource(R.drawable.all_ca_background_off)
        posttext = text
        clicknum = num
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_all_model_so_text_0 -> {
                listOff(mViewDataBinding.btnAllModelSoText0, 0)
                listOn(mViewDataBinding.btnAllModelSoText0, 0)
            }
            R.id.btn_all_model_so_text_1 -> {
                listOff(mViewDataBinding.btnAllModelSoText1, 1)
                listOn(mViewDataBinding.btnAllModelSoText1, 1)
            }
            R.id.btn_all_model_so_text_2 -> {
                listOff(mViewDataBinding.btnAllModelSoText2, 2)
                listOn(mViewDataBinding.btnAllModelSoText2, 2)
            }
            R.id.btn_all_model_so_text_3 -> {
                listOff(mViewDataBinding.btnAllModelSoText3, 3)
                listOn(mViewDataBinding.btnAllModelSoText3, 3)
            }
            R.id.btn_all_model_so_text_4 -> {
                listOff(mViewDataBinding.btnAllModelSoText4, 4)
                listOn(mViewDataBinding.btnAllModelSoText4, 4)
            }
        }
    }
}