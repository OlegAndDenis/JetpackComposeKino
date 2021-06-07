package com.example.kino.network

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.example.kino.connectoninfo.connectionState
import com.example.kino.connectoninfo.model.ConnectionType
import com.example.kino.di.scope.ApplicationScope
import com.example.kino.network.interceptor.Interceptors
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.flow.StateFlow
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://api.themoviedb.org/3/"

@Module
class NetworkModule {

    @Provides
    @ApplicationScope
    fun provideGson(): Gson {
        return GsonBuilder()
            .create()
    }

    @Provides
    @ApplicationScope
    fun provideApiProvider(
        api: Api
    ): NetworkRepository {
        return NetworkRepositoryImpl(api)
    }

    @Provides
    @ApplicationScope
    fun provideOkHttpClient(
        application: Application,
        connectionInfo: StateFlow<ConnectionType>
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addNetworkInterceptor(Interceptors.networkCacheInterceptor(connectionInfo))
            .addInterceptor(Interceptors.offlineCacheInterceptor(connectionInfo))
            .cache(Interceptors.provideCache(application.cacheDir))
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Provides
    @ApplicationScope
    fun provideRetrofit(gson: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    @Provides
    @ApplicationScope
    fun providerApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

    @Provides
    fun provideConnectivityManager(context: Context): ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @Provides
    @ApplicationScope
    fun provideNetworkState(manager: ConnectivityManager): StateFlow<@JvmWildcard ConnectionType> =
        connectionState(manager)
}