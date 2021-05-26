package ru.yellowshark.dsr_weather.domain.mapper

import ru.yellowshark.dsr_weather.data.remote.request.*
import ru.yellowshark.dsr_weather.domain.model.Trigger
import ru.yellowshark.dsr_weather.utils.Mapper

class PostTriggerMapper : Mapper<Trigger, AddTriggerRequest> {
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
                        "MultiPoint"
                    )
                )
            }

            val conditions = arrayListOf<Condition>()
            temp?.let {
                conditions.add(
                    Condition(
                        it,
                        "\$gte",
                        "temp"
                    )
                )
            }
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
            return AddTriggerRequest(
                area = areasList,
                conditions = conditions,
                TimePeriod(
                    start = Start(startMillis.toInt(), "after"),
                    end = End(endMillis.toInt(), "after")
                )
            )
        }
    }
}