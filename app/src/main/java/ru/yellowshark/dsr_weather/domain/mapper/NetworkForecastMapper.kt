package ru.yellowshark.dsr_weather.domain.mapper

import android.content.Context
import ru.yellowshark.dsr_weather.R
import ru.yellowshark.dsr_weather.data.other.UnitManager
import ru.yellowshark.dsr_weather.data.remote.response.ForecastResponse
import ru.yellowshark.dsr_weather.domain.model.Forecast
import ru.yellowshark.dsr_weather.utils.Mapper
import ru.yellowshark.dsr_weather.utils.WEATHER_ICON_URL_POSTFIX
import ru.yellowshark.dsr_weather.utils.WEATHER_ICON_URL_PREFIX
import java.text.SimpleDateFormat
import java.util.*

class NetworkForecastMapper(
    private val context: Context,
    private val unitManager: UnitManager
) : Mapper<Forecast, ForecastResponse> {
    override fun toDomain(dto: ForecastResponse): Forecast {
        with(dto) {
            val sdf = SimpleDateFormat("HH:mm\nE, dd MMM.", Locale.getDefault())
            val date = sdf.format(Date(System.currentTimeMillis()))
            return Forecast(
                id = 0,
                date = date,
                cityName = name,
                temperature = "${main.temp.toInt()} ${unitManager.getUnitSymbol()}",
                humidity = "${context.getString(R.string.humidity)} ${main.humidity}%",
                icon = "$WEATHER_ICON_URL_PREFIX${weather[0].icon}$WEATHER_ICON_URL_POSTFIX",
                description = weather[0].description,
                pressure = "${context.getString(R.string.pressure)} ${main.pressure} ${context.getString(R.string.mm_hg)}",
                wind = "${context.getString(R.string.wind)} ${wind.speed} ${context.getString(R.string.m_s)}"
            )
        }
    }

    override fun fromDomain(domain: Forecast): ForecastResponse {
        TODO("We will not post anything")
    }
}