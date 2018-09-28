package com.first.kotlin.net.retrofit

import com.first.kotlin.util.Constant
import com.first.kotlin.util.Preference
import okhttp3.Interceptor
import okhttp3.Response
import rx.Observable


/**
 * @author zhangxin
 * @date 2018/9/17
 * @description 将cookie存储到本地
 */

class ReceivedCookiesInterceptor : Interceptor {

    private var cookies by Preference(Constant.COOKIES, "")
    private var needSaveCookie by Preference(Constant.NEED_SAVE_COOKIE, false)

    override fun intercept(chain: Interceptor.Chain?): Response {
        val originalResponse = chain!!.proceed(chain.request())
        if (!originalResponse.headers("set-cookie").isEmpty() && needSaveCookie) {
            val cookieBuffer = StringBuffer()
            Observable.from(originalResponse.headers("set-cookie"))
                    .map({
                        s -> val cookieArray = s.split(",")
                        cookieArray[0]
                    }).subscribe({
                        cookie -> cookieBuffer.append(cookie).append(";")
                    })
            cookies = cookieBuffer.toString()
        }
        return originalResponse
    }

}
