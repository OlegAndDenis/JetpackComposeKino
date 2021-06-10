package com.example.kino.listview

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.kino.ComposePagerSnapHelper

@Composable
fun <T> Carousel(
    items: List<T>,
    modifier: Modifier = Modifier,
    itemSpacing: Dp = 0.dp,
    itemContent: @Composable LazyItemScope.(T, PaddingValues) -> Unit
) {
    val halfSpacing = itemSpacing / 2
    val spacingContent = PaddingValues(halfSpacing, 0.dp, halfSpacing, 0.dp)

    ComposePagerSnapHelper(
        width = 320.dp
    ) { listState ->
        LazyRow(
            state = listState,
            modifier = modifier.fillMaxWidth()
                .fillMaxHeight()
                .padding(bottom = 56.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(items) { item ->
                itemContent(item, spacingContent)
            }
        }
    }
}
