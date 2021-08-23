package com.weare.wearecompany.ui.bottommenu.main.weekly

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
import com.weare.wearecompany.data.main.data.hotpick
import com.weare.wearecompany.utils.Constants

class WeeklyExpertViewHodel(v : View): RecyclerView.ViewHolder(v) {

    private val image = itemView.findViewById<ImageView>(R.id.weekly_model_image)
    private val name = itemView.findViewById<TextView>(R.id.weekly_model_name)
    private val name_title = itemView.findViewById<TextView>(R.id.weekly_model_name_title)
    private val title = itemView.findViewById<TextView>(R.id.weekly_title)

    private val category = itemView.findViewById<RecyclerView>(R.id.weekly_model_category)

    private lateinit var dtlist: ArrayList<String>
    private lateinit var dataAdapter: WeeklyCaRecyclerViewAdapter

    fun bindWithView(Item: hotpick, onClickListener: View.OnClickListener) {
        Log.d(Constants.TAG, "hotpickItemViewHolder - bindWithView() called")

        dtlist = ArrayList<String>()

        var multiTransformation = MultiTransformation(CenterCrop(), RoundedCorners(60))
        val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

        Glide.with(MyApplication.instance)
            .load(Item.image)
            .fallback(R.drawable.not_load_image)
            .transition(DrawableTransitionOptions.withCrossFade(factory))
            .apply(RequestOptions.bitmapTransform(multiTransformation))
            .into(DrawableImageViewTarget(image))

        name.setText(Item.name)
        when(Item.target_type) {
            1 -> name_title.text = "포토그래퍼님"
            2 -> name_title.text = "모델님"
            3 -> name_title.text = "뷰티전문가"
        }
        title.setText(Item.title)

        val dt = Item.category!!.split(",")
        for (i in dt) {
            dtlist.add(i)
        }

        dataAdapter = WeeklyCaRecyclerViewAdapter(dtlist)
        category.layoutManager = LinearLayoutManager(
            MyApplication.instance,
            LinearLayoutManager.HORIZONTAL, false
        )
        category.adapter = dataAdapter
    }
}