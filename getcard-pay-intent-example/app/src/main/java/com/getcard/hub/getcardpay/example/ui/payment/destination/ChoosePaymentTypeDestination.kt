package com.getcard.hub.getcardpay.example.ui.payment.destination

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.getcard.hub.getcardpay.example.R
import com.getcard.hub.getcardpay.example.ui.payment.components.OptionButton
import com.getcard.hub.getcardpay.example.ui.payment.components.TopBar
import com.getcard.hub.getcardpay.example.ui.theme.GetcardPayExampleTheme
import com.getcard.hub.scopeprovider.pinpad.extension.toText
import com.getcard.hubinterface.transaction.PaymentType

@Composable
fun PaymentDestination.ChoosePaymentTypePaymentDestination.Composable(
    onChoosePaymentType: (PaymentType) -> Unit,
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
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .wrapContentHeight(Alignment.CenterVertically),
                text = "Forma de Pagamento",
                textAlign = TextAlign.Center,
                fontSize = 25.sp,
            )
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(9.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PaymentType.entries.forEach { it ->
                    OptionButton(
                        item = it,
                        itemText = { it.toText() },
                        icon = painterResource(R.drawable.rounded_arrow_right_24),
                        onClick = {
                            onChoosePaymentType(it)
                        }
                    )
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
private fun ChoosePaymentTypeDestinationPreview() {
    GetcardPayExampleTheme(dynamicColor = false) {
        Surface {
            PaymentDestination.ChoosePaymentTypePaymentDestination.Composable(
                onBackPressed = {},
                onChoosePaymentType = {}
            )
        }
    }
}
