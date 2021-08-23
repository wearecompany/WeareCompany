package com.weare.wearecompany.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<B : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int,
    //clazz: KClass<VM>
) : Fragment() {

    lateinit var mViewDataBinding: B
    //protected val viewModel : VM by viewModel(clazz)
    //private var mActivity: BaseActivity<*, *>? = null

    /*override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is BaseActivity<*, *>) {
            mActivity = context
            mActivity?.onFragmentAttached()
        }
    }*/

    abstract fun onCreate()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) // Fragment Option Menu 사용
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        return mViewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //mViewDataBinding.setVariable(BR.viewModel, viewModel)
        mViewDataBinding.lifecycleOwner = this
        mViewDataBinding.executePendingBindings()

        onCreate()
    }

    /*fun getBaseActivity() : BaseActivity<*, *>? {
        return mActivity
    }

    override fun onDetach() {
        mActivity = null
        super.onDetach()
    }*/

    interface CallBack {
        fun onFragmentAttached()
        fun onFragmentDetached(tag: String)
    }


}