package com.example.kino

import android.content.Context
import android.net.Uri
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class GlideManage private constructor(private val requestManager: RequestManager) {

    companion object {
        fun with(view: View): GlideManage = GlideManage(Glide.with(view))

        fun with(context: Context): GlideManage = GlideManage(Glide.with(context))
    }

    fun loadImage(path: String, imageView: ImageView) {
        requestManager
            .load(createPath(path))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)
    }

    fun loadImage(paths: List<String>, vararg imageView: ImageView) {
        paths.forEachIndexed { index, path ->
            requestManager
                .load(createPath(path))
                .centerCrop()
                .into(imageView[index])
        }
    }

    fun loadImage(paths: List<String>, imageView: ImageView) {
        paths.forEachIndexed { _, path ->
            requestManager
                .load(createPath(path))
                .centerCrop()
                .into(imageView)
        }
    }

    fun loadPoster(path: String, imageView: ImageView) {
        requestManager
            .load(createPoster(path))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)
    }

    fun loadRoundPoster(path: String, imageView: ImageView) {
        requestManager
            .load(createPoster(path))
            .transition(DrawableTransitionOptions.withCrossFade())
            .transform(RoundTransformation())
            .into(imageView)
    }

    private fun createPoster(path: String): Uri =
        Uri.parse("https://image.tmdb.org/t/p/w500/$path")

    private fun createPath(path: String): Uri =
        Uri.parse("https://image.tmdb.org/t/p/w500/$path")
}