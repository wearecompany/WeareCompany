package com.weare.wearecompany.ui.detail.studio

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.weare.wearecompany.R
import kotlinx.android.synthetic.main.sharing_dialog.view.*

class DatailSharingDialog(val itemClick: (Int) -> Unit) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.sharing_dialog, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        view.sharing_kakao.setOnClickListener {
            itemClick(0)
            dialog?.dismiss()
        }
        view.sharing_instar.setOnClickListener {
            itemClick(1)
            dialog?.dismiss()
        }
        view.sharing_facebook.setOnClickListener {
            itemClick(2)
            dialog?.dismiss()
        }
        view.sharing_that.setOnClickListener {
            itemClick(3)
            dialog?.dismiss()
        }
    }
}