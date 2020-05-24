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

    override fun getItemCount(): Int {
        return venueList.size
    }

    class ViewHolder(itemView: View, var venue: Venue? = null) : RecyclerView.ViewHolder(itemView) {

        companion object {
            val VENUE_KEY = "VENUE_KEY"
        }

        val imageView: ImageView = itemView.image_view
        val textView1: TextView = itemView.venue_text_view
        val textView2: TextView = itemView.country_text_view
        val textView3: TextView = itemView.weather_condition_text_view

        init {
            itemView.setOnClickListener {

                val intent = Intent(itemView.context, VenueActivity::class.java)

                intent.putExtra(VENUE_KEY, venue)

                itemView.context.startActivity(intent)
            }
        }
    }

}