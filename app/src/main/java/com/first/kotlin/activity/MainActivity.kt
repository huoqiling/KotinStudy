package com.first.kotlin.activity

import android.support.v7.widget.LinearLayoutManager
import com.first.kotlin.R
import com.first.kotlin.activity.dialog.DialogActivity
import com.first.kotlin.activity.pai.PaiLoginActivity
import com.first.kotlin.activity.pai.PaiMainActivity
import com.first.kotlin.adapter.MainAdapter
import com.first.kotlin.base.BaseActivity
import com.first.kotlin.util.Constant
import com.first.kotlin.util.LogUtil
import com.first.kotlin.util.Preference
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

/**
 *
 @desc  首页
 @date 2018/10/11
 @author zhangxin
 *
 **/
class MainActivity : BaseActivity() {

    private var isLogin: Boolean by Preference(Constant.IS_LOGIN, false)
    private var isPaiLogin by Preference(Constant.IS_PAI_LOGIN,false)

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
                    LogUtil().i("isLogin==" + isLogin)
                    if (isLogin) {
                        jumpActivity(KotlinMainActivity().javaClass)
                    } else {
                        jumpActivity(LoginActivity().javaClass)
                    }

                }
                3 -> {
                    if(isPaiLogin){
                        jumpActivity(PaiMainActivity().javaClass)
                    }else{
                        jumpActivity(PaiLoginActivity().javaClass)
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
        list.add("派项目测试")
        return list
    }
}



