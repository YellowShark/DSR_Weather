package ru.yellowshark.dsr_weather.ui.locations.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import ru.yellowshark.dsr_weather.R
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

    fun bind(
        position: Int,
        location: Location,
        onLocationClickListener: (Location) -> Unit,
        onStarClickListener: (Int, Int, Boolean) -> Unit
    ) {
        with(binding) {
            root.context.let { context ->
                itemLocationCity.text = location.city
                itemLocationTemperature.text =
                    "${context.getString(R.string.curr_temp)}: ${location.temp}"
                if (location.hasNextDayForecast)
                    itemLocationFutureTemperature.apply {
                        isVisible = true
                        text = "${context.getString(R.string.future_temp)}: ${location.futureTemp}"
                    }
                root.setOnClickListener { onLocationClickListener(location) }
                itemLocationFavBtn.apply {
                    setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            if (location.isFavorite) R.drawable.ic_fill_star else R.drawable.ic_outline_star
                        )
                    )
                    setOnClickListener {
                        val isFav = !location.isFavorite
                        itemLocationFavBtn.setImageDrawable(
                            ContextCompat.getDrawable(
                                context,
                                if (isFav) R.drawable.ic_fill_star else R.drawable.ic_outline_star
                            )
                        )
                        onStarClickListener(position, location.id, isFav)
                    }
                }
            }
        }
    }
}