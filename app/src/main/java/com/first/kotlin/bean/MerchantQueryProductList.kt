package com.first.kotlin.bean

import java.io.Serializable

/**
 * 商家查询产品列表
 */
class MerchantQueryProductList : BaseEntity() {

    var data: DataBean? = null

    class DataBean : Serializable {

        var totalCount: String? = null
        var totalPage: String? = null
        var infiniteSaleProductList: List<InfiniteSaleProductListBean>? = null
    }

    class InfiniteSaleProductListBean : Serializable {

        var addPriceRate: String? = null
        var coverImg: String? = null
        var createAt: Long = 0
        var id: String? = null
        var merchantAddress: String? = null
        var merchantLogoImg: String? = null
        var merchantName: String? = null
        var mineSalePrice: String? = null
        var nowBidPrice: String? = null
        var productContent: String? = null
        var productName: String? = null
        var saleNum: String? = null
        var startSaleDate: Long = 0
        var status: Int = 0 //状态 1待审核 2审核通过,待竞价 3审核未通过 4竞价结束 5取消竞价 6竞价中 7竞价失败 8已删除
        var updateAt: Long = 0
        var contentImg: String? = null
    }
}
