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
import ru.yellowshark.dsr_weather.domain.model.Location
import ru.yellowshark.dsr_weather.domain.model.Trigger
import ru.yellowshark.dsr_weather.ui.main.MainViewModel
import ru.yellowshark.dsr_weather.utils.DateConverter
import ru.yellowshark.dsr_weather.utils.Event
import kotlin.properties.Delegates

class DetailTriggerFragment : Fragment(R.layout.fragment_trigger_details) {
    private lateinit var locationName: String
    private var locationLon by Delegates.notNull<Double>()
    private var locationLat by Delegates.notNull<Double>()
    private val binding: FragmentTriggerDetailsBinding by viewBinding()
    private val args: DetailTriggerFragmentArgs by navArgs()
    private val viewModel: TriggersViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private var dialog: LocationDialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initListeners()
        observeViewModel()
        initUi()
    }

    override fun onDestroy() {
        viewModel.clearData()
        super.onDestroy()
    }

    private fun initUi() {
        with(binding) {
            initMasks()
            if (args.id == -1) {
                mainViewModel.updateToolbarTitle(getString(R.string.new_trigger))
                triggerDetailsDeleteBtn.visibility = View.INVISIBLE
                triggerDetailsDateStartEt.setText(DateConverter.dateFormat(System.currentTimeMillis()))
                triggerDetailsDateEndEt.setText(DateConverter.dateFormat(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
            } else viewModel.getTriggerById(args.id)
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
            triggerDetailsLocationEt.setOnClickListener {
                dialog?.show(childFragmentManager, LocationDialog::javaClass.name)
            }
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
                            locationName,
                            locationLat,
                            locationLon,
                            temp.toInt(),
                            if (humidity.isEmpty()) null else humidity.toInt(),
                            if (wind.isEmpty()) null else wind.toInt(),
                            dateStart,
                            dateEnd
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
                .isNotEmpty() && triggerDetailsTempEt.text.toString().trim()
                .isNotEmpty() && viewModel.chosenLocation.value != null
        }
    }

    private fun observeViewModel() {
        with(viewModel) {
            chosenLocation.observe(viewLifecycleOwner) {
                it?.let { location ->
                    binding.triggerDetailsLocationEt.setText(location.city)
                    locationLat = location.lat
                    locationLon = location.lon
                    locationName = location.city
                }
            }
            locations.observe(viewLifecycleOwner) {
                dialog = LocationDialog(it)
            }
            trigger.observe(viewLifecycleOwner) {
                it?.let { trigger ->
                    binding.apply {
                        viewModel.setLocation(
                            Location(
                                0,
                                "",
                                trigger.locationName,
                                lat = trigger.lat,
                                lon = trigger.lon
                            )
                        )
                        triggerDetailsNameEt.setText(trigger.name)
                        triggerDetailsLocationEt.setText(trigger.locationName)
                        triggerDetailsTempEt.setText(trigger.temp.toString())
                        triggerDetailsWindEt.setText(if (trigger.wind == null) "" else trigger.wind.toString())
                        triggerDetailsHumidityEt.setText(if (trigger.humidity == null) "" else trigger.humidity.toString())
                        triggerDetailsDateStartEt.setText(trigger.startDate)
                        triggerDetailsDateEndEt.setText(trigger.endDate)
                    }
                }
            }
            event.observe(viewLifecycleOwner) {
                it?.let { event ->
                    when (event) {
                        Event.LOADING -> {
                            binding.apply {
                                triggerDetailsLoaderPb.isVisible = true
                                triggerDetailsContentWrapper.visibility = View.INVISIBLE
                            }
                        }
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
}