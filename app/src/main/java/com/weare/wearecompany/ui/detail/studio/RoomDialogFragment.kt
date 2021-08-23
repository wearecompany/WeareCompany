package com.weare.wearecompany.ui.detail.studio

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.weare.wearecompany.R
import com.weare.wearecompany.data.hotpick.data.room
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_dialog_room.view.*
import java.text.DecimalFormat

class RoomDialogFragment( var data:room, val itemClick: (Int) -> Unit) : BottomSheetDialogFragment() {

    private lateinit var dataAdaptrt: RoomPageAdapter
    private lateinit var etcAdapter: RoomEtcRecyclerAdapter
    private val dec = DecimalFormat("#,###")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_dialog_room, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataAdaptrt = RoomPageAdapter(requireContext(), data)
        view.room_viewPager.adapter = dataAdaptrt
        view.room_worm_dots_indicator.setViewPager(view.room_viewPager)

        etcAdapter = RoomEtcRecyclerAdapter(data)

        view.room_etc_recyclerView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )
        view.room_etc_recyclerView.adapter = etcAdapter

        view.room_name.setText(data.name)
        view.room_price.text = dec.format(data.price.toInt())
        view.room_contents.setText(data.contents)

        view.room_down.setOnClickListener {
            dialog?.dismiss()
        }

    }
}