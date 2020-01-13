package com.example.gnb.di.module

import com.example.gnb.BuildConfig
import com.example.gnb.api.WebService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
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

/**
 * Module containing all the network dependencies that should be injected
 */
@Module
class ApiModule {

    companion object {
        private const val GSON_IDENTIFIER = "gson"
        private const val TIMEOUT = 10L
    }

    /**
     * Provides [OkHttpClient] object
     * @param httpLoggingInterceptor interceptor used for logging network activity
     * @return an [OkHttpClient] object
     */
    @Provides
    @Singleton
    internal fun provideHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build()

    /**
     * Provides [Gson] converter
     * @return a [Gson] object
     */
    @Provides
    @Singleton
    internal fun provideGson(): Gson = GsonBuilder().create()

    /**
     * Provides [Gson] converter factory
     * @param gson the [Gson] object
     * @return a [GsonConverterFactory] object
     */
    @Provides
    @Singleton
    @Named(GSON_IDENTIFIER)
    internal fun provideConverterFactory(gson: Gson): Converter.Factory =
        GsonConverterFactory.create(gson)

    /**
     * Provides the logging interceptor
     * @return an [HttpLoggingInterceptor] object
     */
    @Provides
    @Singleton
    internal fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return interceptor
    }

    /**
     * Method used for injecting the Web API
     * @param okHttpClient an [OkHttpClient] which doesn't connect to servers without proper certificates
     * @param gsonConverter the converter factory for parsing requests/responses
     * @return the [WebService] API interface
     */
    @Provides
    @Singleton
    internal fun provideWebService(
        okHttpClient: OkHttpClient,
        @Named(GSON_IDENTIFIER) gsonConverter: Converter.Factory
    ): WebService {
        val retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(gsonConverter)
            .client(okHttpClient).build()
        return retrofit.create(WebService::class.java)
    }
}