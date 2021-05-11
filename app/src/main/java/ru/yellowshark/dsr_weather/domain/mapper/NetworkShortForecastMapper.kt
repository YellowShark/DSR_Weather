package ru.yellowshark.dsr_weather.domain.mapper

import ru.yellowshark.dsr_weather.data.remote.response.Forecast
import ru.yellowshark.dsr_weather.domain.model.ShortForecast
import ru.yellowshark.dsr_weather.utils.Mapper
import ru.yellowshark.dsr_weather.utils.WEATHER_ICON_URL_POSTFIX
import ru.yellowshark.dsr_weather.utils.WEATHER_ICON_URL_PREFIX
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

private fun String.toShortTimeFormat(): String {
    var sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    var date: Date? = null
    try {
        date = sdf.parse(this)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    var millis: Long = 0
    if (date != null) {
        millis = date.time
    }
    sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    return sdf.format(Date(millis))
}

class NetworkShortForecastMapper : Mapper<ShortForecast, Forecast> {
    override fun toDomain(dto: Forecast): ShortForecast {
        with(dto) {
            return ShortForecast(
                time = dtTxt.toShortTimeFormat(),
                temp = "${main.temp.toInt()} \u00B0C",
                icon = "$WEATHER_ICON_URL_PREFIX${weather[0].icon}$WEATHER_ICON_URL_POSTFIX",
                humidity = "${main.humidity}%"
            )
        }
    }

    override fun fromDomain(domain: ShortForecast): Forecast {
        TODO("We will not post anything")
    }

}