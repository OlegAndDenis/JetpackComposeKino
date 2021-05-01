package com.example.kino.network.interceptor

import android.content.Context
import com.example.kino.network.ConnectionCheck
import okhttp3.*
import timber.log.Timber
import java.io.File
import java.util.concurrent.TimeUnit

class Interceptors {
    companion object {
        private const val MAX_AGE = 120
        private const val MAX_STALE: Long = 86400

        fun offlineCacheInterceptor(context: Context): Interceptor {
            return Interceptor {
                var request = it.request()
                if (ConnectionCheck.isOnline(context)) {
                    val cacheControl: CacheControl = CacheControl.Builder()
                        .maxStale(1, TimeUnit.DAYS)
                        .build()
                    request = request.newBuilder()
                        .header("Cache-Control", cacheControl.toString())
                        .build()
                }
                return@Interceptor it.proceed(request)
            }
        }

        fun networkCacheInterceptor(context: Context): Interceptor {
            return Interceptor {
                val originalRequest: Request = it.request()

                val cacheHeaderValue = if (ConnectionCheck.isOnline(context)) "public, max-age=$MAX_AGE"
                else "public, only-if-cached, max-stale=$MAX_STALE"
                val request: Request = originalRequest.newBuilder().build()
                val response = it.proceed(request)
                return@Interceptor response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", cacheHeaderValue)
                    .build()
            }
        }

        fun provideCache(dir: File): Cache? {
            var cache: Cache? = null
            try {
                cache = Cache(File(dir, "cache"), 10 * 1024 * 1024)
            } catch (e: Exception) {
                Timber.e(e)
            }
            return cache
        }
    }
}