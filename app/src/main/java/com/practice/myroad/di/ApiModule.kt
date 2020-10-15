package com.practice.myroad.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.practice.myroad.data.network.ConnectivityInterceptor
import com.practice.myroad.data.network.ConnectivityInterceptorImpl
import com.practice.myroad.data.network.MyRoadApiService
import com.practice.myroad.utils.Constants
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val apiModule = module {
    single {
        get<Retrofit>().create(MyRoadApiService::class.java)
    }
}

