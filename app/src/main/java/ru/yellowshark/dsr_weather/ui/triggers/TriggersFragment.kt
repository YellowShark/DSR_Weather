package ru.yellowshark.dsr_weather.ui.triggers

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.yellowshark.dsr_weather.R
import ru.yellowshark.dsr_weather.databinding.FragmentTriggersBinding
import ru.yellowshark.dsr_weather.domain.model.Trigger
import ru.yellowshark.dsr_weather.service.AlertService

@AndroidEntryPoint
class TriggersFragment : Fragment(R.layout.fragment_triggers) {
    private val binding: FragmentTriggersBinding by viewBinding()
    private val viewModel: TriggersViewModel by activityViewModels()
    private val adapter: TriggersAdapter by lazy {
        TriggersAdapter {
            openDetails(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRv()
        observeViewModel()
        initListeners()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getTriggers()
        arguments?.getInt(AlertService.TRIGGER_ID)?.let { id ->
            val actionTriggerDetails = TriggersFragmentDirections.actionTriggerDetails(id)
            requireArguments().clear()
            findNavController().navigate(actionTriggerDetails)
        }
    }

    private fun openDetails(trigger: Trigger) {
        val actionTriggerDetails = TriggersFragmentDirections.actionTriggerDetails(trigger.id)
        findNavController().navigate(actionTriggerDetails)
    }

    private fun initRv() {
        binding.triggersRv.adapter = adapter
    }

    private fun observeViewModel() {
        with(viewModel) {
            triggers.observe(viewLifecycleOwner) { list ->
                if (list.isNotEmpty())
                    with(binding) {
                        triggersRv.isVisible = true
                        triggersTextIfNoTriggers.isVisible = false
                    }.also {
                        adapter.data = list
                    }
                else
                    with(binding) {
                        triggersRv.isVisible = false
                        triggersTextIfNoTriggers.isVisible = true
                    }
            }
        }
    }

    private fun initListeners() {
        binding.triggersAddBtn.setOnClickListener {
            openDetails(
                Trigger(
                    -1,
                    "",
                    "",
                    0.0,
                    0.0,
                    0
                )
            )
        }
    }
}