package ru.yellowshark.dsr_weather.ui.locations.add.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.yellowshark.dsr_weather.R
import ru.yellowshark.dsr_weather.databinding.FragmentDetailsBinding
import ru.yellowshark.dsr_weather.ui.locations.add.AddLocationViewModel

class DetailsFragment : Fragment(R.layout.fragment_details) {
    private val binding: FragmentDetailsBinding by viewBinding()
    private val viewModel: AddLocationViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        with(binding) {
            detailsFinishBtn.setOnClickListener {
                setNextDay()
                saveLocation()
            }
        }
    }

    private fun setNextDay() {
        with(binding) {
            viewModel.setNextDay(detailsWithNextDayRb.isChecked)
        }
    }

    private fun saveLocation() {
        viewModel.saveLocation()
        Navigation.findNavController(binding.root).navigateUp()
    }
}