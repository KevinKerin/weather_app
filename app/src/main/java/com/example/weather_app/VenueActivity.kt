package com.example.weather_app

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class VenueActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_venue)

//        val venueName = intent.getStringExtra(MyAdapter.ViewHolder.VENUE_NAME_KEY)
//        val venueId = intent.getStringExtra(MyAdapter.ViewHolder.VENUE_ID_KEY)
//        val venueCountryName = intent.getStringExtra(MyAdapter.ViewHolder.VENUE_COUNTRY_NAME_KEY)
//        val venueWeatherCondition = intent.getStringExtra(MyAdapter.ViewHolder.VENUE_WEATHER_CONDITION_KEY)
//        val venueWeatherConditionIcon = intent.getStringExtra(MyAdapter.ViewHolder.VENUE_WEATHER_CONDITION_ICON_KEY)
//        val venueWeatherWind = intent.getStringExtra(MyAdapter.ViewHolder.VENUE_WEATHER_WIND_KEY)
//        val venueWeatherHumidity = intent.getStringExtra(MyAdapter.ViewHolder.VENUE_WEATHER_HUMIDITY_KEY)
//        val venueWeatherTemp = intent.getStringExtra(MyAdapter.ViewHolder.VENUE_WEATHER_TEMP_KEY)
//        val venueWeatherFeelsLike = intent.getStringExtra(MyAdapter.ViewHolder.VENUE_WEATHER_FEELS_LIKE_KEY)
//        val venueWeatherLastUpdated = intent.getStringExtra(MyAdapter.ViewHolder.VENUE_WEATHER_LAST_UPDATED_KEY)
//        val venueImageResource = intent.getIntExtra(MyAdapter.ViewHolder.VENUE_IMAGE_RESOURCE)

        val venue = intent.getSerializableExtra(MyAdapter.ViewHolder.VENUE_KEY) as? Venue

        val textView1: TextView = findViewById(R.id.text_view_1)
        val textView2: TextView = findViewById(R.id.text_view_2)
        val textView3: TextView = findViewById(R.id.text_view_3)
        val textView4: TextView = findViewById(R.id.text_view_4)
        val textView5: TextView = findViewById(R.id.text_view_5)
        val imageView: ImageView = findViewById(R.id.image_view)

        textView1.text = venue?._name
        textView2.text = venue?._country?._name
        textView3.text = venue?._weatherCondition
        textView4.text = venue?._weatherTemp + "°"
        textView5.text = "Last Updated: " + venue?.dateString
        venue?.imageResource?.let { imageView.setImageResource(it) }

    }
}