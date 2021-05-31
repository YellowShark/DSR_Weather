package ru.yellowshark.dsr_weather.domain.repository

import io.reactivex.Observable
import io.reactivex.Single
import ru.yellowshark.dsr_weather.data.db.entity.TriggerEntity
import ru.yellowshark.dsr_weather.data.remote.response.ForecastResponse
import ru.yellowshark.dsr_weather.domain.model.Location

interface ServiceRepository {
    fun getForecast(lat: Double, lon: Double): Single<ForecastResponse>

    fun getLocations(): Single<List<Location>>

    fun getTriggers(): Observable<List<TriggerEntity>>

    fun getUnitSymbol(): String
}