package com.getcard.hub.providers.example.ui.payment

import com.getcard.hubinterface.transaction.TransactionResponse

/*
 * Classe auxiliar para rastreamento do estado da operação de pagamento
 */
sealed class PaymentState {
    data object ChoosingPaymentAmount : PaymentState()
    data object ChoosingPaymentType : PaymentState()
    data object ChoosingInstallmentType : PaymentState()
    data object ChoosingInstallmentNumber : PaymentState()
    data object ProcessingPayment : PaymentState()
    data class Finished(val transactionResponse: TransactionResponse) : PaymentState()
}