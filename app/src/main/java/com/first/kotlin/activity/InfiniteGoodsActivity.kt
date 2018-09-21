package com.first.kotlin.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import butterknife.OnClick
import com.first.kotlin.R
import com.first.kotlin.base.BaseActivity
import com.first.kotlin.fragment.InfiniteGoodFragment
import com.first.kotlin.view.CustomTitleBar
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import kotlinx.android.synthetic.main.activity_infinite_goods.*

/**
 * @author zhangxin
 * @date 2018/9/12
 * @description 无限竞派商品
 */
class InfiniteGoodsActivity : BaseActivity(), CustomTitleBar.TitleBarListener {


    private var titleList: Array<String> = arrayOf("竞拍中", "未开拍", "已结束")
    private val mTabEntities = ArrayList<CustomTabEntity>()

    override fun getLayoutResId(): Int {
        return R.layout.activity_infinite_goods
    }

    override fun initView() {
        titleBar.setTitleBarListener(this)
        initTabLayout()
        initViewPager()
    }

    private fun initTabLayout() {

        for (i in 0..(titleList.size - 1)) {
            mTabEntities.add(object : CustomTabEntity {
                override fun getTabUnselectedIcon(): Int {
                    return 0
                }

                override fun getTabSelectedIcon(): Int {
                    return 0
                }

                override fun getTabTitle(): String {
                    return titleList[i]
                }

            })
        }

        tabLayout.setOnTabSelectListener(object : OnTabSelectListener {

            override fun onTabSelect(position: Int) {
                viewPager.setCurrentItem(position, false)
            }

            override fun onTabReselect(position: Int) {

            }

        })

        tabLayout.setTabData(mTabEntities)
    }

    private fun initViewPager() {
        val pagerAdapter = MyViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter
        viewPager.offscreenPageLimit = pagerAdapter.count
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                tabLayout.currentTab = position
            }

        })
    }





    @OnClick(R.id.btnAddInfinite)
    fun onclick(view: View) {
        when (view.id) {
            R.id.btnAddInfinite -> {
                jumpActivity(AddInfiniteProductActivity().javaClass)
            }
        }
    }

    override fun onLeftClick() {
        finish()
    }

    override fun onRightClick() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class MyViewPagerAdapter : FragmentPagerAdapter {

        constructor(fm: FragmentManager?) : super(fm)

        override fun getItem(position: Int): Fragment {
            val fragment = InfiniteGoodFragment()
            val bundle = Bundle()
            bundle.putInt("queryType", position + 1)
            fragment.arguments = bundle
            return fragment
        }

        override fun getCount(): Int {
            return 3
        }

    }
}
