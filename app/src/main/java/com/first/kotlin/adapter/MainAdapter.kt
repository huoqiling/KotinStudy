package com.first.kotlin.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.first.kotlin.R

/**
 * 首页适配器
 */
class MainAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_main) {

    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper!!.setText(R.id.tvTitle, item!!)
    }
}