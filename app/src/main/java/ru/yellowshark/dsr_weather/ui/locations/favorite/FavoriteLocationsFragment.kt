package ru.yellowshark.dsr_weather.ui.locations.favorite

import android.os.Bundle
import android.view.View
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
        observeVm()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavLocations()
    }

    private fun openForecastFragment(location: Location) {
        LocationsFragmentDirections.actionForecast(location.city).also {
            Navigation.findNavController(binding.root).navigate(it)
        }
    }

    private fun initUi() {
        with(binding) {
            locationsRv.adapter = adapter

            locationsLoader.isVisible = false
            locationsNoLocationsWrapper.isVisible = false
            locationsAddFab.isVisible = false
            locationsRv.isVisible = true
        }
    }

    private fun observeVm() {
        viewModel.favLocations.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }
    }
}