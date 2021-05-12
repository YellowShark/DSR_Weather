package ru.yellowshark.dsr_weather.ui.locations.add

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.yellowshark.dsr_weather.R
import ru.yellowshark.dsr_weather.databinding.FragmentAddLocationBinding

class AddLocationFragment : Fragment(R.layout.fragment_add_location) {
    private val binding: FragmentAddLocationBinding by viewBinding()
    private lateinit var pagerAdapter: WizardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pagerAdapter = WizardAdapter(
            requireContext(),
            listOf(
                LocationNameFragment(),
                MapFragment(),
                DetailsFragment()
            ),
            childFragmentManager
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPager()
    }

    private fun initPager() {
        with(binding) {
            addLocationViewPager.apply {
                adapter = pagerAdapter
                setOnTouchListener { v, event -> return@setOnTouchListener true }
                addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int
                    ) {
                    }

                    override fun onPageSelected(position: Int) {
                        addLocationPageIndicator.selection = position
                    }

                    override fun onPageScrollStateChanged(state: Int) {
                    }
                })
            }
        }
    }
}