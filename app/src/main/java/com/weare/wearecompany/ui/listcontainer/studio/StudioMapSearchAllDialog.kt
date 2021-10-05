package com.weare.wearecompany.ui.listcontainer.studio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.JsonArray
import com.weare.wearecompany.R
import com.weare.wearecompany.data.main.data.studio
import com.weare.wearecompany.data.retrofit.List.studio.studioListManager
import com.weare.wearecompany.ui.bottommenu.MainHomePageAdapter
import com.weare.wearecompany.utils.RESPONSE_STATUS
import kotlinx.android.synthetic.main.bottom_dialog_all_model.view.*

class StudioMapSearchAllDialog(
    var postion: Int,
    val category: ArrayList<Int>,
    val location: ArrayList<Int>,
    val min: String,
    val max: String,
    val sort: Int,
    val itemClick: (ArrayList<Int>, ArrayList<Int>, String, String, Int,ArrayList<studio>) -> Unit
) : BottomSheetDialogFragment() {

    var studio_array = JsonArray()
    var location_array = JsonArray()
    val datetime_array = JsonArray()
    lateinit var check_min:String
    lateinit var check_max:String

    private val viewmodel: StudioViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.bottom_dialog_all_model, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dateSetUp()

        val adapter = MainHomePageAdapter(childFragmentManager)
        adapter.addFragment(StudioCategoryFragment(), "카테고리")
        adapter.addFragment(StudioLocationFragment(), "위치")
        adapter.addFragment(StudioMoneyFragment(), "금액대")
        adapter.addFragment(StudioSortFragment(), "조회순")

        view.model_all_viewpager.adapter = adapter
        view.model_all_tab.setupWithViewPager(view.model_all_viewpager)
        view.model_all_viewpager.offscreenPageLimit = 4
        view.model_all_viewpager.currentItem = postion
        view.model_all_viewpager.getChildAt(1)
        view.model_all_viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                positions: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                when (positions) {
                    0 -> {
                        postion = positions
                        view.model_all_max_click_info.visibility = View.VISIBLE
                    }
                    1 -> {
                        postion = positions
                        view.model_all_max_click_info.visibility = View.VISIBLE
                    }
                    2 -> {
                        postion = positions
                        view.model_all_max_click_info.visibility = View.GONE
                    }
                    3 -> {
                        postion = positions
                        view.model_all_max_click_info.visibility = View.GONE
                    }

                }
            }

            override fun onPageSelected(position: Int) {
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

        })

        view.model_all_ok.setOnClickListener {

            studio_array = JsonArray()
            location_array = JsonArray()

            for (i in viewmodel.getCategory()) {
                studio_array.add(i)
            }

            for (i in viewmodel.getCategory()) {
                location_array.add(i)
            }

            check_min = viewmodel.getMin()
            check_max = viewmodel.getMax()

            if (check_min == "0") {
                viewmodel.setMin("")
            }
            if (check_max == "500000") {
                viewmodel.setMax("")
            }

            studioListManager.instant.listdata(
                studio_array,
                location_array,
                datetime_array,
                viewmodel.getMin(),
                viewmodel.getMax(),
                "",
                viewmodel.getSort(),
                1,
                completion = { responseStatus, responsenewlist, responsestudiokList ->

                    when (responseStatus) {
                        RESPONSE_STATUS.OKAY -> {

                            if (responsestudiokList.size != 0) {
                                itemClick(
                                    viewmodel.getCategory(),
                                    viewmodel.getLocation(),
                                    viewmodel.getMin(),
                                    viewmodel.getMax(),
                                    viewmodel.getSort(),
                                    responsestudiokList
                                )
                                dialog!!.dismiss()
                            } else {
                                Toast.makeText(view.context,"검색 결과가 없습니다.",Toast.LENGTH_SHORT).show()
                            }


                        }
                    }
                })
            /*itemClick(
                viewmodel.getCategory(),
                viewmodel.getLocation(),
                viewmodel.getMin(),
                viewmodel.getMax(),
                viewmodel.getSort()
            )
            dialog!!.dismiss()*/
        }

        view.model_all_reset.setOnClickListener {
        }


    }

    fun dateSetUp() {
        viewmodel.setCategory(category)
        viewmodel.setLoaction(location)
        viewmodel.setMin(min)
        viewmodel.setMax(max)
        viewmodel.setSort(sort)
    }
}