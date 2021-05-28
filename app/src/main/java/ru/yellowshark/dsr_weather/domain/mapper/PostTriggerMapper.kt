package ru.yellowshark.dsr_weather.domain.mapper

import ru.yellowshark.dsr_weather.data.other.UnitManager
import ru.yellowshark.dsr_weather.data.remote.request.*
import ru.yellowshark.dsr_weather.domain.model.Trigger
import ru.yellowshark.dsr_weather.utils.DateConverter
import ru.yellowshark.dsr_weather.utils.METRIC_UNITS
import ru.yellowshark.dsr_weather.utils.Mapper

class PostTriggerMapper(
    private val unitManager: UnitManager
) : Mapper<Trigger, AddTriggerRequest> {
    override fun toDomain(dto: AddTriggerRequest): Trigger {
        TODO("Not yet implemented")
    }

    override fun fromDomain(domain: Trigger): AddTriggerRequest {
        with(domain) {
            val areasList = arrayListOf<Area>()
            areas.forEach {
                areasList.add(
                    Area(
                        listOf(it.lat.toInt(), it.lon.toInt()),
                        "Point"
                    )
                )
            }

            val conditions = arrayListOf<Condition>()
            conditions.add(
                Condition(
                    if (unitManager.getUnit() == METRIC_UNITS) temp + 273 else (temp - 32) * (5 / 9) + 273,
                    "\$gte",
                    "temp"
                )
            )
            wind?.let {
                conditions.add(
                    Condition(
                        it,
                        "\$gte",
                        "wind_speed"
                    )
                )
            }
            humidity?.let {
                conditions.add(
                    Condition(
                        it,
                        "\$gte",
                        "humidity"
                    )
                )
            }
            val startMillis: Long =
                if (startDate.isEmpty()) 0 else DateConverter.parseString(startDate) - System.currentTimeMillis()
            val endMillis: Long =
                if (endDate.isEmpty()) 24 * 60 * 60 * 1000 else DateConverter.parseString(startDate) - System.currentTimeMillis()
            return AddTriggerRequest(
                area = areasList,
                conditions = conditions,
                TimePeriod(
                    start = Start(startMillis, "after"),
                    end = End(endMillis, "after")
                )
            )
        }
    }
}