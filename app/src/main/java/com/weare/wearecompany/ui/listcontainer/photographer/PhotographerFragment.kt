package com.weare.wearecompany.ui.listcontainer.photographer

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.List.model.photographer.data.photo
import com.weare.wearecompany.data.retrofit.List.studio.photographerListManager
import com.weare.wearecompany.ui.detail.photo.PhotoActivity
import com.weare.wearecompany.ui.listcontainer.ListContainerActivity
import com.weare.wearecompany.utils.RESPONSE_STATUS
import com.google.gson.JsonArray
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.data.List.clip
import com.weare.wearecompany.data.model.list.data.newlist
import com.weare.wearecompany.ui.listcontainer.model.ModelNewRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_list_photographer.view.*

class PhotographerFragment : Fragment(
), View.OnClickListener {

    private var ca_click = -1
    private var min_money = ""
    private var max_money = ""
    private var nick_name = ""
    private var la_click = ArrayList<Int>()
    private var locationList = ArrayList<Int>()

    var clipmoney = ""
    private var clipList = ArrayList<clip>()

    private var page_count = 1

    lateinit var viewe: View


    var cabool = false


    private var Psort_check = 0

    var photor_category_array = JsonArray()
    var photor_location_array = JsonArray()
    val photor_datetime_array = JsonArray()

    private lateinit var clipAdapter: PhotoClipRecyclerViewAdapter
    private var photographerDataList = ArrayList<photo>()
    private lateinit var photoAdapter: PhotographerRecyclerViewAdapter
    private lateinit var newPhotoAdapter: ModelNewRecyclerViewAdapter

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
        viewe = inflater!!.inflate(R.layout.fragment_list_photographer, container, false)

        datacall(
            photor_category_array,
            photor_location_array,
            photor_datetime_array,
            min_money,
            max_money,
            nick_name,
            Psort_check,
            1
        )
        bindsetup()

        viewe.photo_list_search_edit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (viewe.photo_list_search_edit.isFocusable && !s.toString().equals("")) {
                    viewe.photo_list_search_image_1.visibility = View.VISIBLE
                    viewe.photo_list_search_image_2.visibility = View.GONE
                } else if (viewe.photo_list_search_edit.isFocusable && s.toString().equals("")) {
                    viewe.photo_list_search_image_1.visibility = View.GONE
                    viewe.photo_list_search_image_2.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        viewe.photo_list_search_edit.setOnEditorActionListener(object : TextView.OnEditorActionListener{
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
               when(actionId) {
                   EditorInfo.IME_ACTION_DONE -> {
                       nick_name = viewe.photo_list_search_edit.text.toString()
                       datacall(
                           photor_category_array,
                           photor_location_array,
                           photor_datetime_array,
                           min_money,
                           max_money,
                           nick_name,
                           Psort_check,
                           page_count
                       )
                       return false
                   }
                   else -> {
                       return false
                   }
               }
                return true
            }

        })


        viewe.photo_list_recycler.addOnScrollListener(object  : RecyclerView.OnScrollListener(){

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!viewe.photo_list_recycler.canScrollVertically(1)) {
                    page_count += 1
                    photographerListManager.instant.listdata(
                        photor_category_array,
                        photor_location_array,
                        photor_datetime_array,
                        min_money,
                        max_money,
                        nick_name,
                        Psort_check,
                        page_count,
                        completion = { responseStatus, responsenewlist, responsestudiokList ->
                            when (responseStatus) {
                                RESPONSE_STATUS.OKAY -> {
                                    for (i in responsestudiokList) {
                                        photoAdapter.addItem(i)
                                    }
                                }
                            }
                        })
                }
            }
        })

        return viewe
    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 999) {
            datacall(
                photor_category_array,
                photor_location_array,
                photor_datetime_array,
                mViewDataBinding.moneyMin.text.toString(),
                mViewDataBinding.moneyMax.text.toString(),
                Psort_check,
                1
            )
        }
    }*/

    fun bindsetup() {
        viewe.list_photo_category.setOnClickListener(this)
        viewe.list_photo_location.setOnClickListener(this)
        viewe.list_photo_money.setOnClickListener(this)
        viewe.list_photo_sort.setOnClickListener(this)
        viewe.photo_list_search_btn.setOnClickListener(this)
        viewe.photo_list_search_image_1.setOnClickListener(this)
    }


    fun datacall(
        photorArray: JsonArray,
        locationArray: JsonArray,
        timeArray: JsonArray,
        min: String,
        max: String,
        nickname: String,
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
        photographerListManager.instant.listdata(
            photorArray, locationArray, timeArray, min, max, nickname, sort, page,
            completion = { responseStatus, responsenewlist,responsestudiokList ->

                when (responseStatus) {
                    RESPONSE_STATUS.OKAY -> {

                            newPhotoAdapter = ModelNewRecyclerViewAdapter(responsenewlist)
                            viewe.list_photo_new_recyclerview.layoutManager = LinearLayoutManager(
                                MyApplication.instance,
                                LinearLayoutManager.HORIZONTAL, false
                            )
                            viewe.list_photo_new_recyclerview.adapter = newPhotoAdapter

                            newPhotoAdapter.setItemClickListener(object : ModelNewRecyclerViewAdapter.OnItemClickListener{
                                override fun onClick(v: View, position: Int, Item: newlist) {
                                    var newIntent = Intent(context, PhotoActivity::class.java)
                                    newIntent.putExtra("expert_idx", Item.idx)
                                    newIntent.putExtra("user_idx", MyApplication.prefs.getString("user_idx", ""))
                                    startActivity(newIntent)
                                }

                            })

                        if (responsestudiokList.size != 0) {
                            photographerDataList = responsestudiokList
                            photoAdapter =
                                PhotographerRecyclerViewAdapter(photographerDataList)
                            viewe.photo_list_recycler.layoutManager =
                                GridLayoutManager(context, 2)
                            viewe.photo_list_recycler.adapter = photoAdapter

                            photoAdapter.setItemClickListener(object :
                                PhotographerRecyclerViewAdapter.OnItemClickListener {
                                override fun onClick(v: View, position: Int, Item: photo) {
                                    if (!cabool) {
                                        var newIntent = Intent(context, PhotoActivity::class.java)

                                        newIntent.putExtra("expert_idx", Item.idx)
                                        newIntent.putExtra("user_idx", MyApplication.prefs.getString("user_idx", ""))
                                        startActivity(newIntent)
                                        //startActivity(newIntent)
                                    }
                                }
                            })

                            if (clipList.size != 0) {
                                viewe.list_photo_clip_recyclerview.visibility = View.VISIBLE
                                clipAdapter = PhotoClipRecyclerViewAdapter(clipList)
                                viewe.list_photo_clip_recyclerview.layoutManager =
                                    LinearLayoutManager(
                                        MyApplication.instance,
                                        LinearLayoutManager.HORIZONTAL, false
                                    )
                                viewe.list_photo_clip_recyclerview.adapter = clipAdapter

                                clipAdapter.setItemClickListener(object : PhotoClipRecyclerViewAdapter.OnItemClickListener{
                                    override fun onClick(v: View, position: Int, item: clip) {
                                        when(item.main_type) {
                                            0 -> {
                                                ca_click = -1
                                                photor_category_array = JsonArray()
                                                viewe.list_photo_category.setBackgroundResource(R.drawable.list_sub_background_off)
                                                viewe.photor_category_image.setImageResource(R.drawable.list_category_down_gray)
                                                viewe.photor_category_text.setTextColor(Color.parseColor("#7f7f7f"))
                                            }
                                            1 -> {
                                                if (photor_location_array.size() != 0) {
                                                    val i1 = 1
                                                    for (i in i1..photor_location_array.size()) {
                                                        if(item.sub_type == photor_location_array[i-1].asInt) {
                                                            photor_location_array.remove(i-1)
                                                            locationList.removeAt(i-1)
                                                            break
                                                        }
                                                    }

                                                }
                                            }
                                            2 -> {
                                                min_money = ""
                                                max_money = ""
                                                viewe.list_photo_money.setBackgroundResource(R.drawable.list_sub_background_off)
                                                viewe.photo_money_image.setImageResource(R.drawable.list_category_down_gray)
                                                viewe.photo_money_text.setTextColor(Color.parseColor("#7f7f7f"))
                                            }
                                            3 -> {
                                                Psort_check = 0
                                                viewe.list_photo_sort.setBackgroundResource(R.drawable.list_sub_background_off)
                                                viewe.photo_sort_image.setImageResource(R.drawable.list_category_down_gray)
                                                viewe.photo_sort_text.setTextColor(Color.parseColor("#7f7f7f"))
                                            }
                                        }
                                        if (photor_location_array.size() == 0) {
                                            viewe.list_photo_location.setBackgroundResource(R.drawable.list_sub_background_off)
                                            viewe.photor_location_image.setImageResource(R.drawable.list_category_down_gray)
                                            viewe.photor_location_text.setTextColor(Color.parseColor("#7f7f7f"))
                                        }
                                        datacall(
                                            photor_category_array,
                                            photor_location_array,
                                            photor_datetime_array,
                                            min_money,
                                            max_money,
                                            nick_name,
                                            Psort_check,
                                            page_count
                                        )
                                    }
                                })

                            } else {
                                viewe.list_photo_clip_recyclerview.visibility = View.GONE
                            }
                        } else {
                            Toast.makeText(mContext,"조건에 맞는 전문가가 없습니다.",Toast.LENGTH_SHORT).show()
                        }


                    }
                }
            })
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.photo_list_search_image_1 -> {
                viewe.photo_list_search_image_1.visibility = View.GONE
                viewe.photo_list_search_image_2.visibility = View.VISIBLE
                viewe.photo_list_search_edit.setText("")
                nick_name = ""
                datacall(
                    photor_category_array,
                    photor_location_array,
                    photor_datetime_array,
                    min_money,
                    max_money,
                    nick_name,
                    Psort_check,
                    page_count
                )
            }
            R.id.list_photo_category -> {
                val testiew: PhotoAllDialog = PhotoAllDialog(
                    0,
                    ca_click,
                    locationList,
                    min_money,
                    max_money,
                    Psort_check
                ) { ca: Int, location: ArrayList<Int>, min: String, max: String, sort: Int ->
                    if (ca != -1) {
                        viewe.list_photo_category.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.photor_category_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.photor_category_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_photo_category.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.photor_category_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.photor_category_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    if (location.size > 0) {
                        viewe.list_photo_location.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.photor_location_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.photor_location_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_photo_location.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.photor_location_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.photor_location_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    if (min != "" || max != "") {
                        viewe.list_photo_money.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.photo_money_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.photo_money_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_photo_money.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.photo_money_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.photo_money_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    if (sort != 0) {
                        viewe.list_photo_sort.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.photo_sort_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.photo_sort_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_photo_sort.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.photo_sort_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.photo_sort_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    photor_category_array = JsonArray()
                    photor_location_array = JsonArray()
                    locationList = ArrayList()
                    la_click = ArrayList()

                    if (ca == -1) {
                        photor_category_array = JsonArray()
                    } else {
                        photor_category_array.add(ca)
                    }
                    ca_click = ca
                    for (i in location) {
                        photor_location_array.add(i)
                        locationList.add(i)
                    }
                    min_money = min
                    max_money = max
                    Psort_check = sort

                    datacall(
                        photor_category_array,
                        photor_location_array,
                        photor_datetime_array,
                        min_money,
                        max_money,
                        nick_name,
                        Psort_check,
                        page_count
                    )
                }
                testiew.show(childFragmentManager, testiew.tag)
            }
            R.id.list_photo_location -> {
                val testiew: PhotoAllDialog = PhotoAllDialog(1,ca_click,locationList,min_money,max_money,Psort_check) {ca:Int,location:ArrayList<Int>,min:String,max:String,sort:Int ->

                    if (ca != -1) {
                        viewe.list_photo_category.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.photor_category_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.photor_category_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_photo_category.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.photor_category_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.photor_category_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    if (location.size > 0) {
                        viewe.list_photo_location.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.photor_location_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.photor_location_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_photo_location.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.photor_location_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.photor_location_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    if (min != "" || max != "") {
                        viewe.list_photo_money.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.photo_money_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.photo_money_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_photo_money.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.photo_money_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.photo_money_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    if (sort != 0) {
                        viewe.list_photo_sort.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.photo_sort_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.photo_sort_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_photo_sort.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.photo_sort_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.photo_sort_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    photor_category_array = JsonArray()
                    photor_location_array = JsonArray()
                    locationList = ArrayList()

                    if (ca == -1) {
                        photor_category_array = JsonArray()
                    } else {
                        photor_category_array.add(ca)
                    }
                    ca_click = ca
                    for (i in location) {
                        photor_location_array.add(i)
                        locationList.add(i)
                    }
                    min_money = min
                    max_money = max
                    Psort_check = sort
                    datacall(
                        photor_category_array,
                        photor_location_array,
                        photor_datetime_array,
                        min_money,
                        max_money,
                        nick_name,
                        Psort_check,
                        page_count
                    )
                }
                testiew.show(childFragmentManager, testiew.tag)
            }
            R.id.list_photo_money -> {
                val testiew: PhotoAllDialog = PhotoAllDialog(2,ca_click,locationList,min_money,max_money,Psort_check) {ca:Int,location:ArrayList<Int>,min:String,max:String,sort:Int ->

                    if (ca != -1) {
                        viewe.list_photo_category.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.photor_category_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.photor_category_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_photo_category.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.photor_category_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.photor_category_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    if (location.size > 0) {
                        viewe.list_photo_location.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.photor_location_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.photor_location_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_photo_location.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.photor_location_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.photor_location_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    if (min != "" || max != "") {
                        viewe.list_photo_money.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.photo_money_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.photo_money_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_photo_money.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.photo_money_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.photo_money_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    if (sort != 0) {
                        viewe.list_photo_sort.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.photo_sort_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.photo_sort_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_photo_sort.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.photo_sort_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.photo_sort_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    photor_category_array = JsonArray()
                    photor_location_array = JsonArray()
                    locationList = ArrayList()

                    if (ca == -1) {
                        photor_category_array = JsonArray()
                    } else {
                        photor_category_array.add(ca)
                    }
                    ca_click = ca
                    for (i in location) {
                        photor_location_array.add(i)
                        locationList.add(i)
                    }
                    min_money = min
                    max_money = max
                    Psort_check = sort
                    datacall(
                        photor_category_array,
                        photor_location_array,
                        photor_datetime_array,
                        min_money,
                        max_money,
                        nick_name,
                        Psort_check,
                        page_count
                    )
                }
                testiew.show(childFragmentManager, testiew.tag)
            }
            R.id.list_photo_sort -> {
                val testiew: PhotoAllDialog = PhotoAllDialog(3,ca_click,locationList,min_money,max_money,Psort_check) {ca:Int,location:ArrayList<Int>,min:String,max:String,sort:Int ->

                    if (ca != -1) {
                        viewe.list_photo_category.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.photor_category_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.photor_category_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_photo_category.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.photor_category_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.photor_category_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    if (location.size > 0) {
                        viewe.list_photo_location.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.photor_location_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.photor_location_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_photo_location.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.photor_location_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.photor_location_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    if (min != "" || max != "") {
                        viewe.list_photo_money.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.photo_money_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.photo_money_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_photo_money.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.photo_money_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.photo_money_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    if (sort != 0) {
                        viewe.list_photo_sort.setBackgroundResource(R.drawable.list_sub_background_on)
                        viewe.photo_sort_image.setImageResource(R.drawable.list_category_down_puple)
                        viewe.photo_sort_text.setTextColor(Color.parseColor("#6d34f3"))
                    } else {
                        viewe.list_photo_sort.setBackgroundResource(R.drawable.list_sub_background_off)
                        viewe.photo_sort_image.setImageResource(R.drawable.list_category_down_gray)
                        viewe.photo_sort_text.setTextColor(Color.parseColor("#7f7f7f"))
                    }

                    photor_category_array = JsonArray()
                    photor_location_array = JsonArray()
                    locationList = ArrayList()

                    if (ca == -1) {
                        photor_category_array = JsonArray()
                    } else {
                        photor_category_array.add(ca)
                    }
                    ca_click = ca
                    for (i in location) {
                        photor_location_array.add(i)
                        locationList.add(i)
                    }
                    min_money = min
                    max_money = max
                    Psort_check = sort
                    datacall(
                        photor_category_array,
                        photor_location_array,
                        photor_datetime_array,
                        min_money,
                        max_money,
                        nick_name,
                        Psort_check,
                        page_count
                    )
                }
                testiew.show(childFragmentManager, testiew.tag)
            }
        }
    }
}