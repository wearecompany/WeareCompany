package com.weare.wearecompany.ui.listcontainer.studio

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.weare.wearecompany.R
import com.weare.wearecompany.databinding.BottomDialogStudioBinding
import com.weare.wearecompany.ui.base.BaseFragment
import com.weare.wearecompany.ui.listcontainer.model.ModelFragment

class StudioCategoryFragment : BaseFragment<BottomDialogStudioBinding>(
    R.layout.bottom_dialog_studio
), View.OnClickListener {

    private lateinit var posttext: TextView
    private val viewmodel: StudioViewModel by activityViewModels()
    var click_list = ArrayList<Int>()
    private var clicknum = -1

    override fun onCreate() {
        click_list = viewmodel.getCategory()
        bindingSetup()
        dataSetup()
    }

    fun bindingSetup() {
        mViewDataBinding.btnAllStudioCaText0.setOnClickListener(this)
        mViewDataBinding.btnAllStudioCaText1.setOnClickListener(this)
        mViewDataBinding.btnAllStudioCaText2.setOnClickListener(this)
        mViewDataBinding.btnAllStudioCaText3.setOnClickListener(this)
        mViewDataBinding.btnAllStudioCaText4.setOnClickListener(this)
        mViewDataBinding.btnAllStudioCaText5.setOnClickListener(this)
        mViewDataBinding.btnAllStudioCaText6.setOnClickListener(this)
        mViewDataBinding.btnAllStudioCaText7.setOnClickListener(this)
        mViewDataBinding.btnAllStudioCaText8.setOnClickListener(this)
        mViewDataBinding.btnAllStudioCaText9.setOnClickListener(this)
        mViewDataBinding.btnAllStudioCaText10.setOnClickListener(this)
        mViewDataBinding.btnAllStudioCaText11.setOnClickListener(this)
        mViewDataBinding.btnAllStudioCaText12.setOnClickListener(this)
        mViewDataBinding.btnAllStudioCaText13.setOnClickListener(this)

    }

    fun dataSetup() {
        if (click_list.size != 0) {
            for (i in click_list) {
                when (i) {
                    0 -> listOn(mViewDataBinding.btnAllStudioCaText0, 0)
                    1 -> listOn(mViewDataBinding.btnAllStudioCaText1, 1)
                    2 -> listOn(mViewDataBinding.btnAllStudioCaText2, 2)
                    3 -> listOn(mViewDataBinding.btnAllStudioCaText3, 3)
                    4 -> listOn(mViewDataBinding.btnAllStudioCaText4, 4)
                    5 -> listOn(mViewDataBinding.btnAllStudioCaText5, 5)
                    6 -> listOn(mViewDataBinding.btnAllStudioCaText6, 6)
                    7 -> listOn(mViewDataBinding.btnAllStudioCaText7, 7)
                    8 -> listOn(mViewDataBinding.btnAllStudioCaText8, 8)
                    9 -> listOn(mViewDataBinding.btnAllStudioCaText9, 9)
                    10 -> listOn(mViewDataBinding.btnAllStudioCaText10, 10)
                    11 -> listOn(mViewDataBinding.btnAllStudioCaText11, 11)
                    12 -> listOn(mViewDataBinding.btnAllStudioCaText12, 12)
                    13 -> listOn(mViewDataBinding.btnAllStudioCaText13, 13)
                }
            }

        }
    }

    private fun listOn(text: TextView, num: Int) {
        clicknum = num
        text.setTextColor(Color.parseColor("#6d34f3"))
        text.setBackgroundResource(R.drawable.all_ca_background_on)
    }

    private fun listReset(text: TextView, num: Int) {
        text.setTextColor(Color.parseColor("#cbcbcb"))
        text.setBackgroundResource(R.drawable.all_ca_background_off)
        clicknum = num
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_all_studio_ca_text_0 -> {
                if (click_list.size != 0) {
                    if (click_list.size >= 3) {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (0 == click_list[i - 1]) {
                                viewmodel.removeCategory(i)
                                listReset(mViewDataBinding.btnAllStudioCaText0, 0)
                                return
                            }
                        }
                    } else {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (0 == click_list[i - 1]) {
                                viewmodel.removeCategory(i)
                                listReset(mViewDataBinding.btnAllStudioCaText0, 0)
                                return
                            }
                        }
                        viewmodel.addCategory(0)
                        listOn(mViewDataBinding.btnAllStudioCaText0, 0)
                    }

                } else {
                    viewmodel.addCategory(0)
                    listOn(mViewDataBinding.btnAllStudioCaText0, 0)
                }
            }
            R.id.btn_all_studio_ca_text_1 -> {
                if (click_list.size != 0) {
                    if (click_list.size >= 3) {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (1 == click_list[i - 1]) {
                                viewmodel.removeCategory(i)
                                listReset(mViewDataBinding.btnAllStudioCaText1, 1)
                                return
                            }
                        }
                    } else {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (1 == click_list[i - 1]) {
                                viewmodel.removeCategory(i)
                                listReset(mViewDataBinding.btnAllStudioCaText1, 1)
                                return
                            }
                        }
                        viewmodel.addCategory(1)
                        listOn(mViewDataBinding.btnAllStudioCaText1, 1)
                    }

                } else {
                    viewmodel.addCategory(1)
                    listOn(mViewDataBinding.btnAllStudioCaText1, 1)
                }
            }
            R.id.btn_all_studio_ca_text_2 -> {
                if (click_list.size != 0) {
                    if (click_list.size >= 3) {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (2 == click_list[i - 1]) {
                                viewmodel.removeCategory(i)
                                listReset(mViewDataBinding.btnAllStudioCaText2, 2)
                                return
                            }
                        }
                    } else {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (2 == click_list[i - 1]) {
                                viewmodel.removeCategory(i)
                                listReset(mViewDataBinding.btnAllStudioCaText2, 2)
                                return
                            }
                        }
                        viewmodel.addCategory(2)
                        listOn(mViewDataBinding.btnAllStudioCaText2, 2)
                    }

                } else {
                    viewmodel.addCategory(2)
                    listOn(mViewDataBinding.btnAllStudioCaText2, 2)
                }
            }
            R.id.btn_all_studio_ca_text_3 -> {
                if (click_list.size != 0) {
                    if (click_list.size >= 3) {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (3 == click_list[i - 1]) {
                                viewmodel.removeCategory(i)
                                listReset(mViewDataBinding.btnAllStudioCaText3, 3)
                                return
                            }
                        }
                    } else {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (3 == click_list[i - 1]) {
                                viewmodel.removeCategory(i)
                                listReset(mViewDataBinding.btnAllStudioCaText3, 3)
                                return
                            }
                        }
                        viewmodel.addCategory(3)
                        listOn(mViewDataBinding.btnAllStudioCaText3, 3)
                    }

                } else {
                    viewmodel.addCategory(3)
                    listOn(mViewDataBinding.btnAllStudioCaText3, 3)
                }
            }
            R.id.btn_all_studio_ca_text_4 -> {
                if (click_list.size != 0) {
                    if (click_list.size >= 3) {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (4 == click_list[i - 1]) {
                                viewmodel.removeCategory(i)
                                listReset(mViewDataBinding.btnAllStudioCaText4, 4)
                                return
                            }
                        }
                    } else {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (4 == click_list[i - 1]) {
                                viewmodel.removeCategory(i)
                                listReset(mViewDataBinding.btnAllStudioCaText4, 4)
                                return
                            }
                        }
                        viewmodel.addCategory(4)
                        listOn(mViewDataBinding.btnAllStudioCaText4, 4)
                    }

                } else {
                    viewmodel.addCategory(4)
                    listOn(mViewDataBinding.btnAllStudioCaText4, 4)
                }
            }
            R.id.btn_all_studio_ca_text_5 -> {
                if (click_list.size != 0) {
                    if (click_list.size >= 3) {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (5 == click_list[i - 1]) {
                                viewmodel.removeCategory(i)
                                listReset(mViewDataBinding.btnAllStudioCaText5, 5)
                                return
                            }
                        }
                    } else {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (5 == click_list[i - 1]) {
                                viewmodel.removeCategory(i)
                                listReset(mViewDataBinding.btnAllStudioCaText5, 5)
                                return
                            }
                        }
                        viewmodel.addCategory(5)
                        listOn(mViewDataBinding.btnAllStudioCaText5, 5)
                    }

                } else {
                    viewmodel.addCategory(5)
                    listOn(mViewDataBinding.btnAllStudioCaText5, 5)
                }
            }
            R.id.btn_all_studio_ca_text_6 -> {
                if (click_list.size != 0) {
                    if (click_list.size >= 3) {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (6 == click_list[i - 1]) {
                                viewmodel.removeCategory(i)
                                listReset(mViewDataBinding.btnAllStudioCaText6, 6)
                                return
                            }
                        }
                    } else {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (6 == click_list[i - 1]) {
                                viewmodel.removeCategory(i)
                                listReset(mViewDataBinding.btnAllStudioCaText6, 6)
                                return
                            }
                        }
                        viewmodel.addCategory(6)
                        listOn(mViewDataBinding.btnAllStudioCaText6, 6)
                    }

                } else {
                    viewmodel.addCategory(6)
                    listOn(mViewDataBinding.btnAllStudioCaText6, 6)
                }
            }
            R.id.btn_all_studio_ca_text_7 -> {
                if (click_list.size != 0) {
                    if (click_list.size >= 3) {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (7 == click_list[i - 1]) {
                                viewmodel.removeCategory(i)
                                listReset(mViewDataBinding.btnAllStudioCaText7, 7)
                                return
                            }
                        }
                    } else {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (7 == click_list[i - 1]) {
                                viewmodel.removeCategory(i)
                                listReset(mViewDataBinding.btnAllStudioCaText7, 7)
                                return
                            }
                        }
                        viewmodel.addCategory(7)
                        listOn(mViewDataBinding.btnAllStudioCaText7, 7)
                    }

                } else {
                    viewmodel.addCategory(7)
                    listOn(mViewDataBinding.btnAllStudioCaText7, 7)
                }
            }
            R.id.btn_all_studio_ca_text_8 -> {
                if (click_list.size != 0) {
                    if (click_list.size >= 3) {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (8 == click_list[i - 1]) {
                                viewmodel.removeCategory(i)
                                listReset(mViewDataBinding.btnAllStudioCaText8, 8)
                                return
                            }
                        }
                    } else {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (8 == click_list[i - 1]) {
                                viewmodel.removeCategory(i)
                                listReset(mViewDataBinding.btnAllStudioCaText8, 8)
                                return
                            }
                        }
                        viewmodel.addCategory(8)
                        listOn(mViewDataBinding.btnAllStudioCaText8, 8)
                    }

                } else {
                    viewmodel.addCategory(8)
                    listOn(mViewDataBinding.btnAllStudioCaText8, 8)
                }
            }
            R.id.btn_all_studio_ca_text_9 -> {
                if (click_list.size != 0) {
                    if (click_list.size >= 3) {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (9 == click_list[i - 1]) {
                                viewmodel.removeCategory(i)
                                listReset(mViewDataBinding.btnAllStudioCaText9, 9)
                                return
                            }
                        }
                    } else {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (9 == click_list[i - 1]) {
                                viewmodel.removeCategory(i)
                                listReset(mViewDataBinding.btnAllStudioCaText9, 9)
                                return
                            }
                        }
                        viewmodel.addCategory(9)
                        listOn(mViewDataBinding.btnAllStudioCaText9, 9)
                    }

                } else {
                    viewmodel.addCategory(9)
                    listOn(mViewDataBinding.btnAllStudioCaText9, 9)
                }
            }
            R.id.btn_all_studio_ca_text_10 -> {
                if (click_list.size != 0) {
                    if (click_list.size >= 3) {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (10 == click_list[i - 1]) {
                                viewmodel.removeCategory(i)
                                listReset(mViewDataBinding.btnAllStudioCaText10, 10)
                                return
                            }
                        }
                    } else {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (10 == click_list[i - 1]) {
                                viewmodel.removeCategory(i)
                                listReset(mViewDataBinding.btnAllStudioCaText10, 10)
                                return
                            }
                        }
                        viewmodel.addCategory(10)
                        listOn(mViewDataBinding.btnAllStudioCaText10, 10)
                    }

                } else {
                    viewmodel.addCategory(10)
                    listOn(mViewDataBinding.btnAllStudioCaText10, 10)
                }
            }
            R.id.btn_all_studio_ca_text_11 -> {
                if (click_list.size != 0) {
                    if (click_list.size >= 3) {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (11 == click_list[i - 1]) {
                                viewmodel.removeCategory(i)
                                listReset(mViewDataBinding.btnAllStudioCaText11, 11)
                                return
                            }
                        }
                    } else {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (11 == click_list[i - 1]) {
                                viewmodel.removeCategory(i)
                                listReset(mViewDataBinding.btnAllStudioCaText11, 11)
                                return
                            }
                        }
                        viewmodel.addCategory(11)
                        listOn(mViewDataBinding.btnAllStudioCaText11, 11)
                    }

                } else {
                    viewmodel.addCategory(11)
                    listOn(mViewDataBinding.btnAllStudioCaText11, 11)
                }
            }
            R.id.btn_all_studio_ca_text_12 -> {
                if (click_list.size != 0) {
                    if (click_list.size >= 3) {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (12 == click_list[i - 1]) {
                                viewmodel.removeCategory(i)
                                listReset(mViewDataBinding.btnAllStudioCaText12, 12)
                                return
                            }
                        }
                    } else {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (12 == click_list[i - 1]) {
                                viewmodel.removeCategory(i)
                                listReset(mViewDataBinding.btnAllStudioCaText12, 12)
                                return
                            }
                        }
                        viewmodel.addCategory(12)
                        listOn(mViewDataBinding.btnAllStudioCaText12, 12)
                    }

                } else {
                    viewmodel.addCategory(12)
                    listOn(mViewDataBinding.btnAllStudioCaText12, 12)
                }
            }
            R.id.btn_all_studio_ca_text_13 -> {
                if (click_list.size != 0) {
                    if (click_list.size >= 3) {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (13 == click_list[i - 1]) {
                                viewmodel.removeCategory(i)
                                listReset(mViewDataBinding.btnAllStudioCaText13, 13)
                                return
                            }
                        }
                    } else {
                        val i1 = 1
                        for (i in i1..click_list.size) {
                            if (13 == click_list[i - 1]) {
                                viewmodel.removeCategory(i)
                                listReset(mViewDataBinding.btnAllStudioCaText13, 13)
                                return
                            }
                        }
                        viewmodel.addCategory(13)
                        listOn(mViewDataBinding.btnAllStudioCaText13, 13)
                    }

                } else {
                    viewmodel.addCategory(13)
                    listOn(mViewDataBinding.btnAllStudioCaText13, 13)
                }
            }
        }
    }
}