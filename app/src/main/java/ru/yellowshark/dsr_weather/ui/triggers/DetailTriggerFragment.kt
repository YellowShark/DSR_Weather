package ru.yellowshark.dsr_weather.ui.triggers

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.yellowshark.dsr_weather.R
import ru.yellowshark.dsr_weather.databinding.FragmentTriggerDetailsBinding

class DetailTriggerFragment : Fragment(R.layout.fragment_trigger_details) {
    private val binding: FragmentTriggerDetailsBinding by viewBinding()
    private val args: DetailTriggerFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUi()
        initListeners()
    }

    private fun initUi() {
        with(binding) {
            triggerDetailsNameEt.setText(args.name)
        }
    }

    private fun initListeners() {
        binding.triggerDetailsSaveBtn.setOnClickListener { findNavController().navigateUp() }
    }
}