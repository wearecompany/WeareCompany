package com.weare.wearecompany.ui.detail.studio.reservation

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.hotpick.data.room
import com.weare.wearecompany.utils.Constants

class ReservationStrudioViwHodel(v: View): RecyclerView.ViewHolder(v) {

    private val ImageView = itemView.findViewById<ImageView>(R.id.studio_not_list_image)
    private val checkView = itemView.findViewById<ImageView>(R.id.reservation_room_check)
    private val room_name = itemView.findViewById<TextView>(R.id.reservation_room_name)
    private val max_person = itemView.findViewById<TextView>(R.id.reservation_room_max_person)
    private val room_price = itemView.findViewById<TextView>(R.id.reservation_room_price)
    private val room_time = itemView.findViewById<TextView>(R.id.reservation_room_time)

    fun bindWith(Item: room, check:Boolean,onClickListener: View.OnClickListener) {
        Log.d(Constants.TAG, "DatailRoomItemViewHolder - bindWithView() called")
        var multiTransformation = MultiTransformation(CenterCrop(), RoundedCorners(20))
        Glide.with(MyApplication.instance)
            .load(Item.thumbnail)
            .apply(RequestOptions.bitmapTransform(multiTransformation))
            .into(ImageView)

        room_name.setText(Item.name)
        room_price.setText(Item.price.toString())
        max_person.setText(Item.max_person.toString())
        room_time.setText(Item.time)

        if (check) {
            checkView.visibility = View.VISIBLE
        } else {
            checkView.visibility = View.GONE
        }

    }
}