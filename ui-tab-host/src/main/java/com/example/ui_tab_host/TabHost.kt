package com.example.ui_tab_host

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabHost() {
    val allScreens = TabScreen.values().toList()
    val coroutineScope = rememberCoroutineScope()

    val pagerState = rememberPagerState(
        pageCount = allScreens.size,
        initialOffscreenLimit = 1
    )

    val currentPage by rememberSaveable { mutableStateOf(pagerState.currentPage) }

    Scaffold(
        topBar = {
            TabTopBar(
                tabHostScreens = allScreens,
                pagerState = pagerState,
                onTabSelected = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(it, pagerState.currentPageOffset)
                    }
                }
            )
        }
    ) { innerPadding ->
        HorizontalPager(state = pagerState, Modifier.padding(innerPadding)) { pager ->
            allScreens[pager].Content(onScreenChange = { allScreens[pager] })
        }
    }
}
