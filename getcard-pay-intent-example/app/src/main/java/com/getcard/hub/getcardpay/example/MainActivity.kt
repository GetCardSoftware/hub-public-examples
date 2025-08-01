package com.getcard.hub.getcardpay.example

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.getcard.hub.getcardpay.example.ui.payment.PaymentActivity
import com.getcard.hub.getcardpay.example.ui.theme.GetcardPayExampleTheme
import com.getcard.hubinterface.OperationStatus
import com.getcard.hubinterface.transaction.TransactionResponse

class MainActivity : ComponentActivity() {

    private var lastTransactionId: String? = null
    private var lastTransactionWasSuccessful: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                val transactionResponse =
                    result.data?.getParcelableExtra<TransactionResponse>("TRANSACTION_RESULT")
                val transactionId =
                    result.data?.getStringExtra("TRANSACTION_ID")


                Log.d(
                    "MainActivity",
                    "TransactionResponse: $transactionResponse, TransactionId: $transactionId"
                )
                if (transactionResponse != null) {
                    lastTransactionWasSuccessful =
                        transactionResponse.status == OperationStatus.SUCCESS
                }
                if (transactionId != null) {
                    lastTransactionId = transactionId
                }
            }

        setContent {
            GetcardPayExampleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                    ) {
                        Column(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(horizontal = 32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Exemplo de Integração",
                                style = MaterialTheme.typography.headlineMedium,
                                modifier = Modifier.padding(bottom = 32.dp)
                            )

                            Button(
                                onClick = {
                                    val intent =
                                        Intent(this@MainActivity, PaymentActivity::class.java)
                                    launcher.launch(intent)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = null
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Iniciar Pagamento")
                            }

                            Button(
                                onClick = {
                                    if (lastTransactionId != null && lastTransactionWasSuccessful
                                    ) {
                                        val intent =
                                            Intent(this@MainActivity, PaymentActivity::class.java)
                                        intent.putExtra(
                                            "TRANSACTION_ID",
                                            lastTransactionId
                                        )
                                        launcher.launch(intent)
                                    } else {
                                        Toast.makeText(
                                            this@MainActivity,
                                            "Nenhuma transação para estornar",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = null
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Estornar Última Transação")
                            }
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(bottom = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Bottom
                        ) {

                            Text(
                                text = "Versão 1.0.0",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}