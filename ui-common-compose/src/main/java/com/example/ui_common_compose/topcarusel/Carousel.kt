package com.example.ui_common_compose.topcarusel

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.google.accompanist.pager.*
import kotlin.math.absoluteValue

@Composable
fun Carousel(
    modifier: Modifier = Modifier,
    totalCount: Int = 0,
    title: @Composable () -> Unit = { },
    image: @Composable (Int) -> Unit = { },
    overView: @Composable (Pair<Int, Boolean>) -> Unit = { }
) {
    Column {
        title.invoke()

        Spacer(modifier = Modifier.height(10.dp))

        CarouselWithHeader(
            modifier = modifier,
            image = image,
            overView = overView,
            totalCount = totalCount
        )
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun CarouselWithHeader(
    modifier: Modifier = Modifier,
    totalCount: Int = 0,
    image: @Composable (Int) -> Unit = { },
    overView: @Composable (Pair<Int, Boolean>) -> Unit = { }
) {
    val pagerState = rememberPagerState(
        pageCount = totalCount,
        initialOffscreenLimit = 2,
    )

    HorizontalPager(
        state = pagerState,
        itemSpacing = 2.dp,
        modifier = modifier.padding(bottom = 5.dp)
    ) { page ->
        Column(
            modifier = modifier
                .graphicsLayer {

                    val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                    lerp(
                        start = 0.8f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    ).also { scale ->
                        scaleX = scale
                        scaleY = scale
                    }

                    alpha = lerp(
                        start = 0.4f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
        ) {
            image.invoke(page)
        }
    }
    pagerState.SelectCurrentPage(overView)
}

@Composable
@ExperimentalPagerApi
fun PagerState.SelectCurrentPage(overView: @Composable (Pair<Int, Boolean>) -> Unit = { }) {
    overView.invoke(Pair(currentPage, !isScrollInProgress))
}