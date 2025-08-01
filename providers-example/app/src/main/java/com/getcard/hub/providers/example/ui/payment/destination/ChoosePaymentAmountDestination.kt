package com.getcard.hub.providers.example.ui.payment.destination

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.getcard.hub.providers.example.R
import com.getcard.hub.providers.example.applyMoneyMask
import com.getcard.hub.providers.example.ui.payment.components.TopBar
import com.getcard.hub.providers.example.ui.theme.ProvidersExampleTheme

@Composable
private fun AmountText(amount: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = applyMoneyMask(amount),
            textAlign = TextAlign.Center,
            fontSize = 40.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

private fun concatAmountState(amount: String, text: String, onAmountChange: (String) -> Unit) {
    if (amount.length < 9)
        onAmountChange(amount + text)
}

@Composable
private fun KeyboardNumber(
    text: String,
    modifier: Modifier = Modifier,
    amount: String,
    onAmountChange: (String) -> Unit
) {
    TextButton(
        modifier = modifier
            .fillMaxWidth()
            .height(130.dp),
        colors = ButtonDefaults.textButtonColors(
            containerColor = Color.Transparent, contentColor = MaterialTheme.colorScheme.onSurface
        ), onClick = { concatAmountState(amount, text, onAmountChange) }
    ) {
        Text(text, fontSize = 20.sp)
    }
}


@Composable
private fun IconButton(
    description: String,
    modifier: Modifier = Modifier,
    painter: Painter,
    onClick: () -> Unit = {}
) {
    TextButton(
        modifier = modifier
            .fillMaxWidth()
            .height(130.dp),
        colors = ButtonDefaults.textButtonColors(
            containerColor = Color.Transparent, contentColor = MaterialTheme.colorScheme.tertiary
        ), onClick = onClick
    ) {
        Icon(
            painter = painter,
            contentDescription = description,
        )
    }
}


@Composable
fun PaymentDestination.ChoosePaymentAmountPaymentDestination.Composable(
    onChoosePaymentAmount: (String) -> Unit,
    paymentAmount: String,
    onConfirm: () -> Unit,
    onBackPressed: () -> Unit
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
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val onValueChange = { newValue: String ->
                onChoosePaymentAmount(
                    newValue.ifBlank {
                        "0"
                    }
                )
            }
            AmountText(
                paymentAmount,
                Modifier
                    .weight(1.5f)
                    .fillMaxSize()
            )
            Row(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxSize()
            ) {
                KeyboardNumber("1", Modifier.weight(1 / 3f), paymentAmount, onValueChange)
                KeyboardNumber("2", Modifier.weight(1 / 3f), paymentAmount, onValueChange)
                KeyboardNumber("3", Modifier.weight(1 / 3f), paymentAmount, onValueChange)
            }
            Row(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxSize()
            ) {
                KeyboardNumber("4", Modifier.weight(1 / 3f), paymentAmount, onValueChange)
                KeyboardNumber("5", Modifier.weight(1 / 3f), paymentAmount, onValueChange)
                KeyboardNumber("6", Modifier.weight(1 / 3f), paymentAmount, onValueChange)
            }
            Row(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxSize()
            ) {
                KeyboardNumber("7", Modifier.weight(1 / 3f), paymentAmount, onValueChange)
                KeyboardNumber("8", Modifier.weight(1 / 3f), paymentAmount, onValueChange)
                KeyboardNumber("9", Modifier.weight(1 / 3f), paymentAmount, onValueChange)
            }
            Row(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxSize()
            ) {
                IconButton(
                    "Apagar",
                    Modifier.weight(1 / 3f),
                    painterResource(R.drawable.long_arrow_back_50)
                ) { onValueChange(paymentAmount.dropLast(1)) }
                KeyboardNumber("0", Modifier.weight(1 / 3f), paymentAmount, onValueChange)
                IconButton(
                    description = "Confirmar",
                    modifier = Modifier.weight(1 / 3f),
                    painterResource(R.drawable.rounded_check_circle_50)
                ) {
                    onConfirm()
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
private fun ChoosePaymentAmountDestinationPreview() {
    ProvidersExampleTheme(dynamicColor = false) {
        Surface {
            PaymentDestination.ChoosePaymentAmountPaymentDestination.Composable(
                onChoosePaymentAmount = {},
                paymentAmount = "0",
                onConfirm = {},
                onBackPressed = {}
            )
        }
    }
}
