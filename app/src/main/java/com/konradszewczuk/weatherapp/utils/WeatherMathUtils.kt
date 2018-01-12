package com.konradszewczuk.weatherapp.utils

/**
 * Created by Admin on 2018-01-12.
 */
object WeatherMathUtils {

    fun convertToCelsius(temperatureFahrenheit: Double?): Double? =
            if (temperatureFahrenheit != null)
                ((temperatureFahrenheit - 32) * 5) / 9
            else null
}