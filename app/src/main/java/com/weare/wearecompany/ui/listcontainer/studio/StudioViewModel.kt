package com.weare.wearecompany.ui.listcontainer.studio

import androidx.lifecycle.ViewModel

class StudioViewModel: ViewModel() {
    private var category: ArrayList<Int> = ArrayList()
    private var location: ArrayList<Int> = ArrayList()
    private var minmoney: String = ""
    private var maxmoney: String = ""
    private var sort: Int = 0

    fun setCategory(setcategory: ArrayList<Int>) {
        category = setcategory
    }

    fun setLoaction(setlocation: ArrayList<Int>) {
        location = setlocation
    }

    fun setMin(setmin: String) {
        minmoney = setmin
    }

    fun setMax(setmax: String) {
        maxmoney = setmax
    }

    fun setSort(setsort: Int) {
        sort = setsort
    }

    fun removeLocation(removelocation: Int) {
        location.removeAt(removelocation - 1)
    }

    fun addLocation(addlocation: Int) {
        location.add(addlocation)
    }
    fun removeCategory(removecategory: Int) {
        category.removeAt(removecategory - 1)
    }

    fun addCategory(addcategory: Int) {
        category.add(addcategory)
    }

    fun clickSort(click: Int) {
        sort = click
    }

    fun getCategory(): ArrayList<Int> {
        return category
    }

    fun getMin(): String {
        return minmoney
    }

    fun getMax(): String {
        return maxmoney
    }

    fun getLocation(): ArrayList<Int> {
        return location
    }

    fun getSort(): Int {
        return sort
    }
}