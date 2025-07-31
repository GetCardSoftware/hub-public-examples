package com.getcard.hub.providers.example.ui.payment.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun <T> OptionButton(
    modifier: Modifier = Modifier,
    item: T,
    onClick: () -> Unit,
    itemText: (T) -> String,
    icon: Painter? = null
) {
    TextButton(
        modifier = modifier
            .height(60.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(size = 5.dp),
        elevation = ButtonDefaults.buttonElevation(1.dp),
        colors = ButtonDefaults.textButtonColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        onClick = onClick
    ) {
        Box(Modifier.fillMaxSize()) {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    text = itemText(item),
                    fontSize = 16.sp
                )
            }

            if (icon != null) {
                Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Icon(
                        painter = icon,
                        contentDescription = itemText(item)
                    )
                }
            }
        }
    }
}