package com.getcard.hub.getcardpay.example.data

enum class AvailablePaymentType {
    CREDIT,
    DEBIT,
    PIX
}

fun AvailablePaymentType.toText(): String {
    return when (this) {
        AvailablePaymentType.CREDIT -> "Crédito"
        AvailablePaymentType.DEBIT -> "Débito"
        AvailablePaymentType.PIX -> "PIX"
    }
}