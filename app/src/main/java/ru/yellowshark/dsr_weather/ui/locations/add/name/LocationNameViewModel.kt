package ru.yellowshark.dsr_weather.ui.locations.add.name

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.yellowshark.dsr_weather.domain.repository.Repository
import ru.yellowshark.dsr_weather.ui.locations.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class LocationNameViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {

    fun setLocationName(name: String) {
        repository.setLocationName(name)
    }
}