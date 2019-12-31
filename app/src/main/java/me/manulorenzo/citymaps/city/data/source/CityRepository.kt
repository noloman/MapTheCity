package me.manulorenzo.citymaps.city.data.source

import android.app.Application
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import me.manulorenzo.citymaps.city.data.City
import java.lang.reflect.Type

@Mockable
class CityRepository(private val application: Application) :
    Repository {
    override fun getCities(): List<City> {
        val bufferReader = application.assets.open(CITIES_FILE_NAME).bufferedReader()
        val data = bufferReader.use {
            it.readText()
        }
        val gson = GsonBuilder().create()
        val type: Type = object : TypeToken<ArrayList<City?>?>() {}.type
        return gson.fromJson(data, type)
    }

    override fun getAbout(): String? {
        val bufferReader = application.assets.open(ABOUT_FILE_NAME).bufferedReader()
        return bufferReader.use {
            it.readText()
        }
    }

    companion object {
        private const val ABOUT_FILE_NAME = "aboutInfo.json"
        private const val CITIES_FILE_NAME = "cities.json"
    }
}