package com.first.kotlin.base

import android.content.Context
import android.util.AttributeSet
import com.first.kotlin.R
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout

/**
 * @author zhangxin
 * @date 2018/9/14
 * @description 刷新控件
 */
class BaseRefreshLayout : TwinklingRefreshLayout {

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val progressLayout = ProgressLayout(context)
        progressLayout.setProgressBackgroundColorSchemeResource(R.color.yellow)
        progressLayout.setColorSchemeColors(R.color.white)
        setHeaderView(progressLayout)
        isFocusableInTouchMode = true
        isFocusable = true
        setFloatRefresh(true)
    }


}