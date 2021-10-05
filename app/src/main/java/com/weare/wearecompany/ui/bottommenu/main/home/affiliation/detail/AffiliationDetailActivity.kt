package com.weare.wearecompany.ui.bottommenu.main.home.affiliation.detail

import android.animation.Animator
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
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
import com.weare.wearecompany.data.retrofit.bottomnav.main.DivisionManager
import com.weare.wearecompany.data.retrofit.bottomnav.mypage.mypageManager
import com.weare.wearecompany.ui.detail.model.ModelActivity
import com.weare.wearecompany.utils.ESTIMATE
import com.weare.wearecompany.utils.LIKE

class AffiliationDetailActivity: AppCompatActivity(),View.OnClickListener {

    var model_array = ArrayList<DivisionPage>()

    var user_idx = ""
    var model_idx:String? = ""
    lateinit var book_marker: ImageView
    lateinit var like_number:TextView
    lateinit var view_number:TextView
    lateinit var book_lottie:LottieAnimationView
    lateinit var profile:TextView
    var like_num = 0
    var view_num = 0
    private var like: Boolean = false


    private lateinit var Adapter: AffilitionDetailRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_affiliation_detail)

        user_idx = MyApplication.prefs.getString("user_idx", "")
        model_idx = intent.getStringExtra("model_idx")

        DivisionManager.instance.page(user_idx,model_idx!!,completion = { responseStatus, arrayList ->
            when(responseStatus) {
                ESTIMATE.OKAY -> {

                    model_array = arrayList
                    val detail_image = findViewById<ImageView>(R.id.aff_detail_image)
                    view_number = findViewById<TextView>(R.id.aff_detail_view_number)
                    like_number = findViewById<TextView>(R.id.aff_detail_like_number)
                    book_marker = findViewById<ImageView>(R.id.aff_detail_book_marker)
                    book_lottie = findViewById(R.id.lottieView)
                    val book_marker_layout = findViewById<FrameLayout>(R.id.aff_detail_book_marker_layout)
                    book_marker_layout.setOnClickListener(this)
                    profile = findViewById<TextView>(R.id.aff_detail_profile)
                    profile.setOnClickListener(this)

                    book_lottie.addAnimatorListener(object : Animator.AnimatorListener {
                        override fun onAnimationStart(p0: Animator?) {

                        }

                        override fun onAnimationEnd(p0: Animator?) {
                            //book_lottie.visibility = View.GONE
                        }

                        override fun onAnimationCancel(p0: Animator?) {

                        }

                        override fun onAnimationRepeat(p0: Animator?) {

                        }

                    })
                    val category = findViewById<TextView>(R.id.aff_detail_category)
                    val name = findViewById<TextView>(R.id.aff_detail_name)
                    val content = findViewById<TextView>(R.id.aff_detail_content)
                    val recyclerview = findViewById<RecyclerView>(R.id.aff_detail_recyclerview)


                    like_num = arrayList[0].like_num
                    view_num = arrayList[0].view_num



                    var multiTransformation = MultiTransformation(CenterCrop())
                    val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

                    Glide.with(MyApplication.instance)
                        .load(arrayList[0].thumbnail)
                        .fallback(R.drawable.not_load_image)
                        .transition(DrawableTransitionOptions.withCrossFade(factory))
                        .apply(RequestOptions.bitmapTransform(multiTransformation))
                        .into(DrawableImageViewTarget(detail_image))

                    view_number.text = arrayList[0].view_num.toString()
                    like_number.text = arrayList[0].like_num.toString()

                    if (arrayList[0].scrap_status) {
                        book_marker.setImageResource(R.drawable.like_on)
                        like = true
                    } else {
                        book_marker.setImageResource(R.drawable.like_off)
                    }

                    category.text = arrayList[0].model_category
                    name.text = arrayList[0].model_name
                    content.text = arrayList[0].model_title

                    Adapter = AffilitionDetailRecyclerViewAdapter(arrayList)
                    recyclerview.layoutManager =
                        LinearLayoutManager(
                            MyApplication.instance,
                            LinearLayoutManager.VERTICAL, false
                        )
                    recyclerview.adapter = Adapter

                    Adapter.setItemClickListener(object : AffilitionDetailRecyclerViewAdapter.OnItemClickListener{
                        override fun onClick(v: View, position: Int, Item: DivisionPage) {
                            val uri: Uri = Uri.parse("https://www.youtube.com/channel/UCNplNjDyYkICiptGQhGhqsg")
                            val youtube = Intent(Intent.ACTION_VIEW, uri)

                            youtube.setPackage("com.google.android.youtube")
                            val insta = this@AffiliationDetailActivity.packageManager.getLaunchIntentForPackage("com.google.android.youtube")
                            if (insta == null) {
                                val newintent =Intent(Intent.ACTION_VIEW)
                                newintent.setData(Uri.parse("market://details?id="+ "com.google.android.youtube"))
                                startActivity(newintent)
                            } else {
                                startActivity(youtube)
                            }
                        }

                    })
                }
            }
        })
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.aff_detail_book_marker_layout -> {
                if (MyApplication.prefs.getString("user_idx", "") != "") {
                    if (like) {
                        book_marker.setImageResource(R.drawable.like_off)
                        like = false
                        mypageManager.instance.like_off(
                            2,
                            model_idx!!,
                            user_idx,
                            completion = { responseStatus ->
                                when (responseStatus) {
                                    LIKE.OKAY -> {
                                        like_num -= 1
                                        like_number.text = (like_num).toString()
                                    }
                                }
                            })

                    } else {
                        mypageManager.instance.like_on(
                            2,
                            model_idx!!,
                            user_idx,
                            completion = { responseStatus ->
                                when (responseStatus) {
                                    LIKE.OKAY -> {
                                        book_marker.setImageResource(R.drawable.like_on)
                                        //book_lottie.visibility = View.VISIBLE
                                        book_lottie.playAnimation()
                                        like_num += 1
                                        like_number.text = (like_num).toString()
                                        like = true
                                    }
                                }
                            })
                    }
                } else {
                    Toast.makeText(this, "로그인후 이용 가능합니다.", Toast.LENGTH_SHORT).show()
                }
            }

            R.id.aff_detail_profile -> {
                var newIntent = Intent(this, ModelActivity::class.java)

                newIntent.putExtra("expert_idx", model_idx)
                newIntent.putExtra(
                    "user_idx", MyApplication.prefs.getString(
                        "user_idx",
                        ""
                    )
                )
                startActivity(newIntent)
            }

        }
    }
}