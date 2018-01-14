package com.konradszewczuk.weatherapp

import com.konradszewczuk.weatherapp.data.repository.WeatherRepository
import com.konradszewczuk.weatherapp.ui.WeatherViewModel
import org.junit.Test

import org.junit.Assert.*
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class WeatherViewModelTest {

    @Mock
    private lateinit var weatherRepository: WeatherRepository

    private lateinit var viewModel: WeatherViewModel

}
