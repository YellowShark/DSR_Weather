package ru.yellowshark.dsr_weather.ui.triggers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.yellowshark.dsr_weather.domain.model.Trigger
import ru.yellowshark.dsr_weather.domain.repository.Repository
import ru.yellowshark.dsr_weather.ui.locations.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class TriggersViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {

    private val _triggers = MutableLiveData<List<Trigger>>()
    val triggers: LiveData<List<Trigger>>
        get() = _triggers

    fun getTriggers() {
        _triggers.value = listOf(
            Trigger("", "хороший триггер"),
            Trigger("", "похуже триггер"),
            Trigger("", "такой себе триггер"),
            Trigger("", "классный триггер"),
        )
        disposables.add(repository.getTriggers().subscribe({}, { it.printStackTrace() }))
    }

    fun saveTrigger(trigger: Trigger) {
        disposables.add(repository.saveTrigger(trigger)
            .subscribe({},{ it.printStackTrace() }))
    }
}