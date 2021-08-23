package com.weare.wearecompany.ui.bottommenu.mypage.bookmark

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.bottomnav.mypage.data.allList

class BookMarkViewHodel(v: View): RecyclerView.ViewHolder(v) {

    private val ImageView = itemView.findViewById<ImageView>(R.id.bookmark_image)
    private val name = itemView.findViewById<TextView>(R.id.bookmark_name)
    private val category = itemView.findViewById<TextView>(R.id.bookmark_category)
    private val description = itemView.findViewById<TextView>(R.id.bookmark_description)

    fun BindWithView(Item: allList, postion:Int, onClickListener: View.OnClickListener) {

        var multiTransformation = MultiTransformation(CenterCrop(), RoundedCorners(20))

        Glide.with(MyApplication.instance)
            .load(Item.image)
            .apply(RequestOptions.bitmapTransform(multiTransformation))
            .into(ImageView)

        name.setText(Item.name)
        category.setText(Item.category)
        description.setText(Item.description)
    }

}