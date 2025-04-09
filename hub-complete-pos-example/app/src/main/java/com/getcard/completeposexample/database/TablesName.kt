package com.getcard.completeposexample.database

import com.getcard.completeposexample.PaymentProviderType

object TablesName {
    const val TRANSACTIONS = "transactions"

    object Settings {
        const val MAIN = "settings"
        const val SITEF = "sitef_settings"

        fun getTableName(paymentProviderType: PaymentProviderType): String {
            return when (paymentProviderType) {
                PaymentProviderType.SITEF -> SITEF
                else -> MAIN
            }
        }
    }
}