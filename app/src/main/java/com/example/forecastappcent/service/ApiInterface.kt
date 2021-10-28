package com.example.forecastappcent.service

import com.example.forecastappcent.model.CityWeatherInfo
import com.example.forecastappcent.model.NearLocation
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    // Yakındaki şehirleri getirir ...
    @GET("search/?")
    fun getNearLocation(@Query("lattlong") location:String) : Call<List<NearLocation>>

    // Bulunan şehirdeki hava durumu bilgilerini getirir ...
    @GET("{woeid}")
    fun getWeatherInfo(@Path("woeid") woeid:String) : Call<CityWeatherInfo>
}