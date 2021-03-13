package com.example.kino.network

import android.app.Application
import com.example.kino.db.DatabaseRepository
import com.example.kino.di.scope.ApplicationScope
import com.example.kino.network.interceptor.Interceptors
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.CipherSuite.Companion.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256
import okhttp3.CipherSuite.Companion.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256
import okhttp3.CipherSuite.Companion.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {

    private val BASE_URL = "https://api.themoviedb.org/3/"

    @Provides
    @ApplicationScope
    fun provideGson(): Gson {
        return GsonBuilder()
            .create()
    }

    @Provides
    @ApplicationScope
    fun provideApiProvider(
        application: Application,
        api: Api,
        dbRepository: DatabaseRepository
    ): NetworkRepository {
        return NetworkRepositoryImpl(application, api, dbRepository)
    }

    @Provides
    @ApplicationScope
    fun provideOkHttpClient(connectionSpec: ConnectionSpec,
                            application: Application): OkHttpClient {
        return OkHttpClient.Builder()
            .connectionSpecs(listOf(connectionSpec))
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addNetworkInterceptor(Interceptors.networkCacheInterceptor(application))
            .addInterceptor(Interceptors.offlineCacheInterceptor(application))
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
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @ApplicationScope
    fun providerApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

    @Provides
    @ApplicationScope
    fun providesConnectionSpec(): ConnectionSpec {
        return ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
            .tlsVersions(TlsVersion.TLS_1_2)
            .cipherSuites(
                TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                TLS_DHE_RSA_WITH_AES_128_GCM_SHA256
            )
            .build()
    }
}