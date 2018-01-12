package com.konradszewczuk.weatherapp.ui

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.location.Geocoder
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.konradszewczuk.weatherapp.R
import com.konradszewczuk.weatherapp.repository.WeatherViewModel

import android.widget.ArrayAdapter
import com.jakewharton.rxbinding2.widget.RxTextView
import com.konradszewczuk.weatherapp.repository.remote.weatherModel.WeatherResponse
import com.konradszewczuk.weatherapp.utils.InputValidator.isValidCityInput
import kotlinx.android.synthetic.main.activity_main.*
import org.parceler.Parcels
import io.reactivex.android.schedulers.AndroidSchedulers


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)

        //TODO remove - only for testing
        val adapter = ArrayAdapter(this,
                android.R.layout.simple_dropdown_item_1line, AUTOCOMPLETE)

        autocomplete_textView.setAdapter(adapter)
        autocomplete_textView.threshold = 0

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

            val geocoder = Geocoder(this)
            val fromLocationName = geocoder.getFromLocationName("London", 1)

            val address = fromLocationName.get(0)
            val latitude = address.latitude
            val longitude = address.longitude

            viewModel.getWeather(latitude,longitude).observeOn(AndroidSchedulers.mainThread()).subscribe { weatherResponse: WeatherResponse? ->


                val intent = Intent(this, WeatherDetailsActivity::class.java)
                val temperature: Double? = weatherResponse?.currently?.temperature
                val cloudCoverPercentage: Double? = weatherResponse?.currently?.cloudCover

                intent.putExtra("tempBundle", Parcels.wrap(WeatherDetailsDTO("Andy", temperature, cloudCoverPercentage)))

                startActivity(intent)
            }
        }

    }

    override fun onStart() {
        super.onStart()
    }

    //TODO remove - only for testing
    private val AUTOCOMPLETE = arrayOf("Belgium", "France", "Italy", "Germany", "Spain")
}
