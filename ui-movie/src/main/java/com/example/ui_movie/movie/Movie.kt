package com.example.ui_movie.movie

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.coil_image.CoilImageWithCircularProgress
import com.example.themdb_api.UrlType
import com.example.themdb_api.createPath
import com.example.themdb_api.movie.MovieResult
import com.example.themdb_api.movie.UiMovie
import com.example.ui_common_compose.extensions.rememberFlowWithLifecycle
import com.example.ui_common_compose.genrecommon.HorizontalGenre
import com.example.ui_common_compose.loading.Loading
import com.example.ui_common_compose.topcarusel.Carousel

@Preview
@Composable
fun Movie() {
    val movieViewModel: MovieViewModel = hiltViewModel()
    movieViewModel.loadGenres()
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
fun Movie(
    movie: List<UiMovie>,
    popularity: UiMovie = UiMovie()
) {
    val lazyState = rememberLazyListState()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            state = lazyState
        ) {
            item("Top") {
                Carousel(
                    totalCount = popularity.movies.size,
                    title = {
                        Text(text = popularity.title, modifier = Modifier.padding(start = 10.dp))
                    },
                    image = {
                        PosterCard(
                            modifier = Modifier.fillMaxSize(),
                            popularity.movies[it]
                        )
                    },
                    overView = { (position, isScrolling) ->
                        Overview(item = popularity.movies[position], isScrolling = isScrolling)
                    }
                )
            }

            items(movie.size) { index ->
                val uiMovie = movie[index]
                HorizontalGenre(
                    header = { Header(uiMovie) },
                    items = uiMovie.movies
                ) {
                    Box {
                        var size by remember { mutableStateOf(IntSize(0, 0)) }

                        val path = createPath(size = size, UrlType.PosterPatch, it.posterPath)

                        CoilImageWithCircularProgress(
                            data = path,
                            nameFilm = it.originalTitle,
                            modifier = Modifier
                                .matchParentSize()
                                .onGloballyPositioned {
                                    size = it.size
                                },
                            contentScale = ContentScale.Crop,
                        )
                    }
                }
            }
        }
    }
}


@Composable
internal fun Header(
    movie: UiMovie
) {
    Row(
        modifier = Modifier
            .padding(start = 16.dp, bottom = 16.dp, end = 12.dp)
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = movie.title,
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
        modifier = modifier
            .padding(start = 5.dp, end = 5.dp)
            .clip(shape = MaterialTheme.shapes.large)
    ) {
        var size by remember { mutableStateOf(IntSize(0, 0)) }

        CoilImageWithCircularProgress(
            data = createPath(size, UrlType.Backdrop, movie.backdropPath),
            nameFilm = movie.originalTitle,
            modifier = Modifier
                .aspectRatio(16 / 9f)
                .clickable {
                }
                .onGloballyPositioned {
                    size = it.size
                },
            contentScale = ContentScale.FillBounds,
        )
    }
}