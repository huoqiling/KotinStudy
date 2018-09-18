package com.first.kotlin.bean

import java.io.Serializable

/**
 * 登录信息
 */

class UserInfo : BaseEntity(), Serializable {
    val data: Data? = null
}


data class Data(
        val account: Account,
        val appLoginToken: String,
        val ipArea: Int,
        val merchantUrl: String,
        val netType: String,
        val user: User
)

data class Account(
        val address: String,
        val createAt: Long,
        val fundBalance: String,
        val fundBalanceEntrustFreeze: String,
        val id: String,
        val paiPurseBalance: String,
        val piBalance: String,
        val piBalanceEntrustFreeze: String,
        val piBalanceFreeze: String,
        val piBalancePackageFreeze: String,
        val piLock: String,
        val updateAt: Long,
        val usdBalance: String,
        val usdBalanceEntrustFreeze: String,
        val usdBalanceFreeze: String,
        val usdBalancePackageFreeze: String,
        val voucherBalance: String,
        val voucherBalanceEntrustFreeze: String,
        val voucherPiBalanceEntrustFreeze: String
)

data class User(
        val address: String,
        val coinCnyRateShow: Int,
        val contactWay: String,
        val email: String,
        val gender: Int,
        val iOS: Boolean,
        val id: String,
        val isboss: Int,
        val loginFlag: Int,
        val loginPassword: String,
        val mobile: String,
        val nickname: String,
        val openGoogleCode: Int,
        val openMobileCode: Int,
        val openTradePassword: Int,
        val phoheCode: String,
        val toatlUsdAmount: String,
        val totalInvPiAmount: String,
        val tradePassword: String,
        val userPriorityTotal: String,
        val username: String,
        val vipLevel: Int
)