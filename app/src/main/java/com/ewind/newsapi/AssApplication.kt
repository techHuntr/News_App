package com.ewind.newsapi

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.ewind.newsapi.ki.dataBaseModule
import com.ewind.newsapi.ki.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Created by Arafath Misree on 30/11/19.
 */

open class AssApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AssApplication)
            modules(listOf(networkModule, dataBaseModule))
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}