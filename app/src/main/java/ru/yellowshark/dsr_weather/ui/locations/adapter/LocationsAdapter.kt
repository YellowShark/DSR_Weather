package ru.yellowshark.dsr_weather.ui.locations.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.yellowshark.dsr_weather.domain.model.Location
import ru.yellowshark.dsr_weather.domain.model.ShortForecast

class LocationsAdapter : RecyclerView.Adapter<LocationsViewHolder>() {
    var data: ArrayList<Location> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationsViewHolder =
        LocationsViewHolder.create(parent)

    override fun onBindViewHolder(holder: LocationsViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    fun addItem(location: Location) {
        data.add(location)
        notifyDataSetChanged()
    }
}