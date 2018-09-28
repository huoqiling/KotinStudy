package com.first.kotlin.net.okgo

import android.text.TextUtils
import com.first.kotlin.bean.UserInfo
import com.first.kotlin.util.CommonUtil
import com.lzy.okgo.model.HttpParams

/**
 * @author zhangxin
 * @date 2018/9/28
 * @description okGo的网络请求
 */
class Business {

    /**
     * 登录
     */
    fun login(codeType: Int, userName: String, password: String, token: String, verifyCode: String, tag: Any, callback: JsonCallback<UserInfo>) {
        val params = HttpParams()
        params.put("username", userName)
        params.put("clientVersion", CommonUtil().getLocalVersionName())
        params.put("androidDeviceToken", CommonUtil().getDeviceId())
        params.put("noticePlatform", String.format("%s", 1))
        params.put("clientType", String.format("%s", 1))
        params.put("deviceName", android.os.Build.BRAND + "")
        params.put("deviceType", android.os.Build.MODEL + "")
        if (!TextUtils.isEmpty(password)) {
            params.put("password", password)
        }
        if (!TextUtils.isEmpty(token)) {
            params.put("token", token)
        }
        if (!TextUtils.isEmpty(verifyCode)) {
            params.put("verifyCode", verifyCode)
        }
        if (codeType != -1) {
            params.put("codeType", codeType.toString() + "")
        }
        OkGoUtil().doPost(OkGoUrl.LOGIN_URL, tag, params, callback)
    }
}