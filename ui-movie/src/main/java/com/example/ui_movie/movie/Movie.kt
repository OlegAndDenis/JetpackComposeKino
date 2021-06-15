package com.example.ui_movie.movie

import android.widget.Toast
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.themdb_api.MovieResult
import com.example.themdb_api.UiMovie
import com.example.ui_common_compose.extensions.rememberFlowWithLifecycle
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import kotlin.math.absoluteValue

const val baseImageUrl = "https://image.tmdb.org/t/p/"

val posterSizes = listOf(
    "w92",
    "w154",
    "w185",
    "w342",
    "w500",
    "w780",
    "original"
)

val backdropSizes = listOf(
    "w300",
    "w780",
    "w1280",
    "original"
)

@Preview
@Composable
fun Movie() {
    val movieViewModel: MovieViewModel = hiltViewModel()

    val state = rememberFlowWithLifecycle(flow = movieViewModel.movieState)
        .collectAsState(initial = MovieState.Loading()).value

    when (state) {
        is MovieState.Loading -> Loading()
        is MovieState.Result -> Movie(movie = state.uiMovies, state.top)
        else -> {
        }
    }
}

@Composable
fun Loading() {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = CenterHorizontally,
        ) {
            CircularProgressIndicator(
                modifier = Modifier.wrapContentWidth(CenterHorizontally),
                color = Color.Red
            )
        }
    }
}

@Composable
fun Movie(
    movie: List<UiMovie>,
    popularity: UiMovie = UiMovie()
) {
    val lazyListState = rememberLazyListState()
    val infiniteLoop by rememberSaveable { mutableStateOf(true) }
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
            .fillMaxSize()
            .padding(top = 10.dp),
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            reverseLayout = false,
            state = lazyListState,
        ) {
            item {
                Column {
                    Text(text = popularity.title, modifier = Modifier.padding(start = 10.dp))

                    Spacer(modifier = Modifier.height(10.dp))

                    CarouselWithHeader(
                        popularity,
                        modifier = Modifier
                            .graphicsLayer {
                                translationY = transaction
                                previousOffset = lazyListState.firstVisibleItemScrollOffset
                            },
                        infiniteLoop
                    )
                }
            }

            items(movie) {
                Genres(movie = it)
            }
        }
    }
}

@Composable
internal fun Genres(
    movie: UiMovie
) {
    val contaxt = LocalContext.current
    Column(
        modifier = Modifier.background(color = Color.White, shape = RectangleShape),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = movie.title,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = Modifier
                .padding(start = 16.dp, bottom = 10.dp)
                .wrapContentWidth(Alignment.End)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(),
                ) {
                    Toast
                        .makeText(contaxt, "${movie.title}", Toast.LENGTH_SHORT)
                        .show()
                }
        )

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
            items(movie.movies) {
                //Fixme добавить шейп из темы
                Card(
                    modifier = Modifier
                        .padding(spacingContent)
                        .height(150.dp)
                        .fillMaxWidth()
                        .aspectRatio(2 / 3f)
                ) {
                    Box {
                        Image(
                            painter = rememberCoilPainter(
                                request = baseImageUrl.plus("${posterSizes[2]}/")
                                    .plus(it.posterPath)
                            ),
                            contentDescription = " ",
                            modifier = Modifier.matchParentSize(),
                            contentScale = ContentScale.Crop,
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun CarouselWithHeader(
    items: UiMovie,
    modifier: Modifier = Modifier,
    infiniteLoop: Boolean
) {
    val pagerState = rememberPagerState(
        pageCount = items.movies.size,
        initialOffscreenLimit = 2,
        infiniteLoop = infiniteLoop
    )

    HorizontalPager(
        state = pagerState,
        itemSpacing = 5.dp,
        modifier = modifier.padding(bottom = 5.dp)
    ) { pager ->
        val item = items.movies[pager]
        Column(
            modifier = modifier
                .graphicsLayer {

                    val pageOffset = calculateCurrentOffsetForPage(pager).absoluteValue

                    lerp(
                        start = 0.75f,
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
            PosterCard(
                movie = item,
                modifier = modifier
            )

            Overview(
                item = item,
                isScrolling = !pagerState.isScrollInProgress,
            )
        }
    }
}

@Composable
internal fun Overview(
    item: MovieResult,
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
        Text(text = item.title, fontSize = 18.sp)
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
    movie: MovieResult,
) {
    Card(
        elevation = 5.dp,
        modifier = modifier.padding(start = 5.dp, end = 5.dp).clip(shape = MaterialTheme.shapes.large)
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = CenterHorizontally
        ) {
            Image(
                painter = rememberCoilPainter(
                    request = baseImageUrl.plus("${backdropSizes[3]}/").plus(movie.backdropPath)
                ),
                contentDescription = "",
                modifier = Modifier.aspectRatio(16 / 9F),
                contentScale = ContentScale.FillBounds,
            )
        }
    }
}