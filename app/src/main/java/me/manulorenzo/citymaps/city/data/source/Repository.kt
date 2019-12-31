package me.manulorenzo.citymaps.city.data.source

import me.manulorenzo.citymaps.about.AboutInfo
import me.manulorenzo.citymaps.city.data.City
import me.manulorenzo.citymaps.data.Resource

interface Repository {
    suspend fun getCities(): List<City>
    suspend fun getAbout(): Resource<AboutInfo>
}
