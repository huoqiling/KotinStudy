package com.first.kotlin.bean

/**
 * @author: zhangxin
 * @time: 2018/9/14 17:25
 * @description: 全局参数信息
 */
class GlobalParamInfo : BaseEntity() {

    var data: DataBean? = null

    class DataBean {

        var usd_paymethod: USDPayMethod? = null
        var country: CountryBean? = null
        var system: SystemBean? = null
        var paymethod: PaymentBean? = null
        var phonecode: PhoneCodeBean? = null
        var currency: CurrencyBean? = null
        var cny_paymethod: CNYPayMethod? = null
        var language: LanguageBean? = null

        class USDPayMethod {
            var en: List<EnBean>? = null
            var cn: List<CnBean>? = null

            class EnBean {
                var name: String? = null
                var id: String? = null
            }

            class CnBean {
                var name: String? = null
                var id: String? = null
            }
        }

        class CountryBean {
            var lastVersion: String? = null
            var en: List<EnBean>? = null
            var cn: List<CnBean>? = null

            class EnBean {
                var code: String? = null
                var value: String? = null
            }

            class CnBean {
                var code: String? = null
                var value: String? = null
            }
        }

        class SystemBean {
            var lastVersion: String? = null
            var p2p_exchange_secured_rate: String? = null
            var p2p_exchange_fee_rate: String? = null
        }

        class PaymentBean {

            var en: List<EnBean>? = null
            var cn: List<CnBean>? = null

            class EnBean {
                var name: String? = null
                var id: String? = null
            }

            class CnBean {
                var name: String? = null
                var id: String? = null
            }
        }

        class PhoneCodeBean {
            var lastVersion: String? = null
            var en: List<EnBean>? = null
            var cn: List<CnBean>? = null

            class EnBean {
                var code: String? = null
                var value: String? = null
            }

            class CnBean {
                var code: String? = null
                var value: String? = null
            }
        }

        class CurrencyBean {
            var lastVersion: String? = null
            var en: List<EnBean>? = null
            var cn: List<CnBean>? = null

        }
    }

    class EnBean {
        var showName: String? = null
        var gatewayName: String? = null
        var currency: String? = null
        var issuer: String? = null
    }

    class CnBean {
        var showName: String? = null
        var gatewayName: String? = null
        var currency: String? = null
        var issuer: String? = null
    }

    class CNYPayMethod {
        var en: List<EnBean>? = null
        var cn: List<CnBean>? = null

        class EnBean {
            var name: String? = null
            var id: String? = null
        }

        class CnBean {
            var name: String? = null
            var id: String? = null
        }
    }

    class LanguageBean {
        var en: List<EnBean>? = null
        var cn: List<CnBean>? = null


        class EnBean {
            var code: String? = null
            var value: String? = null
        }

        class CnBean {
            var code: String? = null
            var value: String? = null
        }
    }
}
