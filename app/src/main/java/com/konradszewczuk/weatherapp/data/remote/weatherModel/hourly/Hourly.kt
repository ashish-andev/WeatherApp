package com.konradszewczuk.weatherapp.data.remote.weatherModel.hourly


data class Hourly(
        val summary: String, //Windy starting tomorrow morning and rain tomorrow afternoon.
        val icon: String, //rain
        val data: List<Data>
)

data class Data(
        val time: Int, //1515704400
        val summary: String, //Partly Cloudy
        val icon: String, //partly-cloudy-day
        val precipIntensity: Double, //0
        val precipProbability: Double, //0
        val temperature: Double, //51.53
        val apparentTemperature: Double, //51.53
        val dewPoint: Double, //45.36
        val humidity: Double, //0.79
        val pressure: Double, //1025.88
        val windSpeed: Double, //5.48
        val windGust: Double, //8.73
        val windBearing: Int, //214
        val cloudCover: Double, //0.53
        val uvIndex: Int, //0
        val visibility: Double, //8.89
        val ozone: Double //274.2
)