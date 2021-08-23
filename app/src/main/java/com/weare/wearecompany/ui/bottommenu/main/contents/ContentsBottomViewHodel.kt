package com.weare.wearecompany.ui.bottommenu.main.contents

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
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
import com.weare.wearecompany.data.main.data.contents
import com.weare.wearecompany.data.main.data.list
import com.weare.wearecompany.ui.bottommenu.main.home.HomeListCaRecyclerViewAdapter
import com.weare.wearecompany.utils.Constants

class ContentsBottomViewHodel(v : View): RecyclerView.ViewHolder(v) {

    private val image = itemView.findViewById<ImageView>(R.id.contents_bottom_image)
    private val title = itemView.findViewById<TextView>(R.id.contents_bottom_title)
    private val sub = itemView.findViewById<TextView>(R.id.contents_bottom_sub)
    private val register = itemView.findViewById<TextView>(R.id.contents_bottom_register)

    fun bindWithView(Item: contents, onClickListener: View.OnClickListener) {
        Log.d(Constants.TAG, "hotpickItemViewHolder - bindWithView() called")

        var multiTransformation = MultiTransformation(CenterCrop(), RoundedCorners(40))
        val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

        Glide.with(MyApplication.instance)
            .load(Item.image)
            .fallback(R.drawable.not_load_image)
            .transition(DrawableTransitionOptions.withCrossFade(factory))
            .apply(RequestOptions.bitmapTransform(multiTransformation))
            .into(DrawableImageViewTarget(image))

        title.text = Item.title
        sub.text = Item.sub
        register.text = Item.register

    }
}