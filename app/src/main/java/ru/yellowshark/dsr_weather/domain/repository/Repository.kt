package ru.yellowshark.dsr_weather.domain.repository

import io.reactivex.Completable
import io.reactivex.Single
import ru.yellowshark.dsr_weather.domain.model.Forecast
import ru.yellowshark.dsr_weather.domain.model.Location
import ru.yellowshark.dsr_weather.domain.model.ShortForecast

interface Repository {
    fun getForecast(lat: Double, lon: Double): Single<Forecast>

    fun getAllDayForecast(lat: Double, lon: Double): Single<List<ShortForecast>>

    fun getTomorrowForecast(lat: Double, lon: Double): Single<ShortForecast>

    fun saveLocation(): Completable

    fun getLocations(): Single<List<Location>>

    fun updateLocationTemp(locationId: Int, newTemps: List<String>): Completable

    fun getFavoriteLocations(): Single<List<Location>>

    fun updateIsFavorite(locationId: Int, newValue: Boolean): Completable

    fun setNextDay(hasNextDayForecast: Boolean)

    fun setLocationName(name: String)

    fun setCoordinates(lon: Double, lat: Double)

    fun getUnit(): String
}