package com.example.themdb_api

import androidx.compose.ui.unit.IntSize
import timber.log.Timber

private const val baseImageUrl = "https://image.tmdb.org/t/p/"

private fun posterSizes(size: Int): String =
    when {
        size <= 100 -> "w92"
        size <= 150 -> "w154"
        size <= 200 -> "w185"
        size <= 350 -> "w342"
        size <= 500 -> "w500"
        size <= 800 -> "w780"
        size > 800 -> "original"
        else -> {
            Timber.i("unknown size")
            ""
        }
    }

private fun backdropSizes(size: Int): String =
    when {
        size <= 300 -> "w300"
        size <= 800 -> "w780"
        size <= 1300 -> "w1280"
        size > 1300 -> "original"
        else -> {
            Timber.i("unknown size")
            ""
        }
    }

private fun logoSize(size: Int): String = when {
    size <= 100 -> "w92"
    size <= 160 -> "w154"
    size <= 200 -> "w185"
    size <= 300 -> "w300"
    size <= 500 -> "w500"
    size > 500 -> "original"
    else -> {
        Timber.i("unknown size")
        ""
    }
}

fun createPath(size: IntSize, type: UrlType, path: String): Any {

    val maxSize = size.height.coerceAtLeast(size.width)
    if (maxSize == 0) {
        return when(type) {
            UrlType.Logo -> R.drawable.person_default
            else -> R.drawable.film_default
        }
    }

    if (path.isEmpty()) {

       return when(type) {
            UrlType.Logo -> R.drawable.person_default
            else -> R.drawable.film_default
        }
    }

    return when (type) {
        UrlType.Backdrop -> baseImageUrl.plus(backdropSizes(maxSize)).plus(path)
        UrlType.PosterPatch -> baseImageUrl.plus(posterSizes(maxSize)).plus(path)
        UrlType.Logo -> {
            val url =
                baseImageUrl.plus(logoSize(maxSize).plus(path))
            Timber.tag("URLS").i(url)
            url
        }
    }
}