package com.getcard.hub.providers.example.extension

import com.getcard.hubinterface.transaction.PaymentType

/**
 * Extensão para o enum InstallmentType, que retorna o texto correspondente
 * ao tipo de parcelamento.
 */
fun PaymentType.toText(): String {
    return when (this) {
        PaymentType.CREDIT -> "Crédito"
        PaymentType.DEBIT -> "Débito"
        PaymentType.VOUCHER -> "Voucher"
        PaymentType.PIX -> "PIX"
    }
}
