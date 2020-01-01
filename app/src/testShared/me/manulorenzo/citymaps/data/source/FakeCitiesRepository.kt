package me.manulorenzo.citymaps.data.source

import me.manulorenzo.citymaps.about.AboutInfo
import me.manulorenzo.citymaps.city.data.City
import me.manulorenzo.citymaps.city.data.Coordinates
import me.manulorenzo.citymaps.data.Resource

class FakeCitiesRepository :
    Repository {
    override suspend fun getCities(): Resource<List<City>> =
        Resource.Success(
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
        )

    override suspend fun getAbout(): Resource<AboutInfo> =
        Resource.Success(
            AboutInfo(
                companyName = "Backbase",
                companyAddress = "Jacob Bontiusplaats 9",
                companyPostal = "1018 LL",
                companyCity = "Amsterdam",
                aboutInfo = "This is the Backbase assignment for Android engineering positions."
            )
        )
}