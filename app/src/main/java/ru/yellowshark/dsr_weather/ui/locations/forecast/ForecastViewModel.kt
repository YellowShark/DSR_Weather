package ru.yellowshark.dsr_weather.ui.locations.forecast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import ru.yellowshark.dsr_weather.data.remote.response.ForecastResponse
import ru.yellowshark.dsr_weather.domain.repository.Repository
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val disposables = CompositeDisposable()
    private val _forecast = MutableLiveData<ForecastResponse>()
    val forecast: LiveData<ForecastResponse>
        get() = _forecast

    fun getForecast(city: String) {
        disposables.add(
            repository.getForecast(city)
                .subscribe({ onSuccess(it) }, { onError(it) })
        )
    }

    private fun onSuccess(response: ForecastResponse) {
        _forecast.value = response
    }

    private fun onError(t: Throwable) {
        t.printStackTrace()
    }
}