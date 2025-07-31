package com.getcard.hub.providers.example.ui.payment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.getcard.hub.providers.example.ui.payment.destination.PaymentNavigation
import com.getcard.hubinterface.transaction.TransactionResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class PaymentActivity : ComponentActivity() {
    companion object {
        private const val TAG = "PaymentActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = TransactionViewModel()
        viewModel.setActivity(this@PaymentActivity)
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

