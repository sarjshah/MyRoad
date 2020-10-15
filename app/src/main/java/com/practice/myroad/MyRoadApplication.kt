package com.practice.myroad

import android.app.Application
import android.content.Context
import com.practice.myroad.di.*
import org.koin.android.ext.koin.*
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyRoadApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyRoadApplication)
            androidLogger(Level.DEBUG)
            modules(listOf(viewModelModule, repositoryModule, apiModule, networkModule, databaseModule))
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }
}