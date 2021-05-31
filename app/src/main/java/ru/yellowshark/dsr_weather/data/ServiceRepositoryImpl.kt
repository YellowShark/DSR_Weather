package ru.yellowshark.dsr_weather.data

import io.reactivex.Observable
import io.reactivex.Single
import ru.yellowshark.dsr_weather.data.db.dao.LocationsDao
import ru.yellowshark.dsr_weather.data.db.dao.TriggersDao
import ru.yellowshark.dsr_weather.data.db.entity.TriggerEntity
import ru.yellowshark.dsr_weather.data.other.UnitManager
import ru.yellowshark.dsr_weather.data.remote.api.ForecastApi
import ru.yellowshark.dsr_weather.data.remote.response.ForecastResponse
import ru.yellowshark.dsr_weather.domain.mapper.LocalLocationMapper
import ru.yellowshark.dsr_weather.domain.model.Location
import ru.yellowshark.dsr_weather.domain.repository.ServiceRepository
import javax.inject.Inject

class ServiceRepositoryImpl @Inject constructor(
    private val forecastApi: ForecastApi,
    private val locationsDao: LocationsDao,
    private val triggersDao: TriggersDao,
    private val localLocationMapper: LocalLocationMapper,
    private val unitManager: UnitManager
) : ServiceRepository {

    override fun getForecast(lat: Double, lon: Double): Single<ForecastResponse> {
        return forecastApi.getForecast(lat, lon)
    }

    override fun getLocations(): Single<List<Location>> {
        return locationsDao.getLocations().map { list ->
            list.map { localLocationMapper.toDomain(it) }
        }
    }

    override fun getTriggers(): Observable<List<TriggerEntity>> {
        return triggersDao.getTriggers()
    }

    override fun getUnitSymbol(): String = unitManager.getUnitSymbol()
}