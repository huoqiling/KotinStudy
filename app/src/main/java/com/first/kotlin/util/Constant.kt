package com.first.kotlin.util

import android.os.Environment


/**
 * @author zhangxin
 * @date 2018/9/14
 * @description 常量
 */

interface Constant {

    companion object {
        var IS_LOGIN = "isLogin"
        var TOKEN = "token"
        var USER_NAME = "userName"
        var COOKIES = "cookie"
        var NEED_SAVE_COOKIE = "needSaveCookie"
        var SD_PATH = Environment.getExternalStorageDirectory().absolutePath+"/KotlinImage/"
    }
}