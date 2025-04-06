package com.github.ksalil.scribbledash.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.ksalil.scribbledash.R
import com.github.ksalil.scribbledash.ui.theme.GameModeItemBorder
import com.github.ksalil.scribbledash.ui.theme.ScribbleDashTheme

@Composable
fun GameModeItem(
    modifier: Modifier = Modifier,
    text: String,
    imageResourceId: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .border(
                width = 8.dp,
                color = GameModeItemBorder,
                shape = RoundedCornerShape(16.dp)
            )
            .background(
                color = Color.White
            )
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier
                .weight(0.5f)
                .padding(start = 16.dp),
            text = text,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Start
        )
        Image(
            modifier = Modifier
                .weight(0.5f),
            alignment = Alignment.CenterEnd,
            imageVector = ImageVector.vectorResource(id = imageResourceId),
            contentDescription = null
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun GameModeButtonPreview() {
    ScribbleDashTheme {
        GameModeItem(
            text = "One Round Wonder",
            imageResourceId = R.drawable.ic_one_round_wonder,
            onClick = {}
        )
    }
}