package com.first.kotlin.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.first.kotlin.R
import kotlinx.android.synthetic.main.view_custom_titlebar.view.*

/**
 * @author zhangxin
 * @date 2018/9/10
 * @description 自定义标题
 */
class CustomTitleBar : FrameLayout {


    private var barListener: TitleBarListener? = null
    private var showLeftImage: Boolean = true
    private var title: String? = ""
    private var rightText: String? = ""
    private var rightTextColor: Int = Color.parseColor("#1a1a1a")
    private var rightImageRes: Int = R.mipmap.ic_launcher
    private var showRightImage: Boolean = false

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val a: TypedArray = context!!.obtainStyledAttributes(attrs, R.styleable.CustomTitleBar)
        showLeftImage = a.getBoolean(R.styleable.CustomTitleBar_showLeftImage, showLeftImage)
        title = a.getString(R.styleable.CustomTitleBar_titleText)
        rightText = a.getString(R.styleable.CustomTitleBar_rightText)
        rightTextColor = a.getColor(R.styleable.CustomTitleBar_rightTextColor, rightTextColor)
        showRightImage = a.getBoolean(R.styleable.CustomTitleBar_showRightImage, showRightImage)
        rightImageRes = a.getResourceId(R.styleable.CustomTitleBar_rightImage, rightImageRes)
        a.recycle()

        init(context)
    }

    fun setTitleBarListener(barListener: TitleBarListener?) {
        this.barListener = barListener
    }

    private fun init(context: Context?) {
        val view = LayoutInflater.from(context).inflate(R.layout.view_custom_titlebar, this)
        ButterKnife.bind(view)
        if (showLeftImage) {
            btnLeftImage.visibility = View.VISIBLE
        } else {
            btnLeftImage.visibility = View.INVISIBLE
        }

        if (!TextUtils.isEmpty(title)) {
            tvTitle.text = title
        }
        if (!TextUtils.isEmpty(rightText)) {
            tvRightText.text = rightText
        }
        if (showRightImage) {
            ivRightImage.visibility = View.VISIBLE
            tvRightText.visibility = View.GONE
        } else {
            ivRightImage.visibility = View.GONE
            tvRightText.visibility = View.VISIBLE
        }
        ivRightImage.setImageResource(rightImageRes)
    }

    @OnClick(R.id.btnLeftImage, R.id.flRight)
    fun onclick(view: View) {
        when (view.id) {
            R.id.btnLeftImage -> {
                barListener!!.onLeftClick()
            }
            R.id.flRight -> {
                barListener!!.onRightClick()
            }
        }
    }

    interface TitleBarListener {
        fun onLeftClick()
        fun onRightClick()
    }
}