package ru.yellowshark.dsr_weather.ui.triggers

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher
import ru.yellowshark.dsr_weather.R
import ru.yellowshark.dsr_weather.databinding.FragmentTriggerDetailsBinding
import ru.yellowshark.dsr_weather.domain.model.Point
import ru.yellowshark.dsr_weather.domain.model.Trigger
import ru.yellowshark.dsr_weather.utils.Event

class DetailTriggerFragment : Fragment(R.layout.fragment_trigger_details) {
    private val binding: FragmentTriggerDetailsBinding by viewBinding()
    private val args: DetailTriggerFragmentArgs by navArgs()
    private val viewModel: TriggersViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUi()
        initListeners()
        observeViewModel()
    }

    override fun onDestroy() {
        viewModel.clearData()
        super.onDestroy()
    }

    private fun initUi() {
        with(binding) {
            if (args.id.isEmpty()) triggerDetailsDeleteBtn.isVisible = false
            triggerDetailsNameEt.setText(args.name)
            initMasks()
        }
    }

    private fun initMasks() {
        with(binding) {
            val slots = UnderscoreDigitSlotsParser().parseSlots("__/__/____")
            val mask = MaskImpl.createTerminated(slots)
            val watcher1: FormatWatcher = MaskFormatWatcher(mask)
            val watcher2: FormatWatcher = MaskFormatWatcher(mask)
            watcher1.installOn(triggerDetailsDateStartEt)
            watcher2.installOn(triggerDetailsDateEndEt)
        }
    }

    private fun initListeners() {
        with(binding) {
            triggerDetailsSaveBtn.setOnClickListener {
                if (validFields()) {
                    val name = triggerDetailsNameEt.text.toString()
                    val temp = triggerDetailsTempEt.text.toString()
                    val wind = triggerDetailsWindEt.text.toString()
                    val humidity = triggerDetailsHumidityEt.text.toString()
                    val dateStart = triggerDetailsDateStartEt.text.toString()
                    val dateEnd = triggerDetailsDateEndEt.text.toString()

                    viewModel.saveTrigger(
                        Trigger(
                            args.id,
                            name,
                            temp.toInt(),
                            if (wind.isEmpty()) null else wind.toInt(),
                            if (humidity.isEmpty()) null else humidity.toInt(),
                            dateStart,
                            dateEnd,
                            listOf(Point(55.754093, 37.620407))
                        )
                    )
                } else
                    Toast.makeText(requireContext(), R.string.required_fields, Toast.LENGTH_SHORT)
                        .show()
            }
            triggerDetailsDeleteBtn.setOnClickListener { viewModel.deleteTrigger(args.id) }
        }
    }

    private fun validFields(): Boolean {
        with(binding) {
            return triggerDetailsNameEt.text.toString().trim()
                .isNotEmpty() && triggerDetailsTempEt.text.toString().trim().isNotEmpty()
        }
    }

    private fun observeViewModel() {
        viewModel.event.observe(viewLifecycleOwner) {
            it?.let { event ->
                when (event) {
                    Event.SUCCESS -> {
                        findNavController().navigateUp()
                    }
                    Event.NO_INTERNET -> {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.error_no_internet),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    Event.UNKNOWN_ERROR -> {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.unknown_error),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else -> {
                    }
                }
            }
        }
    }
}