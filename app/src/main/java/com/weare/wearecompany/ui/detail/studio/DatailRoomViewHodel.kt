package com.weare.wearecompany.ui.detail.studio

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
import com.weare.wearecompany.R
import com.weare.wearecompany.data.hotpick.data.room
import com.weare.wearecompany.utils.Constants
import com.weare.wearecompany.MyApplication
import java.text.DecimalFormat

class DatailRoomViewHodel(v: View) : RecyclerView.ViewHolder(v) {

    private val ImageView = itemView.findViewById<ImageView>(R.id.room_image)
    private val room_name = itemView.findViewById<TextView>(R.id.room_name)
    private val room_max_person = itemView.findViewById<TextView>(R.id.room_max_person)
    private val room_price = itemView.findViewById<TextView>(R.id.room_price)
    private val room_time = itemView.findViewById<TextView>(R.id.room_time)

    private val dec = DecimalFormat("#,###")

    fun bindWith(Item: room, onClickListener: View.OnClickListener) {
        Log.d(Constants.TAG, "DatailRoomItemViewHolder - bindWithView() called")
        var multiTransformation = MultiTransformation(CenterCrop(), RoundedCorners(20))
        Glide.with(MyApplication.instance)
            .load(Item.thumbnail)
            .apply(RequestOptions.bitmapTransform(multiTransformation))
            .into(ImageView)

        room_name.text = Item.name
        room_max_person.text = Item.max_person.toString()
        room_price.text = dec.format(Item.price.toInt())
        room_time.text = Item.time

    }
}