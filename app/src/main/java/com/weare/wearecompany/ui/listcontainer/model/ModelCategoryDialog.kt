package com.weare.wearecompany.ui.listcontainer.model

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.weare.wearecompany.R
import com.weare.wearecompany.databinding.BottomDialogModelBinding
import com.weare.wearecompany.ui.base.BaseFragment


class ModelCategoryDialog : BaseFragment<BottomDialogModelBinding>(
    R.layout.bottom_dialog_model
), View.OnClickListener {

    private lateinit var posttext: TextView
    private val viewmodel: ModelViewModel by activityViewModels()
    private var clicknum = -1

    private var setupCategory = -1
    var oknum: Int = 0
    lateinit var stringgg: String


    override fun onCreate() {
        setupCategory = viewmodel.getCategory()
        bindingSetup()
        dataSetup()
    }

    fun bindingSetup() {
        mViewDataBinding.btnAllModelCaText0.setOnClickListener(this)
        mViewDataBinding.btnAllModelCaText1.setOnClickListener(this)
        mViewDataBinding.btnAllModelCaText2.setOnClickListener(this)
        mViewDataBinding.btnAllModelCaText3.setOnClickListener(this)
        mViewDataBinding.btnAllModelCaText4.setOnClickListener(this)
        mViewDataBinding.btnAllModelCaText5.setOnClickListener(this)
        mViewDataBinding.btnAllModelCaText6.setOnClickListener(this)
        mViewDataBinding.btnAllModelCaText7.setOnClickListener(this)
        mViewDataBinding.btnAllModelCaText8.setOnClickListener(this)
        mViewDataBinding.btnAllModelCaText9.setOnClickListener(this)
        mViewDataBinding.btnAllModelCaText10.setOnClickListener(this)
        mViewDataBinding.btnAllModelCaText11.setOnClickListener(this)
        mViewDataBinding.btnAllModelCaText12.setOnClickListener(this)
        mViewDataBinding.btnAllModelCaText13.setOnClickListener(this)
        mViewDataBinding.btnAllModelCaText14.setOnClickListener(this)
        mViewDataBinding.btnAllModelCaText15.setOnClickListener(this)
        mViewDataBinding.btnAllModelCaText16.setOnClickListener(this)
        mViewDataBinding.btnAllModelCaText17.setOnClickListener(this)
        mViewDataBinding.btnAllModelCaText18.setOnClickListener(this)
        mViewDataBinding.btnAllModelCaText19.setOnClickListener(this)
        stringgg = "asdsadasd"
    }

    fun dataSetup() {
        if (setupCategory != -1) {
            when (setupCategory) {
                0 -> listOn(mViewDataBinding.btnAllModelCaText0, 0)
                1 -> listOn(mViewDataBinding.btnAllModelCaText1, 1)
                2 -> listOn(mViewDataBinding.btnAllModelCaText2, 2)
                3 -> listOn(mViewDataBinding.btnAllModelCaText3, 3)
                4 -> listOn(mViewDataBinding.btnAllModelCaText4, 4)
                5 -> listOn(mViewDataBinding.btnAllModelCaText5, 5)
                6 -> listOn(mViewDataBinding.btnAllModelCaText6, 6)
                7 -> listOn(mViewDataBinding.btnAllModelCaText7, 7)
                8 -> listOn(mViewDataBinding.btnAllModelCaText8, 8)
                9 -> listOn(mViewDataBinding.btnAllModelCaText9, 9)
                10 -> listOn(mViewDataBinding.btnAllModelCaText10, 10)
                11 -> listOn(mViewDataBinding.btnAllModelCaText11, 11)
                12 -> listOn(mViewDataBinding.btnAllModelCaText12, 12)
                13 -> listOn(mViewDataBinding.btnAllModelCaText13, 13)
                14 -> listOn(mViewDataBinding.btnAllModelCaText14, 14)
                15 -> listOn(mViewDataBinding.btnAllModelCaText15, 15)
                16 -> listOn(mViewDataBinding.btnAllModelCaText16, 16)
                17 -> listOn(mViewDataBinding.btnAllModelCaText17, 17)
                18 -> listOn(mViewDataBinding.btnAllModelCaText18, 18)
                19 -> listOn(mViewDataBinding.btnAllModelCaText19, 19)
            }
        }
    }

    private fun listOn(text: TextView, num: Int) {
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

    private fun listOff(text: TextView, num: Int) {
        posttext.setTextColor(Color.parseColor("#cbcbcb"))
        posttext.setBackgroundResource(R.drawable.all_ca_background_off)
        posttext = text
        clicknum = num
    }

    private fun listReset(text: TextView, num: Int) {
        text.setTextColor(Color.parseColor("#cbcbcb"))
        text.setBackgroundResource(R.drawable.all_ca_background_off)
        clicknum = num
        viewmodel.clickCategory(-1)
        oknum = num
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_all_model_ca_text_0 -> {
                if (clicknum != -1 && clicknum != 0) {
                    listOff(mViewDataBinding.btnAllModelCaText0, 0)
                    listOn(mViewDataBinding.btnAllModelCaText0, 0)
                } else if (clicknum != -1 && clicknum == 0) {
                    listReset(mViewDataBinding.btnAllModelCaText0, -1)
                } else if (clicknum != 0) {
                    listOn(mViewDataBinding.btnAllModelCaText0, 0)
                }
            }
            R.id.btn_all_model_ca_text_1 -> {
                if (clicknum != -1 && clicknum != 1) {
                    listOff(mViewDataBinding.btnAllModelCaText1, 1)
                    listOn(mViewDataBinding.btnAllModelCaText1, 1)
                } else if (clicknum != -1 && clicknum == 1) {
                    listReset(mViewDataBinding.btnAllModelCaText1, -1)
                } else if (clicknum != 1) {
                    listOn(mViewDataBinding.btnAllModelCaText1, 1)
                }
            }
            R.id.btn_all_model_ca_text_2 -> {
                if (clicknum != -1 && clicknum != 2) {
                    listOff(mViewDataBinding.btnAllModelCaText2, 2)
                    listOn(mViewDataBinding.btnAllModelCaText2, 2)
                } else if (clicknum != -1 && clicknum == 2) {
                    listReset(mViewDataBinding.btnAllModelCaText2, -1)
                } else if (clicknum != 2) {
                    listOn(mViewDataBinding.btnAllModelCaText2, 2)
                }
            }
            R.id.btn_all_model_ca_text_3 -> {
                if (clicknum != -1 && clicknum != 3) {
                    listOff(mViewDataBinding.btnAllModelCaText3, 3)
                    listOn(mViewDataBinding.btnAllModelCaText3, 3)
                } else if (clicknum != -1 && clicknum == 3) {
                    listReset(mViewDataBinding.btnAllModelCaText3, -1)
                } else if (clicknum != 3) {
                    listOn(mViewDataBinding.btnAllModelCaText3, 3)
                }
            }
            R.id.btn_all_model_ca_text_4 -> {
                if (clicknum != -1 && clicknum != 4) {
                    listOff(mViewDataBinding.btnAllModelCaText4, 4)
                    listOn(mViewDataBinding.btnAllModelCaText4, 4)
                } else if (clicknum != -1 && clicknum == 4) {
                    listReset(mViewDataBinding.btnAllModelCaText4, -1)
                } else if (clicknum != 4) {
                    listOn(mViewDataBinding.btnAllModelCaText4, 4)
                }
            }
            R.id.btn_all_model_ca_text_5 -> {
                if (clicknum != -1 && clicknum != 5) {
                    listOff(mViewDataBinding.btnAllModelCaText5, 5)
                    listOn(mViewDataBinding.btnAllModelCaText5, 5)
                } else if (clicknum != -1 && clicknum == 5) {
                    listReset(mViewDataBinding.btnAllModelCaText5, -1)
                } else if (clicknum != 5) {
                    listOn(mViewDataBinding.btnAllModelCaText5, 5)
                }
            }
            R.id.btn_all_model_ca_text_6 -> {
                if (clicknum != -1 && clicknum != 6) {
                    listOff(mViewDataBinding.btnAllModelCaText6, 6)
                    listOn(mViewDataBinding.btnAllModelCaText6, 6)
                } else if (clicknum != -1 && clicknum == 6) {
                    listReset(mViewDataBinding.btnAllModelCaText6, -1)
                } else if (clicknum != 6) {
                    listOn(mViewDataBinding.btnAllModelCaText6, 6)
                }
            }
            R.id.btn_all_model_ca_text_7 -> {
                if (clicknum != -1 && clicknum != 7) {
                    listOff(mViewDataBinding.btnAllModelCaText7, 7)
                    listOn(mViewDataBinding.btnAllModelCaText7, 7)
                } else if (clicknum != -1 && clicknum == 7) {
                    listReset(mViewDataBinding.btnAllModelCaText7, -1)
                } else if (clicknum != 7) {
                    listOn(mViewDataBinding.btnAllModelCaText7, 7)
                }
            }
            R.id.btn_all_model_ca_text_8 -> {
                if (clicknum != -1 && clicknum != 8) {
                    listOff(mViewDataBinding.btnAllModelCaText8, 8)
                    listOn(mViewDataBinding.btnAllModelCaText8, 8)
                } else if (clicknum != -1 && clicknum == 8) {
                    listReset(mViewDataBinding.btnAllModelCaText8, -1)
                } else if (clicknum != 8) {
                    listOn(mViewDataBinding.btnAllModelCaText8, 8)
                }
            }
            R.id.btn_all_model_ca_text_9 -> {
                if (clicknum != -1 && clicknum != 9) {
                    listOff(mViewDataBinding.btnAllModelCaText9, 9)
                    listOn(mViewDataBinding.btnAllModelCaText9, 9)
                } else if (clicknum != -1 && clicknum == 9) {
                    listReset(mViewDataBinding.btnAllModelCaText9, -1)
                } else if (clicknum != 9) {
                    listOn(mViewDataBinding.btnAllModelCaText9, 9)
                }
            }
            R.id.btn_all_model_ca_text_10 -> {
                if (clicknum != -1 && clicknum != 10) {
                    listOff(mViewDataBinding.btnAllModelCaText10, 10)
                    listOn(mViewDataBinding.btnAllModelCaText10, 10)
                } else if (clicknum != -1 && clicknum == 10) {
                    listReset(mViewDataBinding.btnAllModelCaText10, -1)
                } else if (clicknum != 10) {
                    listOn(mViewDataBinding.btnAllModelCaText10, 10)
                }
            }
            R.id.btn_all_model_ca_text_11 -> {
                if (clicknum != -1 && clicknum != 11) {
                    listOff(mViewDataBinding.btnAllModelCaText11, 11)
                    listOn(mViewDataBinding.btnAllModelCaText11, 11)
                } else if (clicknum != -1 && clicknum == 11) {
                    listReset(mViewDataBinding.btnAllModelCaText11, -1)
                } else if (clicknum != 11) {
                    listOn(mViewDataBinding.btnAllModelCaText11, 11)
                }
            }
            R.id.btn_all_model_ca_text_12 -> {
                if (clicknum != -1 && clicknum != 12) {
                    listOff(mViewDataBinding.btnAllModelCaText12, 12)
                    listOn(mViewDataBinding.btnAllModelCaText12, 12)
                } else if (clicknum != -1 && clicknum == 12) {
                    listReset(mViewDataBinding.btnAllModelCaText12, -1)
                } else if (clicknum != 12) {
                    listOn(mViewDataBinding.btnAllModelCaText12, 12)
                }
            }
            R.id.btn_all_model_ca_text_13 -> {
                if (clicknum != -1 && clicknum != 13) {
                    listOff(mViewDataBinding.btnAllModelCaText13, 13)
                    listOn(mViewDataBinding.btnAllModelCaText13, 13)
                } else if (clicknum != -1 && clicknum == 13) {
                    listReset(mViewDataBinding.btnAllModelCaText13, -1)
                } else if (clicknum != 13) {
                    listOn(mViewDataBinding.btnAllModelCaText13, 13)
                }
            }
            R.id.btn_all_model_ca_text_14 -> {
                if (clicknum != -1 && clicknum != 14) {
                    listOff(mViewDataBinding.btnAllModelCaText14, 14)
                    listOn(mViewDataBinding.btnAllModelCaText14, 14)
                } else if (clicknum != -1 && clicknum == 14) {
                    listReset(mViewDataBinding.btnAllModelCaText14, -1)
                } else if (clicknum != 14) {
                    listOn(mViewDataBinding.btnAllModelCaText14, 14)
                }
            }
            R.id.btn_all_model_ca_text_15 -> {
                if (clicknum != -1 && clicknum != 15) {
                    listOff(mViewDataBinding.btnAllModelCaText15, 15)
                    listOn(mViewDataBinding.btnAllModelCaText15, 15)
                } else if (clicknum != -1 && clicknum == 15) {
                    listReset(mViewDataBinding.btnAllModelCaText15, -1)
                } else if (clicknum != 15) {
                    listOn(mViewDataBinding.btnAllModelCaText15, 15)
                }
            }
            R.id.btn_all_model_ca_text_16 -> {
                if (clicknum != -1 && clicknum != 16) {
                    listOff(mViewDataBinding.btnAllModelCaText16, 16)
                    listOn(mViewDataBinding.btnAllModelCaText16, 16)
                } else if (clicknum != -1 && clicknum == 16) {
                    listReset(mViewDataBinding.btnAllModelCaText16, -1)
                } else if (clicknum != 16) {
                    listOn(mViewDataBinding.btnAllModelCaText16, 16)
                }
            }
            R.id.btn_all_model_ca_text_17 -> {
                if (clicknum != -1 && clicknum != 17) {
                    listOff(mViewDataBinding.btnAllModelCaText17, 17)
                    listOn(mViewDataBinding.btnAllModelCaText17, 17)
                } else if (clicknum != -1 && clicknum == 17) {
                    listReset(mViewDataBinding.btnAllModelCaText17, -1)
                } else if (clicknum != 17) {
                    listOn(mViewDataBinding.btnAllModelCaText17, 17)
                }
            }
            R.id.btn_all_model_ca_text_18 -> {
                if (clicknum != -1 && clicknum != 18) {
                    listOff(mViewDataBinding.btnAllModelCaText18, 18)
                    listOn(mViewDataBinding.btnAllModelCaText18, 18)
                } else if (clicknum != -1 && clicknum == 18) {
                    listReset(mViewDataBinding.btnAllModelCaText18, -1)
                } else if (clicknum != 18) {
                    listOn(mViewDataBinding.btnAllModelCaText18, 18)
                }
            }
            R.id.btn_all_model_ca_text_19 -> {
                if (clicknum != -1 && clicknum != 19) {
                    listOff(mViewDataBinding.btnAllModelCaText19, 19)
                    listOn(mViewDataBinding.btnAllModelCaText19, 19)
                } else if (clicknum != -1 && clicknum == 19) {
                    listReset(mViewDataBinding.btnAllModelCaText19, -1)
                } else if (clicknum != 19) {
                    listOn(mViewDataBinding.btnAllModelCaText19, 19)
                }
            }
        }
    }
}