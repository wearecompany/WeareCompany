package com.weare.wearecompany.ui.bottommenu.main.home.affiliation

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
import com.weare.wearecompany.data.division.data.recent
import com.weare.wearecompany.data.main.affiliationTop
import com.weare.wearecompany.data.main.data.list
import com.weare.wearecompany.ui.bottommenu.main.home.HomeListCaRecyclerViewAdapter
import com.weare.wearecompany.utils.Constants

class AffiliationTopViewHodel(v : View): RecyclerView.ViewHolder(v) {

    private val image = itemView.findViewById<ImageView>(R.id.affilition_image)
    private val name = itemView.findViewById<TextView>(R.id.affilition_name)
    private var numbers = itemView.findViewById<TextView>(R.id.affilition_numbers)


    fun bindWithView(Item: recent) {
        Log.d(Constants.TAG, "hotpickItemViewHolder - bindWithView() called")

        when(Item.generation) {
            1 -> {
                numbers.text = "1"
            }
            2 -> {
                numbers.text = "2"
            }
            3 -> {
                numbers.text = "3"
            }
            4 -> {
                numbers.text = "4"
            }
            5 -> {
                numbers.text = "5"
            }
        }

        var multiTransformation = MultiTransformation(CenterCrop(), RoundedCorners(150))
        val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

        Glide.with(MyApplication.instance)
            .load(Item.img)
            .fallback(R.drawable.not_load_image)
            .transition(DrawableTransitionOptions.withCrossFade(factory))
            .apply(RequestOptions.bitmapTransform(multiTransformation))
            .into(DrawableImageViewTarget(image))

        name.setText(Item.name)

    }
}