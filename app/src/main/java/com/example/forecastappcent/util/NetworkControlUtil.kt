package com.example.forecastappcent.util

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * İnternet kontrolünü yapar ...
 */
class NetworkControlUtil {
    companion object{
        fun internetCheck(activity: Activity):Boolean    {
            val cm= activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
            return  isConnected
        }
    }
}