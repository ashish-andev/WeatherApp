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
import com.konradszewczuk.weatherapp.utils.InputValidator.isValidCityInput
import kotlinx.android.synthetic.main.activity_main.*
import org.parceler.Parcels
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: WeatherViewModel
    private var searchedCityNames  = ArrayList<String>()

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

            viewModel.getWeather(latitude,longitude).observeOn(AndroidSchedulers.mainThread()).subscribe { weatherResponse: WeatherResponse? ->
                //TODO format DTO in ViewModel, refactor

                inputLinearLayout.isEnabled = true
                inputLinearLayout.alpha = 1f
                progressBar.visibility = View.INVISIBLE

                val intent = Intent(this, WeatherDetailsActivity::class.java)
                val temperatureFahrenheit: Double? = weatherResponse?.currently?.temperature
                val temperature = convertToCelsius(temperatureFahrenheit)
                val cloudCoverPercentage: Double? = weatherResponse?.currently?.cloudCover

                intent.putExtra("tempBundle", Parcels.wrap(WeatherDetailsDTO(address.featureName + ", " + address.countryName, temperature, cloudCoverPercentage)))

                startActivity(intent)


                if(!(searchedCityNames.contains(searchedCityName)))
                    viewModel.addCity(searchedCityName)
            }
        }

    }

    private fun convertToCelsius(temperatureFahrenheit: Double?): Double? =
            if(temperatureFahrenheit != null)
                ((temperatureFahrenheit - 32)*5)/9
            else null


    override fun onStart() {
        super.onStart()
        viewModel.getCities()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {  citiesList: List<CityEntity> ->
                    searchedCityNames.clear()
                    citiesList.forEach { searchedCityNames.add(it.cityName) }

                    val adapter = ArrayAdapter(this,
                            android.R.layout.simple_dropdown_item_1line, searchedCityNames)

                    autocomplete_textView.setAdapter(adapter)
                    autocomplete_textView.threshold = 0
                }
    }
}
