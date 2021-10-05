package com.weare.wearecompany.ui.listcontainer.model

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.weare.wearecompany.R
import com.weare.wearecompany.ui.bottommenu.MainHomePageAdapter
import com.weare.wearecompany.ui.listcontainer.AllLocationFragment
import com.weare.wearecompany.ui.listcontainer.AllMoneyFragment
import com.weare.wearecompany.ui.listcontainer.AllSortFragment
import kotlinx.android.synthetic.main.bottom_dialog_all_model.view.*

class ModelAllDialog(
    var postion: Int,
    val category: Int,
    val location: ArrayList<Int>,
    val min: String,
    val max: String,
    val sort: Int,
    val itemClick: (Int, ArrayList<Int>, String, String, Int) -> Unit
) : BottomSheetDialogFragment() {

    private val viewmodel: ModelViewModel by activityViewModels()
    private var clicknum = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.bottom_dialog_all_model, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dateSetUp()

        val adapter = MainHomePageAdapter(childFragmentManager)
        adapter.addFragment(ModelCategoryDialog(), "카테고리")
        adapter.addFragment(AllLocationFragment(), "위치")
        adapter.addFragment(AllMoneyFragment(), "금액대")
        adapter.addFragment(AllSortFragment(), "조회순")

        view.model_all_viewpager.adapter = adapter
        view.model_all_tab.setupWithViewPager(view.model_all_viewpager)
        view.model_all_viewpager.offscreenPageLimit = 5
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
                        view.model_all_max_click_info.visibility = View.GONE
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