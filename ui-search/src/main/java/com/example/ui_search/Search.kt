package com.example.ui_search

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.coil_image.CoilImageWithCircularProgress
import com.example.themdb_api.UrlType
import com.example.themdb_api.createPath
import com.example.ui_common_compose.extensions.rememberFlowWithLifecycle
import com.example.ui_common_compose.genrecommon.HorizontalGenre
import com.example.ui_common_compose.textField.SearchTextField
import com.example.ui_search.data.SearchUi

@Composable
fun Search() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        Search(paddingValues)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun Search(contentPaddingValues: PaddingValues) {
    val viewModel: SearchViewModel = hiltViewModel()

    val content = rememberFlowWithLifecycle(flow = viewModel.result).collectAsState(
        initial = emptyList()
    )
    val state = rememberLazyListState()

    LazyColumn(state = state, modifier = Modifier
        .padding(top = 20.dp)
        .fillMaxSize(),
        contentPadding = contentPaddingValues
    ) {
        stickyHeader("Header") {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                SearchTextField(
                    hint = "Search",
                    modifier = Modifier,
                    onValueChange = {
                        if (it.length >= 3) {
                            viewModel.search(it)
                        }
                    }
                )
            }
        }

        if (content.value.isNotEmpty()) {
            items(count = content.value.size) {
                val searchUi = content.value[it]
                HorizontalGenre(
                    modifier = Modifier,
                    header = { Header(searchUi) },
                    items = searchUi.content
                ) {
                    Box {
                        var size by remember { mutableStateOf(IntSize(0, 0)) }

                        val path = createPath(size = size, UrlType.PosterPatch, it.path)
                        CoilImageWithCircularProgress(data = path,
                            nameFilm = it.title,
                            modifier = Modifier
                                .matchParentSize()
                                .onGloballyPositioned {
                                    size = it.size
                                }
                                .animateContentSize())
                    }
                }
            }
        }
    }
}

@Composable
internal fun Header(
    searchUi: SearchUi
) {
    Row(
        modifier = Modifier
            .padding(start = 16.dp, bottom = 16.dp, end = 12.dp)
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val title = searchUi.type
        Text(
            text = title,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = Modifier
                .wrapContentWidth(Alignment.Start)
        )
    }
}

@Preview
@Composable
private fun SearchPreview() {
    Search()
}