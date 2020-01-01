package me.manulorenzo.mapthecity.data.source

import me.manulorenzo.mapthecity.about.AboutInfo
import me.manulorenzo.mapthecity.city.data.City
import me.manulorenzo.mapthecity.data.Resource

interface Repository {
    suspend fun getCities(): Resource<List<City>>
    suspend fun getAbout(): Resource<AboutInfo>
}
