package ru.yellowshark.dsr_weather.ui.locations.forecast

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.yellowshark.dsr_weather.R
import ru.yellowshark.dsr_weather.databinding.FragmentForecastBinding

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
            binding.tv.text = it.toString()
        }
    }
}