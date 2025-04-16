package com.github.ksalil.scribbledash.core.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.github.ksalil.scribbledash.core.presentation.ui.theme.OnBackgroundVariant
import com.github.ksalil.scribbledash.core.presentation.ui.theme.ScribbleDashTheme

@Composable
fun TitleWithDescription(
    modifier: Modifier = Modifier,
    title: String,
    titleStyle: TextStyle = MaterialTheme.typography.displayMedium,
    titleColor: Color = MaterialTheme.colorScheme.onBackground,
    titleTextAlign: TextAlign = TextAlign.Center,
    description: String,
    descriptionStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    descriptionColor: Color = OnBackgroundVariant,
    descriptionTextAlign: TextAlign = TextAlign.Center,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = titleStyle,
            color = titleColor,
            textAlign = titleTextAlign
        )
        Text(
            text = description,
            style = descriptionStyle,
            color = descriptionColor,
            textAlign = descriptionTextAlign
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TitleWithDescriptionPreview() {
    ScribbleDashTheme {
        TitleWithDescription(
            modifier = Modifier.fillMaxWidth(),
            title = "Title",
            description = "Description"
        )
    }
}