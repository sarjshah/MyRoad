package com.practice.myroad.data.network

import android.content.Context
import android.net.ConnectivityManager
import com.practice.myroad.internal.NoConnectivityException
import com.practice.myroad.utils.Constants
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptorImpl(context: Context) : ConnectivityInterceptor {
    private val appContext = context.applicationContext
    override fun intercept(chain: Interceptor.Chain): Response {
        if(!isOnline()) {
            throw NoConnectivityException(Constants.NO_CONNECTIVITY_MESSAGE)
        }
        return chain.proceed(chain.request())
    }

    private fun isOnline():Boolean {
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE)
        as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}