package com.getcard.hub.providers.example.ui.payment.destination

import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.getcard.hub.providers.example.ui.payment.PaymentState
import com.getcard.hub.providers.example.ui.payment.TransactionViewModel

@Composable
fun PaymentNavigation(viewModel: TransactionViewModel) {
    val navController = rememberNavController()
    val paymentState by viewModel.paymentState.collectAsState()

    LaunchedEffect(paymentState) {
        when (paymentState) {
            is PaymentState.ChoosingPaymentType ->
                navController.navigate(PaymentDestination.ChoosePaymentTypePaymentDestination.route)

            is PaymentState.ChoosingInstallmentType ->
                navController.navigate(PaymentDestination.ChooseInstallmentTypePaymentDestination.route)

            is PaymentState.ChoosingInstallmentNumber ->
                navController.navigate(PaymentDestination.ChooseInstallmentNumberPaymentDestination.route)

            else -> {}
        }
    }


    NavHost(
        navController = navController,
        startDestination = PaymentDestination.ChoosePaymentAmountPaymentDestination.route
    ) {

        composable(route = PaymentDestination.ChoosePaymentAmountPaymentDestination.route) {
            val activity = LocalActivity.current
            val amount by viewModel.amount.collectAsState()
            PaymentDestination.ChoosePaymentAmountPaymentDestination.Composable(
                onChoosePaymentAmount = {
                    viewModel.setAmount(it.toInt())
                },
                amount.toString(),
                onConfirm = {
                    viewModel.onAmountConfirmed()
                },
                onBackPressed = {
                    activity?.finish()
                }
            )
        }
        composable(route = PaymentDestination.ChoosePaymentTypePaymentDestination.route) {
            PaymentDestination.ChoosePaymentTypePaymentDestination.Composable(
                onChoosePaymentType = {
                    viewModel.onPaymentTypeSelected(it)
                },
                onBackPressed = {
                    viewModel.onBackPressed()
                }
            )
        }
        composable(route = PaymentDestination.ChooseInstallmentTypePaymentDestination.route) {
            PaymentDestination.ChooseInstallmentTypePaymentDestination.Composable(
                onChooseIntallmentType = {
                    viewModel.onInstallmentTypeSelected(it)
                },
                onBackPressed = {
                    viewModel.onBackPressed()
                }
            )
        }
        composable(route = PaymentDestination.ChooseInstallmentNumberPaymentDestination.route) {
            PaymentDestination.ChooseInstallmentNumberPaymentDestination.Composable(
                onChooseIntallmentNumber = {
                    viewModel.onInstallmentNumberSelected(it)
                },
                onBackPressed = {
                    viewModel.onBackPressed()
                }
            )
        }
    }
}