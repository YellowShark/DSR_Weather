package ru.yellowshark.dsr_weather.ui.locations.forecast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.yellowshark.dsr_weather.databinding.FragmentForecastBinding
import ru.yellowshark.dsr_weather.databinding.ItemForecastBinding
import ru.yellowshark.dsr_weather.domain.model.ShortForecast

class ForecastViewHolder(
    private val binding: ItemForecastBinding
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(parent: ViewGroup): ForecastViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemForecastBinding.inflate(inflater, parent, false)
            return ForecastViewHolder(binding)
        }
    }

    fun bind(forecast: ShortForecast) {
        with(binding) {
            Glide.with(binding.root)
                .load(forecast.icon)
                .fitCenter()
                .into(itemForecastIconImage)

            itemForecastTimeText.text = forecast.time
            itemForecastTempText.text = forecast.temp
            itemForecastHumidityText.text = forecast.humidity
        }
    }
}