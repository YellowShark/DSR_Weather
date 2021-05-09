package ru.yellowshark.dsr_weather.domain.mapper

import ru.yellowshark.dsr_weather.data.remote.response.ForecastResponse
import ru.yellowshark.dsr_weather.domain.model.Forecast
import ru.yellowshark.dsr_weather.utils.Mapper
import java.text.SimpleDateFormat
import java.util.*

class NetworkForecastMapper : Mapper<Forecast, ForecastResponse> {
    override fun toDomain(dto: ForecastResponse): Forecast {
        with(dto) {
            val sdf = SimpleDateFormat("HH:mm\nE, dd MMM.", Locale.getDefault())
            val date = sdf.format(Date(System.currentTimeMillis()))
            return Forecast(
                date = date,
                cityName = name,
                temperature = "${main.temp.toInt()} \u00B0C",
                humidity = "Влажность: ${main.humidity}%",
                icon = weather[0].icon,
                description = weather[0].description,
                pressure = "Давление: ${main.pressure} мм рт. ст.",
                wind = "Ветер: ${wind.speed} м/c"
            )
        }
    }

    override fun fromDomain(domain: Forecast): ForecastResponse {
        TODO("We will not post anything")
    }
}