package com.example.ui_tab_host

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabTopBar(
    tabHostScreens: List<TabScreen>,
    pagerState: PagerState,
    onTabSelected: (Int) -> Unit,
) {
    val indicator = @Composable { tabPositions: List<TabPosition> ->
        HomeCategoryTabIndicator(
            Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
        )
    }
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        indicator = indicator,
        modifier = Modifier
    ) {
        tabHostScreens.forEachIndexed { index, _ ->
            Tab(
                selected = index == pagerState.currentPage,
                onClick = {
                    onTabSelected(index)
                },
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