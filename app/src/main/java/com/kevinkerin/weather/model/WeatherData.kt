package com.kevinkerin.weather.model

class WeatherData(
    val ret: Boolean,
    val isOkay: Boolean,
    var data: List<Venue>
)