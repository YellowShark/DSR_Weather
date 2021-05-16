package ru.yellowshark.dsr_weather.domain.repository

import io.reactivex.Completable
import io.reactivex.Single
import ru.yellowshark.dsr_weather.data.db.entity.LocationEntity
import ru.yellowshark.dsr_weather.data.remote.response.AllDayForecastResponse
import ru.yellowshark.dsr_weather.domain.model.Forecast
import ru.yellowshark.dsr_weather.domain.model.Location
import ru.yellowshark.dsr_weather.domain.model.ShortForecast

interface Repository {
    fun getForecast(city: String): Single<Forecast>

    fun getAllDayForecast(city: String): Single<List<ShortForecast>>

    fun saveLocation(): Completable

    fun getLocations(): Single<List<Location>>

    fun updateLocationTemp(locationId: Int, newTemp: Int): Completable

    fun getFavoriteLocations(): Single<List<Location>>

    fun updateIsFavorite(locationId: Int, newValue: Boolean): Completable

    fun setNextDay(hasNextDayForecast: Boolean)

    fun setLocationName(name: String)

    fun setCoordinates(lon: Long, lat: Long)
}