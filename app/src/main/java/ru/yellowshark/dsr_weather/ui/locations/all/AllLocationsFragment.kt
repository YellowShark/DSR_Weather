package ru.yellowshark.dsr_weather.ui.locations.all

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.yellowshark.dsr_weather.R
import ru.yellowshark.dsr_weather.databinding.FragmentAllLocationsBinding
import ru.yellowshark.dsr_weather.domain.model.Location
import ru.yellowshark.dsr_weather.ui.locations.adapter.LocationsAdapter

class AllLocationsFragment : Fragment(R.layout.fragment_all_locations) {
    private val binding: FragmentAllLocationsBinding by viewBinding()
    private val adapter: LocationsAdapter by lazy { LocationsAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initRv()
    }

    private fun initListeners() {
        binding.allLocationsAddLocationsBtn.setOnClickListener { openWizard() }
    }

    private fun openWizard() {
        Navigation.findNavController(binding.root).navigate(R.id.destination_add_location)
    }

    private fun initRv() {
        binding.allLocationsRv.adapter = adapter
        mockAdapter()
    }

    private fun mockAdapter() {
        adapter.data = listOf(
            Location(
                "Воронеж",
                "30"
            ),
            Location(
                "Москва",
                "30"
            ),
            Location(
                "Питер",
                "30"
            ),

        )
    }
}