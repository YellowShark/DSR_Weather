package ru.yellowshark.dsr_weather.data

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.yellowshark.dsr_weather.data.db.dao.LocationsDao
import ru.yellowshark.dsr_weather.data.db.dao.TriggersDao
import ru.yellowshark.dsr_weather.data.remote.api.ForecastApi
import ru.yellowshark.dsr_weather.data.remote.api.TriggersApi
import ru.yellowshark.dsr_weather.domain.mapper.*
import ru.yellowshark.dsr_weather.domain.model.Forecast
import ru.yellowshark.dsr_weather.domain.model.Location
import ru.yellowshark.dsr_weather.domain.model.ShortForecast
import ru.yellowshark.dsr_weather.domain.model.Trigger
import ru.yellowshark.dsr_weather.domain.repository.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val forecastApi: ForecastApi,
    private val triggersApi: TriggersApi,
    private val locationsDao: LocationsDao,
    private val triggersDao: TriggersDao,
    private val networkMapper: NetworkForecastMapper,
    private val networkShortForecastMapper: NetworkShortForecastMapper,
    private val localLocationMapper: LocalLocationMapper,
    private val postTriggerMapper: PostTriggerMapper,
    private val localTriggerMapper: LocalTriggerMapper
) : Repository {

    override fun getForecast(lat: Double, lon: Double): Single<Forecast> {
        return forecastApi.getForecast(lat, lon)
            .map { networkMapper.toDomain(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getAllDayForecast(lat: Double, lon: Double): Single<List<ShortForecast>> {
        return forecastApi.getAllDayForecast(lat, lon)
            .map {
                it.list.map { forecast ->
                    networkShortForecastMapper.toDomain(forecast)
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getTomorrowForecast(lat: Double, lon: Double): Single<ShortForecast> {
        return forecastApi.getTwoDaysForecast(lat, lon)
            .subscribeOn(Schedulers.io())
            .map {
                it.list.map { forecast ->
                    networkShortForecastMapper.toDomain(forecast)
                }
            }
            .flatMap { Observable.fromIterable(it) }
            .filter { it.time == "12:00" }
            .lastOrError()
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun saveLocation(newLocation: Location): Completable {
        return locationsDao.insertLocation(localLocationMapper.fromDomain(newLocation))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getLocations(): Single<List<Location>> {
        return locationsDao.getLocations()
            .map {
                it.map { entity -> localLocationMapper.toDomain(entity) }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun deleteLocation(locationId: Int): Completable {
        return locationsDao.deleteLocation(locationId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun updateLocationTemp(locationId: Int, newTemps: List<String>): Completable {
        return locationsDao.updateLocationTemps(locationId, newTemps[0], newTemps[1])
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getFavoriteLocations(): Single<List<Location>> {
        return locationsDao.getFavoriteLocations()
            .map {
                it.map { entity -> localLocationMapper.toDomain(entity) }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun updateIsFavorite(locationId: Int, newValue: Boolean): Completable {
        return locationsDao.updateIsFavorite(locationId, newValue)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun saveTrigger(trigger: Trigger): Single<String> {
        return triggersApi.saveTrigger(postTriggerMapper.fromDomain(trigger))
            .map { it.id }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getTriggerById(triggerId: String): Single<Trigger> {
        return triggersDao.getTriggerById(triggerId)
            .map { localTriggerMapper.toDomain(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun deleteTrigger(triggerId: String): Completable {
        return triggersApi.deleteTrigger(triggerId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun saveTriggerLocal(trigger: Trigger): Completable {
        return triggersDao.insertTrigger(localTriggerMapper.fromDomain(trigger))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getTriggers(): Observable<List<Trigger>> {
        return triggersDao.getTriggers()
            .map { list -> list.map { localTriggerMapper.toDomain(it) } }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun deleteLocalTrigger(id: String): Completable {
        return triggersDao.deleteTrigger(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}