package com.example.themdb_api

import android.content.Context
import com.example.base.network.model.ConnectionType
import com.example.themdb_api.api.ApiClient
import com.example.themdb_api.common.module
import com.example.themdb_api.interceptors.Interceptors
import com.example.themdb_api.themdbrepository.ThemdbRepository
import com.example.themdb_api.themdbrepository.ThemdbRepositoryImpl
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.ConnectionPool
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/** API Related Information */
private const val API_HOST = "api.themoviedb.org"
private const val API_VERSION = "3"
private const val API_URL = "https://$API_HOST/$API_VERSION/"

@Module
@InstallIn(SingletonComponent::class)
object ThemdbModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        connectionInfo: StateFlow<ConnectionType>
    ): OkHttpClient =
        OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addNetworkInterceptor(Interceptors.networkCacheInterceptor(connectionInfo))
            .addInterceptor(Interceptors.offlineCacheInterceptor(connectionInfo))
            .cache(Interceptors.provideCache(context.cacheDir))
            .connectionPool(ConnectionPool(10, 2, TimeUnit.MINUTES))
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

    private val json = Json{
        ignoreUnknownKeys = true
        coerceInputValues = true
        classDiscriminator = "media_type"
        serializersModule = module
    }

    fun mediaType(): MediaType = "application/json".toMediaType()

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(API_URL)
        .addConverterFactory(json.asConverterFactory(mediaType()))
        .client(client)
        .build()

    @Provides
    @Singleton
    fun provideApiClient(retrofit: Retrofit): ApiClient = retrofit.create(ApiClient::class.java)

    @Provides
    @Singleton
    fun provideThemdbRepository(apiClient: ApiClient): ThemdbRepository =
        ThemdbRepositoryImpl(apiClient)
}