package ru.yellowshark.dsr_weather.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val metric: LiveData<String?>
        get() = _metric
    private val _metric = MutableLiveData<String?>()

    fun selectMetric(metric: String?) {
        _metric.value = metric
    }
}