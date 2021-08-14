package com.example.ui_search

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.coil_image.CoilImage
import com.example.coil_image.CoilImageWithCircularProgress
import com.example.themdb_api.UrlType
import com.example.themdb_api.createPath
import com.example.ui_common_compose.genrecommon.HorizontalGenre
import com.example.ui_common_compose.loading.Loading
import com.example.ui_common_compose.textField.SearchTextField
import com.example.ui_search.data.SearchUi
import com.example.ui_search.data.SmallData
import kotlinx.coroutines.Job
import kotlinx.coroutines.async

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
    val scope = rememberCoroutineScope()
    var currentJob by remember { mutableStateOf<Job?>(null) }

    val content = viewModel.viewState.value
    val state = rememberLazyListState()

    LazyColumn(
        state = state, modifier = Modifier
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
                    modifier = Modifier.padding(end = 20.dp),
                    onValueChange = {
                        currentJob?.cancel()
                        currentJob = scope.async {
                            if (it.length > 1)
                                viewModel.search(it)
                        }
                    }
                )
            }
        }

        when (content) {
            is SearchState.Loading -> {
                item {
                    Loading()
                }
            }
            is SearchState.Result -> {
                if (content.result.isNotEmpty()) {
                    items(count = content.result.size) {
                        val searchUi = content.result[it]
                        HorizontalGenre(
                            modifier = Modifier,
                            header = { Header(searchUi) },
                            items = searchUi.content
                        ) { smailDate, spacingContent ->
                            when (searchUi.type) {
                                "person" -> {
                                    PersonPoster(smallData = smailDate, spacingContent)
                                }
                                else -> {
                                    FilmPoster(smallData = smailDate, spacingContent)
                                }
                            }
                        }
                    }
                    currentJob?.cancel()
                }
            }
            is SearchState.NoResult -> {
                item {
                    currentJob?.cancel()
                    Text(
                        text = content.result,
                        modifier = Modifier
                            .padding(10.dp),
                    )
                }
            }
            else -> {

            }
        }

    }
}

@Composable
internal fun FilmPoster(
    smallData: SmallData,
    paddingValues: PaddingValues
) {
    Card(
        modifier = Modifier
            .padding(paddingValues)
            .height(150.dp)
            .fillMaxWidth()
            .aspectRatio(2 / 3f),
    ) {
        Box {
            var size by remember { mutableStateOf(IntSize(0, 0)) }
            val path = createPath(size = size, UrlType.PosterPatch, smallData.path)
            CoilImageWithCircularProgress(data = path,
                nameFilm = smallData.title,
                modifier = Modifier
                    .matchParentSize()
                    .onGloballyPositioned {
                        size = it.size
                    }
                    .animateContentSize())
        }
    }

}


@Composable
internal fun PersonPoster(
    smallData: SmallData,
    paddingValues: PaddingValues
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .padding(paddingValues)
                .height(140.dp)
                .clip(CircleShape)
                .fillMaxWidth()
                .aspectRatio(2 / 2F),
            elevation = 2.dp
        ) {
            Box {
                var size by remember { mutableStateOf(IntSize(0, 0)) }
                val path = createPath(size = size, UrlType.Logo, smallData.path)
                CoilImage(
                    data = path,
                    modifier = Modifier
                        .matchParentSize()
                        .onGloballyPositioned {
                            size = it.size
                        }
                        .animateContentSize(),
                    success = {
                        Image(
                            painter = BitmapPainter(it.imageBitmap!!),
                            contentDescription = "",
                            modifier = Modifier,
                            alignment = Alignment.Center,
                            contentScale = ContentScale.Crop,
                        )
                    },
                    loading = {
                    },
                    failure = {
                        Text(text = smallData.title)
                    }
                )
            }

        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = smallData.title)
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
                .width(1.dp)
                .wrapContentWidth(Alignment.Start, true)
        )
        Divider(
            Modifier
                .width(1.dp)
                .wrapContentWidth(Alignment.Start, true),
            color = Color.Black
        )
    }
}

@Preview
@Composable
private fun SearchPreview() {
    Search(PaddingValues(20.dp))
}