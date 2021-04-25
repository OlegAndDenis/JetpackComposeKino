package com.example.kino

import android.net.Uri

object CreatePatchImage{
    fun createPatch(patch: String): Uri {
        return Uri.parse("https://image.tmdb.org/t/p/w500/$patch")
    }
}