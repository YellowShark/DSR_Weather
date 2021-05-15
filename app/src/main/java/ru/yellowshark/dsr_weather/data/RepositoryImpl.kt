package ru.yellowshark.dsr_weather.data

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.yellowshark.dsr_weather.data.db.dao.LocationsDao
import ru.yellowshark.dsr_weather.data.remote.api.ForecastApi
import ru.yellowshark.dsr_weather.domain.mapper.LocalLocationMapper
import ru.yellowshark.dsr_weather.domain.mapper.NetworkForecastMapper
import ru.yellowshark.dsr_weather.domain.mapper.NetworkShortForecastMapper
import ru.yellowshark.dsr_weather.domain.model.Forecast
import ru.yellowshark.dsr_weather.domain.model.Location
import ru.yellowshark.dsr_weather.domain.model.ShortForecast
import ru.yellowshark.dsr_weather.domain.repository.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: ForecastApi,
    private val dao: LocationsDao,
    private val networkMapper: NetworkForecastMapper,
    private val networkShortForecastMapper: NetworkShortForecastMapper,
    private val localLocationMapper: LocalLocationMapper
) : Repository {

    private val newLocation = Location(0, 0, "")

    override fun getForecast(city: String): Single<Forecast> {
        return api.getForecast(city)
            .map { networkMapper.toDomain(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getAllDayForecast(city: String): Single<List<ShortForecast>> {
        return api.getAllDayForecast(city)
            .map {
                it.list.map { forecast ->
                    networkShortForecastMapper.toDomain(forecast)
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun saveLocation(): Completable {
        return dao.insertLocation(localLocationMapper.fromDomain(newLocation))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getLocations(): Single<List<Location>> {
        return dao.getLocations()
            .map {
                it.map { entity -> localLocationMapper.toDomain(entity) }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun updateLocationTemp(locationId: Int, newTemp: Int): Completable {
        return dao.updateLocationTemp(locationId, newTemp)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun setNextDay(hasNextDayForecast: Boolean) {
        newLocation.hasNextDayForecast = hasNextDayForecast
    }

    override fun setLocationName(name: String) {
        newLocation.city = name
    }

    override fun setCoordinates(lon: Long, lat: Long) {
        newLocation.lon = lon
        newLocation.lat = lat
    }
}