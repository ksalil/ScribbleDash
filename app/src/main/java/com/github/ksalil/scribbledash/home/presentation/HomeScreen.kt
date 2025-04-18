package com.github.ksalil.scribbledash.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.ksalil.scribbledash.R
import com.github.ksalil.scribbledash.core.presentation.components.GameModeItem
import com.github.ksalil.scribbledash.core.presentation.components.TitleWithDescription
import com.github.ksalil.scribbledash.core.presentation.ui.theme.BackgroundGradientEnd
import com.github.ksalil.scribbledash.core.presentation.ui.theme.BottomNavBarItemUnselectedColor
import com.github.ksalil.scribbledash.core.presentation.ui.theme.ScribbleDashTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onOneRoundWonderClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.scribble_dash),
                        style = MaterialTheme.typography.headlineMedium,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color.White
            ) {
                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_chart),
                            contentDescription = null
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent,
                        unselectedIconColor = BottomNavBarItemUnselectedColor.copy(
                            alpha = 0.4f
                        ),
                        selectedIconColor = MaterialTheme.colorScheme.primary
                    )
                )
                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_home),
                            contentDescription = null
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent,
                        unselectedIconColor = BottomNavBarItemUnselectedColor.copy(
                            alpha = 0.4f
                        ),
                        selectedIconColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Spacer(modifier = Modifier.height(64.dp))
            TitleWithDescription(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(R.string.home_title),
                description = stringResource(R.string.home_title_description)
            )
            GameModeItem(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                text = stringResource(R.string.game_mode_one_round_wonder),
                imageResourceId = R.drawable.ic_one_round_wonder,
                onClick = onOneRoundWonderClick
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    ScribbleDashTheme {
        HomeScreen(
            onOneRoundWonderClick = {}
        )
    }
}