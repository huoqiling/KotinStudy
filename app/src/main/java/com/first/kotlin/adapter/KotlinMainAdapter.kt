package com.first.kotlin.adapter

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.first.kotlin.R
import com.first.kotlin.bean.GridInfo

/**
 * kotlin首页
 */
class KotlinMainAdapter : BaseQuickAdapter<GridInfo, BaseViewHolder>(R.layout.item_grid_view) {

    override fun convert(helper: BaseViewHolder?, item: GridInfo?) {
        helper!!.setText(R.id.tvExplain, item!!.explain)
        Glide.with(mContext!!).load(item.url).apply(RequestOptions().centerCrop().error(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher)).into(helper.getView(R.id.ivImage))
    }
}