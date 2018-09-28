package com.first.kotlin.util

import android.annotation.SuppressLint
import android.content.Context
import android.telephony.TelephonyManager
import com.first.kotlin.MyApp

/**
 * 公共方法
 */
class CommonUtil {

    @SuppressLint("MissingPermission")
    fun getDeviceId(): String? {
        try {
            val tm = MyApp.instance.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            return tm.deviceId.toString()
        } catch (e: Exception) {
            e.message
        }
        return ""
    }

    //获取应用的版本名称
    fun getLocalVersionName():String{
        try {
            val packageInfo = MyApp.instance.packageManager.getPackageInfo(MyApp.instance.packageName,0)
            return packageInfo.versionName
        } catch (e: Exception) {
        }
        return "1.0.0"
    }
}