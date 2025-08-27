package com.getcard.hub.providers.example.ui.payment.destination

sealed class PaymentDestination(val route: String) {
    object ChoosePaymentAmountPaymentDestination :
        PaymentDestination("choose_payment_amount_destination")

    object ChoosePaymentTypePaymentDestination :
        PaymentDestination("choose_payment_type_destination")

    object ChooseInstallmentTypePaymentDestination :
        PaymentDestination("choose_installment_type_destination")

    object ChooseInstallmentNumberPaymentDestination :
        PaymentDestination("choose_installment_number_destination")
}