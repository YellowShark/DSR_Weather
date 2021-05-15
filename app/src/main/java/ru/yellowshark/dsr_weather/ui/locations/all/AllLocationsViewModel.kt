package ru.yellowshark.dsr_weather.ui.locations.all

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.yellowshark.dsr_weather.domain.model.Forecast
import ru.yellowshark.dsr_weather.domain.model.Location
import ru.yellowshark.dsr_weather.domain.repository.Repository
import ru.yellowshark.dsr_weather.ui.locations.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class AllLocationsViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {
    val location: LiveData<Location>
        get() = _location
    private val _location = MutableLiveData<Location>()

    fun updateLocations() {
        disposables.add(repository.getLocations()
            .subscribe { list ->
                list.forEach { updateTemperature(it) }
            }
        )
    }

    private fun updateTemperature(location: Location) {
        disposables.add(
            repository.getForecast(location.city)
                .subscribe({ writeInDb(it, location) }, { it.printStackTrace() })
        )
    }

    private fun writeInDb(forecast: Forecast, location: Location) {
        disposables.add(
            repository.updateLocationTemp(forecast.id, forecast.temperature)
                .subscribe({
                    _location.value = location.apply { temp = forecast.temperature }
                }, { it.printStackTrace() })
        )
    }
}