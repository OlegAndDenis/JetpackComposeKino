package com.example.kino.network
import android.app.Application
import android.content.Context
import com.example.kino.db.DatabaseRepository
import com.example.kino.di.scope.ApplicationScope
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
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
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.MINUTES)
            .writeTimeout(5, TimeUnit.MINUTES)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Provides
    @ApplicationScope
    fun provideApiProvider(application: Application,
                           api: Api,
                           dbRepository: DatabaseRepository): NetworkRepository {
        return NetworkRepositoryImpl(application,api, dbRepository)
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
}