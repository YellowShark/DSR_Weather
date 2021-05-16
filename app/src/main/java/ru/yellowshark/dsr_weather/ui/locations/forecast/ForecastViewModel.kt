package ru.yellowshark.dsr_weather.ui.locations.forecast

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import ru.yellowshark.dsr_weather.data.remote.response.AllDayForecastResponse
import ru.yellowshark.dsr_weather.data.remote.response.ForecastResponse
import ru.yellowshark.dsr_weather.domain.model.Forecast
import ru.yellowshark.dsr_weather.domain.model.ShortForecast
import ru.yellowshark.dsr_weather.domain.repository.Repository
import ru.yellowshark.dsr_weather.ui.locations.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {
    private val _forecast = MutableLiveData<Forecast>()
    val forecast: LiveData<Forecast>
        get() = _forecast
    private val _fullForecast = MutableLiveData<List<ShortForecast>>()
    val fullForecast: LiveData<List<ShortForecast>>
        get() = _fullForecast

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

    private fun getForecast(city: String) {
        disposables.add(
            repository.getForecast(city)
                .subscribe(
                    { _forecast.value = it },
                    { t -> t.printStackTrace() }
                )
        )
    }

    private fun getAllDayForecast(city: String) {
        disposables.add(
            repository.getAllDayForecast(city)
                .subscribe(
                    { _fullForecast.value = it },
                    { t -> t.printStackTrace() }
                )
        )
    }

    fun getFullForecast(locationName: String) {
        getForecast(locationName)
        getAllDayForecast(locationName)
    }
}