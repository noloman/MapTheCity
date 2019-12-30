package me.manulorenzo.citymaps.city

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.manulorenzo.citymaps.city.data.source.Repository

@Suppress("UNCHECKED_CAST")
class CityListViewModelFactory(
    private val repository: Repository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CityListViewModel(repository) as T
}