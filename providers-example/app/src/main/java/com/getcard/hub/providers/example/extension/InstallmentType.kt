package com.getcard.hub.providers.example.extension

import com.getcard.hubinterface.transaction.InstallmentType

fun InstallmentType.toText(): String {
    return when (this) {
        InstallmentType.ONE_TIME -> "À Vista"
        InstallmentType.INSTALLMENTS -> "Parcelado"
        InstallmentType.INSTALLMENT_BUYER -> "Parcelado Comprador"
        InstallmentType.INSTALLMENT_SELLER -> "Parcelado Vendedor"
    }
}
