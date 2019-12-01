package com.ewind.newsapi.util.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


const val TIME_OUT_DURATION = 40L

fun createNetworkClient(
    baseUrl: String,
    debug: Boolean = false,
    supportInterceptor: SupportInterceptor
) = retrofitClient(baseUrl, httpClient(debug, supportInterceptor))

private fun httpClient(
    debug: Boolean,
    supportInterceptor: SupportInterceptor
): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
    val clientBuilder = OkHttpClient.Builder()
        .apply {
            connectTimeout(TIME_OUT_DURATION, TimeUnit.SECONDS)
            writeTimeout(TIME_OUT_DURATION, TimeUnit.SECONDS)
            readTimeout(TIME_OUT_DURATION, TimeUnit.SECONDS)
        }

    if (debug) {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        clientBuilder.addInterceptor(httpLoggingInterceptor)
    }

    //clientBuilder.authenticator(supportInterceptor)
    clientBuilder.addInterceptor(supportInterceptor)

    return clientBuilder.build()
}

private fun retrofitClient(
    baseUrl: String,
    httpClient: OkHttpClient
): Retrofit =
    Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
