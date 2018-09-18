package com.first.kotlin.net

import android.annotation.SuppressLint
import android.util.Log
import com.first.kotlin.util.Constant
import com.first.kotlin.util.LogUtil
import com.first.kotlin.util.Preference
import okhttp3.Interceptor
import okhttp3.Response
import java.text.SimpleDateFormat
import java.util.*

/**
 * retrofit拦截器
 */

class LogInterceptor : Interceptor {

    private val tag = "Retrofit"

    @SuppressLint("SimpleDateFormat")
    private val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")

    override fun intercept(chain: Interceptor.Chain?): Response {
        val request = chain!!.request()

        Log.i(tag, format.format(Date()) + " Request" + "\nmethod:" + request.method() + "\nurl:" + request.url() + "\nbody:" + request.body())

        val response = chain.proceed(request)
        //response.peekBody不会关闭流
        Log.i(tag, format.format(Date()) + " Response " + "\nsuccessful:" + response.isSuccessful + "\nbody:" + response.peekBody(1024)?.string())

        return response
    }

}
