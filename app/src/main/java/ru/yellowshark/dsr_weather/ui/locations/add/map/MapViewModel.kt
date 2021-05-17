package ru.yellowshark.dsr_weather.ui.locations.add.map

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.yellowshark.dsr_weather.domain.repository.Repository
import ru.yellowshark.dsr_weather.ui.locations.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {

    fun setCoordinates(lon: Double, lat: Double) {
        repository.setCoordinates(lon, lat)
    }
}