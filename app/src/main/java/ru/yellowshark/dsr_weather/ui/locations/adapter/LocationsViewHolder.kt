package ru.yellowshark.dsr_weather.ui.locations.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.yellowshark.dsr_weather.databinding.ItemLocationBinding
import ru.yellowshark.dsr_weather.domain.model.Location

class LocationsViewHolder(
    private val binding: ItemLocationBinding
) : RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun create(parent: ViewGroup): LocationsViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemLocationBinding.inflate(inflater, parent, false)
            return LocationsViewHolder(binding)
        }
    }

    fun bind(location: Location, onLocationClickListener: (Location) -> Unit) {
        with(binding) {
            itemLocationCity.text = location.city
            itemLocationTemperature.text = "${location.temp} °C"
            root.setOnClickListener { onLocationClickListener(location) }
        }
    }
}