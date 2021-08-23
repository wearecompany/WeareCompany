package com.weare.wearecompany.ui.bottommenu.main.home

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.main.data.bottom_banner
import kotlinx.android.synthetic.main.item_banner.view.*
import java.util.ArrayList

class HomeBottomBannerRecyclerViewAdapter(private val bannerList: ArrayList<bottom_banner>): PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(container.context)
        val view = inflater.inflate(R.layout.item_bottom_banner, container, false)

        Glide.with(MyApplication.instance)
            .load(bannerList[position].image)
            .into(view.banner_image)

        container.addView(view)
        return view
    }

    override fun getCount(): Int {
        return bannerList.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View?)
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }
}