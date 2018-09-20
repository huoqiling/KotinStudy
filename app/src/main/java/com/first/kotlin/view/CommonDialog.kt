package com.first.kotlin.view

import android.annotation.SuppressLint
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.first.kotlin.R
import com.first.kotlin.base.BaseDialogFragment


/**
 * @author zhangxin
 * @date 2018/9/13
 * @description 公共弹窗
 */
class CommonDialog : BaseDialogFragment() {

    @BindView(R.id.tvTitle)
    lateinit var tvTitle: TextView

    @BindView(R.id.tvMessage)
    lateinit var tvMessage: TextView

    private var title: String? = ""
    private var message: String? = ""
    private var confirmText: String? = ""
    private var cancelText = ""

    private var dialogListener: CommonDialogListener? = null


    fun setTitle(title: String): CommonDialog {
        this.title = title
        return this
    }

    fun setMessage(message: String): CommonDialog {
        this.message = message
        return this
    }

    fun setConfirmText(confirmText: String): CommonDialog {
        this.confirmText = confirmText
        return this
    }

    fun setCancelText(cancelText: String): CommonDialog {
        this.cancelText = cancelText
        return this
    }

    fun setCommonDialogListener(listener: CommonDialogListener): CommonDialog {
        dialogListener = listener
        return this
    }


    override fun getLayoutResId(): Int {
        return R.layout.dialog_common
    }

    override fun initView() {
        if (!TextUtils.isEmpty(title)) {
            tvTitle.visibility = View.VISIBLE
            tvTitle.text = title
        } else {
            tvTitle.visibility = View.GONE
        }

        if (!TextUtils.isEmpty(message)) {
            tvMessage.text = message
        }
    }

    @OnClick(R.id.btnCancel, R.id.btnConfirm)
    fun onclick(view: View) {
        when (view.id) {
            R.id.btnCancel -> {
                if (dialogListener != null) {
                    dialogListener!!.onCancel()
                }
                dismiss()
            }
            R.id.btnConfirm -> {
                if (dialogListener != null) {
                    dialogListener!!.onConfirm()
                    dismiss()
                }
            }
        }
    }

    interface CommonDialogListener {
        fun onConfirm()
        fun onCancel()
    }

}