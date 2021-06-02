package ru.yellowshark.dsr_weather.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager.widget.ViewPager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import ru.yellowshark.dsr_weather.R
import ru.yellowshark.dsr_weather.databinding.ActivityMainBinding
import ru.yellowshark.dsr_weather.service.AlertService
import ru.yellowshark.dsr_weather.ui.locations.add.OnClickListener
import ru.yellowshark.dsr_weather.utils.hideKeyboard

//TODO
// init favorites +++
// next day forecast +++
// toolbar +++
// onSwipeRefresh +++
// google maps +++
// metric and imperial systems +++
// 2 languages +++
// delete location +++
// triggers +++


@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main), OnClickListener {
    private val binding: ActivityMainBinding by viewBinding()
    private val viewModel: MainViewModel by viewModels()
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNavController()
        setupToolbar()
        initDrawerLayout()
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        checkIfNotificationWasOpened()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        hideKeyboard(currentFocus ?: View(this))
        return super.dispatchTouchEvent(ev)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_main_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

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
        return when (item.itemId) {
            android.R.id.home -> {
                if (navController.currentDestination?.id != R.id.destination_locations)
                    navController.navigateUp()
                else
                    binding.drawerLayout.openDrawer(GravityCompat.START)
                true
            }
            R.id.action_set_unit -> {
                showDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initNavController() {
        val navMainFragment =
            supportFragmentManager.findFragmentById(R.id.nav_main_fragment) as NavHostFragment
        navController = navMainFragment.navController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.destination_locations -> {
                    binding.toolbar.menu.add(0, R.id.action_set_unit, 0, R.string.unit)
                }
                else -> {
                    binding.toolbar.menu.removeItem(R.id.action_set_unit)
                }
            }
        }
    }

    private fun setupToolbar() {
        with(binding) {
            setSupportActionBar(toolbar)
            val toolbarConfiguration = AppBarConfiguration.Builder(
                R.id.destination_locations,
                R.id.destination_add_location
            ).build()

            NavigationUI.setupActionBarWithNavController(
                this@MainActivity,
                navController,
                toolbarConfiguration
            )
        }
    }

    private fun showDialog() {
        SetMeasureDialog(
            arrayOf(
                getString(R.string.metric),
                getString(R.string.imperial)
            )
        ).show(supportFragmentManager, SetMeasureDialog::class.java.simpleName)
    }

    private fun initDrawerLayout() {
        with(binding) {
            drawerLayout.apply {
                setScrimColor(
                    ContextCompat.getColor(
                        this@MainActivity,
                        android.R.color.transparent
                    )
                )
                drawerElevation = 0f
            }

            appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.destination_locations,
                    R.id.destination_triggers,
                ), drawerLayout
            )

            findViewById<NavigationView>(R.id.nav_view)
                .setupWithNavController(navController)

            NavigationUI.setupActionBarWithNavController(
                this@MainActivity,
                navController,
                drawerLayout
            )
        }
    }

    private fun checkIfNotificationWasOpened() {
        val extra = intent.extras?.getInt(AlertService.TRIGGER_ID) ?: -1
        if (extra != -1) {
            val args = Bundle()
            args.putInt(AlertService.TRIGGER_ID, extra)
            navController.navigate(R.id.destination_triggers, args)
        }
    }

    private fun observeViewModel() {
        viewModel.toolbarTitle.observe(this) {
            binding.toolbar.title = it
        }
    }
}