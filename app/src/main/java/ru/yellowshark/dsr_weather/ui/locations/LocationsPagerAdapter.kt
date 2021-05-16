package ru.yellowshark.dsr_weather.ui.locations

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ru.yellowshark.dsr_weather.R

private val TAB_TITLES = arrayOf(
    R.string.all_locations,
    R.string.fav_locations
)

class LocationsPagerAdapter(
    private val context: Context,
    private val fragments: List<Fragment>,
    fm: FragmentManager
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getPageTitle(position: Int): CharSequence =
        context.getString(TAB_TITLES[position])

    override fun getCount(): Int = fragments.size

    override fun getItem(position: Int): Fragment =
        fragments[position]
}