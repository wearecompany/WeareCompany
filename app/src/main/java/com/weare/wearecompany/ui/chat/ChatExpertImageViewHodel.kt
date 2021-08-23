package com.weare.wearecompany.ui.chat

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.bumptech.glide.request.transition.Transition
import com.squareup.picasso.Picasso
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.chatting.data.send

class ChatExpertImageViewHodel(v:View) : RecyclerView.ViewHolder(v) {
    private val day = itemView.findViewById<TextView>(R.id.chat_day)
    private val time = itemView.findViewById<TextView>(R.id.chat_time)
    private val image = itemView.findViewById<ImageView>(R.id.chat_expert_image)
    var bitmap: Bitmap? = null
    fun bindWithView(Item: send, onClickListener: View.OnClickListener) {


        var multiTransformation = MultiTransformation(RoundedCorners(10))
        val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

        //Picasso.get().load(Item.image_resize_url).fit().centerCrop().into(image)

        Glide.with(MyApplication.instance)
            .asBitmap()
            .load(Item.image_resize_url)
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

        /*Glide.with(MyApplication.instance)
            .load(Item.image_resize_url)
            .placeholder(R.drawable.loading_image)
            .fallback(R.drawable.not_load_image)
            .transition(DrawableTransitionOptions.withCrossFade(factory))
            .apply(RequestOptions.bitmapTransform(multiTransformation))
            .into(DrawableImageViewTarget(image))*/

        day.text = Item.send_day
        time.text = Item.send_time

    }
}