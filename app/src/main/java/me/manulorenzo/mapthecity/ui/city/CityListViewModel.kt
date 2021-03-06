package me.manulorenzo.mapthecity.ui.city

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import me.manulorenzo.mapthecity.data.Resource
import me.manulorenzo.mapthecity.data.source.Repository
import me.manulorenzo.mapthecity.ui.city.data.City

class CityListViewModel(
    coroutinesIoDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val repository: Repository
) : ViewModel() {
    @VisibleForTesting
    val allCities: LiveData<Resource<List<City>>> =
        liveData(context = viewModelScope.coroutineContext + coroutinesIoDispatcher) {
            emit(Resource.Loading())
            emit(repository.getCities())
        }
}