package com.example.weather_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item.view.*

class MyAdapter(private val data: WeatherData) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = data.data[position]

        holder.imageView.setImageResource(currentItem.imageResource)
        holder.textView1.text = currentItem._name
        holder.textView2.text = currentItem._weatherCondition
    }

    override fun getItemCount(): Int{
        return data.data.size
    }

class ViewHolder(itemView: View ) : RecyclerView.ViewHolder(itemView) {
    val imageView: ImageView = itemView.image_view
    val textView1: TextView = itemView.text_view_1
    val textView2: TextView = itemView.text_view_2
}

}