package ru.yellowshark.dsr_weather.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebViewFragment
import androidx.navigation.Navigation
import androidx.viewpager.widget.ViewPager
import dagger.hilt.android.AndroidEntryPoint
import ru.yellowshark.dsr_weather.R
import ru.yellowshark.dsr_weather.ui.locations.add.AddLocationFragment
import ru.yellowshark.dsr_weather.ui.locations.add.OnClickListener

//TODO
// add loader
// init favorites
// next day forecast
// toolbar
// google maps
// metric and imperial systems
// 2 languages
// triggers


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onClickListener(v: View) {
        val pager = findViewById<ViewPager>(R.id.addLocation_viewPager)
        val currPos = pager.currentItem
        pager.currentItem = currPos + 1
    }
}