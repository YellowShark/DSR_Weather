package ru.yellowshark.dsr_weather.domain.mapper

import ru.yellowshark.dsr_weather.data.db.entity.LocationEntity
import ru.yellowshark.dsr_weather.domain.model.Location
import ru.yellowshark.dsr_weather.utils.Mapper

class LocalLocationMapper : Mapper<Location, LocationEntity> {
    override fun toDomain(dto: LocationEntity): Location =
        with(dto) {
            Location(id, temp, city, lat, lon, hasNextDayForecast, isFavorite)
        }

    override fun fromDomain(domain: Location): LocationEntity =
        with(domain) {
            LocationEntity(id, temp, city, lat, lon, hasNextDayForecast, isFavorite)
        }
}