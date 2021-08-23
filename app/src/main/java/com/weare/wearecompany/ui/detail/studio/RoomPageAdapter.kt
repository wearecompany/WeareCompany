package com.weare.wearecompany.ui.detail.studio

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.hotpick.data.room
import com.weare.wearecompany.ui.detail.PinchActivity
import kotlinx.android.synthetic.main.item_studio_image.view.*

class RoomPageAdapter(var context:Context, private val data: room): PagerAdapter()  {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(container.context)
        val view = inflater.inflate(R.layout.item_studio_image, container, false)

        var multiTransformation = MultiTransformation(CenterCrop())
        Glide.with(MyApplication.instance)
            .load(data.image[position])
            .apply(RequestOptions.bitmapTransform(multiTransformation))
            .into(view.datail_image)

        view.datail_image.setOnClickListener {
            val newIntent = Intent(context, PinchActivity::class.java)
            newIntent.putExtra("image", data.image[position])
            context.startActivity(newIntent)
        }

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View?)
    }

    override fun getCount(): Int {
        return data.image.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }
}