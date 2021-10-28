package com.example.forecastappcent.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.forecastappcent.R
import com.example.forecastappcent.databinding.ActivityMainBinding
import com.example.forecastappcent.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        viewModel.goToNearLocation(this)
        viewModel.internetCheck(this)
        getLiveData()

    }

    fun getLiveData(){
        viewModel.internetControlMessage.observe(this, Observer { control -> control?.let{
            binding.errorMessage = control
        } })
        viewModel.errorControl.observe(this, Observer { error -> error?.let{
            binding.errorControl = error
        } })
    }


}