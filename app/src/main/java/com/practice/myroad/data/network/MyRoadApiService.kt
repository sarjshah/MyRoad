package com.practice.myroad.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.practice.myroad.data.network.response.RoadResponse
import com.practice.myroad.internal.NonExistentRoadException
import com.practice.myroad.utils.Constants
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MyRoadApiService {
    @GET(Constants.URL_LINK)
    fun getRoad(
        @Path("roadId") roadId:String
    ): Deferred<List<RoadResponse>>
}
