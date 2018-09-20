package com.first.kotlin.activity

import android.Manifest
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.first.kotlin.R
import com.first.kotlin.adapter.MainAdapter
import com.first.kotlin.base.BaseActivity
import com.first.kotlin.util.LogUtil
import com.first.kotlin.util.Preference
import com.first.kotlin.view.CommonDialog
import kotlinx.android.synthetic.main.activity_main.*
import permissions.dispatcher.*
import java.util.*


/**
 * 首页
 */
@RuntimePermissions
class MainActivity : BaseActivity() {

    override fun getLayoutResId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        intAdapter()
        MainActivityPermissionsDispatcher.getStorageWithCheck(this)
    }

    override fun isExitAppAgain(): Boolean {
        return true
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    fun getStorage() {
    }

    /**
     * 被用户拒绝
     */
    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    fun getStorageDenied() {
        showToast("权限未授予，功能无法使用")
    }

    /**
     * 被拒绝并勾选不在提醒授权时，应用需提示用户未获取权限，需用户自己去设置中打开
     */
    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    fun getStorageNeverAskAgain() {
        showToast("存储权限被拒绝切不再提示")
    }

    /**
     * 告知用户具体需要权限的原因
     * @param messageResId
     * @param request
     */
    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    fun showRationaleDialog(request: PermissionRequest) {
        val commonDialog = CommonDialog()
        commonDialog.setMessage("使用此功能需要存储的权限")
                .setConfirmText("确认")
                .setCancelText("取消")
                .setCommonDialogListener(object : CommonDialog.CommonDialogListener {
                    override fun onConfirm() {
                        request.proceed()//请求权限
                    }

                    override fun onCancel() {
                        request.cancel()
                    }

                }).show(supportFragmentManager, "dialog")
    }

    /**
     * 权限请求回调，提示用户之后，用户点击“允许”或者“拒绝”之后调用此方法
     *
     * @param requestCode  定义的权限编码
     * @param permissions  权限名称
     * @param grantResults 允许/拒绝
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun intAdapter() {
        val adapter = MainAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
        recyclerView.adapter = adapter
        adapter.setNewData(getData())
        adapter.setOnItemClickListener { adapter, view, position ->
            when (position) {
                0 -> {
                    jumpActivity(GridViewActivity().javaClass)
                }
                1 -> {
                    jumpActivity(DialogActivity().javaClass)
                }
                2 -> {
                    val isLogin: Boolean by Preference("isLogin", false)
                    LogUtil().i("isLogin==" + isLogin)
                    if (isLogin) {
                        jumpActivity(KotlinMainActivity().javaClass)
                    } else {
                        jumpActivity(LoginActivity().javaClass)
                    }

                }
            }
        }
    }

    private fun getData(): List<String>? {
        val list = ArrayList<String>()
        list.add("gridView用法")
        list.add("dialog")
        list.add("Kotlin项目")
        return list
    }
}



