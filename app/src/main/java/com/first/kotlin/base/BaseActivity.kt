package com.first.kotlin.base

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import butterknife.ButterKnife
import butterknife.Unbinder
import com.first.kotlin.MyApp
import com.first.kotlin.event.BaseEvent
import com.first.kotlin.view.CustomLoadingDialog
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


/**
 * @author zhangxin
 * @date 2018/9/11
 * @description 自定义activity基类
 */
abstract class BaseActivity : AppCompatActivity() {

    private var exitTime: Long = 0
    private var unBinder: Unbinder? = null
    private var loadingDialog: CustomLoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
        MyApp().addActivity(this)
        if (useEventBus()) {
            EventBus.getDefault().register(this)
        }
        unBinder = ButterKnife.bind(this)
        initView()
    }

    protected abstract fun getLayoutResId(): Int
    protected abstract fun initView()


    open fun useEventBus(): Boolean {
        return false
    }

    /**
     * 是否退出app
     */
    open fun isExitAppAgain(): Boolean {
        return false
    }


    /**
     * 显示弹窗
     */
    open fun showLoadingDialog() {
        showLoadingDialog(true)
    }

    open fun showLoadingDialog(isCancellable: Boolean) {
        loadingDialog = CustomLoadingDialog()
        loadingDialog!!.isCancelable = isCancellable
        loadingDialog!!.show(supportFragmentManager, "loading")
    }

    /**
     * 弹窗消失
     */
    open fun dismissDialog() {
        loadingDialog!!.dismiss()
    }

    override fun onBackPressed() {
        if (isExitAppAgain()) {
            exitApp()
        } else {
            super.onBackPressed()
        }
    }

    private fun exitApp() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            showToast("再按一次退出程序")
            exitTime = System.currentTimeMillis()
        } else {
            MyApp().exitApp()
        }
    }

    @Subscribe
    open fun onEventMainThread(eventBus: BaseEvent) {

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("baseActivity", "onDestroy activity")
        MyApp().removeActivity(this)
        if (useEventBus()) {
            EventBus.getDefault().unregister(this)
        }
        unBinder!!.unbind()
    }


    open fun showToast(str: String?) {
        Toast.makeText(this, str!!, Toast.LENGTH_LONG).show()
    }

    /**
     * Activity调转
     */
    open fun jumpActivity(clazz: Class<*>) {
        val intent = Intent()
        intent.setClass(this, clazz)
        startActivity(intent)
    }
}