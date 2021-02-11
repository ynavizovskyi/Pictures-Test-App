package com.ynavizovskyi.picturestestapp.di

import com.google.gson.GsonBuilder
import com.ynavizovskyi.picturestestapp.common.BASE_URL
import com.ynavizovskyi.picturestestapp.datastore.remote.PicturesService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    @Named(BASE_URL)
    fun providesBaseUrl(): String {
        return "https://picsum.photos/v2/"
    }

    @Provides
    @Singleton
    fun providesConverterFactory(): Converter.Factory {
        val gson = GsonBuilder().setPrettyPrinting().create()
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    @JvmSuppressWildcards
    fun providesOkHttpClient(): OkHttpClient {
        val logginInterceptor = HttpLoggingInterceptor()
        logginInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        var builder = OkHttpClient
            .Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(logginInterceptor)
        return builder.build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(
        @Named(BASE_URL) baseUrl: String,
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun providesContactsService(retrofit: Retrofit): PicturesService {
        return retrofit.create(PicturesService::class.java)
    }

}