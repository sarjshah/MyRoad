package com.practice.myroad.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.practice.myroad.R
import com.practice.myroad.data.db.RoadEntityMapperImpl
import com.practice.myroad.data.network.ConnectivityInterceptor
import com.practice.myroad.data.network.ConnectivityInterceptorImpl
import com.practice.myroad.databinding.ActivityMainBinding
import com.practice.myroad.data.network.MyRoadApiService
import com.practice.myroad.data.network.MyRoadDataSourceImpl
import com.practice.myroad.data.network.response.RoadResponseMapperImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var myRoadViewModel: MyRoadViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        myRoadViewModel = ViewModelProviders.of(this, MyRoadViewModel.MyRoadViewModelFactory()).get(MyRoadViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()


        val apiService = MyRoadApiService(ConnectivityInterceptorImpl(this@MainActivity!!))
        val roadDataSource = MyRoadDataSourceImpl(apiService)
        roadDataSource.downloadedRoadData.observe(this, Observer { roadResponseList ->
            val road = RoadEntityMapperImpl()
                .listToDomainModel(
                    RoadResponseMapperImpl()
                        .listToEntity(
                            roadResponseList
                        ))
            binding.result = road[0]

        })
        GlobalScope.launch(Dispatchers.Main) {
            roadDataSource.fetchRoadData("A2")
        }
    }
}