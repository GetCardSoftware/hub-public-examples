package com.getcard.hub.getcardpay.example

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import androidx.core.graphics.createBitmap

class Mocks {
    companion object {
        fun generateReceiptBitmap(): Bitmap {
            // Largura da impressão em pixels (58mm ≈ 384px, depende da densidade da impressora)
            val width = 360
            val height = 600 // Altura estimada, pode ajustar conforme o conteúdo

            val bitmap = createBitmap(width, height)
            val canvas = Canvas(bitmap)
            canvas.drawColor(Color.WHITE) // fundo branco

            val paint = Paint().apply {
                color = Color.BLACK
                textSize = 24f
                typeface = Typeface.MONOSPACE
                isAntiAlias = true
            }

            var y = 40

            fun drawTextLine(text: String, textSize: Float = 24f, bold: Boolean = false) {
                paint.textSize = textSize
                paint.typeface = if (bold) Typeface.create(
                    Typeface.MONOSPACE,
                    Typeface.BOLD
                ) else Typeface.MONOSPACE
                canvas.drawText(text, 10f, y.toFloat(), paint)
                y += (textSize + 10).toInt()
            }

            // Cabeçalho
            drawTextLine("LOJA EXEMPLO LTDA", textSize = 28f, bold = true)
            drawTextLine("CNPJ: 12.345.678/0001-99")
            drawTextLine("Rua Fictícia, 123")
            drawTextLine("Data: 06/05/2025  Hora: 14:35")
            drawTextLine("--------------------------------")

            // Itens
            drawTextLine("QTD DESC         TOTAL", bold = true)
            drawTextLine("1   Produto A    R$ 10,00")
            drawTextLine("2   Produto B    R$ 5,00")
            drawTextLine("1   Produto C    R$ 8,50")

            drawTextLine("--------------------------------")

            // Totais
            drawTextLine("TOTAL:         R$ 28,50", bold = true)
            drawTextLine("PAGAMENTO:     Dinheiro")
            drawTextLine("TROCO:         R$ 1,50")

            drawTextLine("--------------------------------")
            drawTextLine("OBRIGADO PELA PREFERÊNCIA!", textSize = 22f)

            return bitmap
        }

        fun generateSitefReceipt(): String {

            val receipt = "                 BIN\n" +
                    "        VIA - ESTABELECIMENTO\n" +
                    "            LOJA TESTE BIN\n" +
                    "R. VERBO DIVINO 1661, GRANJA JULIETA,\n" +
                    "SAO PAULO                     50201136\n" +
                    "EC:000000000001234       TERM:TFI03C36\n" +
                    ".....S.O.F.T.W.A.R.E.E.X.P.R.E.S.S....\n" +
                    "SI                            Rede 229\n" +
                    "MU               Codigo transacao: 200\n" +
                    "LA               Codigo operacao: 3000\n" +
                    "DO                         Valor: 0,15\n" +
                    ".....S...I...M...U...L...A...D...O....\n" +
                    "SI                   NSU SiTef: 500016\n" +
                    "MU                      27/05/25 11:32\n" +
                    "LA                    ID PDV: CTBHAOCL\n" +
                    "DO             Estab.: 000000000001234\n" +
                    ".....S...I...M...U...L...A...D...O....\n" +
                    "SI                     Host: 005500016\n" +
                    "MU         Transacao Simulada Aprovada\n" +
                    "LA                  VENDA CREDITO SIM.\n" +
                    "            Credito Nubank\n" +
                    "          AID:A0000000041010\n" +
                    "          SiTef from Fiserv\n"

            return receipt
        }
    }
}