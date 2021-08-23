package com.weare.wearecompany.ui.listcontainer.trip

import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.fragment.app.activityViewModels
import com.google.android.material.slider.RangeSlider
import com.weare.wearecompany.R
import com.weare.wearecompany.databinding.BottomDialogAllMoneyBinding
import com.weare.wearecompany.ui.base.BaseFragment
import com.weare.wearecompany.ui.listcontainer.ListContainerActivity
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class TripMoneyFragment : BaseFragment<BottomDialogAllMoneyBinding>(
    R.layout.bottom_dialog_all_money
), View.OnClickListener {

    private val viewmodel: TripViewModel by activityViewModels()

    private lateinit var mContext: Context

    var min:String = ""
    var max:String = ""
    private val dec = DecimalFormat("#,###")
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ListContainerActivity) {
            mContext = context
        }
    }

    override fun onCreate() {
        mViewDataBinding.allMoney.setLabelFormatter { value: Float ->
            val format = NumberFormat.getCurrencyInstance()
            format.maximumFractionDigits = 0
            format.currency = Currency.getInstance("KRW")
            format.format(value.toDouble())
        }

        min = viewmodel.getMin()
        max = viewmodel.getMax()

        if (min == "" && max == "") {
            mViewDataBinding.allMoney.setValues(0f,500000f)
            mViewDataBinding.minMoney.text = dec.format(0)
            mViewDataBinding.maxMoney.text = dec.format(500000)
        } else if (min != "" && max == ""){
            mViewDataBinding.allMoney.setValues(min.toFloat(),500000f)
            mViewDataBinding.minMoney.text = dec.format(min.toInt())
            mViewDataBinding.maxMoney.text = dec.format(500000)
            mViewDataBinding.minMoney.setTextColor(Color.parseColor("#0f0f0f"))
            mViewDataBinding.minMoneyTitle.setTextColor(Color.parseColor("#0f0f0f"))
            mViewDataBinding.maxMoney.setTextColor(Color.parseColor("#0f0f0f"))
            mViewDataBinding.maxMoneyTitle.setTextColor(Color.parseColor("#0f0f0f"))
            mViewDataBinding.moneyAnd.setTextColor(Color.parseColor("#0f0f0f"))
        } else if (min == "" && max != ""){
            mViewDataBinding.allMoney.setValues(0f,max.toFloat())
            mViewDataBinding.minMoney.text = dec.format(0)
            mViewDataBinding.maxMoney.text = dec.format(max.toInt())
            mViewDataBinding.minMoney.setTextColor(Color.parseColor("#0f0f0f"))
            mViewDataBinding.minMoneyTitle.setTextColor(Color.parseColor("#0f0f0f"))
            mViewDataBinding.maxMoney.setTextColor(Color.parseColor("#0f0f0f"))
            mViewDataBinding.maxMoneyTitle.setTextColor(Color.parseColor("#0f0f0f"))
            mViewDataBinding.moneyAnd.setTextColor(Color.parseColor("#0f0f0f"))
        } else {
            mViewDataBinding.allMoney.setValues(min.toFloat(),max.toFloat())
            mViewDataBinding.minMoney.text = dec.format(min.toInt())
            mViewDataBinding.maxMoney.text = dec.format(max.toInt())
            mViewDataBinding.minMoney.setTextColor(Color.parseColor("#0f0f0f"))
            mViewDataBinding.minMoneyTitle.setTextColor(Color.parseColor("#0f0f0f"))
            mViewDataBinding.maxMoney.setTextColor(Color.parseColor("#0f0f0f"))
            mViewDataBinding.maxMoneyTitle.setTextColor(Color.parseColor("#0f0f0f"))
            mViewDataBinding.moneyAnd.setTextColor(Color.parseColor("#0f0f0f"))
        }


        /*mViewDataBinding.allMoney.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener{
            override fun onStartTrackingTouch(slider: RangeSlider) {

            }

            override fun onStopTrackingTouch(slider: RangeSlider) {
                val values = slider.values

                mViewDataBinding.minMoney.text = values[0].toString()
                mViewDataBinding.maxMoney.text = values[1].toString()
            }

        })*/

        mViewDataBinding.allMoney.addOnChangeListener(object : RangeSlider.OnChangeListener{
            override fun onValueChange(slider: RangeSlider, value: Float, fromUser: Boolean) {
                val values = slider.values

                min = String.format("%.0f",values[0])
                max = String.format("%.0f",values[1])

                activity?.runOnUiThread {
                    if (min == "0" && max == "500000") {
                        mViewDataBinding.maxMoneyTitle.text = "원 이상"
                        viewmodel.setMin("")
                        mViewDataBinding.minMoney.text = "0"
                        viewmodel.setMax("")
                        mViewDataBinding.maxMoney.text = "500,000"
                        mViewDataBinding.minMoney.setTextColor(Color.parseColor("#cbcbcb"))
                        mViewDataBinding.minMoneyTitle.setTextColor(Color.parseColor("#cbcbcb"))
                        mViewDataBinding.maxMoney.setTextColor(Color.parseColor("#cbcbcb"))
                        mViewDataBinding.maxMoneyTitle.setTextColor(Color.parseColor("#cbcbcb"))
                        mViewDataBinding.moneyAnd.setTextColor(Color.parseColor("#cbcbcb"))
                    } else {
                        if (min != "0") {
                            viewmodel.setMin(min)
                            mViewDataBinding.minMoney.text = dec.format(min.toInt())
                        } else {
                            viewmodel.setMin("")
                            mViewDataBinding.minMoney.text = dec.format(0)
                        }
                        if (max != "500000") {
                            viewmodel.setMax(max)
                            mViewDataBinding.maxMoneyTitle.text = "원"
                            mViewDataBinding.maxMoney.text = dec.format(max.toInt())
                        } else {
                            viewmodel.setMax("")
                            mViewDataBinding.maxMoney.text = dec.format(500000)
                        }
                        mViewDataBinding.minMoney.setTextColor(Color.parseColor("#0f0f0f"))
                        mViewDataBinding.minMoneyTitle.setTextColor(Color.parseColor("#0f0f0f"))
                        mViewDataBinding.maxMoney.setTextColor(Color.parseColor("#0f0f0f"))
                        mViewDataBinding.maxMoneyTitle.setTextColor(Color.parseColor("#0f0f0f"))
                        mViewDataBinding.moneyAnd.setTextColor(Color.parseColor("#0f0f0f"))
                    }
                    /*viewmodel.setMin(min)
                    mViewDataBinding.minMoney.text = dec.format(min)
                    viewmodel.setMax(max)
                    mViewDataBinding.maxMoney.text = dec.format(max)*/
                }
            }

        })
    }


    override fun onClick(v: View?) {

    }
}