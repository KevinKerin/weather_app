package com.example.weather_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    lateinit var weatherData: WeatherData
    lateinit var venueList: List<Venue>
    lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolBar.title = "Tab Layout"
        setSupportActionBar(toolBar)

        val fragmentAdapter = MyPagerAdapter(supportFragmentManager)
        viewPager.adapter = fragmentAdapter

        tabLayout.setupWithViewPager(viewPager)

        recycler_view.layoutManager = LinearLayoutManager(this)
        getJson()

    }

    fun getJson() {

//        val url = "http://dnu5embx6omws.cloudfront.net/venues/weather.json"

        val request = Request.Builder().url(getString(R.string.SERVER_URL)).build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response?.body?.string()

                val gson = GsonBuilder().create()

                weatherData = gson.fromJson(body, WeatherData::class.java)

                venueList = weatherData.data

                for (venue in venueList) {
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
                    if(venue._weatherTemp != null){
                        venue._weatherTempInt = venue._weatherTemp.toInt()
                    }
                }

                runOnUiThread {
                    adapter = MyAdapter(weatherData)
                    recycler_view.adapter = adapter
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Error")
            }
        })

    }

    private fun updateView() {
        val view = findViewById<RecyclerView>(R.id.recycler_view)
        weatherData.data = venueList
        adapter = MyAdapter(weatherData)
        recycler_view.adapter?.notifyDataSetChanged()
    }

    fun sortAlphabetically(view: View){
        venueList = venueList.sortedBy{it._name}
        updateView()
    }


    fun sortAlphabeticallyDescending(view: View){
        venueList = venueList.sortedByDescending { it._name }
        updateView()
    }

    fun sortByTemperature(view: View){
        venueList = venueList.sortedBy { it._weatherTempInt }
        updateView()
    }

    fun sortByTemperatureDescending(view: View){
        venueList = venueList.sortedByDescending { it._weatherTempInt }
        updateView()
    }

    fun sortByLastUpdated(view: View){
        venueList = venueList.sortedBy { it._weatherTempInt }
        updateView()
    }

    fun sortByLastUpdatedDescending(view: View){
        venueList = venueList.sortedByDescending { it._weatherLastUpdated }
        updateView()
    }

}