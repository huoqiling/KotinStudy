package com.first.kotlin.activity

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.first.kotlin.R
import com.first.kotlin.adapter.MainAdapter
import com.first.kotlin.adapter.OldMainAdapter
import com.first.kotlin.base.BaseActivity
import com.first.kotlin.util.LogUtil
import com.first.kotlin.util.Preference
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

/**
 * 首页
 */
class MainActivity : BaseActivity() {

    override fun getLayoutResId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        intAdapter()
    }

    override fun isExitAppAgain(): Boolean {
        return true
    }


    private fun intAdapter() {
        val adapter = MainAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        adapter.setNewData(getData())
        adapter.setOnItemClickListener { adapter, view, position ->
            when (position) {
                0 -> {
                    jumpActivity(GridViewActivity().javaClass)
                }
                1 -> {
                    jumpActivity(DialogActivity().javaClass)
                }
                2 -> {
                    val isLogin: Boolean by Preference("isLogin", false)
                    LogUtil().i("isLogin=="+isLogin)
                    if (isLogin) {
                        jumpActivity(KotlinMainActivity().javaClass)
                    }else{
                        jumpActivity(LoginActivity().javaClass)
                    }

                }
            }
        }
    }

    private fun getData(): List<String>? {
        val list = ArrayList<String>()
        list.add("gridView用法")
        list.add("dialog")
        list.add("Kotlin项目")
        return list
    }
}



