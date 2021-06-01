package ru.yellowshark.dsr_weather.ui.triggers

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import ru.yellowshark.dsr_weather.R
import ru.yellowshark.dsr_weather.domain.model.Location

class LocationDialog(
    private val locations: List<Location>
) : DialogFragment() {

    private val viewModel: TriggersViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.choose_location)
            .setItems(
                locations.map { it.city }.toTypedArray()
            ) { _, index ->
                viewModel.setLocation(locations[index])
                dismiss()
            }
            .create()
    }
}