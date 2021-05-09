package ru.yellowshark.dsr_weather.domain.repository

import io.reactivex.Single
import ru.yellowshark.dsr_weather.data.remote.response.ForecastResponse
import ru.yellowshark.dsr_weather.domain.model.Forecast

interface Repository {
    fun getForecast(city: String): Single<Forecast>
}