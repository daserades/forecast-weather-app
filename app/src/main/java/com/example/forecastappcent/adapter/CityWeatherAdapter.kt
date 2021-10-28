package com.example.forecastappcent.adapter

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.forecastappcent.R
import com.example.forecastappcent.databinding.CityWeatherCardviewBinding
import com.example.forecastappcent.model.ConsolidatedWeather
import com.example.forecastappcent.util.Constant
import com.example.forecastappcent.util.DayOfWeek
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.city_weather_cardview.view.*
import kotlinx.android.synthetic.main.near_location_cardview.view.*
import java.util.*

class CityWeatherAdapter(val mContext: Context, val weatherList: List<ConsolidatedWeather>?) : RecyclerView.Adapter<CityWeatherAdapter.ModelViewHolder>() {
    class ModelViewHolder(val binding: CityWeatherCardviewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindItems(item: ConsolidatedWeather, mContext: Context) {
            binding.day = DayOfWeek.findDayOfWeek(item?.applicableDate.toString())
            binding.degree = item?.theTemp.toString().split(".").get(0)
            Picasso.get().load(Constant.IMAGE_URL + item?.weatherStateAbbr+".png").into(itemView.imageViewWeatherCardView)
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cityWeatherCardViewBinding = CityWeatherCardviewBinding.inflate(layoutInflater, parent, false)
        return ModelViewHolder(cityWeatherCardViewBinding)
    }

    override fun onBindViewHolder(holder: ModelViewHolder, position: Int) {
        if(weatherList != null){
            weatherList[position]?.let { holder.bindItems(it, mContext) }
        }
    }

    override fun getItemCount(): Int {
        if(weatherList != null){
            return weatherList.size
        }
        return 0
    }

}

