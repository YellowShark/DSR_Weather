package ru.yellowshark.dsr_weather.ui.locations.all

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.yellowshark.dsr_weather.R
import ru.yellowshark.dsr_weather.databinding.FragmentLocationsBinding
import ru.yellowshark.dsr_weather.domain.model.Location
import ru.yellowshark.dsr_weather.ui.locations.LocationsFragmentDirections
import ru.yellowshark.dsr_weather.ui.locations.adapter.LocationsAdapter
import ru.yellowshark.dsr_weather.utils.Event.*

@AndroidEntryPoint
class AllLocationsFragment : Fragment(R.layout.fragment_locations),
    SwipeRefreshLayout.OnRefreshListener {

    private val binding: FragmentLocationsBinding by viewBinding()
    private val viewModel: AllLocationsViewModel by viewModels()
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

    override fun onRefresh() {
        updateData()
        binding.locationsRefresher.isRefreshing = false
    }

    private fun openForecastFragment(location: Location) {
        LocationsFragmentDirections.actionForecast(
            location.lat.toFloat(),
            location.lon.toFloat(),
            location.id
        )
            .also {
                findNavController().navigate(it)
            }
    }

    private fun updateData() {
        adapter.clearData()
        viewModel.updateLocations()
    }

    private fun initListeners() {
        with(binding) {
            locationsRefresher.setOnRefreshListener(this@AllLocationsFragment)
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
                    NO_INTERNET -> {
                        locationsLoader.isVisible = false
                        locationsNoLocationsWrapper.isVisible = false
                        locationsAddFab.isVisible = true
                        locationsRv.isVisible = true
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.error_no_internet),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                    UNKNOWN_ERROR -> {
                        locationsLoader.isVisible = false
                        locationsNoLocationsWrapper.isVisible = false
                        locationsAddFab.isVisible = true
                        locationsRv.isVisible = true
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.unknown_error),
                            Toast.LENGTH_SHORT
                        )
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