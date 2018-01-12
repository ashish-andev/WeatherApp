package com.konradszewczuk.weatherapp.ui

import org.parceler.Parcel
import org.parceler.ParcelConstructor

@Parcel(Parcel.Serialization.BEAN)
data class WeatherDetailsDTO @ParcelConstructor constructor(val cityName: String, val temperature: Double?, val cloudsPercentage: Double?)