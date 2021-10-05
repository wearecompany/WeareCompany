package com.weare.wearecompany.ui.bottommenu.main.home.affiliation

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.data.division.data.recent
import com.weare.wearecompany.data.main.affiliationTop
import com.weare.wearecompany.utils.Constants
class AffiliationTopOneViewHodel(v : View): RecyclerView.ViewHolder(v) {

    fun bindWithView(Item: recent, onClickListener: View.OnClickListener) {
        Log.d(Constants.TAG, "hotpickItemViewHolder - bindWithView() called")

    }
}