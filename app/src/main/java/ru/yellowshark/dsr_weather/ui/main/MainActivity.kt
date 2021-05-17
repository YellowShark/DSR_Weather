package ru.yellowshark.dsr_weather.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.viewpager.widget.ViewPager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.yellowshark.dsr_weather.R
import ru.yellowshark.dsr_weather.databinding.ActivityMainBinding
import ru.yellowshark.dsr_weather.ui.locations.add.OnClickListener

//TODO
// init favorites +++
// next day forecast +++
// toolbar +++
// onSwipeRefresh +++
// google maps +++
// metric and imperial systems +++
// 2 languages +++
// triggers


@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main), OnClickListener {
    private val binding: ActivityMainBinding by viewBinding()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNavController()
        setupToolbar()
    }

    private fun initNavController() {
        val navMainFragment =
            supportFragmentManager.findFragmentById(R.id.nav_main_fragment) as NavHostFragment
        navController = navMainFragment.navController
    }

    override fun onSupportNavigateUp(): Boolean =
        navController.navigateUp()


    override fun onClickListener(v: View) {
        val pager = findViewById<ViewPager>(R.id.addLocation_viewPager)
        val currPos = pager.currentItem
        pager.currentItem = currPos + 1
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_set_unit) {
            showDialog()
            true
        } else
            super.onOptionsItemSelected(item)
    }

    private fun setupToolbar() {
        with(binding) {
            setSupportActionBar(toolbar)
            val appBarConfiguration = AppBarConfiguration.Builder(
                R.id.destination_locations,
                R.id.destination_add_location,
                R.id.destination_forecast
            ).build()

            NavigationUI.setupActionBarWithNavController(
                this@MainActivity,
                navController,
                appBarConfiguration
            )
        }
    }

    private fun showDialog() {
        DialogSetMeasure(
            arrayOf(
                getString(R.string.metric),
                getString(R.string.imperial)
            )
        ).show(supportFragmentManager, DialogSetMeasure::class.java.simpleName)
    }
}