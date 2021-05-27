package ru.yellowshark.dsr_weather.domain.repository

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import ru.yellowshark.dsr_weather.data.remote.response.TriggerResponse
import ru.yellowshark.dsr_weather.domain.model.Forecast
import ru.yellowshark.dsr_weather.domain.model.Location
import ru.yellowshark.dsr_weather.domain.model.ShortForecast
import ru.yellowshark.dsr_weather.domain.model.Trigger

interface Repository {
    fun getForecast(lat: Double, lon: Double): Single<Forecast>

    fun getAllDayForecast(lat: Double, lon: Double): Single<List<ShortForecast>>

    fun getTomorrowForecast(lat: Double, lon: Double): Single<ShortForecast>

    fun saveLocation(newLocation: Location): Completable

    fun getLocations(): Single<List<Location>>

    fun deleteLocation(locationId: Int): Completable

    fun updateLocationTemp(locationId: Int, newTemps: List<String>): Completable

    fun getFavoriteLocations(): Single<List<Location>>

    fun updateIsFavorite(locationId: Int, newValue: Boolean): Completable

    fun saveTrigger(trigger: Trigger): Single<TriggerResponse>

    fun requestAlerts(): Observable<List<Trigger>>

    fun saveTriggerLocal(trigger: Trigger): Completable

    fun getTriggers(): Single<List<Trigger>>

    fun deleteLocalTrigger(id: String): Completable
}