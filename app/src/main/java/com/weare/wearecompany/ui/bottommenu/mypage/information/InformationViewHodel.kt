package com.weare.wearecompany.ui.bottommenu.mypage.information

import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.weare.wearecompany.R
import com.weare.wearecompany.data.bottomnav.mypage.data.manual
import com.weare.wearecompany.utils.Constants

class InformationViewHodel(v:View): RecyclerView.ViewHolder(v) {

    private val ImageView = itemView.findViewById<ImageView>(R.id.information_image)
    private val TextView = itemView.findViewById<TextView>(R.id.information_title)
    private val view = itemView.findViewById<View>(R.id.information_view)



    fun bindWithView(Item: manual, onClickListener: View.OnClickListener) {
        Log.d(Constants.TAG, "dateilItemViewHolder - bindWithView() called")

        TextView.setText(Item.title)

        if (Item.type == "0") {
            ImageView.visibility = View.GONE
            view.visibility = View.GONE
            TextView.setTextColor(Color.parseColor("#6d34f3"))
            TextView.setText(Item.title)
        } else if (Item.type == "1") {
            ImageView.visibility = View.VISIBLE
            view.visibility = View.VISIBLE
            TextView.setTextColor(Color.parseColor("#0f0f0f"))
            TextView.setText(Item.question)
        } else if (Item.type == "2") {
            ImageView.visibility = View.GONE
            view.visibility = View.GONE
            TextView.setText(Item.title)
        }
    }

}