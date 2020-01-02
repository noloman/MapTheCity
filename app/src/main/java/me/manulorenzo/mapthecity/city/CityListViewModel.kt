package me.manulorenzo.mapthecity.city

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import me.manulorenzo.mapthecity.city.data.City
import me.manulorenzo.mapthecity.data.Resource
import me.manulorenzo.mapthecity.data.source.Repository

class CityListViewModel(private val repository: Repository) : ViewModel() {
    @VisibleForTesting
    val allCities: LiveData<Resource<List<City>>> =
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(Resource.Loading())
            emit(repository.getCities())
        }
}