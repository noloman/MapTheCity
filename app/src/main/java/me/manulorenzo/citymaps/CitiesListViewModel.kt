package me.manulorenzo.citymaps

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import me.manulorenzo.citymaps.data.City

class CitiesListViewModel(private val repository: Repository) : ViewModel() {
    @VisibleForTesting
    val allCities: LiveData<List<City>> =
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            val cities = repository.getCities().sortedBy { city: City -> city.name }
            emit(cities)
        }
}