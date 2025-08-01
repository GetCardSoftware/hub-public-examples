package com.getcard.hub.getcardpay.example.ui.payment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.getcard.hub.getcardpay.example.ui.payment.destination.PaymentNavigation
import com.getcard.hubinterface.transaction.TransactionResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * Activity responsável por interagir com o usuário e coletar os dados
 * necessários para iniciar uma transação.
 * Ela também verifica o estado da transação e envia o resultado para a activity principal
 * [com.getcard.hub.getcardpay.example.MainActivity]
 */
class PaymentActivity : ComponentActivity() {
    companion object {
        private const val TAG = "PaymentActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = TransactionViewModel()
        viewModel.setActivity(this@PaymentActivity)
        /**
         * Verifica o estado da transação e caso esteja finalizada, envia o resultado
         * de volta para a MainActivity.
         */
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.paymentState.filter { it is PaymentState.Finished }.first().run {
                Log.d(
                    TAG,
                    "onCreate PaymentActivity, transactionResponse: ${(this as PaymentState.Finished).transactionResponse}"
                )
                setResultAndFinish(
                    this.transactionResponse,
                )
            }
        }
        setContent {
            PaymentNavigation(viewModel)
        }
    }

    /**
     * Função auxiliar responsável apenas por definir o resultado da activity e
     * finaliza-la.
     */
    private fun setResultAndFinish(
        transactionResponse: TransactionResponse,
    ) {
        Intent().apply {
            putExtra("TRANSACTION_RESULT", transactionResponse)
        }.run {
            setResult(RESULT_OK, this)
        }
        finish()
    }
}

