package com.getcard.hub.getcardpay.example.ui.payment.destination

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.getcard.hub.getcardpay.example.ui.payment.components.TopBar
import com.getcard.hub.getcardpay.example.ui.theme.GetcardPayExampleTheme

@Composable
private fun InstallmentNumberButton(
    modifier: Modifier,
    onChooseInstallmentNumber: (Int) -> Unit,
    installmentNumber: Int,
    onFinish: () -> Unit,
) {
    TextButton(
        modifier = modifier.fillMaxSize(),
        onClick = {
            onChooseInstallmentNumber(installmentNumber)
            onFinish()
        },
        colors = ButtonDefaults.textButtonColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(installmentNumber.toString())
    }
}

@Composable
fun PaymentDestination.ChooseInstallmentNumberPaymentDestination.Composable(
    onChooseIntallmentNumber: (Int) -> Unit,
    onBackPressed: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                onIconClick = {
                    onBackPressed()
                },
                label = "Transação"
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Column(Modifier.fillMaxSize()) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .wrapContentHeight(Alignment.CenterVertically),
                    text = "Quantidade de Parcelas",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                )
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(9.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    (2..13).chunked(6).forEach { chunk ->
                        Row(
                            Modifier.height(60.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            chunk.forEach { i ->
                                InstallmentNumberButton(
                                    modifier = Modifier.weight(1f),
                                    onFinish = {},
                                    installmentNumber = i,
                                    onChooseInstallmentNumber = onChooseIntallmentNumber
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight"
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Composable
private fun ChooseInstallmentNumberDestinationPreview() {
    GetcardPayExampleTheme(dynamicColor = false) {
        Surface {
            PaymentDestination.ChooseInstallmentNumberPaymentDestination.Composable(
                onChooseIntallmentNumber = {},
                onBackPressed = {}
            )
        }
    }
}
