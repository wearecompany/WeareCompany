package com.weare.wearecompany.ui.listcontainer.photographer

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.weare.wearecompany.R
import com.weare.wearecompany.databinding.BottomDialogAllLocationBinding
import com.weare.wearecompany.ui.base.BaseFragment

class PhotoLocationFragment : BaseFragment<BottomDialogAllLocationBinding>(
    R.layout.bottom_dialog_all_location
), View.OnClickListener {

    private val viewmodel: PhotoViewModel by activityViewModels()

    var click_list = ArrayList<Int>()
    private var clicknum = -1

    override fun onCreate() {
        click_list = viewmodel.getLocation()
        bindingSetup()
        dataSetup()
    }

    fun bindingSetup() {
        mViewDataBinding.btnAllModelLoText0.setOnClickListener(this)
        mViewDataBinding.btnAllModelLoText1.setOnClickListener(this)
        mViewDataBinding.btnAllModelLoText2.setOnClickListener(this)
        mViewDataBinding.btnAllModelLoText3.setOnClickListener(this)
        mViewDataBinding.btnAllModelLoText4.setOnClickListener(this)
        mViewDataBinding.btnAllModelLoText5.setOnClickListener(this)
        mViewDataBinding.btnAllModelLoText6.setOnClickListener(this)
        mViewDataBinding.btnAllModelLoText7.setOnClickListener(this)
        mViewDataBinding.btnAllModelLoText8.setOnClickListener(this)
        mViewDataBinding.btnAllModelLoText9.setOnClickListener(this)
        mViewDataBinding.btnAllModelLoText10.setOnClickListener(this)
        mViewDataBinding.btnAllModelLoText11.setOnClickListener(this)
        mViewDataBinding.btnAllModelLoText12.setOnClickListener(this)
        mViewDataBinding.btnAllModelLoText13.setOnClickListener(this)
        mViewDataBinding.btnAllModelLoText14.setOnClickListener(this)
        mViewDataBinding.btnAllModelLoText15.setOnClickListener(this)
    }

    fun dataSetup() {

        if (click_list.size != 0) {
            for (i in click_list) {
                when (i) {
                    0 -> locationOn(mViewDataBinding.btnAllModelLoText0, 0)
                    1 -> locationOn(mViewDataBinding.btnAllModelLoText1, 1)
                    2 -> locationOn(mViewDataBinding.btnAllModelLoText2, 2)
                    3 -> locationOn(mViewDataBinding.btnAllModelLoText3, 3)
                    4 -> locationOn(mViewDataBinding.btnAllModelLoText4, 4)
                    5 -> locationOn(mViewDataBinding.btnAllModelLoText5, 5)
                    6 -> locationOn(mViewDataBinding.btnAllModelLoText6, 6)
                    7 -> locationOn(mViewDataBinding.btnAllModelLoText7, 7)
                    8 -> locationOn(mViewDataBinding.btnAllModelLoText8, 8)
                    9 -> locationOn(mViewDataBinding.btnAllModelLoText9, 9)
                    10 -> locationOn(mViewDataBinding.btnAllModelLoText10, 10)
                    11 -> locationOn(mViewDataBinding.btnAllModelLoText11, 11)
                    12 -> locationOn(mViewDataBinding.btnAllModelLoText12, 12)
                    13 -> locationOn(mViewDataBinding.btnAllModelLoText13, 13)
                    14 -> locationOn(mViewDataBinding.btnAllModelLoText14, 14)
                    15 -> locationOn(mViewDataBinding.btnAllModelLoText15, 15)
                }
                clicknum = i
            }
        }
    }

    private fun locationOn(text: TextView, num: Int) {
        clicknum = num
        text.setTextColor(Color.parseColor("#6d34f3"))
        text.setBackgroundResource(R.drawable.all_ca_background_on)
    }

    private fun locationReset(text: TextView, num: Int) {
        text.setTextColor(Color.parseColor("#cbcbcb"))
        text.setBackgroundResource(R.drawable.all_ca_background_off)
        clicknum = num
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_all_model_lo_text_0 -> {
                if (click_list.size != 0) {
                    if (click_list.size >= 3) {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (0 == click_list[i - 1]) {
                                viewmodel.removeLocation(i)
                                //click_list.removeAt(i - 1)
                                locationReset(mViewDataBinding.btnAllModelLoText0, 0)
                                return
                            }
                        }
                    } else {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (0 == click_list[i - 1]) {
                                viewmodel.removeLocation(i)
                                //click_list.removeAt(i - 1)
                                locationReset(mViewDataBinding.btnAllModelLoText0, 0)
                                return
                            }
                        }
                        viewmodel.addLocation(0)
                        //click_list.add(0)
                        locationOn(mViewDataBinding.btnAllModelLoText0, 0)
                    }

                } else {
                    viewmodel.addLocation(0)
                    //click_list.add(0)
                    locationOn(mViewDataBinding.btnAllModelLoText0, 0)
                }
            }
            R.id.btn_all_model_lo_text_1 -> {
                if (click_list.size != 0) {
                    if (click_list.size >= 3) {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (1 == click_list[i - 1]) {
                                viewmodel.removeLocation(i)
                                //click_list.removeAt(i - 1)
                                locationReset(mViewDataBinding.btnAllModelLoText1, 1)
                                return
                            }
                        }
                    } else {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (1 == click_list[i - 1]) {
                                viewmodel.removeLocation(i)
                                //click_list.removeAt(i - 1)
                                locationReset(mViewDataBinding.btnAllModelLoText1, 1)
                                return
                            }
                        }
                        viewmodel.addLocation(1)
                        //click_list.add(1)
                        locationOn(mViewDataBinding.btnAllModelLoText1, 1)
                    }

                } else {
                    viewmodel.addLocation(1)
                    //click_list.add(1)
                    locationOn(mViewDataBinding.btnAllModelLoText1, 1)
                }
            }
            R.id.btn_all_model_lo_text_2 -> {
                if (click_list.size != 0) {
                    if (click_list.size >= 3) {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (2 == click_list[i - 1]) {
                                viewmodel.removeLocation(i)
                                //click_list.removeAt(i - 1)
                                locationReset(mViewDataBinding.btnAllModelLoText2, 2)
                                return
                            }
                        }
                    } else {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (2 == click_list[i - 1]) {
                                viewmodel.removeLocation(i)
                                //click_list.removeAt(i - 1)
                                locationReset(mViewDataBinding.btnAllModelLoText2, 2)
                                return
                            }
                        }
                        viewmodel.addLocation(2)
                        //click_list.add(2)
                        locationOn(mViewDataBinding.btnAllModelLoText2, 2)
                    }

                } else {
                    viewmodel.addLocation(2)
                    //click_list.add(2)
                    locationOn(mViewDataBinding.btnAllModelLoText2, 2)
                }
            }
            R.id.btn_all_model_lo_text_3 -> {
                if (click_list.size != 0) {
                    if (click_list.size >= 3) {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (3 == click_list[i - 1]) {
                                viewmodel.removeLocation(i)
                                //click_list.removeAt(i - 1)
                                locationReset(mViewDataBinding.btnAllModelLoText3, 3)
                                return
                            }
                        }
                    } else {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (3 == click_list[i - 1]) {
                                viewmodel.removeLocation(i)
                                //click_list.removeAt(i - 1)
                                locationReset(mViewDataBinding.btnAllModelLoText3, 3)
                                return
                            }
                        }
                        viewmodel.addLocation(3)
                        //click_list.add(3)
                        locationOn(mViewDataBinding.btnAllModelLoText3, 3)
                    }

                } else {
                    viewmodel.addLocation(3)
                    //click_list.add(3)
                    locationOn(mViewDataBinding.btnAllModelLoText3, 3)
                }
            }
            R.id.btn_all_model_lo_text_4 -> {
                if (click_list.size != 0) {
                    if (click_list.size >= 3) {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (4 == click_list[i - 1]) {
                                viewmodel.removeLocation(i)
                                //click_list.removeAt(i - 1)
                                locationReset(mViewDataBinding.btnAllModelLoText4, 4)
                                return
                            }
                        }
                    } else {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (4 == click_list[i - 1]) {
                                viewmodel.removeLocation(i)
                                //click_list.removeAt(i - 1)
                                locationReset(mViewDataBinding.btnAllModelLoText4, 4)
                                return
                            }
                        }
                        viewmodel.addLocation(4)
                        //click_list.add(4)
                        locationOn(mViewDataBinding.btnAllModelLoText4, 4)
                    }

                } else {
                    viewmodel.addLocation(4)
                    //click_list.add(4)
                    locationOn(mViewDataBinding.btnAllModelLoText4, 4)
                }
            }
            R.id.btn_all_model_lo_text_5 -> {
                if (click_list.size != 0) {
                    if (click_list.size >= 3) {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (5 == click_list[i - 1]) {
                                viewmodel.removeLocation(i)
                                //click_list.removeAt(i - 1)
                                locationReset(mViewDataBinding.btnAllModelLoText5, 5)
                                return
                            }
                        }
                    } else {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (5 == click_list[i - 1]) {
                                viewmodel.removeLocation(i)
                                //click_list.removeAt(i - 1)
                                locationReset(mViewDataBinding.btnAllModelLoText5, 5)
                                return
                            }
                        }
                        viewmodel.addLocation(5)
                        //click_list.add(5)
                        locationOn(mViewDataBinding.btnAllModelLoText5, 5)
                    }

                } else {
                    viewmodel.addLocation(5)
                    //click_list.add(5)
                    locationOn(mViewDataBinding.btnAllModelLoText5, 5)
                }
            }
            R.id.btn_all_model_lo_text_6 -> {
                if (click_list.size != 0) {
                    if (click_list.size >= 3) {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (6 == click_list[i - 1]) {
                                viewmodel.removeLocation(i)
                                //click_list.removeAt(i - 1)
                                locationReset(mViewDataBinding.btnAllModelLoText6, 6)
                                return
                            }
                        }
                    } else {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (6 == click_list[i - 1]) {
                                viewmodel.removeLocation(i)
                                //click_list.removeAt(i - 1)
                                locationReset(mViewDataBinding.btnAllModelLoText6, 6)
                                return
                            }
                        }
                        viewmodel.addLocation(6)
                        //click_list.add(6)
                        locationOn(mViewDataBinding.btnAllModelLoText6, 6)
                    }

                } else {
                    viewmodel.addLocation(6)
                    //click_list.add(6)
                    locationOn(mViewDataBinding.btnAllModelLoText6, 6)
                }
            }
            R.id.btn_all_model_lo_text_7 -> {
                if (click_list.size != 0) {
                    if (click_list.size >= 3) {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (7 == click_list[i - 1]) {
                                viewmodel.removeLocation(i)
                                //click_list.removeAt(i - 1)
                                locationReset(mViewDataBinding.btnAllModelLoText7, 7)
                                return
                            }
                        }
                    } else {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (7 == click_list[i - 1]) {
                                viewmodel.removeLocation(i)
                                //click_list.removeAt(i - 1)
                                locationReset(mViewDataBinding.btnAllModelLoText7, 7)
                                return
                            }
                        }
                        viewmodel.addLocation(7)
                        //click_list.add(7)
                        locationOn(mViewDataBinding.btnAllModelLoText7, 7)
                    }

                } else {
                    viewmodel.addLocation(7)
                    //click_list.add(7)
                    locationOn(mViewDataBinding.btnAllModelLoText7, 7)
                }
            }
            R.id.btn_all_model_lo_text_8 -> {
                if (click_list.size != 0) {
                    if (click_list.size >= 3) {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (8 == click_list[i - 1]) {
                                viewmodel.removeLocation(i)
                                //click_list.removeAt(i - 1)
                                locationReset(mViewDataBinding.btnAllModelLoText8, 8)
                                return
                            }
                        }
                    } else {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (8 == click_list[i - 1]) {
                                viewmodel.removeLocation(i)
                                //click_list.removeAt(i - 1)
                                locationReset(mViewDataBinding.btnAllModelLoText8, 8)
                                return
                            }
                        }
                        viewmodel.addLocation(8)
                        //click_list.add(8)
                        locationOn(mViewDataBinding.btnAllModelLoText8, 8)
                    }

                } else {
                    viewmodel.addLocation(8)
                    //click_list.add(8)
                    locationOn(mViewDataBinding.btnAllModelLoText8, 8)
                }
            }
            R.id.btn_all_model_lo_text_9 -> {
                if (click_list.size != 0) {
                    if (click_list.size >= 3) {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (9 == click_list[i - 1]) {
                                viewmodel.removeLocation(i)
                                //click_list.removeAt(i - 1)
                                locationReset(mViewDataBinding.btnAllModelLoText9, 9)
                                return
                            }
                        }
                    } else {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (9 == click_list[i - 1]) {
                                viewmodel.removeLocation(i)
                                //click_list.removeAt(i - 1)
                                locationReset(mViewDataBinding.btnAllModelLoText9, 9)
                                return
                            }
                        }
                        viewmodel.addLocation(9)
                        //click_list.add(9)
                        locationOn(mViewDataBinding.btnAllModelLoText9, 9)
                    }

                } else {
                    viewmodel.addLocation(9)
                    //click_list.add(9)
                    locationOn(mViewDataBinding.btnAllModelLoText9, 9)
                }
            }
            R.id.btn_all_model_lo_text_10 -> {
                if (click_list.size != 0) {
                    if (click_list.size >= 3) {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (10 == click_list[i - 1]) {
                                viewmodel.removeLocation(i)
                                //click_list.removeAt(i - 1)
                                locationReset(mViewDataBinding.btnAllModelLoText10, 10)
                                return
                            }
                        }
                    } else {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (10 == click_list[i - 1]) {
                                viewmodel.removeLocation(i)
                                //click_list.removeAt(i - 1)
                                locationReset(mViewDataBinding.btnAllModelLoText10, 10)
                                return
                            }
                        }
                        viewmodel.addLocation(10)
                        //click_list.add(10)
                        locationOn(mViewDataBinding.btnAllModelLoText10, 10)
                    }

                } else {
                    viewmodel.addLocation(10)
                    //click_list.add(10)
                    locationOn(mViewDataBinding.btnAllModelLoText10, 10)
                }
            }
            R.id.btn_all_model_lo_text_11 -> {
                if (click_list.size != 0) {
                    if (click_list.size >= 3) {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (11 == click_list[i - 1]) {
                                viewmodel.removeLocation(i)
                                //click_list.removeAt(i - 1)
                                locationReset(mViewDataBinding.btnAllModelLoText11, 11)
                                return
                            }
                        }
                    } else {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (11 == click_list[i - 1]) {
                                viewmodel.removeLocation(i)
                                //click_list.removeAt(i - 1)
                                locationReset(mViewDataBinding.btnAllModelLoText11, 11)
                                return
                            }
                        }
                        viewmodel.addLocation(11)
                        //click_list.add(11)
                        locationOn(mViewDataBinding.btnAllModelLoText11, 11)
                    }

                } else {
                    viewmodel.addLocation(11)
                    //click_list.add(11)
                    locationOn(mViewDataBinding.btnAllModelLoText11, 11)
                }
            }
            R.id.btn_all_model_lo_text_12 -> {
                if (click_list.size != 0) {
                    if (click_list.size >= 3) {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (12 == click_list[i - 1]) {
                                viewmodel.removeLocation(i)
                                //click_list.removeAt(i - 1)
                                locationReset(mViewDataBinding.btnAllModelLoText12, 12)
                                return
                            }
                        }
                    } else {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (12 == click_list[i - 1]) {
                                viewmodel.removeLocation(i)
                                //click_list.removeAt(i - 1)
                                locationReset(mViewDataBinding.btnAllModelLoText12, 12)
                                return
                            }
                        }
                        viewmodel.addLocation(12)
                        //click_list.add(12)
                        locationOn(mViewDataBinding.btnAllModelLoText12, 12)
                    }

                } else {
                    viewmodel.addLocation(12)
                    //click_list.add(12)
                    locationOn(mViewDataBinding.btnAllModelLoText12, 12)
                }
            }
            R.id.btn_all_model_lo_text_13 -> {
                if (click_list.size != 0) {
                    if (click_list.size >= 3) {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (13 == click_list[i - 1]) {
                                viewmodel.removeLocation(i)
                                //click_list.removeAt(i - 1)
                                locationReset(mViewDataBinding.btnAllModelLoText13, 13)
                                return
                            }
                        }
                    } else {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (13 == click_list[i - 1]) {
                                viewmodel.removeLocation(i)
                                //click_list.removeAt(i - 1)
                                locationReset(mViewDataBinding.btnAllModelLoText13, 13)
                                return
                            }
                        }
                        viewmodel.addLocation(13)
                        //click_list.add(13)
                        locationOn(mViewDataBinding.btnAllModelLoText13, 13)
                    }

                } else {
                    viewmodel.addLocation(13)
                    //click_list.add(13)
                    locationOn(mViewDataBinding.btnAllModelLoText13, 13)
                }
            }
            R.id.btn_all_model_lo_text_14 -> {
                if (click_list.size != 0) {
                    if (click_list.size >= 3) {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (14 == click_list[i - 1]) {
                                viewmodel.removeLocation(i)
                                //click_list.removeAt(i - 1)
                                locationReset(mViewDataBinding.btnAllModelLoText14, 14)
                                return
                            }
                        }
                    } else {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (14 == click_list[i - 1]) {
                                viewmodel.removeLocation(i)
                                //click_list.removeAt(i - 1)
                                locationReset(mViewDataBinding.btnAllModelLoText14, 14)
                                return
                            }
                        }
                        viewmodel.addLocation(14)
                        //click_list.add(14)
                        locationOn(mViewDataBinding.btnAllModelLoText14, 14)
                    }

                } else {
                    viewmodel.addLocation(14)
                    //click_list.add(14)
                    locationOn(mViewDataBinding.btnAllModelLoText14, 14)
                }
            }
            R.id.btn_all_model_lo_text_15 -> {
                if (click_list.size != 0) {
                    if (click_list.size >= 3) {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (15 == click_list[i - 1]) {
                                viewmodel.removeLocation(i)
                                //click_list.removeAt(i - 1)
                                locationReset(mViewDataBinding.btnAllModelLoText15, 15)
                                return
                            }
                        }
                    } else {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (15 == click_list[i - 1]) {
                                viewmodel.removeLocation(i)
                                //click_list.removeAt(i - 1)
                                locationReset(mViewDataBinding.btnAllModelLoText15, 15)
                                return
                            }
                        }
                        viewmodel.addLocation(15)
                        //click_list.add(15)
                        locationOn(mViewDataBinding.btnAllModelLoText15, 15)
                    }

                } else {
                    viewmodel.addLocation(15)
                    //click_list.add(15)
                    locationOn(mViewDataBinding.btnAllModelLoText15, 15)
                }
            }
        }
    }
}