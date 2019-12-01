package com.ewind.newsapi.ki

import com.ewind.newsapi.BuildConfig
import com.ewind.newsapi.data.local.database.DatabaseClient
import com.ewind.newsapi.data.remote.apis.NewsApi
import com.ewind.newsapi.util.network.SupportInterceptor
import com.ewind.newsapi.util.network.createNetworkClient
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit

private val tokenAuthenticator = SupportInterceptor(BuildConfig.API_KEY)

val networkModule: Module = module {
    single { tokenAuthenticator }
    single { NEWS_API }
}

val dataBaseModule: Module = module {
    single { DatabaseClient(androidApplication()) }
}

val retrofit: Retrofit =
    createNetworkClient(
        BuildConfig.API_URL,
        BuildConfig.DEBUG,
        tokenAuthenticator
    )

val NEWS_API: NewsApi = retrofit.create(NewsApi::class.java)
