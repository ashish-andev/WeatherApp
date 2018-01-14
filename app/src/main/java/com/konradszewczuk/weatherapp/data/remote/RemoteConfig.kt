package com.konradszewczuk.weatherapp.data.remote


class RemoteConfig {
    companion object {
        const val BASE_API_LAYER = "https://api.darksky.net/forecast/"
        const val ACCESS_KEY_API_LAYER = "22b94c02fb5392e4ed7e2b282aeef163"
        const val BASE_URL = BASE_API_LAYER + ACCESS_KEY_API_LAYER + "/"
        const val GEOCODING_BASE_URL = "https://maps.googleapis.com/maps/api/geocode/"
    }
}