package activity

import adapter.WeatherAdapter
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.animation.RotateAnimation
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_app.R
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import model.Venue
import model.WeatherData
import okhttp3.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    lateinit var weatherData: WeatherData
    lateinit var adapter: WeatherAdapter
    lateinit var aToZButton: Button
    lateinit var tempButton: Button
    lateinit var lastUpdatedButton: Button
    lateinit var buttonList: List<Button>

    var jsonResponseComplete = false
    var venueList: List<Venue>? = null
    var filteredVenueList: List<Venue>? = null
    var countryList: MutableList<String> = ArrayList<String>()
    var reverseToggle: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var spinner: Spinner = findViewById(R.id.spinner)
        countryList.add("All")

        aToZButton = findViewById(R.id.a_to_z_button)
        tempButton = findViewById(R.id.temp_button)
        lastUpdatedButton = findViewById(R.id.last_updated_button)
        buttonList = listOf(aToZButton, tempButton, lastUpdatedButton)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, countryList)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (jsonResponseComplete) {
                    if (position == 0 && venueList != null) {
                        filteredVenueList = venueList
                        updateView()
                        return
                    }
                    val selectedCountry = countryList[position]
                    filteredVenueList = venueList?.filter { it._country._name == selectedCountry }
                    val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
                    recyclerView.adapter = WeatherAdapter(filteredVenueList!!)
                }
            }
        }

        recycler_view.layoutManager = LinearLayoutManager(this)
        getJson()

    }

    fun getJson() {

        val request = Request.Builder().url(getString(R.string.server_url)).build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()

                val gson = GsonBuilder().create()

                weatherData = gson.fromJson(body, WeatherData::class.java)

                venueList = weatherData.data
                filteredVenueList = venueList

                val venueActivityFormatter =
                    SimpleDateFormat("EEE MMM d yyyy, h:mm a")
                val mainActivityFormatter = SimpleDateFormat("hh:mm MMM d yyyy")

                for (venue in venueList!!) {
                    if (venue._weatherConditionIcon != null) {
                        when (venue._weatherConditionIcon) {
                            "clear" -> venue.imageResource =
                                R.drawable.clear
                            "fog" -> venue.imageResource =
                                R.drawable.fog
                            "cloudy", "mostlycloudy" -> venue.imageResource =
                                R.drawable.cloudy
                            "partlycloudy" -> venue.imageResource =
                                R.drawable.partlycloudy
                            "tstorms" -> venue.imageResource =
                                R.drawable.storm
                            "rain" -> venue.imageResource =
                                R.drawable.rain
                            "snow" -> venue.imageResource =
                                R.drawable.snow
                            "hazy" -> venue.imageResource =
                                R.drawable.hazy
                            else -> venue.imageResource =
                                R.drawable.clear
                        }
                    }
                    if (venue._weatherTemp != null) {
                        venue._weatherTempInt = venue._weatherTemp.toInt()
                    }
                    if (venue._weatherLastUpdated != 0L) {
                        venue.dateVenueActivityString =
                            venueActivityFormatter.format(Date(venue._weatherLastUpdated * 1000L))
                        venue.dateMainActivityString =
                            mainActivityFormatter.format(Date(venue._weatherLastUpdated * 1000L))
                    }
                    val countryName = venue._country._name
                    if (!countryList.contains(countryName)) {
                        countryList.add(countryName)
                    }
                }

                runOnUiThread {
                    adapter = WeatherAdapter(venueList!!)
                    recycler_view.adapter = adapter
                    jsonResponseComplete = true
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Error")
            }
        })

    }

    fun refresh(view: View) {
        val image: ImageView = findViewById(R.id.refresh)
        image.clearAnimation()
        val anim =
            RotateAnimation(0F, 360F, (image.width / 2).toFloat(),
                (image.height / 2).toFloat()
            )
        anim.fillAfter = true
        anim.repeatCount = 0
        anim.duration = 1000
        image.startAnimation(anim)


        setButtonAsActive(null)
        getJson()
        spinner.setSelection(0)
        Toast.makeText(applicationContext, "App Refreshed", Toast.LENGTH_SHORT).show()
    }

    fun setButtonAsActive(button: Button?){
        for (currentButton in buttonList){
            if (button == currentButton){
                currentButton.setBackgroundColor(Color.BLACK)
                currentButton.setTextColor(Color.WHITE)
            } else {
                currentButton.setBackgroundColor(Color.WHITE)
                currentButton.setTextColor(Color.BLACK)
            }
        }
    }

    private fun updateView() {
        val updatedView = findViewById<RecyclerView>(R.id.recycler_view)
        adapter = filteredVenueList?.let { WeatherAdapter(it) }!!
        updatedView.adapter = adapter
    }

    fun reverseOrder(view: View) {
        reverseToggle = !reverseToggle
        filteredVenueList = filteredVenueList?.reversed()
        updateView()
    }

    fun sortAlphabetically(view: View) {
        filteredVenueList =
            if (reverseToggle) filteredVenueList?.sortedByDescending { it._name } else filteredVenueList?.sortedBy { it._name }
        updateView()
        setButtonAsActive(aToZButton)
    }

    fun sortByTemperature(view: View) {
        filteredVenueList =
            if (reverseToggle) filteredVenueList?.sortedBy { it._weatherTempInt } else filteredVenueList?.sortedByDescending { it._weatherTempInt }
        updateView()
        setButtonAsActive(tempButton)
    }

    fun sortByLastUpdated(view: View) {
        filteredVenueList =
            if (reverseToggle) filteredVenueList?.sortedBy { it._weatherLastUpdated } else filteredVenueList?.sortedByDescending { it._weatherLastUpdated }
        updateView()
        setButtonAsActive(lastUpdatedButton)
    }


}