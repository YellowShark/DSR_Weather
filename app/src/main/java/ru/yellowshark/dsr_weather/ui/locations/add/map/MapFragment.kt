package ru.yellowshark.dsr_weather.ui.locations.add.map

import android.content.Context
import android.os.Bundle
import android.view.View
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

class MapFragment : Fragment(R.layout.fragment_map), OnMapReadyCallback {
    private val binding: FragmentMapBinding by viewBinding()
    private val viewModel: MapViewModel by lazy {
        ViewModelProvider(requireActivity()).get(MapViewModel::class.java)
    }
    private lateinit var onClickListener: OnClickListener
    private lateinit var map: GoogleMap

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onClickListener = context as OnClickListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mapAddFab.setOnClickListener {
            viewModel.setCoordinates(0L, 0L)
            onClickListener.onClickListener(it)
        }
        initMaps()

    }

    override fun onMapReady(map: GoogleMap) {
        val moscow = LatLng(55.754093, 37.620407)
        map.apply {
            addMarker(
                MarkerOptions()
                    .position(moscow)
                    .title("Moscow")
            )
            moveCamera(CameraUpdateFactory.newLatLng(moscow))
            setOnMapClickListener { latLng ->
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

    private fun initMaps() {
        (childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment)
            .getMapAsync(this)
    }
}