package com.weare.wearecompany.ui.hotpick.list

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.weare.wearecompany.R
import com.weare.wearecompany.data.main.data.hotpick
import com.weare.wearecompany.utils.Constants
import com.weare.wearecompany.MyApplication

class HotpickListViewHoder(v: View) : RecyclerView.ViewHolder(v) {

    private val ImageView = itemView.findViewById<ImageView>(R.id.hotpick_image)
    private val Text_name = itemView.findViewById<TextView>(R.id.hotpick_name)
    private val Text_sub_text = itemView.findViewById<TextView>(R.id.hotpick_sub_text)
    private val Text_title = itemView.findViewById<TextView>(R.id.hotpick_title)

    fun bindWithView(hotpickItem: hotpick, onClickListener: View.OnClickListener) {
        Log.d(Constants.TAG, "StoryItemViewHolder - bindWithView() called")

        var multiTransformation = MultiTransformation(CenterCrop(), RoundedCorners(40))
        Glide.with(MyApplication.instance)
            .load(hotpickItem.image)
            .apply(RequestOptions.bitmapTransform(multiTransformation))
            .into(ImageView)

        Text_name.setText(hotpickItem.name)
        Text_title.setText(hotpickItem.title)
        //view.mRootView.setOnClickListener(onClickListener)

        // viewHodel에 클릭 이벤트 추가하기
        /* view.mRootView.setOnClickListener {
             Toast.makeText(view.context, "${hotpickItem.title}\n${hotpickItem.sub}", Toast.LENGTH_SHORT).show()
         }*/
    }
}