package com.weare.wearecompany.ui.chat

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.squareup.picasso.Picasso
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.chatting.data.send

class ChatUserImageViewHodel(v: View) : RecyclerView.ViewHolder(v) {
    private val day = itemView.findViewById<TextView>(R.id.chat_day)
    private val time = itemView.findViewById<TextView>(R.id.chat_time)
    private val image = itemView.findViewById<ImageView>(R.id.chat_user_image)
    var bitmap: Bitmap? = null
    fun bindWithView(Item: send, onClickListener: View.OnClickListener) {

        var multiTransformation = MultiTransformation(RoundedCorners(10))


        Glide.with(MyApplication.instance)
            .asBitmap()
            .load(Item.image_resize_url)
            .apply(RequestOptions.bitmapTransform(multiTransformation))
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    bitmap = resource
                    image.layoutParams.height = bitmap!!.height
                    image.layoutParams.width = bitmap!!.width
                    image.setImageBitmap(bitmap)
                    image.requestLayout()
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }

            })

        day.text = Item.send_day
        time.text = Item.send_time

    }
}