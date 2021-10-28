package com.example.forecastappcent.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://www.metaweather.com/api/location/"
    private var retrofit: Retrofit? = null

    val client: Retrofit?
        get(){
            if(retrofit == null){
                retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
                    GsonConverterFactory.create()).build()
            }
            return retrofit
        }



}