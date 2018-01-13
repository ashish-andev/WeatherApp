package com.konradszewczuk.weatherapp.ui

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.location.Geocoder
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.konradszewczuk.weatherapp.R
import com.konradszewczuk.weatherapp.repository.WeatherViewModel

import android.widget.ArrayAdapter
import com.jakewharton.rxbinding2.widget.RxTextView
import com.konradszewczuk.weatherapp.repository.remote.weatherModel.WeatherResponse
import com.konradszewczuk.weatherapp.repository.room.CityEntity
import com.konradszewczuk.weatherapp.ui.dto.HourlyWeatherDTO
import com.konradszewczuk.weatherapp.ui.dto.WeatherDetailsDTO
import com.konradszewczuk.weatherapp.ui.dto.WeeklyWeatherDTO
import com.konradszewczuk.weatherapp.utils.InputValidator.isValidCityInput
import com.konradszewczuk.weatherapp.utils.StringDateFormatter.convertTimestampToDayOfTheWeek
import com.konradszewczuk.weatherapp.utils.StringDateFormatter.convertTimestampToHourFormat
import com.konradszewczuk.weatherapp.utils.WeatherMathUtils.convertToCelsius
import kotlinx.android.synthetic.main.activity_main.*
import org.parceler.Parcels
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: WeatherViewModel
    private var searchedCityNames = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)


        val itemInputNameObservable = RxTextView.textChanges(autocomplete_textView)
                .map { inputText: CharSequence -> inputText.isEmpty() || !isValidCityInput(inputText.toString()) }
                .distinctUntilChanged()

        itemInputNameObservable.subscribe { inputIsEmpty: Boolean ->
            Log.v("itemInputNameObservable", inputIsEmpty.toString())

            cityTextInputLayout.setError("Invalid input")
            cityTextInputLayout.setErrorEnabled(inputIsEmpty)

            cityButton?.isEnabled = !inputIsEmpty
        }

        cityButton.setOnClickListener {
            inputLinearLayout.isEnabled = false
            inputLinearLayout.alpha = 0.5f
            progressBar.visibility = View.VISIBLE

            val searchedCityName = autocomplete_textView.text.toString()

            val geocoder = Geocoder(this)
            val fromLocationName = geocoder.getFromLocationName(searchedCityName, 1)

            val address = fromLocationName.get(0)

            val latitude = address.latitude
            val longitude = address.longitude

            viewModel.getWeather(latitude, longitude).observeOn(AndroidSchedulers.mainThread()).subscribe { weatherResponse: WeatherResponse? ->
                inputLinearLayout.isEnabled = true
                inputLinearLayout.alpha = 1f
                progressBar.visibility = View.INVISIBLE

                val intent = Intent(this, WeatherDetailsActivity::class.java)
                val locationName = address.featureName + ", " + address.countryName
                intent.putExtra("tempBundle", Parcels.wrap(transform(locationName, weatherResponse)))

                startActivity(intent)

                if (!(searchedCityNames.contains(searchedCityName)))
                    viewModel.addCity(searchedCityName)
            }
        }

    }

    private fun transform(cityName: String, weatherResponse: WeatherResponse?): WeatherDetailsDTO {
        val temperatureFahrenheit: Double? = weatherResponse?.currently?.temperature
        val temperature = convertToCelsius(temperatureFahrenheit)
        val cloudCoverPercentage: Double? = weatherResponse?.currently?.cloudCover
        val weatherSummary = weatherResponse?.currently?.summary
        val windSpeed = weatherResponse?.currently?.windSpeed
        val humidity = weatherResponse?.currently?.humidity

        val weeklyWeatherList = ArrayList<WeeklyWeatherDTO>()
        weatherResponse?.daily?.data?.forEach {
            if (it.time.toLong() * 1000 > Date().time)
                weeklyWeatherList.add(WeeklyWeatherDTO(it.temperatureMax.toString(), it.temperatureMin.toString(), convertTimestampToDayOfTheWeek(it.time), it.icon))
        }

        val hourlyWeatherList = ArrayList<HourlyWeatherDTO>()
        weatherResponse?.hourly?.data?.forEach {
            hourlyWeatherList.add(HourlyWeatherDTO(it.time.toLong(), it.temperature))
        }


        //temperature for only next 24hours
        val hourlyWeatherStringFormatedHoursList = (0..24).mapTo(ArrayList<String>()) { convertTimestampToHourFormat(timestamp = hourlyWeatherList[it].timestamp, timeZone = weatherResponse?.timezone) }

        return WeatherDetailsDTO(
                cityName = cityName,
                weatherSummary = weatherSummary,
                temperature = temperature,
                windSpeed = windSpeed,
                humidity = humidity?.let { it * 100 },
                cloudsPercentage = cloudCoverPercentage?.let { it * 100 },
                weeklyDayWeahterList = weeklyWeatherList,
                hourlyWeatherList = hourlyWeatherList,
                hourlyWeatherStringFormatedHoursList =  hourlyWeatherStringFormatedHoursList
                )
    }

    override fun onStart() {
        super.onStart()
        viewModel.getCities()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { citiesList: List<CityEntity> ->
                    searchedCityNames.clear()
                    citiesList.forEach { searchedCityNames.add(it.cityName) }

                    val adapter = ArrayAdapter(this,
                            android.R.layout.simple_dropdown_item_1line, searchedCityNames)

                    autocomplete_textView.setAdapter(adapter)
                    autocomplete_textView.threshold = 0
                }
    }
}
