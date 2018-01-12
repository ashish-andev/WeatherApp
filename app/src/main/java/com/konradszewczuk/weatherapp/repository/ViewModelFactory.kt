package com.konradszewczuk.weatherapp.repository

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider


class ViewModelFactory() : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}