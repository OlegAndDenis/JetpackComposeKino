package com.example.kino

import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

fun RequestManager.loadBackdropPath(patch: String, containerImage: ImageView) {
    load(Uri.parse("https://image.tmdb.org/t/p/w500/$patch"))
        .into(containerImage)
}

fun RequestManager.loadResources(containerImage: ImageView) {
    load("file:///android_asset/img/ic_launcher_round.png")
        .into(containerImage)
}

fun RequestManager.loadVarrags(paths: List<String>, vararg imageView: ImageView) {
    paths.forEachIndexed { index, s ->
        load(Uri.parse("https://image.tmdb.org/t/p/w500/$s"))
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView[index])
    }
}