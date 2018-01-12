package com.konradszewczuk.weatherapp.repository

import android.arch.lifecycle.ViewModel
import com.konradszewczuk.weatherapp.di.WeatherApplication
import com.konradszewczuk.weatherapp.repository.remote.RemoteWeatherDataSource
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class WeatherViewModel: ViewModel() {

    @Inject lateinit var remoteCurrencyDataSource: RemoteWeatherDataSource

    init {
        initializeDagger()
    }

    fun getWeather(latitude: Double, longitude: Double) = remoteCurrencyDataSource.requestWeatherForCity(latitude.toString(), longitude.toString()).subscribeOn(Schedulers.computation())

    private fun initializeDagger() = WeatherApplication.appComponent.inject(this)

}