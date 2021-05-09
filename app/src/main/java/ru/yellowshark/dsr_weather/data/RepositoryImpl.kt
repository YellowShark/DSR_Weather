package ru.yellowshark.dsr_weather.data

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.yellowshark.dsr_weather.data.remote.api.ForecastApi
import ru.yellowshark.dsr_weather.data.remote.response.ForecastResponse
import ru.yellowshark.dsr_weather.domain.repository.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: ForecastApi
) : Repository {

    override fun getForecast(city: String): Single<ForecastResponse> {
        return api.getForecast(city)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}