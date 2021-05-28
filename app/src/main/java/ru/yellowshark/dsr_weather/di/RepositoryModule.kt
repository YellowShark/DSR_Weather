package ru.yellowshark.dsr_weather.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.yellowshark.dsr_weather.data.RepositoryImpl
import ru.yellowshark.dsr_weather.data.db.dao.LocationsDao
import ru.yellowshark.dsr_weather.data.db.dao.TriggersDao
import ru.yellowshark.dsr_weather.data.other.UnitManager
import ru.yellowshark.dsr_weather.data.remote.api.ForecastApi
import ru.yellowshark.dsr_weather.data.remote.api.TriggersApi
import ru.yellowshark.dsr_weather.domain.mapper.*
import ru.yellowshark.dsr_weather.domain.repository.Repository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
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
        triggerMapper: TriggerMapper,
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
            triggerMapper,
            localTriggerMapper,
            )
    }

    @Provides
    fun provideNetworkForecastMapper(@ApplicationContext context: Context, unitManager: UnitManager) =
        NetworkForecastMapper(context, unitManager)

    @Provides
    fun provideNetworkShortForecastMapper(unitManager: UnitManager) = NetworkShortForecastMapper(unitManager)

    @Provides
    fun provideLocalLocationMapper() = LocalLocationMapper()

    @Provides
    fun providePostTriggerMapper(unitManager: UnitManager) = PostTriggerMapper(unitManager)

    @Provides
    fun provideTriggerMapper() = TriggerMapper()

    @Provides
    fun provideLocalTriggerMapper() = LocalTriggerMapper()
}