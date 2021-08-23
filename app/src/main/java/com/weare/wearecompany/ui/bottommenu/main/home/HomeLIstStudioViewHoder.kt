package com.weare.wearecompany.ui.bottommenu.main.home

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
import com.weare.wearecompany.data.main.data.list
import com.weare.wearecompany.utils.Constants

class HomeLIstStudioViewHoder(v : View): RecyclerView.ViewHolder(v) {

    private val image = itemView.findViewById<ImageView>(R.id.home_studio_image)
    private val name = itemView.findViewById<TextView>(R.id.home_studio_name)
    private val title = itemView.findViewById<TextView>(R.id.home_studio_expert_title)
    private val place = itemView.findViewById<TextView>(R.id.home_studio_expert_place)

    private val category = itemView.findViewById<RecyclerView>(R.id.home_studio_category)
    private lateinit var dtlist: ArrayList<String>
    private lateinit var dataAdapter: HomeListCaRecyclerViewAdapter

    fun bindWithView(Item: list, onClickListener: View.OnClickListener) {
        Log.d(Constants.TAG, "hotpickItemViewHolder - bindWithView() called")

        dtlist = ArrayList<String>()

        var multiTransformation = MultiTransformation(CenterCrop(), RoundedCorners(20))
        val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

        Glide.with(MyApplication.instance)
            .load(Item.expert_image)
            .fallback(R.drawable.not_load_image)
            .transition(DrawableTransitionOptions.withCrossFade(factory))
            .apply(RequestOptions.bitmapTransform(multiTransformation))
            .into(DrawableImageViewTarget(image))

        name.setText(Item.expert_name)
        title.setText(Item.expert_title)
        place.setText(Item.expert_place)

        val dt = Item.expert_category.split(",")
        for (i in dt) {
            dtlist.add(i)
        }

        dataAdapter = HomeListCaRecyclerViewAdapter(dtlist)
        category.layoutManager = LinearLayoutManager(
            MyApplication.instance,
            LinearLayoutManager.HORIZONTAL, false
        )
        category.adapter = dataAdapter
    }
}