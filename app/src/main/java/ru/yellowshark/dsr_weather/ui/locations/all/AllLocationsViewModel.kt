package ru.yellowshark.dsr_weather.ui.locations.all

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.functions.BiFunction
import ru.yellowshark.dsr_weather.domain.exception.NoConnectivityException
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
        if (location.hasNextDayForecast)
            loadWithNextDayForecast(location)
        else
            loadCurrentForecast(location)
    }

    private fun loadWithNextDayForecast(location: Location) {
        disposables.add(
            repository.getForecast(location.city).zipWith(
                repository.getTomorrowForecast(location.city),
                BiFunction { today, tomorrow ->
                    return@BiFunction listOf(today.temperature, tomorrow.temp)
                })
                .subscribe({
                    writeInDbAndShowResults(it, location)
                }, { onError(it) })
        )
    }

    private fun loadCurrentForecast(location: Location) {
        repository.getForecast(location.city)
            .subscribe({ writeInDbAndShowResults(listOf(it.temperature, ""), location) }, { onError(it) })
    }

    private fun writeInDbAndShowResults(forecasts: List<String>, location: Location) {
        disposables.add(
            repository.updateLocationTemp(location.id, forecasts)
                .subscribe({
                    _event.value = Event.SUCCESS
                    _location.value = location.apply {
                        temp = forecasts[0]
                        futureTemp = forecasts[1]
                    }
                }, { onError(it) })
        )
    }

    private fun onError(t: Throwable) {
        t.printStackTrace()
        if (_event.value != Event.NO_INTERNET && _event.value != Event.UNKNOWN_ERROR) {
            if (t is NoConnectivityException)
                _event.value = Event.NO_INTERNET
            else
                _event.value = Event.UNKNOWN_ERROR
            showLocalData()
        }
    }

    private fun showLocalData() {
        disposables.add(repository.getLocations()
            .subscribe { list ->
                list.forEach { _location.value = it }
            })
    }
}