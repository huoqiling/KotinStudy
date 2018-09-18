package com.first.kotlin.util

import android.util.Log
import com.first.kotlin.BuildConfig

/**
 * 日志工具
 */
class LogUtil {

    companion object {
        private val isDebug = BuildConfig.DEBUG
    }

    fun i(log: String?) {
        if (isDebug) {
            Log.i("zhangx", log!!)
        }
    }

    fun e(log: String?) {
        if (isDebug) {
            Log.e("zhangx", log!!)
        }
    }
}