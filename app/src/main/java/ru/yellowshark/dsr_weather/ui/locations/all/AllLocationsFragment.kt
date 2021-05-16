package ru.yellowshark.dsr_weather.ui.locations.all

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.yellowshark.dsr_weather.R
import ru.yellowshark.dsr_weather.databinding.FragmentAllLocationsBinding
import ru.yellowshark.dsr_weather.ui.locations.adapter.LocationsAdapter

class AllLocationsFragment : Fragment(R.layout.fragment_all_locations) {
    private val binding: FragmentAllLocationsBinding by viewBinding()
    private val viewModel: AllLocationsViewModel by lazy {
        ViewModelProvider(requireActivity()).get(AllLocationsViewModel::class.java)
    }
    private val adapter: LocationsAdapter by lazy { LocationsAdapter() }

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

    private fun updateData() {
        adapter.clearData()
        viewModel.updateLocations()
    }

    private fun initListeners() {
        with(binding) {
            allLocationsAddLocationsBtn.setOnClickListener { openWizard() }
            allLocationsAddFab.setOnClickListener { openWizard() }
        }
    }

    private fun openWizard() {
        Navigation.findNavController(binding.root).navigate(R.id.destination_add_location)
    }

    private fun initRv() {
        binding.allLocationsRv.adapter = adapter
    }

    private fun observeVm() {
        with(binding) {
            viewModel.location.observe(viewLifecycleOwner) {
                adapter.addItem(it)
                allLocationsEmptyRv.isVisible = false
                allLocationsAddFab.isVisible = true
            }
        }
    }
}