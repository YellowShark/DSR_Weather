package ru.yellowshark.dsr_weather.ui.locations.forecast

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import ru.yellowshark.dsr_weather.R
import ru.yellowshark.dsr_weather.databinding.FragmentForecastBinding
import ru.yellowshark.dsr_weather.utils.WEATHER_ICON_URL_POSTFIX
import ru.yellowshark.dsr_weather.utils.WEATHER_ICON_URL_PREFIX

@AndroidEntryPoint
class ForecastFragment : Fragment(R.layout.fragment_forecast) {
    private val binding: FragmentForecastBinding by viewBinding()
    private val viewModel by lazy { ViewModelProvider(requireActivity()).get(ForecastViewModel::class.java) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getForecast("Воронеж")
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.forecast.observe(viewLifecycleOwner) {
            with(binding) {
                Glide.with(requireContext())
                    .load("$WEATHER_ICON_URL_PREFIX${it.icon}$WEATHER_ICON_URL_POSTFIX")
                    .into(forecastIconImage)

                forecastDateText.text = it.date
                forecastCityText.text = it.cityName
                forecastDescText.text = it.description
                forecastTemperatureText.text = it.temperature
                forecastWindText.text = it.wind
                forecastPressureText.text = it.pressure
                forecastHumidityText.text = it.humidity
            }
        }
    }
}