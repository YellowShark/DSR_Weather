package ru.yellowshark.dsr_weather.domain.repository

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import ru.yellowshark.dsr_weather.data.remote.response.Alerts
import ru.yellowshark.dsr_weather.domain.model.*

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

    fun saveTrigger(trigger: Trigger): Single<String>

    fun requestAlerts(): Observable<Map<String, Alerts>>

    fun saveTriggerLocal(trigger: Trigger): Completable

    fun getTriggers(): Observable<List<Trigger>>

    fun getTriggerById(triggerId: String): Single<Trigger>

    fun deleteTrigger(triggerId: String): Completable

    fun deleteLocalTrigger(id: String): Completable

    fun getAreas(): Single<List<Point>>
}