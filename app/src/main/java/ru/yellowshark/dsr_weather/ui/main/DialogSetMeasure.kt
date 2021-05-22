package ru.yellowshark.dsr_weather.ui.main

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import dagger.hilt.android.AndroidEntryPoint
import ru.yellowshark.dsr_weather.R
import ru.yellowshark.dsr_weather.data.other.UnitManager
import ru.yellowshark.dsr_weather.ui.locations.OnMetricChangeListener
import ru.yellowshark.dsr_weather.utils.IMPERIAL_UNITS
import ru.yellowshark.dsr_weather.utils.METRIC_UNITS
import javax.inject.Inject

@AndroidEntryPoint
class DialogSetMeasure(
    private val items: Array<String>
) : DialogFragment() {

    private lateinit var onMetricChangeListener: OnMetricChangeListener
    @Inject
    lateinit var unitManager: UnitManager

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onMetricChangeListener = context as OnMetricChangeListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.choose_measure_system)
            .setItems(items) { _, pos ->
                unitManager.setUnit(
                    when (pos) {
                        0 -> {
                            onMetricChangeListener.onMetricChangeListener(METRIC_UNITS)
                            METRIC_UNITS
                        }
                        else -> {
                            onMetricChangeListener.onMetricChangeListener(IMPERIAL_UNITS)
                            IMPERIAL_UNITS
                        }
                    }
                )
                dismiss()
            }
            .create()
    }
}