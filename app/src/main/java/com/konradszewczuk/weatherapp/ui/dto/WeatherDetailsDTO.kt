package com.konradszewczuk.weatherapp.ui.dto

import org.parceler.Parcel
import org.parceler.ParcelConstructor

@Parcel(Parcel.Serialization.BEAN)
data class WeatherDetailsDTO @ParcelConstructor constructor(val cityName: String, val weatherSummary: String?, val temperature: Double?, val humidity: Double?, val windSpeed: Double?, val cloudsPercentage: Double?, val weeklyDayWeahterList: ArrayList<WeeklyWeatherDTO>?)