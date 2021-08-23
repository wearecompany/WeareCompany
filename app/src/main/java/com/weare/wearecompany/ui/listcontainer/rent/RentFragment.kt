package com.weare.wearecompany.ui.listcontainer.rent

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.weare.wearecompany.R
import com.weare.wearecompany.data.List.rent.data.rent
import com.weare.wearecompany.data.retrofit.List.studio.rentListManager
import com.weare.wearecompany.databinding.FragmentListRentBinding
import com.weare.wearecompany.ui.base.BaseFragment
import com.weare.wearecompany.ui.detail.rent.RentActivity
import com.weare.wearecompany.utils.RESPONSE_STATUS
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.ui.listcontainer.ListContainerActivity
import com.google.gson.JsonArray

class
RentFragment : BaseFragment<FragmentListRentBinding>(
    R.layout.fragment_list_rent
), View.OnClickListener {

    companion object {
        fun newInstance(): RentFragment {
            return RentFragment()
        }
    }

    var RpastText: TextView? = null
    var RpastCheckText: TextView? = null
    lateinit var fadeInAnim: Animation
    lateinit var fadeoutAnim: Animation

    //photographer_list
    var Rcacount = 0
    var Rcabool = false
    private var Rcadatabool = false
    private var RcaDataCheck = false

    var Rlicount = 0
    var Rlibool = false
    private var Rlidatabool = false
    private var RlidataCheck = false

    var Rsocount = 0
    var Rsobool = false
    private var Rsodatabool = false
    private var RsortCheck = 0

    private var on_Lotext_check = -1
    private var on_Catext_check = -1


    //studio_sort_item
    private var Rsort_1 = false
    private var Rsort_2 = false
    private var Rsort_3 = false
    private var Rsort_4 = false
    private var Rsort_5 = false

    private var Rcategory_check = 0
    private var Rlocation_check = 0
    private var pastText: TextView? = null

    var rent_array = JsonArray()
    var location_array = JsonArray()

    private var rentTextList = ArrayList<TextView>()
    private var rentDataList = ArrayList<rent>()
    private lateinit var rentAdapter: RentRecycleViewAdapter

    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ListContainerActivity) {
            mContext = context
        }
    }

    override fun onCreate() {

        datacall(rent_array, location_array, RsortCheck, 1)

        bindsetup()

        mViewDataBinding.listRentCategory.setOnClickListener {
            if (Rcacount == 0) {
                if (Rlibool == true) {
                    rent_sub_layout_on_off(
                        mViewDataBinding.relist2,
                        2,
                        Rlibool,
                        mViewDataBinding.rentLocationImage,
                        Rlidatabool
                    )
                    rent_sub_layout_on_off(
                        mViewDataBinding.relist1,
                        1,
                        Rcabool,
                        mViewDataBinding.rentCategoryImage,
                        Rcadatabool
                    )
                } else if (Rsobool == true) {
                    rent_sub_layout_on_off(
                        mViewDataBinding.relist3,
                        3,
                        Rsobool,
                        mViewDataBinding.rentSortImage,
                        Rsodatabool
                    )
                    rent_sub_layout_on_off(
                        mViewDataBinding.relist1,
                        1,
                        Rcabool,
                        mViewDataBinding.rentCategoryImage,
                        Rcadatabool
                    )
                }
                if (!Rcabool) {
                    rent_sub_layout_on_off(
                        mViewDataBinding.relist1,
                        1,
                        Rcabool,
                        mViewDataBinding.rentCategoryImage,
                        Rcadatabool
                    )
                }
            } else {
                rent_sub_layout_on_off(
                    mViewDataBinding.relist1,
                    1,
                    Rcabool,
                    mViewDataBinding.rentCategoryImage,
                    Rcadatabool
                )
            }
        }
        mViewDataBinding.listRentLocation.setOnClickListener {
            if (Rlicount == 0) {
                if (Rcabool == true) {
                    rent_sub_layout_on_off(
                        mViewDataBinding.relist1,
                        1,
                        Rcabool,
                        mViewDataBinding.rentCategoryImage,
                        Rcadatabool
                    )
                    rent_sub_layout_on_off(
                        mViewDataBinding.relist2,
                        2,
                        Rlibool,
                        mViewDataBinding.rentLocationImage,
                        Rlidatabool
                    )
                } else if (Rsobool == true) {
                    rent_sub_layout_on_off(
                        mViewDataBinding.relist3,
                        3,
                        Rsobool,
                        mViewDataBinding.rentSortImage,
                        Rsodatabool
                    )
                    rent_sub_layout_on_off(
                        mViewDataBinding.relist2,
                        2,
                        Rlibool,
                        mViewDataBinding.rentLocationImage,
                        Rlidatabool
                    )
                }
                if (!Rlibool) {
                    rent_sub_layout_on_off(
                        mViewDataBinding.relist2,
                        2,
                        Rlibool,
                        mViewDataBinding.rentLocationImage,
                        Rlidatabool
                    )
                }
            } else {
                rent_sub_layout_on_off(
                    mViewDataBinding.relist2,
                    2,
                    Rlibool,
                    mViewDataBinding.rentLocationImage,
                    Rlidatabool
                )
            }
        }
        mViewDataBinding.listRentSort.setOnClickListener {
            if (Rsocount == 0) {
                if (Rcabool == true) {
                    rent_sub_layout_on_off(
                        mViewDataBinding.relist1,
                        1,
                        Rcabool,
                        mViewDataBinding.rentCategoryImage,
                        Rcadatabool
                    )
                    rent_sub_layout_on_off(
                        mViewDataBinding.relist3,
                        3,
                        Rsobool,
                        mViewDataBinding.rentSortImage,
                        Rsodatabool
                    )
                } else if (Rlibool == true) {
                    rent_sub_layout_on_off(
                        mViewDataBinding.relist2,
                        2,
                        Rlibool,
                        mViewDataBinding.rentLocationImage,
                        Rlidatabool
                    )
                    rent_sub_layout_on_off(
                        mViewDataBinding.relist3,
                        3,
                        Rsobool,
                        mViewDataBinding.rentSortImage,
                        Rsodatabool
                    )
                }
                if (!Rsobool) {
                    rent_sub_layout_on_off(
                        mViewDataBinding.relist3,
                        3,
                        Rsobool,
                        mViewDataBinding.rentSortImage,
                        Rsodatabool
                    )
                }
            } else {
                rent_sub_layout_on_off(
                    mViewDataBinding.relist3,
                    3,
                    Rsobool,
                    mViewDataBinding.rentSortImage,
                    Rsodatabool
                )
            }
        }

        mViewDataBinding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radio_button_1 -> {
                    RsortCheck = 0
                    rentTextList[0].setTextColor(Color.parseColor("#6d34f3"))
                    if (pastText == null) {
                        pastText = rentTextList[0]
                    } else {
                        pastText!!.setTextColor(Color.parseColor("#7f7f7f"))
                        pastText = rentTextList[0]
                    }
                }
                R.id.radio_button_2 -> {
                    RsortCheck = 1
                    rentTextList[1].setTextColor(Color.parseColor("#6d34f3"))
                    if (pastText == null) {
                        pastText = rentTextList[1]
                    } else {
                        pastText!!.setTextColor(Color.parseColor("#7f7f7f"))
                        pastText = rentTextList[1]
                    }
                }
                R.id.radio_button_3 -> {
                    RsortCheck = 2
                    rentTextList[2].setTextColor(Color.parseColor("#6d34f3"))
                    if (pastText == null) {
                        pastText = rentTextList[2]
                    } else {
                        pastText!!.setTextColor(Color.parseColor("#7f7f7f"))
                        pastText = rentTextList[2]
                    }
                }
                R.id.radio_button_4 -> {
                    RsortCheck = 3
                    rentTextList[3].setTextColor(Color.parseColor("#6d34f3"))
                    if (pastText == null) {
                        pastText = rentTextList[3]
                    } else {
                        pastText!!.setTextColor(Color.parseColor("#7f7f7f"))
                        pastText = rentTextList[3]
                    }
                }
                R.id.radio_button_5 -> {
                    RsortCheck = 4
                    rentTextList[4].setTextColor(Color.parseColor("#6d34f3"))
                    if (pastText == null) {
                        pastText = rentTextList[4]
                    } else {
                        pastText!!.setTextColor(Color.parseColor("#7f7f7f"))
                        pastText = rentTextList[4]
                    }
                }
            }
        }
    }

    fun bindsetup() {

        fadeInAnim = AnimationUtils.loadAnimation(context, R.anim.move_top_down)
        fadeoutAnim = AnimationUtils.loadAnimation(context, R.anim.move_top_up)

        mViewDataBinding.sort1.setTextColor(Color.parseColor("#6d34f3"))
        if (pastText == null) {
            pastText = mViewDataBinding.sort1
        }

        mViewDataBinding.rentItem0.setOnClickListener(this)
        mViewDataBinding.rentItem1.setOnClickListener(this)
        mViewDataBinding.rentItem2.setOnClickListener(this)
        mViewDataBinding.rentItem3.setOnClickListener(this)
        mViewDataBinding.rentItem4.setOnClickListener(this)
        mViewDataBinding.rentItem5.setOnClickListener(this)
        mViewDataBinding.rentItem6.setOnClickListener(this)
        mViewDataBinding.rentItem7.setOnClickListener(this)

        //rent_location_item
        mViewDataBinding.seoul0.setOnClickListener(this)
        mViewDataBinding.gyeonggi1.setOnClickListener(this)
        mViewDataBinding.incheon2.setOnClickListener(this)
        mViewDataBinding.gangwon3.setOnClickListener(this)
        mViewDataBinding.jeju4.setOnClickListener(this)
        mViewDataBinding.daejeon5.setOnClickListener(this)
        mViewDataBinding.chungbuk6.setOnClickListener(this)
        mViewDataBinding.chungnam7.setOnClickListener(this)
        mViewDataBinding.busan8.setOnClickListener(this)
        mViewDataBinding.ulsan9.setOnClickListener(this)
        mViewDataBinding.gyeongnam10.setOnClickListener(this)
        mViewDataBinding.daegu11.setOnClickListener(this)
        mViewDataBinding.kyonbuk12.setOnClickListener(this)
        mViewDataBinding.gwangju13.setOnClickListener(this)
        mViewDataBinding.jeonnam14.setOnClickListener(this)
        mViewDataBinding.jeonbuk15.setOnClickListener(this)

        //data call
        mViewDataBinding.rentSubOk.setOnClickListener(this)
        mViewDataBinding.rentLocationOk.setOnClickListener(this)
        mViewDataBinding.rentSortOk.setOnClickListener(this)

        //trip_sort_item
        rentTextList.add(mViewDataBinding.sort1)
        rentTextList.add(mViewDataBinding.sort2)
        rentTextList.add(mViewDataBinding.sort3)
        rentTextList.add(mViewDataBinding.sort4)
        rentTextList.add(mViewDataBinding.sort5)

    }

    fun rent_sub_layout_on_off(
        layout: CardView,
        count: Int,
        on_off_type: Boolean,
        imageview: ImageView,
        image_type: Boolean
    ) {
        if (!on_off_type) {
            if (!image_type) {
                imageview.setImageResource(R.drawable.list_category_down_gray)
            } else {
                imageview.setImageResource(R.drawable.list_category_down_puple)
            }
            layout.visibility = View.VISIBLE
            layout.startAnimation(fadeInAnim)
            when (count) {
                1 -> {
                    Rcacount = 1
                    Rcabool = true
                    return
                }
                2 -> {
                    Rlicount = 1
                    Rlibool = true
                    return
                }
                3 -> {
                    Rsocount = 1
                    Rsobool = true
                    return
                }

            }
        } else {
            if (!image_type) {
                imageview.setImageResource(R.drawable.list_category_up_gray)
            } else {
                imageview.setImageResource(R.drawable.list_category_up_puple)
            }
            layout.startAnimation(fadeoutAnim)
            layout.visibility = View.GONE
            when (count) {
                1 -> {
                    Rcacount = 0
                    Rcabool = false
                }
                2 -> {
                    Rlicount = 0
                    Rlibool = false
                }
                3 -> {
                    Rsocount = 0
                    Rsobool = false
                }
            }
        }
    }

    fun rent_sub_tab_on_off(
        layout: LinearLayout,
        textView: TextView,
        imageview: ImageView,
        type: Boolean
    ) {
        if (type) {
            layout.setBackgroundResource(R.drawable.list_sub_background_on)
            textView.setTextColor(Color.parseColor("#6d34f3"))
            imageview.setImageResource(R.drawable.list_category_up_puple)
            //layout.setTextColor(Color.parseColor("#ffffff"))
        } else {
            layout.setBackgroundResource(R.drawable.list_sub_background_off)
            textView.setTextColor(Color.parseColor("#545454"))
            imageview.setImageResource(R.drawable.list_category_up_gray)
        }
    }

    fun rent_categort_item_on_off(textView: TextView, num: Int) {
        if (RpastText == null) {
            RcaDataCheck = true
            Rcategory_check = num
            textView.setTextColor(Color.parseColor("#6d34f3"))
            RpastText = textView
            on_Catext_check = num
        } else if (RpastText == textView) {
            RcaDataCheck = false
            RpastText = null
            on_Catext_check = -1
            textView.setTextColor(Color.parseColor("#7f7f7f"))
        } else {
            RcaDataCheck = true
            textView.setTextColor(Color.parseColor("#6d34f3"))
            Rcategory_check = num
            RpastText!!.setTextColor(Color.parseColor("#7f7f7f"))
            RpastText = textView
            on_Catext_check = num
        }
    }

    fun rent_location_item_in_off(textView: TextView, num: Int) {
        if (RpastCheckText == null) {
            RlidataCheck = true
            Rlocation_check = num
            textView.setTextColor(Color.parseColor("#6d34f3"))
            RpastCheckText = textView
        } else if (RpastCheckText == textView) {
            RlidataCheck = false
            RpastCheckText = null
            on_Lotext_check = -1
            textView.setTextColor(Color.parseColor("#7f7f7f"))
        } else {
            RlidataCheck = true
            Rlocation_check = num
            textView.setTextColor(Color.parseColor("#6d34f3"))
            RpastCheckText!!.setTextColor(Color.parseColor("#7f7f7f"))
            RpastCheckText = textView
        }
    }

    fun datacall(rentArray: JsonArray, locationArray: JsonArray, sort: Int, page: Int) {
        rentListManager.instant.listdata(
            rentArray,
            locationArray,
            sort,
            page,
            completion = { responseStatus, responsestudiokList ->

                when (responseStatus) {
                    RESPONSE_STATUS.OKAY -> {
                        rentDataList = responsestudiokList
                        rentAdapter = RentRecycleViewAdapter(rentDataList)

                        mViewDataBinding.rentListRecycler.layoutManager =
                            GridLayoutManager(context, 2)
                        mViewDataBinding.rentListRecycler.adapter = rentAdapter

                        rentAdapter.setItemClickListener(object :
                            RentRecycleViewAdapter.OnItemClickListener {
                            override fun onClick(v: View, position: Int, Item: rent) {
                                var newIntent = Intent(context, RentActivity::class.java)
                                var imageView: ImageView = v.findViewById(R.id.rent_list_image)
                                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                    mContext as Activity,
                                    imageView,
                                    getString(R.string.transitionImage)
                                )
                                newIntent.putExtra("expert_idx", Item.idx)
                                newIntent.putExtra("user_idx", MyApplication.prefs.getString("user_idx",""))
                                startActivity(newIntent, options.toBundle())

                            }

                        })
                    }
                }
            })
    }

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.rent_item_0 -> {
                on_Catext_check = 0
                rent_categort_item_on_off(mViewDataBinding.rentItem0, 0)
            }
            R.id.rent_item_1 -> {
                on_Catext_check = 1
                rent_categort_item_on_off(mViewDataBinding.rentItem1, 1)
            }
            R.id.rent_item_2 -> {
                on_Catext_check = 2
                rent_categort_item_on_off(mViewDataBinding.rentItem2, 2)
            }
            R.id.rent_item_3 -> {
                on_Catext_check = 3
                rent_categort_item_on_off(mViewDataBinding.rentItem3, 3)
            }
            R.id.rent_item_4 -> {
                on_Catext_check = 4
                rent_categort_item_on_off(mViewDataBinding.rentItem4, 4)
            }
            R.id.rent_item_5 -> {
                on_Catext_check = 5
                rent_categort_item_on_off(mViewDataBinding.rentItem5, 5)
            }
            R.id.rent_item_6 -> {
                on_Catext_check = 6
                rent_categort_item_on_off(mViewDataBinding.rentItem6, 6)

            }
            R.id.rent_item_7 -> {
                on_Catext_check = 7
                rent_categort_item_on_off(mViewDataBinding.rentItem7, 7)
            }

            //stdio_location_item
            R.id.seoul_0 -> {
                on_Lotext_check = 0
                rent_location_item_in_off(mViewDataBinding.seoul0, 0)
            }
            R.id.gyeonggi_1 -> {
                on_Lotext_check = 1
                rent_location_item_in_off(mViewDataBinding.gyeonggi1, 1)
            }
            R.id.incheon_2 -> {
                on_Lotext_check = 2
                rent_location_item_in_off(mViewDataBinding.incheon2, 2)
            }
            R.id.gangwon_3 -> {
                on_Lotext_check = 3
                rent_location_item_in_off(mViewDataBinding.gangwon3, 3)
            }
            R.id.jeju_4 -> {
                on_Lotext_check = 4
                rent_location_item_in_off(mViewDataBinding.jeju4, 4)
            }
            R.id.daejeon_5 -> {
                on_Lotext_check = 5
                rent_location_item_in_off(mViewDataBinding.daejeon5, 5)
            }
            R.id.chungbuk_6 -> {
                on_Lotext_check = 6
                rent_location_item_in_off(mViewDataBinding.chungbuk6, 6)
            }
            R.id.chungnam_7 -> {
                on_Lotext_check = 7
                rent_location_item_in_off(mViewDataBinding.chungnam7, 7)
            }
            R.id.busan_8 -> {
                on_Lotext_check = 8
                rent_location_item_in_off(mViewDataBinding.busan8, 8)
            }
            R.id.ulsan_9 -> {
                on_Lotext_check = 9
                rent_location_item_in_off(mViewDataBinding.ulsan9, 9)
            }
            R.id.gyeongnam_10 -> {
                on_Lotext_check = 10
                rent_location_item_in_off(mViewDataBinding.gyeongnam10, 10)
            }
            R.id.daegu_11 -> {
                on_Lotext_check = 11
                rent_location_item_in_off(mViewDataBinding.daegu11, 11)
            }
            R.id.kyonbuk_12 -> {
                on_Lotext_check = 12
                rent_location_item_in_off(mViewDataBinding.kyonbuk12, 12)
            }
            R.id.gwangju_13 -> {
                on_Lotext_check = 13
                rent_location_item_in_off(mViewDataBinding.gwangju13, 13)
            }
            R.id.jeonnam_14 -> {
                on_Lotext_check = 14
                rent_location_item_in_off(mViewDataBinding.jeonnam14, 14)
            }
            R.id.jeonbuk_15 -> {
                on_Lotext_check = 15
                rent_location_item_in_off(mViewDataBinding.jeonbuk15, 15)
            }

            R.id.rent_sub_ok -> {
                rent_array = JsonArray()
                if (on_Catext_check != -1) {
                    rent_array.add(on_Catext_check)
                }
                Rcadatabool = RcaDataCheck
                rent_sub_layout_on_off(
                    mViewDataBinding.relist1,
                    1,
                    Rcabool,
                    mViewDataBinding.rentCategoryImage,
                    Rcadatabool
                )
                rent_sub_tab_on_off(
                    mViewDataBinding.listRentCategory,
                    mViewDataBinding.rentCategoryText,
                    mViewDataBinding.rentCategoryImage,
                    RcaDataCheck
                )

                datacall(rent_array, location_array, RsortCheck, 1)
            }
            R.id.rent_location_ok -> {
                location_array = JsonArray()
                if (on_Lotext_check != -1) {
                    location_array.add(on_Lotext_check)
                }
                Rlidatabool = RlidataCheck
                rent_sub_layout_on_off(
                    mViewDataBinding.relist2,
                    2,
                    Rlibool,
                    mViewDataBinding.rentLocationImage,
                    Rlidatabool
                )
                rent_sub_tab_on_off(
                    mViewDataBinding.listRentLocation,
                    mViewDataBinding.rentLocationText,
                    mViewDataBinding.rentLocationImage,
                    RlidataCheck
                )

                datacall(rent_array, location_array, RsortCheck, 1)
            }
            R.id.rent_sort_ok -> {
                rent_sub_layout_on_off(
                    mViewDataBinding.relist3,
                    3,
                    Rsobool,
                    mViewDataBinding.rentSortImage,
                    Rsodatabool
                )
                if (RsortCheck > 0) {
                    Rsodatabool = true
                    rent_sub_tab_on_off(
                        mViewDataBinding.listRentSort,
                        mViewDataBinding.rentSortText,
                        mViewDataBinding.rentSortImage,
                        true
                    )
                } else {
                    Rsodatabool = false
                    rent_sub_tab_on_off(
                        mViewDataBinding.listRentSort,
                        mViewDataBinding.rentSortText,
                        mViewDataBinding.rentSortImage,
                        false
                    )
                }
                datacall(rent_array, location_array, RsortCheck, 1)
            }
        }
    }

}


