package com.example.weather_app

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi

class VenueActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_venue)

        val venue = intent.getSerializableExtra(MyAdapter.ViewHolder.VENUE_KEY) as? Venue

        val textView1: TextView = findViewById(R.id.venue_text_view)
        val textView2: TextView = findViewById(R.id.weather_condition_text_view)
        val textView3: TextView = findViewById(R.id.temperature_text_view)
        val textView4: TextView = findViewById(R.id.temperature_text_view)
        val textView5: TextView = findViewById(R.id.last_updated_text_view)
        val imageView: ImageView = findViewById(R.id.image_view)

        textView1.text = venue?._name
        textView2.text = venue?._country?._name
        textView3.text = venue?._weatherCondition
        textView4.text = venue?._weatherTemp + "°"
        textView5.text = "Last Updated: " + venue?.dateVenueActivityString
        venue?.imageResource?.let { imageView.setImageResource(it) }

    }
}