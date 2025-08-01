package com.getcard.hub.providers.example.ui.payment

import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.getcard.hub.providers.example.Settings
import com.getcard.hub.providers.example.StartTransactionActivity
import com.getcard.hubinterface.authentication.AuthParams
import com.getcard.hubinterface.transaction.InstallmentType
import com.getcard.hubinterface.transaction.PaymentType
import com.getcard.hubinterface.transaction.TransactionParams
import com.getcard.hubinterface.transaction.TransactionResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.math.BigDecimal

/**
 * Essa classe é responsável por gerenciar o estado da operação de pagamento
 * Ela irá receber os parâmetros vindos da UI, controlar o fluxo de telas
 * e iniciar a transação quando todos os parâmetros necessários estiverem preenchidos.
 */
class TransactionViewModel : ViewModel() {

    private val activity = mutableStateOf<ComponentActivity?>(null)

    private val transactionLauncher = MutableStateFlow<ActivityResultLauncher<Intent>?>(null)

    private val _paymentType = MutableStateFlow(PaymentType.CREDIT)

    private val _installmentType = MutableStateFlow(InstallmentType.ONE_TIME)

    private val _amount = MutableStateFlow(0)
    val amount = _amount.asStateFlow()

    private val _installments = MutableStateFlow(1)

    private val _paymentState = MutableStateFlow<PaymentState>(PaymentState.ChoosingPaymentAmount)
    val paymentState = _paymentState.asStateFlow()

    fun setAmount(amount: Int) {
        _amount.value = amount
    }

    fun onAmountConfirmed() {
        _paymentState.value = PaymentState.ChoosingPaymentType
    }

    fun onPaymentTypeSelected(paymentType: PaymentType) {
        _paymentType.value = paymentType
        if (paymentType == PaymentType.DEBIT || paymentType == PaymentType.PIX) {
            _paymentState.value = PaymentState.ProcessingPayment
            startTransaction()
            return
        }
        _paymentState.value = PaymentState.ChoosingInstallmentType
    }

    fun onInstallmentTypeSelected(type: InstallmentType) {
        _installmentType.value = type
        if (type == InstallmentType.ONE_TIME) {
            _paymentState.value = PaymentState.ProcessingPayment
            startTransaction()
            return
        }
        _paymentState.value = PaymentState.ChoosingInstallmentNumber
    }

    fun onBackPressed() {
        when (_paymentState.value) {
            PaymentState.ChoosingPaymentType -> _paymentState.value =
                PaymentState.ChoosingPaymentAmount

            PaymentState.ChoosingInstallmentType -> _paymentState.value =
                PaymentState.ChoosingPaymentType

            PaymentState.ChoosingInstallmentNumber -> _paymentState.value =
                PaymentState.ChoosingInstallmentType

            else -> {}
        }
    }

    fun onInstallmentNumberSelected(number: Int) {
        _installments.value = number
        _paymentState.value = PaymentState.ProcessingPayment
        startTransaction()
    }

    /**
     * Função responsável por armazenar o contexto da activity que ela foi instanciada e
     * definir o launcher que irá chamar a activity de pagamentos, recuperando o retorno dela
     * e alterando o estado da operação de pagamento.
     */
    fun setActivity(activity: ComponentActivity) {
        this.activity.value = activity
        transactionLauncher.value =
            activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                val response =
                    result.data?.getParcelableExtra<TransactionResponse>("TRANSACTION_RESULT")
                Log.d("MainActivity", "Response: $response")
                if (response != null) {
                    _paymentState.value = PaymentState.Finished(
                        response
                    )
                }
            }
    }

    /**
     * Função responsável por de fato iniciar uma transação. Utilizando o contexto
     * da activity que ela foi instanciada, ela irá chamar a activity de pagamentos,
     * chamada de [StartTransactionActivity].
     */
    fun startTransaction() {
        val intent = Intent(activity.value, StartTransactionActivity::class.java)
        intent.putExtra(
            "TRANSACTION_PARAMS",
            TransactionParams(
                amount = BigDecimal(_amount.value),
                paymentType = _paymentType.value,
                installmentType = _installmentType.value,
                installmentNumber = _installments.value
            )
        )
        intent.putExtra("AUTH_PARAMS", AuthParams(Settings.AUTH_TOKEN))
        transactionLauncher.value?.launch(intent)
    }

}