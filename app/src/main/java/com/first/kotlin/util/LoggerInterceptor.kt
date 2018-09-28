package com.first.kotlin.util

import android.text.TextUtils
import com.orhanobut.logger.Logger
import okhttp3.*
import okio.Buffer
import java.io.IOException

/**
 * @Author zhangxin
 * @date 2017/6/30 18:08
 * @description log打印
 */
class LoggerInterceptor(tag: String, private val isPrint: Boolean // 是否打印
) : Interceptor {
    private val tag: String

    init {
        var tag = tag
        if (TextUtils.isEmpty(tag)) {
            tag = TAG
        }
        this.tag = tag
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url().toString()
        logForRequest(request)
        val response = chain.proceed(request)
        return logForResponse(response)
    }

    /**
     * 禁止log打印
     */
//    private fun forbidLog(url: String): Boolean {
//        return isPrint && !url.contains(UrlConstant.UrlMethod.CHAT_LIST_URL) && url != UrlConstant.UrlMethod.BALANCE && url != UrlConstant.UrlMethod.GET_CHAT_FRIEND_LIST
//    }

    private fun logForResponse(response: Response): Response {
        try {
            Logger.init(tag).methodCount(0).hideThreadInfo()
            val builder = response.newBuilder()
            val clone = builder.build()
            var contentType = ""
            var body: ResponseBody? = null
            var mediaType: MediaType? = null
            if (isPrint) {
                body = clone.body()
                if (body != null) {
                    mediaType = body.contentType()
                    if (mediaType != null) {
                        contentType = mediaType.toString().trim { it <= ' ' }
                    }
                }
            }
            val logInfo = ("type : response（响应）"
                    + "\n" + "url : " + clone.request().url()
                    + "\n" + "code : " + clone.code()
                    + "\n" + "protocol : " + clone.protocol()
                    + "\n" + (if (!TextUtils.isEmpty(clone.message())) "message : " + clone.message() else "message : ")
                    + "\n" + "responseBody's contentType : " + contentType)

            if (clone.code() == 200) {
                Logger.d(logInfo)
            } else {
                Logger.e(logInfo)
            }

            if (body != null) {
                if (mediaType != null) {
                    if (isText(mediaType)) {
                        val resp = body.string()
                        Logger.json(resp)
                        body = ResponseBody.create(mediaType, resp)
                        return response.newBuilder().body(body).build()
                    } else {
                        Logger.d("responseBody's content : " + " maybe [file part] , too large too print , ignored!")
                    }
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return response
    }

    private fun logForRequest(request: Request) {
        try {
            Logger.init(tag).methodCount(0).hideThreadInfo()
            val url = request.url().toString()
            val headers = request.headers()

            val requestBody = request.body()
            var contentType = ""
            var content = ""
            if (requestBody != null) {
                val mediaType = requestBody.contentType()
                if (mediaType != null) {
                    contentType = mediaType.toString()
                    if (true) {
                        content = bodyToString(request)
                    } else {
                        content = "requestBody's content : " + " maybe [file part] , too large too print , ignored!"
                    }
                }
            }
            Logger.v("type : request（请求）"
                    + "\n" + "method : " + request.method()
                    + "\n" + "url : " + url
                    + "\n" + (if (headers != null && headers.size() > 0) "headers : " + headers.toString().trim { it <= ' ' } else "headers : ")
                    + "\n" + "requestBody's contentType : " + contentType
                    + "\n" + "requestBody's content : " + if (!TextUtils.isEmpty(content)) "\n{\n  " + content.replace("&".toRegex(), "\n  ").replace("=".toRegex(), " : ") + "\n}" else content)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun isText(mediaType: MediaType): Boolean {
        if (mediaType.type() != null && mediaType.type() == "text") {
            return true
        }
        if (mediaType.subtype() != null) {
            if (mediaType.subtype() == "json" ||
                    mediaType.subtype() == "xml" ||
                    mediaType.subtype() == "html" ||
                    mediaType.subtype() == "webviewhtml")
                return true
        }
        return false
    }

    private fun bodyToString(request: Request): String {
        try {
            val copy = request.newBuilder().build()
            val buffer = Buffer()
            copy.body()!!.writeTo(buffer)
            return buffer.readUtf8()
        } catch (e: IOException) {
            return "something error when show requestBody."
        }

    }

    companion object {

        val TAG = "OkHttpUtils"
    }
}
