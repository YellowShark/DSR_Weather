package ru.yellowshark.dsr_weather.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.yellowshark.dsr_weather.data.RepositoryImpl
import ru.yellowshark.dsr_weather.data.db.dao.LocationsDao
import ru.yellowshark.dsr_weather.data.remote.api.ForecastApi
import ru.yellowshark.dsr_weather.domain.mapper.LocalLocationMapper
import ru.yellowshark.dsr_weather.domain.mapper.NetworkForecastMapper
import ru.yellowshark.dsr_weather.domain.mapper.NetworkShortForecastMapper
import ru.yellowshark.dsr_weather.domain.repository.Repository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideRepository(
        api: ForecastApi,
        dao: LocationsDao,
        networkMapper: NetworkForecastMapper,
        networkShortForecastMapper: NetworkShortForecastMapper,
        localLocationMapper: LocalLocationMapper,
    ): Repository {
        return RepositoryImpl(
            api,
            dao,
            networkMapper,
            networkShortForecastMapper,
            localLocationMapper
        )
    }

    @Provides
    fun provideNetworkForecastMapper(@ApplicationContext context: Context) =
        NetworkForecastMapper(context)

    @Provides
    fun provideNetworkShortForecastMapper() = NetworkShortForecastMapper()

    @Provides
    fun provideLocalLocationMapper() = LocalLocationMapper()
}