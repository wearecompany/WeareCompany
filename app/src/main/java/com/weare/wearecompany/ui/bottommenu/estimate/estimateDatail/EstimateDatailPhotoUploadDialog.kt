package com.weare.wearecompany.ui.bottommenu.estimate.estimateDatail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.weare.wearecompany.R
import kotlinx.android.synthetic.main.bottom_dialog_camrea.view.*

class EstimateDatailPhotoUploadDialog(val itemClick: (Int) -> Unit) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.bottom_dialog_camrea, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.bottom_camera.setOnClickListener {
            itemClick(0)
            dialog?.dismiss()
        }

        view.bottom_gallery.setOnClickListener {
            itemClick(1)
            dialog?.dismiss()
        }

        view.dialog_request.setOnClickListener {
            dialog?.dismiss()
        }
    }

}