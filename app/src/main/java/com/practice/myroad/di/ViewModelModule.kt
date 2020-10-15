package com.practice.myroad.di

import com.practice.myroad.ui.MyRoadViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MyRoadViewModel(get()) }
}