package com.example.weather_app

class Venue(
    val _venueId: String,
    val _name: String,
    val _country: Country,
    val _weatherCondition: String,
    val _weatherConditionIcon: String,
    val _weatherWind: String,
    val _weatherHumidity: String,
    val _weatherTemp: String,
    var _weatherTempInt: Int,
    val _weatherFeelsLike: String,
    val _sport: Sport,
    val _weatherLastUpdated: String,
    var imageResource: Int
)