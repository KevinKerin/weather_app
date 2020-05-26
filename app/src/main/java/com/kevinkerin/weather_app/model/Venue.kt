package com.kevinkerin.weather_app.model

import java.io.Serializable

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
    val _weatherLastUpdated: Long,
    var dateMainActivityString: String,
    var dateVenueActivityString: String,
    var imageResource: Int
) : Serializable