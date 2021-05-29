package ru.yellowshark.dsr_weather.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.yellowshark.dsr_weather.data.other.UnitManager
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val unitManager: UnitManager
) : ViewModel() {
    val metric: LiveData<String?>
        get() = _metric
    private val _metric = MutableLiveData<String?>()

    fun selectMetric(measure: String?) {
        measure?.let { unitManager.setUnit(it) }
        _metric.value = measure
    }
}