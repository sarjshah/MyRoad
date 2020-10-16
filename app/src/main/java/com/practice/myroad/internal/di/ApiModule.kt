package com.practice.myroad.internal.di

import com.practice.myroad.data.network.MyRoadApiService
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single {
        get<Retrofit>().create(MyRoadApiService::class.java)
    }
}

