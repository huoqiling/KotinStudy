package com.first.kotlin.activity.pai

import android.Manifest
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.EditText
import butterknife.BindView
import butterknife.OnClick
import com.first.kotlin.R
import com.first.kotlin.base.BaseActivity
import com.first.kotlin.bean.UserInfo
import com.first.kotlin.net.okgo.Business
import com.first.kotlin.net.okgo.JsonCallback
import com.first.kotlin.util.Constant
import com.first.kotlin.util.Preference
import com.first.kotlin.view.CommonDialog
import com.lzy.okgo.model.Response
import kotlinx.android.synthetic.main.activity_pai_login.*
import permissions.dispatcher.*

/**
 * @author zhangxin
 * @date 2018/9/28
 * @description 派项目登录
 */
@RuntimePermissions
class PaiLoginActivity : BaseActivity() {

    private var isPaiLogin by Preference(Constant.IS_PAI_LOGIN,false)

    @BindView(R.id.etUserName)
    lateinit var etUserName: EditText

    @BindView(R.id.etPassword)
    lateinit var etPassword: EditText

    private var isShowPwd = false

    override fun getLayoutResId(): Int {
        return R.layout.activity_pai_login
    }

    override fun initView() {

    }

    @OnClick(R.id.btnShowPassword, R.id.btnLogin, R.id.btnRegister)
    fun onclick(view: View) {
        when (view.id) {
            R.id.btnShowPassword -> {
                val password = etPassword.text.toString().trim()
                if (!isShowPwd) {
                    isShowPwd = true
                    btnShowPassword.setImageResource(R.mipmap.iv_password_hide)
                    etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    etPassword.setSelection(password.length)
                } else {
                    isShowPwd = false
                    btnShowPassword.setImageResource(R.mipmap.iv_password_show)
                    etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                    etPassword.setSelection(password.length)
                }
            }
            R.id.btnLogin -> {
                PaiLoginActivityPermissionsDispatcher.getPhoneDataWithCheck(this)
            }
            R.id.btnRegister -> {

            }
        }
    }

    fun login(){
        val userName = etUserName.text.toString().trim()
        val password = etPassword.text.toString().trim()
        if(TextUtils.isEmpty(userName)){
            showToast("请输入用户名")
            return
        }
        if(TextUtils.isEmpty(password)){
            showToast("请输入密码")
            return
        }
        showLoadingDialog()
        Business().login(-1,userName,password,"","",this, object:JsonCallback<UserInfo>(){
            override fun onSuccess(response: Response<UserInfo>?) {
                super.onSuccess(response)
                try {
                    dismissDialog()
                    val userInfo = response!!.body()
                    if(userInfo.isSuccessful()){
                        isPaiLogin = true
                        jumpActivity(PaiMainActivity().javaClass)
                        finish()
                    }else{
                        showToast(userInfo.msg)
                    }
                } catch (e: Exception) {
                }
            }

            override fun onError(response: Response<UserInfo>?) {
                super.onError(response)
                dismissDialog()
            }
        })
    }

    @NeedsPermission(Manifest.permission.READ_PHONE_STATE)
    fun getPhoneData(){
        login()
    }

    @OnPermissionDenied(Manifest.permission.READ_PHONE_STATE)
    fun getPhoneDataDenied(){
        showToast("权限未授予，功能无法使用")
    }

    /**
     * 被拒绝并勾选不在提醒授权时，应用需提示用户未获取权限，需用户自己去设置中打开
     */
    @OnNeverAskAgain(Manifest.permission.READ_PHONE_STATE)
    fun PhoneDataNeverAskAgain() {
        showToast("存储权限被拒绝且不再提示")
    }

    /**
     * 告知用户具体需要权限的原因
     * @param messageResId
     * @param request
     */
    @OnShowRationale(Manifest.permission.READ_PHONE_STATE)
    fun showRationaleDialog(request: PermissionRequest) {
        val commonDialog = CommonDialog()
        commonDialog.setMessage("使用此功能需要读取手机状态的权限")
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
        PaiLoginActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults)
    }

}
