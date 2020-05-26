package com.kevinkerin.weather_app.model

class WeatherData(
    val ret: Boolean,
    val isOkay: Boolean,
    var data: List<Venue>
)