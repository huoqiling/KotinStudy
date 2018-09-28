/*
 * Copyright 2016 jeasonlzy(廖子尧)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.first.kotlin.net.okgo

import com.first.kotlin.bean.BaseEntity
import com.first.kotlin.net.retrofit.BaseUrl
import com.first.kotlin.util.CommonUtil
import com.first.kotlin.util.Constant
import com.first.kotlin.util.LogUtil
import com.first.kotlin.util.Preference
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.JsonParser
import com.google.gson.stream.JsonReader
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.AbsCallback
import com.lzy.okgo.request.base.Request
import okhttp3.HttpUrl
import okhttp3.Response
import java.lang.reflect.ParameterizedType
import java.net.SocketException
import java.net.SocketTimeoutException

/**
 * ================================================
 * 默认将返回的数据解析成需要的Bean,可以是 BaseBean，String，List，Map
 * ================================================
 */
abstract class JsonCallback<T> : AbsCallback<T>() {

    private var isPaiLogin by Preference(Constant.IS_PAI_LOGIN, false)
    private var isLoginRequest by Preference(Constant.IS_LOGIN_REQUEST,false)

    private var jsonString = ""

    override fun onStart(request: Request<T, out Request<Any, Request<*, *>>>?) {
        super.onStart(request)
        try {
            if (isPaiLogin) {
                val cookieStore = OkGo.getInstance().cookieJar.cookieStore
                val httpUrl = HttpUrl.parse(BaseUrl().getBasicUrl())
                val cookies = cookieStore.getCookie(httpUrl)
                request!!.headers("Cookie", cookies.toString().substring(1, cookies.toString().length - 1))
            }
            if (isLoginRequest) {
                request!!.headers("deviceId", CommonUtil().getDeviceId())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 请求返回log
     */
    fun getJsonString(): String {
        return jsonString
    }

    override fun convertResponse(response: Response?): T {
        val genType = javaClass.genericSuperclass
        //从上述的类中取出真实的泛型参数，有些类可能有多个泛型，所以是数值
        val params = (genType as ParameterizedType).actualTypeArguments
        //我们的示例代码中，只有一个泛型，所以取出第一个，得到如下结果
        //com.lzy.demo.model.Login
        val type = params[0]

        //这里我们既然都已经拿到了泛型的真实类型，即对应的 class ，那么当然可以开始解析数据了，我们采用 Gson 解析
        //以下代码是根据泛型解析数据，返回对象，返回的对象自动以参数的形式传递到 onSuccess 中，可以直接使用
        val reader = response!!.body()!!.charStream()
        val jsonReader = JsonReader(reader)
        //有数据类型，表示有data
        val data = Convert.fromJson<T>(jsonReader, type)
        jsonString = Convert.toJson(data!!)
/*        val returnData = JsonParser().parse(Convert.toJson(data)).asJsonObject
        val gs = Gson()
        val baseInfo = gs.fromJson<BaseEntity>(returnData, BaseEntity::class.java)
        if (!baseInfo.isSuccessful()) { //请求失败
            response.close()
            throw IllegalStateException(baseInfo.msg, Throwable(baseInfo.resultCode))
        }*/
        response.close()
        return data
    }

    /**
     * 该方法是子线程处理，不能做ui相关的工作
     * 主要作用是解析网络返回的 response 对象,生产onSuccess回调中需要的数据对象
     * 这里的解析工作不同的业务逻辑基本都不一样,所以需要自己实现,以下给出的时模板代码,实际使用根据需要修改
     */

    override fun onSuccess(response: com.lzy.okgo.model.Response<T>?) {

    }


    override fun onError(response: com.lzy.okgo.model.Response<T>?) {
        super.onError(response)
        val code = response!!.code()
        if (code == 404) {
            LogUtil().i("404 当前链接不存在")
        }
        if (response.exception === SocketTimeoutException()) {
            LogUtil().i("请求超时")
        } else if (response.exception === SocketException()) {
            LogUtil().i("服务器异常")
        } else if (response.exception === JsonParseException("")) {
            LogUtil().i("数据解析异常")
        }

        if (response.exception.cause != null) {

        }
    }

}
