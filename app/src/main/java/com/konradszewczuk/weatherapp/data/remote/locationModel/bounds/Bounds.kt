package com.konradszewczuk.weatherapp.data.remote.locationModel.bounds


data class Bounds(
        val northeast: Northeast,
        val southwest: Southwest
)

data class Northeast(
        val lat: Double, //51.6723432
        val lng: Double //0.148271
)

data class Southwest(
        val lat: Double, //51.38494009999999
        val lng: Double //-0.3514683
)