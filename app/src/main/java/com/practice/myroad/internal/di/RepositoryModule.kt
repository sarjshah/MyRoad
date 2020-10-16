package com.practice.myroad.internal.di

import com.practice.myroad.data.repository.MyRoadRepository
import com.practice.myroad.data.repository.MyRoadRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single {
        MyRoadRepositoryImpl(get(), get()) as MyRoadRepository
    }
}