package com.example.ui_common_compose.genrecommon

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp

@Composable
fun <T> HorizontalGenre(
    items: List<T>,
    modifier: Modifier = Modifier,
    header: @Composable () -> Unit = { },
    content: @Composable (T) -> Unit? = { },
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
    ) {
        header()

        val halfSpacing = 14.dp / 2
        val spacingContent = PaddingValues(halfSpacing, 0.dp, halfSpacing, 10.dp)
        val layoutDir = LocalLayoutDirection.current
        val contentPadding = PaddingValues(start = 16.dp, top = 0.dp, end = 8.dp, bottom = 10.dp)

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentPadding = PaddingValues(
                start = (contentPadding.calculateStartPadding(layoutDir) - halfSpacing).coerceAtLeast(
                    0.dp
                ),
                top = contentPadding.calculateTopPadding(),
                end = (contentPadding.calculateEndPadding(layoutDir) - halfSpacing).coerceAtLeast(0.dp),
                bottom = contentPadding.calculateBottomPadding(),
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(items.size) { index ->
                val item = items[index]
                //Fixme добавить шейп из темы
                Card(
                    modifier = Modifier
                        .padding(spacingContent)
                        .height(150.dp)
                        .fillMaxWidth()
                        .aspectRatio(2 / 3f),
                ) {
                    content(item)
                }
            }
        }
    }
}