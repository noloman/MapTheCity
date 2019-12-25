package me.manulorenzo.citymaps

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.manulorenzo.citymaps.entity.CityEntity

class CitiesListViewModel(application: Application) : AndroidViewModel(application) {
    val allCities: LiveData<List<CityEntity>>
    private val repository: CitiesRepository

    init {
        val cityDao = CitiesDatabase.getDatabase(application, viewModelScope).cityDao()
        repository = CitiesRepository(cityDao)
        allCities = repository.allCities
    }

    /**
     * The implementation of insert() in the database is completely hidden from the UI.
     * Room ensures that you're not doing any long running operations on
     * the main thread, blocking the UI, so we don't need to handle changing Dispatchers.
     * ViewModels have a coroutine scope based on their lifecycle called
     * viewModelScope which we can use here.
     */
    fun insert(cityList: List<CityEntity>) = viewModelScope.launch {
        repository.insertAll(cityList)
    }
}