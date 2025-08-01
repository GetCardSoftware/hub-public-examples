package com.getcard.hub.getcardpay.example.data

enum class AvailableInstallmentType {
    ONE_TIME,
    INSTALLMENT_BUYER,
    INSTALLMENT_SELLER
}

fun AvailableInstallmentType.toText(): String {
    return when (this) {
        AvailableInstallmentType.ONE_TIME -> "À Vista"
        AvailableInstallmentType.INSTALLMENT_BUYER -> "Parcelado Comprador"
        AvailableInstallmentType.INSTALLMENT_SELLER -> "Parcelado Vendedor"
    }
}