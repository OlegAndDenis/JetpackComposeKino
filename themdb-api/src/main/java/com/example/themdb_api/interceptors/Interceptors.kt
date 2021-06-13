package com.example.themdb_api.interceptors

import com.example.base.network.model.ConnectionType
import kotlinx.coroutines.flow.StateFlow
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Request
import timber.log.Timber
import java.io.File
import java.util.concurrent.TimeUnit

object Interceptors {
    private const val MAX_AGE = 120
    private const val MAX_STALE: Long = 86400
    private const val MAX_SIZE_CACHE: Long = 50 * 1024 * 1024

    fun offlineCacheInterceptor(connectionInfo: StateFlow<ConnectionType>): Interceptor {
        return Interceptor {
            var request = it.request()
            if (connectionInfo.value is ConnectionType.Available) {
                Timber.i("Interceptors true")
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

    fun networkCacheInterceptor(connectionInfo: StateFlow<ConnectionType>): Interceptor {
        return Interceptor {
            val originalRequest: Request = it.request()

            val cacheHeaderValue =
                if (connectionInfo.value is ConnectionType.Available) "public, max-age=$MAX_AGE"
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
            cache = Cache(File(dir, "api_cache"), MAX_SIZE_CACHE)
        } catch (e: Exception) {
            Timber.e(e)
        }
        return cache
    }
}