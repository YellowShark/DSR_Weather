package ru.yellowshark.dsr_weather.ui.triggers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.yellowshark.dsr_weather.domain.exception.NoConnectivityException
import ru.yellowshark.dsr_weather.domain.model.Location
import ru.yellowshark.dsr_weather.domain.model.Trigger
import ru.yellowshark.dsr_weather.domain.repository.Repository
import ru.yellowshark.dsr_weather.ui.locations.base.BaseViewModel
import ru.yellowshark.dsr_weather.utils.Event
import javax.inject.Inject

@HiltViewModel
class TriggersViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {

    val chosenLocation: LiveData<Location?>
        get() = _chosenLocation
    private val _chosenLocation = MutableLiveData<Location?>()
    val locations: LiveData<List<Location>>
        get() = _locations
    private val _locations = MutableLiveData<List<Location>>()
    val triggers: LiveData<List<Trigger>>
        get() = _triggers
    private val _triggers = MutableLiveData<List<Trigger>>()
    val event: LiveData<Event?>
        get() = _event
    private val _event = MutableLiveData<Event?>()
    val trigger: LiveData<Trigger?>
        get() = _trigger
    private val _trigger = MutableLiveData<Trigger?>()

    init {
        getLocations()
    }

    private fun onError(t: Throwable) {
        t.printStackTrace()
        if (t is NoConnectivityException)
            _event.value = Event.NO_INTERNET
        else
            _event.value = Event.UNKNOWN_ERROR
    }

    private fun getLocations() {
        disposables.add(
            repository.getLocations().subscribe(
                { _locations.value = it },
                { onError(it) }
            )
        )
    }

    private fun saveLocal(triggerId: String, trigger: Trigger) {
        disposables.add(
            repository.saveTriggerLocal(trigger.apply { id = triggerId })
                .subscribe { _event.value = Event.SUCCESS }
        )
    }

    private fun deleteLocal(triggerId: String) {
        disposables.add(repository.deleteLocalTrigger(triggerId).subscribe())
    }

    fun getTriggers() {
        disposables.add(
            repository.getTriggers().subscribe({ _triggers.value = it }, { onError(it) })
        )
    }

    fun getTriggerById(triggerId: String) {
        disposables.add(
            repository.getTriggerById(triggerId)
                .subscribe(
                    { _trigger.value = it },
                    { onError(it) }
                )
        )
    }

    fun saveTrigger(trigger: Trigger) {
        if (trigger.id.isNotEmpty())
            saveLocal(trigger.id, trigger)
        else
            disposables.add(
                repository.saveTrigger(trigger)
                    .subscribe(
                        { id -> saveLocal(id, trigger) },
                        { onError(it) }
                    )
            )
    }

    fun deleteTrigger(id: String) {
        disposables.add(
            repository.deleteTrigger(id)
                .doOnSubscribe { _event.value = Event.LOADING }
                .subscribe(
                    { _event.value = Event.SUCCESS },
                    {
                        _event.value = Event.SUCCESS
                        deleteLocal(id)
                    }
                )
        )
    }

    fun setLocation(location: Location) {
        _chosenLocation.value = location
    }

    fun clearData() {
        _event.value = null
        _trigger.value = null
        _chosenLocation.value = null
    }
}