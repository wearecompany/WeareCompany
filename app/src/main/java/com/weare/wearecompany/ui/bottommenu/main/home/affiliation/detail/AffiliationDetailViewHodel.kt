package com.weare.wearecompany.ui.bottommenu.main.home.affiliation.detail

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.division.DivisionPage
import com.weare.wearecompany.data.main.affiliationTop
import com.weare.wearecompany.utils.Constants

class AffiliationDetailViewHodel(v : View): RecyclerView.ViewHolder(v) {

    private val qa_title = itemView.findViewById<TextView>(R.id.aff_detail_qa_title)
    private val qa_content = itemView.findViewById<TextView>(R.id.aff_detail_qa_content)

    fun bindWithView(Item: DivisionPage,postion:Int) {
        Log.d(Constants.TAG, "hotpickItemViewHolder - bindWithView() called")
        qa_title.text = Item.qa[postion].question
        qa_content.text = Item.qa[postion].answer
    }
}