package com.first.kotlin

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Process
import android.support.multidex.MultiDex
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.WindowManager
import com.first.kotlin.util.NotNullSingleValueVar
import java.util.*


/**
 * @author zhangxin
 * @date 2018/9/11
 * @description 全局application
 */
class MyApp : Application() {

    private val activityStack: Stack<Activity> = Stack()


    var SCREEN_WIDTH = -1//屏幕宽度
    var SCREEN_HEIGHT = -1//屏幕高度
    var DIMEN_RATE = -1.0f
    var DIMEN_DPI = -1

    companion object {
        // 伴生对象
//        lateinit var instance: MyApp
        var instance: MyApp by NotNullSingleValueVar()
    }


    override fun onCreate() {
        super.onCreate()
        getScreenSize()
        instance = this
    }

    /**
     * 初始化屏幕宽高
     */
    private fun getScreenSize() {
        val windowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        val display = windowManager.defaultDisplay
        display.getMetrics(dm)
        DIMEN_RATE = dm.density / 1.0f
        DIMEN_DPI = dm.densityDpi
        SCREEN_WIDTH = dm.widthPixels
        SCREEN_HEIGHT = dm.heightPixels
        if (SCREEN_WIDTH > SCREEN_HEIGHT) {
            SCREEN_HEIGHT = SCREEN_WIDTH
        }
    }

    /**
     * 添加Activity
     *
     * @param ac
     */
    fun addActivity(ac: AppCompatActivity?) {
        activityStack.add(ac!!)
    }

    /**
     * 移除Activity
     *
     * @param ac
     */
    fun removeActivity(ac: AppCompatActivity?) {
        if (activityStack.contains(ac!!)) {
            activityStack.remove(ac)
        }
    }

    /**
     * 退出App
     */
    fun exitApp() {
        if (activityStack.size > 0) {
            for (ac in activityStack) {
                ac.finish()
            }
        }
        activityStack.clear()
        Process.killProcess(Process.myPid())
        System.exit(0)
    }


    //由于65K限制，采用分包（Dex）
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}