package me.manulorenzo.citymaps

import me.manulorenzo.citymaps.data.City
import me.manulorenzo.citymaps.data.Coordinates

class FakeCitiesRepository : Repository {
    override fun getCities(): List<City> =
        listOf(
            City(
                1,
                "ES",
                "Seville",
                Coordinates(37.38283, -5.97317)
            ),
            City(
                1,
                "UK",
                "London",
                Coordinates(51.509865, -0.118092)
            ),
            City(
                1,
                "ES",
                "Madrid",
                Coordinates(40.416775, -3.703790)
            ),
            City(
                1,
                "NL",
                "Amsterdam",
                Coordinates(52.370216, 4.895168)
            ),
            City(
                1,
                "AU",
                "Sydney",
                Coordinates(-33.865143, 151.209900)
            ),
            City(
                1,
                "US",
                "Denver",
                Coordinates(39.742043, -104.991531)
            )
        )
}