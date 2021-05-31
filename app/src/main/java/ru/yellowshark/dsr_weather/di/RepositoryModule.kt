package ru.yellowshark.dsr_weather.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.yellowshark.dsr_weather.data.RepositoryImpl
import ru.yellowshark.dsr_weather.data.ServiceRepositoryImpl
import ru.yellowshark.dsr_weather.data.db.dao.LocationsDao
import ru.yellowshark.dsr_weather.data.db.dao.TriggersDao
import ru.yellowshark.dsr_weather.data.other.UnitManager
import ru.yellowshark.dsr_weather.data.remote.api.ForecastApi
import ru.yellowshark.dsr_weather.data.remote.api.TriggersApi
import ru.yellowshark.dsr_weather.domain.mapper.*
import ru.yellowshark.dsr_weather.domain.repository.Repository
import ru.yellowshark.dsr_weather.domain.repository.ServiceRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideServiceRepository(
        forecastApi: ForecastApi,
        locationsDao: LocationsDao,
        triggersDao: TriggersDao,
        localLocationMapper: LocalLocationMapper,
        localTriggerMapper: LocalTriggerMapper,
        unitManager: UnitManager
    ): ServiceRepository {
        return ServiceRepositoryImpl(
            forecastApi,
            locationsDao,
            triggersDao,
            localLocationMapper,
            localTriggerMapper,
            unitManager
        )
    }

    @Provides
    @Singleton
    fun provideRepository(
        forecastApi: ForecastApi,
        triggersApi: TriggersApi,
        locationsDao: LocationsDao,
        triggersDao: TriggersDao,
        networkMapper: NetworkForecastMapper,
        networkShortForecastMapper: NetworkShortForecastMapper,
        localLocationMapper: LocalLocationMapper,
        postTriggerMapper: PostTriggerMapper,
        alertMapper: AlertMapper,
        localTriggerMapper: LocalTriggerMapper,
    ): Repository {
        return RepositoryImpl(
            forecastApi,
            triggersApi,
            locationsDao,
            triggersDao,
            networkMapper,
            networkShortForecastMapper,
            localLocationMapper,
            postTriggerMapper,
            alertMapper,
            localTriggerMapper,
        )
    }

    @Provides
    fun provideNetworkForecastMapper(
        @ApplicationContext context: Context,
        unitManager: UnitManager
    ) =
        NetworkForecastMapper(context, unitManager)

    @Provides
    fun provideNetworkShortForecastMapper(unitManager: UnitManager) =
        NetworkShortForecastMapper(unitManager)

    @Provides
    fun provideLocalLocationMapper() = LocalLocationMapper()

    @Provides
    fun providePostTriggerMapper(unitManager: UnitManager) = PostTriggerMapper(unitManager)

    @Provides
    fun provideTriggerMapper() = AlertMapper()

    @Provides
    fun provideLocalTriggerMapper() = LocalTriggerMapper()
}