package com.first.kotlin.base

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatDialogFragment
import android.view.*
import android.widget.Toast
import butterknife.ButterKnife
import butterknife.Unbinder
import com.first.kotlin.R

/**
 * dialogFragment基类
 */
abstract class BaseDialogFragment : AppCompatDialogFragment() {

    private var rootView: View? = null
    private var unBinder: Unbinder? = null

    /**
     * 去除边框
     */
    override fun onStart() {
        super.onStart()
        val dialog = dialog
        val window = dialog?.window
        if (null != dialog && null != window) {
            window.setLayout(-1, -2)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Dialog)  //具有阴影效果
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (null == rootView) {
            rootView = inflater.inflate(getLayoutResId(), container, false)
            unBinder = ButterKnife.bind(this, rootView!!)
        }
        initDialog()
        initView()
        return rootView
    }

    /**
     * 去除标题栏
     */
    private fun initDialog() {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) //去除标题栏
        dialog.window!!.setBackgroundDrawableResource(R.color.transparent)
        dialog.window!!.setGravity(Gravity.CENTER)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
    }

    private fun loadData() {
        initView()
    }

    protected abstract fun getLayoutResId(): Int
    protected abstract fun initView()

    override fun onDestroyView() {
        super.onDestroyView()
        (rootView!!.parent as ViewGroup).removeView(rootView!!)
        unBinder!!.unbind()
    }

    /**
     * 弹窗靠下显示
     */
    fun viewGravityButtom() {
        val window = dialog?.window
        val params = window!!.attributes
        params.gravity = Gravity.BOTTOM
        window.attributes = params

    }


    fun showToast(str: String?) {
        Toast.makeText(context, str!!, Toast.LENGTH_LONG).show()
    }

}