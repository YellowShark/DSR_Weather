package ru.yellowshark.dsr_weather.ui.locations.add.name

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.yellowshark.dsr_weather.R
import ru.yellowshark.dsr_weather.databinding.FragmentLocationNameBinding
import ru.yellowshark.dsr_weather.ui.locations.add.AddLocationViewModel
import ru.yellowshark.dsr_weather.ui.locations.add.OnClickListener

class LocationNameFragment : Fragment(R.layout.fragment_location_name) {
    private val binding: FragmentLocationNameBinding by viewBinding()
    private val viewModel: AddLocationViewModel by activityViewModels()
    private lateinit var onClickListener: OnClickListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onClickListener = context as OnClickListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        with(binding) {
            locationNameOkBtn.setOnClickListener {
                locationNameNameEt.text.toString().let { name ->
                    if (name.trim().isNotEmpty()) {
                        viewModel.setLocationName(name)
                        onClickListener.onClickListener(it)
                    }
                }
            }
        }
    }
}