package com.getcard.hub.getcardpay.example.extension

import com.getcard.hubinterface.transaction.InstallmentType

/**
 * Extensão para o enum InstallmentType, que retorna o texto correspondente
 * ao tipo de parcelamento.
 */
fun InstallmentType.toText(): String {
    return when (this) {
        InstallmentType.ONE_TIME -> "À Vista"
        InstallmentType.INSTALLMENTS -> "Parcelado"
        InstallmentType.INSTALLMENT_BUYER -> "Parcelado Comprador"
        InstallmentType.INSTALLMENT_SELLER -> "Parcelado Vendedor"
    }
}
