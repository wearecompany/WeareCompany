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
import com.weare.wearecompany.R
import com.weare.wearecompany.utils.Constants
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.data.main.data.list

class HomeLIstExpertViewHoder(v : View): RecyclerView.ViewHolder(v) {

    private val image = itemView.findViewById<ImageView>(R.id.home_model_image)
    private val name = itemView.findViewById<TextView>(R.id.home_model_name)
    private val name_title = itemView.findViewById<TextView>(R.id.home_model_title)
    private val title = itemView.findViewById<TextView>(R.id.home_model_expert_title)

    private val category = itemView.findViewById<RecyclerView>(R.id.home_model_expert_category)
    private lateinit var dtlist: ArrayList<String>
    private lateinit var dataAdapter: HomeListCaRecyclerViewAdapter

    fun bindWithView(Item: list, type:Int, onClickListener: View.OnClickListener) {
        Log.d(Constants.TAG, "hotpickItemViewHolder - bindWithView() called")

        dtlist = ArrayList<String>()

        var multiTransformation = MultiTransformation(CenterCrop(), RoundedCorners(90))
        val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

        Glide.with(MyApplication.instance)
            .load(Item.expert_image)
            .fallback(R.drawable.not_load_image)
            .transition(DrawableTransitionOptions.withCrossFade(factory))
            .apply(RequestOptions.bitmapTransform(multiTransformation))
            .into(DrawableImageViewTarget(image))

        name.setText(Item.expert_name)
        when(type) {
            1 -> name_title.text = "포토그래퍼님"
            2 -> name_title.text = "모델님"
            3 -> name_title.text = "뷰티전문가"
        }
        title.setText(Item.expert_title)

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