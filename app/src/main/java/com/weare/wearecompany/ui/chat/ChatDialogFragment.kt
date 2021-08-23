package com.weare.wearecompany.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.weare.wearecompany.R
import kotlinx.android.synthetic.main.bottom_dialog_cancellation.view.*
import kotlinx.android.synthetic.main.bottom_dialog_chat.view.*

class ChatDialogFragment(val itemClick: (Int) -> Unit) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.bottom_dialog_chat, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.chat_out.setOnClickListener {
            dialog?.dismiss()
            itemClick(0)
        }

        view.report.setOnClickListener {
            itemClick(1)
            dialog?.dismiss()
        }

        view.chat_manu_no.setOnClickListener {
            dialog?.dismiss()
        }


    }

}