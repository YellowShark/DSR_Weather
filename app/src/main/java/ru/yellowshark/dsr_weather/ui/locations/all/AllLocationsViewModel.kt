package ru.yellowshark.dsr_weather.ui.locations.all

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.yellowshark.dsr_weather.domain.model.Forecast
import ru.yellowshark.dsr_weather.domain.model.Location
import ru.yellowshark.dsr_weather.domain.repository.Repository
import ru.yellowshark.dsr_weather.ui.locations.base.BaseViewModel
import ru.yellowshark.dsr_weather.utils.Event
import javax.inject.Inject

@HiltViewModel
class AllLocationsViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {

    val location: LiveData<Location>
        get() = _location
    private val _location = MutableLiveData<Location>()
    val event: LiveData<Event>
        get() = _event
    private val _event = MutableLiveData<Event>()

    fun updateIsFavorite(locationId: Int, newValue: Boolean) {
        disposables.add(repository.updateIsFavorite(locationId, newValue).subscribe())
    }

    fun updateLocations() {
        disposables.add(repository.getLocations()
            .doOnSubscribe { _event.value = Event.LOADING }
            .subscribe { list ->
                if (list.isEmpty())
                    _event.value = Event.EMPTY
                else
                    list.forEach { updateTemperature(it) }
            }
        )
    }

    private fun updateTemperature(location: Location) {
        disposables.add(
            repository.getForecast(location.city)
                .subscribe({ writeInDbAndShowResults(it, location) }, { onError(it) })
        )
    }

    private fun writeInDbAndShowResults(forecast: Forecast, location: Location) {
        disposables.add(
            repository.updateLocationTemp(forecast.id, forecast.temperature)
                .subscribe({
                    _event.value = Event.SUCCESS
                    _location.value = location.apply { temp = forecast.temperature }
                }, { onError(it) })
        )
    }

    private fun onError(t: Throwable) {
        t.printStackTrace()
        _event.value = Event.ERROR
    }
}