package ru.yellowshark.dsr_weather.ui.locations

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.yellowshark.dsr_weather.R
import ru.yellowshark.dsr_weather.databinding.FragmentLocationsBinding
import ru.yellowshark.dsr_weather.ui.locations.all.AllLocationsFragment
import ru.yellowshark.dsr_weather.ui.locations.favorite.FavoriteLocationsFragment

class LocationsFragment : Fragment(R.layout.fragment_locations) {
    private val binding: FragmentLocationsBinding by viewBinding()
    private lateinit var pagerAdapter: LocationsPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pagerAdapter = LocationsPagerAdapter(
            requireContext(),
            listOf(
                AllLocationsFragment(),
                FavoriteLocationsFragment()
            ),
            childFragmentManager
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
    }

    private fun initViewPager() {
        with(binding) {
            locationsViewPager.adapter = pagerAdapter
            locationsTabLayout.setupWithViewPager(locationsViewPager)
        }
    }
}