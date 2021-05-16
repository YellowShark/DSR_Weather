package ru.yellowshark.dsr_weather.ui.locations.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.yellowshark.dsr_weather.domain.model.Location

class LocationsAdapter(
    private val onLocationClickListener: (Location) -> Unit
) : RecyclerView.Adapter<LocationsViewHolder>() {

    private var data: ArrayList<Location> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationsViewHolder =
        LocationsViewHolder.create(parent)

    override fun onBindViewHolder(holder: LocationsViewHolder, position: Int) {
        holder.bind(data[position], onLocationClickListener)
    }

    override fun getItemCount(): Int = data.size

    fun addItem(location: Location) {
        data.add(location)
        notifyDataSetChanged()
    }

    fun clearData() {
        if (data.isNotEmpty()) {
            data.clear()
            notifyDataSetChanged()
        }
    }
}