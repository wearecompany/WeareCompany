package com.weare.wearecompany.ui.bottommenu.main.home.affiliation

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.weare.wearecompany.MyApplication
import com.weare.wearecompany.R
import com.weare.wearecompany.data.division.data.division
import com.weare.wearecompany.data.division.data.recent
import com.weare.wearecompany.data.retrofit.bottomnav.main.DivisionManager
import com.weare.wearecompany.databinding.ActivityAffiliationBinding
import com.weare.wearecompany.ui.base.BaseActivity
import com.weare.wearecompany.ui.bottommenu.main.home.affiliation.detail.AffiliationDetailActivity
import com.weare.wearecompany.utils.ESTIMATE
import kotlinx.android.synthetic.main.activity_affiliation.*

class AffiliationActivity: BaseActivity<ActivityAffiliationBinding>(
    R.layout.activity_affiliation
) {

    var recentList = ArrayList<recent>()
    var divisionList = ArrayList<division>()

    private lateinit var Adapter: AffiliationTopRecyclerViewAdapter
    private lateinit var bottomAdapter: AffiliationBottomRecyclerViewAdapter
    override fun onCreate() {


        DivisionManager.instance.list(completion = {responseStatus,arrayList,arrayList2 ->
            when(responseStatus) {
                ESTIMATE.OKAY -> {
                    recentList = arrayList
                    divisionList = arrayList2

                    when(recentList[0].generation) {
                        1 -> {
                            mViewDataBinding.topText.text = "1"
                        }
                        2 -> {
                            mViewDataBinding.topText.text = "2"
                        }
                        3 -> {
                            mViewDataBinding.topText.text = "3"
                        }
                        4 -> {
                            mViewDataBinding.topText.text = "4"
                        }
                        5 -> {
                            mViewDataBinding.topText.text = "5"
                        }
                    }

                    Adapter = AffiliationTopRecyclerViewAdapter(recentList)
                    mViewDataBinding.topRecyclerview.layoutManager =
                        LinearLayoutManager(
                            MyApplication.instance,
                            LinearLayoutManager.HORIZONTAL, false
                        )
                    mViewDataBinding.topRecyclerview.adapter = Adapter

                    Adapter.setItemClickListener(object : AffiliationTopRecyclerViewAdapter.OnItemClickListener{
                        override fun onClick(v: View, position: Int, homelistItem: recent) {
                            val newintent =Intent(Intent.ACTION_VIEW)
                            newintent.setData(Uri.parse("https://forms.gle/5K2bPDB6GMQ6RZT47"))
                            startActivity(newintent)
                        }

                    })

                    bottomAdapter = AffiliationBottomRecyclerViewAdapter(divisionList)
                    mViewDataBinding.bottomRecyclerview.layoutManager =
                        LinearLayoutManager(
                            MyApplication.instance,
                            LinearLayoutManager.VERTICAL, false
                        )
                    mViewDataBinding.bottomRecyclerview.adapter = bottomAdapter

                    bottomAdapter.setItemClickListener(object : AffiliationBottomRecyclerViewAdapter.OnItemClickListener{
                        override fun onClick(v: View, position: Int, Item: division) {
                            var newIntent = Intent(this@AffiliationActivity, AffiliationDetailActivity::class.java)
                            newIntent.putExtra("model_idx",Item.model_idx)
                            startActivity(newIntent)
                        }

                    })
                }
            }
        })

    }
}