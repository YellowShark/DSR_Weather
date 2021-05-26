package ru.yellowshark.dsr_weather.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.yellowshark.dsr_weather.data.RepositoryImpl
import ru.yellowshark.dsr_weather.data.db.dao.LocationsDao
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
        dao: LocationsDao,
        networkMapper: NetworkForecastMapper,
        networkShortForecastMapper: NetworkShortForecastMapper,
        localLocationMapper: LocalLocationMapper,
        postTriggerMapper: PostTriggerMapper,
        triggerMapper: TriggerMapper,
    ): Repository {
        return RepositoryImpl(
            forecastApi,
            triggersApi,
            dao,
            networkMapper,
            networkShortForecastMapper,
            localLocationMapper,
            postTriggerMapper,
            triggerMapper,
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
    fun providePostTriggerMapper() = PostTriggerMapper()

    @Provides
    fun provideTriggerMapper() = TriggerMapper()
}