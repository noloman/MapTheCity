package me.manulorenzo.citymaps

import me.manulorenzo.citymaps.data.City
import me.manulorenzo.citymaps.data.Coordinates
import me.manulorenzo.citymaps.entity.CityEntity


fun List<CityEntity>.toCity() = this.map { cityEntity ->
    City(
        id = cityEntity.id,
        name = cityEntity.name,
        country = cityEntity.country,
        coordinates = Coordinates(
            cityEntity.coordinates.split(", ")[0].toFloat(),
            cityEntity.coordinates.split(", ")[1].toFloat()
        )
    )
}

fun List<City>.toCityEntity() = this.map { city: City ->
    CityEntity(
        id = city.id,
        name = city.name,
        country = city.country,
        coordinates = city.coordinates.lat.toString() + ", " + city.coordinates.lon
    )
}