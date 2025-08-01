package com.getcard.hub.providers.example

import java.text.NumberFormat
import java.util.Locale

/**
 * Função auxiliar responsável por formatar o valor da transação de acordo com
 * o padrão brasileiro.
 */
fun applyMoneyMask(text: String): String {
    val paddedText = text.padStart(3, '0')

    val number = paddedText.toLongOrNull() ?: 0L
    val formatter = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    formatter.minimumFractionDigits = 2
    formatter.maximumFractionDigits = 2
    return formatter.format(number / 100.0).trim()
}