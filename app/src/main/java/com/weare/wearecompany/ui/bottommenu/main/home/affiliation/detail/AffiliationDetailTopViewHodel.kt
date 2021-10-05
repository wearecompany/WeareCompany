package com.weare.wearecompany.ui.bottommenu.main.home.affiliation.detail

import android.util.Log
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.division.DivisionPage
import com.weare.wearecompany.data.division.data.division
import com.weare.wearecompany.data.main.affiliationTop
import com.weare.wearecompany.di.appModules
import com.weare.wearecompany.utils.Constants

class AffiliationDetailTopViewHodel(v : View): RecyclerView.ViewHolder(v) {

    private val hashtag = itemView.findViewById<TextView>(R.id.aff_detail_hashtag)
    private val title = itemView.findViewById<TextView>(R.id.aff_detail_title)
    val image = itemView.findViewById<ImageView>(R.id.aff_detail_top_image)
    //private val webview = itemView.findViewById<WebView>(R.id.aff_detail_top_webview)

    fun bindWithView(Item: DivisionPage,onClickListener: View.OnClickListener) {
        Log.d(Constants.TAG, "AffiliationDetailTopViewHodel - bindWithView() called")

        var multiTransformation = MultiTransformation(CenterCrop())
        val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

        Glide.with(MyApplication.instance)
            .load(Item.video_thumbnail)
            .fallback(R.drawable.not_load_image)
            .transition(DrawableTransitionOptions.withCrossFade(factory))
            .apply(RequestOptions.bitmapTransform(multiTransformation))
            .into(DrawableImageViewTarget(image))

        /*webview.settings.apply {
            javaScriptEnabled = true
            setAppCacheEnabled(true)
            pluginState = WebSettings.PluginState.ON
            useWideViewPort = true
            loadWithOverviewMode = true
        }
        webview.webChromeClient = VideoEnabledWebChromeClient()*/

        hashtag.text = Item.hashtag
        title.text = Item.title

    }
}