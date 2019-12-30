package me.manulorenzo.citymaps

import me.manulorenzo.citymaps.data.City

interface Repository {
    fun getCities(): List<City>
}
