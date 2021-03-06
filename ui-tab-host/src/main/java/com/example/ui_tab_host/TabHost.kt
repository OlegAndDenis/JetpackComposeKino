package com.example.ui_tab_host

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.ui_movie.movie.CreateMovie
import com.example.ui_tv.Serial
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabHost(
    openFilm: (Id: String, type: Type) -> Unit = { _, _ -> },
    openGenres: (genresId: String, type: Type) -> Unit = { _, _ -> },
) {
    val allScreens = TabScreen.values().toList()
    val coroutineScope = rememberCoroutineScope()

    val pagerState = rememberPagerState(
        pageCount = allScreens.size,
        initialOffscreenLimit = 1
    )

    Scaffold(
        topBar = {
            TabTopBar(
                tabHostScreens = allScreens,
                pagerState = pagerState,
                onTabSelected = { page ->
                    if (page != pagerState.currentPage && !pagerState.isScrollInProgress) {
                        coroutineScope.launch {
                            if (pagerState.currentPage != page) {
                                pagerState.animateScrollToPage(
                                    page = page,
                                    pageOffset = pagerState.currentPageOffset,
                                    animationSpec = tween(500, 50)
                                )
                            }
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        HorizontalPager(state = pagerState, Modifier.padding(innerPadding)) { page ->
            when (allScreens[page]) {
                TabScreen.MovieScreen -> CreateMovie(
                    openFilm = { openFilm(it, Type.MOVIE) },
                    openGenres = { openGenres(it, Type.MOVIE) })
                TabScreen.TvScreen -> Serial(
                    openFilm = { openFilm(it, Type.TV) },
                    openGenres = { openGenres(it, Type.TV) })
            }
        }
    }
}