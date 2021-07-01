package com.example.ui_tv

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.ui_common_compose.animation.scaleAnimation
import com.example.ui_common_compose.extensions.rememberFlowWithLifecycle
import com.example.ui_common_compose.genrecommon.HorizontalGenre
import com.example.ui_common_compose.loading.Loading
import com.example.ui_common_compose.topcarusel.Carousel

@Composable
fun Serial(
    openFilm: (Id: String) -> Unit = { },
    openGenres: (genresId: String) -> Unit = { },
) {
    val viewModule: SerialViewModule = hiltViewModel()
    viewModule.loadGenres()
    val state = rememberFlowWithLifecycle(flow = viewModule.serials)
        .collectAsState(initial = SerialState.Loading).value

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Crossfade(
            targetState = state,
            animationSpec = tween(
                durationMillis = 700,
                delayMillis = 250,
                easing = FastOutSlowInEasing
            )
        ) {
            when (it) {
                is SerialState.Loading -> Loading()
                is SerialState.Result -> Serial(
                    listUiSerial = it.uiMovies,
                    popularity = it.top,
                    contentPadding = paddingValues
                )
                else -> {

                }
            }
        }
    }
}

@Composable
fun Serial(
    listUiSerial: List<UiSerial>,
    popularity: UiSerial,
    contentPadding: PaddingValues,
) {
    val lazyState = rememberLazyListState()

    LazyColumn(contentPadding = contentPadding, state = lazyState) {
        item("Top") {
            Box(modifier = Modifier.scaleAnimation(lazyState, 1F)) {
                Carousel(
                    totalCount = popularity.serials.size,
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
        }

        items(listUiSerial.size) { index ->
            val uiSerial = listUiSerial[index]
            HorizontalGenre(
                items = uiSerial.serials,
                header = { Header(serial = uiSerial) }
            ) { serial ->
                var size by remember { mutableStateOf(IntSize(0, 0)) }
                Box {
                    CoilImageWithCircularProgress(
                        modifier = Modifier
                            .matchParentSize()
                            .onGloballyPositioned { layoutParam ->
                                size = layoutParam.size
                            },
                        nameFilm = serial.originalName,
                        data = createPath(size, UrlType.PosterPatch, serial.posterPath),
                        contentScale = ContentScale.Crop,
                    )
                }
            }
        }
    }
}

@Composable
internal fun Header(
    serial: UiSerial
) {
    Row(
        modifier = Modifier
            .padding(start = 16.dp, bottom = 16.dp, end = 12.dp)
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = serial.name,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.Start)
        )

        Button(
            interactionSource = remember { MutableInteractionSource() },
            onClick = { },
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.End)
        ) {
            Text(text = "More")
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