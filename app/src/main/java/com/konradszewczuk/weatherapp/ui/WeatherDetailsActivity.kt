package com.konradszewczuk.weatherapp.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.konradszewczuk.weatherapp.R
import kotlinx.android.synthetic.main.activity_weather_details.*
import org.parceler.Parcels



class WeatherDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_details)
        val weatherDetails = Parcels.unwrap<WeatherDetailsDTO>(intent.getParcelableExtra("tempBundle")) as WeatherDetailsDTO

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = weatherDetails.cityName

        textViewCurrentTemperature.setText("%.2f".format(weatherDetails.temperature) + " \u2103") //TODO
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
