package com.example.ui_movie.movie

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TabTopBar(
    tabHostScreens: List<TabScreen>,
    currentScreen: TabScreen,
    onTabSelected: (TabScreen) -> Unit,
) {
    val selectedIndex = tabHostScreens.indexOfFirst { it == currentScreen }
    val indicator = @Composable { tabPositions: List<TabPosition> ->
        HomeCategoryTabIndicator(
            Modifier.tabIndicatorOffset(tabPositions[selectedIndex])
        )
    }
    TabRow(
        selectedTabIndex = selectedIndex,
        indicator = indicator,
        modifier = Modifier
    ) {
        tabHostScreens.forEachIndexed { index, screen ->
            Tab(
                selected = index == selectedIndex,
                onClick = { onTabSelected(screen) },
                text = {
                    Text(
                        text = tabHostScreens[index].title,
                        style = MaterialTheme.typography.body2
                    )
                }
            )
        }
    }
}

@Composable
fun HomeCategoryTabIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.onSurface
) {
    Spacer(
        modifier
            .padding(horizontal = 24.dp)
            .height(4.dp)
            .background(
                color,
                RoundedCornerShape(
                    topStartPercent = 100,
                    topEndPercent = 100,
                    bottomEndPercent = 100,
                    bottomStartPercent = 100
                )
            )
    )
}