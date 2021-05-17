package ru.yellowshark.dsr_weather.ui.locations.forecast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.yellowshark.dsr_weather.domain.model.Forecast
import ru.yellowshark.dsr_weather.domain.model.ShortForecast
import ru.yellowshark.dsr_weather.domain.repository.Repository
import ru.yellowshark.dsr_weather.ui.locations.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {
    private val _forecast = MutableLiveData<Forecast?>()
    val forecast: LiveData<Forecast?>
        get() = _forecast
    private val _fullForecast = MutableLiveData<List<ShortForecast>?>()
    val fullForecast: LiveData<List<ShortForecast>?>
        get() = _fullForecast

    private fun getForecast(lat: Double, lon: Double) {
        disposables.add(
            repository.getForecast(lat, lon)
                .subscribe(
                    { _forecast.value = it },
                    { t -> t.printStackTrace() }
                )
        )
    }

    private fun getAllDayForecast(lat: Double, lon: Double) {
        disposables.add(
            repository.getAllDayForecast(lat, lon)
                .subscribe(
                    { _fullForecast.value = it },
                    { t -> t.printStackTrace() }
                )
        )
    }

    fun getFullForecast(lat: Double, lon: Double) {
        getForecast(lat, lon)
        getAllDayForecast(lat, lon)
    }

    fun clear() {
        _fullForecast.value = null
        _forecast.value = null
    }
}