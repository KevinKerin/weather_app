package com.example.weather_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getJson()

    }

    fun getJson(){

        val url = "http://dnu5embx6omws.cloudfront.net/venues/weather.json"

        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response?.body?.string()
                println(body)

                val gson = GsonBuilder().create()

                val data = gson.fromJson(body, Data::class.java)

            }
            override fun onFailure(call: Call, e: IOException) {
                println("Error")
            }
        })

    }

}

class Data(val ret: Boolean, val isOkay: Boolean, val data: List<City>)

class City(val _venueId: String, val _name: String, val _country: Country, val _weatherCondition: String, val  _weatherConditionIcon: String, val _weatherWind: String,
           val _weatherHumidity: String, val _weatherTemp: String, val _weatherFeelsLike: String, val _sport: Sport, val _weatherLastUpdated: String)

class Country(val _countryID: String, val _name: String)

class Sport(val _sportID: String, val _description: String)