package activity

import adapter.MyAdapter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.weather_app.R
import model.Venue

class VenueActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_venue)

        val venue = intent.getSerializableExtra(MyAdapter.ViewHolder.VENUE_KEY) as? Venue

        val venueTextView: TextView = findViewById(R.id.venue_text_view)
        val countryTextView: TextView = findViewById(R.id.country_text_view)
        val weatherConditionTextView: TextView = findViewById(R.id.temperature_text_view)
        val windTextView: TextView = findViewById(R.id.wind_text_view)
        val humidityTextView: TextView = findViewById(R.id.humidity_text_view)
        val tempTextView: TextView = findViewById(R.id.temp_text_view)
        val tempFeelsLikeTextView: TextView = findViewById(R.id.temp_feels_like_text_view)
        val lastUpdatedVenueTextView: TextView = findViewById(R.id.last_updated_venue_text_view)
        val imageView: ImageView = findViewById(R.id.image_view)

        venueTextView.text = venue?._name
        countryTextView.text = venue?._country?._name
        weatherConditionTextView.text = venue?._weatherCondition
        windTextView.text = venue?._weatherWind
        humidityTextView.text = venue?._weatherHumidity
        venue?.imageResource?.let { imageView.setImageResource(it) }
        if(venue?._weatherTemp != null){
            tempTextView.text = "Temperature: " + venue?._weatherTemp + "°"
        } else {
            tempTextView.text = " "
        }
        if(venue?._weatherTemp != null){
            tempFeelsLikeTextView.text = "Feels like: " + venue?._weatherFeelsLike + "°"
        } else {
            tempFeelsLikeTextView.text = " "
        }
        if(venue?._weatherLastUpdated != 0L){
            lastUpdatedVenueTextView.text = "Last Updated: " + venue?.dateVenueActivityString
        } else {
            lastUpdatedVenueTextView.text = " "
        }

    }
}