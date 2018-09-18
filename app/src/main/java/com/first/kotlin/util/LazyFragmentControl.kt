package com.first.kotlin.util


/**
 * @date 2018/9/14
 * @author zhangxin
 * @description 懒加载控制
 */
interface LazyFragmentControl {
    /**
     * 界面初始化
     */
    fun initPrepare()

    /**
     * 第一次fragment可见（进行初始化工作）
     */
    fun onFirstUserVisible()

    /**
     * fragment可见（切换回来或者onResume）
     */
    fun onUserVisible()

    /**
     * 第一次fragment不可见（不建议在此处理事件）
     */
    fun onFirstUserInvisible()

    /**
     * fragment不可见（切换掉或者onPause）
     */
    fun onUserInvisible()

    /**
     * 懒加载数据
     * 在onFirstUserVisible之后
     */
    fun lazyData()

    /**
     * 检查上次的显示时间并根据时间间隔自动刷新
     */
    fun checkLastTime()

    /**
     * 根据时间[.setTimeInterval]超时自动刷新
     */
    fun onAutoRefresh()

    /**
     * 设置刷新时间间隔
     *
     * 默认时间30秒
     *
     * @param mTimeInterval 单位milliseconds
     */
    fun setTimeInterval(mTimeInterval: Long)
}
