package me.manulorenzo.mapthecity.data.source

import android.app.Application
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import me.manulorenzo.mapthecity.data.Resource
import me.manulorenzo.mapthecity.ui.about.AboutInfo
import me.manulorenzo.mapthecity.ui.city.data.City
import java.io.IOException
import java.lang.reflect.Type

@Mockable
class RepositoryImpl(private val application: Application) :
    Repository {
    lateinit var cities: Resource<List<City>>
    lateinit var about: Resource<AboutInfo>
    /**
     * Ideally the list of cities would be retrieved from a DB for example, and we would sort the cities
     * alphabetically. Nevertheless here I'm just going to sort them using business logic in the repository, as we don't have a DB.
     */
    override suspend fun getCities(): Resource<List<City>> {
        synchronized(this) {
            if (::cities.isInitialized && cities is Resource.Success) {
                return cities
            } else {
                try {
                    val bufferReader = application.assets.open(CITIES_FILE_NAME).bufferedReader()
                    val data = bufferReader.use {
                        it.readText()
                    }
                    val gson = GsonBuilder().create()
                    val type: Type = object : TypeToken<ArrayList<City?>?>() {}.type
                    val fromJson: List<City> =
                        gson.fromJson<List<City>>(data, type).sortedBy { it.name }
                    cities = Resource.Success(fromJson)
                    return cities
                } catch (e: JsonSyntaxException) {
                    cities = Resource.Error(JSONSYNTAXEXCEPTION_ERROR_MESSAGE)
                    return cities
                } catch (e: IOException) {
                    cities = Resource.Error(IOEXCEPTION_ERROR_MESSAGE)
                    return cities
                }
            }
        }
    }

    override suspend fun getAbout(): Resource<AboutInfo> {
        synchronized(this) {
            if (::about.isInitialized && about is Resource.Success) {
                return about
            } else {
                try {
                    val bufferReader = application.assets.open(ABOUT_FILE_NAME).bufferedReader()
                    val data = bufferReader.use {
                        it.readText()
                    }
                    val gson = GsonBuilder().create()
                    val type: Type = object : TypeToken<AboutInfo?>() {}.type
                    val aboutInfo: AboutInfo = gson.fromJson(data, type)
                    about = Resource.Success(aboutInfo)
                    return about
                } catch (e: JsonSyntaxException) {
                    about = Resource.Error(JSONSYNTAXEXCEPTION_ERROR_MESSAGE)
                    return about
                } catch (e: IOException) {
                    about = Resource.Error(IOEXCEPTION_ERROR_MESSAGE)
                    return about
                }
            }
        }
    }

    companion object {
        private const val ABOUT_FILE_NAME = "aboutInfo.json"
        private const val CITIES_FILE_NAME = "cities.json"
        private const val IOEXCEPTION_ERROR_MESSAGE = "Error opening file from assets"
        private const val JSONSYNTAXEXCEPTION_ERROR_MESSAGE = "Error parsing JSON"
    }
}