package com.getcard.hub.getcardpay.example.ui.payment

import com.getcard.hub.getcardpay.example.data.AvailableOperationStatus

/*
 * Classe auxiliar para rastreamento do estado da operação de pagamento
 */
sealed class PaymentState {
    data object ChoosingPaymentAmount : PaymentState()
    data object ChoosingPaymentType : PaymentState()
    data object ChoosingInstallmentType : PaymentState()
    data object ChoosingInstallmentNumber : PaymentState()
    data object ProcessingPayment : PaymentState()
    data class Finished(
        val status: AvailableOperationStatus,
        val message: String,
        val transactionTimestamp: Long,
        val transactionId: String? = null
    ) : PaymentState()
}