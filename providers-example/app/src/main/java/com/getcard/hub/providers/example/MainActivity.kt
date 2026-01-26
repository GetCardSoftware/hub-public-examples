package com.getcard.hub.providers.example

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.getcard.hub.providers.example.ui.payment.PaymentActivity
import com.getcard.hub.providers.example.ui.payment.components.CustomDropdownMenu
import com.getcard.hub.providers.example.ui.theme.ProvidersExampleTheme
import com.getcard.hubinterface.OperationStatus
import com.getcard.hubinterface.authentication.AuthParams
import com.getcard.hubinterface.transaction.PaymentType
import com.getcard.hubinterface.transaction.TransactionParams
import com.getcard.hubinterface.transaction.TransactionResponse
import java.math.BigDecimal

class MainActivity : ComponentActivity() {

    private var lastTransactionRefundCode: String? = null
    private var lastTransactionPaymentType: PaymentType? = null
    private var lastTransactionAmount: BigDecimal? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        Preferences.init(this.applicationContext)

        val launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                val response =
                    result.data?.getParcelableExtra<TransactionResponse>("TRANSACTION_RESULT")
                Log.d("MainActivity", "Response: $response")
                if (response != null) {
                    Toast.makeText(this, response.message, Toast.LENGTH_LONG).show()
                    if (response.status == OperationStatus.SUCCESS) {
                        lastTransactionRefundCode = response.refundCode
                        lastTransactionAmount = response.transactionAmount
                        lastTransactionPaymentType = response.paymentType
                        return@registerForActivityResult
                    }
                }
            }

        setContent {

            ProvidersExampleTheme {
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
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Exemplo de Integração",
                                style = MaterialTheme.typography.headlineMedium,
                                modifier = Modifier.padding(bottom = 24.dp)
                            )

                            Button(
                                onClick = {
                                    val intent =
                                        Intent(this@MainActivity, PaymentActivity::class.java)
                                    launcher.launch(intent)
                                },
                                modifier = Modifier.fillMaxWidth()
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
                                    if (lastTransactionRefundCode != null
                                        && lastTransactionAmount != null
                                        && lastTransactionPaymentType != null
                                    ) {
                                        val intent = Intent(
                                            this@MainActivity,
                                            StartTransactionActivity::class.java
                                        )
                                        intent.putExtra(
                                            "AUTH_PARAMS",
                                            AuthParams(Settings.AUTH_TOKEN)
                                        )
                                        intent.putExtra(
                                            "TRANSACTION_PARAMS", TransactionParams(
                                                amount = lastTransactionAmount!!,
                                                paymentType = lastTransactionPaymentType!!,
                                                refundCode = lastTransactionRefundCode,
                                                refund = true
                                            )
                                        )
                                        startActivity(intent)
                                    } else {
                                        Toast.makeText(
                                            this@MainActivity,
                                            "Nenhuma transação para estornar",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = null
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Estornar Última Transação")
                            }

                            // Bluetooth
                            var hasBluetoothPermission by remember { mutableStateOf(false) }

                            var btPairedDevices by remember {
                                mutableStateOf<List<BluetoothHelper.BtDeviceInfo?>>(
                                    listOf(null)
                                )
                            }

                            var selectedBtDevice by remember {
                                mutableStateOf<BluetoothHelper.BtDeviceInfo?>(null)
                            }

                            val permissionLauncher = rememberLauncherForActivityResult(
                                contract = ActivityResultContracts.RequestMultiplePermissions()
                            ) { permissions ->
                                val allGranted = permissions.values.all { it }
                                hasBluetoothPermission = allGranted
                            }

                            LaunchedEffect(Unit) {
                                permissionLauncher.launch(BluetoothHelper.bluetoothPermissions)
                            }

                            LaunchedEffect(hasBluetoothPermission) {
                                if (hasBluetoothPermission) {
                                    btPairedDevices = BluetoothHelper.getPairedDevices(this@MainActivity).plus(null)
                                    selectedBtDevice = btPairedDevices.find { it?.address == Preferences.getBtDeviceAddress() }
                                }
                            }

                            CustomDropdownMenu(
                                "PIN Pad Bluetooth",
                                btPairedDevices,
                                selectedBtDevice,
                                { it?.name ?: "Não utiliza dispositivo Bluetooth" },
                                {
                                    selectedBtDevice = it
                                    Preferences.saveBtDeviceAddress(it?.address)
                                },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }


                        Text(
                            text = "Versão 1.0.0",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)
                                .align(Alignment.BottomCenter)
                        )
                    }
                }
            }
        }
    }
}