package ru.yellowshark.dsr_weather.data.remote.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.yellowshark.dsr_weather.data.remote.response.ForecastResponse

interface ForecastApi {
    @GET("data/2.5/weather")
    fun getForecast(@Query("q") city: String): Single<ForecastResponse>
}