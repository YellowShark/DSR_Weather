package ru.yellowshark.dsr_weather.ui.locations.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.yellowshark.dsr_weather.databinding.ItemForecastBinding
import ru.yellowshark.dsr_weather.databinding.ItemLocationBinding
import ru.yellowshark.dsr_weather.domain.model.Location
import ru.yellowshark.dsr_weather.domain.model.ShortForecast
import ru.yellowshark.dsr_weather.ui.locations.forecast.ForecastViewHolder

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

    fun bind(location: Location) {
        with(binding) {
            itemLocationCity.text = location.city
            itemLocationTemperature.text = location.temp
        }
    }
}