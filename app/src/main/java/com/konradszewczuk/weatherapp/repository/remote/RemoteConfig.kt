package com.konradszewczuk.weatherapp.repository.remote


class RemoteConfig {
    companion object {

        //const val BASE_API_LAYER = "http://api.openweathermap.org/data/2.5/"
        const val BASE_API_LAYER = "https://api.darksky.net/forecast/"
        const val ACCESS_KEY_API_LAYER = "22b94c02fb5392e4ed7e2b282aeef163"
        const val WEAHTER = "weather"
        const val BASE_URL = BASE_API_LAYER + ACCESS_KEY_API_LAYER + "/"

        // I should't expose the access key but it is to didactic use
//        const val ACCESS_KEY_API_LAYER = "8d407a6fc5f44a6c0527dfecdaf7d125"



    }
}