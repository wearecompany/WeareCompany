package com.weare.wearecompany.ui.listcontainer.studio

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.main.data.banner
import com.weare.wearecompany.data.main.data.studio
import com.weare.wearecompany.ui.detail.DatailActivity
import kotlinx.android.synthetic.main.item_studio_map.view.*
import java.util.ArrayList

class StudioMapViewPager(private val bannerList: ArrayList<studio>): PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(container.context)
        val view = inflater.inflate(R.layout.item_studio_map, container, false)


        var multiTransformation = MultiTransformation(CenterCrop()
        )

        Glide.with(MyApplication.instance)
            .load(bannerList[position].image)
            .apply(RequestOptions.bitmapTransform(multiTransformation))
            .into(view.map_image)

        view.map_title.text = bannerList[position].title
        view.map_addr.text = bannerList[position].address

        view.cardView.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                var newIntent = Intent(view.context, DatailActivity::class.java)

                newIntent.putExtra("idx", bannerList[position].idx)
                view.context.startActivity(newIntent)
            }

        })
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