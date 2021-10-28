package com.example.forecastappcent.view

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.forecastappcent.R
import com.example.forecastappcent.adapter.CityWeatherAdapter
import com.example.forecastappcent.databinding.FragmentCityWeatherFragmentBinding
import com.example.forecastappcent.viewmodel.CityWeatherInfoFragmentViewModel

class CityWeatherInfoFragment : Fragment() {
    private lateinit var binding : FragmentCityWeatherFragmentBinding
    private lateinit var viewModel : CityWeatherInfoFragmentViewModel
    private lateinit var cityWeatherAdapter: CityWeatherAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_city_weather_fragment, container, false)
        viewModel = ViewModelProvider(this).get(CityWeatherInfoFragmentViewModel::class.java)

        viewModel.internetCheck(activity as Activity)

        val woeid = viewModel!!.getArguments(this)
        woeid?.let{it -> viewModel!!.getWeatherInfoDataFromAPI(it)}
        activity?.let { it -> getLiveData(it) }

        return binding.root
    }

    fun getLiveData(activity : FragmentActivity){
        viewModel!!.cityWeatherInfoData.observe(activity, Observer { data -> data?.let {
            cityWeatherAdapter = CityWeatherAdapter(activity, data.consolidatedWeather)
            binding.cityWeatherAdapter = cityWeatherAdapter

            val cityWeatherInfo = data
            val consolidatedWeather = data.consolidatedWeather?.get(0)
            binding.cityName = cityWeatherInfo?.title
            binding.theTemp = consolidatedWeather?.theTemp.toString().split(".").get(0)
            binding.minMaxTemp= "Min:" + consolidatedWeather?.minTemp.toString().split(".").get(0) + "º" + " " + "Max:" + consolidatedWeather?.maxTemp.toString().split(".").get(0) + "º"
            binding.predictability = consolidatedWeather?.predictability.toString().split(".").get(0)
            binding.visibility = consolidatedWeather?.visibility.toString().split(".").get(0)
            binding.humidity = consolidatedWeather?.humidity.toString().split(".").get(0)
            binding.airPressure = consolidatedWeather?.airPressure.toString().split(".").get(0)
            binding.windDirection = consolidatedWeather?.windDirection.toString().split(".").get(0)
            binding.weatherStateName = consolidatedWeather?.weatherStateName.toString().split(".").get(0)
            binding.windSpeed = consolidatedWeather?.windSpeed.toString().split(".").get(0)
        } })

        viewModel!!.loading.observe(activity, Observer{ loading -> loading?.let{
            binding.progressBarControl = loading
        }})

        viewModel.errorMessage.observe(this, Observer { control -> control?.let{
            binding.errorMessage = control
        } })
        viewModel.errorControl.observe(this, Observer { error -> error?.let{
            binding.errorControl = error
        } })

    }
}