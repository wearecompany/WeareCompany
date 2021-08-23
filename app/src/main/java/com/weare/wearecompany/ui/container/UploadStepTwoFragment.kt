package com.weare.wearecompany.ui.container

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.weare.wearecompany.R
import kotlinx.android.synthetic.main.dialog_upload_step_two.view.*

class UploadStepTwoFragment(val itemClick: (Int) -> Unit) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.dialog_upload_step_two, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.model_Reserve.setOnClickListener {
            itemClick(0)
        }
        view.trip_Reserve.setOnClickListener {
            itemClick(1)
        }
        view.photo_Reserve.setOnClickListener {
            itemClick(2)
        }
        view.studio_Reserve.setOnClickListener {
            itemClick(3)
        }



    }
}