package com.weare.wearecompany.ui.listcontainer.photographer

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.weare.wearecompany.R
import com.weare.wearecompany.databinding.BottomDialogPhotoBinding
import com.weare.wearecompany.ui.base.BaseFragment


class PhotoCategoryFragment : BaseFragment<BottomDialogPhotoBinding>(
    R.layout.bottom_dialog_photo
), View.OnClickListener {

    private lateinit var posttext: TextView
    private val viewmodel: PhotoViewModel by activityViewModels()
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
        mViewDataBinding.btnAllPhotoCaText0.setOnClickListener(this)
        mViewDataBinding.btnAllPhotoCaText1.setOnClickListener(this)
        mViewDataBinding.btnAllPhotoCaText2.setOnClickListener(this)
        mViewDataBinding.btnAllPhotoCaText3.setOnClickListener(this)
        mViewDataBinding.btnAllPhotoCaText4.setOnClickListener(this)
        mViewDataBinding.btnAllPhotoCaText5.setOnClickListener(this)
        mViewDataBinding.btnAllPhotoCaText6.setOnClickListener(this)
        mViewDataBinding.btnAllPhotoCaText7.setOnClickListener(this)
        mViewDataBinding.btnAllPhotoCaText8.setOnClickListener(this)
        mViewDataBinding.btnAllPhotoCaText9.setOnClickListener(this)
        mViewDataBinding.btnAllPhotoCaText10.setOnClickListener(this)
        mViewDataBinding.btnAllPhotoCaText11.setOnClickListener(this)
        mViewDataBinding.btnAllPhotoCaText12.setOnClickListener(this)
        mViewDataBinding.btnAllPhotoCaText13.setOnClickListener(this)

    }

    fun dataSetup() {
        if (setupCategory != -1) {
            when (setupCategory) {
                0 -> listOn(mViewDataBinding.btnAllPhotoCaText0, 0)
                1 -> listOn(mViewDataBinding.btnAllPhotoCaText1, 1)
                2 -> listOn(mViewDataBinding.btnAllPhotoCaText2, 2)
                3 -> listOn(mViewDataBinding.btnAllPhotoCaText3, 3)
                4 -> listOn(mViewDataBinding.btnAllPhotoCaText4, 4)
                5 -> listOn(mViewDataBinding.btnAllPhotoCaText5, 5)
                6 -> listOn(mViewDataBinding.btnAllPhotoCaText6, 6)
                7 -> listOn(mViewDataBinding.btnAllPhotoCaText7, 7)
                8 -> listOn(mViewDataBinding.btnAllPhotoCaText8, 8)
                9 -> listOn(mViewDataBinding.btnAllPhotoCaText9, 9)
                10 -> listOn(mViewDataBinding.btnAllPhotoCaText10, 10)
                11 -> listOn(mViewDataBinding.btnAllPhotoCaText11, 11)
                12 -> listOn(mViewDataBinding.btnAllPhotoCaText12, 12)
                13 -> listOn(mViewDataBinding.btnAllPhotoCaText13, 13)
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
            R.id.btn_all_photo_ca_text_0 -> {
                if (clicknum != -1 && clicknum != 0) {
                    listOff(mViewDataBinding.btnAllPhotoCaText0, 0)
                    listOn(mViewDataBinding.btnAllPhotoCaText0, 0)
                } else if (clicknum != -1 && clicknum == 0) {
                    listReset(mViewDataBinding.btnAllPhotoCaText0, -1)
                } else if (clicknum != 0) {
                    listOn(mViewDataBinding.btnAllPhotoCaText0, 0)
                }
            }
            R.id.btn_all_photo_ca_text_1 -> {
                if (clicknum != -1 && clicknum != 1) {
                    listOff(mViewDataBinding.btnAllPhotoCaText1, 1)
                    listOn(mViewDataBinding.btnAllPhotoCaText1, 1)
                } else if (clicknum != -1 && clicknum == 1) {
                    listReset(mViewDataBinding.btnAllPhotoCaText1, -1)
                } else if (clicknum != 1) {
                    listOn(mViewDataBinding.btnAllPhotoCaText1, 1)
                }
            }
            R.id.btn_all_photo_ca_text_2 -> {
                if (clicknum != -1 && clicknum != 2) {
                    listOff(mViewDataBinding.btnAllPhotoCaText2, 2)
                    listOn(mViewDataBinding.btnAllPhotoCaText2, 2)
                } else if (clicknum != -1 && clicknum == 2) {
                    listReset(mViewDataBinding.btnAllPhotoCaText2, -1)
                } else if (clicknum != 2) {
                    listOn(mViewDataBinding.btnAllPhotoCaText2, 2)
                }
            }
            R.id.btn_all_photo_ca_text_3 -> {
                if (clicknum != -1 && clicknum != 3) {
                    listOff(mViewDataBinding.btnAllPhotoCaText3, 3)
                    listOn(mViewDataBinding.btnAllPhotoCaText3, 3)
                } else if (clicknum != -1 && clicknum == 3) {
                    listReset(mViewDataBinding.btnAllPhotoCaText3, -1)
                } else if (clicknum != 3) {
                    listOn(mViewDataBinding.btnAllPhotoCaText3, 3)
                }
            }
            R.id.btn_all_photo_ca_text_4 -> {
                if (clicknum != -1 && clicknum != 4) {
                    listOff(mViewDataBinding.btnAllPhotoCaText4, 4)
                    listOn(mViewDataBinding.btnAllPhotoCaText4, 4)
                } else if (clicknum != -1 && clicknum == 4) {
                    listReset(mViewDataBinding.btnAllPhotoCaText4, -1)
                } else if (clicknum != 4) {
                    listOn(mViewDataBinding.btnAllPhotoCaText4, 4)
                }
            }
            R.id.btn_all_photo_ca_text_5 -> {
                if (clicknum != -1 && clicknum != 5) {
                    listOff(mViewDataBinding.btnAllPhotoCaText5, 5)
                    listOn(mViewDataBinding.btnAllPhotoCaText5, 5)
                } else if (clicknum != -1 && clicknum == 5) {
                    listReset(mViewDataBinding.btnAllPhotoCaText5, -1)
                } else if (clicknum != 5) {
                    listOn(mViewDataBinding.btnAllPhotoCaText5, 5)
                }
            }
            R.id.btn_all_photo_ca_text_6 -> {
                if (clicknum != -1 && clicknum != 6) {
                    listOff(mViewDataBinding.btnAllPhotoCaText6, 6)
                    listOn(mViewDataBinding.btnAllPhotoCaText6, 6)
                } else if (clicknum != -1 && clicknum == 6) {
                    listReset(mViewDataBinding.btnAllPhotoCaText6, -1)
                } else if (clicknum != 6) {
                    listOn(mViewDataBinding.btnAllPhotoCaText6, 6)
                }
            }
            R.id.btn_all_photo_ca_text_7 -> {
                if (clicknum != -1 && clicknum != 7) {
                    listOff(mViewDataBinding.btnAllPhotoCaText7, 7)
                    listOn(mViewDataBinding.btnAllPhotoCaText7, 7)
                } else if (clicknum != -1 && clicknum == 7) {
                    listReset(mViewDataBinding.btnAllPhotoCaText7, -1)
                } else if (clicknum != 7) {
                    listOn(mViewDataBinding.btnAllPhotoCaText7, 7)
                }
            }
            R.id.btn_all_photo_ca_text_8 -> {
                if (clicknum != -1 && clicknum != 8) {
                    listOff(mViewDataBinding.btnAllPhotoCaText8, 8)
                    listOn(mViewDataBinding.btnAllPhotoCaText8, 8)
                } else if (clicknum != -1 && clicknum == 8) {
                    listReset(mViewDataBinding.btnAllPhotoCaText8, -1)
                } else if (clicknum != 8) {
                    listOn(mViewDataBinding.btnAllPhotoCaText8, 8)
                }
            }
            R.id.btn_all_photo_ca_text_9 -> {
                if (clicknum != -1 && clicknum != 9) {
                    listOff(mViewDataBinding.btnAllPhotoCaText9, 9)
                    listOn(mViewDataBinding.btnAllPhotoCaText9, 9)
                } else if (clicknum != -1 && clicknum == 9) {
                    listReset(mViewDataBinding.btnAllPhotoCaText9, -1)
                } else if (clicknum != 9) {
                    listOn(mViewDataBinding.btnAllPhotoCaText9, 9)
                }
            }
            R.id.btn_all_photo_ca_text_10 -> {
                if (clicknum != -1 && clicknum != 10) {
                    listOff(mViewDataBinding.btnAllPhotoCaText10, 10)
                    listOn(mViewDataBinding.btnAllPhotoCaText10, 10)
                } else if (clicknum != -1 && clicknum == 10) {
                    listReset(mViewDataBinding.btnAllPhotoCaText10, -1)
                } else if (clicknum != 10) {
                    listOn(mViewDataBinding.btnAllPhotoCaText10, 10)
                }
            }
            R.id.btn_all_photo_ca_text_11 -> {
                if (clicknum != -1 && clicknum != 11) {
                    listOff(mViewDataBinding.btnAllPhotoCaText11, 11)
                    listOn(mViewDataBinding.btnAllPhotoCaText11, 11)
                } else if (clicknum != -1 && clicknum == 11) {
                    listReset(mViewDataBinding.btnAllPhotoCaText11, -1)
                } else if (clicknum != 11) {
                    listOn(mViewDataBinding.btnAllPhotoCaText11, 11)
                }
            }
            R.id.btn_all_photo_ca_text_12 -> {
                if (clicknum != -1 && clicknum != 12) {
                    listOff(mViewDataBinding.btnAllPhotoCaText12, 12)
                    listOn(mViewDataBinding.btnAllPhotoCaText12, 12)
                } else if (clicknum != -1 && clicknum == 12) {
                    listReset(mViewDataBinding.btnAllPhotoCaText12, -1)
                } else if (clicknum != 12) {
                    listOn(mViewDataBinding.btnAllPhotoCaText12, 12)
                }
            }
            R.id.btn_all_photo_ca_text_13 -> {
                if (clicknum != -1 && clicknum != 13) {
                    listOff(mViewDataBinding.btnAllPhotoCaText13, 13)
                    listOn(mViewDataBinding.btnAllPhotoCaText13, 13)
                } else if (clicknum != -1 && clicknum == 13) {
                    listReset(mViewDataBinding.btnAllPhotoCaText13, -1)
                } else if (clicknum != 13) {
                    listOn(mViewDataBinding.btnAllPhotoCaText13, 13)
                }
            }

        }
    }
}
