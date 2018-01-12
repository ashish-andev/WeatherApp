package com.konradszewczuk.weatherapp.utils

import java.util.regex.Pattern


object InputValidator {
    private val VALID_CITY_REGEX = Pattern.compile("[a-zA-Z]+")

    fun isValidCityInput(city: String): Boolean {
        val matches = VALID_CITY_REGEX.matcher(city).matches()
        return matches
    }
}