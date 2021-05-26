package ru.yellowshark.dsr_weather.data

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.yellowshark.dsr_weather.data.db.dao.LocationsDao
import ru.yellowshark.dsr_weather.data.remote.api.ForecastApi
import ru.yellowshark.dsr_weather.data.remote.api.TriggersApi
import ru.yellowshark.dsr_weather.data.remote.response.TriggerResponse
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
    private val dao: LocationsDao,
    private val networkMapper: NetworkForecastMapper,
    private val networkShortForecastMapper: NetworkShortForecastMapper,
    private val localLocationMapper: LocalLocationMapper,
    private val postTriggerMapper: PostTriggerMapper,
    private val triggerMapper: TriggerMapper
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

    override fun deleteLocation(locationId: Int): Completable {
        return dao.deleteLocation(locationId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun updateLocationTemp(locationId: Int, newTemps: List<String>): Completable {
        return dao.updateLocationTemps(locationId, newTemps[0], newTemps[1])
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getFavoriteLocations(): Single<List<Location>> {
        return dao.getFavoriteLocations()
            .map {
                it.map { entity -> localLocationMapper.toDomain(entity) }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun updateIsFavorite(locationId: Int, newValue: Boolean): Completable {
        return dao.updateIsFavorite(locationId, newValue)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun saveTrigger(trigger: Trigger): Single<TriggerResponse> {
        return triggersApi.saveTrigger(postTriggerMapper.fromDomain(trigger))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getTriggers(): Observable<List<Trigger>> {
        return triggersApi.getTriggers()
            .map { list -> list.map { triggerMapper.toDomain(it) } }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}