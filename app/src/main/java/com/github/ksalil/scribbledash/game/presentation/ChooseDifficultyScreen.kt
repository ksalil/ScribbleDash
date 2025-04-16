package com.github.ksalil.scribbledash.game.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.ksalil.scribbledash.R
import com.github.ksalil.scribbledash.core.presentation.components.TitleWithDescription
import com.github.ksalil.scribbledash.core.presentation.ui.theme.BackgroundGradientEnd
import com.github.ksalil.scribbledash.core.presentation.ui.theme.BottomNavBarItemUnselectedColor
import com.github.ksalil.scribbledash.core.presentation.ui.theme.OnBackgroundVariant
import com.github.ksalil.scribbledash.core.presentation.ui.theme.ScribbleDashTheme
import com.github.ksalil.scribbledash.game.domain.DifficultyLevel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseDifficultyScreen(
    modifier: Modifier = Modifier,
    onCloseClicked: () -> Unit,
    onDifficultyLevelSelected: (DifficultyLevel) -> Unit
) {
    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {},
                actions = {
                    IconButton(
                        onClick = onCloseClicked
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_close_circle),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.background,
                            BackgroundGradientEnd,
                        )
                    )
                )
                .padding(innerPadding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleWithDescription(
                title = stringResource(R.string.difficulty_level_title),
                description = stringResource(R.string.difficult_level_description)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                DifficultyLevel(
                    modifier = Modifier.weight(0.33f),
                    title = stringResource(R.string.difficulty_level_beginner),
                    imageResourceId = R.drawable.ic_beginner,
                    onDifficultyLevelClicked = {
                        onDifficultyLevelSelected(DifficultyLevel.BEGINNER)
                    }
                )
                DifficultyLevel(
                    modifier = Modifier
                        .padding(bottom = 32.dp)
                        .weight(0.33f),
                    title = stringResource(R.string.difficulty_level_challenging),
                    imageResourceId = R.drawable.ic_challenging,
                    onDifficultyLevelClicked = {
                        onDifficultyLevelSelected(DifficultyLevel.CHALLENGING)
                    }
                )
                DifficultyLevel(
                    modifier = Modifier.weight(0.33f),
                    title = stringResource(R.string.difficulty_level_master),
                    imageResourceId = R.drawable.ic_master,
                    onDifficultyLevelClicked = {
                        onDifficultyLevelSelected(DifficultyLevel.MASTER)
                    }
                )
            }
        }
    }
}

@Composable
fun DifficultyLevel(
    modifier: Modifier = Modifier,
    title: String,
    imageResourceId: Int,
    onDifficultyLevelClicked: () -> Unit
) {
    Column(
        modifier = modifier
            .clickable {
                onDifficultyLevelClicked()
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            // Shadow box behind the image
            Box(
                modifier = Modifier
                    .size(88.dp)
                    .shadow(
                        elevation = 16.dp,
                        shape = CircleShape,
                        spotColor = BottomNavBarItemUnselectedColor.copy(alpha = 0.75f),
                        ambientColor = BottomNavBarItemUnselectedColor.copy(alpha = 0.75f)
                    )
                    .background(Color.Transparent)
            )
            Image(
                imageVector = ImageVector.vectorResource(id = imageResourceId),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            color = OnBackgroundVariant,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
private fun DifficultScreenPreview() {
    ScribbleDashTheme {
        ChooseDifficultyScreen(
            onCloseClicked = {},
            onDifficultyLevelSelected = {}
        )
    }
}