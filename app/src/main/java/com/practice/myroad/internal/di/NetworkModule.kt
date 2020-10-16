package com.practice.myroad.internal.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.practice.myroad.data.network.ConnectivityInterceptor
import com.practice.myroad.data.network.ConnectivityInterceptorImpl
import com.practice.myroad.data.network.MyRoadDataSource
import com.practice.myroad.data.network.MyRoadDataSourceImpl
import com.practice.myroad.internal.NonExistentRoadException
import com.practice.myroad.utils.Constants
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    factory {
        ConnectivityInterceptorImpl(androidApplication()) as ConnectivityInterceptor
    }

    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        val cache = Cache(
            androidApplication().cacheDir,
            10 * 1024 * 1024 // 10 MB
        )

        OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor { chain ->
                val url = chain
                    .request()
                    .url()
                    .newBuilder()
                    .addQueryParameter(Constants.APP_KEY, Constants.API_KEY)
                    .build()

                val request = chain
                    .request()
                    .newBuilder()
                    .url(url)
                    .build()

                chain.proceed(request)
            }
            .addInterceptor(get<ConnectivityInterceptor>())
            .addInterceptor(get<HttpLoggingInterceptor>())
            .addInterceptor { chain ->
                val request = chain.request()
                val response = chain.proceed(request)

                if (response.code() == 404) {
                    throw NonExistentRoadException(Constants.CANNOT_FIND_ROAD_MESSAGE)
                }
                chain.proceed(request)
            }
            .build()
    }

    // Provide Retrofit
    single<Retrofit> {
        Retrofit.Builder()
            .client(get<OkHttpClient>())
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    single {
        MyRoadDataSourceImpl(get()) as MyRoadDataSource
    }
}