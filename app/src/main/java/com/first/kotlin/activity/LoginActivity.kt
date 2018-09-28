package com.first.kotlin.activity

import android.text.TextUtils
import android.view.View
import butterknife.OnClick
import com.first.kotlin.R
import com.first.kotlin.base.BaseActivity
import com.first.kotlin.bean.UserInfo
import com.first.kotlin.net.retrofit.Requester
import com.first.kotlin.util.Constant
import com.first.kotlin.util.Preference
import com.first.kotlin.view.CustomTitleBar
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Response

/**
 * @author zhangxin
 * @date 2018/9/11
 * @description 登录
 */
class LoginActivity : BaseActivity(), CustomTitleBar.TitleBarListener {

    private var isLogin by Preference(Constant.IS_LOGIN, false)
    private var token by Preference(Constant.TOKEN, "")
    private var userName by Preference(Constant.USER_NAME, "")
    private var needSaveCookie by Preference(Constant.NEED_SAVE_COOKIE, false)


    override fun getLayoutResId(): Int {
        return R.layout.activity_login
    }

    override fun initView() {
        titleBar.setTitleBarListener(this)
    }

    @OnClick(R.id.btnLogin)
    fun onclick(view: View) {
        when (view.id) {
            R.id.btnLogin -> {
                val name: String = etUserName.text.toString()
                val password: String = etPassword.text.toString()
                if (TextUtils.isEmpty(name)) {
                    showToast("请输入姓名")
                    return
                }
                if (TextUtils.isEmpty(password)) {
                    showToast("请输入密码")
                    return
                }
                showLoadingDialog()
                needSaveCookie = true
                Requester().login(name, password, "", object : Requester.RetrofitResponseListener<UserInfo> {

                    override fun isSuccessful(response: Response<UserInfo>?) {
                        try {
                            dismissDialog()
                            val userInfo = response!!.body()
                            if (userInfo!!.isSuccessful()) {
                                isLogin = true
                                token = userInfo.data!!.appLoginToken + ""
                                userName = name+""
                                jumpActivity(KotlinMainActivity().javaClass)
                                finish()
                            } else {
                                showToast(userInfo.msg + "")
                            }
                        } catch (e: Exception) {
                        }
                    }

                    override fun isFailed() {
                        dismissDialog()
                        showToast("请求失败")
                    }

                })

            }
        }

    }

    override fun onLeftClick() {
        finish()
    }

    override fun onRightClick() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
