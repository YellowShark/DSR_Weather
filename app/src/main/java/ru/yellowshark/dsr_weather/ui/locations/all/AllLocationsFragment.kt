package ru.yellowshark.dsr_weather.ui.locations.all

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.yellowshark.dsr_weather.R
import ru.yellowshark.dsr_weather.databinding.FragmentLocationsBinding
import ru.yellowshark.dsr_weather.domain.model.Location
import ru.yellowshark.dsr_weather.ui.locations.LocationsFragmentDirections
import ru.yellowshark.dsr_weather.ui.locations.adapter.LocationsAdapter
import ru.yellowshark.dsr_weather.utils.Event
import ru.yellowshark.dsr_weather.utils.Event.*

class AllLocationsFragment : Fragment(R.layout.fragment_locations) {
    private val binding: FragmentLocationsBinding by viewBinding()
    private val viewModel: AllLocationsViewModel by lazy {
        ViewModelProvider(requireActivity()).get(AllLocationsViewModel::class.java)
    }
    private val adapter: LocationsAdapter by lazy {
        LocationsAdapter(
            { openForecastFragment(it) },
            { pos, id, isFav ->
                viewModel.updateIsFavorite(id, isFav)
                adapter.updateLocation(pos)
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initRv()
        observeVm()
    }

    override fun onResume() {
        super.onResume()
        updateData()
    }

    private fun openForecastFragment(location: Location) {
        LocationsFragmentDirections.actionForecast(location.city).also {
            Navigation.findNavController(binding.root).navigate(it)
        }
    }

    private fun updateData() {
        adapter.clearData()
        viewModel.updateLocations()
    }

    private fun initListeners() {
        with(binding) {
            locationsAddLocationsBtn.setOnClickListener { openWizard() }
            locationsAddFab.setOnClickListener { openWizard() }
        }
    }

    private fun openWizard() {
        Navigation.findNavController(binding.root).navigate(R.id.destination_add_location)
    }

    private fun initRv() {
        binding.locationsRv.adapter = adapter
    }

    private fun observeVm() {
        with(binding) {
            viewModel.event.observe(viewLifecycleOwner) { event ->
                when (event) {
                    LOADING -> {
                        locationsLoader.isVisible = true
                        locationsNoLocationsWrapper.isVisible = false
                        locationsAddFab.isVisible = false
                        locationsRv.isVisible = false
                    }
                    SUCCESS -> {
                        locationsLoader.isVisible = false
                        locationsNoLocationsWrapper.isVisible = false
                        locationsAddFab.isVisible = true
                        locationsRv.isVisible = true
                    }
                    ERROR -> {
                        locationsLoader.isVisible = false
                        locationsNoLocationsWrapper.isVisible = false
                        locationsAddFab.isVisible = true
                        locationsRv.isVisible = true
                        Toast.makeText(requireContext(), "Произошла ошибка", Toast.LENGTH_SHORT)
                            .show()
                    }
                    EMPTY -> {
                        locationsLoader.isVisible = false
                        locationsNoLocationsWrapper.isVisible = true
                        locationsAddFab.isVisible = false
                        locationsRv.isVisible = false
                    }
                }
            }

            viewModel.location.observe(viewLifecycleOwner) {
                adapter.addItem(it)
                locationsNoLocationsWrapper.isVisible = false
                locationsAddFab.isVisible = true
            }
        }
    }
}