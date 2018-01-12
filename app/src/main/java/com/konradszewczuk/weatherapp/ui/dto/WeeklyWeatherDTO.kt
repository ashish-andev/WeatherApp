package com.konradszewczuk.weatherapp.ui.dto

import org.parceler.Parcel
import org.parceler.ParcelConstructor

@Parcel(Parcel.Serialization.BEAN)
data class WeeklyWeatherDTO @ParcelConstructor constructor(val maxTemp: String, val minTemp: String, val dayOfWeek: String, val weatherType: String) {
}