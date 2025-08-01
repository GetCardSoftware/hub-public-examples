package com.getcard.hub.getcardpay.example.ui.payment

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.getcard.hub.getcardpay.example.data.AvailableInstallmentType
import com.getcard.hub.getcardpay.example.data.AvailableOperationStatus
import com.getcard.hub.getcardpay.example.data.AvailablePaymentType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Essa classe é responsável por gerenciar o estado da operação de pagamento
 * Ela irá receber os parâmetros vindos da UI, controlar o fluxo de telas
 * e iniciar a transação quando todos os parâmetros necessários estiverem preenchidos.
 */
class TransactionViewModel : ViewModel() {

    private val activity = mutableStateOf<ComponentActivity?>(null)

    private val transactionLauncher = MutableStateFlow<ActivityResultLauncher<Intent>?>(null)

    private val _paymentType = MutableStateFlow(AvailablePaymentType.CREDIT)

    private val _installmentType = MutableStateFlow(AvailableInstallmentType.ONE_TIME)

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

    fun onPaymentTypeSelected(paymentType: AvailablePaymentType) {
        _paymentType.value = paymentType
        if (paymentType == AvailablePaymentType.DEBIT || paymentType == AvailablePaymentType.PIX) {
            _paymentState.value = PaymentState.ProcessingPayment
            startTransaction()
            return
        }
        _paymentState.value = PaymentState.ChoosingInstallmentType
    }

    fun onInstallmentTypeSelected(type: AvailableInstallmentType) {
        _installmentType.value = type
        if (type == AvailableInstallmentType.ONE_TIME) {
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
     * definir o launcher que irá chamar a activity de pagamentos do GetCard Pay,
     * recuperando o retorno dela e alterando o estado da operação de pagamento.
     */
    fun setActivity(activity: ComponentActivity) {
        this.activity.value = activity
        transactionLauncher.value =
            activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                val data = result.data
                /**
                 * Foram preenchido somentes os parâmetros mais simples da transação para simplificar
                 * o exemplo. Os outros parâmetros podem ser encontrados na documentação:
                 * https://doc-hubpay.tefbr.com.br/getcard-pay/1.0.x/intent/payment
                 */
                if (data != null) {
                    data.getStringExtra("RESULT_TRANSACTION_ID_EXTRA")
                        ?.also { Log.d("TestIntent", "RESULT_TRANSACTION_ID_EXTRA = $it") }
                    data.getStringExtra("RESULT_OPERATION_STATUS_EXTRA")
                        ?.also { Log.d("TestIntent", "RESULT_OPERATION_STATUS_EXTRA = $it") }
                    data.getStringExtra("RESULT_MESSAGE_EXTRA")
                        ?.also { Log.d("TestIntent", "RESULT_MESSAGE_EXTRA = $it") }

                    val status = AvailableOperationStatus.valueOf(
                        data.getStringExtra("RESULT_OPERATION_STATUS_EXTRA")!!
                    )
                    val message = data.getStringExtra("RESULT_MESSAGE_EXTRA")!!
                    val transactionId = data.getStringExtra("RESULT_TRANSACTION_ID_EXTRA")
                    val timestamp = data.getLongExtra("RESULT_TRANSACTION_TIMESTAMP_EXTRA", 0)

                    _paymentState.value = PaymentState.Finished(
                        status = status,
                        message = message,
                        transactionTimestamp = timestamp,
                        transactionId = transactionId
                    )
                }
            }
    }

    /**
     * Função responsável por de fato iniciar uma transação. Utilizando o contexto
     * da activity que ela foi instanciada, ela irá chamar a activity de pagamentos
     * do GetCard Pay
     */
    fun startTransaction() {
        if (activity.value == null) {
            Log.e("TransactionViewModel", "Activity não inicializada!")
            return
        }

        val paymentIntent = Intent()
        paymentIntent.setClassName(
            "com.getcard.hub.getcardpayapp",
            "com.getcard.hub.getcardpayapp.ui.PaymentActivity"
        )
        paymentIntent.putExtra("AMOUNT_EXTRA", _amount.value) // Valor da transação EM CENTAVOS
        paymentIntent.putExtra(
            "PAYMENT_TYPE_EXTRA",
            _paymentType.value.toString()
        ) // Tipo de pagamento
        paymentIntent.putExtra(
            "INSTALLMENT_TYPE_EXTRA",
            _installmentType.value.toString()
        ) // Tipo de parcelamento
        paymentIntent.putExtra(
            "INSTALLMENT_NUMBER_EXTRA",
            _installments.value
        ) // Quantidade de parcelas

        if (paymentIntent.resolveActivity(activity.value!!.packageManager) != null) {
            Log.d("Exemplo", "Iniciando GetCardPay")
            transactionLauncher.value?.launch(paymentIntent)
        } else {
            Log.e("Exemplo", "Não existe GetCardPay nesse dispositivo")
            Toast.makeText(activity.value, "GetCard Pay não instalado", Toast.LENGTH_SHORT).show()
        }
    }

    fun doRefund(transactionId: String) {
        if (activity.value == null) {
            Log.e("TransactionViewModel", "Activity não inicializada!")
            return
        }
        val refundIntent = Intent()
        refundIntent.setClassName(
            "com.getcard.hub.getcardpayapp",
            "com.getcard.hub.getcardpayapp.ui.RefundActivity"
        )
        refundIntent.putExtra(
            "TRANSACTION_ID_EXTRA",
            transactionId
        ) // ID da transação a ser estornada

        if (refundIntent.resolveActivity(activity.value!!.packageManager) != null) {
            Log.d("Teste", "Iniciando GetCardPay")
            transactionLauncher.value?.launch(refundIntent)
        } else {
            Log.e("Teste", "Não existe GetCardPay nesse dispositivo")
            Toast.makeText(activity.value, "GetCard Pay não instalado", Toast.LENGTH_SHORT).show()
        }
    }

}