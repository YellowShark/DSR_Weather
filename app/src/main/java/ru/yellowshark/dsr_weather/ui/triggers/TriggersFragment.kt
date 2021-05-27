package ru.yellowshark.dsr_weather.ui.triggers

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.yellowshark.dsr_weather.R
import ru.yellowshark.dsr_weather.databinding.FragmentTriggersBinding
import ru.yellowshark.dsr_weather.domain.model.Trigger

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
        viewModel.getTriggers()
    }

    private fun openDetails(trigger: Trigger) {
        val actionTriggerDetails = TriggersFragmentDirections.actionTriggerDetails(trigger.name)
        findNavController().navigate(actionTriggerDetails)
    }

    private fun initRv() {
        binding.triggersRv.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.triggers.observe(viewLifecycleOwner) {
            adapter.data = it
        }
    }

    private fun initListeners() {
        binding.triggersAddBtn.setOnClickListener { openDetails(Trigger("", "")) }
    }
}