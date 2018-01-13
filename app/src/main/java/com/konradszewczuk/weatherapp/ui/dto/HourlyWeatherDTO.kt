package com.konradszewczuk.weatherapp.ui.dto

import org.parceler.Parcel
import org.parceler.ParcelConstructor


@Parcel(Parcel.Serialization.BEAN)
data class HourlyWeatherDTO @ParcelConstructor constructor(val timestamp: Long, val temperature: Double)