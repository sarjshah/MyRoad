package com.practice.myroad.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class MyRoadViewModel: ViewModel() {


    class MyRoadViewModelFactory(): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(MyRoadViewModel::class.java)) {
                return MyRoadViewModel() as T
            }
            throw IllegalArgumentException("View Model requested does not exist")
        }
    }
}