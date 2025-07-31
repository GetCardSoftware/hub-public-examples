package com.getcard.hub.providers.example

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.getcard.hub.scopeprovider.pinpad.ScopeProvider
import com.getcard.hubinterface.OperationStatus
import com.getcard.hubinterface.authentication.AuthParams
import com.getcard.hubinterface.transaction.TransactionParams
import com.getcard.hubinterface.transaction.TransactionResponse
import kotlinx.coroutines.launch

class StartTransactionActivity : ComponentActivity() {

    companion object {
        const val TAG = "StartTransactionActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val authParams = intent.getParcelableExtra<AuthParams>("AUTH_PARAMS")
        if (authParams == null) {
            Toast.makeText(this, "Nenhum parâmetro de autenticação encontrado", Toast.LENGTH_LONG)
                .show()
            finish()
            return
        }

        Log.d(TAG, "AuthParams: $authParams")

        val paymentParams = intent.getParcelableExtra<TransactionParams>("TRANSACTION_PARAMS")
        if (paymentParams == null) {
            Toast.makeText(this, "Nenhum parâmetro transação encontrado", Toast.LENGTH_LONG).show()
            finish()
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

            val responseIntent = Intent()
            responseIntent.putExtra("TRANSACTION_RESULT", result)
            setResult(RESULT_OK, responseIntent)
            finish()
        }
    }
}