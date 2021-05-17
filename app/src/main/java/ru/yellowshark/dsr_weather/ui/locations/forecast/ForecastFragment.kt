package ru.yellowshark.dsr_weather.ui.locations.forecast

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.jjoe64.graphview.GridLabelRenderer
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import dagger.hilt.android.AndroidEntryPoint
import ru.yellowshark.dsr_weather.R
import ru.yellowshark.dsr_weather.databinding.FragmentForecastBinding
import ru.yellowshark.dsr_weather.domain.model.ShortForecast


@AndroidEntryPoint
class ForecastFragment : Fragment(R.layout.fragment_forecast) {
    private val binding: FragmentForecastBinding by viewBinding()
    private val viewModel by lazy { ViewModelProvider(requireActivity()).get(ForecastViewModel::class.java) }
    private val forecastAdapter: ForecastAdapter by lazy { ForecastAdapter() }
    private val args: ForecastFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRv()
        observeViewModel()
        uploadForecast()
    }

    private fun initRv() {
        binding.forecastRv.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = forecastAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.forecast.observe(viewLifecycleOwner) {
            with(binding) {
                Glide.with(requireContext())
                    .load(it.icon)
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

        viewModel.fullForecast.observe(viewLifecycleOwner) {
            forecastAdapter.data = it.subList(1, it.size)
            initGraph(it)
        }
    }

    private fun initGraph(list: List<ShortForecast>) {
        val data = arrayListOf<DataPoint>()
        for (i in 0..8) {
            val toDouble = list[i].temp.substring(0, list[i].temp.length - 3).toInt().toDouble()
            data.add(DataPoint(i.toDouble(), toDouble))
        }
        LineGraphSeries(data.toArray(arrayOf())).apply {
            isDrawBackground = true
            isDrawDataPoints = true
            color = Color.argb(255, 255, 60, 60)
            backgroundColor = Color.argb(100, 204, 119, 119)
        }.also {
            binding.forecastGraph.apply {
                addSeries(it)
                title = context.getString(R.string.temperature_graph)
                titleColor = ContextCompat.getColor(requireContext(), R.color.black)
                titleTextSize = 50F
                gridLabelRenderer.apply {
                    gridColor = Color.BLACK
                    horizontalLabelsColor = Color.BLACK
                    verticalLabelsColor = Color.BLACK
                    gridStyle = GridLabelRenderer.GridStyle.HORIZONTAL
                    reloadStyles()
                }
            }
        }
    }

    private fun uploadForecast() {
        viewModel.getFullForecast(args.lat.toDouble(), args.lon.toDouble())
    }
}