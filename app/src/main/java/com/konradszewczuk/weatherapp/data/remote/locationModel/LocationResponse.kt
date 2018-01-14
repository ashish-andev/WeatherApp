package com.konradszewczuk.weatherapp.data.remote.locationModel

import com.konradszewczuk.weatherapp.data.remote.locationModel.bounds.Bounds
import com.konradszewczuk.weatherapp.data.remote.locationModel.viewport.Viewport


data class LocationResponse(
        val results: List<Result>,
        val status: String //OK
)

data class Result(
        val address_components: List<AddressComponent>,
        val formatted_address: String, //Londyn, Wielka Brytania
        val geometry: Geometry,
        val place_id: String, //ChIJdd4hrwug2EcRmSrV3Vo6llI
        val types: List<String>
)

data class Geometry(
        val bounds: Bounds,
        val location: Location,
        val location_type: String, //APPROXIMATE
        val viewport: Viewport
)


data class Location(
        val lat: Double, //51.5073509
        val lng: Double //-0.1277583
)

data class AddressComponent(
        val long_name: String, //Londyn
        val short_name: String, //Londyn
        val types: List<String>
)