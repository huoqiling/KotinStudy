package com.first.kotlin.bean

import android.text.TextUtils
import java.io.Serializable

/**
 * 实体类的基类
 */
abstract class BaseEntity : Serializable {

    var msg: String? = ""
    var resultCode: String? = ""
    var status: String? = ""

    /**
     * 请求成功
     */
    open fun isSuccessful(): Boolean {
        if (!TextUtils.isEmpty(status)) {
            return status!! == "success"
        }
        return false
    }

    open fun isFailed(): Boolean {
        if (!TextUtils.isEmpty(status)) {
            return status!! == "failed"
        }
        return false
    }
}