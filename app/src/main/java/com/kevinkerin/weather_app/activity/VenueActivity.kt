package activity

import adapter.WeatherAdapter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.kevinkerin.weather_app.R
import com.kevinkerin.weather_app.model.Venue

class VenueActivity : AppCompatActivity() {

    lateinit var textViewList: List<TextView>
    lateinit var stringList: List<String>
    lateinit var venueTextView: TextView
    lateinit var countryTextView: TextView
    lateinit var weatherConditionTextView: TextView
    lateinit var windTextView: TextView
    lateinit var humidityTextView: TextView
    lateinit var tempTextView: TextView
    lateinit var tempFeelsLikeTextView: TextView
    lateinit var lastUpdatedVenueTextView: TextView
    lateinit var imageView: ImageView

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_venue)

        val venue = intent.getSerializableExtra(WeatherAdapter.ViewHolder.VENUE_KEY) as? Venue

        venueTextView = findViewById(R.id.venue_text_view)
        countryTextView = findViewById(R.id.country_text_view)
        weatherConditionTextView = findViewById(R.id.temperature_text_view)
        windTextView = findViewById(R.id.wind_text_view)
        humidityTextView = findViewById(R.id.humidity_text_view)
        tempTextView = findViewById(R.id.temp_text_view)
        tempFeelsLikeTextView = findViewById(R.id.temp_feels_like_text_view)
        lastUpdatedVenueTextView = findViewById(R.id.last_updated_venue_text_view)
        imageView = findViewById(R.id.image_view)

        textViewList = listOf(
            venueTextView, countryTextView, weatherConditionTextView, windTextView,
            humidityTextView, tempTextView, tempFeelsLikeTextView, lastUpdatedVenueTextView
        )

        stringList = listOf(
            venue?._name,
            venue?._country?._name,
            venue?._weatherCondition,
            venue?._weatherWind,
            venue?._weatherHumidity,
            venue?._weatherTemp,
            venue?._weatherFeelsLike,
            venue?.dateVenueActivityString
        ) as List<String>

        for (view in textViewList) {
            var currentIndex: Int = textViewList.indexOf(view)
            setTextView(venue, view, stringList[currentIndex])
        }

        venue?.imageResource?.let { imageView.setImageResource(it) }

    }

    private fun setTextView(venue: Venue?, textView: TextView, string: String?) {
        if (string != null) {
            when (textView) {
                venueTextView -> venueTextView.text = string
                countryTextView -> countryTextView.text = string
                weatherConditionTextView -> weatherConditionTextView.text = string
                windTextView -> windTextView.text = string
                humidityTextView -> humidityTextView.text = string
                tempFeelsLikeTextView -> tempFeelsLikeTextView.text =
                    "Feels like: " + venue?._weatherFeelsLike + "°"
                tempTextView -> tempTextView.text = "Temperature: " + venue?._weatherTemp + "°"
                lastUpdatedVenueTextView -> lastUpdatedVenueTextView.text =
                    "Last Updated: " + venue?.dateVenueActivityString
            }
        } else {
            textView.text = " "
        }
    }

}