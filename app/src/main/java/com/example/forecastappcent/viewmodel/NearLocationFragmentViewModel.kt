package com.example.forecastappcent.viewmodel

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.forecastappcent.model.CityWeatherInfo
import com.example.forecastappcent.model.ConsolidatedWeather
import com.example.forecastappcent.model.NearLocation
import com.example.forecastappcent.service.ApiClient
import com.example.forecastappcent.service.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "NearLocationFragment"

class NearLocationFragmentViewModel : ViewModel(), LocationListener {
    // For Location
    private var locationManager: LocationManager? = null
    private var permissionControl: Int? = null
    private val LOCATION_PROVIDER = "gps"

    // For API
    private val apiInterface = ApiClient.client?.create(ApiInterface::class.java)
    var nearLocationData = MutableLiveData<List<NearLocation>>()
    val nearLocationLoading = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    val cityWeatherInfoData = MutableLiveData<CityWeatherInfo>()
    val cityWeatherInfoLoading = MutableLiveData<Boolean>()

    // For Errors
    val errorMessage = MutableLiveData<String>()
    val errorControl = MutableLiveData<Boolean>()

    /**
     * Enlem ve boylam bilgilerine API'dan yakındaki şehirleri getirir ...
     */
    fun getNearLocationDataFromAPI(location: String){
        //nearLocationLoading.value = true
        loading.value = true
        var responseFromAPI = apiInterface?.getNearLocation(location)
        responseFromAPI?.enqueue(object: Callback<List<NearLocation>> {
            override fun onResponse(call: Call<List<NearLocation>>, response: Response<List<NearLocation>>) {
                nearLocationData.value = response?.body()
                //nearLocationLoading.value = false
                errorControl.value = false
                arrayControl(response?.body())
                Log.e(TAG, "onResponse: Success")
            }
            override fun onFailure(call: Call<List<NearLocation>>, t: Throwable) {
                errorMessage.value = "Oops, please check your internet connection."
                errorControl.value = true
                //nearLocationLoading.value = false
                loading.value = true
                Log.e(TAG, "onError: " + t.message)
            }
        })
    }

    /**
     * İstenen şehir için hava durumu bilgilerini getirir ...
     */
    fun getWeatherInfoDataFromAPI(woeid: String){
        //cityWeatherInfoLoading.value = true
        var responseFromAPI = apiInterface?.getWeatherInfo(woeid)
        responseFromAPI?.enqueue(object: Callback<CityWeatherInfo> {
            override fun onResponse(call: Call<CityWeatherInfo>, response: Response<CityWeatherInfo>) {
                cityWeatherInfoData.value = response?.body()
                //cityWeatherInfoLoading.value = false
                loading.value = false
                Log.e(TAG, "onResponse: Success")
            }
            override fun onFailure(call: Call<CityWeatherInfo>, t: Throwable) {
                errorMessage.value = "Oops, please check your internet connection."
                errorControl.value = true
                //cityWeatherInfoLoading.value = false
                loading.value = false
                Log.e(TAG, "onError: " + t.message)
            }
        })
    }

    /**
     * Response olarak gelen array'in doluluğunu kontrol eder ...
     */
    fun arrayControl(arr: List<NearLocation>?){
        if(arr == null){
            errorControl.value = true
            errorMessage.value = "Oops, Weather data currently unavailable."
        }
    }

    /**
     * Konum iznini kontrol eder. Eğer izin yoksa izin ister ve
     * verileri getirir, varsa verileri getirir ...
     */
    fun getPermissionForLocation(activity: FragmentActivity){
        locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        permissionControl = ContextCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION)

        if(permissionControl != PackageManager.PERMISSION_GRANTED){ // Not Permission
            ActivityCompat.
            requestPermissions(
                    activity as Activity, Array<String>(2){android.Manifest.permission.ACCESS_FINE_LOCATION},
                    100)
            var location = locationManager!!.getLastKnownLocation(LOCATION_PROVIDER)
            getLocation(location)
        }
        else{ // Already Permission
            var location = locationManager!!.getLastKnownLocation(LOCATION_PROVIDER)
            getLocation(location)
        }
    }

    /**
     * Gerekli izinden sonra konum getirme işini yapar ...
     */
    fun getLocation(location: Location?){
        if(location != null){
            onLocationChanged(location)
            errorControl.value = false
        }
        else{
            errorControl.value = true
            errorMessage.value = "Oops, please check your location settings"
        }
    }

    /**
     * GPS 'den gelen veriye göre enlem ve boylamı verip
     * API'dan şehirleri getirir ...
     */
    override fun onLocationChanged(location: Location) {
        var enlem = location.latitude
        var boylam = location.longitude
        getNearLocationDataFromAPI(enlem.toString()+","+boylam.toString())
    }

    fun updateConsolidatedWeatherObject(consolidatedWeather: ConsolidatedWeather?) : ConsolidatedWeather? {
        consolidatedWeather?.theTemp = consolidatedWeather?.theTemp.toString().split(".")?.get(0).toDouble()
        return consolidatedWeather
    }

}