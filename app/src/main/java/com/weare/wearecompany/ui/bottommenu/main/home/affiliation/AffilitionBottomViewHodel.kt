package com.weare.wearecompany.ui.bottommenu.main.home.affiliation

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
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
import com.weare.wearecompany.data.division.data.division
import com.weare.wearecompany.data.main.affiliationTop
import com.weare.wearecompany.utils.Constants

class AffilitionBottomViewHodel(v : View): RecyclerView.ViewHolder(v) {

    private val image = itemView.findViewById<ImageView>(R.id.division_bottom_image)
    private val stars_0 = itemView.findViewById<ImageView>(R.id.division_bottom_stars_0)
    private val stars_1 = itemView.findViewById<ImageView>(R.id.division_bottom_stars_1)
    private val stars_2 = itemView.findViewById<ImageView>(R.id.division_bottom_stars_2)
    private val stars_3 = itemView.findViewById<ImageView>(R.id.division_bottom_stars_3)
    private val stars_4 = itemView.findViewById<ImageView>(R.id.division_bottom_stars_4)
    private val name = itemView.findViewById<TextView>(R.id.division_bottom_name)
    private val generation = itemView.findViewById<TextView>(R.id.division_bottom_generation)
    private val category = itemView.findViewById<TextView>(R.id.division_bottom_category)
    private val title = itemView.findViewById<TextView>(R.id.division_bottom_title)
    private val view_num = itemView.findViewById<TextView>(R.id.division_bottom_view_num)
    val like_num = itemView.findViewById<TextView>(R.id.division_bottom_like_num)
    val aff_layout = itemView.findViewById<LinearLayoutCompat>(R.id.affilition_layout)


    fun bindWithView(Item: division, onClickListener: View.OnClickListener) {
        Log.d(Constants.TAG, "hotpickItemViewHolder - bindWithView() called")

        var multiTransformation = MultiTransformation(CenterCrop(), RoundedCorners(40))
        val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

        Glide.with(MyApplication.instance)
            .load(Item.img)
            .fallback(R.drawable.not_load_image)
            .transition(DrawableTransitionOptions.withCrossFade(factory))
            .apply(RequestOptions.bitmapTransform(multiTransformation))
            .into(DrawableImageViewTarget(image))

        name.setText(Item.name)
        category.text = Item.category
        title.text = Item.title
        view_num.text = Item.view_num.toString()
        like_num.text = Item.like_num.toString()
        when(Item.grade) {
            "1" ->{
                stars_1.setImageResource(R.drawable.review_on)
            }
            "2" ->{
                stars_1.setImageResource(R.drawable.review_on)
                stars_2.setImageResource(R.drawable.review_on)
            }
            "3" ->{
                stars_1.setImageResource(R.drawable.review_on)
                stars_2.setImageResource(R.drawable.review_on)
                stars_3.setImageResource(R.drawable.review_on)
            }
            "4" ->{
                stars_1.setImageResource(R.drawable.review_on)
                stars_2.setImageResource(R.drawable.review_on)
                stars_3.setImageResource(R.drawable.review_on)
                stars_4.setImageResource(R.drawable.review_on)
            }
        }

        when(Item.generation) {
            1 ->{
                generation.text = "위픽 1기"
            }
            2 ->{
                generation.text = "위픽 2기"
            }
            3 ->{
                generation.text = "위픽 3기"
            }
            4 ->{
                generation.text = "위픽 4기"
            }
            5 ->{
                generation.text = "위픽 5기"
            }
        }


    }
}