package com.first.kotlin.net.okgo

import com.first.kotlin.net.retrofit.BaseUrl

/**
 * @author zhangxin
 * @date 2018/9/28
 * @description
 */
interface OkGoUrl {

    companion object {
        var LOGIN_URL = BaseUrl().getBasicUrl() + "user/applogin"
    }
}