package ru.yellowshark.dsr_weather.ui.triggers

import android.util.Log
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
        disposables.add(
            repository.getTriggers().subscribe({ _triggers.value = it }, { it.printStackTrace() })
        )
        disposables.add(repository.requestAlerts()
            .subscribe(
                {
                    Log.d("TAG", "getTriggers: $it")
                },
                { it.printStackTrace() }
            )
        )
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
            repository.deleteTrigger(id).subscribe(
                {
                    _event.value = Event.SUCCESS
                },
                {
                    if (it.message == "timeout") {
                        deleteLocal(id)
                        _event.value = Event.SUCCESS
                    } else {
                        it.printStackTrace()
                        _event.value = Event.UNKNOWN_ERROR
                    }
                }
            )
        )
    }

    private fun deleteLocal(triggerId: String) {
        disposables.add(repository.deleteLocalTrigger(triggerId).subscribe())
    }

    fun clearData() {
        _event.value = null
    }
}