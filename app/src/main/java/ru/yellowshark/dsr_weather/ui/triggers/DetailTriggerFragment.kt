package ru.yellowshark.dsr_weather.ui.triggers

import android.os.Bundle
import android.view.View
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
import ru.yellowshark.dsr_weather.domain.model.Trigger

class DetailTriggerFragment : Fragment(R.layout.fragment_trigger_details) {
    private val binding: FragmentTriggerDetailsBinding by viewBinding()
    private val args: DetailTriggerFragmentArgs by navArgs()
    private val viewModel: TriggersViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUi()
        initListeners()
    }

    private fun initUi() {
        with(binding) {
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
        binding.triggerDetailsSaveBtn.setOnClickListener {
            viewModel.saveTrigger(Trigger("", ""))
            findNavController().navigateUp()
        }
    }
}