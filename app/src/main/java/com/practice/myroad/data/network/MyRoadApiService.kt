package com.practice.myroad.data.network

import com.practice.myroad.data.network.response.RoadResponse
import com.practice.myroad.utils.Constants
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface MyRoadApiService {
    @GET(Constants.URL_LINK)
    fun getRoad(
        @Path("roadId") roadId:String
    ): Deferred<List<RoadResponse>>
}
