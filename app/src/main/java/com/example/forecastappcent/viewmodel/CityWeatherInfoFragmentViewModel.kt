package com.example.forecastappcent.viewmodel

import android.app.Activity
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.forecastappcent.model.CityWeatherInfo
import com.example.forecastappcent.service.ApiClient
import com.example.forecastappcent.service.ApiInterface
import com.example.forecastappcent.util.Constant
import com.example.forecastappcent.util.NetworkControlUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "CityWeatherInfo"

class CityWeatherInfoFragmentViewModel : ViewModel() {

    // For API
    private val apiInterface = ApiClient.client?.create(ApiInterface::class.java)

    // For Data
    val cityWeatherInfoData = MutableLiveData<CityWeatherInfo>()
    val loading = MutableLiveData<Boolean>()

    // For Errors
    val errorMessage = MutableLiveData<String>()
    val errorControl = MutableLiveData<Boolean>()

    /**
     * İstenen şehir için hava durumu bilgilerini getirir ...
     */
    fun getWeatherInfoDataFromAPI(woeid: String){
        loading.value = true
        var responseFromAPI = apiInterface?.getWeatherInfo(woeid)
        responseFromAPI?.enqueue(object: Callback<CityWeatherInfo> {
            override fun onResponse(call: Call<CityWeatherInfo>, response: Response<CityWeatherInfo>) {
                cityWeatherInfoData.value = response?.body()
                loading.value = false
                Log.e(TAG, "onResponse: Success")
            }
            override fun onFailure(call: Call<CityWeatherInfo>, t: Throwable) {
                errorMessage.value = "Oops, please check your internet connection."
                errorControl.value = true
                loading.value = false
                Log.e(TAG, "onError: " + t.message)
            }
        })
    }

    /**
     * Fragmenttaki argümentleri yakalar ...
     */
    fun getArguments(fragment : Fragment) : String?{
        return fragment.arguments?.getString(Constant.WOEID)
    }

    /**
     * İnternet bağlantısını kontrol eder ...
     */
    fun internetCheck(activity: Activity) {
        if(!NetworkControlUtil.internetCheck(activity)){
            errorMessage.value = "Oops, please check your internet connection ."
            errorControl.value = true
        }
    }

}