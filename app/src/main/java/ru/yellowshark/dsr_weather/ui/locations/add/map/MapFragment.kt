package ru.yellowshark.dsr_weather.ui.locations.add.map

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import ru.yellowshark.dsr_weather.R
import ru.yellowshark.dsr_weather.databinding.FragmentMapBinding
import ru.yellowshark.dsr_weather.ui.locations.add.OnClickListener
import java.io.IOException


class MapFragment : Fragment(R.layout.fragment_map), OnMapReadyCallback {
    companion object {
        const val MOSCOW_LAT = 55.754093
        const val MOSCOW_LON = 37.620407
    }

    private val binding: FragmentMapBinding by viewBinding()
    private val viewModel: MapViewModel by lazy {
        ViewModelProvider(requireActivity()).get(MapViewModel::class.java)
    }
    private lateinit var onClickListener: OnClickListener
    private lateinit var map: GoogleMap
    private var currentCoordinates = LatLng(MOSCOW_LAT, MOSCOW_LON)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onClickListener = context as OnClickListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initMaps()

    }

    override fun onMapReady(map: GoogleMap) {
        map.apply {
            addMarker(
                MarkerOptions()
                    .position(currentCoordinates)
            )
            moveCamera(CameraUpdateFactory.newLatLng(currentCoordinates))
            setOnMapClickListener { latLng ->
                currentCoordinates = latLng
                val markerOptions = MarkerOptions()
                markerOptions.position(latLng)
                markerOptions.title(latLng.latitude.toString() + ":" + latLng.longitude + " " + latLng.toString())
                this.apply {
                    clear()
                    animateCamera(CameraUpdateFactory.newLatLng(latLng))
                    addMarker(markerOptions)
                }
            }
        }
        this.map = map
    }

    private fun initListeners() {
        with(binding) {
            mapAddFab.setOnClickListener {
                viewModel.setCoordinates(currentCoordinates.longitude, currentCoordinates.latitude)
                onClickListener.onClickListener(it)
            }
            mapSearchTil.setStartIconOnClickListener { search() }
            mapSearchEt.setOnEditorActionListener(TextView.OnEditorActionListener { _: TextView, _: Int, event: KeyEvent? ->
                if (event != null) {
                    if (!event.isShiftPressed) {
                        search()
                        return@OnEditorActionListener true
                    }
                    return@OnEditorActionListener false
                }
                search()
                return@OnEditorActionListener true
            })
        }
    }

    private fun search() {
        val location = binding.mapSearchEt.text.toString()
        val addressList: List<Address>?
        if (location.trim().isNotEmpty()) {
            val geocoder = Geocoder(requireContext())
            try {
                addressList = geocoder.getFromLocationName(location, 1)
                if (addressList != null && addressList.isNotEmpty()) {
                    val address = addressList[0]
                    currentCoordinates = LatLng(address.latitude, address.longitude)
                    map.apply {
                        clear()
                        addMarker(MarkerOptions().position(currentCoordinates).title(location))
                        moveCamera(CameraUpdateFactory.newLatLngZoom(currentCoordinates, 15F))
                        animateCamera(CameraUpdateFactory.newLatLng(currentCoordinates))
                        animateCamera(CameraUpdateFactory.zoomTo(15F), 2000, null)
                    }
                } else
                    onFailedSearch()
            } catch (e: IOException) {
                e.printStackTrace()
                onFailedSearch()
            }
        }
    }

    private fun onFailedSearch() {
        Toast.makeText(
            requireContext(),
            getString(R.string.nothing_found),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun initMaps() {
        (childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment)
            .getMapAsync(this)
    }
}