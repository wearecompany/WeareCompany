package com.weare.wearecompany.ui.bottommenu.estimate.refund

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.weare.wearecompany.R
import kotlinx.android.synthetic.main.dialog_refund.view.*
import kotlinx.android.synthetic.main.rent_reservation_check_dialog.view.*
import kotlinx.android.synthetic.main.rent_reservation_check_dialog.view.rent_reservation_check_ok

class RefundDialog(val itemClick:(String) -> Unit): DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_refund, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCancelable(false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.rent_reservation_check_ok.setOnClickListener {
            itemClick(view.refund.text.toString())
            dialog!!.dismiss()
        }
    }

}