package com.konradszewczuk.weatherapp.data.remote.weatherModel.minutely


data class Minutely(
        val summary: String, //Mostly cloudy for the hour.
        val icon: String, //partly-cloudy-night
        val data: List<Data>
)

data class Data(
        val time: Int, //1515707940
        val precipIntensity: Double, //0
        val precipProbability: Double //0
)