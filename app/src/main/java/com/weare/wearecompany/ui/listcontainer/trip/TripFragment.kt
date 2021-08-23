package com.weare.wearecompany.ui.listcontainer.trip

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonArray
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.List.clip
import com.weare.wearecompany.data.List.trip.data.trip
import com.weare.wearecompany.data.model.list.data.newlist
import com.weare.wearecompany.data.retrofit.List.studio.modelListManager
import com.weare.wearecompany.data.retrofit.List.studio.tripListManager
import com.weare.wearecompany.databinding.FragmentListTripBinding
import com.weare.wearecompany.ui.base.BaseFragment
import com.weare.wearecompany.ui.detail.trip.TripActivity
import com.weare.wearecompany.ui.listcontainer.ListContainerActivity
import com.weare.wearecompany.ui.listcontainer.SortDialog
import com.weare.wearecompany.ui.listcontainer.model.ModelAllDialog
import com.weare.wearecompany.ui.listcontainer.model.ModelClipRecyclerViewAdapter
import com.weare.wearecompany.ui.listcontainer.model.ModelNewRecyclerViewAdapter
import com.weare.wearecompany.ui.listcontainer.photographer.PhotographerLocationDialog
import com.weare.wearecompany.utils.RESPONSE_STATUS
import kotlinx.android.synthetic.main.fragment_list_model.view.*
import kotlinx.android.synthetic.main.fragment_list_model.view.model_list_recycler
import kotlinx.android.synthetic.main.fragment_list_trip.view.*

class TripFragment : Fragment(
), View.OnClickListener {

    private var ca_click = -1
    private var min_money = ""
    private var max_money = ""
    private var la_click = ArrayList<Int>()
    private var page_count = 1
    lateinit var viewe: View

    var clipmoney = ""
    private var clipList = ArrayList<clip>()

    private lateinit var clipAdapter: TripClipRecyclerViewAdapter
    private var locationList = ArrayList<Int>()

    private var BsortCheck = 0

    var beauty_array = JsonArray()
    var beauty_location = JsonArray()

    private var tripDataList = ArrayList<trip>()
    private lateinit var tripAdapter: TripRecyclerViewAdapter
    private lateinit var newTripAdapter: ModelNewRecyclerViewAdapter

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
        viewe = inflater!!.inflate(R.layout.fragment_list_trip, container, false)

        datacall(beauty_array, beauty_location,min_money,max_money,BsortCheck, page_count)
        bindsetup()


        viewe.trip_list_recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!viewe.trip_list_recyclerview.canScrollVertically(1)) {
                    page_count += 1
                    tripListManager.instant.listdata(
                        beauty_array,
                        beauty_location,
                        min_money,
                        max_money,
                        BsortCheck,
                        page_count,
                        completion = { responseStatus, responsenewlist, responsestudiokList ->
                            when (responseStatus) {
                                RESPONSE_STATUS.OKAY -> {
                                    for (i in responsestudiokList) {
                                        tripAdapter.addItem(i)
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
        viewe.list_trip_category.setOnClickListener(this)
        viewe.list_trip_location.setOnClickListener(this)
        viewe.list_trip_money.setOnClickListener(this)
        viewe.list_trip_sort.setOnClickListener(this)
    }


    fun datacall(
        photorArray: JsonArray, locationArray: JsonArray, min: String,
        max: String, sort: Int, page: Int
    ) {
        clipList = ArrayList()
        if (photorArray.size() != 0) {
            for (i in photorArray) {
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

        tripListManager.instant.listdata(
            photorArray,
            locationArray,
            min,
            max,
            sort,
            page,
            completion = { responseStatus, responsenewlist, responsestudiokList ->

                when (responseStatus) {
                    RESPONSE_STATUS.OKAY -> {
                        newTripAdapter = ModelNewRecyclerViewAdapter(responsenewlist)
                        viewe.list_trip_new_recyclerview.layoutManager =
                            LinearLayoutManager(
                                MyApplication.instance,
                                LinearLayoutManager.HORIZONTAL, false
                            )
                        viewe.list_trip_new_recyclerview.adapter = newTripAdapter

                        newTripAdapter.setItemClickListener(object :
                            ModelNewRecyclerViewAdapter.OnItemClickListener {
                            override fun onClick(v: View, position: Int, Item: newlist) {
                                var newIntent = Intent(context, TripActivity::class.java)

                                newIntent.putExtra("expert_idx", Item.idx)
                                newIntent.putExtra(
                                    "user_idx", MyApplication.prefs.getString(
                                        "user_idx",
                                        ""
                                    )
                                )
                                startActivity(newIntent)
                            }

                        })
                        tripDataList = responsestudiokList
                        tripAdapter = TripRecyclerViewAdapter(tripDataList)

                        viewe.trip_list_recyclerview.layoutManager =
                            GridLayoutManager(context, 2)
                        viewe.trip_list_recyclerview.adapter = tripAdapter

                        tripAdapter.setItemClickListener(object :
                            TripRecyclerViewAdapter.OnItemClickListener {
                            override fun onClick(v: View, position: Int, Item: trip) {
                                var newIntent = Intent(context, TripActivity::class.java)

                                newIntent.putExtra("expert_idx", Item.idx)
                                newIntent.putExtra(
                                    "user_idx", MyApplication.prefs.getString(
                                        "user_idx",
                                        ""
                                    )
                                )
                                startActivity(newIntent)
                            }

                        })

                        if (clipList.size != 0) {
                            viewe.list_trip_clip_recyclerview.visibility = View.VISIBLE
                            clipAdapter = TripClipRecyclerViewAdapter(clipList)
                            viewe.list_trip_clip_recyclerview.layoutManager =
                                LinearLayoutManager(
                                    MyApplication.instance,
                                    LinearLayoutManager.HORIZONTAL, false
                                )
                            viewe.list_trip_clip_recyclerview.adapter = clipAdapter

                            clipAdapter.setItemClickListener(object : TripClipRecyclerViewAdapter.OnItemClickListener{
                                override fun onClick(v: View, position: Int, item: clip) {
                                    when(item.main_type) {
                                        0 -> {
                                            ca_click = -1
                                            beauty_array = JsonArray()
                                            viewe.list_trip_category.setBackgroundResource(R.drawable.list_sub_background_off)
                                            viewe.trip_category_image.setImageResource(R.drawable.list_category_down_gray)
                                            viewe.trip_category_text.setTextColor(Color.parseColor("#7f7f7f"))
                                        }
                                        1 -> {
                                            if (beauty_location.size() != 0) {
                                                val i1 = 1
                                                for (i in i1..beauty_location.size()) {
                                                    if(item.sub_type == beauty_location[i-1].asInt) {
                                                        beauty_location.remove(i-1)
                                                        locationList.removeAt(i-1)
                                                        break
                                                    }
                                                }

                                            }
                                        }
                                        2 -> {
                                            min_money = ""
                                            max_money = ""
                                            viewe.list_trip_money.setBackgroundResource(R.drawable.list_sub_background_off)
                                            viewe.trip_money_image.setImageResource(R.drawable.list_category_down_gray)
                                            viewe.trip_money_text.setTextColor(Color.parseColor("#7f7f7f"))
                                        }
                                        3 -> {
                                            BsortCheck = 0
                                            viewe.list_trip_sort.setBackgroundResource(R.drawable.list_sub_background_off)
                                            viewe.trip_sory_image.setImageResource(R.drawable.list_category_down_gray)
                                            viewe.trip_sory_text.setTextColor(Color.parseColor("#7f7f7f"))
                                        }
                                    }
                                    if (beauty_location.size() == 0) {
                                        viewe.list_trip_location.setBackgroundResource(R.drawable.list_sub_background_off)
                                        viewe.trip_location_image.setImageResource(R.drawable.list_category_down_gray)
                                        viewe.trip_location_text.setTextColor(Color.parseColor("#7f7f7f"))
                                    }
                                    datacall(beauty_array, beauty_location, min_money, max_money, BsortCheck, page_count)
                                }
                            })

                        } else {
                            viewe.list_trip_clip_recyclerview.visibility = View.GONE
                        }

                    }
                }
            })
    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.list_trip_category -> {
                val testiew: TripAllDialog = TripAllDialog(
                    0,
                    ca_click,
                    locationList,
                    min_money,
                    max_money,
                    BsortCheck
                ) { ca: Int, location: ArrayList<Int>, min: String, max: String, sort: Int ->
                    if (ca != -1) {
                        viewe.list_trip_category.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.trip_category_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.trip_category_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_trip_category.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.trip_category_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.trip_category_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    if (location.size > 0) {
                        viewe.list_trip_location.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.trip_location_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.trip_location_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_trip_location.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.trip_location_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.trip_location_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    if (min != "" || max != "") {
                        viewe.list_trip_money.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.trip_money_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.trip_money_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_trip_money.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.trip_money_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.trip_money_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    if (sort != 0) {
                        viewe.list_trip_sort.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.trip_sory_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.trip_sory_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_trip_sort.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.trip_sory_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.trip_sory_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    beauty_array = JsonArray()
                    beauty_location = JsonArray()
                    locationList = ArrayList()
                    la_click = ArrayList()

                    if (ca == -1) {
                        beauty_array = JsonArray()
                    } else {
                        beauty_array.add(ca)
                    }
                    ca_click = ca
                    for (i in location) {
                        beauty_location.add(i)
                        locationList.add(i)
                    }
                    min_money = min
                    max_money = max
                    BsortCheck = sort

                    datacall(beauty_array, beauty_location, min_money, max_money, BsortCheck, page_count)
                }
                testiew.show(childFragmentManager, testiew.tag)
            }
            R.id.list_trip_location -> {
                val testiew: TripAllDialog = TripAllDialog(1,ca_click,locationList,min_money,max_money,BsortCheck) {ca:Int,location:ArrayList<Int>,min:String,max:String,sort:Int ->

                    if (ca != -1) {
                        viewe.list_trip_category.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.trip_category_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.trip_category_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_trip_category.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.trip_category_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.trip_category_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    if (location.size > 0) {
                        viewe.list_trip_location.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.trip_location_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.trip_location_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_trip_location.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.trip_location_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.trip_location_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    if (min != "" || max != "") {
                        viewe.list_trip_money.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.trip_money_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.trip_money_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_trip_money.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.trip_money_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.trip_money_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    if (sort != 0) {
                        viewe.list_trip_sort.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.trip_sory_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.trip_sory_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_trip_sort.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.trip_sory_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.trip_sory_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    beauty_array = JsonArray()
                    beauty_location = JsonArray()
                    locationList = ArrayList()

                    if (ca == -1) {
                        beauty_array = JsonArray()
                    } else {
                        beauty_array.add(ca)
                    }
                    ca_click = ca
                    for (i in location) {
                        beauty_location.add(i)
                        locationList.add(i)
                    }
                    min_money = min
                    max_money = max
                    BsortCheck = sort
                    datacall(beauty_array, beauty_location, min_money, max_money, BsortCheck, page_count)
                }
                testiew.show(childFragmentManager, testiew.tag)
            }
            R.id.list_trip_money -> {
                val testiew: TripAllDialog = TripAllDialog(2,ca_click,locationList,min_money,max_money,BsortCheck) {ca:Int,location:ArrayList<Int>,min:String,max:String,sort:Int ->

                    if (ca != -1) {
                        viewe.list_trip_category.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.trip_category_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.trip_category_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_trip_category.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.trip_category_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.trip_category_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    if (location.size > 0) {
                        viewe.list_trip_location.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.trip_location_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.trip_location_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_trip_location.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.trip_location_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.trip_location_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    if (min != "" || max != "") {
                        viewe.list_trip_money.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.trip_money_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.trip_money_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_trip_money.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.trip_money_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.trip_money_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    if (sort != 0) {
                        viewe.list_trip_sort.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.trip_sory_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.trip_sory_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_trip_sort.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.trip_sory_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.trip_sory_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    beauty_array = JsonArray()
                    beauty_location = JsonArray()
                    locationList = ArrayList()

                    if (ca == -1) {
                        beauty_array = JsonArray()
                    } else {
                        beauty_array.add(ca)
                    }
                    ca_click = ca
                    for (i in location) {
                        beauty_location.add(i)
                        locationList.add(i)
                    }
                    min_money = min
                    max_money = max
                    BsortCheck = sort
                    datacall(beauty_array, beauty_location, min_money, max_money, BsortCheck, page_count)
                }
                testiew.show(childFragmentManager, testiew.tag)
            }
            R.id.list_trip_sort -> {
                val testiew: TripAllDialog = TripAllDialog(3,ca_click,locationList,min_money,max_money,BsortCheck) {ca:Int,location:ArrayList<Int>,min:String,max:String,sort:Int ->

                    if (ca != -1) {
                        viewe.list_trip_category.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.trip_category_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.trip_category_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_trip_category.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.trip_category_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.trip_category_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    if (location.size > 0) {
                        viewe.list_trip_location.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.trip_location_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.trip_location_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_trip_location.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.trip_location_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.trip_location_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    if (min != "" || max != "") {
                        viewe.list_trip_money.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.trip_money_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.trip_money_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_trip_money.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.trip_money_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.trip_money_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    if (sort != 0) {
                        viewe.list_trip_sort.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.trip_sory_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.trip_sory_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_trip_sort.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.trip_sory_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.trip_sory_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    beauty_array = JsonArray()
                    beauty_location = JsonArray()
                    locationList = ArrayList()

                    if (ca == -1) {
                        beauty_array = JsonArray()
                    } else {
                        beauty_array.add(ca)
                    }
                    ca_click = ca
                    for (i in location) {
                        beauty_location.add(i)
                        locationList.add(i)
                    }
                    min_money = min
                    max_money = max
                    BsortCheck = sort
                    datacall(beauty_array, beauty_location, min_money, max_money, BsortCheck, page_count)
                }
                testiew.show(childFragmentManager, testiew.tag)
            }

        }

    }
}