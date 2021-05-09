package ru.yellowshark.dsr_weather.domain.mapper

import ru.yellowshark.dsr_weather.data.remote.response.ForecastResponse
import ru.yellowshark.dsr_weather.domain.model.Forecast
import ru.yellowshark.dsr_weather.utils.Mapper

class NetworkForecastMapper : Mapper<Forecast, ForecastResponse> {
    override fun toDomain(dto: ForecastResponse): Forecast {
        with(dto) {
            return Forecast(
                temperature = main.temp.toString(),
                humidity = main.humidity.toString(),
                icon = weather[0].icon,
                description = weather[0].description,
                pressure = main.pressure.toString(),
                windSpeed = wind.speed.toString(),
                windDirection = wind.deg.toString()
            )
        }
    }

    override fun fromDomain(domain: Forecast): ForecastResponse {
        TODO("We will not post anything")
    }
}