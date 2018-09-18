package com.first.kotlin.base

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import butterknife.ButterKnife
import butterknife.Unbinder
import com.first.kotlin.MyApp
import com.first.kotlin.event.BaseEvent
import com.first.kotlin.view.CustomLoadingDialog
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


/**
 * fragment基类
 */

abstract class BaseFragment : DialogFragment() {

    private var rootView: View? = null
    private var unbinder: Unbinder? = null
    private var loadingDialog: CustomLoadingDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        if (rootView == null) {
            rootView = inflater.inflate(getLayoutResId(), container, false)
            unbinder = ButterKnife.bind(this, rootView!!)
        }

        if (userEventBus()) {
            EventBus.getDefault().register(this)
        }
        initView()
        return rootView

    }

    protected abstract fun getLayoutResId(): Int
    protected abstract fun initView()

    open fun userEventBus(): Boolean {
        return false
    }


    /**
     * 显示弹窗
     */
    open fun showLoadingDialog() {
        showLoadingDialog(true)
    }

    open fun showLoadingDialog(isCancellable: Boolean) {
        loadingDialog = CustomLoadingDialog()
        loadingDialog!!.isCancelable = isCancellable
        loadingDialog!!.show(childFragmentManager, "loading")
    }

    /**
     * 弹窗消失
     */
    open fun dismissDialog() {
        loadingDialog!!.dismiss()
    }

    @Subscribe
    open fun onEventMainThread(event: BaseEvent) {

    }

    open fun showToast(str: String?) {
        Toast.makeText(MyApp.instance, str!!, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder!!.unbind()
        ((rootView!!.parent) as ViewGroup).removeView(rootView!!)
        if (userEventBus()) {
            EventBus.getDefault().unregister(this)
        }
    }
}
