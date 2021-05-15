package ru.yellowshark.dsr_weather.ui.locations.add.details

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import ru.yellowshark.dsr_weather.domain.model.Location
import ru.yellowshark.dsr_weather.domain.repository.Repository
import ru.yellowshark.dsr_weather.ui.locations.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {

    fun setNextDay(hasNextDayForecast: Boolean) {
        repository.setNextDay(hasNextDayForecast)
    }

    fun saveLocation() {
        disposables.add(repository.saveLocation().subscribe())
    }
}