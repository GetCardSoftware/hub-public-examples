package com.getcard.hub.getcardpay.example

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.getcard.hub.getcardpay.example.data.AvailableOperationStatus
import com.getcard.hub.getcardpay.example.ui.icons.Adf_scanner
import com.getcard.hub.getcardpay.example.ui.payment.PaymentActivity
import com.getcard.hub.getcardpay.example.ui.theme.GetcardPayExampleTheme
import java.io.File
import java.io.FileOutputStream

class MainActivity : ComponentActivity() {

    private var lastTransactionId: String? = null
    private var lastTransactionWasSuccessful: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.data == null) {
                    return@registerForActivityResult
                }

                val status = result.data?.getStringExtra("TRANSACTION_STATUS")
                val message = result.data?.getStringExtra("TRANSACTION_MESSAGE")
                val transactionTimestamp =
                    result.data?.getLongExtra("TRANSACTION_TIMESTAMP", 0)
                val transactionId =
                    result.data?.getStringExtra("TRANSACTION_ID")

                if (status == null) {
                    Toast.makeText(this, "Ocorreu um erro na transação.", Toast.LENGTH_SHORT).show()
                    return@registerForActivityResult
                }

                Log.d(
                    "MainActivity",
                    "Transaction Finished: Status = $status, " +
                            "Message = $message, " +
                            "Timestamp = $transactionTimestamp, " +
                            "TransactionId: $transactionId"
                )
                lastTransactionWasSuccessful =
                    status == AvailableOperationStatus.SUCCESS.toString()
                if (transactionId != null) {
                    lastTransactionId = transactionId
                }
            }

        val printLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                val response = result.data
                if (response != null) {
                    response.getStringExtra("RESULT_OPERATION_STATUS_EXTRA").also {
                        "Status da impressão: $it".run {
                            Toast.makeText(this@MainActivity, this, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                    response.getStringExtra("RESULT_MESSAGE_EXTRA").also {
                        "Mensagem da impressão: $it".run {
                            Toast.makeText(this@MainActivity, this, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
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
                            StartPrintButton(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                launch = { printLauncher.launch(it) })
                            StartPrintImageButton(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                launch = { printLauncher.launch(it) })
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


    @Composable
    fun StartPrintButton(
        modifier: Modifier = Modifier,
        launch: (Intent) -> Unit
    ) {
        val context = LocalContext.current

        Button(
            modifier = modifier,
            onClick = {
                val printIntent = Intent()
                printIntent.setClassName(
                    "com.getcard.hub.getcardpayapp.homolog",
                    "com.getcard.hub.getcardpayapp.ui.PrintActivity"
                )
                printIntent.putExtra("RECEIPT", Mocks.generateSitefReceipt())

                if (printIntent.resolveActivity(context.packageManager) != null) {
                    Log.d("MainActivity", "Iniciando GetCardPay")
                    launch(printIntent)
                } else {
                    Log.e("MainActivity", "Não existe GetCardPay nesse dispositivo")
                    Toast.makeText(context, "App não instalado", Toast.LENGTH_SHORT).show()
                }
            }) {
            Icon(
                imageVector = Adf_scanner,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Iniciar Impressão De Texto GetCard Pay")
        }
    }

    @Composable
    fun StartPrintImageButton(
        modifier: Modifier = Modifier,
        launch: (Intent) -> Unit
    ) {
        val context = LocalContext.current

        fun getImageUri(context: Context, bitmap: Bitmap): Uri {
            val imagesFolder = File(context.cacheDir, "images")
            imagesFolder.mkdirs()

            val file = File(imagesFolder, "shared_image.png")
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            }

            return FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
        }

        Button(
            modifier = modifier,
            onClick = {
                val uri = getImageUri(context, Mocks.generateReceiptBitmap())
                Log.d(
                    "MainActivity",
                    "Image URI: $uri"
                )
                val printIntent = Intent().apply {
                    setClassName(
                        "com.getcard.hub.getcardpayapp.homolog",
                        "com.getcard.hub.getcardpayapp.ui.PrintActivity"
                    )
                    putExtra("RECEIPT_IMAGE_URI", uri)
                    flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                    clipData = ClipData.newUri(context.contentResolver, "RECEIPT_IMAGE_URI", uri)
                }
                if (printIntent.resolveActivity(context.packageManager) != null) {
                    Log.d("MainActivity", "Iniciando GetCardPay")
                    launch(printIntent)
                } else {
                    Log.e("MainActivity", "Não existe GetCardPay nesse dispositivo")
                    Toast.makeText(context, "App não instalado", Toast.LENGTH_SHORT).show()
                }
            }) {
            Icon(
                imageVector = Adf_scanner,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Iniciar Impressão De Imagem GetCard Pay")
        }
    }


}