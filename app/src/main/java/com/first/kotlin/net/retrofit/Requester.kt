package com.first.kotlin.net.retrofit

import android.text.TextUtils
import com.first.kotlin.MyApp
import com.first.kotlin.bean.BaseEntity
import com.first.kotlin.bean.GlobalParamInfo
import com.first.kotlin.bean.MerchantQueryProductList
import com.first.kotlin.bean.UserInfo
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.google.gson.GsonBuilder


/**
 * 请求的主类
 */

class Requester {

    companion object {

        private fun <T> getService(baseUrl: String, service: Class<T>): T {


            val cookieJar = PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(MyApp.instance.applicationContext))

            val client = OkHttpClient.Builder()
                    //自定义拦截器用于日志输出
                    .addInterceptor(LogInterceptor())
                    .cookieJar(cookieJar)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .build()

            val retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    //格式转换
                    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                    //正常的retrofit返回的是call，此方法用于将call转化成Rxjava的Observable或其他类型
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client)
                    .build()
            return retrofit.create(service)
        }

        //可用于多种不同种类的请求
        fun apiService(): ApiService {
            return getService(BaseUrl().getBasicUrl(), ApiService::class.java)
        }

        fun apiBossService(): ApiService {
            return getService(BaseUrl().getBasicBossUrl(), ApiService::class.java)
        }
    }

    /**
     * 登录
     */
    fun login(userName: String, password: String, token: String, listener: RetrofitResponseListener<UserInfo>) {
        val map = HashMap<String, String>()
        map.put("username", userName)
        if (!TextUtils.isEmpty(password)) {
            map.put("password", password)
        }
        if (!TextUtils.isEmpty(token)) {
            map.put("token", token)
        }
        apiService().login(map).enqueue(request(listener))
    }

    /**
     * 退出登录
     */
    fun loginOut(listener: RetrofitResponseListener<BaseEntity>) {
        apiService().loginOut().enqueue(request(listener))
    }

    /**
     *  商家查询产品列表
     *   queryType	int	查询条件 1竞拍中 2未开拍 3已结束
     */
    fun merchantQueryProductList(queryType: Int, pageIndex: Int, pageSize: Int, listener: RetrofitResponseListener<MerchantQueryProductList>) {
        apiBossService().merchantQueryProductList(queryType, pageIndex, pageSize).enqueue(request(listener))
    }

    /**
     * 获取系统全局参数
     */
    fun getGlobalParam(listener: RetrofitResponseListener<GlobalParamInfo>) {
        apiBossService().getGlobalParam(System.currentTimeMillis()).enqueue(request(listener))
    }


    private fun <T> request(responseListener: RetrofitResponseListener<T>): Callback<T> {
        val callBack = object : Callback<T> {
            override fun onFailure(call: Call<T>?, t: Throwable?) {
                responseListener.isFailed()
            }

            override fun onResponse(call: Call<T>?, response: Response<T>?) {
                responseListener.isSuccessful(response)
            }

        }
        return callBack
    }

    interface RetrofitResponseListener<T> {
        fun isSuccessful(response: Response<T>?)
        fun isFailed()
    }
}
