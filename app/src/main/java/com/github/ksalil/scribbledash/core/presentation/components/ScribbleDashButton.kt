package com.github.ksalil.scribbledash.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.ksalil.scribbledash.ui.theme.Success

@Composable
fun ScribbleDashButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    text: String,
    containerColor: Color = Success,
    disabledContainerColor: Color = MaterialTheme.colorScheme.surfaceContainerLowest,
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = modifier,
        enabled = enabled,
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = 4.dp,
            color = Color.White
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = containerColor,
            disabledContainerColor = disabledContainerColor
        )
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp),
            text = text,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onPrimary.copy(
                alpha = 0.8f
            )
        )
    }
}