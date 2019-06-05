package com.first.kotlin.activity.dialog

import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.first.kotlin.R
import com.first.kotlin.base.BaseActivity
import com.first.kotlin.view.CustomDialog
import com.first.kotlin.view.CustomTitleBar
import com.first.kotlin.view.ShareDialog
import kotlinx.android.synthetic.main.activity_dialog.*

class DialogActivity : BaseActivity(), CustomTitleBar.TitleBarListener {

    @BindView(R.id.btnShowDialog)
    lateinit var btnShowDialog: TextView

    override fun getLayoutResId(): Int {
        return R.layout.activity_dialog
    }

    override fun initView() {
        titleBar.setTitleBarListener(this)
    }

    @OnClick(R.id.btnShowDialog,R.id.btnShareDialog)
    fun onClick(view: View) {
        when (view.id) {
            R.id.btnShowDialog -> {
                val dialog = CustomDialog()
                dialog.show(supportFragmentManager, "弹窗")
            }
            R.id.btnShareDialog -> {
                val dialog = ShareDialog()
                dialog.show(supportFragmentManager, "分享弹窗")
            }
        }
    }

    override fun onLeftClick() {
        finish()
    }

    override fun onRightClick() {

    }
}
