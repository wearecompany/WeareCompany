package com.weare.wearecompany.ui.bottommenu.estimate.estimateDatail

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import kotlinx.android.synthetic.main.estimate_reservation_check_dialog.view.*

class EstimateDatailDialog(val expert_type:String, val expert_category:String, val location:String, val request_thumbnail :ArrayList<Uri>, val request_dt:String, val request_start_time:String, val request_end_time:String, val request_contents:String, val itemClick:(Int) -> Unit): DialogFragment() {

    private lateinit var dateadapter : EstimateDatailDialogRecyclerViewAdapter
    val windowManager = MyApplication.instance.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = windowManager.defaultDisplay
    val size = Point()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.estimate_reservation_check_dialog, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCancelable(false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        display.getSize(size)

        view.expert_type.text = expert_type
        view.expert_category.text = expert_category
        view.expert_location.text = location
        if (request_thumbnail.size != 0) {
         view.request_thumbnail_layout.visibility = View.VISIBLE
            dateadapter = EstimateDatailDialogRecyclerViewAdapter(request_thumbnail)
            view.request_thumbnail.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL, false
            )
            view.request_thumbnail.adapter = dateadapter
        }
        view.request_dt.text = request_dt
        view.request_start_time.text = request_start_time
        view.request_end_time.text = request_end_time
        view.request_contents.text = request_contents

        view.Re_expert_check_no.setOnClickListener {
            dialog?.dismiss()
        }
        view.Re_expert_check_ok.setOnClickListener {
            dialog?.dismiss()
            itemClick(1)
        }
    }

    override fun onResume() {
        super.onResume()
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size.x
        params?.width = (deviceWidth * 0.8).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }


}