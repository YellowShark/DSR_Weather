package ru.yellowshark.dsr_weather.ui.locations.all

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.yellowshark.dsr_weather.domain.model.Location
import ru.yellowshark.dsr_weather.domain.repository.Repository
import ru.yellowshark.dsr_weather.ui.locations.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class AllLocationsViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {
    val locations: LiveData<List<Location>>
        get() = _locations
    private val _locations = MutableLiveData<List<Location>>()

    fun getLocations() {
        disposables.add(repository.getLocations()
            .subscribe({ _locations.value = it }, {})
        )
    }
}