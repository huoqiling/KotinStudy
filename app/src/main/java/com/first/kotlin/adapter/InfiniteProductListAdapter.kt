package com.first.kotlin.adapter

import android.annotation.SuppressLint
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.first.kotlin.R
import com.first.kotlin.bean.MerchantQueryProductList
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author zhangxin
 * @date 2018/9/14
 * @description 商家查询产品列表适配器
 */
class InfiniteProductListAdapter : BaseQuickAdapter<MerchantQueryProductList.InfiniteSaleProductListBean, BaseViewHolder>(R.layout.item_infinit_product_list) {


    override fun convert(helper: BaseViewHolder?, itemData: MerchantQueryProductList.InfiniteSaleProductListBean?) {
        helper!!.setText(R.id.tvStartTime, getTime(itemData!!.createAt))
        helper.setText(R.id.tvOrderStatus, getOrderStatus(itemData.status))
        helper.setText(R.id.tvProductName, itemData.productName)
        helper.setText(R.id.tvCurrentPrice, itemData.nowBidPrice + " π")
        try {
            val coverImg = itemData.coverImg!!.split(",")
            Glide.with(mContext!!).load(getUrl(coverImg[0])).apply(RequestOptions().centerCrop().error(R.mipmap.ic_launcher)
                    .placeholder(R.mipmap.ic_launcher)).into(helper.getView(R.id.ivProductLogo))
        } catch (e: Exception) {
        }
    }

    private fun getUrl(imgeUrl: String): String {
        if (imgeUrl.startsWith("http://") || imgeUrl.startsWith("https://")) {
            return imgeUrl
        } else {
            return "http://ofydu65mj.bkt.clouddn.com/" + imgeUrl
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getTime(millis: Long): String {
        try {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm")
            val dt = Date(millis)
            return sdf.format(dt)
        } catch (e: Exception) {
        }
        return ""
    }

    //状态 1待审核 2审核通过,待竞价 3审核未通过 4竞价结束 5取消竞价 6竞价中 7竞价失败 8已删除
    private fun getOrderStatus(status: Int): String {
        var orderStatus = ""
        when (status) {
            1 -> orderStatus = "待审核"
            2 -> orderStatus = "审核通过,待竞价"
            3 -> orderStatus = "审核未通过"
            4 -> orderStatus = "竞价结束"
            5 -> orderStatus = "取消竞价"
            6 -> orderStatus = "竞价中"
            7 -> orderStatus = "竞价失败"
            8 -> orderStatus = "已删除"
        }
        return orderStatus
    }
}