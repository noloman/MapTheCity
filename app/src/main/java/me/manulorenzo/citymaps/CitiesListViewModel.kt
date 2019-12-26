package me.manulorenzo.citymaps

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.manulorenzo.citymaps.data.City

class CitiesListViewModel(val app: Application) : AndroidViewModel(app) {
    private val repository: CitiesRepository = CitiesRepository(app)
    /**
     * The implementation of insert() in the database is completely hidden from the UI.
     * Room ensures that you're not doing any long running operations on
     * the main thread, blocking the UI, so we don't need to handle changing Dispatchers.
     * ViewModels have a coroutine scope based on their lifecycle called
     * viewModelScope which we can use here.
     */
    val allCities: LiveData<List<City>> =
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            val cities = repository.getCities().sortedBy { city: City -> city.name }
            withContext(Dispatchers.Main) {
                emit(cities)
            }
        }
}