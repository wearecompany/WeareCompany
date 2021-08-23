package com.weare.wearecompany.ui.listcontainer.model

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.JsonArray
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.List.clip
import com.weare.wearecompany.data.bottomnav.mypage.data.model
import com.weare.wearecompany.data.chatting.data.send
import com.weare.wearecompany.data.model.list.ChangeList
import com.weare.wearecompany.data.model.list.data.Dmodel
import com.weare.wearecompany.data.model.list.data.Mstate
import com.weare.wearecompany.data.model.list.data.newlist
import com.weare.wearecompany.data.retrofit.List.studio.modelListManager
import com.weare.wearecompany.ui.detail.model.ModelActivity
import com.weare.wearecompany.ui.listcontainer.ListContainerActivity
import com.weare.wearecompany.ui.listcontainer.MoneyDialog
import com.weare.wearecompany.ui.listcontainer.SortDialog
import com.weare.wearecompany.ui.listcontainer.photographer.PhotographerLocationDialog
import com.weare.wearecompany.utils.RESPONSE_STATUS
import kotlinx.android.synthetic.main.fragment_list_model.view.*


class ModelFragment : Fragment(
), View.OnClickListener {

    companion object {
        fun newInstance(): ModelFragment {
            return ModelFragment()
        }
    }

    private lateinit var layoutManager: GridLayoutManager
    private var ca_click = -1
    private var min_money = ""
    private var max_money = ""
    private var la_click = ArrayList<Int>()

    lateinit var viewe: View

    var numdsa:Int = 0


    lateinit var fadeInAnim: Animation
    lateinit var fadeoutAnim: Animation


    var Mcabool = false

    private var MsortCheck = 0

    private var page_count = 1

    var model_array = JsonArray()
    var model_location = JsonArray()
    val model_datatime = JsonArray()

    var clipmoney = ""
    private var clipList = ArrayList<clip>()
    private var modelDataList = ArrayList<Dmodel>()
    private var locationList = ArrayList<Int>()

    private lateinit var modelAdapter: ModelRecyclerViewAdapter
    private lateinit var newModelAdapter: ModelNewRecyclerViewAdapter
    private lateinit var clipAdapter: ModelClipRecyclerViewAdapter

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
        viewe = inflater!!.inflate(R.layout.fragment_list_model, container, false)

        datacall(
            model_array,
            model_location,
            model_datatime,
            min_money,
            max_money,
            MsortCheck,
            page_count
        )
        bindsetup()


        viewe.model_list_recycler.addOnScrollListener(object  : RecyclerView.OnScrollListener(){

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!viewe.model_list_recycler.canScrollVertically(1)) {
                    page_count += 1
                    modelListManager.instant.listdata(
                        model_array,
                        model_location,
                        model_datatime,
                        min_money,
                        max_money,
                        MsortCheck,
                        page_count,
                        completion = { responseStatus, responsenewlist, responsestudiokList ->
                            when (responseStatus) {
                                RESPONSE_STATUS.OKAY -> {
                                    for (i in responsestudiokList) {
                                        modelAdapter.addItem(i)
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

        viewe.list_model_category.setOnClickListener(this)
        viewe.list_model_location.setOnClickListener(this)
        viewe.list_model_money.setOnClickListener(this)
        viewe.list_model_sort.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.list_model_category -> {
                val testiew: ModelAllDialog = ModelAllDialog(0,ca_click,locationList,min_money,max_money,MsortCheck) {ca:Int,location:ArrayList<Int>,min:String,max:String,sort:Int ->

                    if (ca != -1) {
                        viewe.list_model_category.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.model_category_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.model_category_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_model_category.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.model_category_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.model_category_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    if (location.size > 0) {
                        viewe.list_model_location.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.model_location_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.model_location_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_model_location.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.model_location_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.model_location_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    if (min != "" || max != "") {
                        viewe.list_model_money.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.model_money_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.model_money_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_model_money.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.model_money_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.model_money_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    if (sort != 0) {
                        viewe.list_model_sort.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.model_sory_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.model_sory_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_model_sort.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.model_sory_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.model_sory_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    model_array = JsonArray()
                    model_location = JsonArray()
                    locationList = ArrayList()
                    la_click = ArrayList()

                    if (ca == -1) {
                        model_array = JsonArray()
                    } else {
                        model_array.add(ca)
                    }
                    ca_click = ca
                    for (i in location) {
                        model_location.add(i)
                        locationList.add(i)
                    }
                    min_money = min
                    max_money = max
                    MsortCheck = sort

                    datacall(
                        model_array,
                        model_location,
                        model_datatime,
                        min_money,
                        max_money,
                        MsortCheck,
                        1
                    )
                }
                testiew.show(childFragmentManager, testiew.tag)
            }
            R.id.list_model_location -> {

                val testiew: ModelAllDialog = ModelAllDialog(1,ca_click,locationList,min_money,max_money,MsortCheck) {ca:Int,location:ArrayList<Int>,min:String,max:String,sort:Int ->

                    if (ca != -1) {
                        viewe.list_model_category.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.model_category_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.model_category_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_model_category.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.model_category_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.model_category_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    if (location.size > 0) {
                        viewe.list_model_location.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.model_location_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.model_location_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_model_location.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.model_location_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.model_location_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    if (min != "" || max != "") {
                        viewe.list_model_money.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.model_money_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.model_money_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_model_money.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.model_money_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.model_money_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    if (sort != 0) {
                        viewe.list_model_sort.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.model_sory_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.model_sory_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_model_sort.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.model_sory_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.model_sory_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    model_array = JsonArray()
                    model_location = JsonArray()
                    locationList = ArrayList()

                    if (ca == -1) {
                        model_array = JsonArray()
                    } else {
                        model_array.add(ca)
                    }
                    ca_click = ca
                    for (i in location) {
                        model_location.add(i)
                        locationList.add(i)
                    }
                    min_money = min
                    max_money = max
                    MsortCheck = sort
                    datacall(
                        model_array,
                        model_location,
                        model_datatime,
                        min_money,
                        max_money,
                        MsortCheck,
                        1
                    )
                }
                testiew.show(childFragmentManager, testiew.tag)
            }
            R.id.list_model_money -> {
                val testiew: ModelAllDialog = ModelAllDialog(2,ca_click,locationList,min_money,max_money,MsortCheck) {ca:Int,location:ArrayList<Int>,min:String,max:String,sort:Int ->

                    if (ca != -1) {
                        viewe.list_model_category.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.model_category_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.model_category_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_model_category.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.model_category_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.model_category_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    if (location.size > 0) {
                        viewe.list_model_location.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.model_location_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.model_location_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_model_location.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.model_location_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.model_location_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    if (min != "" || max != "") {
                        viewe.list_model_money.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.model_money_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.model_money_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_model_money.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.model_money_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.model_money_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    if (sort != 0) {
                        viewe.list_model_sort.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.model_sory_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.model_sory_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_model_sort.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.model_sory_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.model_sory_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    model_array = JsonArray()
                    model_location = JsonArray()
                    locationList = ArrayList()

                    if (ca == -1) {
                        model_array = JsonArray()
                    } else {
                        model_array.add(ca)
                    }
                    ca_click = ca
                    for (i in location) {
                        model_location.add(i)
                        locationList.add(i)
                    }
                    min_money = min
                    max_money = max
                    MsortCheck = sort
                    datacall(
                        model_array,
                        model_location,
                        model_datatime,
                        min_money,
                        max_money,
                        MsortCheck,
                        1
                    )
                }
                testiew.show(childFragmentManager, testiew.tag)

            }
            R.id.list_model_sort -> {
                val testiew: ModelAllDialog = ModelAllDialog(3,ca_click,locationList,min_money,max_money,MsortCheck) {ca:Int,location:ArrayList<Int>,min:String,max:String,sort:Int ->

                    if (ca != -1) {
                        viewe.list_model_category.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.model_category_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.model_category_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_model_category.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.model_category_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.model_category_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    if (location.size > 0) {
                        viewe.list_model_location.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.model_location_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.model_location_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_model_location.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.model_location_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.model_location_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    if (min != "" || max != "") {
                        viewe.list_model_money.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.model_money_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.model_money_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_model_money.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.model_money_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.model_money_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    if (sort != 0) {
                        viewe.list_model_sort.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.model_sory_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.model_sory_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_model_sort.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.model_sory_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.model_sory_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    model_array = JsonArray()
                    model_location = JsonArray()
                    locationList = ArrayList()

                    if (ca == -1) {
                        model_array = JsonArray()
                    } else {
                        model_array.add(ca)
                    }
                    ca_click = ca
                    for (i in location) {
                        model_location.add(i)
                        locationList.add(i)
                    }
                    min_money = min
                    max_money = max
                    MsortCheck = sort
                    datacall(
                        model_array,
                        model_location,
                        model_datatime,
                        min_money,
                        max_money,
                        MsortCheck,
                        1
                    )
                }
                testiew.show(childFragmentManager, testiew.tag)
            }
        }
    }

    fun datacall(
        photorArray: JsonArray,
        locationArray: JsonArray,
        timeArray: JsonArray,
        min: String,
        max: String,
        sort: Int,
        page: Int
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

        modelListManager.instant.listdata(
            photorArray,
            locationArray,
            timeArray,
            min,
            max,
            sort,
            page,
            completion = { responseStatus, responsenewlist, responsestudiokList ->

                when (responseStatus) {
                    RESPONSE_STATUS.OKAY -> {

                        newModelAdapter = ModelNewRecyclerViewAdapter(responsenewlist)
                        viewe.list_model_new_recuclerview.layoutManager =
                            LinearLayoutManager(
                                MyApplication.instance,
                                LinearLayoutManager.HORIZONTAL, false
                            )
                        viewe.list_model_new_recuclerview.adapter = newModelAdapter

                        newModelAdapter.setItemClickListener(object :
                            ModelNewRecyclerViewAdapter.OnItemClickListener {
                            override fun onClick(v: View, position: Int, Item: newlist) {
                                var newIntent = Intent(context, ModelActivity::class.java)
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
                        modelDataList = responsestudiokList
                        modelAdapter = ModelRecyclerViewAdapter(modelDataList)

                        layoutManager = GridLayoutManager(mContext, 2)


                        viewe.model_list_recycler.layoutManager = layoutManager

                        viewe.model_list_recycler.adapter = modelAdapter
                        viewe.model_list_recycler.setHasFixedSize(true)

                        modelAdapter.setItemClickListener(object :
                            ModelRecyclerViewAdapter.OnItemClickListener {
                            override fun onClick(v: View, position: Int, Item: Dmodel) {
                                if (!Mcabool) {
                                    var newIntent = Intent(context, ModelActivity::class.java)

                                    newIntent.putExtra("expert_idx", Item.idx)
                                    newIntent.putExtra(
                                        "user_idx", MyApplication.prefs.getString(
                                            "user_idx",
                                            ""
                                        )
                                    )
                                    startActivity(newIntent)
                                }
                            }
                        })

                        if (clipList.size != 0) {
                            viewe.list_model_clip_recyclerview.visibility = View.VISIBLE
                            clipAdapter = ModelClipRecyclerViewAdapter(clipList)
                            viewe.list_model_clip_recyclerview.layoutManager =
                                LinearLayoutManager(
                                    MyApplication.instance,
                                    LinearLayoutManager.HORIZONTAL, false
                                )
                            viewe.list_model_clip_recyclerview.adapter = clipAdapter

                            clipAdapter.setItemClickListener(object : ModelClipRecyclerViewAdapter.OnItemClickListener{
                                override fun onClick(v: View, position: Int, item: clip) {
                                    when(item.main_type) {
                                        0 -> {
                                            ca_click = -1
                                            model_array = JsonArray()
                                            viewe.list_model_category.setBackgroundResource(R.drawable.list_sub_background_off)
                                            viewe.model_category_image.setImageResource(R.drawable.list_category_down_gray)
                                            viewe.model_category_text.setTextColor(Color.parseColor("#7f7f7f"))
                                        }
                                        1 -> {
                                            if (model_location.size() != 0) {
                                                val i1 = 1
                                                for (i in i1..model_location.size()) {
                                                    if(item.sub_type == model_location[i-1].asInt) {
                                                        model_location.remove(i-1)
                                                        locationList.removeAt(i-1)
                                                        break
                                                    }
                                                }

                                            }
                                        }
                                        2 -> {
                                            min_money = ""
                                            max_money = ""
                                            viewe.list_model_money.setBackgroundResource(R.drawable.list_sub_background_off)
                                            viewe.model_money_image.setImageResource(R.drawable.list_category_down_gray)
                                            viewe.model_money_text.setTextColor(Color.parseColor("#7f7f7f"))
                                        }
                                        3 -> {
                                            MsortCheck = 0
                                            viewe.list_model_sort.setBackgroundResource(R.drawable.list_sub_background_off)
                                            viewe.model_sory_image.setImageResource(R.drawable.list_category_down_gray)
                                            viewe.model_sory_text.setTextColor(Color.parseColor("#7f7f7f"))
                                        }
                                    }
                                    if (model_location.size() == 0) {
                                        viewe.list_model_location.setBackgroundResource(R.drawable.list_sub_background_off)
                                        viewe.model_location_image.setImageResource(R.drawable.list_category_down_gray)
                                        viewe.model_location_text.setTextColor(Color.parseColor("#7f7f7f"))
                                    }
                                    datacall(
                                        model_array,
                                        model_location,
                                        model_datatime,
                                        min_money,
                                        max_money,
                                        MsortCheck,
                                        page_count
                                    )
                                }
                            })

                        } else {
                            viewe.list_model_clip_recyclerview.visibility = View.GONE
                        }
                    }
                }
            })
    }

}