package com.konradszewczuk.weatherapp.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.konradszewczuk.weatherapp.R
import org.parceler.Parcels



class WeatherDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_details)

        val weatherDetails = Parcels.unwrap<WeatherDetailsDTO>(intent.getParcelableExtra("tempBundle")) as WeatherDetailsDTO



    }
}
