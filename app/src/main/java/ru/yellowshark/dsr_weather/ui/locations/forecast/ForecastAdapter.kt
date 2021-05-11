package ru.yellowshark.dsr_weather.ui.locations.forecast

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.yellowshark.dsr_weather.domain.model.ShortForecast

class ForecastAdapter : RecyclerView.Adapter<ForecastViewHolder>() {
    var data: List<ShortForecast> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder =
        ForecastViewHolder.create(parent)

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}