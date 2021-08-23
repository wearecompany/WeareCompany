package com.weare.wearecompany.ui.listcontainer.studio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.weare.wearecompany.R
import com.weare.wearecompany.ui.bottommenu.MainHomePageAdapter
import kotlinx.android.synthetic.main.bottom_dialog_all_model.view.*

class StudioAllDialog(
    var postion: Int,
    val category: ArrayList<Int>,
    val location: ArrayList<Int>,
    val min: String,
    val max: String,
    val sort: Int,
    val itemClick: (ArrayList<Int>, ArrayList<Int>, String, String, Int) -> Unit
) : BottomSheetDialogFragment() {

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
            itemClick(
                viewmodel.getCategory(),
                viewmodel.getLocation(),
                viewmodel.getMin(),
                viewmodel.getMax(),
                viewmodel.getSort()
            )
            dialog!!.dismiss()
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