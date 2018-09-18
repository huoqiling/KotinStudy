package com.first.kotlin.activity

import android.support.v7.widget.GridLayoutManager
import com.first.kotlin.R
import com.first.kotlin.adapter.KotlinMainAdapter
import com.first.kotlin.base.BaseActivity
import com.first.kotlin.bean.BaseEntity
import com.first.kotlin.bean.GlobalParamInfo
import com.first.kotlin.bean.GridInfo
import com.first.kotlin.bean.UserInfo
import com.first.kotlin.net.Requester
import com.first.kotlin.util.Constant
import com.first.kotlin.util.LogUtil
import com.first.kotlin.util.Preference
import com.first.kotlin.view.CommonDialog
import com.first.kotlin.view.CommonDialog.CommonDialogListener
import com.first.kotlin.view.CustomTitleBar
import kotlinx.android.synthetic.main.activity_kotlin_main.*
import retrofit2.Response

/**
 * @author zhangxin
 * @date 2018/9/12
 * @description kotlin项目首页
 */
class KotlinMainActivity : BaseActivity(), CustomTitleBar.TitleBarListener {

    private var isLogin by Preference(Constant.IS_LOGIN, false)
    private var token by Preference(Constant.TOKEN, "")
    private var userName by Preference(Constant.USER_NAME, "")
    private var needSaveCookie by Preference(Constant.NEED_SAVE_COOKIE, false)

    override fun getLayoutResId(): Int {
        return R.layout.activity_kotlin_main
    }

    override fun initView() {
        titleBar.setTitleBarListener(this)
        initAdapter()
        getGlobalParam()
        appLoginAgain()
        LogUtil().i("token=="+token)
    }


    private fun initAdapter() {
        recyclerView!!.layoutManager = GridLayoutManager(this, 3)
        val mainAdapter = KotlinMainAdapter()
        recyclerView.adapter = mainAdapter
        mainAdapter.setNewData(getData())
        mainAdapter.setOnItemClickListener { adapter, view, position ->
            when (position) {
                0 -> {
                    this.jumpActivity(InfiniteGoodsActivity().javaClass)
                }
            }
        }
    }

    private fun appLoginAgain() {
        needSaveCookie = true
        Requester().login(userName, "", token, object : Requester.RetrofitResponseListener<UserInfo> {
            override fun isSuccessful(response: Response<UserInfo>?) {
                try {
                    val userInfo = response!!.body()
                    if (userInfo!!.isSuccessful()) {

                    } else {
                        showToast(userInfo.msg + "")
                    }
                } catch (e: Exception) {
                }
            }

            override fun isFailed() {

            }

        })
    }


    private fun getGlobalParam() {
        Requester().getGlobalParam(object : Requester.RetrofitResponseListener<GlobalParamInfo> {
            override fun isSuccessful(response: Response<GlobalParamInfo>?) {

            }

            override fun isFailed() {

            }

        })
    }

    private fun getData(): ArrayList<GridInfo> {
        val gridInfoList = ArrayList<GridInfo>()
        gridInfoList.add(GridInfo("http://img1.imgtn.bdimg.com/it/u=575945376,2660867257&fm=200&gp=0.jpg", "无限竞派"))
        return gridInfoList
    }

    override fun onLeftClick() {
        finish()
    }

    override fun onRightClick() {
        val dialog = CommonDialog()
        dialog.setMessage("确认退出登录?")
                .setCommonDialogListener(listener)
                .show(supportFragmentManager, "dialog")

    }

    private val listener = object : CommonDialogListener {
        override fun onConfirm() {
            showLoadingDialog()
            Requester().loginOut(object : Requester.RetrofitResponseListener<BaseEntity> {
                override fun isSuccessful(response: Response<BaseEntity>?) {
                    try {
                        dismissDialog()
                        jumpActivity(LoginActivity().javaClass)
                        finish()
                        isLogin = false
                    } catch (e: Exception) {
                    }

                }

                override fun isFailed() {
                    dismissDialog()
                    LogUtil().i("请求失败")
                }

            })
        }

    }

}
