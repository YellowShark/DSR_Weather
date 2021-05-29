package ru.yellowshark.dsr_weather.ui.main

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.yellowshark.dsr_weather.R
import ru.yellowshark.dsr_weather.data.other.UnitManager
import ru.yellowshark.dsr_weather.utils.IMPERIAL_UNITS
import ru.yellowshark.dsr_weather.utils.METRIC_UNITS
import javax.inject.Inject

@AndroidEntryPoint
class DialogSetMeasure(
    private val items: Array<String>
) : DialogFragment() {

    @Inject
    lateinit var unitManager: UnitManager
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.choose_measure_system)
            .setItems(items) { _, pos ->
                when (pos) {
                    0 -> {
                        viewModel.selectMetric(METRIC_UNITS)
                    }
                    else -> {
                        viewModel.selectMetric(IMPERIAL_UNITS)
                    }
                }
                dismiss()
            }
            .create()
    }
}