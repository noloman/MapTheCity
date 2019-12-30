package me.manulorenzo.citymaps.city.data.source

import me.manulorenzo.citymaps.city.data.City

interface Repository {
    fun getCities(): List<City>
}
