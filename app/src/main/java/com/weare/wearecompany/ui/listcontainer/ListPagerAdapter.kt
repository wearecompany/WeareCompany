package com.weare.wearecompany.ui.listcontainer

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.weare.wearecompany.ui.listcontainer.model.ModelFragment
import com.weare.wearecompany.ui.listcontainer.photographer.PhotographerFragment
import com.weare.wearecompany.ui.listcontainer.studio.StudioFragment
import com.weare.wearecompany.ui.listcontainer.trip.TripFragment

class ListPagerAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int = 5

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {

            0       ->  StudioFragment()
            1       ->  PhotographerFragment()
            2       ->  ModelFragment()
            else       ->  TripFragment()

        }
    }
}