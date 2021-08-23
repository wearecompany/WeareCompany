package com.weare.wearecompany.ui.bottommenu.estimate.progress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.weare.wearecompany.R
import kotlinx.android.synthetic.main.bottom_dialog_refund.view.*


class ProgressBottomRefundFragment(val itemClick: (Int) -> Unit) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.bottom_dialog_refund, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.refund_ok.setOnClickListener {
            itemClick(0)
            dialog?.dismiss()
        }

        view.refund_no.setOnClickListener {
            dialog?.dismiss()
        }
    }
}