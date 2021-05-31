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
    val toolbarTitle: LiveData<String>
        get() = _toolbarTitle
    private val _toolbarTitle = MutableLiveData<String>()
    val metric: LiveData<String?>
        get() = _metric
    private val _metric = MutableLiveData<String?>()

    fun updateToolbarTitle(title: String) {
        _toolbarTitle.value = title
    }

    fun selectMetric(measure: String?) {
        measure?.let { unitManager.setUnit(it) }
        _metric.value = measure
    }
}