package com.example.forecastappcent.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.forecastappcent.R
import com.example.forecastappcent.adapter.NearLocationAdapter
import com.example.forecastappcent.databinding.FragmentNearLocationBinding
import com.example.forecastappcent.util.Constant
import com.example.forecastappcent.viewmodel.NearLocationFragmentViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_near_location.*

class NearLocationFragment() : Fragment(){
    private lateinit var binding : FragmentNearLocationBinding
    private lateinit var viewModel: NearLocationFragmentViewModel
    private lateinit var nearLocationAdapter: NearLocationAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_near_location, container, false)
        viewModel = ViewModelProvider(this).get(NearLocationFragmentViewModel::class.java)

        activity?.let { it-> viewModel.getPermissionForLocation(it) }
        activity?.let { it -> getLiveData(it) }

        return binding.root
    }

    private fun getLiveData(activity: FragmentActivity){
        viewModel!!.nearLocationData.observe(activity, Observer { data -> data?.let {
            nearLocationAdapter = NearLocationAdapter(activity, data)
            binding.nearLocationAdapter = nearLocationAdapter
            viewModel!!.getWeatherInfoDataFromAPI(data[0]?.woeid.toString())
        }})
        viewModel!!.loading.observe(activity, Observer { loading -> loading?.let {
            binding.progressBarControl = loading
        }})

        viewModel!!.errorMessage.observe(activity, Observer { errorMessage -> errorMessage?.let{
            binding.errorMessage = errorMessage
        } })
        viewModel!!.errorControl.observe(activity, Observer { errorControl -> errorControl?.let{
            binding.errorControl = errorControl
            Log.e("geldiii", errorControl.toString())
        } })
        viewModel!!.cityWeatherInfoData.observe(activity, Observer { data -> data?.let{
            val cityName = data?.title
            val consolidateWeather = data?.consolidatedWeather?.get(0)
            binding.cityName = cityName
            binding.theTemp = consolidateWeather?.theTemp.toString().split(".")?.get(0)
            binding.weatherState = consolidateWeather?.weatherStateName.toString()
            Picasso.get().load(Constant.IMAGE_URL + consolidateWeather?.weatherStateAbbr+".png").into(binding.imageViewWeatherImage)
        }})
        viewModel!!.cityWeatherInfoLoading.observe(activity, Observer { loading -> loading?.let{
            binding.progressBarControlForWeatherInfo = loading
        }})

    }
}