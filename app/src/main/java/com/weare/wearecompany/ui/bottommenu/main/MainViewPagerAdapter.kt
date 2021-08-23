package com.weare.wearecompany.ui.bottommenu.main

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.main.data.banner
import kotlinx.android.synthetic.main.item_banner.view.*
import java.util.ArrayList


class MainViewPagerAdapter(private val bannerList: ArrayList<banner>): PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(container.context)
        val view = inflater.inflate(R.layout.item_banner, container, false)


        var multiTransformation = MultiTransformation(RoundedCorners(20))

        Glide.with(MyApplication.instance)
            .load(bannerList[position].image)
            .apply(RequestOptions.bitmapTransform(multiTransformation))
            .into(view.banner_image)

        container.addView(view)

        view.banner_image.setOnClickListener {

            if (position == 3) {
                val uri: Uri = Uri.parse("http://instagram.com/weare.company/")
                val Instagram_Intent = Intent(Intent.ACTION_VIEW, uri)

                Instagram_Intent.setPackage("com.instagram.android")
                view.context.startActivity(Instagram_Intent)
            }


        }
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