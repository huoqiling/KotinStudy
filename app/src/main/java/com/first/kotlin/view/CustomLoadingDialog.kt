package com.first.kotlin.view

import android.content.DialogInterface
import butterknife.BindView
import com.first.kotlin.R
import com.first.kotlin.base.BaseDialogFragment
import com.wang.avi.AVLoadingIndicatorView

/**
 * 自定义加载弹窗
 */
class CustomLoadingDialog : BaseDialogFragment() {

    @BindView(R.id.loading)
    lateinit var loading: AVLoadingIndicatorView

    override fun getLayoutResId(): Int {
        return R.layout.dialog_custom_loading
    }

    override fun initView() {
        loading.smoothToShow()
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        loading.smoothToHide()
    }


}