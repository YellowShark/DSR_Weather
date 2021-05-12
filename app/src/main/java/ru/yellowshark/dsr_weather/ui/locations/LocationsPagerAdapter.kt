package ru.yellowshark.dsr_weather.ui.locations

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

private val TAB_TITLES = arrayOf("Все локации", "Избранные")

class LocationsPagerAdapter(
    private val context: Context,
    private val fragments: List<Fragment>,
    fm: FragmentManager
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getPageTitle(position: Int): CharSequence =
        TAB_TITLES[position]

    override fun getCount(): Int = fragments.size

    override fun getItem(position: Int): Fragment =
        fragments[position]
}