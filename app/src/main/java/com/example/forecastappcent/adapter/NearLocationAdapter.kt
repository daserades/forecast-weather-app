package com.example.forecastappcent.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.forecastappcent.R
import com.example.forecastappcent.databinding.NearLocationCardviewBinding
import com.example.forecastappcent.model.NearLocation
import com.example.forecastappcent.view.CityWeatherInfoFragment
import kotlinx.android.synthetic.main.near_location_cardview.view.*

class NearLocationAdapter(val mContext: Context, val nearLocationList: List<NearLocation>): RecyclerView.Adapter<NearLocationAdapter.ModelViewHolder>(){

    class ModelViewHolder(val binding: NearLocationCardviewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindItems(item: NearLocation, mContext: Context) {
            binding.cityName = item?.title

            itemView.nearLocationListCardView.setOnClickListener{
                val cityWeatherInfoFragment = CityWeatherInfoFragment()
                var bundle = Bundle()
                bundle.putString("woeid", item?.woeid.toString())
                cityWeatherInfoFragment.arguments = bundle
                (mContext as AppCompatActivity).supportFragmentManager.beginTransaction().replace(R.id.mainFrameLayout, cityWeatherInfoFragment).addToBackStack(null).commit()
            }
        }
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): NearLocationAdapter.ModelViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val nearLocationCardViewBinding = NearLocationCardviewBinding.inflate(layoutInflater, parent, false)
        return ModelViewHolder(nearLocationCardViewBinding)
    }

    override fun getItemCount(): Int {
        if(nearLocationList != null){
            return nearLocationList.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: NearLocationAdapter.ModelViewHolder, position: Int) {
        if(nearLocationList != null){
            nearLocationList[position]?.let { it -> holder.bindItems(it, mContext) }
        }
    }
}