package com.getcard.hub.providers.example

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.getcard.hub.scopeprovider.pinpad.ScopeProvider
import com.getcard.hubinterface.OperationStatus
import com.getcard.hubinterface.authentication.AuthParams
import com.getcard.hubinterface.transaction.TransactionParams
import com.getcard.hubinterface.transaction.TransactionResponse
import kotlinx.coroutines.launch

/**
 * Activity responsável por iniciar uma transação de fato utilizando
 * o Provider de pagamentos.
 * Elas recebe os parâmetros de autenticação e transação
 * e chama o Provider para iniciar a transação. Ao final, retorna
 * o resultado para a activity que à chamou.
 */
class StartTransactionActivity : ComponentActivity() {

    companion object {
        const val TAG = "StartTransactionActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val authParams = intent.getParcelableExtra<AuthParams>("AUTH_PARAMS")
        if (authParams == null) {
            setResultAndFinish(
                TransactionResponse(
                    status = OperationStatus.FAILED,
                    message = "Nenhum parâmetro de autenticação encontrado",
                    transactionTimestamp = System.currentTimeMillis()
                )
            )
            return
        }

        Log.d(TAG, "AuthParams: $authParams")

        val paymentParams = intent.getParcelableExtra<TransactionParams>("TRANSACTION_PARAMS")
        if (paymentParams == null) {
            setResultAndFinish(
                TransactionResponse(
                    status = OperationStatus.FAILED,
                    message = "Nenhum parâmetro de transação encontrado",
                    transactionTimestamp = System.currentTimeMillis()
                )
            )
            return
        }

        Log.d(TAG, "PaymentParams: $paymentParams")
        val provider = ScopeProvider(Settings.PROVIDER_CONFIG)

        lifecycleScope.launch {
            val result = try {
                provider.startTransaction(
                    this@StartTransactionActivity,
                    paymentParams,
                    authParams
                )
            } catch (e: Exception) {
                Log.e("PaymentActivity", "Erro ao iniciar transação", e)
                TransactionResponse(
                    status = OperationStatus.FAILED,
                    message = "Erro ao iniciar transação: ${e.message}",
                    transactionTimestamp = System.currentTimeMillis()
                )
            }
            setResultAndFinish(result)
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