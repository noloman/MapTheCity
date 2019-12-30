package me.manulorenzo.citymaps

import android.app.Application
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import me.manulorenzo.citymaps.data.City
import java.lang.reflect.Type

@Mockable
class CitiesRepository(private val application: Application) {
    fun getCities(): List<City> {
        val bufferReader = application.assets.open("cities.json").bufferedReader()
        val data = bufferReader.use {
            it.readText()
        }
        val gson = GsonBuilder().create()
        val type: Type = object : TypeToken<ArrayList<City?>?>() {}.type
        return gson.fromJson(data, type)
    }
}