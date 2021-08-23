package com.weare.wearecompany.ui.listcontainer.studio


import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonArray
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.List.clip
import com.weare.wearecompany.data.main.data.newStudio
import com.weare.wearecompany.data.main.data.studio
import com.weare.wearecompany.data.retrofit.List.studio.studioListManager
import com.weare.wearecompany.ui.detail.DatailActivity
import com.weare.wearecompany.ui.listcontainer.ListContainerActivity
import com.weare.wearecompany.ui.listcontainer.photographer.PhotoAllDialog
import com.weare.wearecompany.ui.listcontainer.photographer.PhotoClipRecyclerViewAdapter
import com.weare.wearecompany.utils.RESPONSE_STATUS
import kotlinx.android.synthetic.main.fragment_list_model.view.*
import kotlinx.android.synthetic.main.fragment_list_photographer.view.*
import kotlinx.android.synthetic.main.fragment_list_stdio.view.*
import kotlinx.android.synthetic.main.fragment_list_trip.view.*


class StudioFragment : Fragment(
), View.OnClickListener {

    private var ca_click = ArrayList<Int>()
    private var min_money = ""
    private var max_money = ""
    private var la_click = ArrayList<Int>()
    private var locationList = ArrayList<Int>()
    private var categoryList = ArrayList<Int>()
    private var page_count = 1
    private var sort_check = 0
    lateinit var viewe: View

    var clipmoney = ""
    private var clipList = ArrayList<clip>()

    var stbool = false

    var studio_array = JsonArray()
    var location_array = JsonArray()
    val datetime_array = JsonArray()

    private lateinit var clipAdapter: StudioClipRecyclerViewAdapter
    private var studioDataList = ArrayList<studio>()
    private lateinit var studioAdapter: StudioRecyclerViewAdapter
    private lateinit var newStudioAdapter: StudioNewRecyclerViewAdapter

    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ListContainerActivity) {
            mContext = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewe = inflater!!.inflate(R.layout.fragment_list_stdio, container, false)

        datacall(
            studio_array,
            location_array,
            datetime_array,
            min_money,
            max_money,
            sort_check,
            page_count
        )
        bindsetup()


        viewe.studio_list_recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!viewe.studio_list_recycler.canScrollVertically(1)) {
                    page_count += 1
                    studioListManager.instant.listdata(
                        studio_array,
                        location_array,
                        datetime_array,
                        min_money,
                        max_money,
                        sort_check,
                        page_count,
                        completion = { responseStatus, responsenewlist, responsestudiokList ->
                            when (responseStatus) {
                                RESPONSE_STATUS.OKAY -> {
                                    for (i in responsestudiokList) {
                                        studioAdapter.addItem(i)
                                    }
                                }
                            }
                        })
                }
            }
        })

        return viewe
    }

    fun bindsetup() {
        viewe.list_studio_category.setOnClickListener(this)
        viewe.list_studio_location.setOnClickListener(this)
        viewe.list_studio_money.setOnClickListener(this)
        viewe.list_studio_sort.setOnClickListener(this)
    }


    fun datacall(
        studioArray: JsonArray,
        locationArray: JsonArray,
        timeArray: JsonArray,
        min: String,
        max: String,
        sort: Int,
        page: Int
    ) {
        clipList = ArrayList()

        if (studioArray.size() != 0) {
            for (i in studioArray) {
                val clipitem = clip(
                    main_type = 0,
                    sub_type = i.asInt,
                    name = ""
                )
                clipList.add(clipitem)
            }
        }

        if (locationArray.size() != 0) {
            for (i in locationArray) {
                val clipitem = clip(
                    main_type = 1,
                    sub_type = i.asInt,
                    name = ""
                )
                clipList.add(clipitem)
            }
        }

        if (min != "" && max != "") {
            clipmoney = min + " ~ " + max
            val moneyitem = clip(
                main_type = 2,
                sub_type = -1,
                name = clipmoney
            )
            clipList.add(moneyitem)
        } else if (min == "" && max != "") {
            clipmoney = "0" + " ~ " + max
            val moneyitem = clip(
                main_type = 2,
                sub_type = -1,
                name = clipmoney
            )
            clipList.add(moneyitem)
        } else if (min != "" && max == "") {
            clipmoney = min + " ~ " + "500,000 이상"
            val moneyitem = clip(
                main_type = 2,
                sub_type = -1,
                name = clipmoney
            )
            clipList.add(moneyitem)
        }

        if (sort != 0) {
            val sortitem = clip(
                main_type = 3,
                sub_type = sort,
                name = ""
            )
            clipList.add(sortitem)
        }
        studioListManager.instant.listdata(
            studioArray,
            locationArray,
            timeArray,
            min,
            max,
            sort,
            page,
            completion = { responseStatus, responsenewlist, responsestudiokList ->

                when (responseStatus) {
                    RESPONSE_STATUS.OKAY -> {
                        if (responsestudiokList != null) {

                            newStudioAdapter = StudioNewRecyclerViewAdapter(responsenewlist)
                            viewe.list_studio_new_recyclerview.layoutManager =
                                LinearLayoutManager(
                                    MyApplication.instance,
                                    LinearLayoutManager.HORIZONTAL, false
                                )
                            viewe.list_studio_new_recyclerview.adapter = newStudioAdapter

                            newStudioAdapter.setItemClickListener(object :
                                StudioNewRecyclerViewAdapter.OnItemClickListener {
                                override fun onClick(
                                    v: View,
                                    position: Int,
                                    Item: newStudio
                                ) {
                                    var newIntent = Intent(context, DatailActivity::class.java)
                                    newIntent.putExtra("idx", Item.idx)
                                    startActivity(newIntent)
                                }

                            })

                            studioDataList = responsestudiokList
                            studioAdapter = StudioRecyclerViewAdapter(studioDataList)
                            viewe.studio_list_recycler.layoutManager =
                                GridLayoutManager(context, 2)
                            viewe.studio_list_recycler.adapter = studioAdapter

                            studioAdapter.setItemClickListener(object :
                                StudioRecyclerViewAdapter.OnItemClickListener {
                                override fun onClick(v: View, position: Int, Item: studio) {
                                    if (!stbool) {
                                        var newIntent = Intent(context, DatailActivity::class.java)

                                        newIntent.putExtra("idx", Item.idx)
                                        startActivity(newIntent)
                                    }
                                }
                            })

                            if (clipList.size != 0) {
                                viewe.list_studio_clip_recyclerview.visibility = View.VISIBLE
                                clipAdapter = StudioClipRecyclerViewAdapter(clipList)
                                viewe.list_studio_clip_recyclerview.layoutManager =
                                    LinearLayoutManager(
                                        MyApplication.instance,
                                        LinearLayoutManager.HORIZONTAL, false
                                    )
                                viewe.list_studio_clip_recyclerview.adapter = clipAdapter

                                clipAdapter.setItemClickListener(object : StudioClipRecyclerViewAdapter.OnItemClickListener{
                                    override fun onClick(v: View, position: Int, item: clip) {
                                        when(item.main_type) {
                                            0 -> {
                                                if (studio_array.size() != 0) {
                                                    val i1 = 1
                                                    for (i in i1..studio_array.size()) {
                                                        if(item.sub_type == studio_array[i-1].asInt) {
                                                            studio_array.remove(i-1)
                                                            categoryList.removeAt(i-1)
                                                            break
                                                        }
                                                    }

                                                }
                                            }
                                            1 -> {
                                                if (location_array.size() != 0) {
                                                    val i1 = 1
                                                    for (i in i1..location_array.size()) {
                                                        if(item.sub_type == location_array[i-1].asInt) {
                                                            location_array.remove(i-1)
                                                            locationList.removeAt(i-1)
                                                            break
                                                        }
                                                    }

                                                }
                                            }
                                            2 -> {
                                                min_money = ""
                                                max_money = ""
                                                viewe.list_studio_money.setBackgroundResource(R.drawable.list_sub_background_off)
                                                viewe.list_money_image.setImageResource(R.drawable.list_category_down_gray)
                                                viewe.list_money_text.setTextColor(Color.parseColor("#7f7f7f"))
                                            }
                                            3 -> {
                                                sort_check = 0
                                                viewe.list_studio_sort.setBackgroundResource(R.drawable.list_sub_background_off)
                                                viewe.list_sort_image.setImageResource(R.drawable.list_category_down_gray)
                                                viewe.list_sort_text.setTextColor(Color.parseColor("#7f7f7f"))
                                            }
                                        }
                                        if (studio_array.size() == 0) {
                                            viewe.list_studio_category.setBackgroundResource(R.drawable.list_sub_background_off)
                                            viewe.list_studio_image.setImageResource(R.drawable.list_category_down_gray)
                                            viewe.list_studio_text.setTextColor(Color.parseColor("#7f7f7f"))
                                        }
                                        if (location_array.size() == 0) {
                                            viewe.list_studio_location.setBackgroundResource(R.drawable.list_sub_background_off)
                                            viewe.list_location_image.setImageResource(R.drawable.list_category_down_gray)
                                            viewe.list_location_text.setTextColor(Color.parseColor("#7f7f7f"))
                                        }
                                        datacall(
                                            studio_array,
                                            location_array,
                                            datetime_array,
                                            min_money,
                                            max_money,
                                            sort_check,
                                            page_count
                                        )
                                    }
                                })

                            } else {
                                viewe.list_studio_clip_recyclerview.visibility = View.GONE
                            }
                        }
                    }
                }
            })
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.list_studio_category -> {
                val testiew: StudioAllDialog = StudioAllDialog(
                    0,
                    categoryList,
                    locationList,
                    min_money,
                    max_money,
                    sort_check
                ) { ca: ArrayList<Int>, location: ArrayList<Int>, min: String, max: String, sort: Int ->
                    if (ca.size != 0) {
                        viewe.list_studio_category.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.list_studio_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.list_studio_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_studio_category.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.list_studio_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.list_studio_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    if (location.size > 0) {
                        viewe.list_studio_location.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.list_location_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.list_location_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_studio_location.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.list_location_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.list_location_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    if (min != "" || max != "") {
                        viewe.list_studio_money.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.list_money_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.list_money_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_studio_money.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.list_money_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.list_money_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    if (sort != 0) {
                        viewe.list_studio_sort.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.list_sort_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.list_sort_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_studio_sort.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.list_sort_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.list_sort_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    studio_array = JsonArray()
                    categoryList = ArrayList()
                    location_array = JsonArray()
                    locationList = ArrayList()
                    la_click = ArrayList()

                    for (i in ca) {
                        studio_array.add(i)
                        categoryList.add(i)
                    }

                    for (i in location) {
                        location_array.add(i)
                        locationList.add(i)
                    }
                    min_money = min
                    max_money = max
                    sort_check = sort

                    datacall(
                        studio_array,
                        location_array,
                        datetime_array,
                        min_money,
                        max_money,
                        sort_check,
                        page_count
                    )
                }
                testiew.show(childFragmentManager, testiew.tag)
            }
            R.id.list_studio_location -> {
                val testiew: StudioAllDialog = StudioAllDialog(
                    1,
                    categoryList,
                    locationList,
                    min_money,
                    max_money,
                    sort_check
                ) { ca: ArrayList<Int>, location: ArrayList<Int>, min: String, max: String, sort: Int ->

                    if (ca.size != 0) {
                        viewe.list_studio_category.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.list_studio_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.list_studio_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_studio_category.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.list_studio_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.list_studio_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    if (location.size > 0) {
                        viewe.list_studio_location.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.list_location_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.list_location_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_studio_location.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.list_location_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.list_location_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    if (min != "" || max != "") {
                        viewe.list_studio_money.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.list_money_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.list_money_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_studio_money.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.list_money_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.list_money_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    if (sort != 0) {
                        viewe.list_studio_sort.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.list_sort_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.list_sort_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_studio_sort.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.list_sort_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.list_sort_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    studio_array = JsonArray()
                    categoryList = ArrayList()
                    location_array = JsonArray()
                    locationList = ArrayList()
                    la_click = ArrayList()

                    for (i in ca) {
                        studio_array.add(i)
                        categoryList.add(i)
                    }
                    for (i in location) {
                        location_array.add(i)
                        locationList.add(i)
                    }
                    min_money = min
                    max_money = max
                    sort_check = sort

                    datacall(
                        studio_array,
                        location_array,
                        datetime_array,
                        min_money,
                        max_money,
                        sort_check,
                        page_count
                    )
                }
                testiew.show(childFragmentManager, testiew.tag)
            }
            R.id.list_studio_money -> {
                val testiew: StudioAllDialog = StudioAllDialog(
                    2,
                    categoryList,
                    locationList,
                    min_money,
                    max_money,
                    sort_check
                ) { ca: ArrayList<Int>, location: ArrayList<Int>, min: String, max: String, sort: Int ->

                    if (ca.size != 0) {
                        viewe.list_studio_category.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.list_studio_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.list_studio_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_studio_category.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.list_studio_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.list_studio_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    if (location.size > 0) {
                        viewe.list_studio_location.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.list_location_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.list_location_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_studio_location.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.list_location_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.list_location_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    if (min != "" || max != "") {
                        viewe.list_studio_money.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.list_money_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.list_money_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_studio_money.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.list_money_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.list_money_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    if (sort != 0) {
                        viewe.list_studio_sort.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.list_sort_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.list_sort_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_studio_sort.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.list_sort_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.list_sort_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    studio_array = JsonArray()
                    categoryList = ArrayList()
                    location_array = JsonArray()
                    locationList = ArrayList()
                    la_click = ArrayList()

                    for (i in ca) {
                        studio_array.add(i)
                        categoryList.add(i)
                    }
                    for (i in location) {
                        location_array.add(i)
                        locationList.add(i)
                    }
                    min_money = min
                    max_money = max
                    sort_check = sort

                    datacall(
                        studio_array,
                        location_array,
                        datetime_array,
                        min_money,
                        max_money,
                        sort_check,
                        page_count
                    )
                }
                testiew.show(childFragmentManager, testiew.tag)
            }
            R.id.list_studio_sort -> {
                val testiew: StudioAllDialog = StudioAllDialog(3,categoryList,locationList,min_money,max_money,sort_check) {ca:ArrayList<Int>,location:ArrayList<Int>,min:String,max:String,sort:Int ->

                    if (ca.size != 0) {
                        viewe.list_studio_category.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.list_studio_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.list_studio_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_studio_category.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.list_studio_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.list_studio_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    if (location.size > 0) {
                        viewe.list_studio_location.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.list_location_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.list_location_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_studio_location.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.list_location_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.list_location_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    if (min != "" || max != "") {
                        viewe.list_studio_money.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.list_money_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.list_money_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_studio_money.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.list_money_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.list_money_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    if (sort != 0) {
                        viewe.list_studio_sort.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.list_sort_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.list_sort_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_studio_sort.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.list_sort_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.list_sort_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    studio_array = JsonArray()
                    categoryList = ArrayList()
                    location_array = JsonArray()
                    locationList = ArrayList()
                    la_click = ArrayList()

                    for (i in ca) {
                        studio_array.add(i)
                        categoryList.add(i)
                    }
                    for (i in location) {
                        location_array.add(i)
                        locationList.add(i)
                    }
                    min_money = min
                    max_money = max
                    sort_check = sort

                    datacall(
                        studio_array,
                        location_array,
                        datetime_array,
                        min_money,
                        max_money,
                        sort_check,
                        page_count
                    )
                }
                testiew.show(childFragmentManager, testiew.tag)
            }
        }
    }

}