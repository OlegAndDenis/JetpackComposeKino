package com.example.ui_tab_host

import android.annotation.SuppressLint
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import kotlin.math.absoluteValue
import kotlin.math.max

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

@SuppressLint("UnusedTransitionTargetStateParameter", "UnnecessaryComposedModifier")
@ExperimentalPagerApi
fun Modifier.pagerTabIndicatorOffset(
    pagerState: PagerState,
    tabPositions: List<TabPosition>,
): Modifier = composed {

    val transition = updateTransition(targetState = pagerState.currentPage, label = "")


    val offset: State<Dp>
    var indicatorWidth: Dp = 0.dp


    offset = transition.animateDp(transitionSpec = {
        spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMedium)
    }, label = "") {

        val currentTab = tabPositions[it]
        val targetPage = pagerState.targetPage ?: 0

        val targetTab = targetPage.let { tabPositions.getOrNull(it) }

        val targetDistance = (targetPage - pagerState.currentPage).absoluteValue

        val fraction = (pagerState.currentPageOffset / max(targetDistance, 1)).absoluteValue

        if (targetTab != null) {
            indicatorWidth = lerp(currentTab.width, targetTab.width, fraction).absoluteValue

            lerp(currentTab.left, targetTab.left, fraction)
        } else {
            indicatorWidth = currentTab.width
            currentTab.left
        }
    }

    fillMaxWidth()
        .wrapContentSize(Alignment.BottomStart)
        .offset { IntOffset(x = offset.value.roundToPx(), y = (-2).dp.roundToPx()) }
        .width(indicatorWidth)
}

private inline val Dp.absoluteValue: Dp
    get() = value.absoluteValue.dp