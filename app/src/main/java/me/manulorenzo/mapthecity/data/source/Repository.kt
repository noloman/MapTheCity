package me.manulorenzo.mapthecity.data.source

import me.manulorenzo.mapthecity.data.Resource
import me.manulorenzo.mapthecity.ui.about.AboutInfo
import me.manulorenzo.mapthecity.ui.city.data.City

interface Repository {
    suspend fun getCities(): Resource<List<City>>
    suspend fun getAbout(): Resource<AboutInfo>
}
