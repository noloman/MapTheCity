package me.manulorenzo.citymaps.city

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import me.manulorenzo.citymaps.city.data.City
import me.manulorenzo.citymaps.data.Resource
import me.manulorenzo.citymaps.data.source.Repository

class CityListViewModel(private val repository: Repository) : ViewModel() {
    @VisibleForTesting
    val allCities: LiveData<Resource<List<City>>> =
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(Resource.Loading())
            when (val cities = repository.getCities()) {
                is Resource.Success -> {
                    cities.data?.let {
                        emit(Resource.Success(it.sortedBy { city: City -> city.name }))
                    }
                }
                else -> emit(cities)
            }
        }
}