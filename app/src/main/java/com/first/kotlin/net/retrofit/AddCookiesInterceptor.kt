package com.first.kotlin.net.retrofit

import com.first.kotlin.util.Constant
import com.first.kotlin.util.LogUtil
import com.first.kotlin.util.Preference
import okhttp3.Interceptor
import okhttp3.Response
import rx.Observable
import java.io.IOException


/**
 * 将本地的cookie追加到http请求头中
 */
class AddCookiesInterceptor : Interceptor {


    private var cookies by Preference(Constant.COOKIES, "")
    private var needSaveCookie by Preference(Constant.NEED_SAVE_COOKIE, false)

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        Observable.just(this.cookies)
                .subscribe({ cookie ->
                    //添加cookie
                    if (!needSaveCookie) {
                        builder.addHeader("cookie", cookie)
                    }else{
                        needSaveCookie = false
                    }
                })
        return chain.proceed(builder.build())
    }
}