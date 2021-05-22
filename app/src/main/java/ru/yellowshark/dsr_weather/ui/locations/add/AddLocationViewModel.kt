package ru.yellowshark.dsr_weather.ui.locations.add

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.yellowshark.dsr_weather.domain.model.Location
import ru.yellowshark.dsr_weather.domain.repository.Repository
import ru.yellowshark.dsr_weather.ui.locations.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class AddLocationViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {

    private val newLocation = Location(0, "", "")

    fun setNextDay(hasNextDayForecast: Boolean) {
        newLocation.hasNextDayForecast = hasNextDayForecast
    }

    fun setLocationName(name: String) {
        newLocation.city = name
    }

    fun setCoordinates(lon: Double, lat: Double) {
        newLocation.lon = lon
        newLocation.lat = lat
    }

    fun saveLocation() {
        disposables.add(repository.saveLocation(newLocation).subscribe())
    }
}