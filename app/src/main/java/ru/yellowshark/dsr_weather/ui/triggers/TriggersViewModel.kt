package ru.yellowshark.dsr_weather.ui.triggers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.yellowshark.dsr_weather.domain.model.Trigger
import ru.yellowshark.dsr_weather.domain.repository.Repository
import javax.inject.Inject

@HiltViewModel
class TriggersViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val _triggers = MutableLiveData<List<Trigger>>()
    val triggers: LiveData<List<Trigger>>
        get() = _triggers

    init {
        getTriggers()
    }

    private fun getTriggers() {
        _triggers.value = listOf(
            Trigger("хороший триггер"),
            Trigger("похуже триггер"),
            Trigger("такой себе триггер"),
            Trigger("классный триггер"),
        )
    }
}