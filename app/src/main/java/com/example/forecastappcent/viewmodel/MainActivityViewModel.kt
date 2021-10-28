package com.example.forecastappcent.viewmodel

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.forecastappcent.R
import com.example.forecastappcent.util.NetworkControlUtil
import com.example.forecastappcent.view.NearLocationFragment

private const val TAG = "MainActivityViewModel"

class MainActivityViewModel: ViewModel(){

    val internetControlMessage = MutableLiveData<String>()
    val errorControl = MutableLiveData<Boolean>()

    /**
     * Konuma göre yakın lokasyonları gösteren fragment' a
     * gönderir ...
     */
    fun goToNearLocation(activity : AppCompatActivity){
        val nearLocationFragment = NearLocationFragment()
        activity.supportFragmentManager.beginTransaction().replace(R.id.mainFrameLayout, nearLocationFragment).addToBackStack(null).commit()
    }

    /**
     * İnternet bağlantısını kontrol eder ...
     */
    fun internetCheck(activity: Activity) {
        if(!NetworkControlUtil.internetCheck(activity)){
            internetControlMessage.value = "Oops, please check your internet connection ."
            errorControl.value = true
        }
    }





}