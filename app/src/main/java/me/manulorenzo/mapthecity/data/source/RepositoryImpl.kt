package me.manulorenzo.mapthecity.data.source

import android.app.Application
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import me.manulorenzo.mapthecity.about.AboutInfo
import me.manulorenzo.mapthecity.city.data.City
import me.manulorenzo.mapthecity.data.Resource
import java.io.IOException
import java.lang.reflect.Type

@Mockable
class RepositoryImpl(private val application: Application) :
    Repository {
    override suspend fun getCities(): Resource<List<City>> =
        try {
            val bufferReader = application.assets.open(CITIES_FILE_NAME).bufferedReader()
            val data = bufferReader.use {
                it.readText()
            }
            val gson = GsonBuilder().create()
            val type: Type = object : TypeToken<ArrayList<City?>?>() {}.type
            val fromJson = gson.fromJson<List<City>>(data, type)
            Resource.Success(fromJson)
        } catch (e: JsonSyntaxException) {
            Resource.Error(JSONSYNTAXEXCEPTION_ERROR_MESSAGE)
        } catch (e: IOException) {
            Resource.Error(IOEXCEPTION_ERROR_MESSAGE)
        }

    override suspend fun getAbout(): Resource<AboutInfo> =
        try {
            val bufferReader = application.assets.open(ABOUT_FILE_NAME).bufferedReader()
            val data = bufferReader.use {
                it.readText()
            }
            val gson = GsonBuilder().create()
            val type: Type = object : TypeToken<AboutInfo?>() {}.type
            val aboutInfo: AboutInfo = gson.fromJson(data, type)
            Resource.Success(aboutInfo)
        } catch (e: JsonSyntaxException) {
            Resource.Error(JSONSYNTAXEXCEPTION_ERROR_MESSAGE)
        } catch (e: IOException) {
            Resource.Error(IOEXCEPTION_ERROR_MESSAGE)
        }

    companion object {
        private const val ABOUT_FILE_NAME = "aboutInfo.json"
        private const val CITIES_FILE_NAME = "cities.json"
        private const val IOEXCEPTION_ERROR_MESSAGE = "Error opening file from assets"
        private const val JSONSYNTAXEXCEPTION_ERROR_MESSAGE = "Error parsing JSON"
    }
}