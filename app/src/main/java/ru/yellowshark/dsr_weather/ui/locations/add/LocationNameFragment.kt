package ru.yellowshark.dsr_weather.ui.locations.add

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.yellowshark.dsr_weather.R
import ru.yellowshark.dsr_weather.databinding.FragmentLocationNameBinding

class LocationNameFragment : Fragment(R.layout.fragment_location_name) {
    private val binding: FragmentLocationNameBinding by viewBinding()
    private lateinit var onClickListener: OnClickListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onClickListener = context as OnClickListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.locationNameOkBtn.setOnClickListener {
            onClickListener.onClickListener(it)
        }
    }
}