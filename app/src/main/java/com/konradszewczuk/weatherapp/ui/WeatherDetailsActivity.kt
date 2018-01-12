package com.konradszewczuk.weatherapp.ui

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.konradszewczuk.weatherapp.R
import com.konradszewczuk.weatherapp.ui.adapters.WeeklyWeatherAdapter
import com.konradszewczuk.weatherapp.ui.dto.WeatherDetailsDTO
import com.konradszewczuk.weatherapp.ui.dto.WeeklyWeatherDTO
import kotlinx.android.synthetic.main.activity_weather_details.*
import org.parceler.Parcels
import java.util.ArrayList


class WeatherDetailsActivity : AppCompatActivity() {

    private var weeklyWeatherList = ArrayList<WeeklyWeatherDTO>()
    private var adapter: WeeklyWeatherAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_details)
        val weatherDetails = Parcels.unwrap<WeatherDetailsDTO>(intent.getParcelableExtra("tempBundle"))

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = weatherDetails.cityName

        textViewCurrentTemperature.text = "%.2f".format(weatherDetails.temperature) + " \u2103" //TODO
        textViewWeatherSummary.text = weatherDetails.weatherSummary
        textViewHumidityValue.text = weatherDetails.humidity.toString() + "%"
        textViewWindSpeedValue.text = weatherDetails.windSpeed.toString() + "m/s"
        textViewCloudCoverageValue.text = weatherDetails.cloudsPercentage.toString() + "%"

        weatherDetails.temperature?.let { processTemperatureText(it) }

        weeklyWeatherList = weatherDetails.weeklyDayWeahterList as ArrayList<WeeklyWeatherDTO>
        adapter = WeeklyWeatherAdapter(weeklyWeatherList)

        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerViewWeeklyWeather.setLayoutManager(mLayoutManager)
        recyclerViewWeeklyWeather.setItemAnimator(DefaultItemAnimator())
        recyclerViewWeeklyWeather.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerViewWeeklyWeather.setAdapter(adapter)

    }

    private fun processTemperatureText(temperature: Double) {
        when {
            temperature < 10 -> textViewCurrentTemperature.setTextColor(Color.BLUE)
            temperature in 10..20 -> textViewCurrentTemperature.setTextColor(Color.BLACK)
            temperature > 20 -> textViewCurrentTemperature.setTextColor(Color.RED)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
