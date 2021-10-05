package com.weare.wearecompany.ui.bottom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.weare.wearecompany.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomCategoryRentFragment(val itemClick: (Int) -> Unit) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.bottom_dialog_rent, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       /* view.bottom_rent_0.setOnClickListener {
            itemClick(0)
        }
        view.bottom_rent_1.setOnClickListener {
            itemClick(1)
        }
        view.bottom_rent_2.setOnClickListener {
            itemClick(2)
        }
        view.bottom_rent_3.setOnClickListener {
            itemClick(3)
        }
        view.bottom_rent_4.setOnClickListener {
            itemClick(4)
        }
        view.bottom_rent_5.setOnClickListener {
            itemClick(5)
        }
        view.bottom_rent_6.setOnClickListener {
            itemClick(6)
        }
        view.bottom_rent_7.setOnClickListener {
            itemClick(7)
        } */
    }
}