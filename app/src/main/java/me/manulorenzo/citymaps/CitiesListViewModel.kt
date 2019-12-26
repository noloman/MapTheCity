package me.manulorenzo.citymaps

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.manulorenzo.citymaps.data.City
import me.manulorenzo.citymaps.entity.CityEntity
import java.lang.reflect.Type

class CitiesListViewModel(val app: Application) : AndroidViewModel(app) {
    /**
     * The implementation of insert() in the database is completely hidden from the UI.
     * Room ensures that you're not doing any long running operations on
     * the main thread, blocking the UI, so we don't need to handle changing Dispatchers.
     * ViewModels have a coroutine scope based on their lifecycle called
     * viewModelScope which we can use here.
     */
    val allCities: LiveData<List<CityEntity>> =
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            if (repository.fetchNumberRows() <= 0) {
                val bufferReader = app.assets.open("cities.json").bufferedReader()
                val data = bufferReader.use {
                    it.readText()
                }
                val gson = GsonBuilder().create()
                val type: Type = object : TypeToken<ArrayList<City?>?>() {}.type
                val cityList: List<City> = gson.fromJson(data, type)
                val cityEntityList: List<CityEntity> = cityList.toCityEntity()
                repository.insertAll(cityEntityList)
                withContext(Dispatchers.Main) {
                    emit(cityEntityList)
                }
            } else {
                val cities = repository.allCities
                withContext(Dispatchers.Main) {
                    emit(cities)
                }
            }
        }
    private val repository: CitiesRepository

    init {
        val cityDao = CitiesDatabase.getDatabase(app).cityDao()
        repository = CitiesRepository(cityDao)
    }
}