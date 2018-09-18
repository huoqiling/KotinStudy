package com.first.kotlin.activity

import android.support.v7.widget.GridLayoutManager
import com.first.kotlin.R
import com.first.kotlin.adapter.GridAdapter
import com.first.kotlin.base.BaseActivity
import com.first.kotlin.bean.GridInfo
import com.first.kotlin.util.GridLayoutItemDecoration
import com.first.kotlin.view.CustomTitleBar
import kotlinx.android.synthetic.main.activity_grid_view.*

class GridViewActivity : BaseActivity(),CustomTitleBar.TitleBarListener{


    override fun getLayoutResId(): Int {
        return  R.layout.activity_grid_view
    }

    override fun initView() {
        titleBar.setTitleBarListener(this)
        initAdapter()
    }

    private fun initAdapter() {
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.addItemDecoration(GridLayoutItemDecoration())
        recyclerView.adapter = GridAdapter(this, initData()!!)
    }

    private fun initData(): ArrayList<GridInfo>? {
        val animList = ArrayList<GridInfo>()
        val gridInfo1 = GridInfo("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1757162974,3924613766&fm=26&gp=0.jpg", "狗")
        val gridInfo2 = GridInfo("http://img1.imgtn.bdimg.com/it/u=1382579206,728449647&fm=26&gp=0.jpg", "小松鼠")
        val gridInfo3 = GridInfo("http://img1.imgtn.bdimg.com/it/u=575945376,2660867257&fm=200&gp=0.jpg", "老虎")
        val gridInfo4 = GridInfo("http://img5.imgtn.bdimg.com/it/u=904362672,1767969749&fm=26&gp=0.jpg", "花蝴蝶")
        animList.add(gridInfo1)
        animList.add(gridInfo2)
        animList.add(gridInfo3)
        animList.add(gridInfo4)
        return animList
    }

    override fun onLeftClick() {
        finish()
    }

    override fun onRightClick() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
