package com.example.ui_tv

import android.widget.Toast
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.coil_image.CoilImageWithCircularProgress
import com.example.themdb_api.UrlType
import com.example.themdb_api.createPath
import com.example.themdb_api.serials.SerialResult
import com.example.themdb_api.serials.UiSerial
import com.example.ui_common_compose.extensions.rememberFlowWithLifecycle
import com.example.ui_common_compose.loading.Loading
import com.example.ui_common_compose.topcarusel.Carousel

@Composable
fun Serial() {
    val viewModule: SerialViewModule = hiltViewModel()
    viewModule.loadGenres()
    val state = rememberFlowWithLifecycle(flow = viewModule.serials)
        .collectAsState(initial = SerialState.Loading).value
    when (state) {
        is SerialState.Loading -> Loading()
        is SerialState.Result -> Serial(listUiSerial = state.uiMovies, popularity = state.top)
        else -> {

        }
    }
}

@Composable
fun Serial(
    listUiSerial: List<UiSerial>,
    popularity: UiSerial
) {
    val lazyListState = rememberLazyListState()
    var previousOffset by remember { mutableStateOf(0) }

    val transaction by animateFloatAsState(
        targetValue = lazyListState.firstVisibleItemScrollOffset.toFloat() - previousOffset,
        tween(
            durationMillis = 150,
            delayMillis = 10,
        )
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        LazyColumn(contentPadding = it) {
            item("Top") {
                Carousel(
                    totalCount = popularity.serials.size,
                    modifier = Modifier.graphicsLayer {
                        translationY = transaction
                        previousOffset = lazyListState.firstVisibleItemScrollOffset
                    },
                    title = {
                        Text(text = popularity.name, modifier = Modifier.padding(start = 10.dp))
                    },
                    image = {
                        PosterCard(
                            modifier = Modifier.fillMaxSize(),
                            popularity.serials[it]
                        )
                    },
                    overView = { (position, isScrolling) ->
                        Overview(item = popularity.serials[position], isScrolling = isScrolling)
                    }
                )
            }

            items(listUiSerial) { serial ->
                Genres(serial)
            }
        }
    }
}

@Composable
internal fun Genres(
    uiSerial: UiSerial = UiSerial()
) {
    val contaxt = LocalContext.current
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
                modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
        ) {
        Text(
            text = uiSerial.name,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.Start)
        )

        Button(
            interactionSource = remember { MutableInteractionSource() },
            onClick = {
                Toast
                    .makeText(contaxt, "${uiSerial.name}", Toast.LENGTH_SHORT)
                    .show()
            },
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.End)
        ) {
            Text(text = "More")
        }
    }

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
            items(uiSerial.serials) {
                //Fixme добавить шейп из темы
                Card(
                    modifier = Modifier
                        .padding(spacingContent)
                        .height(150.dp)
                        .fillMaxWidth()
                        .aspectRatio(2 / 3f)
                ) {
                    Box {
                        var size by remember { mutableStateOf(IntSize(0, 0)) }

                        CoilImageWithCircularProgress(
                            modifier = Modifier
                                .matchParentSize()
                                .onGloballyPositioned {
                                    size = it.size
                                },
                            nameFilm = it.originalName,
                            data = createPath(size, UrlType.PosterPatch, it.posterPath),
                            contentScale = ContentScale.Crop,
                        )
                    }
                }
            }
        }
    }
}

@Composable
internal fun Overview(
    item: SerialResult,
    isScrolling: Boolean,
    modifier: Modifier = Modifier,
) {
    val alpha by animateFloatAsState(
        targetValue = if (isScrolling) 1F else 0F,
        tween(
            durationMillis = 150,
            delayMillis = 10,
            easing = LinearOutSlowInEasing
        )
    )

    Column(
        modifier = Modifier
            .graphicsLayer(
                alpha = alpha,
            )
            .padding(start = 10.dp, end = 10.dp),
    ) {
        Spacer(modifier = modifier.height(5.dp))
        Spacer(modifier = modifier.width(5.dp))
        Text(text = item.name, fontSize = 18.sp)
        Spacer(modifier = modifier.height(5.dp))

        Text(
            text = item.overview, maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            fontSize = 14.sp,
        )
        Spacer(modifier = modifier.height(10.dp))
    }
}

@Composable
internal fun PosterCard(
    modifier: Modifier = Modifier,
    serial: SerialResult,
) {
    Card(
        elevation = 5.dp,
        modifier = modifier
            .padding(start = 5.dp, end = 5.dp)
            .clip(shape = MaterialTheme.shapes.large)
    ) {
        var size by remember { mutableStateOf(IntSize(0, 0)) }

        CoilImageWithCircularProgress(
            modifier = Modifier
                .aspectRatio(16 / 9f)
                .clickable {
                }
                .onGloballyPositioned {
                    size = it.size
                },
            nameFilm = serial.originalName,
            data = createPath(size = size, UrlType.Backdrop, serial.backdropPath),
            contentScale = ContentScale.FillBounds,
        )
    }
}