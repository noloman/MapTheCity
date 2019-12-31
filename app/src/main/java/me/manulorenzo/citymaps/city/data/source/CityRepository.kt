package me.manulorenzo.citymaps.city.data.source

import android.app.Application
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import me.manulorenzo.citymaps.about.AboutInfo
import me.manulorenzo.citymaps.city.data.City
import me.manulorenzo.citymaps.data.Resource
import java.lang.reflect.Type

@Mockable
class CityRepository(private val application: Application) :
    Repository {
    // TODO Wrap around a Resource
    override suspend fun getCities(): List<City> {
        val bufferReader = application.assets.open(CITIES_FILE_NAME).bufferedReader()
        val data = bufferReader.use {
            it.readText()
        }
        val gson = GsonBuilder().create()
        val type: Type = object : TypeToken<ArrayList<City?>?>() {}.type
        return gson.fromJson(data, type)
    }

    override suspend fun getAbout(): Resource<AboutInfo> {
        return try {
            val bufferReader = application.assets.open(ABOUT_FILE_NAME).bufferedReader()
            val data = bufferReader.use {
                it.readText()
            }
            val gson = GsonBuilder().create()
            val type: Type = object : TypeToken<AboutInfo?>() {}.type
            val aboutInfo: AboutInfo = gson.fromJson(data, type)
            return Resource.Success(aboutInfo)
            // TODO Catch specific exceptions
        } catch (e: Exception) {
            Resource.Error("Error retrieving object")
        }
    }

    companion object {
        private const val ABOUT_FILE_NAME = "aboutInfo.json"
        private const val CITIES_FILE_NAME = "cities.json"
    }
}