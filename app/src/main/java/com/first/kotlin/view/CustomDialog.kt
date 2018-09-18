package com.first.kotlin.view

import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.first.kotlin.R
import com.first.kotlin.base.BaseDialogFragment

/**
 * 自定义弹窗
 */
class CustomDialog : BaseDialogFragment() {


    @BindView(R.id.btnHappy)
    lateinit var btnHappy: TextView

    @BindView(R.id.btnNotHappy)
    lateinit var btnNotHappy: TextView

    override fun getLayoutResId(): Int {
        return R.layout.dialog_show
    }

    override fun initView() {

    }

    @OnClick(R.id.btnHappy, R.id.btnNotHappy)
    fun onClick(view: View) {
        when (view.id) {
            R.id.btnNotHappy -> {
                showToast("不开心")
            }
            R.id.btnHappy -> {
                showToast("开心死了")
            }
        }
    }

}