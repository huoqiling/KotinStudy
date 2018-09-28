package com.first.kotlin.net.okgo

import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.AbsCallback
import com.lzy.okgo.model.HttpParams

/**
 * @author zhangxin
 * @date 2018/9/28
 * @description OkGo帮助类
 */
class OkGoUtil {


    /**
     * post方法 带参数
     *
     * @param url        地址
     * @param tag        标志
     * @param httpParams 参数
     * @param <T>        实体类
    </T> */
    fun <T> doPost(url: String, tag: Any, httpParams: HttpParams?, callback: AbsCallback<T>) {
        if (null != httpParams) {
            OkGo.post<T>(url).tag(tag).params(httpParams).execute(callback)
        } else {
            OkGo.post<T>(url).tag(tag).execute(callback)
        }
    }

    /**
     * 待参数的get方法
     *
     * @param url        地址
     * @param tag        标志
     * @param httpParams 参数
     * @param <T>        实体类
     */
    fun <T> doGet(url: String, tag: Any, httpParams: HttpParams?, callback: AbsCallback<T>) {
        if(httpParams == null){
            OkGo.get<T>(url).tag(tag).execute(callback)
        }else{
            OkGo.get<T>(url).tag(tag).params(httpParams).execute(callback)
        }
    }
}