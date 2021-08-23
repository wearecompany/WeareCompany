package com.weare.wearecompany.ui.base


import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<B: ViewDataBinding>(
    @LayoutRes private val layoutResId: Int,
    //clazz: KClass<VM>
) : AppCompatActivity(), BaseFragment.CallBack {

    protected lateinit var mViewDataBinding: B
    //protected val viewModel : VM by viewModel(clazz)

    abstract fun onCreate()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
        onCreate()
    }

    private fun performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, layoutResId)
        mViewDataBinding.lifecycleOwner = this
        //mViewDataBinding.setVariable(BR.viewModel, viewModel)
        mViewDataBinding.executePendingBindings()
    }

    override fun onFragmentAttached() {

    }

    override fun onFragmentDetached(tag: String) {

    }
}