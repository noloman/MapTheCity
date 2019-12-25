package me.manulorenzo.citymaps

import me.manulorenzo.citymaps.entity.CityDao
import me.manulorenzo.citymaps.entity.CityEntity

class CitiesRepository(private val cityDao: CityDao) {
    val allCities = cityDao.getAll()

    suspend fun insertAll(city: List<CityEntity>) {
        cityDao.insertAll(city)
    }
}