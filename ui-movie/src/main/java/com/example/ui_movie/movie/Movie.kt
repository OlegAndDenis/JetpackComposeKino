package com.example.ui_movie.movie

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.coil_image.CoilImageWithCircularProgress
import com.example.themdb_api.UrlType
import com.example.themdb_api.common.MovieResult
import com.example.themdb_api.createPath
import com.example.themdb_api.movie.UiMovie
import com.example.ui_common_compose.animation.FadeThrough
import com.example.ui_common_compose.animation.FlingBehavior
import com.example.ui_common_compose.animation.rememberSplineDecay
import com.example.ui_common_compose.animation.scaleAnimation
import com.example.ui_common_compose.genrecommon.HorizontalGenre
import com.example.ui_common_compose.loading.Loading
import com.example.ui_common_compose.topcarusel.Carousel
import com.example.ui_movie.R
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun CreateMovie(
    openFilm: (Id: String) -> Unit = { },
    openGenres: (genresId: String) -> Unit = { },
) {
    val movieViewModel: MovieViewModel = hiltViewModel()
    val state = movieViewModel.viewState.value
    val effect = movieViewModel.effect
    val scaffoldState: ScaffoldState = rememberScaffoldState()

    FadeThrough(
        targetState = state,
    ) {
        if (it == state) {
            when (it) {
                is MovieState.Init -> {
                    Loading()
                    movieViewModel.loadGenres()
                }
                is MovieState.Result -> {
                    ShowMovie(
                        scaffoldState = scaffoldState,
                        movieViewModel = movieViewModel,
                        it.uiMovies
                    )
                }
                else -> {
                }
            }
        }
    }

    LaunchedEffect("") {
        effect.onEach { effect ->
            when (effect) {
                is Effect.Navigation.ToCategoryDetails -> {
                    openFilm(effect.id)
                }
                else -> {
                }
            }
        }.collect { }
    }
}

@Composable
private fun ShowMovie(
    scaffoldState: ScaffoldState,
    movieViewModel: MovieViewModel,
    movie: List<UiMovie>
) {
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        Movie(
            movie = movie,
            openFilm = { id -> movieViewModel.setEvent(Event.CategorySelection(id)) },
            contentPadding = paddingValues,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Movie(
    movie: List<UiMovie>,
    openFilm: (Id: String) -> Unit = { },
    contentPadding: PaddingValues,
) {
    val lazyState = rememberLazyListState()

    LazyColumn(
        contentPadding = contentPadding,
        state = lazyState,
        flingBehavior = FlingBehavior(flingDecay = rememberSplineDecay())
    ) {
        items(movie.size, key = { it }) { index ->
            if (index == 0) {
                Box(
                    modifier = Modifier
                        .scaleAnimation(lazyState, 0.5F)
                ) {
                    Carousel(
                        modifier = Modifier,
                        totalCount = movie[index].movies.size,
                        title = {
                            Text(
                                text = movie[index].title,
                                modifier = Modifier.padding(start = 10.dp)
                            )
                        },
                        image = {
                            PosterCard(
                                modifier = Modifier.fillMaxSize(),
                                movie = movie[index].movies[it],
                                openFilm = openFilm
                            )
                        },
                        overView = { (position, isScrolling) ->
                            Overview(
                                modifier = Modifier,
                                item = movie[index].movies[position],
                                isScrolling = isScrolling
                            )
                        }
                    )
                }
            } else {
                val position by animateIntAsState(targetValue = index, tween(400))
                val uiMovie = movie[position]
                HorizontalGenre(
                    modifier = Modifier,
                    header = { Header(uiMovie) },
                    items = uiMovie.movies
                ) { movieResult, spassingConent ->
                    Card(
                        modifier = Modifier
                            .padding(spassingConent)
                            .height(150.dp)
                            .fillMaxWidth()
                            .aspectRatio(2 / 3f),
                    ) {
                        Box {
                            var size by remember { mutableStateOf(IntSize(0, 0)) }

                            val path =
                                createPath(size = size, UrlType.PosterPatch, movieResult.posterPath)

                            CoilImageWithCircularProgress(
                                data = path,
                                nameFilm = movieResult.originalTitle,
                                modifier = Modifier
                                    .matchParentSize()
                                    .onGloballyPositioned {
                                        size = it.size
                                    }
                                    .animateContentSize()
                                    .clickable {
                                        openFilm(movieResult.id.toString())
                                    },
                                contentScale = ContentScale.Crop,
                            )
                        }
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
        val title =
            movie.title.replaceFirst(
                movie.title.first(),
                movie.title.first().uppercaseChar(),
                true
            )
        Text(
            text = title,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.Start, true)
        )

        Button(
            interactionSource = remember { MutableInteractionSource() },
            onClick = { },
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.End, true)
        ) {
            Text(text = stringResource(R.string.more))
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
            durationMillis = 400,
            delayMillis = 50,
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
    openFilm: (Id: String) -> Unit = { },
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
                    openFilm(movie.id.toString())
                }
                .onGloballyPositioned {
                    size = it.size
                },
            contentScale = ContentScale.FillBounds,
        )
    }
}