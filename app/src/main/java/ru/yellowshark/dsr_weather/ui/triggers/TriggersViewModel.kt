package ru.yellowshark.dsr_weather.ui.triggers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.yellowshark.dsr_weather.domain.exception.NoConnectivityException
import ru.yellowshark.dsr_weather.domain.model.Trigger
import ru.yellowshark.dsr_weather.domain.repository.Repository
import ru.yellowshark.dsr_weather.ui.locations.base.BaseViewModel
import ru.yellowshark.dsr_weather.utils.Event
import javax.inject.Inject

@HiltViewModel
class TriggersViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {

    private val _triggers = MutableLiveData<List<Trigger>>()
    val triggers: LiveData<List<Trigger>>
        get() = _triggers
    val event: LiveData<Event?>
        get() = _event
    private val _event = MutableLiveData<Event?>()

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
        disposables.add(
            repository.saveTrigger(trigger)
                .subscribe(
                    {
                        saveLocal(it.id, trigger)
                        _event.value = Event.SUCCESS
                    },
                    {
                        it.printStackTrace()
                        if (it is NoConnectivityException)
                            _event.value = Event.NO_INTERNET
                        else
                            _event.value = Event.UNKNOWN_ERROR
                    }
                )
        )
    }

    private fun saveLocal(triggerId: String, trigger: Trigger) {
        disposables.add(
            repository.saveTriggerLocal(trigger.apply { id = triggerId }).subscribe()
        )
    }

    fun deleteTrigger(id: String) {
        disposables.add(
            repository.deleteLocalTrigger(id).subscribe(
                { _event.value = Event.SUCCESS },
                { _event.value = Event.UNKNOWN_ERROR }
            )
        )
    }

    fun clearData() {
        _event.value = null
    }
}