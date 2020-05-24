package com.example.weather_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var weatherData: WeatherData
    lateinit var adapter: MyAdapter
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

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, countryList)
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
                if(jsonResponseComplete){
                    if (position == 0 && venueList != null) {
                        filteredVenueList = venueList
                        updateView()
                        return
                    }
                    val selectedCountry = countryList[position]
                    filteredVenueList = venueList?.filter { it._country._name == selectedCountry }
                    val view = findViewById<RecyclerView>(R.id.recycler_view)
                    view.adapter = MyAdapter(filteredVenueList!!)
                }
            }
        }

        recycler_view.layoutManager = LinearLayoutManager(this)
        getJson()

    }

    fun getJson() {

        val request = Request.Builder().url(getString(R.string.SERVER_URL)).build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response?.body?.string()

                val gson = GsonBuilder().create()

                weatherData = gson.fromJson(body, WeatherData::class.java)

                venueList = weatherData.data
                filteredVenueList = venueList

                val dateFormatter: SimpleDateFormat = SimpleDateFormat("EEE MMM d yyyy, h:mm a")

                for (venue in venueList!!) {
                    if (venue._weatherConditionIcon != null) {
                        when (venue._weatherConditionIcon) {
                            "clear" -> venue.imageResource = R.drawable.ic_sun
                            "fog" -> venue.imageResource = R.drawable.ic_default
                            "cloudy" -> venue.imageResource = R.drawable.ic_cloud
                            "partlycloudy" -> venue.imageResource = R.drawable.ic_cloud
                            "rain" -> venue.imageResource = R.drawable.ic_rain
                            "snow" -> venue.imageResource = R.drawable.ic_default
                            "hazy" -> venue.imageResource = R.drawable.ic_default
                            else -> venue.imageResource = R.drawable.ic_default
                        }
                    }
                    if (venue._weatherTemp != null) {
                        venue._weatherTempInt = venue._weatherTemp.toInt()
                    }
                    if (venue._weatherLastUpdated != null) {
                        venue.dateString =
                            dateFormatter.format(Date(venue._weatherLastUpdated * 1000L))
                    }
                    val countryName = venue._country._name
                    if (!countryList.contains(countryName)) {
                        countryList.add(countryName)
                    }
                }

                runOnUiThread {
                    adapter = MyAdapter(venueList!!)
                    recycler_view.adapter = adapter
                    jsonResponseComplete = true
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Error")
            }
        })

    }

    fun refresh(view: View){
        getJson()
        spinner.setSelection(0)
        Toast.makeText(applicationContext,"App Refreshed",Toast.LENGTH_SHORT).show()
    }

    private fun updateView() {
        val view = findViewById<RecyclerView>(R.id.recycler_view)
        adapter = filteredVenueList?.let { MyAdapter(it) }!!
        view.adapter = adapter
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
    }

    fun sortByTemperature(view: View) {
        filteredVenueList =
            if (reverseToggle) filteredVenueList?.sortedBy { it._weatherTempInt } else filteredVenueList?.sortedByDescending { it._weatherTempInt }
        updateView()
    }

    fun sortByLastUpdated(view: View) {
        filteredVenueList =
            if (reverseToggle) filteredVenueList?.sortedBy { it._weatherLastUpdated } else filteredVenueList?.sortedByDescending { it._weatherLastUpdated }
        updateView()
    }


}