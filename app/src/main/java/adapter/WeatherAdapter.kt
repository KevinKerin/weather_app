package adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_app.R
import activity.VenueActivity
import kotlinx.android.synthetic.main.item.view.*
import model.Venue

class WeatherAdapter(private val venueList: List<Venue>) : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = venueList[position]

        holder.imageView.setImageResource(currentItem.imageResource)
        holder.venueTextView.text = currentItem._name
        if(currentItem._weatherConditionIcon != null){
            holder.weatherConditionTextView.text = currentItem._weatherCondition

        } else {
            holder.weatherConditionTextView.text = "No information available"
        }
        if(currentItem._weatherTemp != null){
            holder.temperatureTextView.text = currentItem._weatherTempInt.toString() + "°"
            holder.lastUpdatedListTextView.text = "Updated " + currentItem.dateMainActivityString
        } else {
            holder.temperatureTextView.text = null
            holder.lastUpdatedListTextView.text = " "
        }
        holder?.venue = currentItem
    }

    override fun getItemCount(): Int {
        return venueList.size
    }

    class ViewHolder(itemView: View, var venue: Venue? = null) : RecyclerView.ViewHolder(itemView) {

        companion object {
            val VENUE_KEY = "VENUE_KEY"
        }

        val imageView: ImageView = itemView.image_view
        val venueTextView: TextView = itemView.venue_text_view
        val weatherConditionTextView: TextView = itemView.country_text_view
        val temperatureTextView: TextView = itemView.temperature_text_view
        val lastUpdatedListTextView: TextView = itemView.last_updated_list_text_view

        init {
            itemView.setOnClickListener {

                val intent = Intent(itemView.context, VenueActivity::class.java)

                intent.putExtra(VENUE_KEY, venue)

                itemView.context.startActivity(intent)
            }
        }
    }

}