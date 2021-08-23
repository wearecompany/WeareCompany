package com.weare.wearecompany.ui.listcontainer.studio

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.main.data.newStudio
import com.weare.wearecompany.data.model.list.data.newlist
import com.weare.wearecompany.utils.Constants

class StudioNewViewHodel(v: View) : RecyclerView.ViewHolder(v) {

    private val image = itemView.findViewById<ImageView>(R.id.new_studio_image)
    private val name = itemView.findViewById<TextView>(R.id.new_studio_name)
    private val place = itemView.findViewById<TextView>(R.id.new_studio_place)

    private lateinit var placelist: ArrayList<String>

    fun bindWithView(item: newStudio, onClickListener: View.OnClickListener) {
        Log.d(Constants.TAG, "ModelItemViewHolder - bindWithView() called")

        placelist = ArrayList()
        var multiTransformation = MultiTransformation(CenterCrop(), RoundedCorners(60))
        val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

        Glide.with(MyApplication.instance)
            .load(item.thumbnail)
            .fallback(R.drawable.not_load_image)
            .transition(DrawableTransitionOptions.withCrossFade(factory))
            .apply(RequestOptions.bitmapTransform(multiTransformation))
            .into(DrawableImageViewTarget(image))

        name.text = item.name

        val pr = item.place!!.split(" ")
        for (i in pr) {
            placelist.add(i)
        }
        place.text = placelist[0] + " " + placelist[1]

    }
}