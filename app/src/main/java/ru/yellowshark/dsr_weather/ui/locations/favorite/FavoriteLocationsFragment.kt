package ru.yellowshark.dsr_weather.ui.locations.favorite

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.yellowshark.dsr_weather.R
import ru.yellowshark.dsr_weather.databinding.FragmentLocationsBinding
import ru.yellowshark.dsr_weather.domain.model.Location
import ru.yellowshark.dsr_weather.ui.locations.LocationsFragmentDirections
import ru.yellowshark.dsr_weather.ui.locations.adapter.LocationsAdapter

class FavoriteLocationsFragment : Fragment(R.layout.fragment_locations) {
    private val binding: FragmentLocationsBinding by viewBinding()
    private val viewModel: FavoriteLocationsViewModel by lazy {
        ViewModelProvider(requireActivity()).get(FavoriteLocationsViewModel::class.java)
    }
    private val adapter: LocationsAdapter by lazy {
        LocationsAdapter(
            { openForecastFragment(it) },
            { pos, id, isFav ->
                viewModel.updateIsFavorite(id, isFav)
                adapter.removeItem(pos)
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initListeners()
        initRv()
        observeVm()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavLocations()
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

    private fun initUi() {
        with(binding) {
            locationsLoader.isVisible = false
            locationsNoLocationsWrapper.isVisible = false
            locationsAddFab.isVisible = false
            locationsRv.isVisible = true
        }
    }

    private fun initListeners() {
        binding.locationsRefresher.apply {
            setOnRefreshListener { this.isRefreshing = false }
        }
    }

    private fun initRv() {
        binding.locationsRv.adapter = adapter
    }

    private fun observeVm() {
        viewModel.favLocations.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }
    }
}