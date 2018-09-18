package com.first.kotlin.base

import android.os.Bundle
import com.first.kotlin.util.LazyFragmentControl

/**
 * @date 2018/9/14
 * @author zhangxin
 * @description  懒加载的Fragment基类
 */
abstract class BaseLazyFragment : BaseFragment(), LazyFragmentControl {

    private var isPrepared: Boolean = false
    /**
     * 第一次onResume中的调用onUserVisible避免操作与onFirstUserVisible操作重复
     */
    private var isFirstResume = true
    private var isFirstVisible = true
    private var isFirstInvisible = true

    private var mLastVisibleTime = System.currentTimeMillis()//上一次显示的时间
    private val mTimeInterval = DEFAULT_TIME_INTERVAL

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initPrepare()
    }

    override fun onResume() {
        super.onResume()
        if (isFirstResume) {
            isFirstResume = false
            return
        }
        if (userVisibleHint) {
            onUserVisible()
        }
    }

    override fun onPause() {
        super.onPause()
        if (userVisibleHint) {
            onUserInvisible()
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false
                initPrepare()
            } else {
                checkLastTime()
                onUserVisible()
            }
        } else {
            if (isFirstInvisible) {
                isFirstInvisible = false
                onFirstUserInvisible()
            } else {
                //界面不显示 设置上次显示时间
                mLastVisibleTime = System.currentTimeMillis()
                onUserInvisible()
            }
        }
    }

    @Override
    override fun checkLastTime() {
        if (System.currentTimeMillis() - mLastVisibleTime > mTimeInterval) {
            onAutoRefresh()
        }
    }

    @Synchronized
    override fun initPrepare() {
        if (isPrepared) {
            onFirstUserVisible()
            lazyData()
        } else {
            isPrepared = true
        }
    }

    override fun onFirstUserVisible() {

    }

    override fun onUserVisible() {

    }

    override fun onFirstUserInvisible() {

    }

    override fun onUserInvisible() {

    }

    override fun lazyData() {

    }

    override fun onAutoRefresh() {

    }

    override fun setTimeInterval(mTimeInterval: Long) {

    }

    companion object {

        private val DEFAULT_TIME_INTERVAL = (30 * 1000).toLong()//默认间隔时间30秒
    }


}
