package com.first.kotlin.net.retrofit

import com.first.kotlin.bean.BaseEntity
import com.first.kotlin.bean.GlobalParamInfo
import com.first.kotlin.bean.MerchantQueryProductList
import com.first.kotlin.bean.UserInfo
import retrofit2.Call
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.QueryMap

/**
 * 请求接口
 */
interface ApiService {

    //登录
    @POST("user/merchantlogin")
    fun login(@QueryMap map: Map<String, String>): Call<UserInfo>

    //退出登录
    @POST("user/logout")
    fun loginOut(): Call<BaseEntity>

    //1.5 商家查询产品列表
    @POST("infinite/merchantQueryProductList")
    fun merchantQueryProductList(@Query("queryType") queryType: Int, @Query("pageIndex") pageIndex: Int, @Query("pageSize") pageSize: Int): Call<MerchantQueryProductList>

    //获取全局参数
    @POST("app/globalParam")
    fun getGlobalParam(@Query("lastUpdateDate") lastUpdateDate: Long):Call<GlobalParamInfo>
}