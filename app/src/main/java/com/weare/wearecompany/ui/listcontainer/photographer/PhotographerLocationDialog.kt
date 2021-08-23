package com.weare.wearecompany.ui.listcontainer.photographer

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.JsonArray
import com.weare.wearecompany.R
import kotlinx.android.synthetic.main.bottom_dialog_location.view.*

class PhotographerLocationDialog(var con:Context, var setupdata:ArrayList<Int>, val itemClick: (ArrayList<Int>) -> Unit) : BottomSheetDialogFragment() {

    private lateinit var postimage: ImageView
    private lateinit var posttext: TextView
    var click_list = ArrayList<Int>()
    private var clicknum = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.bottom_dialog_location, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (setupdata.size != 0) {
            for (i in setupdata) {
                click_list.add(i)
                when(i) {
                    0 -> locationOn(view.location_image_0, view.location_text_0, 0)
                    1 -> locationOn(view.location_image_1, view.location_text_1, 1)
                    2 -> locationOn(view.location_image_2, view.location_text_2, 2)
                    3 -> locationOn(view.location_image_3, view.location_text_3, 3)
                    4 -> locationOn(view.location_image_4, view.location_text_4, 4)
                    5 -> locationOn(view.location_image_5, view.location_text_5, 5)
                    6 -> locationOn(view.location_image_6, view.location_text_6, 6)
                    7 -> locationOn(view.location_image_7, view.location_text_7, 7)
                    8 -> locationOn(view.location_image_8, view.location_text_8, 8)
                    9 -> locationOn(view.location_image_9, view.location_text_9, 9)
                    10 -> locationOn(view.location_image_10, view.location_text_10, 10)
                    11 -> locationOn(view.location_image_11, view.location_text_11, 11)
                    12 -> locationOn(view.location_image_12, view.location_text_12, 12)
                    13 -> locationOn(view.location_image_13, view.location_text_13, 13)
                    14 -> locationOn(view.location_image_14, view.location_text_14, 14)
                    15 -> locationOn(view.location_image_15, view.location_text_15, 15)
                }
            }

        }

        view.location_0.setOnClickListener {
            if (click_list.size != 0) {
                if (click_list.size >= 3) {
                    val i1 = 1
                    for (i in i1..click_list.size) {
                        if (0 == click_list[i-1]) {
                            click_list.removeAt(i-1)
                            locationReset(view.location_image_0,view.location_text_0,0)
                            return@setOnClickListener
                        }
                    }
                } else {
                    val i1 = 1
                    for (i in i1..click_list.size) {
                        if (0 == click_list[i-1]) {
                            click_list.removeAt(i-1)
                            locationReset(view.location_image_0,view.location_text_0,0)
                            return@setOnClickListener
                        }
                    }
                    click_list.add(0)
                    locationOn(view.location_image_0, view.location_text_0, 0)
                }

            } else {
                click_list.add(0)
                locationOn(view.location_image_0, view.location_text_0, 0)
            }
            //itemClick(0)
        }
        view.location_1.setOnClickListener {
            if (click_list.size != 0) {
                if (click_list.size >= 3) {
                    val i1 = 1
                    for (i in i1..click_list.size) {
                        if (1 == click_list[i-1]) {
                            click_list.removeAt(i-1)
                            locationReset(view.location_image_1,view.location_text_1,1)
                            return@setOnClickListener
                        }
                    }
                } else {
                    val i1 = 1
                    for (i in i1..click_list.size) {
                        if (1 == click_list[i-1]) {
                            click_list.removeAt(i-1)
                            locationReset(view.location_image_1,view.location_text_1,1)
                            return@setOnClickListener
                        }
                    }
                    click_list.add(1)
                    locationOn(view.location_image_1, view.location_text_1, 1)
                }

            } else {
                click_list.add(1)
                locationOn(view.location_image_1, view.location_text_1, 1)
            }
        }
        view.location_2.setOnClickListener {
            if (click_list.size != 0) {
                if (click_list.size >= 3) {
                    val i1 = 1
                    for (i in i1..click_list.size) {
                        if (2 == click_list[i-1]) {
                            click_list.removeAt(i-1)
                            locationReset(view.location_image_2,view.location_text_2,2)
                            return@setOnClickListener
                        }
                    }
                } else {
                    val i1 = 1
                    for (i in i1..click_list.size) {
                        if (2 == click_list[i-1]) {
                            click_list.removeAt(i-1)
                            locationReset(view.location_image_2,view.location_text_2,2)
                            return@setOnClickListener
                        }
                    }
                    click_list.add(2)
                    locationOn(view.location_image_2, view.location_text_2, 2)
                }

            } else {
                click_list.add(2)
                locationOn(view.location_image_2, view.location_text_2, 2)
            }
        }
        view.location_3.setOnClickListener {
            if (click_list.size != 0) {
                if (click_list.size >= 3) {
                    val i1 = 1
                    for (i in i1..click_list.size) {
                        if (3 == click_list[i-1]) {
                            click_list.removeAt(i-1)
                            locationReset(view.location_image_3,view.location_text_3,3)
                            return@setOnClickListener
                        }
                    }
                } else {
                    val i1 = 1
                    for (i in i1..click_list.size) {
                        if (3 == click_list[i-1]) {
                            click_list.removeAt(i-1)
                            locationReset(view.location_image_3,view.location_text_3,3)
                            return@setOnClickListener
                        }
                    }
                    click_list.add(3)
                    locationOn(view.location_image_3, view.location_text_3, 3)
                }

            } else {
                click_list.add(3)
                locationOn(view.location_image_3, view.location_text_3, 3)
            }
        }
        view.location_4.setOnClickListener {
            if (click_list.size != 0) {
                if (click_list.size >= 3) {
                    val i1 = 1
                    for (i in i1..click_list.size) {
                        if (4 == click_list[i-1]) {
                            click_list.removeAt(i-1)
                            locationReset(view.location_image_4,view.location_text_4,4)
                            return@setOnClickListener
                        }
                    }
                } else {
                    val i1 = 1
                    for (i in i1..click_list.size) {
                        if (4 == click_list[i-1]) {
                            click_list.removeAt(i-1)
                            locationReset(view.location_image_4,view.location_text_4,4)
                            return@setOnClickListener
                        }
                    }
                    click_list.add(4)
                    locationOn(view.location_image_4, view.location_text_4, 4)
                }

            } else {
                click_list.add(4)
                locationOn(view.location_image_4, view.location_text_4, 4)
            }
        }
        view.location_5.setOnClickListener {
            if (click_list.size != 0) {
                if (click_list.size >= 3) {
                    val i1 = 1
                    for (i in i1..click_list.size) {
                        if (5 == click_list[i-1]) {
                            click_list.removeAt(i-1)
                            locationReset(view.location_image_5,view.location_text_5,5)
                            return@setOnClickListener
                        }
                    }
                } else {
                    val i1 = 1
                    for (i in i1..click_list.size) {
                        if (5 == click_list[i-1]) {
                            click_list.removeAt(i-1)
                            locationReset(view.location_image_5,view.location_text_5,5)
                            return@setOnClickListener
                        }
                    }
                    click_list.add(5)
                    locationOn(view.location_image_5, view.location_text_5, 5)
                }

            } else {
                click_list.add(5)
                locationOn(view.location_image_5, view.location_text_5, 5)
            }
        }
        view.location_6.setOnClickListener {
            if (click_list.size != 0) {
                if (click_list.size >= 3) {
                    val i1 = 1
                    for (i in i1..click_list.size) {
                        if (6 == click_list[i-1]) {
                            click_list.removeAt(i-1)
                            locationReset(view.location_image_6,view.location_text_6,6)
                            return@setOnClickListener
                        }
                    }
                } else {
                    val i1 = 1
                    for (i in i1..click_list.size) {
                        if (6 == click_list[i-1]) {
                            click_list.removeAt(i-1)
                            locationReset(view.location_image_6,view.location_text_6,6)
                            return@setOnClickListener
                        }
                    }
                    click_list.add(6)
                    locationOn(view.location_image_6, view.location_text_6, 6)
                }

            } else {
                click_list.add(6)
                locationOn(view.location_image_6, view.location_text_6, 6)
            }
        }
        view.location_7.setOnClickListener {
            if (click_list.size != 0) {
                if (click_list.size >= 3) {
                    val i1 = 1
                    for (i in i1..click_list.size) {
                        if (7 == click_list[i-1]) {
                            click_list.removeAt(i-1)
                            locationReset(view.location_image_7,view.location_text_7,7)
                            return@setOnClickListener
                        }
                    }
                } else {
                    val i1 = 1
                    for (i in i1..click_list.size) {
                        if (7 == click_list[i-1]) {
                            click_list.removeAt(i-1)
                            locationReset(view.location_image_7,view.location_text_7,7)
                            return@setOnClickListener
                        }
                    }
                    click_list.add(7)
                    locationOn(view.location_image_7, view.location_text_7, 7)
                }

            } else {
                click_list.add(7)
                locationOn(view.location_image_7, view.location_text_7, 7)
            }
        }
        view.location_8.setOnClickListener {
            if (click_list.size != 0) {
                if (click_list.size >= 3) {
                    val i1 = 1
                    for (i in i1..click_list.size) {
                        if (8 == click_list[i-1]) {
                            click_list.removeAt(i-1)
                            locationReset(view.location_image_8,view.location_text_8,8)
                            return@setOnClickListener
                        }
                    }
                } else {
                    val i1 = 1
                    for (i in i1..click_list.size) {
                        if (8 == click_list[i-1]) {
                            click_list.removeAt(i-1)
                            locationReset(view.location_image_8,view.location_text_8,8)
                            return@setOnClickListener
                        }
                    }
                    click_list.add(8)
                    locationOn(view.location_image_8, view.location_text_8, 8)
                }

            } else {
                click_list.add(8)
                locationOn(view.location_image_8, view.location_text_8, 8)
            }
        }
        view.location_9.setOnClickListener {
            if (click_list.size != 0) {
                if (click_list.size >= 3) {
                    val i1 = 1
                    for (i in i1..click_list.size) {
                        if (9 == click_list[i-1]) {
                            click_list.removeAt(i-1)
                            locationReset(view.location_image_9,view.location_text_9,9)
                            return@setOnClickListener
                        }
                    }
                } else {
                    val i1 = 1
                    for (i in i1..click_list.size) {
                        if (9 == click_list[i-1]) {
                            click_list.removeAt(i-1)
                            locationReset(view.location_image_9,view.location_text_9,9)
                            return@setOnClickListener
                        }
                    }
                    click_list.add(9)
                    locationOn(view.location_image_9, view.location_text_9, 9)
                }

            } else {
                click_list.add(9)
                locationOn(view.location_image_9, view.location_text_9, 9)
            }
        }
        view.location_10.setOnClickListener {
            if (click_list.size != 0) {
                if (click_list.size >= 3) {
                    val i1 = 1
                    for (i in i1..click_list.size) {
                        if (10 == click_list[i-1]) {
                            click_list.removeAt(i-1)
                            locationReset(view.location_image_10,view.location_text_10,10)
                            return@setOnClickListener
                        }
                    }
                } else {
                    val i1 = 1
                    for (i in i1..click_list.size) {
                        if (10 == click_list[i-1]) {
                            click_list.removeAt(i-1)
                            locationReset(view.location_image_10,view.location_text_10,10)
                            return@setOnClickListener
                        }
                    }
                    click_list.add(10)
                    locationOn(view.location_image_10, view.location_text_10, 10)
                }

            } else {
                click_list.add(10)
                locationOn(view.location_image_10, view.location_text_10, 10)
            }
        }
        view.location_11.setOnClickListener {
            if (click_list.size != 0) {
                if (click_list.size >= 3) {
                    val i1 = 1
                    for (i in i1..click_list.size) {
                        if (11 == click_list[i-1]) {
                            click_list.removeAt(i-1)
                            locationReset(view.location_image_11,view.location_text_11,11)
                            return@setOnClickListener
                        }
                    }
                } else {
                    val i1 = 1
                    for (i in i1..click_list.size) {
                        if (11 == click_list[i-1]) {
                            click_list.removeAt(i-1)
                            locationReset(view.location_image_11,view.location_text_11,11)
                            return@setOnClickListener
                        }
                    }
                    click_list.add(11)
                    locationOn(view.location_image_11, view.location_text_3, 11)
                }

            } else {
                click_list.add(11)
                locationOn(view.location_image_11, view.location_text_11, 11)
            }
        }
        view.location_12.setOnClickListener {
            if (click_list.size != 0) {
                if (click_list.size >= 3) {
                    val i1 = 1
                    for (i in i1..click_list.size) {
                        if (12 == click_list[i-1]) {
                            click_list.removeAt(i-1)
                            locationReset(view.location_image_12,view.location_text_12,12)
                            return@setOnClickListener
                        }
                    }
                } else {
                    val i1 = 1
                    for (i in i1..click_list.size) {
                        if (12 == click_list[i-1]) {
                            click_list.removeAt(i-1)
                            locationReset(view.location_image_12,view.location_text_12,12)
                            return@setOnClickListener
                        }
                    }
                    click_list.add(12)
                    locationOn(view.location_image_12, view.location_text_12, 12)
                }

            } else {
                click_list.add(12)
                locationOn(view.location_image_12, view.location_text_12, 12)
            }
        }
        view.location_13.setOnClickListener {
            if (click_list.size != 0) {
                if (click_list.size >= 3) {
                    val i1 = 1
                    for (i in i1..click_list.size) {
                        if (0 == click_list[i-1]) {
                            click_list.removeAt(i-1)
                            locationReset(view.location_image_13,view.location_text_13,13)
                            return@setOnClickListener
                        }
                    }
                } else {
                    val i1 = 1
                    for (i in i1..click_list.size) {
                        if (0 == click_list[i-1]) {
                            click_list.removeAt(i-1)
                            locationReset(view.location_image_13,view.location_text_13,13)
                            return@setOnClickListener
                        }
                    }
                    click_list.add(13)
                    locationOn(view.location_image_13, view.location_text_13, 13)
                }

            } else {
                click_list.add(13)
                locationOn(view.location_image_13, view.location_text_13, 13)
            }
        }
        view.location_14.setOnClickListener {
            if (click_list.size != 0) {
                if (click_list.size >= 3) {
                    val i1 = 1
                    for (i in i1..click_list.size) {
                        if (14 == click_list[i-1]) {
                            click_list.removeAt(i-1)
                            locationReset(view.location_image_14,view.location_text_14,14)
                            return@setOnClickListener
                        }
                    }
                } else {
                    val i1 = 1
                    for (i in i1..click_list.size) {
                        if (14 == click_list[i-1]) {
                            click_list.removeAt(i-1)
                            locationReset(view.location_image_14,view.location_text_14,14)
                            return@setOnClickListener
                        }
                    }
                    click_list.add(14)
                    locationOn(view.location_image_14, view.location_text_14, 14)
                }

            } else {
                click_list.add(14)
                locationOn(view.location_image_14, view.location_text_14, 14)
            }
        }
        view.location_15.setOnClickListener {
            if (click_list.size != 0) {
                if (click_list.size >= 3) {
                    val i1 = 1
                    for (i in i1..click_list.size) {
                        if (15 == click_list[i-1]) {
                            click_list.removeAt(i-1)
                            locationReset(view.location_image_15,view.location_text_15,15)
                            return@setOnClickListener
                        }
                    }
                } else {
                    val i1 = 1
                    for (i in i1..click_list.size) {
                        if (15 == click_list[i-1]) {
                            click_list.removeAt(i-1)
                            locationReset(view.location_image_15,view.location_text_15,15)
                            return@setOnClickListener
                        }
                    }
                    click_list.add(15)
                    locationOn(view.location_image_15, view.location_text_15, 15)
                }

            } else {
                click_list.add(15)
                locationOn(view.location_image_15, view.location_text_15, 15)
            }
        }

        view.top_di_request.setOnClickListener{
            itemClick(click_list)
            dialog?.dismiss()
        }

    }

    private fun locationOn(image: ImageView, text: TextView, num:Int) {
        if (clicknum == -1) {
            postimage = image
            posttext = text
            clicknum = num
        }
        image.visibility = View.VISIBLE
        text.setTextColor(Color.parseColor("#6d34f3"))
    }

    private fun locationOff(image: ImageView, text: TextView, num:Int) {
        postimage.visibility = View.GONE
        posttext.setTextColor(Color.parseColor("#000000"))
        postimage = image
        posttext = text
        clicknum = num
    }

    private fun locationReset(image: ImageView, text: TextView, num:Int) {
        image.visibility = View.GONE
        text.setTextColor(Color.parseColor("#000000"))
        clicknum = num
    }
}