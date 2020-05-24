package com.example.weather_app

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item.view.*

class MyAdapter(private val venueList: List<Venue>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = venueList[position]

        holder.imageView.setImageResource(currentItem.imageResource)
        holder.textView1.text = currentItem._name
        holder.textView2.text = currentItem._weatherCondition
        holder.textView3.text = currentItem._weatherTempInt.toString()

        holder?.venue = currentItem
    }

    override fun getItemCount(): Int{
        return venueList.size
    }

class ViewHolder(itemView: View, var venue: Venue? = null) : RecyclerView.ViewHolder(itemView) {

    companion object {
        val VENUE_NAME_KEY = "VENUE_NAME"
        val VENUE_ID_KEY = "VENUE_ID"
        val VENUE_COUNTRY_NAME_KEY = "VENUE_COUNTRY_NAME_KEY"
        val VENUE_WEATHER_CONDITION_KEY = "VENUE_WEATHER_CONDITION_KEY"
        val VENUE_WEATHER_CONDITION_ICON_KEY = "VENUE_WEATHER_CONDITION_ICON_KEY"
        val VENUE_WEATHER_WIND_KEY = "VENUE_WEATHER_WIND_KEY"
        val VENUE_WEATHER_HUMIDITY_KEY = "VENUE_WEATHER_HUMIDITY_KEY"
        val VENUE_WEATHER_TEMP_KEY = "VENUE_WEATHER_TEMP_KEY"
        val VENUE_WEATHER_FEELS_LIKE_KEY = "VENUE_WEATHER_FEELS_LIKE_KEY"
        val VENUE_WEATHER_LAST_UPDATED_KEY = "VENUE_WEATHER_LAST_UPDATED_KEY"
    }

    val imageView: ImageView = itemView.image_view
    val textView1: TextView = itemView.text_view_1
    val textView2: TextView = itemView.text_view_2
    val textView3: TextView = itemView.text_view_3

    init {
        itemView.setOnClickListener {

            val intent =  Intent(itemView.context, VenueActivity::class.java)

//            intent.putStringArrayListExtra()
            intent.putExtra(VENUE_NAME_KEY, venue?._name)
            intent.putExtra(VENUE_ID_KEY, venue?._venueId)
            intent.putExtra(VENUE_COUNTRY_NAME_KEY, venue?._country?._name)
            intent.putExtra(VENUE_WEATHER_CONDITION_KEY, venue?._weatherCondition)
            intent.putExtra(VENUE_WEATHER_CONDITION_ICON_KEY, venue?._weatherConditionIcon)
            intent.putExtra(VENUE_WEATHER_WIND_KEY, venue?._weatherWind)
            intent.putExtra(VENUE_WEATHER_HUMIDITY_KEY, venue?._weatherHumidity)
            intent.putExtra(VENUE_WEATHER_TEMP_KEY, venue?._weatherTemp)
            intent.putExtra(VENUE_WEATHER_FEELS_LIKE_KEY, venue?._weatherFeelsLike)
            intent.putExtra(VENUE_WEATHER_LAST_UPDATED_KEY, venue?._weatherLastUpdated)


            itemView.context.startActivity(intent)
        }
    }

}

}